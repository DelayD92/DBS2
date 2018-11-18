#!/bin/bash

cd ../../../../..

#1. Delete and recreate the output directory
rm -fr ./out && mkdir ./out

# 2. Compile source file
javac -d ./out -cp .;./lib/ojdbc8.jar;./lib/gson-2.8.5.jar;./src/main/java;./src/main/resources/ src/main/java/de/hsh/dbs2/imdb/Starter.java

# 3. Run compiled code
java -cp .;./out;./lib/ojdbc8.jar;./lib/gson-2.8.5.jar;./src/main/resources/ de.hsh.dbs2.imdb.Starter
