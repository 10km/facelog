# facelog-client-android

与facelog-service通讯的client端实现,基于Microsoft/thrifty框架实现。适用于androi平台，也可以用于PC平台。短连接通讯方式。

[src/thrifty](src/thrifty)下的代码由[Microsoft/thrifty-compiler](https://github.com/Microsoft/thrifty/tree/master/thrifty-compiler)自动生成

[src/sql2java](src/sql2java)下的代码由sql2java自动生成

接口定义: [net.gdface.facelog.client.thrift.IFaceLog](src/thrift/java/net/gdface/facelog/client/thrift/IFaceLog.java)

同步接口调用封装:[net.gdface.facelog.client.IFaceLogClient](src/sql2java/java/net/gdface/facelog/client/IFaceLogClient.java)

异步接口调用封装:[net.gdface.facelog.client.IFaceLogClientAsync](src/sql2java/java/net/gdface/facelog/client/IFaceLogClientAsync.java)
