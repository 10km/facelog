java -cp lib\codegen-thrift-1.0.3-SNAPSHOT-standalone.jar;facelog-base\target\classes;db\facelog-db-base\target\classes;..\faceapi\faceapi-base\target\classes; ^
    net.gdface.codegen.thrift.ThriftServiceDecoratorGenerator ^
		-ic net.gdface.facelog.IFaceLog ^
		-rc net.gdface.facelog.FacelogDefaultImpl ^
		-p net.gdface.facelog.decorator ^
		-o facelog-thriftservice\src\service\java ^
		--thrift-package net.gdface.facelog.thrift.client ^
		-gt SERVICE