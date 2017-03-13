package com.tptech.isi;

import com.tptech.mes.H101ClientTest_end;
import org.apache.log4j.Logger;

import com.tptech.Hist_Variable;
import com.tptech.log.WriteLogger;
import com.tptech.mes.H101ClientTest_end;
import com.tptech.util.FileMoveUtil;
import com.tptech.yieldAnalysis_hist.YieldAnalysis_Hist_Dele;

public class SmtIsi_Process {

    private static Logger logger = WriteLogger.getInstance("SmtSbl_Process.java");

    
    Utils utils = new Utils();
    String location = FileMoveUtil.getDirpath("conSuccess") + "/" + Utils.getDate("MM", 0) + "Data";
    
    public void Sbl_Start(String FileName) {
        YieldAnalysis_Hist_Dele dele = new YieldAnalysis_Hist_Dele();

        String creatDate = utils.substringDate(FileName);
        
        if (FileName.contains("casi")) {
            for (int i = 0; i < Hist_Variable.casiList.size(); i++) {
                String[] casiData = Hist_Variable.casiList.get(i).split("\\s+");
                dele.insertGasi((i + 1), casiData[0], casiData[1], casiData[2].replaceAll(";", ""));
            }
            dele.insertRawData(location, FileName, creatDate, "CASI_BIN");
        } else if (FileName.contains("chipid")) {

        } else if (FileName.contains("mainbin")) {

            String testBin = "";
            String handlerBin = "";
            String opBin = "";

            // TEST_BIN
            int testBinListSize = Hist_Variable.testBinList.size();
            for (int i = 0; i < testBinListSize; i++) {
                testBin += Hist_Variable.testBinList.get(i);
                if (i != (testBinListSize - 1)) {
                    testBin += ",";
                }
            }

            // HANDLER_BIN
            int handlerBinListSize = Hist_Variable.handlerBinList.size();
            for (int i = 0; i < handlerBinListSize; i++) {
                handlerBin += Hist_Variable.handlerBinList.get(i);
                if (i != (handlerBinListSize - 1)) {
                    handlerBin += ",";
                }
            }

            // OP_BIN
            int opBinListSize = Hist_Variable.opBinList.size();
            for (int i = 0; i < opBinListSize; i++) {
                opBin += Hist_Variable.opBinList.get(i);
                if (i != (opBinListSize - 1)) {
                    opBin += ",";
                }
            }

            dele.insertMainBin(testBin, handlerBin, opBin);
            dele.insertRawData(location, FileName, creatDate, "MAIN_BIN");
            getMESInfo();
        } else if (FileName.contains("ngbin")) {

            String mainBin = "";
            String ngBin = "";

            // MAIN_BIN
            int subBinMainListSize = Hist_Variable.ngBinMainList.size();
            for (int i = 0; i < subBinMainListSize; i++) {
                mainBin += Hist_Variable.ngBinMainList.get(i);
                if (i != (subBinMainListSize - 1)) {
                    mainBin += ",";
                }
            }

            // NG_BIN
            int subBinListSize = Hist_Variable.ngBinList.size();
            for (int i = 0; i < subBinListSize; i++) {

                if (!Hist_Variable.ngBinList.get(i).contains("99999999")) {
                    ngBin += Hist_Variable.ngBinList.get(i) + ",";
                }
            }
            dele.insertNgBin(mainBin, ngBin);
            dele.insertRawData(location, FileName, creatDate, "NG_BIN");
        } else if (FileName.contains("cancel")) {
            for (int i = 0; i < Hist_Variable.cancelList.size(); i++) {
                String[] casiData = Hist_Variable.cancelList.get(i).split("\\s+");
                dele.insertCancel((i + 1), casiData[0], casiData[1], casiData[2].replaceAll(";", ""));
            }
        } else if (FileName.contains(".FLS")) {
            int indexSize = Hist_Variable.tdbi_bi_List.size();

            for (int i = 0; i < indexSize; i++) {
                String[] zon_split = Hist_Variable.tdbi_zon_List.get(i).split("[A-Z]");
                dele.insertTDBI((i + 1), Integer.parseInt(Hist_Variable.tdbi_bi_List.get(i).substring(0, 4)), Integer.parseInt(Hist_Variable.tdbi_bi_List.get(i)),
                        Integer.parseInt(zon_split[1]), Integer.parseInt(zon_split[2]),
                        Hist_Variable.tdbi_x_List.get(i), Hist_Variable.tdbi_y_List.get(i), Hist_Variable.tdbi_map_List.get(i));
            }
            dele.insertRawData(location, FileName, creatDate, "TDBI");
        }
    }
    public void getMESInfo(){
        H101ClientTest_end ct = new H101ClientTest_end();
        if(ct.InitMsgHandler() == false) {
            System.out.println("getMESInfo : false");
        }else {
            ct.ViewLot();
            ct.TermMsgHandler();
        }
        return;
    }
}


