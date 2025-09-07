# JOA - Java OGC API (Work In Progress)

A lightweight, simple and easy to use OGC API implementation built with [Dropwizard](https://www.dropwizard.io/).

## Features

- Implements the [OGC API - Features](https://www.ogc.org/standards/ogcapi-features) standard
- Follows a minimialistic aproach, which allows **Zero Config** deployment of an **OGC API**.
- Supports currently the following backends:
  - [GeoPackage](https://www.geopackage.org/) (powerd by [NGA's GeoPackage Java lib](https://github.com/ngageoint/geopackage-java))
- Conforms to the following standards:
  - [OGC API - Features - Part 1: Core](http://docs.opengeospatial.org/is/17-069r3/17-069r3.html)
  - [OGC API - Features - Part 2: Coordinate Reference Systems by Reference](http://docs.opengeospatial.org/is/18-058/18-058.html)
- Provides a simple HTML based UI for browsing services, collections and data
- Supports standard query parameters: `bbox`, `bbox-crs`, `datetime`, `limit` and `crs`
- Supports simple filtering by property: `propertyA=<value>&propertyB=<value>`
- Supports additional query parameters: `offset`
- Comes with built in support for
  - `CORS`
  - `GZIP` response encoding
  - `HTTP` and `HTTPS`

## Getting Started with Docker

### 1. Create a workspace:

```bash
$ mkdir -p $HOME/joa/workspace
```

Place one or more GeoPackage-Files under `$HOME/joa/workspace/`.

> If you have no GeoPackage-File available, you can download a sample file from [GitHub](https://github.com/sebastianfrey/joa/raw/main/data/example.gpkg).

### 2. Spin up a container:

```bash
$ docker run -d -it -v $HOME/joa/workspace:/data/joa/workspace -p 8080:8080 sfrey/joa:0.1.0
```
### 3. Open your browser at http://localhost:8080/api/

### 4. Enjoy your **Zero Config** OGC API. =)


## Getting Started Old School

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
  rootPath: /
joa:
  gpkg:
    worksapce: /data/joa/workspace
    runtime: mod_spatialite
```

### 5. Place one or more GeoPackage-Files under `/data/joa/workspace/`.

> If you have no GeoPackage-File available, you can download a sample file from [JOA](https://github.com/sebastianfrey/joa/raw/main/data/example.gpkg).
Then open your browser, go to http://localhost:8080/ and enjoy your **Zero Config** OGC API. =)

### 6. Now start the application

```bash
java -jar joa-0.1.0.jar server config.yml
```

## Development

TODO

## License

TODO
