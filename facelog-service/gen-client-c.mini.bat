echo off
rem ���� FaceApi cpp client����ű�(c)
set sh_folder=%~dp0
rem ɾ������ '\'
set sh_folder=%sh_folder:~0,-1%
pushd "%sh_folder%"
set IDL=%sh_folder%\IFaceLog.mini.thrift
if NOT "%1"=="" ( set IDL=%1 )
set OUT_FOLDER=%sh_folder%\..\facelog-client-ext\c.mini
:: ָ��thrift compilerλ��
where thrift >nul 2>nul
if errorlevel 1 (
	echo thrift NOT FOUND.
	exit /B -1
)
set THRIFT_EXE=thrift
if exist "%OUT_FOLDER%" (
	del  "%OUT_FOLDER%"\*.c >nul 2>nul
	)
if not exist "%OUT_FOLDER%" mkdir  "%OUT_FOLDER%"

%THRIFT_EXE% --gen c_glib^
	-out "%OUT_FOLDER%" ^
	"%IDL%"

popd
