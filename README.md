# ACME Bank Account Management Service

A SpringBoot demo application with RESTful API for account management.

## Requirement

JRE >= 11

## Usage 

Git clone or download the project to your computer

> git clone https://github.com/NosearY/acme-account-manager.git

Cd into the project root folder

>cd acme-account-manager

## Run

Windows
> .\gradlew.bat bootRun

Linux
> chmod u+x gradlew && ./gradlew bootRun

Changes made will be persisted into an H2 database located in ./db/acmedb-prod.h2.mv.db.

Two accounts are available from the database out-of-the-box:

| Account No. | Currency | Amount    |
|-------------|----------|-----------|
| 12345678    | HKD      | 1,000,000 |
| 88888888    | HKD      | 1,000,000 |

## API Doc

https://petstore.swagger.io/?url=https://raw.githubusercontent.com/NosearY/acme-account-manager/main/dist/api-docs.yaml


