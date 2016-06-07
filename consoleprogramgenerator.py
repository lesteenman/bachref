from pyparsing import *
from collections import deque
from pprint import *
from javafileemitter import JavaFileEmitter

class ConsoleProgramGenerator(JavaFileEmitter):
    className = 'ConsoleProgram'

    def toJava(s):
        s.imports = [
            s.root_package + '.models.*',
            s.root_package + '.models.Sorts.*',
            'java.io.*',
            'java.util.HashMap',
            'java.util.Map',
            'java.util.Iterator'
        ]
        s.openFile('ConsoleProgram')
        s.constructConsoleProgram()
        s.closeFile()

        return super(ConsoleProgramGenerator, s).toJava()

    def constructConsoleProgram(s):
        s.openClass()

        s.emitProperties()
        s.newline()
        s.emitFunctionBlock(static=True, returntype='void', name='main', func=s.main, params=['String[] args'])
        s.emitFunctionBlock(name=s.className, func=s.constructor)
        s.emitFunctionBlock(returntype='void', name='handleInput', func=s.handleInput, params=['String input'])
        s.emitFunctionBlock(returntype='void', name='printStates', func=s.printStates)

        s.closeClass()

    def emitProperties(s):
        s.emit('Controller', 'controller', ';')
        s.newline()

    def main(s):
        s.emit('new ConsoleProgram();')
        s.newline()

    def constructor(s):
        s.emitLine('this.controller = new Controller() ;')
        s.emitLine('InputStreamReader inputStream = new InputStreamReader(System.in) ;')
        s.emitLine('BufferedReader inputReader = new BufferedReader(inputStream);')
        s.emitLine('this.printStates();')
        s.emitLine('try {')
        s.indent()

        s.emitLine('while (true) {')
        s.indent()

        s.emitLine('if (inputReader.ready()) {')
        s.indent()

        s.emitLine('String input = inputReader.readLine() ;')
        s.emitLine('this.handleInput(input);')
        s.emitLine('this.printStates();')

        s.unindent()
        s.emitLine('}')

        s.unindent()
        s.emitLine('}')

        s.unindent()
        s.emitLine('} catch (IOException e) {')
        
        s.indent()
        s.emitLine('System.out.println("Error while reading from console: " + e.getMessage());')

        s.unindent()
        s.emitLine('}')

    def handleInput(s):
        s.emit('String[] split = input.split(" ");')
        s.newline()
        s.emit('String', 'method', '=', 'split[0];')
        s.newline()
        s.emit('boolean', 'result', '=', 'true;')
        s.newline()
        s.emit('String[]', 'args', '=', 'new', 'String[', 'split.length', '-', '1', '];')
        s.newline()
        s.emit('System.arraycopy', '(', 'split, 1, args, 0, args.length);')
        s.newline()
        s.newline()

        s.emit('switch', '(', 'method', ')', '{')
        s.newline()

        s.indent()
        
        for action_id, action in s.symbolTable['actions'].iteritems():
            actor = action['actor']
            instances = s.symbolTable['actors'][actor]['instances'].keys()
            for instance in instances:
                s.emitInputHandler(action_id, action, instance)

        s.emit('default:')
        s.newline()
        s.indent()
        s.emit('System.out.println("Unrecognized input: " + method);')
        s.newline()
        s.emit('break;')
        s.newline()
        s.unindent()

        s.unindent()
        s.emit('}')
        s.newline()

    def emitInputHandler(s, action_id, action, instance):
        function_name = s.namegen.camelcase('perform_' + instance + '_' + action['identifier'], True)
        listen_name = instance + '_' + action['identifier']
        params = action['parameters']

        s.emit('case', '"' + listen_name + '"', ':')
        s.newline()
        s.indent()

        if len(params):
            s.emit('if', '(', 'args.length', '!=', str(len(params)), ')', '{')
            s.newline()
            s.indent()

            s.emit('System.out.println("Invalid number of parameters, expected:', str(len(params)), '");')
            s.newline()
            s.emit('return;')
            s.newline()

            s.unindent()
            s.emit('}')
            s.newline()

        s.emit('result', '=', 'controller.' + function_name, '(')
        
        if len(params):
            i = 0
            for p in action['parameters']:
                sort = s.symbolTable['sorts'][p]
                # s.emit(str(sort), '||')
                if sort['type'] == 'struct':
                    s.emitProperty(p, 'valueOf')
                    s.emit('(', 'args', '[', str(i), ']', ')', ',')
                else:
                    s.emit('args', '[', str(i), ']', ',')
                i += 1
            s.tosstoken()

        s.emit(')', ';')
        s.newline()
        s.emit('if (result)')
        s.newline()
        s.indent()
        s.emit('System.out.println("executed");')
        s.newline()
        s.unindent()
        s.emit('else')
        s.newline()
        s.indent()
        s.emit('System.out.println("could not execute");')
        s.newline()
        s.unindent()

        s.emit('break', ';')
        s.newline()
        s.unindent()
        s.newline()

    def printStates(s):
        s.emitLine('System.out.println("Current states:");')
        s.emitLine('HashMap<String, HashMap<String, String>> states = this.controller.getStates();')

        s.emitLine('Iterator actor_it = states.entrySet().iterator();')
        s.emitLine('while (actor_it.hasNext()) {')
        s.indent()
        s.emitLine('Map.Entry actor_pair = (Map.Entry)actor_it.next();')
        s.emitLine('String actor = (String)actor_pair.getKey();')
        s.emitLine('System.out.println("Actor: " + actor);')
        s.emitLine('Iterator state_it = ((HashMap)actor_pair.getValue()).entrySet().iterator();')
        s.emitLine('while (state_it.hasNext()) {')
        s.indent()
        s.emitLine('Map.Entry state_pair = (Map.Entry)state_it.next();')
        s.emitLine('System.out.println("\t" + (String)state_pair.getKey() + ":\t" + (String)state_pair.getValue());')
        s.unindent()
        s.emitLine('}')
        s.unindent()
        s.emitLine('}')
        s.emitLine('System.out.println("\\r\\n\\r\\n");')
