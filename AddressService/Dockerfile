##############
#build
#############
FROM maven:3.6.0-jdk-11-slim AS build
COPY ./ ./AddressService
#Add settings.xml file for github auth
#ARG TOKEN
#RUN mkdir ~/.m2 && \
#echo "<settings><servers><server><id>github</id><username>x-access-token</username><password>${TOKEN}</password></server></servers></settings>" > ~/.m2/settings.xml

RUN cd ./AddressService && \
mvn package

##############
#Package
#############
FROM openjdk:11.0-jre AS PROD
#FROM cubeiocorp/jre:11 AS PROD


COPY --from=build AddressService/target/addressservice-5.jar /root.jar
COPY --from=build AddressService/src/main/resources/io/cube/sampleapps/AddressService/dd-java-agent-0.68.0.jar /dd-java.jar

CMD ["java", "-javaagent:/dd-java.jar", "-Ddd.propagation.style.extract=B3", "-Ddd.propagation.style.inject=B3", "-jar", "/root.jar" ]

EXPOSE 8087
