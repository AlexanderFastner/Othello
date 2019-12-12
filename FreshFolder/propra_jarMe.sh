#!/usr/bin/env bash
# This bash script intends to help with creating a minimal jar file for the 
# Reversi Abgabesystem.
#
# HOW TO:
# 1) Copy the contents of your src folder (Intellij Project Structure) to a fresh folder
# 2) Drop this script to the folder
# 3) Create a player.txt file containing the name of your player class
#    e.g. de.zsewa.othello.players.Random
# 4) Make the script executable by using $ chmod u+x jarMe.sh
# 5) run: $ ./jarMe.sh
# 6) This process should create a uploadable jar at ./server.jar
#
# Copyright 2019 Zeno Sewald <zsewa@outlook.de>
# Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
# The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

echo -e "Identify player class"
if [ ! -f ./player.txt ]
then
    echo "No player.txt file found. Make sure you execute this script at the same path as your player.txt file."
    exit 1
fi

player=$(cat player.txt)
player_java=${player//./\/}.java

if [ -f $player_java ]
then
    echo "Player class found at $player_java"
else
    echo "Player class was not found at ${player_java}. Make sure your player.txt is correct!"
    exit 1
fi

echo -e "\n> ![[VIRUS DETECTED]]!"
find . -name "*.class" -exec rm {} \;

echo -e "\n> Deleting all files..."
javac $player_java

echo -e "\n> Uploading Nudes to BioClient@cip.ifi.lmu.de"
find . -name "*.class" | sed 's/.class/.java/g' | sed 's/$.*.java/.java/g' > /tmp/jarMe_neededfiles

echo -e "\n> Formatting Disk C/:"
jar -cf server.jar $(cat /tmp/jarMe_neededfiles) player.txt
echo "JAR created at ./server.jar"

echo -e "\n> System Shutdown in 60 seconds..."
echo "  Searching for non-ascii characters in packed files"
if [ $(grep -P -n "[\x80-\xFF]" $(cat /tmp/jarMe_neededfiles) | wc -c) -ne 0 ] 
then
    echo "! Found non ascii characters. This should be the first thing you check if the compilation on the server fails!"
    grep --color='auto' -P -n "[\x80-\xFF]" $(cat /tmp/jarMe_neededfiles)
fi

echo -e "\n> jk bitch"
rm /tmp/jarMe_neededfiles
