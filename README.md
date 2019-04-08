# SMTP Pranks [![Build Status](https://travis-ci.org/nyg/smtp-pranks.svg?branch=master)](https://travis-ci.org/nyg/smtp-pranks)

## Description

From a list of emails, *SMTP Pranks* creates groups consisting of one sender and multiple recipients. *SMTP Pranks* then chooses a message and sends it to the recipients on behalf of the sender.

The list of emails, the groups sizes and the messages to be sent are all user-defined, making SMTP very flexible.

*SMTP Pranks* is a lab project for the *Network programming* course at the HEIG-VD. Its requirements are defined here: [SoftEng-HEIGVD/Teaching-HEIGVD-RES-2019-Labo-SMTP](https://github.com/SoftEng-HEIGVD/Teaching-HEIGVD-RES-2019-Labo-SMTP).

## Configure, Build & Run

The list of emails are to be defined in the `victims.txt` file, one email per line. The messages to be sent must be written in the `pranks.txt` file. The group size is set in the `application.properties` file. All files are in the `src/main/resources` folder.

To build *SMTP Pranks*, use [Maven](https://maven.apache.org):

```sh
$ mvn clean package
[INFO] Scanning for projects...
[INFO]
[INFO] ----------------------< ch.heig.res:smtp-pranks >-----------------------
[INFO] Building smtp-pranks 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.439 s
[INFO] Finished at: 2019-04-08T21:51:17+02:00
[INFO] ------------------------------------------------------------------------
```

The built `jar` is placed in the `target` directory. Run it with the following command:

```sh
$ java -jar target/smtp-pranks-1.0.0.jar
```

## Testing

When running *SMTP Pranks*, an SMTP server must be specified for the mails to be sent. Instead of using a real-world server, a mock one can be used. A mock SMTP server will not relay the mails it receives to other SMTP servers, instead, it keeps them so they can be viewed and analyzed, usually through a Web interface.

Two scripts to build and run a [MockMock](https://github.com/dc55028/MockMock) [Docker](https://www.docker.com) image are provided in the `docker` folder. Use the following commands to build and run the image:

```sh
$ cd docker
$ ./build-image.sh   # will clone and build MockMock as well as create the Docker image
$ ./run-container.sh # starts up the container for the Docker image 
```

The MockMock server is listens to port 2525 of the localhost and the Web interface can be access at [localhost:8282](http://localhost:8282).

## Architecture

TODO
