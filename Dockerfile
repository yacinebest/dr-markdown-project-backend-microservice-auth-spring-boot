#docker image build -t drmarkdown-microservice-auth-spring .
FROM openjdk:15
EXPOSE 9990
ADD build/libs/auth-0.0.1-SNAPSHOT.jar auth-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/auth-0.0.1-SNAPSHOT.jar"]