
# Pulp

Pulp project provides Java API for EPUB processing and its implementation.

## Project Structure

This project is composed of the following modules.

Modules                     |Description                                 |Status
----------------------------|--------------------------------------------|---------
pulp-api                    |Java API for EPUB processing                |Pre-alpha
pulp-api-tests              |Unit tests for the API implementation       |-   
pulp-api-tests-with-samples |Testing with IDPF EPUB3 samples             |-
pulp-cli                    |Command line utility built on the API       |Pre-alpha
pulp-distribution           |Distribution file generator                 |-
pulp-impl                   |Reference implementation of the API         |Pre-alpha
pulp-parent                 |Common settings for all other modules above |-

## How To Build

### Prerequisites
All prerequisites to build the project are listed below:
- JDK 8 or higher
- Apache Maven 3.3 or higher

### Building with Maven
The following commands build and install all modules provided by this project into your local Maven repository.
```bash
$ cd <where you place local git repositories>
$ git clone https://github.com/i49/pulp.git
$ cd pulp/pulp-parent
$ mvn install
```
