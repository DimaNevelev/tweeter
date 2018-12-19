FROM openjdk:8-jdk-alpine

ARG JAVA_ARGS
ADD . /usr/local/tweeter

#RUN cd /usr/local/tweeter && ./mvnw clean install -DskipTests
#
COPY target/tweeter.jar /usr/local/run/
COPY entrypoint.sh /usr/local/run/
RUN chmod +x /usr/local/run/entrypoint.sh && chmod +x /usr/local/run/tweeter.jar
ENTRYPOINT ["/usr/local/run/entrypoint.sh"]

EXPOSE 8080 5005