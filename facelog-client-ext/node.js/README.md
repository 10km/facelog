# node.js 调用facelog说明

facelog服务支持在node.js环境访问，node.js 端代码由thrift自动生成

参见：

[IFaceLog.js](IFaceLog.js)

[IFaceLog_types.js](IFaceLog_types.js)

## 安装所有依赖库

在当前文件夹下执行，如果node.js没有找到需要的依赖库会报错：

	npm install

## 调用示例

参见 [demo.js](demo.js)