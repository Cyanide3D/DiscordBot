FROM openjdk:15

COPY target/123.jar /bot.jar
COPY /settings.properties /settings.properties

CMD ["java", "-jar", "/bot.jar"]