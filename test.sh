#!/bin/bash

echo "Compiling examples:"
./imconvert.py


echo "Validating example:"

pushd mcrl2
result="$(mcrl22lps example.mcrl2)"
if [ ! -z "$result" ]; then

	echo "Success!"
	# echo "Showing graph:"

# 	mcrl22lps example.mcrl2 example.lps
# 	lps2lts example.lps example.lts
# 	ltsgraph example.lts
else
	echo "Error while parsing!"
fi


echo ""
echo ""
echo ""
echo ""



echo "Validating rollercoaster example:"

result="$(mcrl22lps example_rollercoaster.mcrl2)"
if [ ! -z "$result" ]; then

	echo "Success!"

	echo "Showing graph"

	mcrl22lps example_rollercoaster.mcrl2 example_rollercoaster.lps
	lps2lts example_rollercoaster.lps example_rollercoaster.lts
	ltsgraph example_rollercoaster.lts

else
	echo "Error while parsing!"
fi
