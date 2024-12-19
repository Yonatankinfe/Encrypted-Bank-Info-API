# Encrypted-Bank-Info-API
This project is a backend application that manages user data and encrypted bank information using a Spring Boot REST API and CockroachDB as the database.
# Features
## User Management
 •  Create, retrieve,update, and delete user details
 •	User entity includes fields like firstName, lastName, email, and phoneNumber.
## Bank Information Management:
 •	Create, retrieve, update, and delete bank information associated with a user.
	•	Bank information includes fields like bankAccountNumber, bankName, and accountType.
	•	All sensitive bank account data is encrypted and decrypted using AES encryption.
# Used
  •	Spring Boot
	•	CockroachDB
	•	AES Encryption
	•	Java
	•	PostgreSQLDialect
	•	Spring Data JPA
 # Configuration 
 // application.properties 
   spring.application.name=demo
    spring.datasource.url=jdbc:postgresql://localhost:26257/backend_test?sslmode=disable
    spring.datasource.username=root
    spring.datasource.password=
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
## Database 
  1.	Create the database:

CREATE DATABASE backend_test;


2.	Switch to the database:

USE backend_test;


3.	Create the user table:

CREATE TABLE user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name STRING NOT NULL,
    last_name STRING NOT NULL,
    email STRING UNIQUE NOT NULL,
    phone_number STRING
);


4.	Create the bank_information table:

CREATE TABLE bank_information (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES user(id) ON DELETE CASCADE,
    bank_account_number STRING NOT NULL,
    bank_name STRING NOT NULL,
    account_type STRING NOT NULL
);

5.	Verify the tables:
To confirm the tables are set up correctly, run:

SHOW TABLES;


6.	Test Foreign Key Constraints:
Ensure the foreign key (user_id) in the bank_information table references the id in the user table:

SHOW CONSTRAINTS FROM bank_information;

# API Endpoints:

Users
	•	Create User: POST /users
	•	Get User: GET /users/{id}
	•	Update User: PUT /users/{id}
	•	Delete User: DELETE /users/{id}

Bank Information
	•	Create Bank Information: POST /users/{userId}/bank-information
	•	Get Bank Information: GET /users/{userId}/bank-information
	•	Update Bank Information: PUT /users/{userId}/bank-information
	•	Delete Bank Information: DELETE /users/{userId}/bank-information
# Testing 
{
  "firstName": "Yonatan",
  "lastName": "Kinfe ",
  "email": "yonatankinfe@gmail.com",
  "phoneNumber": "+251-1923-123-123"
}

## Notes:
  •	The ON DELETE CASCADE ensures that when a user is deleted, their associated bank information is also deleted automatically.
	•	The gen_random_uuid() function is used to auto-generate UUIDs for id fields.


