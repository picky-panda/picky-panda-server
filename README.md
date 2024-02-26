## How to run
```
1. clone this repository
2. Create a file named application.yml inside the resources folder.
3. Fill in the contents of the application.yml file.
```

## application.yml
```
server:
  port:

spring:
  output:
    ansi:
      enabled:

  datasource:
    driver-class-name:
    url:
    username:
    password:

  jpa:
    hibernate:
      ddl-auto:
    properties:
      hibernate:
        show_sql:
        format_sql:
        use_sql_comments:
    open-in-view:

  security:
    user:
      name:
      password:

jwt:
  secret:
  access-expiration:
  refresh-expiration:

google:
  client-id:
  client-secret:
```

# picky-panda-server
picky-panda-server with Spring, Java

## 🛠️ Environment

| |  |
| --- | --- |
| Idea | IntelliJ |
| Spring version | 3.2.2 |
| Database | AWS RDS(MySQL), Redis |
| Server | Google Cloud Platform E2(Ubuntu) |
| Project build tool | Gradle |
| Java version | Java 17  |
| package structure | domain package structure |
| API test | PostMan |
| External connection | Google login

## ☁️ ERD
<img width="600" src="https://github.com/picky-panda/picky-panda-server/assets/51286325/c8adee74-00ec-447d-927a-5f375b6297d2">

<hr>

## 📂 Project Structure
```
📂 picky-panda-server
├── build.gradle
└── src
    └── main
        ├── 📂 java/io/picky/panda
        │   ├── PickyPandaApplication.java
        │   ├── 🗂 auth
        │   ├── 🗂 common
        │   ├── 🗂 config
        │   ├── 🗂 exception
        │   ├── 🗂 external
        │   ├── 🗂 restaurant
        │   ├── 🗂 review
        │   ├── 🗂 security
        └── 📂 resources
            ├── application.yaml
```
