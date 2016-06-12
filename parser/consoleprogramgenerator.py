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
        s.emitFunctionBlock(name=s.className, func=s.constructor, params=['boolean jtorx'])
        s.emitFunctionBlock(returntype='void', name='handleInput', func=s.handleInput, params=['String input'])
        s.emitFunctionBlock(returntype='void', name='printStates', func=s.printStates)
        s.emitFunctionBlock(returntype='void', name='output', func=s.output, params=['String output'])
        s.emitFunctionBlock(returntype='void', name='outputError', func=s.outputError, params=['String output'])

        s.closeClass()

    def emitProperties(s):
        s.emit('Controller', 'controller', ';')
        s.emit('boolean', 'jtorx', ';')
        s.newline()

    def main(s):
        s.emitLine('boolean jtorx = args.length > 0 && args[0].equals("jtorx");');
        s.emitLine('new ConsoleProgram(jtorx);')

    def constructor(s):
        s.emitLine('this.jtorx = jtorx;')
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
        s.emitLine('this.outputError("Error while reading from console: " + e.getMessage());')

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
                s.emitInputHandler(action_id, action, instance, actor)

        s.emit('default:')
        s.newline()
        s.indent()
        s.emit('this.outputError("Unrecognized input: " + method);')
        s.newline()
        s.emit('break;')
        s.newline()
        s.unindent()

        s.unindent()
        s.emit('}')
        s.newline()

    def emitInputHandler(s, action_id, action, instance, actor_id):
        function_label = 'perform_' + instance + '_' + action['identifier']
        function_name = s.namegen.camelcase(function_label, True)
        listen_name = instance + '_' + action['identifier']
        params = action['parameters']

        s.emit('case', '"' + listen_name + '"', ':')
        s.newline()
        
        # If the action has guards, the MCRL2 name is prefixed with perform_
        guards = s.symbolTable['guards'].get(actor_id, {}).get(instance, {}).get(action['identifier'], {})
        if len(guards):
            s.emit('case', '"' + function_label + '"', ':')
            s.newline()

        s.indent()

        if len(params):
            s.emit('if', '(', 'args.length', '!=', str(len(params)), ')', '{')
            s.newline()
            s.indent()

            s.emit('this.outputError("Invalid number of parameters, expected:', str(len(params)), '");')
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
        s.emit('this.output("executed");')
        s.newline()
        s.unindent()
        s.emit('else')
        s.newline()
        s.indent()
        s.emit('this.outputError("could not execute");')
        s.newline()
        s.unindent()

        s.emit('break', ';')
        s.newline()
        s.unindent()
        s.newline()

    def printStates(s):
        s.emitLine('this.output("Current states:");')
        s.emitLine('HashMap<String, HashMap<String, String>> states = this.controller.getStates();')

        s.emitLine('Iterator actor_it = states.entrySet().iterator();')
        s.emitLine('while (actor_it.hasNext()) {')
        s.indent()
        s.emitLine('Map.Entry actor_pair = (Map.Entry)actor_it.next();')
        s.emitLine('String actor = (String)actor_pair.getKey();')
        s.emitLine('this.output("Actor: " + actor);')
        s.emitLine('Iterator state_it = ((HashMap)actor_pair.getValue()).entrySet().iterator();')
        s.emitLine('while (state_it.hasNext()) {')
        s.indent()
        s.emitLine('Map.Entry state_pair = (Map.Entry)state_it.next();')
        s.emitLine('this.output("\t" + (String)state_pair.getKey() + ":\t" + (String)state_pair.getValue());')
        s.unindent()
        s.emitLine('}')
        s.unindent()
        s.emitLine('}')
        s.emitLine('this.output("\\r\\n\\r\\n");')

    def output(s):
        s.emitLine('if (!this.jtorx) {')
        s.indent()
        s.emitLine('System.out.println(output);')
        s.unindent()
        s.emitLine('}')

    def outputError(s):
        s.emitLine('System.out.println(output);')
