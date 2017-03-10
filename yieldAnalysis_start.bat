@echo off
SET BASE_DIR=D:/YieldAnalysis
SET CLASSPATH="./;D:/yieldanalysis/jar/ojdbc14.jar;D:/yieldanalysis/jar/log4j-1.2.15.jar;D:/yieldanalysis/jar/transceiverx-7.0.4.jar"
CD %BASE_DIR%/bin
echo PROCESS YieldAnalysis START
rem start /B java com.tptech.YieldAnalysis_Main
java com.tptech.YieldAnalysis_Main