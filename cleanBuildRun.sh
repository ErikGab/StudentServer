#!/bin/bash

# REMOVE OLD STUDENTS DATABASE...
rm database/students


# CREATE NEW STUDENTS DATABASE...
sqlite3 database/students < database/createStudentsDB_SQLITE.sql && \
sqlite3 database/students < database/DbFiller/fill_SQLITE.sql


# REMOVE OLD CLASS-FILES...
find | grep "\.class" | xargs rm


# COMPILE SOURCECODE...
CLASSPATH=".:lib:studentservice/WEB-INF/classes/:lib/servlet-api.jar:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar:studentservice/WEB-INF/lib/*"
javac -cp $CLASSPATH  studentservice/WEB-INF/classes/student/servlets/StudentServiceAPI.java && \
javac -cp $CLASSPATH  studentservice/WEB-INF/classes/student/*/*.java


# RUN UNIT TESTS AND START...
TESTCLASSPATH=".:lib:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar:studentservice/WEB-INF/classes:studentservice/WEB-INF/lib/*"
java -Ddebug=false -cp $TESTCLASSPATH org.junit.runner.JUnitCore  student.storage.StorageFactoryTest \
                                                    student.storage.SQLStorageTest \
                                                    student.format.JSONFormatterTest && \
java -Ddebug=true -Dconnection=sqlite -jar lib/jenkins-winstone-0.9.10-jenkins-47.jar --webroot=studentservice
