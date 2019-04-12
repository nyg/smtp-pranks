FROM java:8

COPY MockMock/target/MockMock-1.4.0.one-jar.jar /opt/app/mockmock.jar

# SMTP server
EXPOSE 25

# Web interface
EXPOSE 8282

# Run MockMock server when container starts
CMD [ "java", "-jar", "/opt/app/mockmock.jar" ]
