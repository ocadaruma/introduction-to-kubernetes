name := "example"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

val akkaHttpVersion = "10.0.1"

val scalikejdbcVersion = "3.0.0-M4"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc",
  "org.scalikejdbc" %% "scalikejdbc-interpolation-macro",
  "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro",
  "org.scalikejdbc" %% "scalikejdbc-config"
).map(_ % scalikejdbcVersion) ++ Seq(
  "com.typesafe.akka" %% "akka-http-core",
  "com.typesafe.akka" %% "akka-http",
  "com.typesafe.akka" %% "akka-http-spray-json"
).map(_ % akkaHttpVersion) ++ Seq(
  "com.h2database" % "h2" % "1.4.193",
  "ch.qos.logback" % "logback-classic"  % "1.1.8",
  "mysql" % "mysql-connector-java" % "5.1.40"
)

fork in run := true

enablePlugins(SbtTwirl, DockerPlugin)

dockerfile in docker := {
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"
  val serverPort = 3000

  new Dockerfile {
    from("java:8-jre-alpine")
    add(artifact, artifactTargetPath)
    expose(serverPort)
    entryPoint("java", "-jar", artifactTargetPath, "0.0.0.0", serverPort.toString)
  }
}

imageNames in docker := Seq(
  ImageName(s"${organization.value}/${name.value}:latest"),
  ImageName(s"${organization.value}/${name.value}:v${version.value}")
)
