FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/ms-bank-account-balance-management-*-SNAPSHOT.jar
COPY ${JAR_FILE} ms-bank-account-balance-management.jar
RUN addgroup -S bootcampgroup && adduser -S bootcampuser -G bootcampgroup
RUN mkdir -p /opt/logs/ms-bank-account-balance-management
RUN chown -R bootcampuser:bootcampgroup /opt/logs/ms-bank-account-balance-management
USER bootcampuser:bootcampgroup
ENTRYPOINT ["java","-jar","/ms-bank-account-balance-management.jar"]
