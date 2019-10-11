package di.uniba.ardimento.utils;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import gr.spinellis.ckjm.CkjmOutputHandler;
import gr.spinellis.ckjm.ClassMetrics;
/**
 * Handler class that allows you to save the metrics of the analyzed file in a sigleton
 * 
 * @author Francesco Mininno
 *
 */
public class PrintResultsOnVideo implements CkjmOutputHandler {

	 public PrintResultsOnVideo(PrintStream p) {
	    }
	 
	//Singleton class where to save the metrics
	ClassMetricsDataContainer outputClass = ClassMetricsDataContainer.getIstance();
	//Support variable to save the metrics of a analyzed .class
	ClassMetricsData temp;
	
	@Override
	public void handleClass(String name, ClassMetrics c) {
		// TODO Auto-generated method stub
        temp = new ClassMetricsData(name,c);
        //Call the method to populate the classmetrics container
        outputClass.loadMetrics(temp);
        //CC calculation
        printCC(c);   	
	}
	
	/**
	 * Calculation of the CC metric and saving in the outputClass hash map
	 * @param cm
	 * @return
	 */
	 private void printCC(ClassMetrics cm) {
	        List<String> methodNames = cm.getMethodNames();
	        Iterator<String> itr = methodNames.iterator();
	        String name;
	        
	        while (itr.hasNext()) {
	            name = itr.next();
	            outputClass.data.get(outputClass.data.size()-1).getCc().put(name, cm.getCC(name));	         
	        }	        
	    }
}