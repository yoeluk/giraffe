import SonatypeKeys._
import com.typesafe.sbt.SbtStartScript
seq(SbtStartScript.startScriptForClassesSettings: _*)

name         := "giraffe"
version      := "1.0-SNAPSHOT"
organization := "com.zaporbit"
profileName  := "com.zaporbit.giraffe"

scalaVersion := "2.11.4"

resolvers  += Classpaths.sbtPluginReleases
resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype snapshots"  at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Spray Repository"    at "http://repo.spray.io",
  "Spray Nightlies"     at "http://nightlies.spray.io/"
)

libraryDependencies ++= {
  val akkaVersion       = "2.3.4"
  val sprayVersion      = "1.3.1"
  Seq(
    "com.typesafe.akka"       %%  "akka-actor"                      % akkaVersion,
    "com.typesafe.akka"       %%  "akka-slf4j"                      % akkaVersion,
    "com.typesafe.akka"       %%  "akka-remote"                     % akkaVersion,
    "com.typesafe.akka"       %%  "akka-multi-node-testkit"         % akkaVersion   % "test",
    "com.typesafe.akka"       %%  "akka-testkit"                    % akkaVersion   % "test",
    "org.scalatest"           %%  "scalatest"                       % "2.2.0"       % "test",
    "io.spray"                %%  "spray-can"                       % sprayVersion,
    "io.spray"                %%  "spray-client"                    % sprayVersion,
    "io.spray"                %%  "spray-routing"                   % sprayVersion,
    "io.spray"                %%  "spray-json"                      % "1.2.6",
    "com.typesafe.akka"       %%  "akka-slf4j"                      % akkaVersion,
    "ch.qos.logback"           %   "logback-classic"                % "1.1.2"
  )
}

sonatypeSettings

publishMavenStyle       := true
publishArtifact in Test := false
pomIncludeRepository    := { _ => false }
publishTo               := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

licenses := Seq(
  "Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
)
homepage := Some(url("http://github.com/yoeluk/giraffe"))

pomExtra := (
  <developers>
    <developer>
      <id>yoeluk</id>
      <name>Yoel Garcia</name>
      <email>yoeluk@gmail.com</email>
    </developer>
  </developers>
  <scm>
    <connection>scm:git:git@github.com:yoeluk/giraffe.git</connection>
    <url>scm:git:git@github.com:yoeluk/giraffe.git</url>
    <developerConnection>scm:git:git@github.com:yoeluk/giraffe.git</developerConnection>
  </scm>
)

test in assembly := {}
//mainClass in assembly := Some("com.zaporbit.giraffe.GiraffeBackend")
//assemblyJarName in assembly := "giraffe-backend.jar"

mainClass in assembly := Some("com.zaporbit.giraffe.GiraffeBackend")
assemblyJarName in assembly := "giraffe-frontend.jar"
