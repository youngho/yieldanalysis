package com.tptech.mes;

import java.util.Scanner;

import com.tptech.Hist_Variable;

public class H101ClientTest_end {

	public static void main(String[] args) {
		H101ClientTest_end ct = new H101ClientTest_end();
		
		if(ct.InitMsgHandler() == false){
			System.out.println("false");
			return ;
		}
		
		
		if(ct.ViewLot() == false)
		{
			ct.TermMsgHandler();
			return ;
		}
		
		ct.TermMsgHandler();
	}
	
	public boolean InitMsgHandler()
	{
		if (h101stub.getInstance().init("TEST_CLIENT", 2, 
				"12.230.54.47:10101", 10104) == false)
		{
			return false;
		}
		
		EISCaster.setEISChannel("/MPJY/EISServer");
		EISCaster.setEISTTL(60000);
		
		return true;
	}

		
	public boolean TermMsgHandler()
	{
        try 
        {
        	h101stub.getInstance().term();

		} 
        catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		
		return true;
	}
	
	public boolean ViewLot()
	{
		EISType.BETSEIS_Lot_End_Req_In_Tag in = new EISType.BETSEIS_Lot_End_Req_In_Tag();
		
		String msg = Hist_Variable.ngMap.get("SBL_YIELD_RESULT");
		
//		Scanner scan = new Scanner(System.in); 
		
//		in.h_factory = "HMKA1";
//		in.h_language = '2';
//		in.h_password = "1";
//		in.h_proc_step = '1';
//		in.h_user_id = "1070529";
//		in.lot_id = "PBPT080";
		in.h_passport = "";//""
		in.h_language=1;//1
		in.h_factory="HMKT1";//HMBT1
		in.h_user_id="ADMIN";//4
		in.h_password="";
		in.h_proc_step=1; //1
		in.factory="HMKT1"; //HMBT1
		in.res_id="T2413A";
		in.lot_id="FKG971.1-2";
		
	

		
		EISType.BETSEIS_Lot_End_Req_Out_Tag out = new EISType.BETSEIS_Lot_End_Req_Out_Tag();
		
		boolean flag = EISCaster.BETSEIS_Lot_End_Req(in, out);
		
 
		
		System.out.println("h_status_value : " + out.h_status_value);
		System.out.println("h_msg_code : " + out.h_msg_code);
		System.out.println("h_msg : " + out.h_msg);
		System.out.println("lot_id : " + out.lot_id);

		
		return false; //flag;
	}
}
