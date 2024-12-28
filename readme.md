https://code.visualstudio.com/docs/java/java-spring-boot

https://console.aiven.io/account/a502e3c5694b/project/shubhgupta4u-c0e4/services/mysql-shubh/overview

https://www.geeksforgeeks.org/easiest-way-to-create-rest-api-using-spring-boot/

./mvnw spring-boot:run

keytool -import -trustcacerts -alias mysqlServerCACert -file "E:\Kotlin\springboot-api-demo\springboot-api-demo\ca.pem" -keystore truststore.jks


java --Djavax.net.ssl.trustStore="E:\Kotlin\springboot-api-demo\springboot-api-demo\truststore.jks"
-Djavax.net.ssl.trustStorePassword=123456


openssl genrsa -out private.pem 2048
openssl rsa -in private.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in private.pem -out private_key_pkcs8.pem