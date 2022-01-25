# JOA - Java OGC API (WIP)

About
---

A simple (and not complete) Java based OGC API implementation. Currently only a small subset of
[OGC API - Features - Part 1: Core](http://docs.opengeospatial.org/is/17-069r3/17-069r3.html)
is implemented.

Getting started
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/joa-0.1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

