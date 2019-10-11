package di.uniba.ardimento.utils;

import java.util.ArrayList;

/**
 * Singleton class that contains an array of ClassMetriscData (the computed metrics of the java .class and .jar files)
 * @author Francesco Mininno
 *
 */
public class ClassMetricsDataContainer {
	
	public String fileName;
	public ArrayList<ClassMetricsData>  data;
	private static ClassMetricsDataContainer istance = null;
	/**
	 * Returns the instance of the singleton class
	 * @return instance of ClassMetricsDataContainer
	 */
	public static synchronized ClassMetricsDataContainer getIstance() {
		if(istance == null)
		{
			istance = new ClassMetricsDataContainer();
			istance.data = new ArrayList<ClassMetricsData>();
			istance.fileName = "";	
		}
		return istance;
	}
	
	/**
	 * Reset the instance
	 */
	public void resetData() {
		istance.data = new ArrayList<ClassMetricsData>();
	}
	/**
	 * Upload ClassMetricsContainer with metrics
	 * @param metrics metrics loaded
	 */
	public void loadMetrics(ClassMetricsData metrics) {
		data.add(metrics);
	}
	/**
	 * Assign the filename
	 * @param name filename
	 */
	public void setFileName(String name) {
		fileName = name;
	}

}