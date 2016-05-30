from subprocess import Popen, PIPE, STDOUT, CalledProcessError, check_output

class Mcrl2Helper:
    def __init__(self, input_file):
        self.input_file = input_file

    def execute(self, command):
        try:
            out = check_output(command, shell=True).decode()
            # p = Popen(command, stdout=PIPE, stderr=PIPE)
            # out, err = p.communicate()
        except CalledProcessError as e:
            print 'Error while calling subprocess! (' + e.cmd + ')'
            print e.output
            return False

        # if err:
        #     print 'Error: ' + str(err)
        #     return False

        print out
        return True

    def mcrl22lps(self):
        print 'Converting MCRL2 to LPS'
        return self.execute('mcrl22lps example.mcrl2 example.lps')
        # return self.execute(['mcrl22lps', self.input_file + '.mcrl2', self.input_file + '.lps'])

    def lps2lts(self):
        print 'Converting LPS to LTS'
        return self.execute('lps2lts example.lps example.lts')
        # return self.execute(['lps2lts', self.input_file + '.lps', self.input_file + '.lts'])

    def graph(self):
        print 'Showing graph'
        self.execute(['ltsgraph', self.input_file + '.lts'])
