# Uebung 3

## Authors
- Kenan Güler
- Daniel Diele
- Julian Sender
- Yannick Hollmann

## How to run

### Setup your environment

1. Navigate to project dir

2. Copy the file `src/main/resources/options/settings.Example.json` and rename it as
`settings.json` and leave it in the same directory

3. Fill out the settings according to your setup

### Create SSH TUNNEL to the DB server
```bash
ssh -i id_rsa -L 8080:dboracleserv.inform.hs-hannover.de:1521 FH_BENUTZER_NAME@ssh.inform.hs-hannover.de
```

### Init and seed the Database
Connect to database and execute the `dbInit.sql` file init and seed database
You can find it in the following path: `de/hsh/dbs2/ue3/iter1/dbInit.sql`


### Compile and Run

#### In Terminal

###### Linux & macOS
1. Delete and recreate the output directory 
```bash
rm -fr ./out; mkdir ./out;
```

2. Compile source file
```bash
javac -d ./out -cp .;./lib/ojdbc8.jar;./lib/gson-2.8.5.jar;./src/main/java src/main/java/de/hsh/dbs2/imdb/Starter.java
```

3. Run compiled code
```bash
java -classpath .;./out;./lib/ojdbc8.jar;./lib/gson-2.8.5.jar;./src/main/resources/ de.hsh.dbs2.imdb.Starter
```

###### Windows
1. Delete and recreate the output directory 
```batch
rmdir /S /Q .\out 2>nul & mkdir .\out
```

2. Compile source file
```batch
javac -d .\out -cp .;.\lib\ojdbc8.jar;.\lib\gson-2.8.5.jar;.\src\main\java src\main\java\de\hsh\dbs2\imdb\Starter.java
```

3. Run compiled code
```batch
java -cp .;.\out;.\lib\ojdbc8.jar;.\lib\gson-2.8.5.jar;.\src\main\resources\ de.hsh.dbs2.imdb.Starter
```

***

#### ATTENTION

Do not commit and push the 'src/main/resources/options/settings.json' file, as it should contain your database credentials.
This file already added to .gitignore. So, please don't change it and don't commit it on your behalf. 
