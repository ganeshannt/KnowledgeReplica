# KnowledgeReplica
Article platform to share your knowledge

[![Java Version](https://img.shields.io/badge/java-11-brightgreen.svg)](https://jdk.java.net/11)
[![Spring boot Version](https://img.shields.io/badge/SpringBoot-2.5.6-brightred.svg)](https://spring.io/projects/spring-boot)

### Contributions are welcome

## Requirements

For building and running the application you need:

- [JDK 11](https://jdk.java.net/11)
- [Maven 3](https://maven.apache.org)


## Running the application locally

First, clone the repository to your local machine:

```bash
git clone https://github.com/ganeshannt/KnowledgeReplica.git
```
Import it as Maven project from your favourite IDE and run below command to start mysql and local mail server
```bash
docker-compose up -d
```

Run maven command to build project
```bash
mvn clean compile test install
```

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `package com.knowledgereplica.KnowledgeReplicaApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

The web application is accessible via `localhost:8080`
