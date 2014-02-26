#!/bin/bash
SSH_SERVER=tomcat@server.stekoe.de
TOMCAT_HOME=/home/tomcat/tomcat/

# The hard way...
echo "Killing Tomcat on destination..."
ssh $SSH_SERVER killall java -9

# Start tomcat
echo "Restarting tomcat..."
ssh tomcat@server.stekoe.de $TOMCAT_HOME/bin/catalina.sh start