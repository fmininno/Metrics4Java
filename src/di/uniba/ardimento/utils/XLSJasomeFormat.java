package di.uniba.ardimento.utils;

import java.util.HashMap;
import java.util.TreeMap;
/**
 * Class representing a metric computation file of the Jasome tool
 * @author Francesco Mininno
 *
 */
public class XLSJasomeFormat {
	
	//Declaration
	public TreeMap<String, TreeMap<String,String> > packages;
	public TreeMap<String, TreeMap<String,String> > classes; 
	public TreeMap<String,  TreeMap<String,String> > methods;
	/**
	 * Constructor
	 */
	public  XLSJasomeFormat() {
		packages = new TreeMap<String, TreeMap<String,String> >();
		classes = new TreeMap<String, TreeMap<String,String> >();
		methods = new TreeMap<String, TreeMap<String,String> >();
	}
	
}
