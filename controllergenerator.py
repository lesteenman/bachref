from pyparsing import *
from collections import deque
from pprint import *
from javafileemitter import JavaFileEmitter

class ControllerGenerator(JavaFileEmitter):

    className = 'Controller'

    def toJava(s):
        s.openFile(s.className)

        s.imports = [
            s.root_package + '.models.*',
            s.root_package + '.models.Sorts.*',
            'java.io.*',
            'java.util.HashMap'
        ]

        s.openClass()
        s.emitDoc()
        s.emitProperties()
        s.emitFunctionBlock(name=s.className, func=s.constructor)
        s.emitActions()
        s.emitGuards()
        s.emitGetStates()
        s.closeClass()

        s.closeFile()

        return super(ControllerGenerator, s).toJava()

    def emitDoc(s):
        s.emit('/**')
        s.newline()

        s.emit('Types:')
        s.newline()

        s.indent()
        for sortname, sort in s.symbolTable['sorts'].iteritems():
            if 'items' in sort:
                s.emit(sortname)
                s.newline()
                items = sort['items']
                s.indent()
                for item in items:
                    s.emit(item)
                    s.newline()
                s.unindent()
        s.unindent()

        s.newline()
        s.emit('Available functions:')
        s.newline()
        s.indent()
        for action_id, action in s.symbolTable['actions'].iteritems():
            actor = action['actor']
            instances = s.symbolTable['actors'][actor]['instances'].keys()
            for instance in instances:
                function_name = instance + '_' + action['identifier']
                params = action['parameters']
                s.emit(function_name)
                s.newline()
                s.indent()
                for param in params:
                    s.emit(param)
                    s.newline()
                s.unindent()
        s.emit('**/')
        s.newline()
        s.newline()

    def emitProperties(s):
        for actor_id, actor in s.symbolTable['actors'].iteritems():
            class_label = s.namegen.className(actor_id + '_model')
            for instance_id, instance in actor['instances'].iteritems():
                s.emit(class_label, instance_id, ';')
                s.newline()

        s.newline()

    def constructor(s):
        for actor_id, actor in s.symbolTable['actors'].iteritems():
            actor_parameters = actor['parameters']
            class_label = s.namegen.className(actor_id + '_model')

            for instance_id, instance_parameters in actor['instances'].iteritems():
                s.emit('this.' + instance_id)
                s.emit('=', 'new', class_label, '(')

                if len(actor_parameters):
                    for param in actor_parameters:
                        # param_type = s.symbolTable['sorts'][param['type']]
                        val = instance_parameters[param['identifier']]

                        if s.symbolTable['sorts'][param['type']]['type'] == 'struct':
                            s.emitProperty(param['type'], val)
                        else:
                            s.emit(val)
                        s.emit(',')
                    s.tosstoken()

                s.emit(')', ';')
                s.newline()

    # TODO: Add a function that returns all currently allowed parameters for an action, or null if it's not allowed.
    def emitGuards(s):
        for action_id, action in s.symbolTable['actions'].iteritems():
            guards = action['guards']
            if len(guards):
                guarded_instances = s.symbolTable['actors'][action['actor']]['instances'].keys()
                parameters = []
                parameter_names = []
                i = 0
                for param in action['parameters']:
                    i += 1
                    parameters.append(param + ' ' + param.lower() + str(i))
                    parameter_names.append(param.lower() + str(i))

                for guarded_instance in guarded_instances:
                    function_name = s.namegen.camelcase('is_' + guarded_instance + '_' + action['identifier'] + '_allowed', True)

                    s.emitFunctionBlock(name=function_name, returntype='boolean', funcparams=[guards, guarded_instance, action, parameter_names], func=s.isAllowed, params=parameters)
    
    def emitActions(s):
        for action_id, action in s.symbolTable['actions'].iteritems():
            actor = action['actor']
            instances = s.symbolTable['actors'][actor]['instances'].keys()
            for instance in instances:
                # s.emit(str(action))
                function_name = s.namegen.camelcase('perform_' + instance + '_' + action['identifier'], True)

                parameters = []
                parameter_names = []
                i = 0
                for param in action['parameters']:
                    i += 1
                    parameters.append(param + ' ' + param.lower() + str(i))
                    parameter_names.append(param.lower() + str(i))

                s.emitFunctionBlock(name=function_name, returntype='boolean', funcparams=[instance, action, parameter_names], func=s.performAction, params=parameters)

    def isAllowed(s, guards, guarded_instance, action, parameters):
        s.emit('boolean', 'allow', '=', 'true', ';')
        s.newline()

        for guard in guards:
            for guard_instance in s.symbolTable['actors'][guard]['instances']:
                if guard_instance != guarded_instance:
                    allows_label = s.namegen.guardLabel(None, guarded_instance, action['identifier'])
                    s.emit('allow', '=', 'allow', '&&')
                    s.emit(guard_instance + '.' + allows_label, '(')

                    if len(parameters):
                        for parameter in parameters:
                            s.emit(parameter, ',')
                        s.tosstoken()

                    s.emit(')', ';')
                    s.newline()

        s.emit('return', 'allow', ';')
        s.newline()

    def performAction(s, instance, action, parameters):

        # Test guard
        if len(action['guards']):
            s.emit('if', '(')
            guard_label = s.namegen.camelcase('is_' + instance + '_' + action['identifier'] + '_allowed', True)
            s.emit('this.' + guard_label, '(')
            if len(parameters):
                for param in parameters:
                    s.emit(param)
                    s.emit(',')
                s.tosstoken()
            s.emit(')')

            s.emit(')', '{')
            s.newline()
            s.indent()

        # Perform action
        s.emit('return', 'this.' + instance + '.' + action['identifier'], '(')
        if len(parameters):
            for param in parameters:
                s.emit(param)
                s.emit(',')
            s.tosstoken()
        s.emit(')', ';')
        s.newline()

        if len(action['guards']):
            s.unindent()
            s.emit('}', 'else', '{')
            s.newline()
            s.indent()
            s.emit('return', 'false', ';')
            s.newline()

            s.unindent()
            s.emit('}')
            s.newline()

    def emitGetStates(s):
        s.emitFunctionBlock(name='getStates', returntype='HashMap<String,HashMap<String,String>>', func=s.getStates)

    def getStates(s):
        s.emit('HashMap<String, HashMap<String, String>> states = new HashMap<String, HashMap<String, String>>();')
        s.newline()

        s.emit('HashMap<String, String> instance_states = null;')
        s.newline()

        for actor_id, actor in s.symbolTable['actors'].iteritems():
            actor_parameters = actor['parameters']
            if len(actor_parameters):
                for instance_id, instance in actor['instances'].iteritems():
                    s.emit('instance_states = new HashMap<String, String>();')
                    s.newline()
                    for state in actor['parameters']:
                        s.emitProperty('instance_states', 'put')
                        s.emit('(', '"' + state['identifier'] + '"', ',')
                        s.emitProperty('String', 'valueOf')
                        s.emit('(')
                        s.emitProperty('this', instance_id, state['identifier'])
                        s.emit(')', ')', ';')
                        s.newline()
                    s.emitProperty('states', 'put')
                    s.emit('(', '"' + instance_id + '"', ',', 'instance_states', ')', ';')
                    s.newline()

        s.emit('return', 'states', ';')
        s.newline()
