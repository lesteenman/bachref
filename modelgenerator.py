from pyparsing import *
from collections import deque
from pprint import *
from javafileemitter import JavaFileEmitter

class ModelGenerator(JavaFileEmitter):

    def __init__(self, model, symbolTable, package, actor_id):
        super(ModelGenerator, self).__init__(model, symbolTable, package)
        self.actor_id = actor_id
        self.actor_data = symbolTable['actors'][actor_id]
        
        for actor in model.actors:
            if actor.identifier == actor_id:
                self.actor = actor

        self.className = self.namegen.className(actor_id + '_model')
        self.imports = ['models.Sorts.*']

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

        for guarded_actor in s.actor.guarded_actors:
            pass
            # guarded_actor_entry = s.symbolTable['actors'][guarded_actor]
            # for instance in guarded_actor_entry['instances']:
            #     s.emitGuard(guarded_actor, instance)

        s.closeClass()

    def emitProperties(s):
        for state in s.actor.states:
            t = state.type
            if t == 'Bool':
                s.emit('boolean')
            elif t == 'Natural':
                s.emit('integer')
            else:
                s.emit(t)
            s.emit(state.identifier)
            s.emit(';')
            s.newline()

        s.newline()

    def emitConstructor(s):
        params = []
        for state in s.actor.states:
            t = state.type
            if t == 'Bool':
                t = 'boolean'
            if t == 'Nat':
                t = 'integer'

            params.append(t + ' ' + state.identifier)

        s.emitFunctionBlock(params=params, func=s.constructor)

    def constructor(s):
        for state in s.actor.states:
            s.emit('self.' + state.identifier)
            s.emit('=')
            s.emit(state.identifier)
            s.emit(';')
            s.newline()

        s.newline()

    def emitActions(s):
        for action in s.actor.actions:
            s.emitAction(action)
            s.newline()

    def emitAction(s, action):
        params = []
        i = 0
        for param in action.parameter_types:
            i += 1
            name = param.lower() + '_' + str(i)
            params.append(name)

        funcparams = [action.identifier, action.function_blocks, params]
        s.emitFunctionBlock(returntype='boolean', public=True, func=s.action, funcparams=funcparams, params=params, name=action.identifier)

    def action(s, action_label, function_blocks, action_params):
        for function_block in function_blocks:
            if function_block.ifelse:
                s.emitElseIf(action_label, function_block.ifelse, action_params)

            elif function_block.function:
                s.emitFunction(action_label, function_block.function, action_params)

    def emitElseIf(s, action_label, elseif, action_params):
        ifst = elseif.ifst
        s.emit('if', '(')
        s.emitCondition(ifst.condition)
        s.emit(')', '{')
        s.newline()
        s.indent()
        s.action(action_label, elseif.ifst.function_blocks, action_params)
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

                s.action(action_label, elseifst.function_blocks, action_params)

                s.unindent()
                s.emit('}')
                s.newline()

        if elseif.elsest:
            s.emit('else', '{')
            s.newline()
            s.indent()
            s.action(action_label, elseif.elsest.function_blocks, action_params)
            s.unindent()
            s.emit('}')
            s.newline()

    def emitCondition(s, condition):
        s.emitComp(condition.comp)
        if condition.andc:
            s.emit('&&')
            s.emitCondition(condition.condition)
        elif condition.orc:
            s.emit('||')
            s.emitCondition(condition.condition)

    def emitComp(s, comp):
        s.emit(comp.prop)
        s.emit(comp.comparator)
        s.emit(comp.val)

    def emitFunction(s, action_label, function, action_params):
        if len(action_params):
            s.emit('if', '(')
            for param_name, param in zip(action_params, function.parameters):
                s.emit(param_name, '==', str(param))
                s.emit('&&')

            s.tosstoken()
            s.emit(')', '{')

            s.newline()
            s.indent()

        for ass in function.assignments:
            s.emit('self.' + ass.prop, '=', ass.val, ';')
            s.newline()

        if len(action_params):
            s.unindent()
            s.emit('}')

        s.newline()

    def emitGuard(s, guarded_actor, guarded_instance):
        s.emit('guard:')
        s.newline()
        pass
