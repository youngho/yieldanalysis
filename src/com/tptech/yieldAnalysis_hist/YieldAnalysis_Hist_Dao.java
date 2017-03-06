package com.tptech.yieldAnalysis_hist;import org.apache.log4j.Logger;import com.tptech.GlobalDao;import com.tptech.Hist_Variable;import com.tptech.log.WriteLogger;import com.tptech.util.Utils;public class YieldAnalysis_Hist_Dao {    private static Logger logger = WriteLogger.getInstance("YieldAnalysis_Hist_Dao.java");    /**     * @uml.property name="util"     * @uml.associationEnd     */    Utils util = new Utils();    public boolean insertGasi(int seq, String shotStartTime, String shotEndTime, String dutMainBin) {        StringBuffer sSQL = new StringBuffer();        int size = 0;        try {                        sSQL.append("  INSERT                                                               \n");            sSQL.append("  INTO CASI_BIN                                                        \n");            sSQL.append("    (                                                                  \n");            sSQL.append("      CASI_SEQ                                                         \n");            sSQL.append("      ,TESTER_NUMBER                                                   \n");            sSQL.append("      ,TESTER_MODEL                                                    \n");            sSQL.append("      ,HANDLER_MODEL                                                   \n");            sSQL.append("      ,HEAD                                                            \n");            sSQL.append("      ,LOT_ID                                                          \n");            sSQL.append("      ,PROCESS_CODE                                                    \n");            sSQL.append("      ,PART_NUMBER                                                     \n");            sSQL.append("      ,MAIN_PROGRAM_NAME                                               \n");            sSQL.append("      ,GRADE                                                           \n");            sSQL.append("      ,FAB                                                             \n");            sSQL.append("      ,FIRMWARE_NAME                                                   \n");            sSQL.append("      ,FIRMWARE_VERSION                                                \n");            sSQL.append("      ,TEMPERATURE                                                     \n");            sSQL.append("      ,OPERATOR_ID                                                     \n");            sSQL.append("      ,QUANTITY                                                        \n");            sSQL.append("      ,FUNCTION_KEY                                                    \n");            sSQL.append("      ,TEST_COUNTER                                                    \n");            sSQL.append("      ,TEST_INPUT                                                      \n");            sSQL.append("      ,TEST_FLOW                                                       \n");            sSQL.append("      ,REWORK_FLAG                                                     \n");            sSQL.append("      ,TEST_MODE                                                       \n");            sSQL.append("      ,BOARD_ID                                                        \n");            sSQL.append("      ,PRELOT_END_TIME                                                 \n");            sSQL.append("      ,LOT_IN_TIME                                                     \n");            sSQL.append("      ,BETS_IN_TIME                                                    \n");            sSQL.append("      ,BETS_END_TIME                                                   \n");            sSQL.append("      ,LOT_IN_END_TIME                                                 \n");            sSQL.append("      ,LOT_START_TIME                                                  \n");            sSQL.append("      ,END_TIME                                                        \n");            sSQL.append("      ,BIN_IN_TIME                                                     \n");            sSQL.append("      ,BIN_END_TIME                                                    \n");            sSQL.append("      ,SBL_IN_TIME                                                     \n");            sSQL.append("      ,SBL_END_TIME                                                    \n");            sSQL.append("      ,FINAL_END_TIME                                                  \n");            sSQL.append("      ,SBL_RESULT                                                      \n");            sSQL.append("      ,SBL_YIELD_LIMIT                                                 \n");            sSQL.append("      ,SBL_SUB_BINA_COUNTER                                            \n");            sSQL.append("      ,SBL_SUB_BINA_LIMIT                                              \n");            sSQL.append("      ,SBL_SUB_BINB_COUNTER                                            \n");            sSQL.append("      ,SBL_SUB_BINB_LIMIT                                              \n");            sSQL.append("      ,SBL_BIN9_COUNTER                                                \n");            sSQL.append("      ,SBL_BIN9_LIMIT                                                  \n");            sSQL.append("      ,SHOT_START_TIME                                                 \n");            sSQL.append("      ,SHOT_END_TIME                                                   \n");            sSQL.append("      ,DUT_MAIN_BIN                                                    \n");            sSQL.append("    )                                                                  \n");            sSQL.append("    VALUES                                                             \n");            sSQL.append("    (                                                                  \n");            sSQL.append("        "+ seq +"                                                      \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("TESTER_NUMBER") +"'              \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("TESTER_MODEL") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("HANDLER_MODEL") +"'              \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("HEAD") +"'                       \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("LOT_ID") +"'                     \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("PROCESS_CODE") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("PART_NUMBER") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("MAIN_PROGRAM_NAME") +"'          \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("GRADE") +"'                      \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("FAB") +"'                        \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("FIRMWARE_NAME") +"'              \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("FIRMWARE_VERSION") +"'           \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("TEMPERATURE") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("OPERATOR_ID") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("QUANTITY") +"'                   \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("FUNCTION_KEY") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("TEST_COUNTER") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("TEST_INPUT") +"'                 \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("TEST_FLOW") +"'                  \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("REWORK_FLAG") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("TEST_MODE") +"'                  \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("BOARD_ID") +"'                   \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("PRELOT_END_TIME") +"'            \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("LOT_IN_TIME") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("BETS_IN_TIME") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("BETS_END_TIME") +"'              \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("LOT_IN_END_TIME") +"'            \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("LOT_START_TIME") +"'             \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("END_TIME") +"'                   \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("BIN_IN_TIME") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("BIN_END_TIME") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("SBL_IN_TIME") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("SBL_END_TIME") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("FINAL_END_TIME") +"'             \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("SBL_RESULT") +"'                 \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("SBL_YIELD_LIMIT") +"'            \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("SBL_SUB_BINA_COUNTER") +"'       \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("SBL_SUB_BINA_LIMIT") +"'         \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("SBL_SUB_BINB_COUNTER") +"'       \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("SBL_SUB_BINB_LIMIT") +"'         \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("SBL_BIN9_COUNTER") +"'           \n");            sSQL.append("      ,'"+ Hist_Variable.casiMap.get("SBL_BIN9_LIMIT") +"'             \n");            sSQL.append("      ,'"+ shotStartTime +"'                                           \n");            sSQL.append("      ,'"+ shotEndTime +"'                                             \n");            sSQL.append("      ,'"+ dutMainBin +"'                                              \n");            sSQL.append("    )                                                                  \n");                            size = GlobalDao.executeUpdate(sSQL.toString());                        if (size > 0) {                return true;            } else {                return false;            }        } catch (Exception e) {            logger.error(e.toString());            return false;        }    }        public boolean insertNgBin(String mainBin, String subBin) {        StringBuffer sSQL = new StringBuffer();        int size = 0;        try {                        sSQL.append("  INSERT                                                             \n");            sSQL.append("  INTO NG_BIN                                                        \n");            sSQL.append("    (                                                                \n");            sSQL.append("      TESTER_NUMBER                                                  \n");            sSQL.append("      ,TESTER_MODEL                                                  \n");            sSQL.append("      ,HANDLER_MODEL                                                 \n");            sSQL.append("      ,HEAD                                                          \n");            sSQL.append("      ,LOT_ID                                                        \n");            sSQL.append("      ,PROCESS_CODE                                                  \n");            sSQL.append("      ,PART_NUMBER                                                   \n");            sSQL.append("      ,MAIN_PROGRAM_NAME                                             \n");            sSQL.append("      ,GRADE                                                         \n");            sSQL.append("      ,FAB                                                           \n");            sSQL.append("      ,FIRMWARE_NAME                                                 \n");            sSQL.append("      ,FIRMWARE_VERSION                                              \n");            sSQL.append("      ,TEMPERATURE                                                   \n");            sSQL.append("      ,OPERATOR_ID                                                   \n");            sSQL.append("      ,QUANTITY                                                      \n");            sSQL.append("      ,FUNCTION_KEY                                                  \n");            sSQL.append("      ,TEST_COUNTER                                                  \n");            sSQL.append("      ,TEST_INPUT                                                    \n");            sSQL.append("      ,TEST_FLOW                                                     \n");            sSQL.append("      ,REWORK_FLAG                                                   \n");            sSQL.append("      ,TEST_MODE                                                     \n");            sSQL.append("      ,BOARD_ID                                                      \n");            sSQL.append("      ,PRELOT_END_TIME                                               \n");            sSQL.append("      ,LOT_IN_TIME                                                   \n");            sSQL.append("      ,BETS_IN_TIME                                                  \n");            sSQL.append("      ,BETS_END_TIME                                                 \n");            sSQL.append("      ,LOT_IN_END_TIME                                               \n");            sSQL.append("      ,LOT_START_TIME                                                \n");            sSQL.append("      ,END_TIME                                                      \n");            sSQL.append("      ,BIN_IN_TIME                                                   \n");            sSQL.append("      ,BIN_END_TIME                                                  \n");            sSQL.append("      ,SBL_IN_TIME                                                   \n");            sSQL.append("      ,SBL_END_TIME                                                  \n");            sSQL.append("      ,FINAL_END_TIME                                                \n");            sSQL.append("      ,SBL_RESULT                                                    \n");            sSQL.append("      ,SBL_YIELD_LIMIT                                               \n");            sSQL.append("      ,SBL_SUB_BINA_COUNTER                                          \n");            sSQL.append("      ,SBL_SUB_BINA_LIMIT                                            \n");            sSQL.append("      ,SBL_SUB_BINB_COUNTER                                          \n");            sSQL.append("      ,SBL_SUB_BINB_LIMIT                                            \n");            sSQL.append("      ,SBL_BIN9_COUNTER                                              \n");            sSQL.append("      ,SBL_BIN9_LIMIT                                                \n");            sSQL.append("      ,MAIN_BIN                                                      \n");            sSQL.append("      ,NG_BIN                                                        \n");            sSQL.append("    )                                                                \n");            sSQL.append("    VALUES                                                           \n");            sSQL.append("    (                                                                \n");             sSQL.append("      '"+ Hist_Variable.ngMap.get("TESTER_NUMBER") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("TESTER_MODEL") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("HANDLER_MODEL") +"'              \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("HEAD") +"'                       \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("LOT_ID") +"'                     \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("PROCESS_CODE") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("PART_NUMBER") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("MAIN_PROGRAM_NAME") +"'          \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("GRADE") +"'                      \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("FAB") +"'                        \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("FIRMWARE_NAME") +"'              \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("FIRMWARE_VERSION") +"'           \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("TEMPERATURE") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("OPERATOR_ID") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("QUANTITY") +"'                   \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("FUNCTION_KEY") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("TEST_COUNTER") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("TEST_INPUT") +"'                 \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("TEST_FLOW") +"'                  \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("REWORK_FLAG") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("TEST_MODE") +"'                  \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("BOARD_ID") +"'                   \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("PRELOT_END_TIME") +"'            \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("LOT_IN_TIME") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("BETS_IN_TIME") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("BETS_END_TIME") +"'              \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("LOT_IN_END_TIME") +"'            \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("LOT_START_TIME") +"'             \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("END_TIME") +"'                   \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("BIN_IN_TIME") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("BIN_END_TIME") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("SBL_IN_TIME") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("SBL_END_TIME") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("FINAL_END_TIME") +"'             \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("SBL_RESULT") +"'                 \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("SBL_YIELD_LIMIT") +"'            \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("SBL_SUB_BINA_COUNTER") +"'       \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("SBL_SUB_BINA_LIMIT") +"'         \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("SBL_SUB_BINB_COUNTER") +"'       \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("SBL_SUB_BINB_LIMIT") +"'         \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("SBL_BIN9_COUNTER") +"'           \n");            sSQL.append("      ,'"+ Hist_Variable.ngMap.get("SBL_BIN9_LIMIT") +"'             \n");            sSQL.append("      ,'"+ mainBin +"'                                               \n");            sSQL.append("      ,'"+ subBin +"'                                                \n");            sSQL.append("    )                                                                \n");                        System.out.println("sSQL =============> " + sSQL);                        size = GlobalDao.executeUpdate(sSQL.toString());                        if (size > 0) {                return true;            } else {                return false;            }        } catch (Exception e) {            logger.error(e.toString());            return false;        }    }        public boolean insertMainBin(String testBin, String handlerBin, String opBin) {        StringBuffer sSQL = new StringBuffer();        int size = 0;        try {                        sSQL.append("   INSERT                                                            \n");            sSQL.append("   INTO MAIN_BIN                                                     \n");            sSQL.append("     (                                                               \n");            sSQL.append("        TESTER_NUMBER                                                \n");            sSQL.append("       ,TESTER_MODEL                                                 \n");            sSQL.append("       ,HANDLER_MODEL                                                \n");            sSQL.append("       ,HEAD                                                         \n");            sSQL.append("       ,LOT_ID                                                       \n");            sSQL.append("       ,PROCESS_CODE                                                 \n");            sSQL.append("       ,PART_NUMBER                                                  \n");            sSQL.append("       ,MAIN_PROGRAM_NAME                                            \n");            sSQL.append("       ,GRADE                                                        \n");            sSQL.append("       ,FAB                                                          \n");            sSQL.append("       ,FIRMWARE_NAME                                                \n");            sSQL.append("       ,FIRMWARE_VERSION                                             \n");            sSQL.append("       ,TEMPERATURE                                                  \n");            sSQL.append("       ,OPERATOR_ID                                                  \n");            sSQL.append("       ,QUANTITY                                                     \n");            sSQL.append("       ,FUNCTION_KEY                                                 \n");            sSQL.append("       ,TEST_COUNTER                                                 \n");            sSQL.append("       ,TEST_INPUT                                                   \n");            sSQL.append("       ,TEST_FLOW                                                    \n");            sSQL.append("       ,REWORK_FLAG                                                  \n");            sSQL.append("       ,TEST_MODE                                                    \n");            sSQL.append("       ,BOARD_ID                                                     \n");            sSQL.append("       ,PRELOT_END_TIME                                              \n");            sSQL.append("       ,LOT_IN_TIME                                                  \n");            sSQL.append("       ,BETS_IN_TIME                                                 \n");            sSQL.append("       ,BETS_END_TIME                                                \n");            sSQL.append("       ,LOT_IN_END_TIME                                              \n");            sSQL.append("       ,LOT_START_TIME                                               \n");            sSQL.append("       ,END_TIME                                                     \n");            sSQL.append("       ,BIN_IN_TIME                                                  \n");            sSQL.append("       ,BIN_END_TIME                                                 \n");            sSQL.append("       ,SBL_IN_TIME                                                  \n");            sSQL.append("       ,SBL_END_TIME                                                 \n");            sSQL.append("       ,FINAL_END_TIME                                               \n");            sSQL.append("       ,SBL_RESULT                                                   \n");            sSQL.append("       ,SBL_YIELD_LIMIT                                              \n");            sSQL.append("       ,SBL_SUB_BINA_COUNTER                                         \n");            sSQL.append("       ,SBL_SUB_BINA_LIMIT                                           \n");            sSQL.append("       ,SBL_SUB_BINB_COUNTER                                         \n");            sSQL.append("       ,SBL_SUB_BINB_LIMIT                                           \n");            sSQL.append("       ,SBL_BIN9_COUNTER                                             \n");            sSQL.append("       ,SBL_BIN9_LIMIT                                               \n");            sSQL.append("       ,TEST_BIN                                                     \n");            sSQL.append("       ,HANDLER_BIN                                                  \n");            sSQL.append("       ,OP_BIN                                                       \n");            sSQL.append("     )                                                               \n");            sSQL.append("     VALUES                                                          \n");            sSQL.append("     (                                                               \n");            sSQL.append("      '"+ Hist_Variable.mainMap.get("TESTER_NUMBER") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("TESTER_MODEL") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("HANDLER_MODEL") +"'              \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("HEAD") +"'                       \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("LOT_ID") +"'                     \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("PROCESS_CODE") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("PART_NUMBER") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("MAIN_PROGRAM_NAME") +"'          \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("GRADE") +"'                      \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("FAB") +"'                        \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("FIRMWARE_NAME") +"'              \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("FIRMWARE_VERSION") +"'           \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("TEMPERATURE") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("OPERATOR_ID") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("QUANTITY") +"'                   \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("FUNCTION_KEY") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("TEST_COUNTER") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("TEST_INPUT") +"'                 \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("TEST_FLOW") +"'                  \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("REWORK_FLAG") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("TEST_MODE") +"'                  \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("BOARD_ID") +"'                   \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("PRELOT_END_TIME") +"'            \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("LOT_IN_TIME") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("BETS_IN_TIME") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("BETS_END_TIME") +"'              \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("LOT_IN_END_TIME") +"'            \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("LOT_START_TIME") +"'             \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("END_TIME") +"'                   \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("BIN_IN_TIME") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("BIN_END_TIME") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("SBL_IN_TIME") +"'                \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("SBL_END_TIME") +"'               \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("FINAL_END_TIME") +"'             \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("SBL_RESULT") +"'                 \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("SBL_YIELD_LIMIT") +"'            \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("SBL_SUB_BINA_COUNTER") +"'       \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("SBL_SUB_BINA_LIMIT") +"'         \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("SBL_SUB_BINB_COUNTER") +"'       \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("SBL_SUB_BINB_LIMIT") +"'         \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("SBL_BIN9_COUNTER") +"'           \n");            sSQL.append("      ,'"+ Hist_Variable.mainMap.get("SBL_BIN9_LIMIT") +"'             \n");            sSQL.append("      ,'"+ testBin +"'                                               \n");            sSQL.append("      ,'"+ handlerBin +"'                                            \n");            sSQL.append("      ,'"+ opBin+"'                                                  \n");            sSQL.append("     )                                                               \n");                        size = GlobalDao.executeUpdate(sSQL.toString());                        if (size > 0) {                return true;            } else {                return false;            }        } catch (Exception e) {            logger.error(e.toString());            return false;        }    }        public boolean insertTDBI(int biSEQ, int biProcessCode, int biBoardId, int biZoneNumber,             int biSlotNumber, String biSocketX, String biSocketY, String biSocketNumber) {        StringBuffer sSQL = new StringBuffer();        int size = 0;        try {                        sSQL.append("    INSERT                                                         \n");            sSQL.append("    INTO BURN_IN_BOARD_MAP                                         \n");            sSQL.append("      (                                                            \n");            sSQL.append("        BI_SEQ                                                     \n");            sSQL.append("        ,BI_LOT_ID                                                 \n");            sSQL.append("        ,BI_MAIN_PROGRAM_NAME                                      \n");            sSQL.append("        ,BI_PROCESS_CODE                                           \n");            sSQL.append("        ,BI_OPERATOR_ID                                            \n");            sSQL.append("        ,BI_START_TIME                                             \n");            sSQL.append("        ,BI_END_TIME                                               \n");            sSQL.append("        ,BI_TESTER_MODEL                                           \n");            sSQL.append("        ,BI_TEST_NUMBER                                            \n");            sSQL.append("        ,BI_BOARD_ID                                               \n");            sSQL.append("        ,BI_PART_NUMBER                                            \n");            sSQL.append("        ,BI_ZONE_NUMBER                                            \n");            sSQL.append("        ,BI_SLOT_NUMBER                                            \n");            sSQL.append("        ,BI_SOCKET_X                                               \n");            sSQL.append("        ,BI_SOCKET_Y                                               \n");            sSQL.append("        ,BI_SOCKET_NUMBER                                          \n");            sSQL.append("      )                                                            \n");            sSQL.append("      VALUES                                                       \n");            sSQL.append("      (                                                            \n");            sSQL.append("          "+ biSEQ +"                                              \n");            sSQL.append("        ,'"+ Hist_Variable.tdbiMap.get("LOTNO") +"'                \n");            sSQL.append("        ,'"+ Hist_Variable.tdbiMap.get("TP") +"'                   \n");            sSQL.append("        ," + biProcessCode +"                                      \n");            sSQL.append("        ,'"+ Hist_Variable.tdbiMap.get("OID") +"'                  \n");            sSQL.append("        ,'"+ Hist_Variable.tdbiMap.get("START-TIME") +"'           \n");            sSQL.append("        ,'"+ Hist_Variable.tdbiMap.get("END-TIME") +"'             \n");            sSQL.append("        ,'"+ Hist_Variable.tdbiMap.get("BI_TESTER_MODEL") +"'      \n");            sSQL.append("        ," + Hist_Variable.tdbiMap.get("BI_TEST_NUMBER") +"        \n");            sSQL.append("        ," + biBoardId +"                                          \n");            sSQL.append("        ,'"+ Hist_Variable.tdbiMap.get("PARTS")+"'                 \n");            sSQL.append("        ," + biZoneNumber +"                                       \n");            sSQL.append("        ," + biSlotNumber +"                                       \n");            sSQL.append("        ,'"+ biSocketX +"'                                         \n");            sSQL.append("        ,'"+ biSocketY +"'                                         \n");            sSQL.append("        ,'"+ biSocketNumber +"'                                    \n");            sSQL.append("      )                                                            \n");                        size = GlobalDao.executeUpdate(sSQL.toString());                        System.out.println("size ======> " + size);            if (size > 0) {                return true;            } else {                return false;            }        } catch (Exception e) {            logger.error(e.toString());            return false;        }    }}