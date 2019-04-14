@echo off
pushd %~dp0
call target\%~nx0 %*
popd