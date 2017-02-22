package com.tptech.isi;

import java.util.Vector;
import org.apache.log4j.Logger;

import com.tptech.Hist_Variable;
import com.tptech.log.WriteLogger;
import com.tptech.smtoscar_hist.SmtOscar_Hist_Dele;

public class SmtIsi_JudgeExcept{

	private static Logger logger = WriteLogger.getInstance("SmtSbl_JudgeExcept.java");
	/**************************************************************************
	 * 판정제외 사항들을 체크한다.
	 * ------------------------------------------------------------------------
	 * @return - PASS, FAIL, ERROR
	 */
	public String runBefore(SmtIsi_Parameter parameter, SmtIsi_Stdinfo stdinfo){

		parameter.clearStepValue();
		parameter.logPass = "Y";
		parameter.logJudge1 = "EXCEPT";		
		
		// OP 수량이 0일 경우
		if(!parameter.flag_Stop)
		{
			CheckOpCnt(parameter, stdinfo);
			parameter.ctrlFlag(false);
		}
		
		
		return "";
	}
	
	/**************************************************************************
	 * 작업자 수량이 0 이면 제외한다.
	 * ------------------------------------------------------------------------
	 * @param parameter - 인자값 클래스
	 * @param stdinfo   - 기준정보값 클래스
	 * @return - PASS, FAIL, ERROR
	 */
	private void CheckOpCnt(SmtIsi_Parameter parameter, SmtIsi_Stdinfo stdinfo)
	{
		try{
			parameter.logJudge2 = "OP_CNT";
			parameter.logReason = " OP_CNT=|" + Hist_Variable.hs.get("OP_CNT") + "|";
			
			if(parameter.OP_TOTAL == 0)
			{
				parameter.logReason ="[OP_TOTAL==0]" + parameter.logReason;
				parameter.logPass = "N";
				parameter.logSpec = String.valueOf(parameter.OP_TOTAL);
				parameter.logValue = String.valueOf(parameter.OP_TOTAL);
				
				parameter.rtSblAllCode.add("");
				parameter.rtYield = stdinfo.LIMIT_YIELD;
				parameter.rtSBL1 = "LOT";
				parameter.rtSBL2 = "";
				parameter.rtSblCode = "333";
				parameter.rtSblComment = "";
				parameter.rtReasonCode.add("");
				
				parameter.makeLogResult();
			}
		}catch(Exception e){
			parameter.logPass = "ERROR";
			parameter.logReason = e.toString();
			logger.error(e.toString());
		}
	}	
	
	public String runAfter(SmtIsi_Parameter clsParam, SmtIsi_Stdinfo clsStd){
	
		clsParam.clearStepValue();
		clsParam.logPass = "Y";
		clsParam.logJudge1 = "EXCEPT";	
		
		// ENG LOT 일 경우
		if(!clsParam.flag_Stop)
		{
			CheckEngLot(clsParam, clsStd);
			clsParam.ctrlFlag(false);
		}
	
		// 설비 총 수량이 기준정보의 특정수량보다 작을 때
		if(!clsParam.flag_Stop)
		{
			CheckEqpCnt(clsParam, clsStd);
			clsParam.ctrlFlag(false);
		}
		
		// 제외 LOT으로 분류가 된 경우
		if(!clsParam.flag_Stop)
		{
			CheckLotId(clsParam, clsStd);
			clsParam.ctrlFlag(false);
		}
		
		return "";
	}
	
	/**************************************************************************
	 * Engineer Lot 인경우 제외한다.
	 * ------------------------------------------------------------------------
	 * @param parameter - 인자값 클래스
	 * @param stdinfo   - 기준정보값 클래스
	 * @return - PASS, FAIL, ERROR
	 */
	private void CheckEngLot(SmtIsi_Parameter parameter, SmtIsi_Stdinfo stdinfo)
	{
		try{
			parameter.logJudge2 = "ENG_LOT";
			parameter.logReason = " LOTTYPE=|" + Hist_Variable.hs.get("Type") + "|";
			parameter.logReason += " LOTID=|" + Hist_Variable.hs.get("LotId") + "|";
			
			if(Hist_Variable.hs.get("Type").substring(0, 1).equals("E"))
			{
				parameter.logReason ="[LOTTYPE like ''E%''] => " + parameter.logReason;
				parameter.logPass = "N";
				parameter.logSpec = "E%";
				parameter.logValue = Hist_Variable.hs.get("Type");
	
				parameter.rtSblAllCode.add("");
				parameter.rtYield = stdinfo.LIMIT_YIELD;
				parameter.rtSBL1 = "LOT";
				parameter.rtSBL2 = "";
				parameter.rtSblCode = "333";
				parameter.rtSblComment = "";
				parameter.rtReasonCode.add("");
				
				parameter.makeLogResult();
			}
			if(Hist_Variable.hs.get("LotId").substring(0, 3).equals("ENG"))
			{
				parameter.logReason ="[LOTID like ''ENG%''] => " + parameter.logReason;
				parameter.logPass = "N";
				parameter.logSpec = "ENG%";
				parameter.logValue = Hist_Variable.hs.get("LotId");
	
				parameter.rtSblAllCode.add("");
				parameter.rtYield = stdinfo.LIMIT_YIELD;
				parameter.rtSBL1 = "LOT";
				parameter.rtSBL2 = "";
				parameter.rtSblCode = "333";
				parameter.rtSblComment = "";
				parameter.rtReasonCode.add("");
	
				parameter.makeLogResult();
			}
		}catch(Exception e)
		{
			parameter.logPass = "ERROR";
			parameter.logReason = e.toString();
			logger.error(e.toString());
		}
	}
	

	
	
	/**************************************************************************
	 * 작업수량이 기준정보의 수량보다 작으면 제외한다. 
	 * ------------------------------------------------------------------------
	 * @param parameter - 인자값 클래스
	 * @param stdinfo   - 기준정보값 클래스
	 * @return - PASS, FAIL, ERROR
	 */
	private void CheckEqpCnt(SmtIsi_Parameter parameter, SmtIsi_Stdinfo stdinfo)
	{
		try{
			parameter.logJudge2 = "EXCEPT_INPUT_CNT";
			parameter.logReason = " OP_TOTAL=|" + String.valueOf(parameter.OP_TOTAL) + "|";
			parameter.logReason += " EXCEPT_INPUT_CNT=|" + stdinfo.EXCEPT_INPUT_CNT + "|";

			if( parameter.OP_TOTAL < Integer.valueOf(stdinfo.EXCEPT_INPUT_CNT)){
				parameter.logReason ="[OP_TOTAL < EXCEPT_INPUT_CNT] =>" + parameter.logReason;
				parameter.logPass = "N";
				parameter.logSpec = stdinfo.EXCEPT_INPUT_CNT;
				parameter.logValue = String.valueOf(parameter.OP_TOTAL);
				
				parameter.rtSblAllCode.add("");
				parameter.rtYield = stdinfo.LIMIT_YIELD;
				parameter.rtSBL1 = "LOT";
				parameter.rtSBL2 = "";
				parameter.rtSblCode = "333";
				parameter.rtSblComment = "";
				parameter.rtReasonCode.add("");
				
				parameter.makeLogResult();
			}
		}catch(Exception e)
		{
			parameter.logPass = "ERROR";
			parameter.logReason = e.toString();
			logger.error(e.toString());
		}
	}
	
	
	
	
	/**************************************************************************
	 * 기준정보의 제외LOTID를 체크하여 해당되면 판정제외 한다. 
	 * ------------------------------------------------------------------------
	 * @param parameter - 인자값 클래스
	 * @param stdinfo   - 기준정보값 클래스
	 * @return - PASS, FAIL, ERROR
	 */
	private void CheckLotId(SmtIsi_Parameter parameter, SmtIsi_Stdinfo stdinfo){
		SmtOscar_Hist_Dele dele = new SmtOscar_Hist_Dele();
		
		try{
			parameter.logJudge2 = "EXCEPT_LOT";
			parameter.logReason = " LOTID=|" + Hist_Variable.hs.get("LotId") + "|";
			
			for(int i=0; i<stdinfo.aEXCEPT_LOT.length; i++ ){
				if(stdinfo.aEXCEPT_LOT[i]!=null && !stdinfo.aEXCEPT_LOT[i].equals("")){
					parameter.logReason += " EXCEPT_LOT["+String.valueOf(i)+"]=|" + stdinfo.aEXCEPT_LOT[i] + "|";
					
					if(!stdinfo.aEXCEPT_LOT[i].equals("NON")){
						Vector<?> vlimit = dele.getExceptLot(Hist_Variable.hs.get("LotId"), stdinfo.aEXCEPT_LOT[i]);
		
						if(vlimit.size() != 0){
			         		String[] sSbl_Temp = (String[]) vlimit.elementAt(0);
			         		if(sSbl_Temp[0].equals("1")){
			         			parameter.logReason ="[LOTID like EXCEPT_LOT] =>" + parameter.logReason ; 
				    			parameter.logPass = "N";
								parameter.logSpec = stdinfo.aEXCEPT_LOT[i];
								parameter.logValue = Hist_Variable.hs.get("LotId");
			         					
			        			parameter.rtSblAllCode.add("");
			        			parameter.rtYield = stdinfo.LIMIT_YIELD;
			        			parameter.rtSBL1 = "LOT";
			        			parameter.rtSBL2 = "";
			        			parameter.rtSblCode = "333";
			        			parameter.rtSblComment = "";
			        			parameter.rtReasonCode.add("");
	
			    				parameter.makeLogResult();
			         		}
			         	}
					}
				}
			}
		} catch (Exception e){
			parameter.logPass = "ERROR";
			parameter.logReason = e.toString();
			logger.error(e.toString());
		}
	}


}

