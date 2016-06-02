#!python
from parser import Parser
from javagenerator import JavaGenerator
from analyzer import Analyzer
import os
import pprint

print 'Basic Example...'
with open("example/example.im", 'r') as f:
	example = f.read()
	parser = Parser(example)
	parser.parse()

	analyzer = Analyzer(parser.im)
	correct = analyzer.analyze();

        if correct:
            generator = JavaGenerator(parser.im, analyzer.symbolTable, 'example')
            java = generator.toJava()
            print 'Java generated.'
            pp = pprint.PrettyPrinter()
            for f, data in java.iteritems():
                with open('example/java/' + f + '.java', 'w') as out:
                    out.write(data)
                    print 'Wrote ' + f + '.java'
        else:
            exit(1)

# exit()

print ''
print ''

print 'Advanced Example...'
with open("rollercoaster/rollercoaster.im", 'r') as f:
	example = f.read()
	parser = Parser(example)
	parser.parse()

        analyzer = Analyzer(parser.im)
        correct = analyzer.analyze()

        if correct:
            generator = JavaGenerator(parser.im, analyzer.symbolTable, 'rollercoaster')
            java = generator.toJava()
            print 'Java generated.'
            for f, data in java.iteritems():
                with open('rollercoaster/java/' + f + '.java', 'w') as out:
                    out.write(data)
                    print 'Wrote ' + f + '.java'
        else:
            exit(1)

exit(0)
