#!/bin/bash
find | grep "\.class" | xargs rm
javac -cp .:webroot/WEB-INF/classes/:servlet-api.jar:webroot/WEB-INF/lib/*  webroot/WEB-INF/classes/student/servlets/StudentServiceAPI.java && \
javac -cp .:webroot/WEB-INF/classes/:servlet-api.jar:webroot/WEB-INF/lib/*  webroot/WEB-INF/classes/student/*/*.java && \
java -jar jenkins-winstone-0.9.10-jenkins-47.jar --webroot=webroot