#!python
import sys
sys.path.insert(0, '../parser')

from parser import Parser
from javagenerator import JavaGenerator
from analyzer import Analyzer
import pprint

print 'Traffic Lights version 1...'
with open("../trafficlights_v1/trafficlights.im", 'r') as f:
	trafficlights = f.read()
	parser = Parser(trafficlights)
	parser.parse()

	analyzer = Analyzer(parser.im)
	correct = analyzer.analyze();

        if correct:
            generator = JavaGenerator(parser.im, analyzer.symbolTable, 'trafficlights')
            java = generator.toJava()
            generator.writeFiles('../trafficlights_v1/java')
        else:
            exit(1)

print ''
print ''

print 'Traffic Lights version 2...'
with open("../trafficlights_v2/trafficlights.im", 'r') as f:
	trafficlights = f.read()
	parser = Parser(trafficlights)
	parser.parse()

	analyzer = Analyzer(parser.im)
	correct = analyzer.analyze();

        if correct:
            generator = JavaGenerator(parser.im, analyzer.symbolTable, 'trafficlights')
            java = generator.toJava()
            generator.writeFiles('../trafficlights_v2/java')
        else:
            exit(1)

print ''
print ''

print 'Rollercoaster...'
with open("../rollercoaster/rollercoaster.im", 'r') as f:
	rollercoaster = f.read()
	parser = Parser(rollercoaster)
	parser.parse()

        analyzer = Analyzer(parser.im)
        correct = analyzer.analyze()

        if correct:
            generator = JavaGenerator(parser.im, analyzer.symbolTable, 'rollercoaster')
            java = generator.toJava()
            print 'Java generated.'
            generator.writeFiles('../rollercoaster/java')
        else:
            exit(1)

exit(0)
