#!/bin/bash
ant clean && ant jar
FOLDER=$1
if [ $FOLDER ]
	then
	echo "The making process has been successfully finished."
	cp bin/fblibraryinterface.jar $FOLDER
fi
