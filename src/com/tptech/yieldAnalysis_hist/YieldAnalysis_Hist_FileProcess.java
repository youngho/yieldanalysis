package com.tptech.yieldAnalysis_hist;import java.util.ArrayList;import java.util.HashMap;import java.util.List;import java.util.Map;import java.util.Vector;import org.apache.log4j.Logger;import com.tptech.Hist_Variable;import com.tptech.log.WriteLogger;public class YieldAnalysis_Hist_FileProcess {    private static Logger logger = WriteLogger.getInstance("YieldAnalysis_Hist_FileProcess.java");    public boolean FileProcess(String File, Vector<?> vFileLine) {        int iFileLine = vFileLine.size();        int casiBinCount = -1;        int subBinCount = -1;        int mainBinCount = -1;        int subBinFileDataCount = 1;        for (int i = 0; i < iFileLine; i++) {            String data = (String) vFileLine.elementAt(i);            if (File.contains("casi")) {                if (data.contains("=")) {                    // 해더 부분 추출                    String[] headData = data.split("=");                    Hist_Variable.casiMap.put(headData[0], headData[1].trim());                } else {                    if (data.contains("</")) {                        continue;                    } else if (data.contains("<")) {                        casiBinCount++;                    } else {                        if (casiBinCount == 0) { // CASI                            Hist_Variable.casiList.add(data);                        }                    }                }            } else if (File.contains("chipid")) {                if (data.contains("=")) {                    // 해더 부분 추출                    String[] headData = data.split("=");                    Hist_Variable.chipId.put(headData[0], headData[1].trim());                } else {                    // 일단 칩데이터는 하이닉스에서 줘야지만 사용할수 있기 때문에 사황을 보고 추후에 개발                }            } else if (File.contains("mainbin")) {                if (data.contains("=")) {                    // 해더 부분 추출                    String[] headData = data.split("=");                    Hist_Variable.mainMap.put(headData[0], headData[1].trim());                } else {                    if (data.contains("</")) {                        continue;                    } else if (data.contains("<")) {                        mainBinCount++;                    } else {                        if (mainBinCount == 0) { // TEST_BIN                            String[] values = data.split("\\s+");                            for (int x = 0; x < values.length; x++) {                                Hist_Variable.testBinList.add(values[x]);                            }                        } else if (mainBinCount == 1) { // HANDLER_BIN                            String[] values = data.split("\\s+");                            for (int x = 0; x < values.length; x++) {                                Hist_Variable.handlerBinList.add(values[x]);                            }                        } else if (mainBinCount == 2) { // OP_BIN                             String[] values = data.split("\\s+");                            for (int x = 0; x < values.length; x++) {                                Hist_Variable.opBinList.add(values[x]);                            }                        }                    }                }            } else if (File.contains("ngbin")) {                if (data.contains("=")) {                    // 해더 부분 추출                    String[] headData = data.split("=");                    Hist_Variable.ngMap.put(headData[0], headData[1].trim());                } else {                    if (data.contains("</")) {                        continue;                    } else if (data.contains("<")) {                        subBinCount++;                    } else {                        if (subBinCount == 0) {                            // main_bin 결과                            String[] values = data.split("\\s+");                            for (int x = 0; x < values.length; x++) {                                Hist_Variable.ngBinMainList.add(values[x]);                            }                        } else if (subBinCount == 1) {                            // ng_bin 결과                            String[] values = data.split("\\s+");                            for (int x = 1; x < values.length; x += 2) {                                int conver = Integer.parseInt(values[x]) == 0 ? 99999999 : Integer.parseInt(values[x]);                                Hist_Variable.ngBinList.add(subBinFileDataCount + "," + conver);                                subBinFileDataCount++;                            }                        } else if (subBinCount == 2) {                            // dut bin 결과 ==> gasi 데이터에 들어가 있음..                        }                    }                }            } else if (File.contains("cancel")) {                if (data.contains("=")) {                    // 해더 부분 추출                    String[] headData = data.split("=");                    Hist_Variable.cancelMap.put(headData[0], headData[1].trim());                } else {                    if (data.contains("</")) {                        continue;                    } else if (data.contains("<")) {                        casiBinCount++;                    } else {                        if (casiBinCount == 0) { // cancel                            Hist_Variable.cancelList.add(data);                        }                    }                }            }        }        return true;    }}