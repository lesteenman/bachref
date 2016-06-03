from pyparsing import *
from collections import deque
from pprint import *
from javafileemitter import JavaFileEmitter

class ConsoleProgramGenerator(JavaFileEmitter):
    className = 'ConsoleProgram'
    imports = ['models.*', 'models.Sorts.*']

    def toJava(s):
        s.openFile('ConsoleProgram')
        s.constructConsoleProgram()
        s.closeFile()

        return super(ConsoleProgramGenerator, s).toJava()

    def constructConsoleProgram(s):
        s.openClass()

        # Main method
        s.indent()
        s.emit('this.controller', '=', 'new', 'Controller', '(', ')', ';')
        s.newline()

        s.unindent()
        s.emit('}')

        # TODO: Main method
        # TODO: Allow'ed methods
        # TODO: In- and output from the console
        # TODO: Print current allowed methods to console
        s.closeClass()
