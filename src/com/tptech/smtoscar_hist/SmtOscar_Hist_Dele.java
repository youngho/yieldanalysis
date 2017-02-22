package com.tptech.smtoscar_hist;import java.util.Vector;import com.tptech.isi.SmtIsi_Parameter;import com.tptech.isi.SmtIsi_Stdinfo;/** * @author  Administrator */
public class SmtOscar_Hist_Dele {
	/**	 * @uml.property  name="dao"	 * @uml.associationEnd  	 */
	static SmtOscar_Hist_Dao dao = new SmtOscar_Hist_Dao();	//SBL	public Vector<String[]> getLastTable() 	{		return dao.getLastTable();	}		public Vector<String[]> getExceptLot(String pLotid, String pExcept)	{		return dao.getExceptLot(pLotid, pExcept);	}		// OSCAR	
	public int SMTOSCAR_DATA_CHECK(String LotId, String ProcessId, String ProductId) {		return dao.SMTOSCAR_DATA_CHECK(LotId, ProcessId, ProductId);	}
/*	public void Select_V_Wip() {		dao.Select_V_Wip();	}*/
	public void Select_Last_Table()	{		dao.Select_Last_Table();	}
/*	public void Select_LineId() {		dao.Select_LineId();	}	*/
	public static void Insert_Test_History_Err(String sFile){		dao.Insert_Test_History_Err(sFile);	}	
	public void Insert_Test_History(String sFile){		dao.Insert_Test_History(sFile);	}	public void Select_FRLot() {
		dao.Select_FRLot();	}	public void insertResult(SmtIsi_Parameter parameter, SmtIsi_Stdinfo stdinfo) {		dao.insertResult(parameter, stdinfo);	}
}
