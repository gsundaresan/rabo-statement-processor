Introduction

Rabo bank receives monthly deliveries of customer statement records. This information is delivered in two formats, CSV and XML.
The rabo-statement-processor microservice has it's main function in validating the provided customer statement records.
The main functions covered as part of this assignment involves the following:

1. All transaction references should be unique irrespective of accounts.

2. The end balance needs to be validated based on opening balance and mutation.
#######################################################################################################################################
API Documentation:

The API's have been exposed through Swagger2 with required documentation on input and output types, sample and description etc.
However we will outline the basic API definition here:

API: POST::/api/v1/statementprocessor/validateStatements

The request body requires a file to be uploaded(multipart)
This implementation supports only csv and xml files. The api responds with a json response of transaction reference and description of each of the failed records.
#######################################################################################################################################
Libraries/Frameworks Used:

Java 8
SpringBoot 1.5.3
gradle-6.6.1
Jackson Core, jackson-dataformat-xml, jackson-dataformat-csv
swagger-2.9.2

Note: I have used Jackson libraries for mapping/reading xml& csv due to the fact that Jackson is threadsafe when compared to POI or OpenCSV
#######################################################################################################################################

Unit Tests & Integration Tests:

All the required unit tests and integration tests are available under the \src\test\java\com\rabo\transactions\assignmentbackend location.
