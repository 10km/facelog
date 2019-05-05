# Access facelog on browser


facelog服务支持在浏览器上访问(javascript)

## javascript

javascript访问facelog服务的示例代码如下：

	<script src="lib/thrift.js"         type="text/javascript" charset="utf-8"></script>
	<script src="../jquery/IFaceLog.js" type="text/javascript" charset="utf-8"></script>
	<script src="../jquery/IFaceLog_types.js"       type="text/javascript" charset="utf-8"></script>
	
	<!-- jQuery -->
	<script src="https://cdn.bootcss.com/jquery/1.10.0/jquery.min.js"></script>
	
	
	<script>
	// 连接facelog XHR服务
	var transport = new Thrift.Transport("http://localhost:36411");
	var protocol  = new Thrift.Protocol(transport);
	var client    = new IFaceLogClient(protocol);
	
	</script>

	<script>
		// 调用version接口方法
		client.version((res)=>{
		  $('.hint-version').append('version:' + res ) 
		})
	</script>
	<div>
		RESPONSE:
		<p class="hint-version"></p>
	</div>


完整测试代码参见 

[facelog-client-ext/test_js/test.html](facelog-client-ext/test_js/test.html)

## XHR服务启动

自从2.1.0版本开始，facelog支持XHR(XML HTTP Request)服务启动。开启这个服务，才能支持javascript访问。

facelog服务启动时，默认启动XHR服务。

![](manual/images/xhr.png)



