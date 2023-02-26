
## ConvertME

ConvertME is a back-end web application that provides a currency conversion service to its clients. The application is built using the Java programming language and Spring Boot framework, and utilizes the ExchangeRates Data API provided by apilayer to retrieve real-time and historical exchange rate data for over 168 world currencies. It provides a REST API that can be used by other applications to convert currencies. The API allows clients to specify the input currency, output currency, and the amount to be converted.

## Prerequisites

To build and run ConvertME, you will need:

- Java 11
- Maven 3.6 or higher

## Building and Running

To build and run ConvertME, follow these steps:

1. Clone the repository:
- git clone https://github.com/erkan9/pu-computational-linguistics-project.git

2. Navigate to the project root directory:
- cd ConvertME

3. Build the project:
- mvn package

4. Create Database called ConvertMe2 in PgAdmin4


5. Update datasource username and password credentials


6. Run the application:
- java -jar target/ConvertME-3.0.0.jar

You should see output similar to the following:
2023-02-25 14:10:34.537  INFO 1124 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2023-02-25 14:10:34.543  INFO 1124 --- [           main] com.example.demo.DemoApplication         : Started DemoApplication in 2.822 seconds (JVM running for 3.479)

## API

The ExchangeRates Data API provided by apilayer allows developers to access real-time and historical exchange rate data for over 168 world currencies. The API uses a simple HTTP GET request to retrieve the exchange rate data in JSON format, and provides a wide range of features including currency conversion, data filtering, and support for multiple languages.

1. Get API key by visiting the Link below
- https://apilayer.com/marketplace/exchangerates_data-api#pricing

2. Create System variable CM_ECB_API_KEY and paste your API key as variable value

## Dependencies

- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Validation
- Spring Boot Starter Web
- PostgreSQL Driver
- OkHttp 2.7.5
- OkHttp3 3.11.0
- Lombok
- Spring Boot Starter Test
- Spring Security Test
- ModelMapper
- Apache HttpClient 4.5.13
- JSON 20220924

## Contributors

- Erkan Kamber(@erkamber)
