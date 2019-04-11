@rem java -cp lib\codegen-thrift-1.0.5-standalone.jar;..\facelog-base\target\classes;..\db\facelog-db-base\target\classes; ^
@rem     net.gdface.codegen.thrift.ThriftServiceDecoratorGenerator ^
@rem 		-ic net.gdface.facelog.IFaceLog ^
@rem 		-rc net.gdface.facelog.FacelogDefaultImpl ^
@rem 		-p net.gdface.facelog.decorator ^
@rem 		-o src\codegen\java ^
@rem 		--thrift-package net.gdface.facelog.thrift.client ^
@rem 		-gt SERVICE
		
@rem 生成接口的基于facebook/swift的service端实现代码(decorator)
@set sh_folder=%~dp0
@pushd %sh_folder%
@set OUT_FOLDER=src\codegen\java
@if exist "%OUT_FOLDER%" rmdir  %OUT_FOLDER% /s/q
call mvn com.gitee.l0km:codegen-thrift-maven-plugin:generate
@popd