# facelog-client

与facelog-service通讯的client端实现

[src/thrift](src/thrift)下的代码由swift自动生成

[src/sql2java](src/sql2java)下的代码由sql2java自动生成

接口定义: [net.gdface.facelog.client.thrift.IFaceLog](src/thrift/java/net/gdface/facelog/client/thrift/IFaceLog.java)

同步接口调用封装:[net.gdface.facelog.client.IFaceLogClient](src/sql2java/java/net/gdface/facelog/client/IFaceLogClient.java)

异步接口调用封装:[net.gdface.facelog.client.IFaceLogClientAsync](src/sql2java/java/net/gdface/facelog/client/IFaceLogClientAsync.java)
