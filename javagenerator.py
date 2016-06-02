from pyparsing import *
from collections import deque
from pprint import *

from consoleprogramgenerator import ConsoleProgramGenerator
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

        consoleProgramGenerator = ConsoleProgramGenerator(self.model, self.symbolTable, self.package)
        sortsGenerator = SortsGenerator(self.model, self.symbolTable, self.package + '.models')
        controllerGenerator = ControllerGenerator(self.model, self.symbolTable, self.package + '.models')

        models = {}
        for actor in self.symbolTable['actors']:
            print 'Generating: ' + str(actor)
            modelGenerator = ModelGenerator(self.model, self.symbolTable, self.package + '.models', actor)
            models.update(modelGenerator.toJava())

        consoleProgram = consoleProgramGenerator.toJava()
        sorts = sortsGenerator.toJava()
        controller = controllerGenerator.toJava()

        self.java.update(models)
        self.java.update(consoleProgram)
        self.java.update(sorts)
        self.java.update(controller)

        return self.java

    def emitController(s):
        # TODO: Sorts
        # TODO: Comms
        pass

    def emitModels(s, actors):
        for actor in actors:
            s.emitModel(actor)

    def emitModel(s, actor):
        s.openFile(actor['identifier'])
