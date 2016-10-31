name := """quill-pg"""

scalaVersion in ThisBuild := "2.11.8"

organization in ThisBuild := "org.mdedetrich"

description := "Postgres extensions for quill"

val quillVersion   = "1.0.0"
val currentVersion = "0.1.0-SNAPSHOT"
val jawnVersion    = "0.10.1"
val circeVersion   = "0.5.3"

scalafmtConfig in ThisBuild := Some(file(".scalafmt.conf"))

lazy val quillPgJdbc = project
  .in(file("quill-pg-jdbc"))
  .settings(
    name := "quill-pg-jdbc",
    version := currentVersion,
    libraryDependencies ++= Seq(
      "org.mdedetrich" %% "quill-pg-core" % currentVersion,
      "org.postgresql" % "postgresql"     % "9.4.1211",
      "io.getquill"    %% "quill-jdbc"    % quillVersion
    )
  )
  .dependsOn(quillPgCore)

lazy val quillPgAsync = project
  .in(file("quill-pg-async"))
  .settings(
    name := "quill-pg-async",
    version := currentVersion,
    libraryDependencies ++= Seq(
      "org.mdedetrich" %% "quill-pg-core"        % currentVersion,
      "io.getquill"    %% "quill-async-postgres" % quillVersion
    )
  )
  .dependsOn(quillPgCore)

lazy val quillAsyncPlayJson = project
  .in(file("quill-pg-async-playjson"))
  .settings(
    name := "quill-pg-async-playjson",
    version := currentVersion,
    libraryDependencies ++= Seq(
      "org.mdedetrich" %% "quill-pg-async" % currentVersion,
      "org.spire-math" %% "jawn-play"      % jawnVersion
    )
  )
  .dependsOn(quillPgCore)

lazy val quillAsyncJson4s = project
  .in(file("quill-pg-async-json4s"))
  .settings(
    name := "quill-pg-async-json4s",
    version := currentVersion,
    libraryDependencies ++= Seq(
      "org.mdedetrich" %% "quill-pg-async" % currentVersion,
      "org.spire-math" %% "jawn-json4s"    % jawnVersion,
      "org.json4s"     %% "json4s-native"  % "3.4.1"
    )
  )
  .dependsOn(quillPgCore)

lazy val quillAsyncArgonaut = project
  .in(file("quill-pg-async-argonaut"))
  .settings(
    name := "quill-pg-async-argonaut",
    version := currentVersion,
    libraryDependencies ++= Seq(
      "org.mdedetrich" %% "quill-pg-async" % currentVersion,
      "org.spire-math" %% "jawn-argonaut"  % jawnVersion
    )
  )
  .dependsOn(quillPgCore)

lazy val quillAsyncSpray = project
  .in(file("quill-pg-async-spray"))
  .settings(
    name := "quill-pg-async-spray",
    version := currentVersion,
    libraryDependencies ++= Seq(
      "org.mdedetrich" %% "quill-pg-async" % currentVersion,
      "org.spire-math" %% "jawn-spray"     % jawnVersion
    )
  )
  .dependsOn(quillPgCore)

lazy val quillAsyncRojoma = project
  .in(file("quill-pg-async-rojoma"))
  .settings(
    name := "quill-pg-async-rojoma",
    version := currentVersion,
    libraryDependencies ++= Seq(
      "org.mdedetrich" %% "quill-pg-async" % currentVersion,
      "org.spire-math" %% "jawn-rojoma"    % jawnVersion
    )
  )
  .dependsOn(quillPgCore)

lazy val quillAsyncRojomaV3 = project
  .in(file("quill-pg-async-rojoma-v3"))
  .settings(
    name := "quill-pg-async-rojoma-v3",
    version := currentVersion,
    libraryDependencies ++= Seq(
      "org.mdedetrich" %% "quill-pg-async" % currentVersion,
      "org.spire-math" %% "jawn-rojoma-v3" % jawnVersion
    )
  )
  .dependsOn(quillPgCore)

lazy val quillAsyncCirce = project
  .in(file("quill-pg-async-circe"))
  .settings(
    name := "quill-pg-async-circe",
    version := currentVersion,
    libraryDependencies ++= Seq(
      "org.mdedetrich" %% "quill-pg-async" % currentVersion,
      "io.circe"       %% "circe-core"     % circeVersion,
      "io.circe"       %% "circe-generic"  % circeVersion,
      "io.circe"       %% "circe-parser"   % circeVersion
    )
  )
  .dependsOn(quillPgCore)

lazy val quillPgCore = project
  .in(file("quill-pg-core"))
  .settings(
    name := "quill-pg-core",
    version := currentVersion
  )

lazy val root = (project in file(".")).aggregate(quillPgJdbc,
                                                 quillPgAsync,
                                                 quillPgCore,
                                                 quillAsyncPlayJson,
                                                 quillAsyncJson4s,
                                                 quillAsyncArgonaut,
                                                 quillAsyncSpray,
                                                 quillAsyncRojoma,
                                                 quillAsyncRojomaV3,
                                                 quillAsyncCirce)
