from pyparsing import *
from collections import deque
from pprint import *
from names import Names

newline = '\r\n'
indent = '\t'

# TODO: Possibly check if an action has guards, and if not, automatically rename to the trigger scheme.
# TODO: If above, add utility function to see if an actor/action has guards.
# TODO: Create a toolchain script
# TODO: Type checking (analyzer)

# Naming scheme for actions: <actor>_<identifier>
# Naming scheme for guards: <guarding_actor>_allows_<actor>_<identifier>
# Naming scheme for triggers: <perform>_<actor>_<identifier>
class Mcrl2Generator:
    debug = False
    current_indent = 0

    def __init__(self, model, symbolTable):
        self.model = model
        self.symbolTable = symbolTable
        self.mcrl2 = []
        self.line = deque([])
        self.errors = [];
        self.nameGen = Names(symbolTable, 'mcrl2')

    def toMcrl2(self):
        self.emitActionsList(self.symbolTable['actions'], self.symbolTable['guards'])
        self.newline()
        self.emitSorts(self.model.sorts)
        self.newline()
        self.emitActors(self.model.actors)
        self.emitInit(self.symbolTable)

        output = ''
        i = 0
        for line in self.mcrl2:
            i += 1
            output += line.popleft()
            # try:
            output += ' '.join(line)
            # except TypeError as e:
            #     print ''
            #     print 'Error while appending lines'
            #     print '\r\n'.join(output.split('\r\n')[-10:])
            #     print ''
            #     raise Exception('At line ' + str(i) + ': ' + str(line))

            output += newline

        return output

    def emit(*args):
        tokens = list(args)
        self = tokens.pop(0)
        for t in tokens:
            if not isinstance(t, str):
                raise Exception('Attempting to emit a non-string type: ' + str(t))
            self.line.append(t)

    def startBlock(self, name):
        if self.debug:
            self.emit('<start ' + name + '>')

    def endBlock(self, name):
        if self.debug:
            self.emit('<end ' + name + '>')

    # Throw away the {num} last appended lines, not counting the active line.
    def tossline(s, num = 1):
        for i in range(num):
            s.mcrl2.pop()

    # Throw away the {num} last emitted tokens in the active line.
    def tosstoken(s, num = 1):
        for i in range(num):
            if len(s.line) > 0:
                s.line.pop()

    # Append the active line to the mcrl2 and clear it.
    def newline(s):
        s.line.appendleft(indent * s.current_indent)

        s.mcrl2.append(s.line)
        s.line = deque([])

    # Increase the indentation level starting at the active line
    def indent(s):
        s.current_indent += 1

    # Decrease the indentation level starting at the active line
    def unindent(s):
        s.current_indent -= 1

    def emitInit(s, symbolTable):
        s.startBlock('init')

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

        s.endBlock('init')

    def emitAllow(s, actions):
        s.startBlock('allow')

        s.emit('allow')
        s.emit('(')
        s.emit('{')
        s.newline()
        s.indent()
        
        for act, action in actions.iteritems():
            actor = s.symbolTable['actors'][action['actor']]
            # pp = PrettyPrinter()
            # print 'Prettyprint, ' + str(act)
            # pp.pprint(actor['instances'])
            for instance in actor['instances']:
                s.emit(s.nameGen.commLabel(instance, action['identifier']))

                s.emit(',')
        s.tosstoken()

        s.newline()
        s.unindent()
        s.emit('}')

        s.endBlock('allow')

    def emitComm(s, actions):
        s.startBlock('comm')

        s.emit('{')
        s.newline()
        s.indent()

        for act, action in actions.iteritems():
            actor = s.symbolTable['actors'][action['actor']]
            actor_guards = s.symbolTable['guards'].get(action['actor'], {})
            for instance in actor['instances']:
                action_guards = actor_guards.get(instance, {}).get(action['identifier'], [])
                if len(action_guards) > 0:
                    s.newline()

                    s.emit(s.nameGen.actionLabel(instance, action['identifier']))
                    s.emit('|')
                    for guard in action_guards:
                        guard_actor = s.symbolTable['actors'][guard]
                        for guard_instance in guard_actor['instances']:
                            if guard_instance == instance:
                                continue

                            s.emit(s.nameGen.guardLabel(guard_instance, instance, action['identifier']))
                            s.emit('|')
                    s.tosstoken()
                    s.emit('->')

                    s.emit(s.nameGen.commLabel(instance, action['identifier']))
                    s.emit(',')

        s.tosstoken()
        s.newline()

        s.newline()
        s.unindent()
        s.emit('}')
        s.newline()

        s.endBlock('comm')

    def emitComposition(s, actors):
        s.startBlock('composition')

        for actor_name, actor in actors.iteritems():
            s.newline()
            for instance_name, instance in actor['instances'].iteritems():
                s.emit(instance_name)
                if len(instance):
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

        s.endBlock('composition')

    def emitActionsList(s, actions, guards):
        s.emit('act')
        s.newline()
        s.indent()

        for action_id, action in actions.iteritems():
            actor = s.symbolTable['actors'][action['actor']]

            for instance in actor['instances']:
                # pp = PrettyPrinter()
                # print 'Prettyprint, ' + str(action['actor'])
                # pp.pprint(guards)

                action_guards = guards.get(action['actor'], {}).get(instance, {}).get(action['identifier'], [])

                s.emit(s.nameGen.actionLabel(instance, action['identifier']))
                s.emit(',')
                if len(action_guards) > 0:
                    s.emit(s.nameGen.commLabel(instance, action['identifier']))
                    s.emit(',')
                    for guard in action_guards:
                        guard_actor = s.symbolTable['actors'][guard]
                        for guard_instance in guard_actor['instances']:
                            s.emit(s.nameGen.guardLabel(guard_instance, instance, action['identifier']))
                            s.emit(',')

                s.tosstoken()
                
                s.emit(':')
                for parameter in action['parameters']:
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

            if sort.identifier in s.symbolTable['sorts']:
                data = s.symbolTable['sorts'][sort.identifier]
                if 'items' in data:
                    s.emit(sort.identifier)
                    s.emit('=')
                    s.emit('struct')
                    for item in data['items']:
                        s.emit(item)
                        s.emit('|')
                    s.tosstoken()
                    s.emit(';')
                    s.newline()
            else:
                raise Exception('Sort not recognized: ' + str(sort))

            s.newline()
            s.unindent()

    def emitActors(s, actors):
        for actor in actors:
            if actor.instances and len(actor.instances):
                for instance in actor.instances:
                    s.emitActor(actor, actor.identifier, instance.identifier)
                    s.newline()
            else:
                s.emitActor(actor, actor.identifier, actor.identifier)
                s.newline()
            
    def emitActor(s, actor, actor_id, actor_instance):
        s.emit('proc')
        s.emit(actor_instance)
        if actor.states:
            s.emit('(')
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

        s.emitActions(actor_id, actor_instance, actor.actions)

        if len(actor.actions) and len(actor.guarded_actors):
            s.emit('+')
            s.newline()

        if len(actor.guarded_actors):
            s.emitGuards(actor_id, actor_instance, actor.guarded_actors)

        s.emit(';')
        s.newline()

        s.unindent()

    def emitActions(s, actor_id, actor_instance, actions):
        if len(actions):
            for action in actions:
                identifier = action.identifier
                params = action.parameter_types
                function = action.function_blocks

                s.emitFunctionBlocks(actor_id, actor_instance, identifier, function)
                s.emit('+')
                s.newline()
            s.tossline(1)

    def emitFunction(s, actor_id, actor_instance, guarded_actor_instance, function_id, assignments, parameters):
        if guarded_actor_instance:
            s.emit(s.nameGen.guardLabel(actor_instance, guarded_actor_instance, function_id))
        else:
            s.emit(s.nameGen.actionLabel(actor_instance, function_id))

        if len(parameters):
            s.emit('(')
            for parameter in parameters:
                s.emit(parameter)
                s.emit(',')
            s.tosstoken()
            s.emit(')')
        s.emit('.')

        s.emit(actor_instance)

        actor_entry = s.symbolTable['actors'][actor_id]
        # print 'Actor entires' + str(actor_entry.keys())
        if len(actor_entry['parameters']) > 0:
            s.emit('(')
            if len(assignments) > 0:
                for assignment in assignments:
                    s.emit(assignment.prop)
                    s.emit('=')
                    s.emit(assignment.val)
                    s.emit(',')
                s.tosstoken()
            s.emit(')')

        s.newline()

    def emitSum(s, sum_type, var, negations = []):
        s.emit('sum')
        s.emit(var)
        s.emit(':')
        s.emit(sum_type)
        s.emit('.')
        if len(negations):
            s.emit('(')
            for neg in negations:
                s.emit(var)
                s.emit('!=')
                s.emit(neg)
                s.emit('&&')
            s.tosstoken()
            s.emit(')')
            s.emit('->')

    def constructFunctionParameters(s, actor_id, actor_instance, guarded_actor_instance, function_id, assignments, parameters, function_parameters):
        if len(parameters):
            nextParam = parameters.pop(0)
            if isinstance(nextParam, str): # Simple identifier
                param = nextParam
            elif nextParam.anyst: # Use a sum operator over the type
                var = nextParam.type.lower() + '_' + str(len(parameters)) # Ensure unique naming
                s.emitSum(nextParam.type, var)
                param = var
            elif nextParam.notst: # Use a sum operator over the type, excluding the not's parameters
                var = nextParam.type.lower() + '_' + str(len(parameters)) # Ensure unique naming
                s.emitSum(nextParam.type, var, nextParam.identifiers)
                param = var

            # More parameters to be appended
            function_parameters.append(param)
            s.constructFunctionParameters(actor_id, actor_instance, guarded_actor_instance, function_id, assignments, parameters, function_parameters)
        else:
            # All parameters are now in function_parameters
            s.emitFunction(actor_id, actor_instance, guarded_actor_instance, function_id, assignments, function_parameters)

    def emitFunctionBlocks(s, actor_id, actor_instance, function_id, blocks, guarded_actor_instance = None):
        s.startBlock('functionblock')

        for block in blocks:
            if block.ifelse:

                ifelse = block.ifelse
                if ifelse.ifst:
                    s.emitIf(actor_id, actor_instance, function_id, ifelse.ifst, ifelse.elseifst, ifelse.elsest, guarded_actor_instance)

            elif block.function:
                s.constructFunctionParameters(actor_id, actor_instance, guarded_actor_instance, function_id, block.function.assignments, list(block.function.parameters), [])

            s.emit('+')
            s.newline()

        s.tossline()

        s.endBlock('functionblock')

    def emitIf(s, actor_id, actor_instance, function_id, ifst, elseifst, elsest, guarded_actor_instance):
        s.startBlock('if')

        s.emit('(')
        s.emitCondition(ifst.condition)
        s.emit(')', '->', '(')

        s.newline()
        s.indent()

        s.emitFunctionBlocks(actor_id, actor_instance, function_id, ifst.function_blocks, guarded_actor_instance)

        s.unindent()
        s.emit(')')
        s.newline()

        if elseifst and len(elseifst):
            stat = elseifst.pop(0)
            s.emitElseIf(actor_id, actor_instance, function_id, stat, elseifst, elsest, guarded_actor_instance)
        elif elsest:
            s.emitElse(actor_instance, function_id, elsest, guarded_actor_instance)

        s.endBlock('if')

    def emitElseIf(s, actor_id, actor_instance, function_id, stat, elseifst, elsest, guarded_actor_instance):
        s.startBlock('elseif')

        s.emit('<>', '(')
        s.newline()
        s.indent()
        s.emitIf(actor_id, actor_instance, function_id, stat, elseifst, elsest, guarded_actor_instance)
        s.emit(')')
        s.unindent()
        s.newline()

        s.endBlock('elseif')

    def emitElse(s, actor_id, actor_instance, function_id, elsest, guarded_actor_instance):
        s.startBlock('else')

        s.emit('<>')
        s.indent()
        s.emit('(')
        s.emitFunctionBlocks(actor_id, actor_instance, function_id, elsest.function_blocks, guarded_actor_instance)
        s.emit(')')
        s.unindent()
        s.newline()

        s.endBlock('else')
            

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

    def instanceOf(self, instance):
        if instance in self.symbolTable['actors']:
            return None

        for actor_id, actor in self.symbolTable['actors'].iteritems():
            if instance in actor['instances']:
                return actor_id
        return None

    def emitGuards(s, actor_id, actor_instance, guarded_actors):
        s.startBlock('guards')

        for guarded_actor in guarded_actors:
            # Guarded actor will either be an actor or an instance
            guarded_actor_instance = guarded_actor.actor

            guarded_actor_class = s.instanceOf(guarded_actor_instance)
            if guarded_actor_class != None:
                s.emitGuardedActor(actor_id, actor_instance, guarded_actor, guarded_actor_instance)
            else:
                guarded_actor_entry = s.symbolTable['actors'][guarded_actor_instance]
                for instance in guarded_actor_entry['instances']:
                    if instance != actor_instance:
                        s.emitGuardedActor(actor_id, actor_instance, guarded_actor, instance)
                        s.emit('+')
                        s.newline()
                s.tossline()

            s.emit('+')
            s.newline()
        s.tossline()

        s.endBlock('guards')

    def emitGuardedActor(s, actor_id, actor_instance, guarded_actor, guarded_actor_instance):
        s.startBlock('guardedActor')

        for guarded_function in guarded_actor.functions:
            guarding_function = guarded_function.function_identifier
            s.emitFunctionBlocks(actor_id, actor_instance, guarding_function, guarded_function.function_blocks, guarded_actor_instance)
            s.emit('+')
            s.newline()

        s.tossline(1)
        
        s.endBlock('guardedActor')
