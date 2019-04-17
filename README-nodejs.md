# Access facelog on node.js

facelog服务支持在node.js环境访问，示例代码如下：

	var thrift = require('thrift');
	
	var IFacelog = require('./IFaceLog.js'),
	ttypes = require('./IFaceLog_types.js');
	
	// 创建网络连接
	var connection = thrift.createConnection('localhost', 26411),
	
	// 创建facelog服务client端实例
	client = thrift.createClient(IFacelog, connection);
	
	connection.on('error', function(err) {
	  console.error(err);
	});

	// 调用version接口 
	client.version(function(err, res) {
	  if (err) {
	    console.error(err);
	  } else {
	    console.log('fcelog version',res)
	  }
	})



完整测试代码及说明,参见 

[facelog-client-ext/node.js/README.md](facelog-client-ext/node.js/README.md)
