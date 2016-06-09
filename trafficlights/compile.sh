#!/bin/bash

rm -rf java/trafficlights/*

echo "Compiling trafficlights to MCRL2 and Java:"
python compile.py

if [ $? -ne 0 ]; then
	echo "Compilation failed."
	exit 1
fi


echo ""
echo ""

echo "Validating example:"

result="$(mcrl22lps --lin-method=stack -q trafficlights.mcrl2)"
if [ ! -z "$result" ]; then
	echo "Success!"
else
	echo "Error while parsing!"
	mcrl22lps --lin-method=stack trafficlights.mcrl2
	exit 1
fi

echo ""
echo ""


echo "Attempting to compile trafficlights to Java:"

cd java

javac trafficlights/*.java trafficlights/models/*.java

if [ $? -ne 0 ]; then
	echo "Compilation failed."
	exit 1
else
	echo "Compiled"
fi

cd ..
