# bookstore.api
sample rest level 3 (HATEOS) api for a bookstore using spring


# Quick Start
```
git clone https://github.com/sibyg/bookstore.api
cd bookstore.api
mvn clean install
```


# Persistence
By default, the project uses [the H2 in-memory DB](http://www.h2database.com/html/main.html) and - `persistence-h2.properties`.

# Technology Stack
The project uses the following technologies: <br/>
- **web/REST**: [Spring](http://www.springsource.org/) 4.2.x <br/>
- **marshalling**: [Jackson](https://github.com/FasterXML/jackson-databind) 2.x (for JSON) and the new  [Jackson XML extension](https://github.com/FasterXML/jackson-dataformat-xml) (for XML) <br/>
- **persistence**: [Spring Data JPA](http://www.springsource.org/spring-data/jpa) <br/>
- **persistence providers**: H2
- **testing**: [junit](http://www.junit.org/), [hamcrest](http://code.google.com/p/hamcrest/), [mockito](http://code.google.com/p/mockito/), [spring-boot](https://projects.spring.io/spring-boot/) <br/>



# IntelliJ
- see the [IntelliJ for Spring page](https://www.jetbrains.com/help/idea/spring.html)

# Running Locally,
Swagger UI
http://localhost:8080/bookstore/api/swagger-ui.html

To add a bookstore `enfield`:
http://localhost:8080/bookstore/api/enfield/register

To add a book to `enfield` bookstore:
HTTP POST to http://localhost:8080/bookstore/api/enfield/books
with JSON body
{
        "id": 1,
        "author": "author/1/enfield",
        "title": "A description"
}

To read all books from `enfield` bookstore:
 http://localhost:8080/bookstore/api/swagger-ui.html

 # Test
 Integration tests:
 `**IT`
 Repository tests are
 `**RepositoryTest`
 Unit tests:
`**Test`