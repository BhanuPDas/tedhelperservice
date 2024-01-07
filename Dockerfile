FROM eclipse-temurin:21-jdk
WORKDIR /javaApp
COPY /target/tedHelperService*.jar /javaApp/tedHelperService.jar
EXPOSE 8050
ENTRYPOINT [ "java", "-jar", "/javaApp/tedHelperService.jar" ]