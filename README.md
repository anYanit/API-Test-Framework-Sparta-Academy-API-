# PROJECT OVERVIEW

This project is a robust, BDD-driven test automation framework designed to validate the Sparta Academy REST API. 
The SUT allows a user to view, edit, create and delete Spartan users and also view Courses taken by Spartans.

## 🧱 FRAMEWORK ARCHITECHTURE
The framework is built around:
- Service Object Model (SOM) for clean separation of API logic
- RestAssured for HTTP request/response handling
- Cucumber BDD (optional) for human‑readable test scenarios
- JUnit 5 for test execution
- Mockito for unit testing helper utilities
- Maven for dependency management and build automation

## TECH STACK
- Language: Java 21
- API Client: RestAssured
- Test Runner: JUnit 5 / Cucumber
- Build Tool: Maven

## Prerequisites
- JDK 21 or higher
- Maven 4.0.0
- IntelliJ IDEA (recommended)

## Required dependencies
The following dependencies must be copied and synced in the pom.xml file of the project to run the tests:
- hamcrest 2.2
- slf4j-simple 2.0.7
- serenity-cucumber 3.9.8
- junit-jupiter 6.0.0
- junit-vintage-engine 6.0.0
- rest-assured 5.3.1
- json-simple 1.1.1
- jackson-databind 2.20.1
- jackson-annotations 2.20

```text
<dependencies>
        <dependency>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest</artifactId>
            <version>2.2</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>2.0.7</version>
        </dependency>

        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-cucumber</artifactId>
            <version>3.9.8</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>6.0.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
            <version>6.0.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>5.3.1</version>
        </dependency>

        <dependency>
            <groupId>com.googlecode.json-simple</groupId>
            <artifactId>json-simple</artifactId>
            <version>1.1.1</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.20.1</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.20</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

## How to set-up the test framework
1. Clone the repository
2. The Project SDK must be set under: 
21 Oracle OpenJDK 21.0.10

The Language level must be set under: 
21 - Record patterns, pattern matching for switch

<img width="707" height="86" alt="image" src="https://github.com/user-attachments/assets/3df9057c-1ba7-4b20-b29f-8691b8f36075" />

3. Install the following plugins:
   - Cucumber for Java
   - Gherkin
  
<img width="543" height="133" alt="image" src="https://github.com/user-attachments/assets/0e181913-2101-4fc9-a87a-c0a3f975afe9" />

4. Run all tests using the CucumberRunnerTest file

## 🌲 Project Tree 
```text
API-Test-Framework-Sparta-Academy-API-/
├── .github/
│   └── workflows/
│       └── maven-publish.yml
├── .gitignore
├── .idea/
│   ├── .gitignore
│   ├── encodings.xml
│   ├── misc.xml
│   └── vcs.xml
├── pom.xml
├── README.md
└── src/
    └── test/
        ├── java/
        │   └── com/
        │       └── sparta/
        │           ├── pojos/
        │           │   ├── Course.java
        │           │   ├── LoginRequest.java
        │           │   ├── Spartan.java
        │           │   └── Stream.java
        │           ├── runner/
        │           │   └── CucumberRunnerTest.java
        │           ├── steps/
        │           │   ├── CreateNewSpartanSteps.java
        │           │   ├── DeleteSpartanSteps.java
        │           │   ├── SearchAllCoursesSteps.java
        │           │   ├── SearchAllSpartansSteps.java
        │           │   ├── SearchCourseWithIdSteps.java
        │           │   ├── SearchSpartanWithIdSteps.java
        │           │   ├── UpdateSpartanSteps.java
        │           │   └── UserLoginSteps.java
        │           └── utils/
        │               └── ApiBuilder.java
        └── resources/
            ├── config.properties
            ├── external/
            │   ├── MissingFirstNameSpartan.json
            │   ├── NewSpartan.json
            │   └── UpdatedSpartan.json
            └── features/
                ├── CreateNewSpartan.feature
                ├── DeleteSpartan.feature
                ├── SearchAllCourses.feature
                ├── SearchAllSpartans.feature
                ├── SearchCourseWithId.feature
                ├── SearchSpartanWithId.feature
                ├── UpdateSpartan.feature
                └── UserLogin.feature
```

## 📌 FEATURES
1. Automated tests for 3+ API endpoints
2. Happy & sad path coverage
3. Unit tests for helper logic 
4. Optional Cucumber BDD support
5. Clean, modular framework structure

## END POINTS COVERED
At least three endpoints are fully validated, including:

1. /Auth/login
   - POST request
   - Validate response code
   - Validate response body

2. /api/Courses
   - GET request for all and singular Courses using IDs
   - Validate response code
   - Validate approriate response body retrieved

3. /api/Spartans
   - GET request for all and singular Spartans using IDs
   - POST request for creating new Spartans
   - PUT request for updating Spartans
   - DELETE request for deleting a Spartan
   - Validate response codes
   - Validate appropriate response bodies retrieved
  
## ✔️ TEST COVERAGE
### Happy Path Tests
1. Valid requests return correct status codes
2. Response body contains expected fields
3. Schema validation

### Sad Path Tests
1. Missing fields
2. Invalid data types
3. Incorrect credentials
4. Trying to request without authorization
