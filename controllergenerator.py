from pyparsing import *
from collections import deque
from pprint import *
from javafileemitter import JavaFileEmitter

# TODO: Emit javadoc at the top that lists all functions.
class ControllerGenerator(JavaFileEmitter):

    className = 'Controller'

    def toJava(s):
        s.openFile(s.className)

        s.openClass()
        s.emitProperties()
        s.emitFunctionBlock(name=s.className, func=s.constructor)
        s.emitGuards()
        s.closeClass()

        s.closeFile()

        return super(ControllerGenerator, s).toJava()

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
                s.emit('self.' + instance_id)
                s.emit('=', 'new', class_label, '(')

                if len(actor_parameters):
                    for param in actor_parameters:
                        val = instance_parameters[param['identifier']]
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
                    function_name = s.namegen.camelcase('is_' + guarded_instance + '_' + action['identifier'] + '_allowed')
                    function_name = function_name[0].lower() + function_name[1:]

                    s.emitFunctionBlock(name=function_name, returntype='boolean', funcparams=[guards, guarded_instance, action, parameter_names], func=s.isAllowed, params=parameters)


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

    # TODO: Generate an 'action' method for each action that can be performed, which is only performed if the 'allows*' method returns true.
    def performAction(s, action):
        pass
