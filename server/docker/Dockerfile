# https://read.acloud.guru/3-pro-tips-to-speed-up-your-java-based-aws-lambda-continuous-deployment-builds-72310fe18274

FROM openjdk:8-jdk
RUN apt-get -y update && apt-get -y upgrade
RUN apt-get -y install python-pip python-setuptools python-wheel --no-install-recommends
RUN pip install --upgrade awscli
ARG MAVEN_VERSION=3.5.4
ARG USER_HOME_DIR="/root"
ARG SHA=ce50b1c91364cb77efe3776f756a6d92b76d9038b0a0782f7d53acf1e997a14d
ARG BASE_URL=http://mirror.ox.ac.uk/sites/rsync.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
 && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
 && echo "${SHA} /tmp/apache-maven.tar.gz" | sha256sum -c - \
 && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
 && rm -f /tmp/apache-maven.tar.gz \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
RUN mkdir --parents /usr/src/app
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn verify clean --fail-never
RUN rm -r /usr/src/app/*

