from pyparsing import *

# TODO: Allow any order within an actor.

class Parser:

	# Tokens
	upcase = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
	comment = '#' + restOfLine
	t_lbrack = Word('{')
	t_rbrack = Word('}')
	t_lbrace = Word('(')
	t_rbrace = Word(')')
	t_col = Word(':')
	eq = Word('=')
	string = Word('"') + alphanums + Word('"')

	t_if = Word('if')
	t_elseif = Word('elseif')
	t_else = Word('else')
	t_func = Word('->')
	t_other = Word('Other')
	t_any = Word('Any')
	t_not = Word('Not')
	t_struct = Word('struct')

	t_and = Word('&&')
	t_or = Word('||')

	t_comp_eq = Word('==')
	t_comp_neq = Word('!=')
	t_comp_gt = Word('>')
	t_comp_gte = Word('>=')
	t_comp_lt = Word('<')
	t_comp_lte = Word('<=')
	t_excl = Word('!')

	t_identifier = Word(alphanums + "_")
	t_type = Word(upcase, alphanums + "_")
	sorts_label = Word('sorts') + t_col
	actors_label = Word('actors') + t_col
	states_label = Word('states') + t_col
	actions_label = Word('actions') + t_col
	guards_label = Word('guards') + t_col
	instances_label = Word('instances') + t_col


	# Recursive declarations
	function_block = Forward()
	condition = Forward()

	# Parsing
	state = Group(t_identifier.setResultsName('identifier') - \
			t_col - \
			t_identifier.setResultsName('type'))

	value = Or([t_identifier, string])

	assignment = Group(t_identifier.setResultsName('prop') + \
			eq + \
			value.setResultsName('val'))

	parameter = Or([Group(t_identifier.setResultsName('identifier')), \
			Group(t_other.setResultsName('otherst') + t_lbrace + t_identifier.setResultsName('type') + t_rbrace), \
			Group(t_any.setResultsName('anyst') + t_lbrace + t_identifier.setResultsName('type') + t_rbrace), \
			Group(t_not.setResultsName('notst') + t_lbrace + t_identifier.setResultsName('type') + t_col + Group( \
				Optional(delimitedList(t_identifier)) \
			).setResultsName('identifiers') + t_rbrace) \
			])

	function = Group(t_func - \
			Group(Optional(delimitedList(parameter))).setResultsName('parameters') - \
			t_lbrack - \
			Group(ZeroOrMore(assignment)).setResultsName('assignments') - \
			t_rbrack).setResultsName('function')

	comp = ( \
		t_identifier.setResultsName('prop') + Or([ \
			t_comp_eq, t_comp_neq, t_comp_lt, t_comp_lte, t_comp_gt, t_comp_gte \
		]).setResultsName('comparator') - t_identifier.setResultsName('val') \
	) \

	condition << Group(Or([\
		comp.setResultsName('comp'), \
		comp.setResultsName('comp') - t_and.setResultsName('andc') - condition, \
		comp.setResultsName('comp') - t_or.setResultsName('orc') - condition \
	])).setResultsName('condition')

	ifelse = Group( \
			Group(t_if - t_lbrace - condition - t_rbrace - function_block).setResultsName('ifst') - \
			Group(ZeroOrMore(Group(t_elseif + t_lbrace - condition - t_rbrace - function_block))).setResultsName('elseifst') + \
			Group(Optional(t_else - function_block)).setResultsName('elsest') \
		).setResultsName('ifelse')

	function_block << t_lbrack - Group(ZeroOrMore(Or([ \
		Group(ifelse).setResultsName('ifelses'), Group(function).setResultsName('functions') \
	]))).setResultsName('function_block') - t_rbrack

	action = Group(
			t_identifier.setResultsName('identifier') - \
			t_lbrace - Group(Optional(delimitedList(t_type))).setResultsName('parameter_types') - t_rbrace - \
			t_col - \
			OneOrMore(function_block))

        instance = Group( t_identifier.setResultsName('identifier') + t_col + t_lbrack + \
            Group(OneOrMore(assignment)).setResultsName('assignments') + \
            t_rbrack )

        instances = instances_label + t_lbrack + ( \
            Group(OneOrMore(assignment)).setResultsName('assignments') \
            ^ \
            Group(OneOrMore(instance)).setResultsName('instances') \
        ) + t_rbrack

	states = states_label - t_lbrack - Group(OneOrMore(state)).setResultsName('states') + t_rbrack

	actions = actions_label - t_lbrack - Group(ZeroOrMore(action)).setResultsName('actions') - t_rbrack

	guarded_function = Group(t_identifier.setResultsName('function_identifier') - t_col - \
			function_block)

	guarded_actor = Group(t_identifier.setResultsName('actor') - \
			t_col - t_lbrack - \
			Group(ZeroOrMore(guarded_function)).setResultsName('functions') - \
			t_rbrack)

	guards = guards_label - t_lbrack - Group(ZeroOrMore(guarded_actor)).setResultsName('guarded_actors') - t_rbrack

	actor = Group(t_identifier.setResultsName('identifier') - t_col - \
			t_lbrack - (\
				Optional(instances) & Optional(states) & Optional(actions) & Optional(guards) \
			) - t_rbrack)

	actors = actors_label - \
			t_lbrack - \
			OneOrMore(actor).setResultsName('actors') - \
			t_rbrack

	struct = Group( \
			t_struct - t_lbrace - \
			Group(Optional(delimitedList(t_identifier))).setResultsName('opts') - \
			t_rbrace)

	sort = Group( \
			t_identifier.setResultsName('identifier') - t_col - Or([
					struct.setResultsName('struct')
				])
			)

	sorts = sorts_label + \
			t_lbrack + \
			OneOrMore(sort).setResultsName('sorts') + \
			t_rbrack

	model = Optional(sorts) + actors

	def parse_function_block(self, block, ind):
		# print str('\t') * ind + 'Parsing: ' + str(block)
		if block.function:
			# print str('\t') * ind + 'Has a function!'
			print str('\t') * ind + 'Function: ' + str(block)

		if block.ifelse:
			# print str('\t') * ind + 'Has an ifelse!'
			st_if = block.ifelse.ifst
			print str('\t') * ind + 'If: ' + str(st_if.condition)
			self.parse_function_block(st_if.function, ind + 1)
			for st_elseif in block.ifelse.elseifst:
				print str('\t') * ind + 'ElseIf: ' + str(st_elseif.condition)
				self.parse_function_block(st_elseif.function, ind + 1)
			if block.ifelse.elsest:
				st_else = block.ifelse.elsest
				print str('\t') * ind + 'Else: ' + str(st_else.condition)
				self.parse_function_block(st_else.function, ind + 1)

	text = ''
	def __init__(self, text):
		self.text = text

	def parse(self):
		self.model.ignore(self.comment)
		self.im = self.model.parseString(self.text)
		# return
		# for s in self.im.sorts:
		# 	print 'Sort:'
		# 	print '\t' + s.name
			
		# 	if s.struct:
		# 		print '\t\tStruct: '
		# 		print '\t\tItems: ' + str(s.struct.opts)

		# for a in self.im.actors:
		# 	print 'Actor:'
		# 	print '\t' + a.identifier

		# 	print '\tStates:'
		# 	for state in a.states:
		# 		print '\t\t' + state.id + ', type ' + state.type

		# 	print '\tActions: '
		# 	for action in a.actions:
		# 		print '\t\t' + str(action.actuator) + ':'
		# 		print '\t\tParams: ' + str(action.parameter_types)
		# 		self.parse_function_block(action.function, 3)

		# 	print '\tGuards: '
		# 	for guard in a.guarded_actors:
		# 		print '\t\t' + str(guard.actor) + ':'
		# 		for func in guard.functions:
		# 			print '\t\t\t' + str(func.name) + ':'
		# 			self.parse_function_block(func.function, 3)
		# 	print ''
