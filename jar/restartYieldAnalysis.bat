@echo off
SET BASE_DIR=f:/workspace/BasisInfo
CD %BASE_DIR%/bin
SET CLASSPATH="./;f:/workspace/jar/ojdbc14.jar;f:/workspace/jar/tartool.jar;f:/workspace/jar/transceiverx-7.0.4.jar"
echo PROCESS  YieldAnalysis START
rem start /B java com.tptecht.YieldAnalysis_Main
java com.tptecht.YieldAnalysis_Main