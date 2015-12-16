name := """kickerduino-server"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  filters
)
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
dockerExposedPorts in Docker := Seq(9000)
dockerBaseImage := "zalando/openjdk:8u40-b09-4"
dockerRepository := Some("pierone.stups.zalan.do/hackweek")
packageName := "kicker-server"