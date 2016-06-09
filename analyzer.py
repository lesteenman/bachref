from pyparsing import *
from pprint import *
import re

# TODO: Type checking in actor parameters

class Analyzer:
    symbolTable = {}

    def __init__(self, model):
        self.model = model
        self.parseErrors = []
        self.symbolTable = {
            'actions': { },
            'sorts': {
                'Bool': {
                    're': re.compile(r'(true|false)'),
                    'type': 'number'
                },
                'Pos': {
                    're': re.compile(r'[1-9][0-9]*'),
                    'type': 'number'
                },
                'Nat': {
                    're': re.compile(r'-?[1-9][0-9]'),
                    'type': 'number'
                },
                'Int': {
                    're': re.compile(r'-?[1-9][0-9]'),
                    'type': 'number'
                },
                'Real': {
                    're': re.compile(r'-?[1-9][0-9](/-?[1-9][0-9]*)?'),
                    'type': 'number'
                },
            }
        }

    def analyze(self):
        self.buildSortsTable()
        self.buildActorsTable()
        self.buildActionsTable()
        self.buildGuardsTable()

        if len(self.parseErrors):
            print 'Errors occured during parsing:'
            for error in self.parseErrors:
                print '\t' + error
            return False
        
        return True

    def parseError(self, error):
        self.parseErrors.append(str(error))

    def buildSortsTable(self):
        # print self.model.sorts.dump()
        for sort in self.model.sorts:
            self.testKeyword(sort)
            if sort.struct:
                identifier = sort.identifier
                items = []
                for item in sort.struct.opts:
                    self.testKeyword(item)
                    items.append(item)
                
                if identifier in self.symbolTable['sorts']:
                    self.parseError('Error: Duplicate sort definition ' + identifier);
                    
                self.symbolTable['sorts'][identifier] = {
                    'type': 'struct',
                    'items': items,
                    're': re.compile(r'(' + '|'.join(items) + ')')
                }

    def instanceOf(self, instance):
        for actor_id, actor in self.symbolTable['actors'].iteritems():
            if instance in actor['instances']:
                return actor_id
        return None

    def buildActionsTable(self):
        for actor in self.model.actors:
            for action in actor.actions:
                # print action.dump()
                identifier = action.identifier
                self.testKeyword(identifier)

                parameters = []
                for parameter_type in action.parameter_types:
                    parameters.append(parameter_type)

                action_label = actor.identifier + '_' + identifier
                if action_label in self.symbolTable['actions']:
                    self.parseError('Error: Duplicate action identifier ' + action_label)

                entry = {
                    'identifier': identifier,
                    'actor': actor.identifier,
                    'parameters': parameters,
                }
                self.symbolTable['actions'][action_label] = entry

        # pp = PrettyPrinter()
        # pp.pprint(self.symbolTable['actions'])

    def buildGuardsTable(self):
        guards = {}
        for actor in self.model.actors:
            for guarded_actor in actor.guarded_actors:
                for function in guarded_actor.functions:
                    guarded_actor_id = guarded_actor.actor
                    instanceOf = self.instanceOf(guarded_actor_id)

                    action_label = '_' + function.function_identifier

                    if instanceOf:
                        # Actor is the instance in this case
                        action_label = instanceOf + action_label
                        actor_entry = guards.get(instanceOf, {})
                        instance_entry = actor_entry.get(guarded_actor_id, {})
                        function_entry = instance_entry.get(function.function_identifier, [])
                        function_entry.append(actor.identifier)

                        instance_entry[function.function_identifier] = function_entry

                        actor_entry[guarded_actor_id] = instance_entry
                        guards[instanceOf] = actor_entry
                    else:
                        action_label = guarded_actor_id + action_label
                        actor_entry = guards.get(guarded_actor_id, {})
                        for instance in self.symbolTable['actors'][guarded_actor_id]['instances']:
                            instance_entry = actor_entry.get(instance, {})
                            function_entry = instance_entry.get(function.function_identifier, [])
                            function_entry.append(actor.identifier)

                            instance_entry[function.function_identifier] = function_entry
                            actor_entry[instance] = instance_entry
                        guards[guarded_actor_id] = actor_entry

                    # Error: Undefined function being guarded
                    if action_label not in self.symbolTable['actions'] and instanceOf == None:
                        self.parseError('Error: trying to guard an undefined function ' + \
                                str(function.function_identifier) + \
                                ' for actor ' + str(guarded_actor_id) + \
                                ', by actor ' + str(actor.identifier))
                        continue
                    
                    # Error: Trying to guard itself without other instances being available
                    if actor.identifier == guarded_actor_id:
                        # Check if there are other instances of this actor
                        entry = self.symbolTable['actors'][actor.identifier]
                        if len(entry['instances']) <= 1:
                            self.parseError('Error: The only available instance to guard of actor ' + \
                                str(actor.identifier) + ' at function ' + str(action_label) + ' is the instance itself.')
                            continue

        # pp = PrettyPrinter()
        # pp.pprint(guards)
        self.symbolTable['guards'] = guards


    def buildActorsTable(self):
        actors = {}

        for actor in self.model.actors:
            self.testKeyword(actor.identifier)

            if actor.identifier in actors:
                self.parseError('Duplicate actor identifier: ' + str(actor.identifier))

            parameters = []
            numparams = 0
            if actor.states:
                for state in actor.states:
                    numparams += 1
                    parameter = {
                        'identifier': state.identifier,
                        'type': state.type
                    }
                    parameters.append(parameter)

            instances = {}
            if actor.instances:
                for instance in actor.instances:
                    assignments = {}
                    numassignments = 0
                    for assignment in instance.assignments:
                        numassignments += 1
                        assignments[assignment.prop] = assignment.val

                    if numparams > numassignments:
                        self.parseError('Not all parameters have been initialized in instance ' + instance.identifier)
                    if numassignments > numparams:
                        self.parseError('More assignments than parameters in instance ' + instance.identifier)

                    instances[instance.identifier] = assignments
            else:
                assignments = {}
                numassignments = 0
                for assignment in actor.assignments:
                    numassignments += 1
                    assignments[assignment.prop] = assignment.val

                if numparams > numassignments:
                    self.parseError('Not all parameters have been initialized in actor ' + actor.identifier)
                if numassignments > numparams:
                    self.parseError('More assignments than parameters in actor ' + actor.identifier)

                instances[actor.identifier] = assignments

            # for state in actor.states:
            actors[actor.identifier] = {
                'instances': instances,
                'parameters': parameters
            }

        self.symbolTable['actors'] = actors

    def testKeyword(self, keyword):
        illegalchars = [
            'switch',
            'if',
            'else'
        ]

        if keyword in illegalchars:
            self.parseError('Illegal keyword used: ' + str(keyword))
