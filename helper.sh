#!/bin/bash

find_models() {
    models=
    i=0
    for model in *.mcrl2; do
        models[i]=$model
        ((i++))
    done
}

find_test_files() {
    tests=
    i=0
    for test in *.mcf; do
        tests[i]=$test
        ((i++))
    done
}

# Assumes that a valid .tmp.pbes file is already in place.
run_test() {
    lps2pbes .tmp.lps -f $1 .tmp1.pbes
    pbesconstelm .tmp1.pbes .tmp2.pbes
    pbesparelm .tmp2.pbes .tmp3.pbes

    namelength=${#1}
    N=`expr 50 - $namelength`
    if [ "$N" -le "0" ]; then
        N=5
    fi
    base='..................................................'
    echo -n "Test '$1'${base:0:$N}"

    result="$(pbes2bool -s3 .tmp3.pbes)"
    echo "$result"

    rm .tmp1.pbes 2> /dev/null
    rm .tmp2.pbes 2> /dev/null
    rm .tmp3.pbes 2> /dev/null
    last_test="$1"
}

counter_test() {
    lps2pbes .tmp.lps -f $1 .tmp.pbes

    namelength=${#1}
    N=`expr 50 - $namelength`
    if [ "$N" -le "0" ]; then
        N=5
    fi
    base='..................................................'
    echo -n "Countering Test '$1'${base:0:$N}"

    result="$(pbes2bool -s2 -c .tmp.pbes)"
    echo "$result"

    rm .tmp.pbes 2> /dev/null
}

find_models
find_test_files

echo ""
echo ""
echo "Files found:"
(IFS=$' '; echo "   Available models: ${model[*]}")
(IFS=$' '; echo "   Available tests: ${tests[*]}")
echo ""
echo ""
echo "Actions:"
echo "   parse [model]"
echo "   aut [model]"
echo "   val [model]"
echo "   sim [model]"
echo "   test [model] [test]"
echo "   counter [model] [test]"
echo "   testall [model]"
echo "   view [model]"
echo "   graph [model]"
echo "   deadlock [model]"
echo "   deadlocktrace [model]"
echo ""
echo ""

while [ 1 ]
do
    IFS=' ' read -ea action

    command="${action[0]}"
    model="${action[1]}"
    if [ -z $model ]; then
        model="${models[0]}"
    fi

    if [ $command = "exit" ]; then
        exit
    elif [ -z $model ]; then
        echo "No model given and no model found in directory."
    elif [ ! -f $model ]; then
        echo "<error> File not found: $model";
    else
        # Parse: Test if valid file
        if [ $command = "parse" ]; then
            result="$(mcrl22lps $model)"
            if [ ! -z "$result" ]; then
                echo "Succesfully parsed!"
            fi
        elif [ $command = "aut" ]; then # Aut: Generate AUT file to use with jtorx
            mcrl22lps $model .tmp.lps &&
            lps2lts .tmp.lps .tmp.lts &&
            ltsconvert .tmp.lts output.aut
            rm .tmp.lps 2> /dev/null
            rm .tmp.lts 2> /dev/null
            echo "AUT file has been generated!"
        elif [ $command = "val" ]; then # Val: Run mcrl2xi
            mcrl2xi $model
        elif [ $command = "sim" ]; then # Sim: Simulate the model
            mcrl22lps $model .tmp.lps
            lpsxsim .tmp.lps
            rm .tmp.lps 2> /dev/null
        elif [ $command = "counter" ]; then
            test="${action[2]}"
            # One argument given, should be only test
            if [ -z "$test" ]; then
                model="${models[0]}"
                test="${action[1]}"
            fi
            # No arguments given, so counter last run test
            if [ -z "$test" ]; then
                test="$last_test"
            fi

            mcrl22lps $model .tmp.lps
            counter_test $test
            rm .tmp.lps 2> /dev/null
            echo "Done"
        elif [ $command = "test" ]; then
            test="${action[2]}"
            # One argument given, should be only test
            if [ -z "$test" ]; then
                model="${models[0]}"
                test="${action[1]}"
            fi
            # No arguments given, so re-run last run test
            if [ -z "$test" ]; then
                test="$last_test"
            fi

            mcrl22lps $model .tmp.lps
            run_test $test
            rm .tmp.lps 2> /dev/null
            echo "Done"
        elif [ $command = "testall" ]; then
            find_test_files
            echo "Generating LPS..."
            mcrl22lps $model .tmp.lps
            for test in "${tests[@]}"; do
                run_test $test
            done
            rm .tmp.lps 2> /dev/null
            echo "Done"
        elif [ $command = "view" ]; then
            mcrl22lps $model .tmp.lps &&
            lps2lts .tmp.lps .tmp.lts &&
            ltsview .tmp.lts
            rm .tmp.lps 2> /dev/null
            rm .tmp.lts 2> /dev/null
        elif [ $command = "graph" ]; then
            mcrl22lps $model .tmp.lps &&
            lps2lts .tmp.lps .tmp.lts &&
            ltsgraph .tmp.lts
            rm .tmp.lps 2> /dev/null
            rm .tmp.lts 2> /dev/null
        elif [ $command = "deadlock" ]; then
            mcrl22lps $model .tmp.lps
            lps2lts -D .tmp.lps
            rm .tmp.lps 2> /dev/null
        elif [ $command = "deadlocktrace" ]; then
            mcrl22lps $model .tmp.lps &&
            lps2lts --deadlock --trace .tmp.lps
            rm .tmp.lps

            i=1
            for trace in .tmp.lps*.trc; do
                if [ ! $trace = ".tmp.lps*.trc" ]; then
                    echo ""
                    echo "Deadlock $i:"
                    tracepp $trace
                    ((i++))
                    echo ""
                fi
            done
            if [[ "$i" -eq 1 ]]; then
                echo "No deadlocks found."
            else
                echo "Done."
                rm .tmp.lps*.trc 2> /dev/null
            fi
        else
            echo "Unknown command '$command'"
        fi

        # if [ $command = "trace" ]; then
        #   generate traces with lps2lts --action=$action -t$num .tmp.lps
        #   output each trace one by one
        # fi
    fi
    echo ""
done
