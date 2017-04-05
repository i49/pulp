
# Pulp

Pulp project provides Java API for EPUB processing and its implementation.

## Project Structure

This project is composed of the following modules.

Modules                     |Description
----------------------------|-------------------------------------------
pulp-api                    |Java API for EPUB processing
pulp-api-tests              |Unit tests for the API implementation
pulp-api-tests-with-samples |Testing with IDPF EPUB3 samples
pulp-core                   |Reference implementation of the API
pulp-cli                    |Command line utility built on the API
pulp-distribution           |Distribution file generator
pulp-parent                 |Common settings for all other modules above

## How To Build

### Prerequisites
All prerequisites necessary to build the project are listed below:
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
