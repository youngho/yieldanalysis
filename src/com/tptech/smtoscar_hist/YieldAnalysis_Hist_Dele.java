package com.tptech.smtoscar_hist;/** * @author  Administrator */
public class YieldAnalysis_Hist_Dele {
	/**	 * @uml.property  name="dao"	 * @uml.associationEnd  	 */
	static YieldAnalysis_Hist_Dao dao = new YieldAnalysis_Hist_Dao();	public void insertGasi(int seq, String shotStartTime, String shotEndTime, String dutMainBin) {	    // gasi 데이터	     dao.insertGasi(seq, shotStartTime, shotEndTime, dutMainBin);	}		public void insertNgBin(String mainBin , String subBin) {        // sub bin 데이터        dao.insertNgBin(mainBin, subBin);    }		public void insertMainBin(String testBin, String handlerBin, String opBin) {        // main bin 데이터        dao.insertMainBin(testBin, handlerBin, opBin);    }		public void insertTDBI(int biSEQ, int biProcessCode, int biBoardId, int biZoneNumber,             int biSlotNumber, String biSocketX, String biSocketY, String biSocketNumber) {        // TDBI insert        dao.insertTDBI(biSEQ, biProcessCode, biBoardId, biZoneNumber, biSlotNumber, biSocketX, biSocketY, biSocketNumber);    }
}
