#!python
import sys
sys.path.insert(0, '../parser')

from parser import Parser
from mcrl2generator import Mcrl2Generator
from analyzer import Analyzer

print 'Traffic Lights...'
with open("../trafficlights_v1/trafficlights.im", 'r') as f:
	trafficlights = f.read()
	parser = Parser(trafficlights)
	parser.parse()

	analyzer = Analyzer(parser.im)
	correct = analyzer.analyze();

        if correct:
            generator = Mcrl2Generator(parser.im, analyzer.symbolTable)
            mcrl2 = generator.toMcrl2()
            with open("../trafficlights_v1/trafficlights.mcrl2", 'w') as out:
                out.write(mcrl2)
                print 'MCRL2 generated.'
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
            generator = Mcrl2Generator(parser.im, analyzer.symbolTable)
            mcrl2 = generator.toMcrl2()
            with open("../rollercoaster/rollercoaster.mcrl2", 'w') as out:
                out.write(mcrl2)
                print 'MCRL2 generated.'
        else:
            exit(1)

exit(0)
