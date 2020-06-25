FROM maven:3.6.3-jdk-11

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

COPY . .

EXPOSE 8090

CMD mvn spring-boot:run

