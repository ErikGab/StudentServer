#!/bin/bash

echo "INSERT INTO tblStudent (fldName, fldSurName, fldBirthdate, fldPostAddress, fldStreetAdress)"
echo "VALUES"
antal=0
target=100
while [ $antal -lt $target ]
do
    echo -n "("
    echo -n $(shuf -n 1 < name | head -1)
    echo -n ", "
    echo -n $(shuf -n 1 < surname | head -1)
    echo -n ", "
    echo -n $(shuf -i 1970-1998 -n 1)
    echo -n "-"
    echo -n $(shuf -i 01-12 -n 1)
    echo -n "-"
    echo -n $(shuf -i 01-30 -n 1)
    echo -n ", "
    echo -n $(shuf -n 1 < postAddress | head -1)
    echo -n ", "
    echo -n $(shuf -n 1 < streetAddress | head -1)
    echo "),"
    let antal=antal+1
done

echo "INSERT INTO tblStudentPhone (fldStudentId, fldNumber, fldType, fldPrimary)"
echo "VALUES"
antal=0
target=100
while [ $antal -lt $target ]
do
    echo -n "("
    echo -n $antal
    let antal=antal+1
done
