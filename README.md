# KnowledgeReplica :books:
Article platform to share your knowledge.created this project while learning spring boot. It might not be perfect sample spring mvc project :relieved: but still beginner can explore and learn from it hence sharing here.

[![Java Version](https://img.shields.io/badge/java-21-brightgreen.svg)](https://jdk.java.net/21)
[![Spring boot Version](https://img.shields.io/badge/SpringBoot-3.2.7-brightred.svg)](https://spring.io/projects/spring-boot)

### Contributions are welcome :heart_eyes:

## Requirements

For building and running the application you need:

- [JDK 21](https://jdk.java.net/21)
- [Maven 3](https://maven.apache.org)


## Running the application locally :computer:

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

## Demo :fire:

[![Demo video](https://i.postimg.cc/MHJGSWX6/Screenshot-2022-10-09-at-2-58-27-PM.png)](https://youtu.be/DLW1YUFg8kQ)

