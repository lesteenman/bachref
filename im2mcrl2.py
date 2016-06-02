#!python
from parser import Parser
from mcrl2generator import Mcrl2Generator
from analyzer import Analyzer
from mcrl2tool import Mcrl2Helper

print 'Basic Example...'
with open("example/example.im", 'r') as f:
	example = f.read()
	parser = Parser(example)
	parser.parse()

	analyzer = Analyzer(parser.im)
	correct = analyzer.analyze();

        if correct:
            generator = Mcrl2Generator(parser.im, analyzer.symbolTable)
            mcrl2 = generator.toMcrl2()
            with open("example/example.mcrl2", 'w') as out:
                out.write(mcrl2)
                print 'MCRL2 generated.'
        else:
            exit(1)

                # helper = Mcrl2Helper('example')
                # if helper.mcrl22lps():
                #     if helper.lps2lts():
                #         helper.graph()

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
            generator = Mcrl2Generator(parser.im, analyzer.symbolTable)
            mcrl2 = generator.toMcrl2()
            with open("rollercoaster/rollercoaster.mcrl2", 'w') as out:
                out.write(mcrl2)
                print 'MCRL2 generated.'
        else:
            exit(1)

                # helper = Mcrl2Helper()
                # lps = helper.mcrl22lps(mcrl2)
                # if lps:
                #     lts = helper.lps2lts(lps)
                #     if lts:
                #         helper.graph(lts)


exit(0)
