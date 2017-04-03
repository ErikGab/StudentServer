#!/bin/bash

engine=$(shuf -n 1 < motor | head -1)

antal=0
target=$(echo $1 | sed 's/[^0-9]*//g')

if [$1 -eq ""]
  then
    target=1
fi

while [ $antal -lt $target ]
do

  for word in $engine; do
    current=$(shuf -n 1 < $word | head -1)
    #echo $word : $current
    echo -n " $current" 
  done
  echo -n "."

  let antal=antal+1
done
echo ""
#echo engine: $engine

#echo "($RANDOM/32767)*3" | bc -l | printf "%.0f"
