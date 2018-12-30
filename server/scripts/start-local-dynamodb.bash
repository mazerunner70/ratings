#1/bin/bash

#mvn clean package
mvn -X exec:java -Dexec.mainClass="com.amazonaws.services.dynamodbv2.DynamoDBLocalFixture" \
      -Dexec.classpathScope="test" \
      -Dsqlite4java.library.path=target/dependencies
