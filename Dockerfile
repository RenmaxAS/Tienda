FROM openjdk:17-jdk-alpine as javita

WORKDIR /app/TiendaVirtual

COPY ./pom.xml /app
COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY --from=javita /app/TiendaVirtual/target/Tienda-0.0.1-SNAPSHOT.jar .

EXPOSE 9091

ENTRYPOINT ["java", "-jar", "Tienda-0.0.1-SNAPSHOT.jar"]
#CMD ["java", "-jar", "Tienda-0.0.1-SNAPSHOT.jar"]