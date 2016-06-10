#!python

# Make sure we can import the files from the root dir
import sys
sys.path.insert(0, '../parser')

from parser import Parser
from mcrl2generator import Mcrl2Generator
from javagenerator import JavaGenerator
from analyzer import Analyzer

with open("trafficlights.im", 'r') as f:
	trafficlights = f.read()
	parser = Parser(trafficlights)
	parser.parse()

	analyzer = Analyzer(parser.im)
	correct = analyzer.analyze();

        if correct:
            generator = Mcrl2Generator(parser.im, analyzer.symbolTable)
            mcrl2 = generator.toMcrl2()
            with open("trafficlights.mcrl2", 'w') as out:
                out.write(mcrl2)
                print 'MCRL2 generated.'
        else:
            exit(1)


with open("trafficlights.im", 'r') as f:
	trafficlights = f.read()
	parser = Parser(trafficlights)
	parser.parse()

	analyzer = Analyzer(parser.im)
	correct = analyzer.analyze();

        if correct:
            generator = JavaGenerator(parser.im, analyzer.symbolTable, 'trafficlights')
            java = generator.toJava()
            generator.writeFiles('java')
        else:
            exit(1)
