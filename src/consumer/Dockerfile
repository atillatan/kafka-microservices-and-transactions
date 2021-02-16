FROM adoptopenjdk
COPY target/consumer-0.0.1-SNAPSHOT.jar /opt/app/
WORKDIR /opt/app
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/opt/app/consumer-0.0.1-SNAPSHOT.jar", "com.tradingcorp.consumerApplication"]
