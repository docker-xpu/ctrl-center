# 0、环境说明

centos7.5 x64

# 1、安装Docker

```bash
yum install docker
```

# 2、配置阿里云加速服务

```bash
sudo mkdir -p /etc/docker

sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://g7g0wjjq.mirror.aliyuncs.com"]
}
EOF

sudo systemctl daemon-reload

sudo systemctl restart docker
```

# 3、安装Docker Compose

下面是CDN加速下载的地址，直接运行命令即可：

```bash
curl -L https://get.daocloud.io/docker/compose/releases/download/1.25.4/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose

docker-compose -version #查看是否安装成功
```

# 4、开始之前的一些清理工作

因为本项目使用3306、27017、8080端口，所以如果之前有运行过MySQL的话可能会出现端口占用，如有必要，直接清理掉。

```bash
#停止所有Docker容器
docker stop $(docker ps -aq)

#删除所有Docker容器
docker rm $(docker ps -aq)

#删除所有Docker镜像
docker rmi $(docker images -q)
```

# 5、开始服务编排

在任意目录下载压缩包

```bash
wget http://img.zouchanglin.cn//mydocker/docker-compose-v1.zip
```

解压压缩包：unzip 

```bash
unzip docker-compose-v1.zip
```

进入解压目录

```bash
cd docker-compose-v1
```

开始服务编排

```bash
docker-compose up --build -d #-d选项是后台编排，如不需要使用Shell也可以不加-d
```

注意关闭防火墙

# 6、导入MYSQL文件

使用外部工具，如Navicat Premium 12连接MySQL（用户名是root、密码是123456），将mydocker.sql导入到mydocker数据库中即可。（但目前代码中还未用到数据库，可以不导入）

# 7、验证编排是否成功

浏览器输入虚拟机的地址，如我的CentOS的IP为192.168.2.2，那么访问

```http
http://192.168.2.2:8080/images/list
```

即可看到镜像文件列表！