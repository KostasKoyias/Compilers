#!/bin/bash
make execute < ${1-"input/in1"} > Out/Main.java
sed -i '1d' Out/Main.java
pr -mt ${1-"input/in1"} Out/Main.java -W "$(tput cols)"
cd Out;javac Main.java
if [ $? -eq 0 ] 
then
	echo "Let's run it!"
	java Main;cd ..
else
	echo "Something went wrong with the compilation"
fi
