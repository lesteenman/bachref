#!python
from parser import Parser
from javagenerator import JavaGenerator
from analyzer import Analyzer
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
            generator.writeFiles('example/java')
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
            generator.writeFiles('rollercoaster/java')
        else:
            exit(1)

exit(0)
