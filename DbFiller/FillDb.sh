#!/bin/bash

echo "INSERT INTO tblStudent (fldName, fldSurName, fldBirthdate, fldPostAddress, fldStreetAddress)"
echo "VALUES"
student=0
studentTarget=100
while [ $student -lt $studentTarget ]
do
    echo -n "(\""
    echo -n $(shuf -n 1 < name )
    echo -n "\", \""
    echo -n $(shuf -n 1 < surname )
    echo -n "\", \""
    echo -n $(shuf -i 1970-1998 -n 1)
    echo -n "-"
    echo -n $(shuf -n 1 < month )
    echo -n "-"
    echo -n $(shuf -n 1 < day )
    echo -n "\", \""
    echo -n $(shuf -n 1 < postAddress )
    echo -n "\", \""
    echo -n $(shuf -n 1 < streetAddress )
    echo "\"),"
    let student=student+1
done




echo -e "\nINSERT INTO tblStudentPhone (fldStudentId, fldNumber, fldType, fldPrimary)"
echo "VALUES"
student=1
while [ $student -lt $studentTarget ]
do
    seed=$(echo "${RANDOM: -1}")
    if [ $seed -lt 7 ]; then
        #Insert mobile number for student "antal"
        echo "($student, \"07${RANDOM: -2}-${RANDOM: -2}${RANDOM: -2}${RANDOM: -2}\", \"mobile\", null),"
    fi
    seed=$(echo "${RANDOM: -1}")
    if [ $seed -lt 4 ]; then
        #Insert home number for student "antal"
        echo "($student, \"0${RANDOM: -2}-${RANDOM: -2}${RANDOM: -2}${RANDOM: -2}\", \"home\", null),"
    fi
    seed=$(echo "${RANDOM: -1}")
    if [ $seed -lt 2 ]; then
        #Insert work number for student "antal"
        echo "($student, \"0${RANDOM: -2}-${RANDOM: -2}${RANDOM: -2}00\", \"work\", null),"
    fi
    let student=student+1
done




echo -e "\nINSERT INTO tblStudentCourse (fldStudentId, fldCourseId, fldStatus, fldGrade, fldComment)"
echo "VALUES"
student=1
course=1
courseTarget=20
while [ $course -lt $courseTarget ]
do
    studentsInThisCourse=0
    targetForThisCourse=$(shuf -i 10-20 -n1)
    echo " " > tmpList
    while [ $studentsInThisCourse -lt $targetForThisCourse ]
    do
        studentSeed=$(shuf -i 1-$studentTarget -n 1)
        grep -x "$studentSeed" < tmpList > /dev/null
        if [ $? != 0 ]; then
            echo -n "($studentSeed, $course, "
            if [ $course -lt 14 ]; then
                echo -n $(shuf -n 1 < statusGradePast )
            else
                echo -n $(shuf -n 1 < statusGradeCurrent )
            fi
            echo ", null),"
            echo $studentSeed >> tmpList
            let studentsInThisCourse=studentsInThisCourse+1
        fi
    done
    rm tmpList
    let course=course+1
done
