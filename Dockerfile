FROM openjdk:21

ADD out/artifacts/ParkingTerminal_jar/ParkingTerminal.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]