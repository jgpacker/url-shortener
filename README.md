## URL shortener

1. Install and execute Cassandra (tested with version 2.0)

2. Launch CQL shell and create the necessary Keyspace and Table:

        $ cqlsh
        > CREATE KEYSPACE url WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
        > USE url;
        > CREATE TABLE urltable (id text PRIMARY KEY,  longUrl text);
        > exit

3. Install and then launch SBT:

        $ sbt

4. Compile everything and run all tests:

        > test

5. Start the application:

        > re-start

6. Make the web service requests at http://localhost:8080 (configurable in the file Boot.scala)

7. Stop the application:

        > re-stop
