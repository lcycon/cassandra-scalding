import sbt._
import sbt.Keys._

object ProjectBuild extends Build {

  lazy val buildVersion =  "0.0.1"

  lazy val root = Project(id = "cassandra-scalding", base = file("."), settings = Project.defaultSettings).settings(
    organization := "com.lukecycon",
    description := "A project to run Apache Cassandra data through Twitter's Scalding",
    version := buildVersion,

    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    pomExtra := (
      <url>http://github.com/lcycon/cassandra-scalding</url>
      <licenses>
        <license>
          <name>GNU GPLv3</name>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:lcycon/cassandra-scalding.git</url>
        <connection>git:git@github.com:lcycon/cassandra-scalding.git</connection>
      </scm>
      <developers>
        <developer>
          <id>lcycon</id>
          <name>Luke Cycon</name>
          <url>http://lukecycon.com/</url>
        </developer>
      </developers>
    ),
    publishTo <<= version { version: String =>
      Some(
        Resolver.sftp(
          "Luke Cycon's Maven Repository",
          "maven.lukecycon.com",
          "maven.lukecycon.com/releases"
        )
      )
    }
  )

}
