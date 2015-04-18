organization  := "com.jgpacker"

version       := "0.1"

scalaVersion  := "2.10.5"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray"              %%  "spray-can"     % sprayV,
    "io.spray"              %%  "spray-routing" % sprayV,
    "io.spray"              %%  "spray-testkit" % sprayV  % "test",
    "io.spray"              %%  "spray-json"    % "1.3.1",
    "com.typesafe.akka"     %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"     %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"            %%  "specs2-core"   % "2.3.7" % "test",

    "com.twitter"           %% "util-hashing"   % "6.23.0",

    "com.websudos"          %%  "phantom-dsl"   % "1.5.0" % "compile", //Cassandra DSL
    "org.apache.cassandra"   % "cassandra-all"  % "2.1.0-rc5",
    "com.datastax.cassandra" % "cassandra-driver-core" % "2.1.5",
    "com.datastax.cassandra" % "cassandra-driver-mapping" % "2.1.5",

    "commons-validator"      % "commons-validator" % "1.4.1" % "compile" //for the UrlValidator
  )
}

Revolver.settings

resolvers ++= Seq( //from https://github.com/websudos/phantom
 "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype repo"                    at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype releases"                at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype snapshots"               at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype staging"                 at "http://oss.sonatype.org/content/repositories/staging",
  "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
  "Twitter Repository"               at "http://maven.twttr.com",
  "Websudos releases"                at "http://maven.websudos.co.uk/ext-release-local"
)

