echo off
rem 生成 FaceApi cpp client代码脚本(cpp)
set sh_folder=%~dp0
rem 删除最后的 '\'
set sh_folder=%sh_folder:~0,-1%
pushd "%sh_folder%"
set IDL=%sh_folder%\IFaceLog.mini.thrift
if NOT "%1"=="" ( set IDL=%1 )
set OUT_FOLDER=%sh_folder%\..\facelog-client-ext\cpp.mini
if NOT "%2"=="" ( set OUT_FOLDER=%2 )
:: 指定thrift compiler位置
where thrift >nul 2>nul
if errorlevel 1 (
	echo thrift NOT FOUND.
	exit /B -1
)
set THRIFT_EXE=thrift
if exist "%OUT_FOLDER%" (
	del  "%OUT_FOLDER%"\*.cpp >nul 2>nul
	)
if not exist "%OUT_FOLDER%" mkdir  "%OUT_FOLDER%"
%THRIFT_EXE% --gen cpp:no_skeleton,moveable_types,templates^
	-out "%OUT_FOLDER%" ^
	"%IDL%"

popd
