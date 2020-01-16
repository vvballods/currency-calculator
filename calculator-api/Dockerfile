ARG VERSION=8

FROM openjdk:${VERSION}-alpine as BUILD

COPY . /app
WORKDIR /app
RUN ./mvnw clean package

FROM openjdk:${VERSION}-alpine

COPY --from=BUILD /app/target/calculator-api-1.0.jar /bin/runner/calculator-api.jar
WORKDIR /bin/runner
CMD ["java", "-jar", "calculator-api.jar"]