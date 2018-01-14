# docker 部署

facelog支持docker容器部署,facelog docker 服务由三个容器组成:`redis`,`mysql`,`facelog service`,

 `facelog-service-${version}-distribution.zip`压缩包包含docker容器部署所有文件，解压缩后，在解压缩后的文件夹下执行相应的docker命令，即可完成容器部署操作。

**注意：**

facelog提供的所有`Dockerfile`和`docker-compose.yml`都没有指定volume本地挂载，项目部署时应该根据实际运行环境，配置volume

## Docker Compose服务部署

使用docker compose部署facelog服务非常简单，在当前文件夹下执行`docker-compose up -d`即完成服务部署。

创建,启动服务

	docker-compose up -d

启动服务

	docker-compose start

停止服务

    docker-compse stop

删除服务

	docker-compse rm

关于docker-compose的详细说明参见官方文档 [https://docs.docker.com/compose/reference/](https://docs.docker.com/compose/reference/)

## 部署容器

### redis

	docker run --name redis \ 
				-p 6379:6379 \
				-d \ 
				redis:4.0.6 --requirepass ${redis_password}

`--requirepass ${redis_password}` 用于指定redis访问密码AUTH

关于redis镜像的使用说明参见官方文档: [https://hub.docker.com/r/library/redis/](https://hub.docker.com/r/library/redis/)


### mysql

生成镜像：

	docker build -t db db/

启动容器，第一次容器启动时自动完成facelog数据库初始化：

	docker run --name db \
		-e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
		-e MYSQL_DATABASE=gdface \
		-d db:latest

关于msyql镜像的使用说明参见官方文档 [https://hub.docker.com/r/library/mysql/](https://hub.docker.com/r/library/mysql/)

### facelog service

生成镜像：

	docker build -t facelog_service facelog_service/

启动容器：

	docker run  --name facelog_service \
				# 指定 redis 主机名
				-e REDIS_HOST=${redis_host} \
				# 指定 数据库 主机名
				-e DATABASE_HOST=${database_host} \
				# 指定 数据库名
				-e DATABASE_SCHEMA=gdface \
				# 对外暴露端口 
				-p 26411:26411 \ 
				-d facelog_service:latest
**注意:**

上面的示例中只配置了基本的服务参数，更多参数说明参见下一节。

### facelog 镜像参数说明

facelog 镜像中提供一系列参数用于连接mysql和redis。项目部署时根据实际情况调整参数设置，具体如下(=号后为默认值)

	# 是否开启java远程调试端口，默认关闭，
	# 开发者使用，开启此端口，可以远程跟踪调试facelog 服务代码
	FACELOG_DEBUG=false
	# java远程调试端口 
	FACELOG_DEBUG_PORT=8000 
	# 服务端口
	SERVICE_PORT=26411 
	# redis 主机名/IP
	REDIS_HOST=localhost 
	# redis 端口号
	REDIS_PORT=6379 
	# redis 访问密码，默认为空
	REDIS_PASSWORD="" 
	# 数据库(mysql)主机名/IP
	DATABASE_HOST=localhost 
	# 数据库服务端口号
	DATABASE_PORT=3306 
	# 数据库名
	DATABASE_SCHEMA=test 
	# 数据库访问用户名
	DATABASE_USER=root 
	# 数据库访问用户密码，默认为空
	DATABASE_PASSWORD="" 

