from pyparsing import *
from collections import deque
from pprint import *

newline = '\r\n'
indent = '\t'

# TODO: Support multiple instances. Probably translate each function to each instance.
#       In that case, maybe Any() can be removed, just as the next ToDo.
# TODO: Assign an Any() to a variable to use in the function block.
# TODO: Create a toolchain script
# TODO: Translate to Java
# TODO: Parameter checking for guards
# TODO: Type checking for action parameters

# Naming scheme for actions: <actor>_<identifier>
# Naming scheme for guards: <guarding_actor>_allows_<actor>_<identifier>
# Naming scheme for triggers: <perform>_<actor>_<identifier>
class Mcrl2Generator:
    model = None
    mcrl2 = []
    line = deque([])

    current_indent = 0

    def __init__(self, model, symbolTable):
        self.model = model
        self.symbolTable = symbolTable
        self.mcrl2 = []
        self.line = deque([])
        self.errors = [];

    def toMcrl2(self):
        self.emitActionsList(self.symbolTable['actions'])
        self.newline()
        self.emitSorts(self.model.sorts)
        self.newline()
        self.emitActors(self.model.actors)
        self.emitInit(self.symbolTable)

        output = ''
        for line in self.mcrl2:
            output += line.popleft()
            output += ' '.join(line)
            output += newline

        return output

    def emit(*args):
        tokens = list(args)
        self = tokens.pop(0)
        for t in tokens:
            self.line.append(t)

    # Throw away the {num} last appended lines, not counting the active line.
    def tossline(s, num = 1):
        for i in range(num):
            s.mcrl2.pop()

    def tosstoken(s, num = 1):
        for i in range(num):
            s.line.pop()

    # Append the active line to the mcrl2 and clear it.
    def newline(s):
        s.line.appendleft(indent * s.current_indent)

        s.mcrl2.append(s.line)
        s.line = deque([])

    def indent(s):
        s.current_indent += 1

    def unindent(s):
        s.current_indent -= 1

    def emitInit(s, symbolTable):
        s.emit('init')
        s.newline()
        s.indent()

        s.emitAllow(symbolTable['actions'])
        s.emit(',')
        s.emit('comm')
        s.emit('(')
        s.emitComm(symbolTable['actions'])
        s.emit(',')
        s.emitComposition(symbolTable['actors'])
        s.emit(')')
        s.emit(')')
        s.emit(';')
        s.newline()

    def emitAllow(s, actions):
        s.emit('allow')
        s.emit('(')
        s.emit('{')
        s.newline()
        s.indent()
        
        for act, action in actions.iteritems():
            actor = s.symbolTable['actors'][action['actor']]
            for instance in actor['instances']:
                action_label = instance + '_' + action['identifier']
                if len(action['guards']) > 0:
                    allow_label = 'perform_' + action_label
                else:
                    allow_label = action_label
                s.emit(allow_label)

                s.emit(',')
        s.tosstoken()

        s.newline()
        s.unindent()
        s.emit('}')

    def emitComm(s, actions):
        s.emit('{')
        s.newline()
        s.indent()

        for act, action in actions.iteritems():
            actor = s.symbolTable['actors'][action['actor']]
            for instance in actor['instances']:
                action_label = instance + '_' + action['identifier']
                if len(action['guards']) > 0:
                    s.newline()
                    allow_label = 'perform_' + action_label

                    # TODO: Instances
                    s.emit(action_label)
                    s.emit('|')
                    for guard in action['guards']:
                        guard_actor = s.symbolTable['actors'][guard]
                        for guard_instance in guard_actor['instances']:
                            guard_label = guard_instance + '_allows_' + action_label
                            s.emit(guard_label)
                            s.emit('|')
                    s.tosstoken()
                    s.emit('->')
                    s.emit(allow_label)
                    s.emit(',')

        s.tosstoken()
        s.newline()

        s.newline()
        s.unindent()
        s.emit('}')
        s.newline()

    def emitComposition(s, actors):
        for actor_name, actor in actors.iteritems():
            s.newline()
            for instance_name, instance in actor['instances'].iteritems():
                s.emit(instance_name)
                s.emit('(')
                for key, val in instance.iteritems():
                    s.emit(key)
                    s.emit('=')
                    s.emit(val)
                    s.emit(',')
                s.tosstoken()
                s.emit(')')
                s.emit('||')

        s.tosstoken()
        s.newline()

    def emitActionsList(s, actions):
        s.emit('act')
        s.newline()
        s.indent()

        pp = PrettyPrinter(depth=5)
        # pp.pprint(actions)
        # pp.pprint(s.symbolTable['actors'])

        for act, details in actions.iteritems():
            actor = s.symbolTable['actors'][details['actor']]
            guards = details['guards']

            for instance in actor['instances']:
                # print 'Emitting actions for instance ' + str(instance) + ' of actor ' + str(details['actor'])
                action = instance + '_' + details['identifier']

                s.emit(action)
                s.emit(',')
                if len(guards) > 0:
                    s.emit('perform_' + action)
                    s.emit(',')
                    for guard in guards:
                        guard_actor = s.symbolTable['actors'][guard]
                        for guard_instance in guard_actor['instances']:
                            s.emit(guard_instance + '_allows_' + action)
                            s.emit(',')

                s.tosstoken()
                
                s.emit(':')
                for parameter in details['parameters']:
                    s.emit(parameter)
                    s.emit('#')
                s.tosstoken()

                s.emit(';')

                s.newline()

        s.newline()
        s.unindent()

    def emitSorts(s, sorts):
        for sort in sorts:
            s.emit('sort')

            s.newline()
            s.indent()

            s.emit(sort.identifier)

            s.newline()
            s.unindent()

    def emitActors(s, actors):
        for actor in actors:
            if actor.instances and len(actor.instances):
                for instance in actor.instances:
                    s.emitActor(actor, instance.identifier)
                    s.newline()
            else:
                s.emitActor(actor, actor.identifier)
                s.newline()
            
    def emitActor(s, actor, actor_id):
        s.emit('proc')
        s.emit(actor_id)
        s.emit('(')
        if actor.states:
            for state in actor.states[:-1]:
                s.emit(state.identifier)
                s.emit(':')
                s.emit(state.type)
                s.emit(',')
            s.emit(actor.states[-1].identifier)
            s.emit(':')
            s.emit(actor.states[-1].type)

        s.emit(')')
        s.emit('=')

        s.newline()
        s.indent()

        s.emitActions(actor, actor_id, actor.actions)

        if len(actor.actions) and len(actor.guarded_actors):
            s.emit('+')
            s.newline()

        if len(actor.guarded_actors):
            s.emitGuards(actor, actor_id, actor.guarded_actors)

        s.emit(';')
        s.newline()

        s.unindent()

    def emitActions(s, actor, actor_id, actions):
        if len(actions):
            for action in actions:
                identifier = action.identifier
                params = action.parameter_types
                function = action.function_block

                s.emitFunctionBlock(actor, actor_id, identifier, function)
                s.emit('+')
                s.newline()
            s.tossline(1)

    # Should be called with a list of lists for the repetitions.
    def emitRecursiveFunction(s, actor, actor_id, function_id, function, repetitions, params, guards):
        if len(repetitions):
            new_repetitions = list(repetitions)
            repetition = new_repetitions.pop(0)
            for r in repetition:
                new_params = list(params)
                new_params.append(r)
                s.emitRecursiveFunction(actor, actor_id, function_id, function, new_repetitions, new_params, guards)
        else:
            if guards is None:
                action_label = actor_id + '_' + function_id
            else:
                action_label = actor_id + '_allows_' + guards + '_' + function_id

            s.emit(action_label)

            if len(params):
                s.emit('(')
                for parameter in params[:-1]:
                    s.emit(parameter)
                    s.emit(',')
                s.emit(params[-1])
                s.emit(')')
            s.emit('.')

            s.emit(actor_id)
            s.emit('(')

            for assignment in function.assignments[:-1]:
                s.emit(assignment.prop)
                s.emit('=')
                s.emit(assignment.val)
                s.emit(',')

            if len(function.assignments) > 0:
                s.emit(function.assignments[-1].prop)
                s.emit('=')
                s.emit(function.assignments[-1].val)
            s.emit(')')
            s.newline()
            s.emit('+')
            s.newline()
                    

    def emitFunction(s, actor, actor_id, function_id, function, guards):
        repetitions = []
        simpleParams = []
        if function.parameters:
            # First check for any/other parameter types.
            simpleParams = []
            for parameter in function.parameters:
                if parameter.anyst:
                    struct = s.symbolTable['sorts'][parameter.type]
                    repetitions.append(struct['items'])
                elif parameter.otherst:
                    # TODO: Do for all struct entries that are not used as an identifier by this instance.
                    # TODO: Remove?
                    print 'Other found! ' + str(parameter.type)
                elif parameter.notst:
                    items = []
                    struct = s.symbolTable['sorts'][parameter.type]
                    for i in struct['items']:
                        if i not in parameter.identifiers:
                            items.append(i)
                    repetitions.append(items)
                else:
                    simpleParams.append(parameter.identifier)

        s.emitRecursiveFunction(actor, actor_id, function_id, function, repetitions, simpleParams, guards)

        # Last line should now be a '+'
        s.tossline()

    def emitFunctionBlock(s, actor, actor_id, function_id, block, guards = None):
        if block.ifelses:
            for ifelse in block.ifelses:
                if ifelse.ifst:
                    s.emitIf(actor, actor_id, function_id, ifelse.ifst, ifelse.elseifst, ifelse.elsest, guards)
        if block.functions:
            for function in block.functions:
                s.emitFunction(actor, actor_id, function_id, function, guards)

    def emitIf(s, actor, actor_id, function_id, ifst, elseifst, elsest, guards):
            # s.emit('<startif>')
            s.emit('(')
            s.emitCondition(ifst.condition)
            s.emit(')')
            s.emit('->')
            s.emit('(')

            s.newline()
            s.indent()

            s.emitFunctionBlock(actor, actor_id, function_id, ifst.function_block, guards)

            s.unindent()
            s.emit(')')
            s.newline()

            if elseifst and len(elseifst):
                    stat = elseifst.pop(0)
                    s.emitElseIf(actor, actor_id, function_id, stat, elseifst, elsest, guards)
            elif elsest:
                    s.emitElse(actor, actor_id, function_id, elsest, guards)
            # s.emit('<endif>')

    def emitElseIf(s, actor, actor_id, function_id, stat, elseifst, elsest, guards):
            # s.emit('<startelseif>')
            s.emit('<>')
            s.emit('(')
            s.newline()
            s.indent()
            s.emitIf(actor, actor_id, function_id, stat, elseifst, elsest, guards)
            s.emit(')')
            s.unindent()
            s.newline()
            # s.emit('<endelseif>')

    def emitElse(s, actor, actor_id, function_id, elsest, guards):
            s.emit('<>')
            s.indent()
            s.emit('(')
            s.emitFunctionBlock(actor, actor_id, function_id, elsest.function_block, guards)
            s.emit(')')
            s.unindent()
            s.newline()
            

    def emitCondition(s, cond):
            s.emitComp(cond.comp)
            if cond.andc:
                    s.emit('&&')
                    s.emitCondition(cond.condition)
            if cond.orc:
                    s.emit('||')
                    s.emitCondition(cond.condition)

    def emitComp(s, comp):
        if comp.comparator:
            s.emit(comp.prop)
            s.emit(comp.comparator)
            s.emit(comp.val)
        else:
            if comp.negation:
                s.emit('!')
            s.emit(comp.prop)

    def emitGuards(s, actor, actor_id, guarded_actors):
        for guarded_actor in guarded_actors:
            # TODO: Do this for each instance of a guarded actor!

            guarded_actor_id = guarded_actor.actor
            guarded_actor_entry = s.symbolTable['actors'][guarded_actor_id]

            if 'instances' in guarded_actor_entry:
                for instance in guarded_actor_entry['instances']:
                    s.emitGuardedActor(actor, actor_id, guarded_actor, instance)
                    s.emit('+')
                    s.newline()
            else:
                s.emitGuardedActor(actor, actor_id, guarded_actor, guarded_actor_id)
                s.emit('+')
                s.newline()

        s.tossline(1)

    def emitGuardedActor(s, actor, actor_id, guarded_actor, guarded_actor_id):
            for guarded_function in guarded_actor.functions:
                    guarding_function = guarded_function.function_identifier
                    s.emitFunctionBlock(actor, actor_id, guarding_function, guarded_function.function_block, guarded_actor_id)
                    s.emit('+')
                    s.newline()
            s.tossline(1)
