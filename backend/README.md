# kickerduino-server

## deploy on aws
```
sbt docker:publishLocal
docker push pierone.stups.zalan.do/hackweek/kicker-server:1.0-SNAPSHOT
senza create kicker-server.yaml [stack-number] 1.0-SNAPSHOT
``