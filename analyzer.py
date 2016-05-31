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
        self.buildActionTable()

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
            if sort.struct:
                identifier = sort.identifier
                items = []
                for item in sort.struct.opts:
                    items.append(item)
                
                if identifier in self.symbolTable['sorts']:
                    self.parseError('Error: Duplicate sort definition ' + identifier);
                    
                self.symbolTable['sorts'][identifier] = {
                    'type': 'struct',
                    'items': items,
                    're': re.compile(r'(' + '|'.join(items) + ')')
                }

    def buildActionTable(self):
        for actor in self.model.actors:
            for action in actor.actions:
                # print action.dump()
                identifier = action.identifier

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
                    'guards': []
                }
                self.symbolTable['actions'][action_label] = entry

        for actor in self.model.actors:
            for guarded_actor in actor.guarded_actors:
                for function in guarded_actor.functions:
                    action_label = guarded_actor.actor + '_' + function.function_identifier

                    # Error: Undefined function being guarded
                    if action_label not in self.symbolTable['actions']:
                        self.parseError('Error: trying to guard an undefined function ' + \
                                str(function.function_identifier) + \
                                ' for actor ' + str(guarded_actor.actor) + \
                                ', by actor ' + str(actor.identifier))
                    
                    # Error: Trying to guard itself without other instances being available
                    if actor.identifier == guarded_actor.actor:
                        # Check if there are other instances of this actor
                        entry = self.symbolTable['actors'][actor.identifier]
                        if len(entry['instances']) <= 1:
                            self.parseError('Error: The only available instance to guard of actor ' + \
                                str(actor.identifier) + ' at function ' + str(action_label) + ' is the instance itself.')

                    self.symbolTable['actions'][action_label]['guards'].append(actor.identifier)

    def buildActorsTable(self):
        actors = {}

        for actor in self.model.actors:
            if actor.identifier in actors:
                self.parseError('Duplicate actor identifier: ' + str(actor.identifier))

            parameters = {}
            numparams = 0
            if actor.states:
                for state in actor.states:
                    numparams += 1
                    parameters[state.identifier] = state.type

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
