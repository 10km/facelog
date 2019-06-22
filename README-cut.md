# facelog接口裁剪

facelog是基于thrift RPC框架开发的,thrift框架支持广泛的开发语言，对于嵌入式平台可以生成C(c_glib)语言的stub代码，通过调用stub代码就可以访问facelog服务。

facelog目前接口定义的方法数量已经达到150个左右，如果将接口中所有方法全部生成C语言代码，则代码非常庞大，对于存储和运行空间都非常有限的嵌入式平台，这可能就是个致命的问题。

为了适应嵌入式平台，可以根据实际应用需要对facelog接口进行剪裁，只生成应用程序所需要的接口方法的代码。这样就可以大大减小程序的体积。本文说明如何对facelog接口裁剪并生成相应的stub代码(c_glib)。

## 接口剪裁定义

参照 [facelog-service/thrift.service.config.mini.properties](facelog-service/thrift.service.config.mini.properties)创建一个.properties文件(可以直接在此文件上修改)需要用到的facelog接口：
	

	net.gdface.facelog.IFaceLog.include=getFeatureBytes \
										getFeatures \
										getDevicesOfGroup \
										setPersonExpiryDate.TimeStr


1. net.gdface.facelog.IFaceLog.include用于定义正向白名单，在此名单中的接口方法才会被生成代码
2. net.gdface.facelog.IFaceLog.exclude用于定义负向黑名单，不在此名单中的接口方法才会被生成代码
3. 如果同时定义了include和exclude，则使用include,忽略exclude
4. 方法名单中定义的方法以空格/逗号/分号分隔,如果要分多行定义，则行尾使用‘\’分隔
5. 接口方法会有重载方法，即函数名相同参数不同，为了区分重载方法，facelog接口使用`@DeriveMethod`注释的`methodSuffix`属性来定义方法后缀名,所以如果接口方法有`@DeriveMethod`注释且定义了methodSuffix属性，则需要在方法名后用‘.’分隔加上`methodSuffix`的值，如上`setPersonExpiryDate`方法的定义

## 生成裁剪的thrift服务代码

执行[facelog-service/gen-decorator-service-mini.sh](facelog-service/gen-decorator-service-mini.sh)或[facelog-service/gen-decorator-service-mini.bat](facelog-service/gen-decorator-service-mini.bat)
会根据[facelog-service/thrift.service.config.mini.properties](facelog-service/thrift.service.config.mini.properties)定义的裁剪要求生成裁剪后的接口服务代码。

如果要使用你自定义的.properties文件在，可以在 `gen-decorator-service-mini.sh`或`gen-decorator-service-mini.bat` 后加上你的.properties文件名，如下：


	./gen-decorator-service-mini.sh $HOME/my.properties

生成的java代码位置在 facelog-service/src/codegen/java/net/gdface/facelog/decorator/mini

此时执行 `mvn install` 编译生成的代码供下一步使用

## 生成IDL

执行[facelog-service/gen_thrift_mini.bat](facelog-service/gen_thrift_mini.bat)或[facelog-service/gen_thrift_mini.sh](facelog-service/gen_thrift_mini.sh)生成thrift接口定义文件(IDL)。

如果执行脚本时不指定任何参数，则默认生成的IDL文件名为`facelog-service/IFaceLog.mini.thrift`

## 生成thrift CPP/C(c_glib) stub代码

执行[facelog-service/gen-client-c.mini.bat](facelog-service/gen-client-c.mini.bat)或[facelog-service/gen-client-c.mini.sh](facelog-service/gen-client-c.mini.sh)生成C stub代码。

执行[facelog-service/gen-client-cpp.mini.bat](facelog-service/gen-client-cpp.mini.bat)或[facelog-service/gen-client-cpp.mini.sh](facelog-service/gen-client-cpp.mini.sh)生成C++ stub代码。

如果执行脚本时不指定任何参数，则默认使用的IDL文件名为`facelog-service/IFaceLog.mini.thrift`，默认的C代码输出位置为 `facelog-client-ext/c.mini`,C++代码输出位置为 `facelog-client-ext/cpp.mini`

如果要自定义IDL文件位置和输出位置，可以在脚本后添加参数，第一个参数为IDL文件位置，第二个参数为代码输出位置(文件夹)，如：

	# 使用自定义的$HOME/my.cur.thrift IDL文件生成c stub代码，输出到$HOME/project/src/stub 
	gen-client-c.mini.sh $HOME/my.cur.thrift $HOME/project/src/stub 

## thrift compiler

上一步操作需要thrift compiler来完成，关于编译thrift compiler以及c_glib库，参见官方文档[http://thrift.apache.org/docs/install/](http://thrift.apache.org/docs/install/)

ubuntu下可以直接安装thrift-compiler使用

	sudo apt-get install thrift-compiler 


