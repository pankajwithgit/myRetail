# myRetail API - Pankaj Khurana

myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps.

The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller. 

Your goal is to create a RESTful service that can retrieve product and price details by ID. The URL structure is up to you to define, but try to follow some sort of logical convention.

Build an application that performs the following actions: 

Responds to an HTTP GET request at /products/{id} and delivers product data as JSON (where {id} will be a number. 

Example product IDs: 13860428, 54456119, 13264003, 12954218) 

Example response: {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}

Performs an HTTP GET to retrieve the product name from an external API. (For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail)

Example: https://redsky.target.com/v3/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate 

Reads pricing information from a NoSQL data store and combines it with the product id and name from the HTTP request into a single response.

BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store.

*********************************************************************************************************************************
## __Solution:__

### __Tools & Technologies used:__
1. Spring Boot [As application framework, version 2.4.3]: https://spring.io/quickstart

2. Mongodb [as noSql datastore, version 4.4.4] - https://www.mongodb.com/try/download/community

3. Maven [as build tool] - https://maven.apache.org/

4. Mockito [as Unit testing framework] - https://site.mockito.org/

5. Postman [as API testing tool] - https://www.postman.com/

6. GitHub [as code repo tool] - https://www.github.com

7. Java 1.8 [as programming language]

8. Spring Tools Suite 4 (STS) [as IDE] - https://spring.io/tools

### __How to run the project?:__
1. Install and run MongoDB version 4.4.4 (current as on today) - https://docs.mongodb.com/manual/administration/install-community/

2. Use any IDE (eclipse, IntelliJ, STS etc) and import/clone the git repo - https://github.com/pankajwithgit/myRetail.git

4. Install Java 1.8

5. Install Maven

6. Open terminal, go to project root folder where pom.xml exists.

7. Build the project using 'mvn clean install' command

8. Run the test cases using 'mvn test' command

9. Run the project using 'mvn spring-boot:run' command. Tomcat would be started on 8080 port.

### __Play with APIs:__

Get product Details

    GET http://localhost:8080/api/v1/products/12954218

    Response:

    {
        "id": 12954218,
        "name": "Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz",
        "current_price": {
            "value": 56.13,
            "currency_code": "USD"
        }   
    }

Update Pricing details of a product

    PUT http://localhost:8080/api/v1/products/12954218

    request body:
        {
            "id": 12954218,
            "name": "Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz",
            "current_price": {
                "value": 16.13,
                "currency_code": "USD"
            }
        }

    Response:
    {
        "id": 12954218,
        "name": "Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz",
        "current_price": {
            "value": 16.13,
            "currency_code": "USD"
        }
    }

PS: if product id passed in API calls is not available in datastore or in redsky API response, system will throw ProductNotFoundException.

### __Some Screenshots from Postman:__
GET: With valid product (http://localhost:8080/api/v1/products/13264003)
![Screen Shot 2021-03-02 at 12 14 11 AM (2)](https://user-images.githubusercontent.com/11853379/109601431-ddfabe00-7aec-11eb-8b6c-250401dd2ea3.png)

PUT: With valid product (http://localhost:8080/api/v1/products/12954218)
![Screen Shot 2021-03-02 at 12 01 59 AM (2)](https://user-images.githubusercontent.com/11853379/109600939-0afaa100-7aec-11eb-9324-4aa34a3e584c.png)

GET: With invalid product (http://localhost:8080/api/v1/products/13264002)
![Screen Shot 2021-03-02 at 12 14 54 AM (2)](https://user-images.githubusercontent.com/11853379/109601498-f965c900-7aec-11eb-8cc0-0168095e3234.png)


