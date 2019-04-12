FROM java:8

COPY smtp-pranks.jar /opt/app/smtp-pranks.jar
COPY user-files/* /opt/app/

CMD [ "java", "-Dapp.properties.location=/opt/app/application.properties", "-jar", "/opt/app/smtp-pranks.jar" ]
