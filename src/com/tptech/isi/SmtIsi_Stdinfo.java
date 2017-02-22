package com.tptech.isi;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.tptech.log.WriteLogger;
import com.tptech.smtoscar_hist.SmtOscar_Hist_Dele;

public class SmtIsi_Stdinfo {

	private static Logger logger = WriteLogger.getInstance("SmtSbl_Stdinfo.java");
	
	public String LIMIT_YIELD;
	public String RUN_SBL;
	public String YIELD_SBL;
	public String SUB_BIN;
	public String T_LOT_SBL;
	public String EXCEPT_LOT;
	public String EXCEPT_INPUT_CNT;
	public String FAIL_SUM;
	public String FAIL_SUM_CMD;
	
	public String[] aEXCEPT_LOT = new String[30];
	public ArrayList<SmtIsi_cstSubBinLimit> alSubBin = new ArrayList<SmtIsi_cstSubBinLimit> ();

	public double dLIMIT_YIELD;
	public double dFAIL_SUM;
	public int nEXCEPT_INPUT_CNT;
	
	public boolean setValue(SmtIsi_Parameter parameter)
	{
		//SBL_Dele dele = new SBL_Dele();
		//SBL_Parameter clsParam = new SBL_Parameter();
		clearValue();
				
		parameter.logPass ="Y";
		parameter.logJudge1 ="STDINF";
		
		// 기준정보 테이블에서 정보를 얻어온다.
		if(!getTableValue(parameter))
		{
			parameter.logPass = "ERROR";
			//parameter.logReason = "setValue().getTableValue():" + toString() ;
			return false;
		}
		
		// 숫자 타입으로 변형할 변수 처리
		try{
	 		this.dLIMIT_YIELD = Double.valueOf(this.LIMIT_YIELD);
	 		this.nEXCEPT_INPUT_CNT = Integer.valueOf(this.EXCEPT_INPUT_CNT);
		}catch(Exception e)
		{
			logger.error(e.toString());
			return false;
		}
 		
		
		// EXCEPT_LOT 의 요소를 분리해서 배열에 넣는다.
		aEXCEPT_LOT = EXCEPT_LOT.split(",");
		
		// SubBin 정보를 split 해서 구조체에 넣는다.
		//this.SUB_BIN="114!#!5:HOLD:machine_BIN8!#!5:HOLD:!#!5:HOLD:!#!F!#!@#@119!#!1:HOLD:LINE MIX!#!1:HOLD:!#!1:HOLD:!#!F!#!@#@151!#!1:HOLD:INT(PRIME)!#!1:HOLD:!#!1:HOLD:!#!P!#!@#@111!#!0.5:HOLD:SOCKET!#!0.5:HOLD:SOCKET!#!0.5:HOLD:SOCKET!#!F!#!@#@155!#!1:HOLD:tCH 3.2NS!#!1:HOLD:tCH 3.2NS!#!1:HOLD:tCH 3.2NS!#!P!#!@#@153!#!2:AAC:CORE!#!2:AAC:!#!2:AAC:!#!F!#!@#@125!#!1.4:AAC:CELL!#!1.4:AAC:!#!1.4:AAC:!#!F!#!@#@161!#!2:ABC:7713 PGRMW!#!2:ABC:!#!2:ABC:!#!F!#!@#@144!#!1:AAC:SAME CSL!#!1:AAC:!#!1:AAC:!#!F!#!@#@103!#!0.3:AAC:SLIM TC!#!0.3:AAC:!#!0.3:AAC:!#!P!#!@#@105!#!1:AAC:L2B_LFC SKIP!#!1:AAC:!#!1:AAC:!#!P!#!@#@113!#!0.1:AAC:TN861,862!#!4.5:AAC:!#!4.5:AAC:!#!F!#!@#@135!#!0.05:AAC:FUSE MC fail!#!0.05:AAC:!#!0.05:AAC:!#!F!#!@#@122!#!0.3:AA1:PSEC!#!1:AAC:!#!1:AAC:!#!P!#!@#@154!#!1:HOLD:WtCH!#!1:HOLD:!#!1:HOLD:!#!F!#!@#@";
		if(!splitSubBin(alSubBin, SUB_BIN))
		{
			logger.error("setValue().splitSubBin(): SUB_BIN=|"+ SUB_BIN +"|");
			//clsParam.logPass = "ERROR";
			//clsParam.logReason = "setValue().splitSubBin(): SUB_BIN=|"+ SUB_BIN +"|";
			return false;
		}
		return true;
	}	

	private boolean getTableValue(SmtIsi_Parameter parameter)
	{
		boolean PassFlag = true;
		SmtOscar_Hist_Dele dele = new SmtOscar_Hist_Dele();
		//SBL_Parameter clsParam = new SBL_Parameter();

		// Last_Table 에서 값 를 구한다.
		Vector<?> vlimit=null; 
		try 
		{
			vlimit = dele.getLastTable();
			if(vlimit.size() != 0)
         	{
         		String[] sSbl_Temp = (String[]) vlimit.elementAt(0);
         		this.LIMIT_YIELD = sSbl_Temp[0];
         		this.RUN_SBL = sSbl_Temp[1];
         		this.YIELD_SBL = sSbl_Temp[2];
         		this.SUB_BIN = sSbl_Temp[3];
         		this.T_LOT_SBL = sSbl_Temp[4];
         		this.EXCEPT_LOT = sSbl_Temp[5];
         		this.EXCEPT_INPUT_CNT = sSbl_Temp[6];
         		this.FAIL_SUM = sSbl_Temp[7];
         		this.dFAIL_SUM = Double.valueOf(sSbl_Temp[7]);
         		this.FAIL_SUM_CMD = sSbl_Temp[8];         		
         	}else{
         		this.LIMIT_YIELD = "0";
         		this.RUN_SBL = "";
         		this.YIELD_SBL = "";
         		this.SUB_BIN = "";
         		this.T_LOT_SBL = "";
         		this.EXCEPT_LOT = "";
         		this.EXCEPT_INPUT_CNT = "0";
         		this.FAIL_SUM = "0";
         		this.dFAIL_SUM = Double.valueOf("0");
         		this.FAIL_SUM_CMD = ""; 
         	}
		} catch (Exception e){
			logger.error(e.toString());
			PassFlag = false;
		}

		return PassFlag;
	}	
	
	private boolean splitArrayList(ArrayList<String> pTemp, String pData, String pD){
		
		boolean PassFlag = true;
		try{
			pTemp.clear();
			if(pTemp == null || pData == null || pD == null ){
				PassFlag = false;
			}else{
				StringTokenizer stData = new StringTokenizer(pData, pD);
				
				while(stData.hasMoreTokens()){
					String temp = stData.nextToken();
					pTemp.add(temp);
				}
			}
		}catch (Exception e){
			PassFlag = false;
		}

		logger.info("[SBL_Stdinfo] splitArrayList() pTemp= "+pTemp);
		logger.info("[SBL_Stdinfo] splitArrayList() pData= "+pData);
		logger.info(" [SBL_Stdinfo] splitArrayList() result= "+PassFlag);
		return PassFlag;
	}	
	
	private boolean splitSubBin(ArrayList<SmtIsi_cstSubBinLimit> alSubBin, String sData){

		//SBL_Parameter clsParam = new SBL_Parameter();

		boolean PassFlag = true;
		
		logger.info("[SBL_Stdinfo] splitSubBin() ****sData="+sData);
		
		//118!#!0.3:HOLD:PKGMAP!#!0.3:HOLD:!#!0.33:HOLD:!#!F!#!@#@171!#!0.3:HOLD:GF!#!0.32:HOLD:!#!0.4:HOLD:!#!F!#!@#@121!#!0.4:AB1:IDD!#!0.4:HOLD:!#!0.4:HOLD:!#!F!#!@#@129!#!2:AAC:tRDL!#!2:HOLD:!#!2:HOLD:!#!F!#!@#@166!#!0.3:AAC:CRIS!#!0.3:HOLD:!#!0.3:HOLD:!#!F!#!@#@153!#!0.5:AA1:ROW!#!0.5:HOLD:!#!0.5:HOLD:!#!F!#!@#@170!#!0.5:AB1:C2C!#!0.64:HOLD:!#!0.78:HOLD:!#!F!#!@#@127!#!3:AA1:B2B!#!3:HOLD:!#!3:HOLD:!#!F!#!@#@128!#!1.27:AA1:Int SB!#!1.68:HOLD:!#!2.09:HOLD:!#!F!#!@#@162!#!10:AA1:S2P!#!10:AAC:!#!10:HOLD:!#!F!#!@#@126!#!0.3:AB1:chipping!#!0.38:HOLD:!#!0.47:HOLD:!#!F!#!@#@113!#!1:AA1:FUSE!#!1:HOLD:!#!1:HOLD:!#!F!#!@#@
		String[] alBinList = new String[200];
		ArrayList<String> alValueList = new ArrayList<String>();
		//sData = "118!#!0.3:HOLD:PKGMAP!#!0.3:HOLD:!#!::!#!F!#!@#@171!#!0.3:HOLD:GF!#!0.32:HOLD:!#!0.4:HOLD:!#!F!#!@#@121!#!0.4:AB1:IDD!#!0.4:HOLD:!#!0.4:HOLD:!#!F!#!@#@129!#!2:AAC:tRDL!#!2:HOLD:!#!2:HOLD:!#!F!#!@#@166!#!0.3:AAC:CRIS!#!0.3:HOLD:!#!0.3:HOLD:!#!F!#!@#@153!#!0.5:AA1:ROW!#!0.5:HOLD:!#!0.5:HOLD:!#!F!#!@#@170!#!0.5:AB1:C2C!#!0.64:HOLD:!#!0.78:HOLD:!#!F!#!@#@127!#!3:AA1:B2B!#!3:HOLD:!#!3:HOLD:!#!F!#!@#@128!#!1.27:AA1:Int SB!#!1.68:HOLD:!#!2.09:HOLD:!#!F!#!@#@162!#!10:AA1:S2P!#!10:AAC:!#!10:HOLD:!#!F!#!@#@126!#!0.3:AB1:chipping!#!0.38:HOLD:!#!0.47:HOLD:!#!F!#!@#@113!#!1:AA1:FUSE!#!1:HOLD:!#!1:HOLD:!#!F!#!@#@"; 
		try{
			alBinList = sData.split("@#@");
			for(int i=0; i<alBinList.length; i++){
				if(alBinList[i].equals("")){
					continue;
				}
				if(splitArrayList(alValueList, alBinList[i], "!#!")){
					
					SmtIsi_cstSubBinLimit cstSubBin = new SmtIsi_cstSubBinLimit();
					for(int j=0; j<alValueList.size(); j++){
						//Sbl_Utils.sblmakeLog(clsParam.MODEL, clsParam.LOTID+" ***********alValueList.get("+String.valueOf(j)+")="+alValueList.get(j));
						String sValue = alValueList.get(j); 
						if(j==0){
							cstSubBin.Bin = Integer.valueOf(sValue);
						}else if(j==4){
							cstSubBin.PrimeFlag = sValue;
						}else if(j==5){
							cstSubBin.S1Flag = sValue;
						}else if(j<4){
							sValue = sValue+":";
							String[] aItemList= sValue.split(":");
							for(int k=0; k<aItemList.length; k++){
								if(aItemList[k]!=null){
									String sValue2 = aItemList[k];
									//Sbl_Utils.sblmakeLog(clsParam.MODEL, clsParam.LOTID+" **********************sValue"+String.valueOf(k)+" ="+sValue2);
									if(k==0){
										if(!sValue2.equals("")){
											cstSubBin.aYield[j-1] = Double.valueOf(sValue2);
										}
									}else if(k==1){
										cstSubBin.aCode[j-1] = sValue2;
									}else if(k==2){
										cstSubBin.aComment[j-1] = sValue2;
									}
								}
							}
						}
					}
					alSubBin.add(cstSubBin);
				}
			}
		}catch (Exception e)
		{
			logger.error(e.toString());
			PassFlag = false;
		}

		return PassFlag;
	}
		
	
	public void clearValue()
	{
		this.LIMIT_YIELD = "";
		this.RUN_SBL = "";
		this.YIELD_SBL = "";
		this.SUB_BIN = "";
		this.T_LOT_SBL = "";
		this.EXCEPT_LOT = "";
		this.EXCEPT_INPUT_CNT = "";
		this.FAIL_SUM = "";
		this.FAIL_SUM_CMD = "";
		
		for(int i=0; i<aEXCEPT_LOT.length; i++) aEXCEPT_LOT[i]="";
		alSubBin.clear();
		
		this.dLIMIT_YIELD = 0.0;
		this.dFAIL_SUM = 0.0;
		this.nEXCEPT_INPUT_CNT = 0;
	}	
}
