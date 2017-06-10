
# Pulp

Pulp project provides Java API for EPUB processing and its implementation.

## Features

Pulp has the features described below:

- Supports EPUB specification 3.0 and 3.1, which are defined by IDPF.
- Can read and write EPUB files.
- Can create EPUBs programmatically from scratch.
- Clean separation between API and its implementation.

## Project Structure

This project is composed of the following modules.

Modules                     |Description                                 |Status
----------------------------|--------------------------------------------|---------
pulp                        |Common settings for all other modules above |-
pulp-api                    |Java API for EPUB processing                |Pre-alpha
pulp-api-it                 |Integration tests with IDPF EPUB3 samples   |-
pulp-api-ut                 |Unit tests for the API implementation       |-   
pulp-cli                    |Command line utility built on the API       |Pre-alpha
pulp-core                   |Reference implementation of the API         |Pre-alpha
pulp-distribution           |Distribution file generator                 |-

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
$ cd pulp/pulp
$ mvn install
```
