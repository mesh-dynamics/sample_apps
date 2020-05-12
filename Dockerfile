################
####build####
###############
FROM maven:3.6.0-jdk-11-slim AS build
ARG TOKEN
COPY . /code
RUN mkdir ~/.m2
RUN echo "<settings><servers><server><id>github</id><username>x-access-token</username><password>${TOKEN}</password></server></servers></settings>" > ~/.m2/settings.xml
RUN cd /code && \
mvn install && \
cd MIRest && \
mvn package

############
#Copy build to production
###########
FROM tomcat:9-jre11 AS Prod
RUN rm -rf /usr/local/tomcat/webapps/ROOT
# adding line below to speedup tomcat startup
# see https://github.com/theotherp/nzbhydra2/issues/42
# reduced time from 360 s to 6s!
RUN perl -0777 -i -pe 's/securerandom.source=file:\/dev\/random/securerandom.source=file:\/dev\/urandom/' /etc/java-11-openjdk/security/java.security
COPY --from=build /code/MIRest/target/MIRest-V1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
COPY --from=build /code/MIRest/jib/tmp/samplerconfig.json /tmp/samplerconfig.json
