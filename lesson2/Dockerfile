FROM maven:3-openjdk-17 AS builder
WORKDIR /app

COPY . /app/shell

WORKDIR /app/shell
RUN ./gradlew clean build

FROM amd64/openjdk:17-alpine AS production

WORKDIR /app

COPY --from=builder /app/shell/build/libs/shell.jar .

CMD ["java", "-jar", "/app/shell.jar"]
