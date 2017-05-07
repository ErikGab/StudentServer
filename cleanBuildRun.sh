#!/bin/bash

# REMOVE OLD STUDENTS DATABASE...
rm students


# CREATE NEW STUDENTS DATABASE...
sqlite3 students < createStudentsDB.sql && \
sqlite3 students < DbFiller/fill.sql


# REMOVE OLD CLASS-FILES...
find | grep "\.class" | xargs rm


# COMPILE SOURCECODE...
CLASSPATH=".:webroot/WEB-INF/classes/:servlet-api.jar:junit-4.12.jar:hamcrest-core-1.3.jar:webroot/WEB-INF/lib/*"
javac -cp $CLASSPATH  webroot/WEB-INF/classes/student/servlets/StudentServiceAPI.java && \
javac -cp $CLASSPATH  webroot/WEB-INF/classes/student/*/*.java


# RUN UNIT TESTS AND START...
TESTCLASSPATH=".:junit-4.12.jar:hamcrest-core-1.3.jar:webroot/WEB-INF/classes:webroot/WEB-INF/lib/*"
java -Ddebug=false -cp $TESTCLASSPATH org.junit.runner.JUnitCore  student.storage.StorageFactoryTest \
                                                    student.storage.SQLStorageTest \
                                                    student.format.JSONFormatterTest && \
java -Ddebug=true -Dconnection=sqlite -jar jenkins-winstone-0.9.10-jenkins-47.jar --webroot=webroot
