from pyparsing import *
from collections import deque
from pprint import *
import os

from consoleprogramgenerator import ConsoleProgramGenerator
from controllergenerator import ControllerGenerator
from modelgenerator import ModelGenerator
from sortsgenerator import SortsGenerator

newline = ';\r\n'
indent = '\t'

# TODO: Translate to Java
class JavaGenerator:

    def __init__(self, model, symbolTable, package):
        self.model = model
        self.symbolTable = symbolTable
        self.package = package

    # Emits a dictionary of files with their contents as a string
    def toJava(self):
        self.java = {}

        consoleProgramGenerator = ConsoleProgramGenerator(self.model, self.symbolTable, self.package, self.package)
        sortsGenerator = SortsGenerator(self.model, self.symbolTable, self.package + '.models', self.package)
        controllerGenerator = ControllerGenerator(self.model, self.symbolTable, self.package + '.models', self.package)

        models = {}
        for actor in self.symbolTable['actors']:
            print 'Generating: ' + str(actor)
            modelGenerator = ModelGenerator(self.model, self.symbolTable, self.package + '.models', self.package, actor)
            models.update(modelGenerator.toJava())

        consoleProgram = consoleProgramGenerator.toJava()
        sorts = sortsGenerator.toJava()
        controller = controllerGenerator.toJava()

        self.java.update(models)
        self.java.update(consoleProgram)
        self.java.update(sorts)
        self.java.update(controller)

        return self.java

    def writeFiles(self, path):
        if path[-1] != '/':
            path += '/'

        for f, data in self.java.iteritems():
            fp = path + f.replace('.', '/') + '.java'
            if not os.path.exists(os.path.dirname(fp)):
                try:
                    os.makedirs(os.path.dirname(fp))
                except OSError as exc:
                    if exc.errno != errno.EEXIST:
                        raise
            with open(fp, 'w') as out:
                out.write(data)
                print 'Wrote ' + f + ' to ' + fp
