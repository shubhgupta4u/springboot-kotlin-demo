
## Setup VSCode for SpringBoot Project
https://code.visualstudio.com/docs/java/java-spring-boot

## MySql Database
https://console.aiven.io/account/a502e3c5694b/project/shubhgupta4u-c0e4/services/mysql-shubh/overview

## SpringBoot Web Tutorial
https://www.geeksforgeeks.org/easiest-way-to-create-rest-api-using-spring-boot/

## Build & Run SpringBoot Project

### Run SpringBoot Project
``` bash
./mvnw spring-boot:run
```

### Build the JAR File
Build the Project: Run the build command:
``` bash
./mvnw package
```

### Publish the JAR to a Repository
You need to publish the JAR to a repository so other projects can use it.

#### Option 1: Local Maven Repository
``` bash
./mvnw install
```
C:\Users\shubh\.m2\repository\springboot\library\domain\springboot-library-domain\1.0.0\springboot-library-domain-1.0.0.jar

#### Option 2: Remote Maven Repository
You can publish to a remote repository like Maven Central, JFrog Artifactory, or GitHub Packages.
``` bash
./mvnw publish
```

## Create public and private certificate pem file
``` bash
openssl genrsa -out private.pem 2048
openssl rsa -in private.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in private.pem -out private_key_pkcs8.pem
```

## Import CA Certificate for SSL to be used for MySQL
``` bash
keytool -import -trustcacerts -alias mysqlServerCACert -file "E:\Kotlin\springboot-api-demo\springboot-api-demo\ca.pem" -keystore truststore.jks

java --Djavax.net.ssl.trustStore="E:\Kotlin\springboot-api-demo\springboot-api-demo\truststore.jks"
-Djavax.net.ssl.trustStorePassword=123456
```

