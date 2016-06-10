#!/bin/bash

cd "$(dirname "$0")"

imdiff=$(diff -y --suppress-common-lines ../trafficlights_v1/trafficlights.im ../trafficlights_v2/trafficlights.im | grep '^' | wc -l)
mcrl2diff=$(diff -y --suppress-common-lines ../trafficlights_v1/trafficlights.mcrl2 ../trafficlights_v2/trafficlights.mcrl2 | grep '^' | wc -l)
javadiff=$(diff -r -y --suppress-common-lines --exclude=*.class ../trafficlights_v1/java ../trafficlights_v2/java | grep '^' | wc -l)

echo "Difference in .im files:"
echo $imdiff
echo "Difference in .mcrl2 files:"
echo $mcrl2diff
echo "Difference in .java files:"
echo $javadiff
