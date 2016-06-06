from pyparsing import *
from collections import deque
from pprint import *
from javafileemitter import JavaFileEmitter

class ModelGenerator(JavaFileEmitter):
    def __init__(self, model, symbolTable, package, root_package, actor_id):
        super(ModelGenerator, self).__init__(model, symbolTable, package, root_package)
        self.actor_id = actor_id
        self.actor_data = symbolTable['actors'][actor_id]
        
        for actor in model.actors:
            if actor.identifier == actor_id:
                self.actor = actor

        self.className = self.namegen.className(actor_id + '_model')
        self.imports = [root_package + '.models.Sorts.*']

    def toJava(s):
        s.openFile(s.className)
        s.constructModel()
        s.closeFile()

        return super(ModelGenerator, s).toJava()

    def constructModel(s):
        s.openClass()
        
        s.emitProperties()

        s.emitConstructor()

        s.emitActions()

        s.emitGuards()

        s.closeClass()

    def emitProperties(s):
        for state in s.actor.states:
            t = state.type
            s.emit('public')
            if t == 'Bool':
                s.emit('boolean')
            elif t == 'Natural':
                s.emit('integer')
            else:
                s.emit(t)
            s.emit(state.identifier, ';')
            s.newline()

        s.newline()

    def emitConstructor(s):
        params = []
        for state in s.actor_data['parameters']:
            t = state['type']
            if t == 'Bool':
                t = 'boolean'
            if t == 'Nat':
                t = 'integer'

            params.append(t + ' ' + state['identifier'])

        s.emitFunctionBlock(params=params, name=s.className, func=s.constructor)

    def emitActions(s):
        for action in s.actor.actions:
            s.emitAction(action)
            s.newline()

    def emitGuards(s):
        for guarded_actor in s.actor.guarded_actors:
            guarded_actor_entry = s.symbolTable['actors'][guarded_actor.actor]
            for guarded_instance in guarded_actor_entry['instances']:
                for guarded_function in guarded_actor.functions:
                    guarded_function_entry = s.symbolTable['actions'][guarded_actor.actor + '_' + guarded_function.function_identifier]
                    guard_label = s.namegen.guardLabel(None, guarded_instance, guarded_function.function_identifier)

                    parameters = []
                    action_parameters = {}
                    i = 0
                    for param in guarded_function_entry['parameters']:
                        i += 1
                        parameters.append(param + ' ' + param.lower() + str(i))
                        action_parameters[param.lower() + str(i)] = param

                    funcparams = [guarded_function, action_parameters]
                    s.emitFunctionBlock(returntype='boolean', name=guard_label, func=s.guard, funcparams=funcparams, params=parameters)

    def guard(s, guarded_function, action_params):
        inner_function = s.returnTrue
        s.action(None, guarded_function.function_blocks, action_params, inner_function)

        s.emit('return', 'false', ';')
        s.newline()

    def constructor(s):
        for state in s.actor.states:
            s.emit('this.' + state.identifier)
            s.emit('=')
            s.emit(state.identifier)
            s.emit(';')
            s.newline()

        s.newline()

    def emitAction(s, action):
        params = []
        i = 0

        parameters = []
        action_parameters = {}
        i = 0
        for param in action.parameter_types:
            i += 1
            parameters.append(param + ' ' + param.lower() + str(i))
            action_parameters[param.lower() + str(i)] = param

        inner_function = s.assignments

        funcparams = [action.identifier, action.function_blocks, action_parameters, inner_function]
        s.emitFunctionBlock(returntype='boolean', public=True, func=s.makeAction, funcparams=funcparams, params=parameters, name=action.identifier)

    def makeAction(s, action_label, function_blocks, action_params, inner_function):
        s.action(action_label, function_blocks, action_params, inner_function)
        s.emit('return false;')
        s.newline()

    def action(s, action_label, function_blocks, action_params, inner_function):
        for function_block in function_blocks:
            if function_block.ifelse:
                s.emitElseIf(action_label, function_block.ifelse, action_params, inner_function)

            elif function_block.function:
                s.emitFunction(action_label, function_block.function, action_params, inner_function)

    def emitElseIf(s, action_label, elseif, action_params, inner_function):
        ifst = elseif.ifst
        s.emit('if', '(')

        actor_params = {}
        for p in s.actor_data['parameters']:
            actor_params[p['identifier']] = p['type']

        types = action_params.copy()
        types.update(actor_params)
        s.emitCondition(ifst.condition, types)

        s.emit(')', '{')
        s.newline()
        s.indent()
        s.action(action_label, elseif.ifst.function_blocks, action_params, inner_function)
        s.newline()
        s.unindent()
        s.emit('}')
        s.newline()

        elseifs = elseif.elseifst
        if len(elseifs):
            for elseifst in elseifs:
                s.emit('else', 'if', '(')

                s.emitCondition(elseifst.condition, action_params)

                s.emit(')', '{')
                s.newline()
                s.indent()

                s.action(action_label, elseifst.function_blocks, action_params, inner_function)
                s.newline()

                s.unindent()
                s.emit('}')
                s.newline()

        if elseif.elsest:
            s.emit('else', '{')
            s.newline()

            s.indent()
            s.action(action_label, elseif.elsest.function_blocks, action_params, inner_function)
            s.newline()

            s.unindent()
            s.emit('}')
            s.newline()

    def emitCondition(s, condition, types):
        s.emitComp(condition.comp, types)
        if condition.andc:
            s.emit('&&')
            s.emitCondition(condition.condition, types)
        elif condition.orc:
            s.emit('||')
            s.emitCondition(condition.condition, types)

    def emitComp(s, comp, types):
        s.emit(comp.prop)
        s.emit(comp.comparator)

        proptype = types[comp.prop]
        sort = s.symbolTable['sorts'][proptype]
        if sort['type'] == 'struct' and comp.val in sort['items']:
            s.emitProperty(types[comp.prop], comp.val)
        else:
            s.emit(comp.val)

    def emitFunction(s, action_label, function, action_params, inner_function):
        actor_params = {}
        for p in s.actor_data['parameters']:
            actor_params[p['identifier']] = p['type']

        types = action_params.copy()
        types.update(actor_params)

        if len(action_params):
            s.emit('if', '(')
            for param_name, param in zip(action_params.keys(), function.parameters):
                proptype = types[param_name]
                sort = s.symbolTable['sorts'][types[param_name]]

                if type(param) == str:
                    if sort['type'] == 'struct' and param in sort['items']:
                        s.emit(param_name, '==', proptype + '.' + str(param))
                    else:
                        s.emit(param_name, '==', str(param))
                elif param.anyst:
                    s.emitAnyOrNot(param_name, param.type, [])
                elif param.notst:
                    s.emitAnyOrNot(param_name, param.type, param.vals)

                s.emit('&&')

            s.tosstoken()
            s.emit(')', '{')

            s.newline()
            s.indent()

        inner_function(action_label, function, types)

        if len(action_params):
            s.unindent()
            s.emit('}')
            s.newline()

    def assignments(s, action_label, function, types):
        for ass in function.assignments:
            proptype = types[ass.prop]
            sort = s.symbolTable['sorts'][proptype]

            if sort['type'] == 'struct':
                s.emit('this.' + ass.prop, '=')
                s.emitProperty(proptype, ass.val)
                s.emit(';')
            else:
                s.emit('this.' + ass.prop, '=', ass.val, ';')
            s.newline()

        s.emit('return', 'true', ';')
        s.newline()

    def emitAnyOrNot(s, var, sort, nots):
        s.emit('(')
        s.newline()
        s.indent()
        items = s.symbolTable['sorts'][sort]['items']
        for item in items:
            if item not in nots:
                s.emit(var)
                s.emit('==')
                s.emitProperty(sort, item)
                s.emit('||')
                s.newline()
        s.tosstoken()
        s.unindent()
        s.emit(')')

    def returnTrue(s, action_label, function, action_params):
        s.emit('return', 'true', ';')
        s.newline()
