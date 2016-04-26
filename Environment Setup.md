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

### To start Spark in cluster mode: <br/> 
* Execute following command on Master machine: <br/> 
`cd spark-1.6.0-bin-hadoop2.6/sbin/`  <br/> 
`./start-master.sh`  <br/> 
* Output of this command will be logged to file, verify that Master started successfully by checking the log file:  <br/> 
`tail <log_file>`  <br/> 
`[Example: tail -100f /home/ubuntu/spark-1.6.0-bin-hadoop2.6/logs/spark-ubuntu-org.apache.spark.deploy.master.Master-1-ec2-52-87-183-25.compute-1.amazonaws.com.out]` <br/>
* Check Spark console from UI `[Public_IP:8080]` <br/>
* Execute following command on each Slave machine: <br/>
`cd spark-1.6.0-bin-hadoop2.6/sbin/` <br/>
`./start-slave.sh spark://Hostname_Of_Master:7077 [eg: Public_DNS_Of_Master = ec2-54-175-167-72.compute-1.amazonaws.com]`  <br/>
* Output of this command will be logged to file, verify that Master started successfully by checking the log file:  <br/>
`tail <log_file>` <br/>
* Check Master UI console again and workers will be added to it.  <br/>


Hadoop:
-------
* Refer following [this](https://letsdobigdata.wordpress.com/2014/01/13/setting-up-hadoop-1-2-1-multi-node-cluster-on-amazon-ec2-part-2/) link for installing and **starting** Hadoop in cluster mode. <br/> 
* **Note:**
  * These links are bit old, use the latest version of Hadoop (2.7) and Java(1.8) <br/> 
  * In Hadoop 2.7, HADOOP_CONF directory is at $HADOOP_HOME/etc/hadoop instead of old $HADOOP_HOME/conf <br/> 
  * All conf files should contain private IP, otherwise NameNode won't start <br/> 
  * Delete hdfs tmp directory from each machine before formatting namenode <br/> 
