
# Twitter kata
Twitter server kata implementing IDD + clean architecture

#### Build

    $ ./gradlew clean build
    
#### Start mongo
You'll need [docker compose](https://docs.docker.com/compose/install/) installed in order to startup a mongodb container locally
    
    $ twitter/docker/docker-compose up

#### Start App
Once build is finished:
    
    $ twitter/delivery/build/libs/java -jar twitter-kata.jar
    
#### Available endpoints

Ping

    $ curl localhost:8080/api/v1
    

Register user       
    
    curl -X POST localhost:8080/api/v1/users -H "Content-Type: application/json" -d '{"realName":"Joey Ramone", "nickname": "@joey", "follows":[]}'

Update user         
     
     curl -X PUT localhost:8080/api/v1/users/@joey -H "Content-Type: application/json" -d '{"realName":"Joey", "nickname": "@joey", "follows":[]}'

     
Follow user

    curl -X POST localhost:8080/api/v1/users/@joey/follows -H "Content-Type: application/json" -d '{"nickname" : "@johnny" }'

List followers
    
    $ curl localhost:8080/api/v1/users/@joey        
