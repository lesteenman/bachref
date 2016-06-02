from pyparsing import *
from collections import deque
from pprint import *
from names import Names

newline = '\r\n'
indent = '\t'

class JavaFileEmitter(object):
    debug = False
    current_indent = 0

    imports = []
    extends = None
    className = ''

    def __init__(self, model, symbolTable, package):
        self.model = model
        self.symbolTable = symbolTable
        self.package = package

        self.namegen = Names(symbolTable, 'java')
        self.files = {} # All files
        self.fileName = None # Current file name
        self.lines = []
        self.line = deque([]) # Current line
        self.errors = [];

    # Emits a dictionary of files with their contents as a string
    def toJava(self):
        output = {}
        i = 0
        for file_name, contents in self.files.iteritems():
            file_contents = ''
            for line in contents:
                i += 1
                file_contents += line.popleft() # indentation
                file_contents += ' '.join(line) # rest of the tokens
                file_contents += newline
            output[file_name] = file_contents

        return output

    def emit(*args):
        tokens = list(args)
        self = tokens.pop(0)
        for t in tokens:
            if not isinstance(t, str):
                raise Exception('Attempting to emit a non-string type: ' + str(t))
            self.line.append(t)

    def startBlock(self, name):
        if self.debug:
            self.emit('<start ' + name + '>')

    def endBlock(self, name):
        if self.debug:
            self.emit('<end ' + name + '>')

    # Throw away the {num} last appended lines, not counting the active line.
    def tossline(s, num = 1):
        for i in range(num):
            s.lines.pop()

    # Throw away the {num} last emitted tokens
    def tosstoken(s, num = 1):
        poppedLine = False
        for i in range(num):
            if len(s.line) == 0 and len(s.lines) > 0:
                poppedLine = True
                s.line = s.lines.pop()

            if len(s.line) > 0:
                s.line.pop()

        if poppedLine:
            s.lines.append(s.line)
            s.line = deque([])

    # Set f as the current file that's being appended to.
    def openFile(s, f):
        if s.fileName:
            s.closeFile()

        s.fileName = f
        s.lines = s.files.get(f, [])

    # Close the current file
    def closeFile(s):
        s.newline()
        s.files[s.fileName] = list(s.lines)
        s.lines = []

    # Append the active line to the lines and clear it.
    def newline(s):
        s.line.appendleft(indent * s.current_indent)

        s.lines.append(s.line)
        s.line = deque([])

    # Increase the indentation level starting at the active line
    def indent(s):
        s.current_indent += 1

    # Decrease the indentation level starting at the active line
    def unindent(s):
        s.current_indent -= 1


    # Some common emits

    # Public, returntype, params, func, funcparams
    def emitFunctionBlock(s, public=True, returntype=None, name='', params=[], func=None, funcparams=[]):
        if public:
            s.emit('public')
        else:
            s.emit('protected')

        if returntype:
            s.emit(returntype)

        s.emit(name)

        s.emit('(')
        if len(params) > 0:
            for param in params:
                s.emit(param)
                s.emit(',')
            s.tosstoken()
        s.emit(')', '{')
        s.newline()
        s.indent()

        func(*funcparams)

        s.unindent()
        s.emit('}')
        s.newline()
        s.newline()


    def openClass(s):
        s.emit('package', s.package)
        s.newline()

        s.jEmitImports(s.imports)
        s.newline()
        s.jEmitClassDeclaration(s.className, s.extends)
        s.indent()

    def closeClass(s):
        s.unindent()
        s.emit('}')
        s.newline()

    def jEmitImports(s, imports):
        for imp in imports:
            s.emit('import', imp, ';')
            s.newline()
        s.newline()

    def jEmitClassDeclaration(s, cls, extends = None):
        s.emit('public', 'class', cls)
        if extends:
            s.emit('extends', extends)
        s.emit('{')
        s.newline()

