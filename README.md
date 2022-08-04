# ACME Bank Account Management Service

A SpringBoot demo application with RESTful API service for account management

## Usage 

Git clone or download the project to your computer

> git clone https://github.com/NosearY/acme-account-manager.git

Cd into the project root folder

>cd account-manager

## Run (Dev)

Windows
> .\gradlew.bat bootRun -Pprofile=dev

Linux
> ./gradlew bootRun -Pprofile=dev

An empty in-memory H2 database is setup where you can login into `http://localhost:8080/h2-console` to view or modify any data.

## Run (Production)

Windows
> .\gradlew.bat bootRun

Linux
> ./gradlew bootRun

Changes made will be persisted into an H2 database located in ./db/acmedb-prod.h2.mv.db.

Two accounts are available from the database out-of-the-box:

| Account No. | Currency | Amount    |
|-------------|----------|-----------|
| 12345678    | HKD      | 1,000,000 |
| 88888888    | HKD      | 1,000,000 |

## API Doc

https://petstore.swagger.io/?url=https://raw.githubusercontent.com/NosearY/acme-account-manager/main/dist/api-docs.yaml


