from pyparsing import *
from collections import deque
from pprint import *
from javafileemitter import JavaFileEmitter

class SortsGenerator(JavaFileEmitter):
    className = 'Sorts'

    def toJava(s):
        s.openFile('Sorts')
        s.constructSorts()
        s.closeFile()

        return super(SortsGenerator, s).toJava()

    def constructSorts(s):
        s.openClass()

        for sort, data in s.symbolTable['sorts'].iteritems():
            if data['type'] == 'struct':
                s.emitEnum(sort, data)

        s.closeClass()

    def emitEnum(s, enum, enum_data):
        s.emit('public', 'enum', s.namegen.className(enum), '{')
        s.newline()
        s.indent()
        for item in enum_data['items']:
            s.emit(s.namegen.sort(item), ',')
            s.newline()
        s.tosstoken()
        s.unindent()
        s.emit('}')
        s.newline()
