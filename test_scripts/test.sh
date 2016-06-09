#!/bin/bash

cd "$(dirname "$0")"

echo "Compiling traffic lights to MCRL2:"
python im2mcrl2.py

if [ $? -ne 0 ]; then
	echo "Compilation failed."
	exit 1
fi


echo ""
echo ""

echo "Validating traffic lights:"
error=0

cd ../trafficlights_v1

result="$(mcrl22lps --lin-method=stack -q trafficlights.mcrl2)"
if [ ! -z "$result" ]; then
	echo "Success!"
else
	echo "Error while parsing!"
	error=1
fi

cd -
echo ""
echo ""



echo "Validating rollercoaster:"

cd ../rollercoaster

result="$(mcrl22lps --lin-method=stack -q rollercoaster.mcrl2)"
if [ ! -z "$result" ]; then
	echo "Success!"
else
	echo "Error while parsing!"
	error=1
fi


cd -
echo ""
echo ""


if [ $error -eq 1 ]; then
	exit 1
fi



echo "Compiling traffic lights to Java:"
echo "$PWD"

python im2java.py

if [ $? -ne 0 ]; then
	echo "Compilation failed."
	exit 1
fi



echo "Attempting to compile traffic lights:"

cd ../trafficlights_v1/java

javac trafficlights/*.java trafficlights/models/*.java

if [ $? -ne 0 ]; then
	echo "Compilation failed."
	exit 1
else
	echo "Compiled"
fi

cd -


echo "Attempting to compile rollercoaster:"

cd ../rollercoaster/java

javac rollercoaster/*.java rollercoaster/models/*.java

if [ $? -ne 0 ]; then
	echo "Compilation failed."
	exit 1
else
	echo "Compiled"
fi

cd -
