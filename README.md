# JOA - Java OGC API (Work In Progress)

An OGC API implementation built with Java leveraging Dropwizard.

## About

JOA follows an minimialistic aproach, which allows **Zero Config** deployment of an **OGC API**, which conforms to the following standards:

- [OGC API - Features - Part 1: Core](http://docs.opengeospatial.org/is/17-069r3/17-069r3.html)

Currently the following backends are supported:

- [GeoPackage](https://www.geopackage.org/) powerd by [NGA's GeoPackage Java lib](https://github.com/ngageoint/geopackage-java) (`Default`)

Coming soon:

- [PostgreSQL](https://www.postgresql.org/) with [PostGIS](https://postgis.net/)

## Getting started

This section demonstrates, how JOA can be deployed using the GeoPackage backend using docker.

First create a directory. This is the directory where your GeoPackage files will be located:

```
mkdir -p joa/data/
```

Now place one or more GeoPackage files in the previously created directory.

After that run the following command:

```
docker run -d -v $(pwd)/joa/data:/workspace/joa/data -p 8080:8080 joa/joa:latest
```

Then open your browser and go to http://localhost:8080/api/.

Now enjoy your **Zero Config** OGC API and buy me a coffe. ;-)

