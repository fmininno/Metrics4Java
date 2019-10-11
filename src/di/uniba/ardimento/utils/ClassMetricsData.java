package di.uniba.ardimento.utils;

import java.util.HashMap;
import java.util.Map;

import gr.spinellis.ckjm.ClassMetrics;

/**
 * Singleton class which contains the results of the analysis of files to be shown to the user on the screen
 * @author Francesco Mininno
 *
 */
public class ClassMetricsData {
	
	private String methodName;
	private double Amc;
	private int Loc;
	private int Wmc;
	private int Noc;
	private int Rfc;
	private int Dit;
	private int Lcom;
	private double Lcom3;
	private int Npm;  
	private double Dam;
	private int Moa;
	private double Mfa;
	private double Cam;
	private int Ic;
	private int Cbm;
	private double Cbo; 
	private double Ca;
	private double Ce;
	//Map containing the name of the method with the corresponding CC
	public Map<String, Integer > Cc = new HashMap<String, Integer >();
	public Map<String, Integer> getCc() {
		return Cc;
	}

	public String getmethodName() {
		return methodName;
	}
	
	public void setCc(Map<String, Integer> cc) {
		Cc = cc;
	}


	public int getLoc() {
		return Loc;
	}


	public void setLoc(int loc) {
		Loc = loc;
	}


	public int getWmc() {
		return Wmc;
	}


	public void setWmc(int wmc) {
		Wmc = wmc;
	}


	public int getNoc() {
		return Noc;
	}


	public void setNoc(int noc) {
		Noc = noc;
	}


	public int getRfc() {
		return Rfc;
	}


	public void setRfc(int rfc) {
		Rfc = rfc;
	}


	public int getDit() {
		return Dit;
	}


	public void setDit(int dit) {
		Dit = dit;
	}


	public int getLcom() {
		return Lcom;
	}


	public void setLcom(int lcom) {
		Lcom = lcom;
	}


	public double getLcom3() {
		return Lcom3;
	}


	public void setLcom3(double lcom3) {
		Lcom3 = lcom3;
	}


	public int getNpm() {
		return Npm;
	}


	public void setNpm(int npm) {
		Npm = npm;
	}


	public double getDam() {
		return Dam;
	}


	public void setDam(double dam) {
		Dam = dam;
	}


	public int getMoa() {
		return Moa;
	}


	public void setMoa(int moa) {
		Moa = moa;
	}


	public double getMfa() {
		return Mfa;
	}


	public void setMfa(double mfa) {
		Mfa = mfa;
	}


	public double getCam() {
		return Cam;
	}


	public void setCam(double cam) {
		Cam = cam;
	}


	public int getIc() {
		return Ic;
	}


	public void setIc(int ic) {
		Ic = ic;
	}


	public double getAmc() {
		return Amc;
	}


	public void setAmc(double amc) {
		Amc = amc;
	}


	public double getCbo() {
		return Cbo;
	}


	public void setCbo(double cbo) {
		Cbo = cbo;
	}


	public double getCa() {
		return Ca;
	}


	public void setCa(double ca) {
		Ca = ca;
	}


	public double getCe() {
		return Ce;
	}


	public void setCe(double ce) {
		Ce = ce;
	}


	public int getCbm() {
		return Cbm;
	}


	public void setCbm(int cbm) {
		Cbm = cbm;
	}
	
	public ClassMetricsData() {}
	/**
	 * Constructor that initializes ClassMetricsData by assigning name and metrics
	 * @param name
	 * @param c
	 */
	public ClassMetricsData (String name, ClassMetrics c) {
		methodName = name;
		Wmc = c.getWmc();
	    Dit = c.getDit();
	    Noc = c.getNoc();
	    Cbo = c.getCbo();
	    Rfc = c.getRfc(); 
	    Lcom =  c.getLcom();
	    Ca = c.getCa();
	    Ce = c.getCe();
	    Npm =  c.getNpm();
	    Lcom3 =  c.getLcom3();
	    Loc =  c.getLoc();
	    Dam =  c.getDam();
	    Moa = c.getMoa();
	    Mfa = c.getMfa();
	    Cam = c.getCam();
	    Ic =  c.getIc();
	    Cbm =  c.getCbm();
	    Amc = c.getAmc();
	}
	/**
	 * Copy metrics from a ClassMetrics instance
	 * @param c
	 */
	public void copyResults(ClassMetrics c) {
		Wmc = c.getWmc();
	    Dit = c.getDit();
	    Noc = c.getNoc();
	    Cbo = c.getCbo();
	    Rfc = c.getRfc(); 
	    Lcom =  c.getLcom();
	    Ca = c.getCa();
	    Ce = c.getCe();
	    Npm =  c.getNpm();
	    Lcom3 =  c.getLcom3();
	    Loc =  c.getLoc();
	    Dam =  c.getDam();
	    Moa = c.getMoa();
	    Mfa = c.getMfa();
	    Cam = c.getCam();
	    Ic =  c.getIc();
	    Cbm =  c.getCbm();
	    Amc = c.getAmc();
	}
}
