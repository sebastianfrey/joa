# JOA - Java OGC API (Work In Progress)

An OGC API implementation built with Dropwizard.

## About

JOA follows a minimialistic aproach, which allows **Zero Config** deployment of an **OGC API**.

Currently the following backends are supported:

- [GeoPackage](https://www.geopackage.org/) powerd by [NGA's GeoPackage Java lib](https://github.com/ngageoint/geopackage-java) (`Default`)

Which conforms to the following standards:

- [OGC API - Features - Part 1: Core](http://docs.opengeospatial.org/is/17-069r3/17-069r3.html)

## Getting started

This section demonstrates, how JOA can be deployed.

### Run with Docker

### 1. Create a workspace:

```bash
$ mkdir -p $HOME/joa/workspace
```

Place one or more GeoPackage-Files under `$HOME/joa/workspace/`.

> If you have no GeoPackage-File available, you can download a sample file from [JOA](https://github.com/sebastianfrey/joa/raw/main/data/example.gpkg).

### 2. Spin up a container:

```bash
$ docker run -d -it -v $HOME/joa/workspace:/data/joa/workspace -p 8080:8080 sfrey/joa:0.1.0
```
### 3. Open your browser at http://localhost:8080/api/**

### 4. Enjoy your **Zero Config** OGC API. =)


### Manual Installation

### 1. Install dependencies:

```bash
$ sudo apt-get install openjdk-17-jre gdal-bin libsqlite3-mod-spatialite
```

### 2. Download binaries:

```bash
$ wget -O joa-0.1.0.jar https://github.com/sebastianfrey/joa/releases/download/v0.1.0/joa-0.1.0.jar
```

### 3. Create a working directory:

```bash
$ mkdir -p /data/joa/workspace/ && cd /data/joa/
```

### 4. Create a config file called `config.yml`

```bash
$ touch config.yml
```

with the following content

```yml
logging:
  level: INFO
  loggers:
    com.github.sebastianfrey.joa: DEBUG
server:
  rootPath: /api
joa:
  gpkg:
    worksapce: /data/joa/workspace
    runtime: mod_spatialite
```

### 5. Place one or more GeoPackage-Files under `/data/joa/workspace/`.

> If you have no GeoPackage-File available, you can download a sample file from [JOA](https://github.com/sebastianfrey/joa/raw/main/data/example.gpkg).
Then open your browser, go to http://localhost:8080/api/ and enjoy your **Zero Config** OGC API. =)

### 6. Now start the application

```bash
java -jar joa-0.1.0.jar server config.yml
```

## Development

TODO

## License

TODO
