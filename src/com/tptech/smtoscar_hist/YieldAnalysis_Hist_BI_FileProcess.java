package com.tptech.smtoscar_hist;import java.util.Vector;import org.apache.log4j.Logger;import com.tptech.Hist_Variable;import com.tptech.log.WriteLogger;public class YieldAnalysis_Hist_BI_FileProcess {    private static Logger logger = WriteLogger.getInstance("YieldAnalysis_Hist_BI_FileProcess.java");    public boolean FileProcess(String File, Vector<?> vFileLine) {        int iFileLine = vFileLine.size();        boolean BoardMapStart = false;        boolean mapStart = false;        String boardMapAppand = "";                for (int i = 0; i < iFileLine; i++) {            String data = (String) vFileLine.elementAt(i);            String[] biReport = data.split("\\s+");            for (int j = 0; j < biReport.length; j++) {                if (biReport[j].contains("MAP")) {                    BoardMapStart = true;                    continue;                }                if (BoardMapStart) {                    if (biReport[j].matches("START-TIME")) {                        Hist_Variable.tdbiMap.put("START-TIME", biReport[j + 1] + " " + biReport[j + 2]);                        continue;                    }                    if (biReport[j].matches("END-TIME")) {                        Hist_Variable.tdbiMap.put("END-TIME", biReport[j + 1] + " " + biReport[j + 2]);                        continue;                    }                    if (biReport[j].matches("PARTS")) {                        Hist_Variable.tdbiMap.put("PARTS", biReport[j + 1]);                        continue;                    }                    if (biReport[j].matches("LOTNO")) {                        Hist_Variable.tdbiMap.put("LOTNO", biReport[j + 1]);                        continue;                    }                    if (biReport[j].matches("SUBLOT")) {                        Hist_Variable.tdbiMap.put("SUBLOT", biReport[j + 1]);                        continue;                    }                    if (biReport[j].matches("TP")) {                        String[] tp_split = biReport[j + 1].split("\\.");                        Hist_Variable.tdbiMap.put("TP", tp_split[0]);                        continue;                    }                    if (biReport[j].matches("STATION")) {                        String[] station_split = biReport[j + 1].split("-");                        Hist_Variable.tdbiMap.put("BI_TESTER_MODEL", station_split[0]);                        Hist_Variable.tdbiMap.put("BI_TEST_NUMBER",  station_split[1]);                        continue;                    }                    if (biReport[j].matches("CHAMBER1")) {                        Hist_Variable.tdbiMap.put("CHAMBER1", biReport[j + 1]);                        continue;                    }                    if (biReport[j].matches("CLOSE")) {                        Hist_Variable.tdbiMap.put("CLOSE", biReport[j + 1]);                        continue;                    }                    if (biReport[j].matches("OID")) {                        Hist_Variable.tdbiMap.put("OID", "NULL");                        continue;                    }                    if (biReport[j].matches("HISTORY")) {                        BoardMapStart = false;                        mapStart = false;                        break;                    }                    if (biReport[j].matches("BID")) {                        mapStart = true;                        break;                    }                }            }            if (mapStart) {                boardMapAppand += data + ",";            }        }        String[] boardMapArry = boardMapAppand.split("BID");        for (int j = 0; j < boardMapArry.length; j++) {            if (!boardMapArry[j].isEmpty()) {                String[] boardMapArryComma = boardMapArry[j].split(",");                /*                 * ******************************* BID / ZON 데이터_s *****************************/                String[] bid_zon_Data = boardMapArryComma[0].split("\\s+");                for (int t = 0; t < bid_zon_Data.length; t++) {                    if (!bid_zon_Data[t].isEmpty()) {                        Hist_Variable.tdbi_bi_List.add(bid_zon_Data[t]);                        Hist_Variable.tdbi_zon_List.add(bid_zon_Data[t+1]);                        break;                    }                }                /*                 ******************************* BID / ZON 데이터_n *****************************/                /*                 * ******************************* BOARD MAP 해더 추출_s *****************************/                String[] x_low = boardMapArryComma[1].split("\\s+");                int x_lowLength = x_low.length;                String x_low_data = "";                for (int q = 0; q < x_low.length; q++) {                    if (!x_low[q].isEmpty()) {                        x_low_data += x_low[q] + ",";                         break;                    }                }                x_low_data += x_low[x_lowLength - 1];                Hist_Variable.tdbi_x_List.add(x_low_data);                /*                 * ******************************* BOARD MAP 해더 추출_n *****************************/                /*                 * ******************************* BOARD MAP 바디 추출_s *****************************/                                String mapLowData = "";                boolean yLowData = true;                int yLow = 0;                String y_low_data = "";                                for (int a = 2; a < boardMapArryComma.length; a++) {                    String[] y_low = boardMapArryComma[a].split("\\s+");                    boolean yData = false;                                        for (int sy = 0; sy < y_low.length; sy++) {                        if (!y_low[sy].isEmpty()) {                            if (yData) {                                mapLowData += y_low[sy];                            }                            if (yLowData) {                                yLowData = false;                                yData = true;                                yLow++;                            }                            if (yLow == 1) {                                y_low_data = y_low[sy] + ",";                                 yLow++;                            }                                                        if (yLow == (boardMapArryComma.length-1)) {                                y_low_data += y_low[sy];                                 yLow++;                            }                        }                    }                    mapLowData += ",";                    yData = false;                    yLowData = true;                }                Hist_Variable.tdbi_y_List.add(y_low_data);                Hist_Variable.tdbi_map_List.add(mapLowData);                /*                 * ******************************* BOARD MAP 바디 추출_n *****************************/            }        }        return true;    }}