package com.tptech.isi;

import java.util.HashMap;
import org.apache.log4j.Logger;

import com.tptech.Hist_Variable;
import com.tptech.log.WriteLogger;

public class SmtIsi_JudgeSubbin {

	private static Logger logger = WriteLogger.getInstance("SmtSbl_JudgeSubbin.java");
	/**************************************************************************
	 * SubBin 판정을 한다.
	 * ------------------------------------------------------------------------
	 * @return - PASS, FAIL, ERROR
	 */
	public void run(SmtIsi_Parameter clsParam, SmtIsi_Stdinfo clsStd){
		//SBL_Parameter clsParam = new SBL_Parameter();
		//SBL_Stdinfo clsStd = new SBL_Stdinfo();

		if(!clsParam.flag_Stop){
			clsParam.clearStepValue();
			clsParam.logPass = "Y";
			clsParam.logJudge1 = "SUBBIN";	
			normalSubBin(clsParam, clsStd);
			clsParam.ctrlFlag(true);
		}
		
/*		if(!clsParam.flag_Stop){
			clsParam.clearStepValue();
			clsParam.logPass = "Y";
			clsParam.logJudge1 = "SUBBIN";	
			//spcialSubBin(clsParam, clsStd);
			clsParam.ctrlFlag(true);
		}*/
	}
	
	/**************************************************************************
	 * 일반적인 SubBin에 대한 판정을 한다.
	 * ------------------------------------------------------------------------
	 * @param pParam - 인자값 클래스
	 * @param pStd   - 기준정보값 클래스
	 * @return - PASS, FAIL, ERROR
	 */
	private void normalSubBin(SmtIsi_Parameter pParam, SmtIsi_Stdinfo pStd){
		pParam.logJudge2 = "NORMAL_BIN";

		if(pParam.OP_TOTAL<=0){
			pParam.logReason ="[OP_TOTAL<=0] => ";
			pParam.logReason += "OP_TOTAL=|" + String.valueOf(pParam.OP_TOTAL) + "|";
			return ;
		}
		
		try{
			for(int i=0; i<pStd.alSubBin.size(); i++){
				SmtIsi_cstSubBinLimit cstSubBin = pStd.alSubBin.get(i);
				
				if(cstSubBin.Bin!=114 && cstSubBin.Bin!=115 && cstSubBin.Bin!=116){
					// Prime flag 에 의해 판정받을 data 을 설정한다.
					HashMap<Integer, Integer> hmTestSubbin;  
					if(cstSubBin.PrimeFlag.equals("Y")){
						hmTestSubbin = pParam.hmPrimeTestSubbin;
					}else{
						hmTestSubbin = pParam.hmFinalTestSubbin;
					}
					
					// 기준정보의 bin 값이  Test 한 결과에 있다면
					logger.info("Check BinNo=" + String.valueOf(cstSubBin.Bin));
					//Sbl_Utils.sblmakeLog(pParam.MODEL, pParam.LOTID+" [JudgeSubbin] Check BinNo=" + String.valueOf(cstSubBin.Bin));
					
					//pParam.logReason="";
					if(hmTestSubbin.containsKey(cstSubBin.Bin)){

						//Sbl_Utils.sblmakeLog(pParam.MODEL, pParam.LOTID+" [JudgeSubbin] in BinNo=" + String.valueOf(cstSubBin.Bin));
						Double dTestValue = Double.valueOf(hmTestSubbin.get(cstSubBin.Bin))/Double.valueOf(pParam.OP_TOTAL) * 100.0;
						
						pParam.logReason += "|| SubBin="+String.valueOf(cstSubBin.Bin)+ ",";
						pParam.logReason += "BinValue=" + hmTestSubbin.get(cstSubBin.Bin) + ",";
						pParam.logReason += "OP_TOTAL=" + String.valueOf(pParam.OP_TOTAL) + ",";
						pParam.logReason += "dTestValue=" + String.valueOf(dTestValue) + ",";
						pParam.logReason += "(j,dTestValue>dSpecValue)=";
						for(int j=cstSubBin.aYield.length-1; j>=0; j--){
							if(cstSubBin.aYield[j]>0){
								Double dSpecValue = Double.valueOf(cstSubBin.aYield[j]);
								
								pParam.logReason += "("+String.valueOf(j)+","+ String.valueOf(dTestValue)+">" + String.valueOf(dSpecValue)+")";
								if(dTestValue>dSpecValue){
									//pParam.logReason ="[dTestValue>dSpecValue] =>" + pParam.logReason;
									pParam.logPass = "N";
									pParam.logSpec = String.valueOf(dSpecValue);
									pParam.logValue = String.valueOf(dTestValue);
									
									String sReasonCode, sSBL3;
									pParam.rtSblAllCode.add(cstSubBin.aCode[j]);
									pParam.rtYield = pStd.LIMIT_YIELD;
									pParam.rtSBL1 = "LOT";
									pParam.rtSBL2 = "BIN";
									pParam.rtSblAllResult.add(String.valueOf(cstSubBin.Bin));	// subbin 번호를 넣는다.
									pParam.rtSblAllComment.add(cstSubBin.aComment[j]);

									logger.info("failbin="+ String.valueOf(cstSubBin.Bin));
									//Sbl_Utils.sblmakeLog(pParam.MODEL, pParam.LOTID+" [JudgeSubbin] failbin="+ String.valueOf(cstSubBin.Bin));
									
									sSBL3 = getSBL3(String.valueOf(cstSubBin.Bin),pParam, pStd);
									if(sSBL3.length() > 5 && sSBL3.substring(0, 5).equals("ERROR")){
										pParam.logPass = "ERROR";
										pParam.logReason ="[Error getSBL3()] =>";
										pParam.logReason += " SubBin=|"+String.valueOf(cstSubBin.Bin) + "|";
										pParam.logReason += " ErrString=|" + sSBL3+ "|";
									}else{
										pParam.rtSBL3.add(getSBL3(String.valueOf(cstSubBin.Bin),pParam, pStd));
										sReasonCode = getReasonCode(String.valueOf(cstSubBin.Bin), cstSubBin.aCode[j], pParam, pStd);
										if(sReasonCode.length()>5 && sReasonCode.substring(0, 5).equals("ERROR")){
											pParam.logPass = "ERROR";
											pParam.logReason ="[Error getReasonCode()] =>";
											pParam.logReason += " SubBin=|"+String.valueOf(cstSubBin.Bin) + "|";
											pParam.logReason += " Code=|" + cstSubBin.aCode[j] + "|";
											pParam.logReason += " ErrString=|" + sReasonCode+ "|";
										}else{
											pParam.rtReasonCode.add(sReasonCode);
										}
									}
								}//if(dTestValue>dSpecValue)
								if(pParam.rtSblAllCode.get(0).equals("HOLD")){
									pParam.makeLogResult();
									return;
								}
							}//if(cstSubBin.aYield[j]>0)
						}
						//Sbl_Utils.sblmakeLog(pParam.MODEL, pParam.LOTID+" [JudgeSubbin] logReason = "+ pParam.logReason);
					} // if(hmTestSubbin.containsKey(cstSubBin.Bin))
					
				}
			}
			pParam.makeLogResult();
		}catch(Exception e){
			pParam.logPass = "ERROR";
			pParam.logReason = e.toString();
			//Sbl_Utils.sblmakeLog(pParam.MODEL, pParam.LOTID+" [[ERROR]] [JudgeSubbin] normalSubBin() error >>>  "+e.toString() );
		}
	}
	
	private String getReasonCode(String pBin, String pCode, SmtIsi_Parameter pParam, SmtIsi_Stdinfo pStd){
		boolean flag_reason = false;
		String sReasonCode = "";
		String pProduct = Hist_Variable.hs.get("ProductId");
		String pProcess = Hist_Variable.hs.get("ProcessId");
		
		try{
			// Reason 을 쓸 조건을 체크한다.
			if( pProduct.substring(0,3).equals("K4B") || pProduct.substring(0,3).equals("K4T")|| pProduct.substring(0,3).equals("K4H")
				|| pProduct.substring(0,3).equals("K4J") || pProduct.substring(0,3).equals("K4G") || pProduct.substring(0,3).equals("K4S")
				|| pProduct.substring(0,3).equals("K4Y") || pProduct.substring(0,3).equals("K4D") || pProduct.substring(0,2).equals("K3")){
				flag_reason = true;
			}
			if(pProduct.length()>=3){
				if( pProduct.substring(pProduct.length()-3).equals("LBA")
					|| pProduct.substring(pProduct.length()-3).equals("LA1")){
					flag_reason = true;
				}
			}
			if( pProduct.substring(0,2).equals("K5") || pProduct.substring(0,2).equals("KA")|| pProduct.substring(0,2).equals("KB")
				|| pProduct.substring(0,2).equals("KC") || pProduct.substring(0,2).equals("KL") || pProduct.substring(0,2).equals("KM")){
				if( !pProcess.substring(0,4).equals("T091")
					&&	!pProcess.substring(0,4).equals("T092")
					&&	!pProcess.substring(0,4).equals("T093")
					&&	!pProcess.substring(0,4).equals("T094")
					&&	!pProcess.substring(0,4).equals("T070")){
					flag_reason = true;
				}
			}
			
			if(pBin.equals("114")|| pBin.equals("115") || pBin.equals("116")){
				if(flag_reason){
					if(pBin.equals("114")){
						sReasonCode = "ATM";
					}else if(pBin.equals("115") || pBin.equals("116")){
						sReasonCode = "AAP";
					}
				}else{
					int nBin = Integer.valueOf(pBin);
					if(Hist_Variable.Retest<1){
						sReasonCode = "ATM";
					}else{
						Integer nPrimeSubbin = 0;
						if(pParam.hmPrimeTestSubbin.containsKey(nBin)){
							nPrimeSubbin = pParam.hmPrimeTestSubbin.get(nBin);
						}
						Integer nFinalSubbin = pParam.hmFinalTestSubbin.get(nBin);
						
						if(nPrimeSubbin>0){
							Double dYield = Double.valueOf(nFinalSubbin) / Double.valueOf(nPrimeSubbin);
							if(dYield >= 0.6){
								sReasonCode = "AAP";
							}else{
								sReasonCode = "ATM";
							}
						}else{
							if(nBin==114){
								sReasonCode = "ATM";
							}else{
								sReasonCode = "AAP";
							}
						}
					}
				} // end if(flag_reason)
			}else{
				sReasonCode = "-";
				if(pCode.length()>=3){
					if(pCode.substring(0,3).equals("TAS") || pCode.substring(0,3).equals("TAD")){
						sReasonCode = "AAP";
					}
				}
			}// end if(pBin.equals("114")|| pBin.equals("115") || pBin.equals("116"))
		}catch(Exception e){
			logger.error(e.toString());
			//Sbl_Utils.sblmakeLog(pParam.MODEL, pParam.LOTID+" [[ERROR]] [JudgeSubbin]  getReasonCode() error >>>  "+e.toString() );
			return "ERROR " + e.toString(); 			
		}
		
		return sReasonCode;
	}
	
	
	private String getSBL3(String pBin, SmtIsi_Parameter pParam, SmtIsi_Stdinfo pStd){
		String sSBL3 = pBin;
		int nBin = Integer.valueOf(pBin);
		
		try{
			if(pBin.equals("114")|| pBin.equals("115") || pBin.equals("116")){
				Integer nPrimeSubbin = 0;
				if(pParam.hmPrimeTestSubbin.containsKey(nBin)){
					nPrimeSubbin = pParam.hmPrimeTestSubbin.get(nBin);
				}
				Integer nFinalSubbin = pParam.hmFinalTestSubbin.get(nBin);
				
				if(nPrimeSubbin>0){
					Double dYield = Double.valueOf(nFinalSubbin) / Double.valueOf(nPrimeSubbin);
					if(dYield >= 0.6){
						sSBL3 = "TAS";
					}else{
						sSBL3 = "TAM";
					}
				}
			}
		}catch(Exception e){
			logger.error(e.toString());
			//Sbl_Utils.sblmakeLog(pParam.MODEL, pParam.LOTID+" [[ERROR]] [JudgeSubbin]  getSBL3() error >>>  "+e.toString() );
			return "ERROR " + e.toString(); 
		}
		return sSBL3;
	}
	
	
	/**************************************************************************
	 * 114, 115, 116 SubBin에 대한 판정을 한다.
	 * ------------------------------------------------------------------------
	 * @param pParam - 인자값 클래스
	 * @param pStd   - 기준정보값 클래스
	 * @return - PASS, FAIL, ERROR
	 */
/*	private void spcialSubBin(SmtSbl_Parameter pParam, SmtSbl_Stdinfo pStd)
	{
		
		pParam.logJudge2 = "SPECIAL_BIN";
		try{
			// K7 로 시작하는 제품은 제외한다.
			if(Hist_Variable.hs.get("ProductId").substring(0, 2).equals("K7"))
			{
				pParam.logReason ="[PRODUCT like ''K7%''] => ";
				pParam.logReason += "PRODUCT=|" + Hist_Variable.hs.get("ProductId") + "|";
				return ;
			}
			if(Hist_Variable.hs.get("Inqty").equals(""))
			{
				pParam.logPass = "ERROR";
				pParam.logReason ="[SIMAX_QTY is Null] => ";
				pParam.logReason += "SIMAX_QTY=|" + Hist_Variable.hs.get("Inqty") + "|";
				return ;
			}
			if(Integer.valueOf(Hist_Variable.hs.get("Inqty"))<=0)
			{
				pParam.logPass = "ERROR";
				pParam.logReason ="[SIMAX_QTY is Null] => ";
				pParam.logReason += "SIMAX_QTY=|" + Hist_Variable.hs.get("Inqty") + "|";
				return ;
			}
			
			// Test 결과값 중 114번이 있다면
			Double dTestValue = 0.0;
			Double dSpecValue = 0.0;
			String sBinNo = "0";
			
			
			pParam.logReason =" SIMAX_QTY=|" + Hist_Variable.hs.get("Inqty") + "|" ;
			if(sBinNo.equals("0")&& pParam.hmFinalTestSubbin.containsKey(114))
			{
				dTestValue = Double.valueOf(pParam.hmFinalTestSubbin.get(114))/Double.valueOf(Hist_Variable.hs.get("Inqty")) * 100.0;
				dSpecValue = pStd.dLIMIT_114;
				
				pParam.logReason += " BIN=114";
				pParam.logReason += " (dTestValue>dSpecValue)=(" + String.valueOf(dTestValue) + ">" + String.valueOf(dSpecValue)+ ")" ;
				
				if(dTestValue>dSpecValue)
				{
					pParam.logSpec = String.valueOf(dSpecValue);
					pParam.logValue = String.valueOf(dTestValue);
					
					sBinNo="114";
					pParam.rtSblAllCode.add("HOLD");
					pParam.rtYield = pStd.LIMIT_YIELD;
					pParam.rtSBL1 = "LOT";
					pParam.rtSBL2 = "BIN";
					pParam.rtSBL3.add(getSBL3(sBinNo,pParam, pStd));
					pParam.rtSblAllResult.add(sBinNo);	// subbin 번호를 넣는다.
					pParam.rtSblAllComment.add("");
					pParam.rtReasonCode.add(getReasonCode(sBinNo, "HOLD", pParam, pStd));
					
					
				}
			}
			// Test 결과값 중 115번이 있다면
			if(sBinNo.equals("0") && pParam.hmFinalTestSubbin.containsKey(115))
			{
				dTestValue = Double.valueOf(pParam.hmFinalTestSubbin.get(115))/Double.valueOf(pParam.SIMAX_QTY) * 100.0;
				dSpecValue = pStd.dLIMIT_115;
				
				pParam.logReason += " BIN=115";
				pParam.logReason += " (dTestValue>dSpecValue)=(" + String.valueOf(dTestValue) + ">" + String.valueOf(dSpecValue)+ ")" ;
				
				if(dTestValue>dSpecValue)
				{
					pParam.logSpec = String.valueOf(dSpecValue);
					pParam.logValue = String.valueOf(dTestValue);
					
					sBinNo="115";
					pParam.rtSblAllCode.add("HOLD");
					pParam.rtYield = pStd.LIMIT_YIELD;
					pParam.rtSBL1 = "LOT";
					pParam.rtSBL2 = "BIN";
					pParam.rtSBL3.add(getSBL3(sBinNo,pParam, pStd));
					pParam.rtSblAllResult.add(sBinNo);	// subbin 번호를 넣는다.
					pParam.rtSblAllComment.add("");
					pParam.rtReasonCode.add(getReasonCode(sBinNo, "HOLD", pParam, pStd));
				}
			}
			// Test 결과값 중 116번이 있다면
			if(sBinNo.equals("0") && pParam.hmFinalTestSubbin.containsKey(116))
			{
				dTestValue = Double.valueOf(pParam.hmFinalTestSubbin.get(116))/Double.valueOf(pParam.SIMAX_QTY) * 100.0;
				dSpecValue = pStd.dLIMIT_116;
				
				pParam.logReason += " BIN=116";
				pParam.logReason += " (dTestValue>dSpecValue)=(" + String.valueOf(dTestValue) + ">" + String.valueOf(dSpecValue)+ ")" ;
	
				if(dTestValue>dSpecValue)
				{
					pParam.logSpec = String.valueOf(dSpecValue);
					pParam.logValue = String.valueOf(dTestValue);
					
					sBinNo="116";
					pParam.rtSblAllCode.add("HOLD");
					pParam.rtYield = pStd.LIMIT_YIELD;
					pParam.rtSBL1 = "LOT";
					pParam.rtSBL2 = "BIN";
					pParam.rtSBL3.add(getSBL3(sBinNo,pParam, pStd));
					pParam.rtSblAllResult.add(sBinNo);	// subbin 번호를 넣는다.
					pParam.rtSblAllComment.add("");
					pParam.rtReasonCode.add(getReasonCode(sBinNo, "HOLD", pParam, pStd));
				}
			}
			if(!sBinNo.equals("0"))
			{
				pParam.logReason ="[dTestValue>dSpecValue] =>" + pParam.logReason;
				pParam.logPass = "N";
				pParam.makeLogResult();
			}
		}catch(Exception e)
		{
			pParam.logPass = "ERROR";
			pParam.logReason = e.toString();
			Sbl_Utils.sblmakeLog(pParam.MODEL, pParam.LOTID+" [[ERROR]] [JudgeSubbin]  spcialSubBin() error >>>  "+e.toString() );
		}
	}*/
	
}
