# Iteration 2

## How to run

### Setup your environment

1. Navigate to project dir

2. Copy the file `src/main/resources/options/settings.Example.json` and rename it as
`settings.json` and leave it in the same directory

3. Fill out the settings according to your setup

### Compile and Run

#### In Terminal

Repeat those steps every time if you want to rerun the program after some changes happen:

1. Delete and recreate the output directory 
```bash
rm -fr ./out; mkdir ./out;
```

2. Compile source file
```bash
javac -d ./out -cp .:./lib/ojdbc8.jar:./lib/gson-2.8.5.jar:./src/main/java/ src/main/java/de/hsh/dbs2/ue3/ReadMovieDB.java
```

3. Run compiled code

- To get all movies from DB:
```bash
java -cp ./out:./lib/ojdbc8.jar:./lib/gson-2.8.5.jar:src/ de.hsh.dbs2.ue3.ReadMovieDB
```

- To get a specific movie with an ID (e.g. **2**):
```bash
java -cp ./out:./lib/ojdbc8.jar:./lib/gson-2.8.5.jar:src/ de.hsh.dbs2.ue3.ReadMovieDB 2
``` 

- To get multiple movies with specific IDs (e.g. 2 abc 42 0 -4 12)
```bash
java -cp ./out:./lib/ojdbc8.jar:./lib/gson-2.8.5.jar:src/ de.hsh.dbs2.ue3.ReadMovieDB 2 abc 42 0 12
``` 

#### In IDE

IntelliJ IDEA 

1. Click on "Edit Configurations..." on the right top corner
2. Go to "Program arguments:" field in the popup window
3. Put arbitrary values in this field separated by space to pass them to the java program (`main(String args[])`)
4. Navigate to "src/main/java/de/hsh/dbs2/ue3/ReadMovieDB.java" and press "Shift + F10" to run the program



# ATTENTION

Do not commit and push the 'src/main/resources/options/settings.json' file, as it should contain your database credentials.
This file already added to .gitignore. So, please don't change it and don't commit it on your behalf. 
