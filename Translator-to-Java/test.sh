#!/bin/bash
Out=${2-Out}
mkdir $Out
make execute < ${1-"In/in1"} > $Out/Main.java
pr -mt ${1-"In/in1"} $Out/Main.java -W "$(tput cols)"
cd $Out;javac Main.java
if [ $? -eq 0 ] 
then
	echo "Let's run it!"
	java Main
else
	echo "Something went wrong with the compilation"
fi
cd ..
rm -rf $Out