package com.tptech.isi;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.tptech.Hist_Variable;
import com.tptech.log.WriteLogger;
import com.tptech.util.FileMoveUtil;
import com.tptech.util.Utils;

public class SmtIsi_Parameter {
	
	private static Logger logger = WriteLogger.getInstance("SmtSbl_Parameter.java");
	
	public String Event_Time=""; // 세팅후 판정 끝까지 변동없이 가져간다.
	// 받은 인자값 변형값
	public Integer[] aOcnt= new Integer[9];
	public Integer[] aHcnt= new Integer[8];
	//public Integer[] aEcnt= new Integer[8];
	public Integer[] aBin9= new Integer[10];
	
	public Integer OP_TOTAL = 0;         //(cat1~cat8)
	public Integer OP_PASS = 0;          //(cat1~cat4) 
	public Integer EQP_TOTAL = 0;        //(cat1~cat8)
	//public Integer EQP_PASS = 0;         //(cat1~cat4) 
	public Integer HD_TOTAL = 0;         //(cat1~cat8)
	public Integer HD_PASS = 0;          //(cat1~cat4) 
	
	public HashMap<Integer, Integer> hmPrimeTestSubbin = new HashMap();
	public HashMap<Integer, Integer> hmFinalTestSubbin = new HashMap();
	
	public String rtSblCode ="";
	public String rtSblComment ="";
	public String rtSBL1 ="";
	public String rtSBL2 ="";
	public String rtYield ="";

	public ArrayList<String> rtSBL3 = new ArrayList<String> ();
	public ArrayList<String> rtSblAllCode = new ArrayList<String> ();
	public ArrayList<String> rtSblAllResult = new ArrayList<String> ();
	public ArrayList<String> rtSblAllComment = new ArrayList<String> ();
	public ArrayList<String> rtReasonCode = new ArrayList<String> ();	
	
	public String rtPass ="";
	public String rtJudge1 ="";
	public String rtJudge2 ="";
	public String rtReal ="";
	public String rtSpec ="";
	public String rtValue ="";
	public String rtReason ="";	
	public ArrayList<String[]> rtResult = new ArrayList<String[]> ();  //실제적으론 이 값만 ADP에 전달	
	public ArrayList<String> rtResultDb = new ArrayList<String> ();
	public ArrayList<String> rtAllJudge = new ArrayList<String> ();
	// FLAG 값
	public boolean flag_Stop;         // true 이면 판정로직 중지.
	public boolean flag_ResultWrite;  // rtResult 반환값을 쓰기전에는 false, 쓰고나면 true
 	public boolean flag_VirtualMode;  // true 이면 판정에 걸렸더라도 가상으로 끝까지 판정을 받아 이력을 남긴다.
	public boolean flag_RealMode ;    // true 이면 진짜 판정중, false 이면 가상판정 중.

	// LOG값
	public Integer logSeq =1;         // sbl_result_his 테이블의 seq 에 들어갈 값(판정 step을 진행 할때 마다 1씩 증가) 
	public String logPass ="";        // 각 판정 step 마다 기본 Y, 걸렸다면 N
	public String logJudge1 ="";      // 판정의 종류
	public String logJudge2 ="";      // 판정의 세부종류
	public String logSpec ="";        // 
	public String logValue ="";       // 
	public String logReason ="";      // 판정의 근거

	public ArrayList<String> logResultDb = new ArrayList<String> ();  // 판정 결과값 생성 
	public ArrayList<String[]> logResult = new ArrayList<String[]> ();  // 판정 결과값 생성
	
	public boolean set(){
		
		this.clearValue();
		
		
		// Event_Time 구하기
		this.Event_Time = getCurrentTime();
		
		logPass ="Y";
		logJudge1 ="PARAM";

		try{
			// OP 수량 배열에 넣기
			for(int i = 0; i< 9; i++){
				aOcnt[i]  = Integer.parseInt(Hist_Variable.hs.get("OP_CAT"+String.valueOf(i+1)));
				if(i < 8){
					if(null != Hist_Variable.hs.get("OP_CAT"+String.valueOf(i+1)) && !"".equals( Hist_Variable.hs.get("OP_CAT"+String.valueOf(i+1)))){
						OP_TOTAL = OP_TOTAL + Integer.parseInt(Hist_Variable.hs.get("OP_CAT" + (i+1)));
						if(i < 4){
							OP_PASS = OP_PASS + Integer.parseInt(Hist_Variable.hs.get("OP_CAT" + (i+1)));
						}
					}
				}				
			}
		}catch(Exception e){
			logger.error(e.toString());
			logger.error("Format Error OP Not");
			FileMoveUtil.makeDirectory(FileMoveUtil.getDirpath("conFormat") + "/" + Utils.getDate("MM", 0) + "Data");
			//FileMoveUtil.makeDirectory2(data_format + "/" + Utils.getDate("MM", 0) + "Data");
			File fFileName =  new File(FileMoveUtil.getDirpath("conFile") + "/"+ Hist_Variable.sFileName.getName());

			try {
				Utils.zipping(fFileName, fFileName.getAbsolutePath() + ".gz");
			} catch (IOException ex) {
				logger.error("[ReName-3]" + ex.toString());
			}
			File fGzip_File = new File(fFileName.getAbsolutePath() + ".gz");
			FileMoveUtil.moveFile(fGzip_File, FileMoveUtil.getDirpath("conFormat") + "/" + Utils.getDate("MM", 0) + "Data/");			
			return false;
		}		
		
		try{
			// Handler 수량 배열에 넣기
			for(int i = 0; i< 8; i++){
				aHcnt[i]  = Integer.parseInt(Hist_Variable.hs.get("HD_CAT"+String.valueOf(i+1)));
				HD_TOTAL = HD_TOTAL + Integer.parseInt(Hist_Variable.hs.get("HD_CAT" + (i+1)));
				if(i < 4){
					HD_PASS = HD_PASS + Integer.parseInt(Hist_Variable.hs.get("HD_CAT" + (i+1)));
				}
			}
		}catch(Exception e){
			logger.error(e.toString());
			logger.error("Format Error HD Not");
			FileMoveUtil.makeDirectory(FileMoveUtil.getDirpath("conFormat") + "/" + Utils.getDate("MM", 0) + "Data");
			//FileMoveUtil.makeDirectory2(data_format + "/" + Utils.getDate("MM", 0) + "Data");
			File fFileName =  new File(FileMoveUtil.getDirpath("conFile") + "/"+ Hist_Variable.sFileName.getName());

			try {
				Utils.zipping(fFileName, fFileName.getAbsolutePath() + ".gz");
			} catch (IOException ex) {
				logger.error("[ReName-3]" + ex.toString());
			}
			File fGzip_File = new File(fFileName.getAbsolutePath() + ".gz");
			FileMoveUtil.moveFile(fGzip_File, FileMoveUtil.getDirpath("conFormat") + "/" + Utils.getDate("MM", 0) + "Data/");			
			return false;
		}			
		
/*		try{
			// Eqp 수량 배열에 넣기
			for(int i = 0; i< 8; i++){
				aEcnt[i]  = Integer.parseInt(Hist_Variable.hs.get("EQP_CAT"+String.valueOf(i+1)));
				EQP_TOTAL = EQP_TOTAL + Integer.parseInt(Hist_Variable.hs.get("EQP_CAT" + (i+1)));
				if(i < 4){
					EQP_PASS = EQP_PASS + Integer.parseInt(Hist_Variable.hs.get("EQP_CAT" + (i+1)));
				}
			}
		}catch(Exception e){
			logger.error(e.toString());
			logger.error("Format Error EQP Not");
			FileMoveUtil.makeDirectory(FileMoveUtil.getDirpath("conFormat") + "/" + Utils.getDate("MM", 0) + "Data");
			//FileMoveUtil.makeDirectory2(data_format + "/" + Utils.getDate("MM", 0) + "Data");
			File fFileName =  new File(FileMoveUtil.getDirpath("conFile") + "/"+ Hist_Variable.sFileName.getName());

			try {
				Utils.zipping(fFileName, fFileName.getAbsolutePath() + ".gz");
			} catch (IOException ex) {
				logger.error("[ReName-3]" + ex.toString());
			}
			File fGzip_File = new File(fFileName.getAbsolutePath() + ".gz");
			FileMoveUtil.moveFile(fGzip_File, FileMoveUtil.getDirpath("conFormat") + "/" + Utils.getDate("MM", 0) + "Data/");			
			return false;
		}	*/		
		
		if(null != Hist_Variable.hs.get("Tmode")){
			if(Hist_Variable.hs.get("Tmode").toUpperCase().equals("TEND")){
				Integer bin9=0;
				bin9 = Integer.valueOf(Hist_Variable.hs.get("Inqty")) - OP_TOTAL;
				if(bin9>0){
					this.aOcnt[8] = bin9;
				}
			}
		}
/*		int aa = Hist_Variable.Retest;
		int aaa = Hist_Variable.Fe_Dut_Sort.length;
		System.out.println(aaa);
		*/
		try{
			
			if(splitSubbin(this.hmPrimeTestSubbin,Hist_Variable.Fe_Dut_Sub[0])){
				if(Hist_Variable.Retest == 0){
					splitSubbin(this.hmFinalTestSubbin,Hist_Variable.Fe_Dut_Sub[0]);
				}else{
					splitSubbin(this.hmFinalTestSubbin,Hist_Variable.Fe_Dut_Sub[Hist_Variable.Retest]);
				}
			}else{
				//logPass = "ERROR";
				//logReason = "splitSubbin(): PRIME_SUBBIN=|"+PRIME_SUBBIN+"| ";
				logger.error("splitSubbin(): PRIME_SUBBIN");
				return false;
			}
		}catch(Exception e)
		{
			logger.error(e.toString());
			//logPass = "ERROR";
			//logReason = "splitSubbin(): PRIME_SUBBIN=|"+PRIME_SUBBIN+"| " + e.toString();
			return false;
		}	
		return true;
	}
	
	//Sub SubBin을 HashMap에 넣는다.
	private boolean splitSubbin(HashMap<Integer, Integer> hmTemp, String[] sSubbinData)	{
		boolean PassFlag = true;
		try{
			for(int i=0; i<sSubbinData.length; i++ ){
				if(sSubbinData[i].length() > 0){
					String[] SubValue = sSubbinData[i].split(",");
						for(int k=0; k<SubValue.length; k++ ){
							String[] SubKey = SubValue[k].split(":");
							if(SubValue.length > 1){
								hmTemp.put(Integer.parseInt(SubKey[0]), Integer.parseInt(SubKey[1]));
							}							
						}
						//Integer SortValue = Integer.parseInt(sSubbinData[i]);
						//if(SortValue != 0){

						//}
				}
			}
		}catch(Exception e){
			logger.error(e.toString());
			PassFlag = false ;
		}		
		return PassFlag;
	}
	
	public void ctrlFlag(boolean flag_ing){
		//SBL_Dele dele = new SBL_Dele();
		
		//dele.insertResultHis(this);
		makeAllJudeg();
				
		if(logPass.equals("N")){
			if(!flag_ing){ // 판정결과를 가지고 바로 result 를 만들어야 한다면
				if(flag_RealMode){
					// result 옮기기 
					moveResult();
					
					// result 쓰기
					//dele.insertResult();
					flag_ResultWrite = true;
					flag_RealMode = false ;
				}
				if(flag_VirtualMode || logJudge1.equals("LOS")){
					flag_Stop = false;
				}else{
					flag_Stop = true;
				}
				
			}else{ // 다음판정까지 받고 result 를 만들어야 한다면
				if(flag_RealMode){
					// result 옮기기 
					flag_ResultWrite = true;
					moveResult();
				}
			}
		}else if(logPass.equals("Y")){
			if(flag_RealMode && flag_ResultWrite){
				// result 쓰기
				flag_RealMode = false ;
				//dele.insertResult();
			}
		}else{ // 에러가 발생했을 때
			// result 옮기기 
			moveResult();
			
			// result 쓰기
			//dele.insertResult();
			flag_ResultWrite = true;
			flag_RealMode = true ;
			flag_Stop = true;
		}
	}
	
	private boolean makeAllJudeg()
	{
		try
		{
			String sData = "";
			String sReal = "N";
			String sResult = "";
			
			if(flag_RealMode) sReal = "Y";
			for(int i=0; i<logResult.size(); i++)
			{
				sResult += "<" + logResult.get(i) + ">,"; 
			}
			rtReal = sReal;
			sData = "JUDGE1=" + logJudge1 + "!@!";
			sData = sData + "JUDGE2=" + logJudge2 + "!@!";
			sData = sData + "REAL=" + sReal + "!@!";
			sData = sData + "PASS=" + logPass + "!@!";
			sData = sData + "SPEC=" + logSpec + "!@!";
			sData = sData + "VALUE=" + logValue + "!@!";
			sData = sData + "RESULT=" + sResult + "!@!";
			sData = sData + "REASON=" + logReason + "\n";

			//Sbl_Utils.sblmakeLog(MODEL, LOTID+" makeAllJudeg sData = " + sData );
			rtAllJudge.add(sData);
		}catch(Exception e)
		{
			logger.error(e.toString());
			return false;
		}
		return true;
	}
	
	public boolean makeLogResult()
	{
		String pJudge = this.logJudge1;
		String sDataResult="";
		String[] sData = new String[8];
		for(int i=0; i< 8; i++) sData[i] = "";
		
		if(!Hist_Variable.sSblCode.equals("")){
			this.rtSblCode = Hist_Variable.sSblCode;
		}
		if(pJudge.equals("SUBBIN")){
			for(int i=0; i<this.rtSblAllResult.size(); i++)
			{
				sData[0] = this.rtSblAllCode.get(i);
				sData[1] = this.rtYield;
				sData[2] = this.rtSBL1;
				sData[3] = this.rtSBL2;
				sData[4] = this.rtSBL3.get(i);
				sData[5] = this.rtSblAllResult.get(i);
				sData[6] = this.rtSblAllComment.get(i);
				sData[7] = this.rtReasonCode.get(i);
				sDataResult = this.rtSblAllCode.get(i) + ":";
				sDataResult = sDataResult + this.rtYield + ":";
				sDataResult = sDataResult + this.rtSBL1 + ":";
				sDataResult = sDataResult + this.rtSBL2 + ":";
				sDataResult = sDataResult + this.rtSBL3.get(i) + ":";
				sDataResult = sDataResult + this.rtSblAllResult.get(i) + ":";
				sDataResult = sDataResult + this.rtSblAllComment.get(i) + ":";
				sDataResult = sDataResult + this.rtReasonCode.get(i) ;
				this.logResultDb.add(sDataResult);
				this.logResult.add(sData);
				
				//Sbl_Utils.sblmakeLog(MODEL, LOTID+" makeLogResult sData = " + sData );
			}
		}else{
			if(this.rtSblAllCode.size() > 0){
				sData[0] = this.rtSblAllCode.get(0);
			}else{
				sData[0] = "";
			}
			sData[1] = this.rtYield;
			sData[2] = this.rtSBL1;
			sData[3] = this.rtSBL2;
			sData[4] = this.rtSblCode;
			sData[5] = this.rtSblCode;
			sData[6] = this.rtSblComment;
			sData[7] = this.rtReasonCode.get(0);
			if(this.rtSblAllCode.size() > 0){
				sDataResult =  this.rtSblAllCode.get(0);
			}else{
				sDataResult = ":";
			}
			sDataResult = sDataResult + this.rtYield + ":";
			sDataResult = sDataResult + this.rtSBL1 + ":";
			sDataResult = sDataResult + this.rtSBL2 + ":";
			sDataResult = sDataResult + this.rtSblCode + ":";
			sDataResult = sDataResult + this.rtSblCode + ":";
			sDataResult = sDataResult + this.rtSblComment + ":";
			sDataResult = sDataResult + this.rtReasonCode.get(0) ;
			this.logResultDb.add(sDataResult);
			this.logResult.add(sData);
		}
		
		return true;
	}
	
	private boolean moveResult()
	{
		try{
			rtPass = logPass;
			rtJudge1 = logJudge1;
			rtJudge2 = logJudge2;
			rtSpec = logSpec;
			rtValue = logValue;
			rtReason = this.logReason;
			for(int i=0; i<logResultDb.size(); i++)
			{
				rtResultDb.add(logResultDb.get(i));
				
			}
			for(int i=0; i<logResult.size(); i++)
			{
				rtResult.add(logResult.get(i));
				
			}			
			
		}catch(Exception e)
		{
			logger.error(e.toString());
			return false;
		}
		return true;
	}
	
	private void clearValue(){

		Event_Time="";
		for(int i=0; i<aOcnt.length; i++) aOcnt[i]=0;
		for(int i=0; i<aHcnt.length; i++) aHcnt[i]=0;
		//for(int i=0; i<aEcnt.length; i++) aEcnt[i]=0;
		
		OP_TOTAL = 0;
		OP_PASS = 0;
		EQP_TOTAL = 0;
		//EQP_PASS = 0;
		HD_TOTAL = 0;
		HD_PASS = 0;
		
		hmPrimeTestSubbin.clear();
		hmFinalTestSubbin.clear();

		rtSblCode ="";
		rtSblComment = "";
		rtSBL1 ="";
		rtSBL2 ="";
		rtYield ="";
		
		rtSBL3.clear();
		rtSblAllCode.clear();
		rtSblAllResult.clear();
		rtSblAllComment.clear();
		rtReasonCode.clear();		
		rtResult.clear();
		rtAllJudge.clear();
		
		rtPass ="";
		rtJudge1 ="";
		rtJudge2 ="";
		rtSpec ="";
		rtValue ="";
		
		flag_Stop = false;
		flag_ResultWrite = false;
		flag_VirtualMode = true;
		flag_RealMode = true;
		
		
		logSeq =1;
		logPass ="Y";
		logJudge1 ="-";
		logJudge2 ="-";
		logSpec = "";
		logValue = "";
		logReason ="";
		logResult.clear();
	}
	
	public void clearStepValue()
	{
		rtSblCode ="";
		rtSblComment = "";
		rtSBL1 ="";
		rtSBL2 ="";
		rtYield ="";
		
		rtSBL3.clear();
		rtSblAllCode.clear();
		rtSblAllResult.clear();
		rtSblAllComment.clear();
		rtReasonCode.clear();
		
		logPass ="Y";
		logJudge1 ="-";
		logJudge2 ="-";
		logSpec = "";
		logValue = "";
		logReason ="";
		logResult.clear();
	}

	private String getCurrentTime(){
		long todaytime;
		SimpleDateFormat day, time;
		String sDay, sTime;
		
		todaytime = System.currentTimeMillis();
		day = new SimpleDateFormat("yyyyMMdd");
		time = new SimpleDateFormat("HHmmss");
		
		sDay = day.format(new Date(todaytime));
		sTime = time.format(new Date(todaytime));
		
		return sDay+sTime;
	}
	
}
