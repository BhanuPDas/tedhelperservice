FROM eclipse-temurin:21-jdk
WORKDIR /javaApp
COPY /target/tedHelperService*.jar /javaApp/tedHelperService.jar
EXPOSE 8050
CMD [ "java", "-jar", "tedHelperService.jar" ]