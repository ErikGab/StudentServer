#!/bin/bash

antal=0
target=100
while [ $antal -lt $target ]
do
    echo -n "returnee.add(new Student("
    echo -n "$antal"
    echo -n ",\""
    echo -n $(shuf -n 1 < name | head -1)
    echo -n "\", \""
    echo -n $(shuf -n 1 < surname | head -1)
    echo  "\"));"
    let antal=antal+1
done
