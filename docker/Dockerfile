ARG UBUNTU_VERSION=20.04
FROM ubuntu:$UBUNTU_VERSION

# Define working directory
WORKDIR /data/joa

# Create binary directory
RUN mkdir /data/joa/bin

# Create workspace directory
RUN mkdir /data/joa/workspace

# Create config directory
RUN mkdir /data/joa/config

COPY config.yml /data/joa/config/config.yml

# Install dependencies
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
     && apt-get -y install --no-install-recommends \
       wget openjdk-17-jre gdal-bin libsqlite3-mod-spatialite \
     && rm -rf /var/lib/apt/lists/*

# Download binary
RUN wget -O /data/joa/bin/joa-0.1.0.jar https://github.com/sebastianfrey/joa/releases/download/v0.1.0/joa-0.1.0.jar

# Ports
EXPOSE 8080 8081

# Mount points
VOLUME ["/data/joa/workspace", "/data/joa/config"]

# Default entrypoint
ENTRYPOINT ["java", "-jar", "/data/joa/bin/joa-0.1.0.jar", "server"]

# Default CMD
CMD ["/data/joa/config/config.yml"]
