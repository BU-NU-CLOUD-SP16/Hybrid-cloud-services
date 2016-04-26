Hybrid Cloud Services Project 
=============================

**CS 7680 - Special Topics in Computer Systems: Cloud Computing** <br />
**Akash Singh, Jaison Babu, Surekha Jadhwani, Vignesh Shanmuganathan** <br />
__singh.aka@husky.neu.edu, babu.j@husky.neu.edu, jadhwani.s@husky.neu.edu, shanmuganathan.v@husky.neu.edu__ <br />
April 26, 2016 <br />

Description
-----------
The goal of this project is to develop a hybrid service of public and private cloud that combines the benefits of both these clouds which cannot be offered neither of the clouds in standalone.
This project consists of two parts:
 - Data segregation between two clouds
 - Run analyitcal jobs in hybrid cloud environment

For data segregation, we have built HybridCloudStreaming project which uses data tagging mechanism to classify sensitive data and non-sensitive data in order to store in private and public cloud respectively.
For analytical jobs, we have built HybridCloudAnalytics project which has two analytical jobs used for performance analysis of different cloud environments.

The complete instructions are below.

Prerequisites
-------------
To execute the code and simulate hybrid cloud environment, two cloud environments are required one is considered as private and the other public cloud.
On each cloud, following components should be running:
- Java 8
- Apache Maven
- Scala
- Spark in cluster mode
- Hadoop in cluster mode
- Unzip utility (optional - if transfering project in .zip to cloud from local) <br />
Refer `EnvironmentSetup.md` for detailed installation and execution instructions of each component on ubuntu machines.

Installation
------------
Before starting execution, verify that each machine is setup correctly using following steps

### Machine Setup:
* Login to each machine and verify:
  * Hostname is same as PUBLIC_DNS_NAME or INSTANCE_NAME
  * /etc/hosts file has entry in the form _private_ip_address public_dns_name_ [public ip causes spark start up issues]
  * Connection between machines in same cloud: `ping other's_hostname`
  * Hadoop and Spark are running, execute `jps` and check following processes should be running on Master: 
   `JobHistoryServer`
   `SecondaryNameNode`
   `NameNode`
   `ResourceManager`
   `Master`  [For Spark]
  And following processes on slaves:
   `DataNode`
   `NodeManager`
   `Worker` [For Spark]
  * ~/.bashrc has entry for JAVA_HOME, SCALA_HOME, SPARK_HOME, PATH, HADOOP_CONF, HADOOP_PREFIX, PATH
* Load Public cloud credentials into ssh-agent to transfer the data securely
  `eval \`ssh-agent\``
  `ssh-add hadoopmoc.pem`
  `ssh-add HybridEC2.pem`

### Executing Streaming Job:
* Copy HybridCloudStreaming project to private cloud.
  * To copy data from Local to Cloud, use following command with correct values: 
  `scp -i private_key.pem HybridCloudStreaming.zip login_user@ip_address:path`
* Navigate to the directory where project files are copied.
`cd HybridCloudStreaming`
* Update configuration files if required. Each property is described below for reference. 
  * oauth.consumerKey: Twitter developer API consumer key
  * oauth.consumerSecret: Twitter developer API consumer secret key
  * oauth.accessToken: Twitter developer API access token
  * oauth.accessTokenSecret: Twitter developer API secret access token
  * cloud.sensitivetags: twitter tags which are considered as sensitive
  * cloud.autoscale: size of sensitive data in bytes
  * cloud.autoscaletime: frequency of checking sensitive data size for autoscaling
  * public.cloud.login.user: User to ssh into public cloud
  * public.cloud.master.ip: IP address of master in public cloud 
  * data.sensitive.path: location of sensitive path, can be hdfs or disk storage location
    * Example: /home/ubuntu/Data_new/SensitiveTweets/ or hdfs://172.31.60.98:8020/user/ubuntu/SensitiveTweets/
  * data.non-sensitive.path: location of non-sensitive path, can be hdfs or disk storage location
    * Example: /home/ubuntu/Data_new/NonSensitiveTweets/ or hdfs://129.10.3.172:8020/user/ubuntu/NonSensitiveTweets/
* Compile the project
`mvn clean compile package`
* Copy jar from target to project root directory
`cp target/HybridCloudStreaming-1.0-jar-with-dependencies.jar ./HybridCloudStreaming.jar`
* Execute the code
```
$SPARK_HOME/bin/spark-submit --class neu.hybrid.HybridCloud HybridCloudStreaming.jar twitter4jproperties.txt sensitivedataconfiguration.txt
```

### Executing Analytical Job:
* Copy HybridCloudAnalytics project to private cloud.
  * To copy data from Local to Cloud, use following command with correct values: 
  `scp -i private_key.pem HybridCloudSAnalytics.zip login_user@ip_address:path`
* Navigate to the directory where project files are copied.
`cd HybridCloudAnalytics`
* Compile the project
`mvn clean compile package`
* Copy jar from target to project root directory
`cp target/HybridCloudAnalytics-0.0.1-SNAPSHOT.jar ./HybridCloudAnalytics.jar`
* Execute the code
  * LanguageBased:
  `hadoop jar HybridCloudAnalytics.jar neu.Hybrid.LanguageWiseTweetCount Data/SenstiveTweets output`
  * LocationBased:
  `hadoop jar HybridCloudAnalytics.jar neu.Hybrid.LocationWiseTweetCount Data/SenstiveTweets output`
