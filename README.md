# Uebung 3 - Iteration 2

## How to run

### Setup your environment

1. Navigate to project dir

2. Copy the file `src/main/resources/options/settings.Example.json` and rename it as
`settings.json` and leave it in the same directory

3. Fill out the settings according to your setup

### Compile and Run

#### In Terminal

Repeat those steps every time if you want to rerun the program after some changes happen:

###### Linux & macOS
1. Delete and recreate the output directory 

```bash
rm -fr ./out; mkdir ./out;
```

2. Compile source file
```bash
javac -d ./out -cp .:./lib/ojdbc8.jar:./lib/gson-2.8.5.jar:./src/main/java/ src/main/java/de/hsh/dbs2/ue3/iter2/ReadMovieDB.java
```

3. Run compiled code

- To get all movies from DB:
```bash
java -cp ./out:./lib/ojdbc8.jar:./lib/gson-2.8.5.jar:src/ de.hsh.dbs2.ue3.iter2.ReadMovieDB
```

- To get a specific movie with an ID (e.g. **2**):
```bash
java -cp ./out:./lib/ojdbc8.jar:./lib/gson-2.8.5.jar:src/ de.hsh.dbs2.ue3.iter2.ReadMovieDB 2
``` 

- To get multiple movies with specific IDs (e.g. 2 abc 42 0 -4 12)
```bash
java -cp ./out:./lib/ojdbc8.jar:./lib/gson-2.8.5.jar:src/ de.hsh.dbs2.ue3.iter2.ReadMovieDB 2 abc 42 0 12
``` 

***

###### Windows

###### Windows: via batch script

1. You can simply use this batch script here to compile and run teh code:
```bash
src/main/resources/scripts/windows/iter2_compile_and_run.EXAMPLE.bat
````

2. Copy and rename it, also adjust the last line, as you need. For example:
- `iter2_compile_and_run.list-all-movies.bat`
- `iter2_compile_and_run.zeigMovieMitId5An.bat`

3. Then either run it directly by double clicking on it, or within the IDE (e.g. Intellij) navigate to the file you want to run and press `CTRL + SHIFT + F10`


###### Windows: via Command Line Terminal (either power shell or cmd)

1. Delete and recreate the output directory 
```bash
rmdir /S /Q .\out && mkdir .\out
```

2. Compile source file
```bash
javac -d .\out -cp .;.\lib\ojdbc8.jar;.\lib\gson-2.8.5.jar;.\src\main\java\ src\main\java\de\hsh\dbs2\ue3\iter2\ReadMovieDB.java
```

3. Run compiled code

- To get all movies from DB;
```bash
java -cp .\out;.\lib\ojdbc8.jar;.\lib\gson-2.8.5.jar;src\main\resources de.hsh.dbs2.ue3.iter2.ReadMovieDB
```

- To get a specific movie with an ID (e.g. **2**);
```bash
java -cp .\out;.\lib\ojdbc8.jar;.\lib\gson-2.8.5.jar;src\main\resources de.hsh.dbs2.ue3.iter2.ReadMovieDB 2
``` 

- To get multiple movies with specific IDs (e.g. 2 abc 42 0 -4 12)
```bash
java -cp .\out;.\lib\ojdbc8.jar;.\lib\gson-2.8.5.jar;src\main\resources de.hsh.dbs2.ue3.iter2.ReadMovieDB 2 abc 42 0 12
```

***

#### In IDE

IntelliJ IDEA 

1. Click on "Edit Configurations..." on the right top corner
2. Go to "Program arguments:" field in the popup window
3. Put arbitrary values in this field separated by space to pass them to the java program (`main(String args[])`)
4. Navigate to "src/main/java/de/hsh/dbs2/ue3/ReadMovieDB.java" and press "Shift + F10" to run the program


***

#### ATTENTION

Do not commit and push the 'src/main/resources/options/settings.json' file, as it should contain your database credentials.
This file already added to .gitignore. So, please don't change it and don't commit it on your behalf. 
