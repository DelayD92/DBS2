# Uebung 4

## Authors

## How to run

### Setup your environment
1. In `src/main/resources/META-INF/` locate the file `persistence.example.xml` and rename as `persistence.xml`. 

  - Place the `hibernate.connection.url` and your db credentials properly.
  - Read the comments above the `hibernate.hbm2ddl.auto` property carefully and remove the surrounding
  comments ("<!-- -->") properly to activate the property!

2. Create SSH TUNNEL to the DB server

```bash
ssh -i id_rsa -L 8080:dboracleserv.inform.hs-hannover.de:1521 FH_BENUTZER_NAME@ssh.inform.hs-hannover.de
```

### Init and seed the Database
1. On the first run run this script to drop everything from your schema `src/main/resources/META-INF/sql/drop_schema.sql` 

2. Then run the java app

3. Then seed your db by executing this script: `src/main/resources/META-INF/sql/seed_db_without_id.sql` 

4. Now your tables have enough records and you can make queries.

### Compile and Run

TODO

