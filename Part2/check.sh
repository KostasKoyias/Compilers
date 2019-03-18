#!/bin/bash
make execute < ${1-"input/in1"} > Out/Main.java
sed -i '1d' Out/Main.java
echo ${1-"input/in1"};echo "-----------"
cat ${1-"input/in1"}
echo "";echo "";echo "Main.java";echo "---------"
cat Out/Main.java
cd Out;javac Main.java
echo "Let's run it!"
java Main;cd ..
