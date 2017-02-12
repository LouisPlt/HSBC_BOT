FROM maven:3-jdk-8

ADD . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean compile assembly:single

CMD ["java","-jar","/usr/src/app/target/hsbc-project2-1.0-SNAPSHOT-jar-with-dependencies.jar"]
