# bookstore.api
sample rest level 3 (HATEOS) api for a bookstore using spring


# Resources
- [REST With Spring] (http://bit.ly/restwithspring)


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
