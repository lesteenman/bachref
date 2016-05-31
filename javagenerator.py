from pyparsing import *
from collections import deque
from pprint import *

newline = '\r\n'
indent = '\t'

# TODO: Translate to Java
class JavaGenerator:
    debug = False
    current_indent = 0

    def __init__(self, model, symbolTable):
        self.model = model
        self.symbolTable = symbolTable
        self.files = {} # All files
        self.java = [] # Current file
        self.line = deque([]) # Current line
        self.errors = [];

    def toJava(self):
        output = ''
        i = 0
        for line in self.java:
            i += 1
            output += line.popleft() # indentation
            output += ' '.join(line) # rest of the tokens
            output += newline

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
            s.java.pop()

    # Throw away the {num} last emitted tokens in the active line.
    def tosstoken(s, num = 1):
        for i in range(num):
            if len(s.line) > 0:
                s.line.pop()

    # Set f as the current file that's being appended to.
    def openFile(s, f):
        s.newline()
        s.files[s.currentfile] = s.java
        s.java = s.files.get(f, [])

    # Append the active line to the java and clear it.
    def newline(s):
        s.line.appendleft(indent * s.current_indent)

        s.java.append(s.line)
        s.line = deque([])

    # Increase the indentation level starting at the active line
    def indent(s):
        s.current_indent += 1

    # Decrease the indentation level starting at the active line
    def unindent(s):
        s.current_indent -= 1
