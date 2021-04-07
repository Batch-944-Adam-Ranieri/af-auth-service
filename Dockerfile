FROM ubuntu
# FROM you specifiy a base image (background environment)
# create an enviornment that has centos in it

COPY . /workspace
# copy everything in the current directory of this docker file
# and put it in the virtual enviornment under the root directory
# called /workspace
# on your PC the root directory for most things is your C: drive
# in this enviornment we are creating it will be /workspace

WORKDIR /workspace
# when you write commands what directory they will execute in
RUN apt update
RUN apt-get install -y maven
# any commands you need to run while building the image

WORKDIR /workspace/build/libs

EXPOSE 8080
# this allows the container to be accessed on that port

RUN sudo chmod 777 auth-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", "-jar", "auth-0.0.1-SNAPSHOT.jar" ]
# The command that will execute when you create an instance of the image
# Usually this would be the command to start your application