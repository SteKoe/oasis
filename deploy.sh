#!/bin/bash
SSH_SERVER=tomcat@server.stekoe.de
DEST_DIR=/home/tomcat/tomcat/app_oasis
TOMCAT_HOME=/home/tomcat/tomcat/

# The hard way...
echo "Killing Tomcat on destination..."
ssh $SSH_SERVER killall java -9

# Copy most current target
echo "Transferring new file..."
SC=`dirname $0`
find $SC -name 'idss-webui*.war' -exec scp '{}' $SSH_SERVER:$DEST_DIR \;

# Delete old dist directory
echo "Delete old files..."
ssh $SSH_SERVER rm -rf $DEST_DIR/dist

# Unzip it
echo "Unzip war..."
ssh $SSH_SERVER find $DEST_DIR -name 'idss-*.war' -exec "unzip -o -qq {} -d $DEST_DIR/dist \;"

# Start tomcat
echo "Restarting tomcat..."
ssh tomcat@server.stekoe.de $TOMCAT_HOME/bin/catalina.sh start