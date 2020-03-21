# ctrl-center
This is controller for my-docker engine

# MongoDB安装
CentOS 7.5_x64
先下载安装包
```bash
wget http://img.zouchanglin.cn/mongodb-linux-x86_64-rhel70-4.2.3.tgz

tar -zxvf mongodb-linux-x86_64-rhel70-4.2.3.tgz

mv mongodb-linux-x86_64-rhel70-4.2.3 /usr/local

cd /usr/local/mongodb-linux-x86_64-rhel70-4.2.3

cd ..

mv mongodb-linux-x86_64-rhel70-4.2.3 mongodb

cd mongodb

mkdir db

mkdir logs

chmod 777 ./db

chmod 777 ./logs

touch ./logs/mongo.log

vim mongo.conf

port=27017 #端口
dbpath= /usr/local/mongodb/db
logpath= /usr/local/mongodb/log/mongodb.log
logappend=true
fork=true
maxConns=100
noauth=true
journal=true
storageEngine=wiredTiger
bind_ip = 0.0.0.0

ln -s /usr/local/mongodb/bin/mongod /usr/bin/mongod
```

启动MongoDB
```bash
mongod -f /usr/local/mongodb/mongodb.conf 
```

# 项目启动流程
1、开启内网穿透

2、开启MongoDB

3、开启Redis

4、开启MySQL

5、开启MyDocker，注意配置6060端口(以及私服地址配置)

6、启动SpringBoot