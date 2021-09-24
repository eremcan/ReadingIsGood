## ReadingIsGood Demo  for Getir
ReadingIsGood is an online books retail firm which operates only on the Internet. Main
target of ReadingIsGood is to deliver books from its one centralized warehouse to their
customers within the same day. That is why stock consistency is the first priority for their
vision operations.      
       
## Requirements for Project     

    • Registering New Customer 
    • Placing a new order
    • Tracking the stock of books
    • List all orders of the customer with Pagination
    • Viewing the order details
    • Query Monthly Statistics
      
## Start PlayGround
 
 In order to build project, first we need to start with mongodb and mongoExpress in root directory.

    > docker-compose -f docker-compose-mongodb.yaml up -d

    > mvn clean
    > mvn package

**Note**:    Mongo Express can be accessed -> localhost:8081

 If It successfully completes, you will see a package in target directory. So we can run below.
    
    > docker build -t eremcan/reading-is-good .
  
 Once build is successfully complete, docker-compose.yaml can now be started in root directory.
     
 While running first time:    

    > docker-compose up -d

 
## USAGE 

 IT can be tested from Postman. Please refer postman collection directory below.
 
    /postman/Getir-ReadingIsGOod.postman_collection.json

  Once u have imported the json, You need to first call auth in order to call other apis.

 - Authentication request body(username/password) for generating JWT endpoint :
   
        {
         "username": "getir",
         "password": "password"
         }
 
 After calling authentication path, Add Authentication header with Bearer prefix.
 
For Ex: Bearer header.payload.signature

----

 Swagger can be accessed from below:

    http://localhost:8080/swagger-ui/index.html
 Localization can be used for this project. Check below;
 
 Add header;

    Accept-Language: tr or en.

## TOOLS
- Java 11
- Spring Boot 2.5.5
    - Spring Security
    - Token Authentication (JWT)
    - Spring MongoDB Starter
    - Spring AOP (For Demo purposes)
    - Swagger3 
    - Lombok
    - Localization (for Only specific errors)
- Docker
- MongoDb





