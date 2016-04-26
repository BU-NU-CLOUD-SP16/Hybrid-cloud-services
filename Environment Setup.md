Software Installation:
======================
Java:
-----
* Check if JDK 1.8 is installed already: <br/> 
`java -version` <br/> 
* If not, follow below steps: Type 'Y' on installation prompt <br/> 
`sudo add-apt-repository ppa:webupd8team/java` <br/> 
`sudo apt-get update` <br/> 
`sudo apt-get install oracle-java8-installer` <br/> 

* Update JAVA_HOME in ~/.bashrc <br/> 
* Add JAVA_HOME to PATH: <br/> 
`export PATH=$PATH:$JAVA_HOME/bin` <br/> 
* Execute following command to update current session: <br/> 
`source ~/.bashrc` <br/> 
* Verify version and path: <br/> 
`java -version` <br/> 
`echo $JAVA_HOME` <br/> 

Unzip utility:
--------------
* Execute following command to install unzip <br/>    
`sudo apt-get install unzip` <br/> 

Maven:
------
* Java should be installed and set in 'Path' environment variable before installing Maven <br/> 
* Execute following commands to download maven binary and extract it <br/> 
```
wget http://mirror.symnds.com/software/Apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz <br/> 
tar xzvf apache-maven-3.3.9-bin.tar.gz <br/> 
```
* Update PATH variable: <br/> 
`export PATH=$PATH:/home/ubuntu/apache-maven-3.3.9/bin` <br/> 
* Verify installation: <br/> 
`mvn -v` <br/> 

Scala:
------
* Check if scala is installed already: <br/> 
`scala -version` <br/> 
* Follow below steps: Type 'Y' on installation prompt <br/> 
`sudo apt-get install scala` <br/> 
* Update SCALA_HOME in ~/.bashrc and execute following command to update current session: <br/> 
`source ~/.bashrc` <br/> 
* Verify version and path: <br/> 
`scala -version` <br/> 
`echo $SCALA_HOME` <br/> 

Spark:
------
* Java and Scala should be installed before installing Spark. <br/> 
* Get latest version of Spark binary:  <br/> 
`wget http://www.us.apache.org/dist/spark/spark-1.6.0/spark-1.6.0-bin-hadoop2.6.tgz` <br/> 
* Extract the file: <br/> 
`tar xvzf spark-1.6.0-bin-hadoop2.6.tgz` <br/> 
* Update SPARK_HOME in ~/.bashrc and execute following command to update current session: <br/> 
`source ~/.bashrc` <br/> 
* Add SPARK_HOME to PATH: <br/> 
`export PATH=$PATH:$SPARK_HOME/bin:$SPARK_HOME/sbin` <br/> 
* Verify the variables: <br/> 
`echo $SPARK_HOME` <br/> 

Hadoop:
-------
* Refer following [this](https://letsdobigdata.wordpress.com/2014/01/13/setting-up-hadoop-1-2-1-multi-node-cluster-on-amazon-ec2-part-2/) link for installing Hadoop in cluster mode. <br/> 
* **Note:**
  * These links are bit old, use the latest version of Hadoop (2.7) and Java(1.8) <br/> 
  * In Hadoop 2.7, HADOOP_CONF directory is at $HADOOP_HOME/etc/hadoop instead of old $HADOOP_HOME/conf <br/> 
  * All conf files should contain private IP, otherwise NameNode won't start <br/> 
