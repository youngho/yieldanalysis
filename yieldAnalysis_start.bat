@echo off
SET BASE_DIR=D:/YieldAnalysis
CD %BASE_DIR%/bin
SET CLASSPATH="D:/jar/ojdbc14.jar;D:/jar/log4j-1.2.15.jar;D:/YieldAnalysis/bin"
echo PROCESS YieldAnalysis START
rem start /B java com.tptech.YieldAnalysis_Main
java com.tptech.YieldAnalysis_Main