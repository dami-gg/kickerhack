gulp --cordova 'build browser'
docker build -t pierone.stups.zalan.do/hackweek/kicker-frontend:1.0.0-SNAPSHOT .
pierone login
docker push pierone.stups.zalan.do/hackweek/kicker-frontend:1.0.0-SNAPSHOT
echo "senza create kicker-frontend.yaml YOURVERSION 1.0.0-SNAPSHOT"
