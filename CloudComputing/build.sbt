name := "HybridCloud"
 
version := "1.0"
 
scalaVersion := "2.11.7"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.4.1"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "1.4.0" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.4.0" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.4.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming-twitter" % "1.4.0"
libraryDependencies += "com.google.code.gson" % "gson" % "2.3"
libraryDependencies += "org.twitter4j" % "twitter4j-core" % "3.0.3"
libraryDependencies += "commons-cli" % "commons-cli" % "1.2"