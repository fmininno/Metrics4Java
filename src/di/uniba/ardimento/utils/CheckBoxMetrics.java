package di.uniba.ardimento.utils;
/**
 * Class that keeps track of checkbox values
 * @author Francesco Mininno
 *
 */
public class CheckBoxMetrics {
	
	public boolean Amc;
	public boolean Loc;
	public boolean Wmc;
	public boolean Noc;
	public boolean Rfc;
	public boolean Dit;
	public boolean Lcom;
	public boolean Lcom3;
	public boolean Npm;
	public boolean Dam;
	public boolean Moa;
	public boolean Mfa;
	public boolean Cam;
	public boolean Ic;
	public boolean Cbm;
	public boolean Cbo;
	public boolean Ca;
	public boolean Ce;
	//False 
	public boolean Cc = false;
	
private static CheckBoxMetrics istance = null;
	/**
	 * Returns the instance of the singleton class
	 * @return instance of CheckBoxMetrics
	 */
	public static synchronized CheckBoxMetrics getIstance() {
		if(istance == null)
		{
			istance = new CheckBoxMetrics();
		}
		return istance;
	}
	/**
	 *Function that keeps track of the status of the dataMenu checkbox
	 * @param st1
	 * @param st2
	 * @param st3
	 * @param st4
	 * @param st5
	 * @param st6
	 * @param st7
	 * @param st8
	 * @param st9
	 * @param st10
	 * @param st11
	 * @param st12
	 * @param st13
	 * @param st14
	 * @param st15
	 * @param st16
	 * @param st17
	 * @param st18
	 * @param st19
	 */
	public void checkMetrics(boolean st1, boolean st2, boolean st3, boolean st4, boolean st5, boolean st6, boolean st7, boolean st8
			, boolean st9, boolean st10, boolean st11, boolean st12, boolean st13, boolean st14, boolean st15, boolean st16, boolean st17
			, boolean st18, boolean st19) {
		Amc = st1;
		Lcom = st2;
		Moa = st3;
		Mfa = st4;
		Cam = st5;
		Ic = st6;
		Cbm = st7;
		Wmc = st8;
		Dit = st9;
		Noc = st10;
		Cbo = st11;
		Cc = st12;
		Ca = st13;
		Ce = st14;
		Npm = st15;
		Loc = st16;
		Lcom3 = st17;
		Rfc = st18;
		Dam = st19;
		
	}
}