## Description of framework
__This framework use the next technologies: Java 11,
Maven, TestNG, Allure report, Jackson/JSON, HTTP client, Log4j. Details in POM.xml__

__Run tests with maven:__
```
mvn clean test
```
__To generate a report use:__
```
allure serve ./allure-results
```
__If you need to run this tests in local machine, you can use class ApiOmdbTest.class__

### Project structure:
__In main/java we have HttpClient.class. It's our http client, which contains some methods like searchGetRequest, getByIdGetRequest, getByTitleGetRequest. All of this methods we are using in tests.__

__In main/java we also have POJO package. There are JSON serialization to java objects. We can validate schema/values for all get requests in our http client.__

__In main/resources we have simple log4j.properties file and testData.properties file.__

__log4j.properties file print logs to the console.__

__testData.properties file use in data provider testNG mechanism. You can change test data for our tests according to the assignment.__

__In src/test/java our tests.__

### Test descriptions:
__In first test case I didn't see "The STEM Journals" and "Activision: STEM - in the Videogame Industry" in response log, therefore I did pretty similar asserts.__

__I left the code commented out for this check and I didn't use data provider in commented code. You can change data provider and run.__

__For validate JSON values I use serialization and streams.__

### TODO list:

__If test class will grow, move data provider methods to another class.__

__Display asserts and Get request in allure report.__