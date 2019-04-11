echo off
rem ���� FaceApi cpp client����ű�
set sh_folder=%~dp0
rem ɾ������ '\'
set sh_folder=%sh_folder:~0,-1%
pushd "%sh_folder%"
set OUT_FOLDER=%sh_folder%\..\facelog-client-ext\node.js
:: ָ��thrift compilerλ��
where thrift >nul 2>nul
if errorlevel 1 (
	echo thrift NOT FOUND.
	exit /B -1
)
set THRIFT_EXE=thrift
if exist "%OUT_FOLDER%" (
	del  "%OUT_FOLDER%"\FaceApi*.c* >nul 2>nul
	del  "%OUT_FOLDER%"\FaceApi*.h* >nul 2>nul
	)
if not exist "%OUT_FOLDER%" mkdir  "%OUT_FOLDER%"

%THRIFT_EXE% --gen js:node^
	-out "%OUT_FOLDER%" ^
	%sh_folder%\IFaceLog.thrift 

popd
