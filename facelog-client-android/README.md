# facelog-client-android

与facelog-service通讯的client端实现,基于Microsoft/thrifty框架实现，适用于android平台(也可以用于x86平台)

[src/thrift](src/thrift)下的代码由thrifty-compiler自动生成

[src/codegen](src/codegen)下的代码由codegen自动生成

同步接口调用封装:[net.gdface.facelog.client.IFaceLogClient](src/main/java/net/gdface/facelog/client/IFaceLogClient.java)

异步接口调用封装:[net.gdface.facelog.client.IFaceLogClientAsync](src/main/java/net/gdface/facelog/client/IFaceLogClientAsync.java)
