# kickerduino-server

## deploy on aws
```
sbt docker:publishLocal
docker push pierone.stups.zalan.do/hackweek/kicker-server:1.0-SNAPSHOT
senza create
``