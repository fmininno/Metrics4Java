package di.uniba.ardimento.data;
import java.io.File;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import di.uniba.ardimento.gui.mainMenu;
import di.uniba.ardimento.utils.CSVToExcelConverter;
import di.uniba.ardimento.utils.CheckBoxMetrics;
import di.uniba.ardimento.utils.ClassMetricsData;
import di.uniba.ardimento.utils.ClassMetricsDataContainer;
import di.uniba.ardimento.utils.PrintResultsOnVideo;
import di.uniba.ardimento.utils.Settings;
import gr.spinellis.ckjm.CkjmOutputHandler;
import gr.spinellis.ckjm.MetricsFilter;
/**
 * Instance of the abstract class ToolAnalyzer, used to use the CKJM library
 * @author Francesco Mininno
 *
 */
public class CkjmTool extends ToolAnalyzer {
	//ClassLogger
	private final Logger logger = LogManager.getLogger(mainMenu.class);
	/**
	 * Instantiates a CkjmTool by assigning the name and list of work files
	 * @param name the name that identifies the tool
	 * @param fileNames loaded file array
	 */
	
	public CkjmTool(String name,File fileNames[]) {
		setName(name);
		setData(fileNames);
	}
	
	/*
	 * Metric calculation
	 */
	@Override
	public void calculateMetrics() {
		
		System.out.println("Start Ckjm Tool... ");
		logger.info(Settings.getTime() + "CKJM TOOL START");
		//I read the uploaded files
		File files[] = getData();
		//Path of the file
		String name[] = new String[1];
		name[0] = files[0].getPath();
		//I write on file assigning the name of the uploaded file
		  try {
			//Singleton class where to save the metrics
			ClassMetricsDataContainer outputClass = ClassMetricsDataContainer.getIstance();
			outputClass.setFileName(files[0].getName());
			//Check saved file name
			//System.out.println("ClassMetricsDataContainer name : " + outputClass.fileName);
			  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(Settings.getTime() + e.getMessage());
			System.out.println("ERROR : " + e);
		}

		CkjmOutputHandler handler = new PrintResultsOnVideo(System.out);
		//Metric calculation using the variable 'name' which contains the path of the uploaded file
		try{
			MetricsFilter.runMetrics(name, handler, true);	
			System.out.println("Success");
		}catch(Exception e) {
			logger.error(Settings.getTime() + e.getMessage());
			System.out.println("Metric calculation error :" + e);
		}
	}
	/**
	 * Export the metrics to a csv file
	 */
	@Override
	public void exportMetricsCSV() {
		System.out.println("METRIC EXPORTS...");
		PrintStream console = System.out;
		try {			
			ClassMetricsDataContainer outputClass = ClassMetricsDataContainer.getIstance();
			CheckBoxMetrics isCheck = CheckBoxMetrics.getIstance();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");
	/*
	 * If i want to save the metrics except CC
	 */
	if(isCheck.Cc == false ) {
		String newfilename = outputClass.fileName;
		newfilename = newfilename.replace(".class","");
		newfilename = newfilename.replace(".jar", "");
		
		//String userDir = System.getProperty("user.dir");
		//System.out.println("JARPATH:" + Settings._JARPATH);
		File fileOut = new File(Settings._JARPATH + "/result/csv/" + newfilename + sdf.format(timestamp) + ".csv");
			PrintStream o = new PrintStream(fileOut);
			System.setOut(o);
			//Support PrintStream to write to File
			PrintStream p = System.out;
			StringBuilder header = new StringBuilder("classname");
			//wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc
			if(isCheck.Wmc == true ) header.append(",wmc");
			if(isCheck.Dit == true ) header.append(",dit");
			if(isCheck.Noc == true) header.append(",noc");
			if(isCheck.Cbo == true) header.append(",cbo");
			if(isCheck.Rfc == true) header.append(",rfc");
			if(isCheck.Lcom == true) header.append(",lcom");
			if(isCheck.Ca == true) header.append(",ca");
			if(isCheck.Ce == true) header.append(",ce");
			if(isCheck.Npm == true) header.append(",npm");
			if(isCheck.Lcom3 == true) header.append(",lcom3");
			if(isCheck.Loc == true) header.append(",loc");
			if(isCheck.Dam == true) header.append(",dam");
			if(isCheck.Moa == true) header.append(",moa");
			if(isCheck.Mfa == true) header.append(",mfa");
			if(isCheck.Cam == true) header.append(",cam");
			if(isCheck.Ic == true) header.append(",ic");
			if(isCheck.Cbm == true) header.append(",cbm");
			if(isCheck.Amc == true) header.append(",amc");
			p.println(header);
			//Support arrays to read the metrics
			ArrayList<ClassMetricsData>  c = new ArrayList<ClassMetricsData>();
			c = outputClass.data;
			
			for(int i = 0; i < c.size(); i++) {
				StringBuilder metrics = new StringBuilder(c.get(i).getmethodName());
				if(isCheck.Wmc == true ) 	metrics.append("," + c.get(i).getWmc() );
				if(isCheck.Dit == true ) 	metrics.append("," + c.get(i).getDit() );
				if(isCheck.Noc == true)  	metrics.append("," + c.get(i).getNoc() );
				if(isCheck.Cbo == true)  	metrics.append("," + c.get(i).getCbo() );
				if(isCheck.Rfc == true)  	metrics.append("," + c.get(i).getRfc() );
				if(isCheck.Lcom == true) 	metrics.append("," + c.get(i).getLcom() );
				if(isCheck.Ca == true) 		metrics.append("," + c.get(i).getCa() );
				if(isCheck.Ce == true) 	 	metrics.append("," + c.get(i).getCe() );
				if(isCheck.Npm == true) 	metrics.append("," + c.get(i).getNpm() );
				if(isCheck.Lcom3 == true) 	metrics.append("," + c.get(i).getLcom3() );
				if(isCheck.Loc == true) 	metrics.append("," + c.get(i).getLoc() );
				if(isCheck.Dam == true) 	metrics.append("," + c.get(i).getDam() );
				if(isCheck.Moa == true) 	metrics.append("," + c.get(i).getMoa() );
				if(isCheck.Mfa == true) 	metrics.append("," + c.get(i).getMfa() );
				if(isCheck.Cam == true) 	metrics.append("," + c.get(i).getCam() );
				if(isCheck.Ic == true) 		metrics.append("," + c.get(i).getIc() );
				if(isCheck.Cbm == true) 	metrics.append("," + c.get(i).getCbm() );
				if(isCheck.Amc == true) 	metrics.append("," + c.get(i).getAmc() );
				p.println(metrics);
			}
		
			System.setOut(console);
			JOptionPane.showMessageDialog(null,"SUCCESS - Metrics Export to : result/csv/"+ newfilename + sdf.format(timestamp) + ".csv","",JOptionPane.INFORMATION_MESSAGE); 
			System.out.println("SAVED METRICS IN : " + newfilename + sdf.format(timestamp) + ".csv");
			logger.info(Settings.getTime() + "SAVED METRICS IN : " + newfilename + sdf.format(timestamp) + ".csv");
	}
	/**
	 * If I want to save only CC
	 */
	else {
		String newfilename = outputClass.fileName;
		newfilename = newfilename.replace(".class","");
		newfilename = newfilename.replace(".jar", "");
		
		File fileOut = new File(Settings._JARPATH + "/result/CC/csv/" + newfilename + sdf.format(timestamp) + ".csv");
		PrintStream o = new PrintStream(fileOut);
		System.setOut(o);
		//Support PrintStream to write to File
		PrintStream p = System.out;
		p.println("classname,methodname,cc");
		ArrayList<ClassMetricsData>  c = new ArrayList<ClassMetricsData>();
		c = outputClass.data;
		//I write the name of the methods
		for(int i = 0; i < c.size(); i++) {
			for (Map.Entry<String, Integer> entry : c.get(i).Cc.entrySet()) {
			   p.println(c.get(i).getmethodName() + "," + entry.getKey().replace(",",";") + "," + entry.getValue());
			}
		}	
		
		System.setOut(console);
		JOptionPane.showMessageDialog(null,"SUCCESS - Metrics Export to : result/CC/csv/"+ newfilename + sdf.format(timestamp) + ".csv","",JOptionPane.INFORMATION_MESSAGE); 
		System.out.println("CC SAVED IN : " + newfilename + sdf.format(timestamp) + ".csv");
		logger.info(Settings.getTime() + "CC SAVED IN : " + newfilename + sdf.format(timestamp) + ".csv" );
	}
		}catch(Exception e) {
			System.setOut(console);
			logger.error(Settings.getTime() + e.getMessage());
			System.out.println("ERROR : " + e);
		}
	}
/**
 * Metrics export in XLS format
 */
	@Override
	public void exportMetricsXLS() {
		System.out.println("METRIC EXPORTS...");
		PrintStream console = System.out;
		try {
			ClassMetricsDataContainer outputClass = ClassMetricsDataContainer.getIstance();
			CheckBoxMetrics isCheck = CheckBoxMetrics.getIstance();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");		
			//If I want to save the metrics except CC
			if(isCheck.Cc == false ) {
				String newfilename = outputClass.fileName;
				newfilename = newfilename.replace(".class","");
				newfilename = newfilename.replace(".jar", "");		
				File fileOut = new File(Settings._JARPATH + "/temp/" + newfilename + sdf.format(timestamp) + ".csv");
				PrintStream o = new PrintStream(fileOut);	
				System.setOut(o);
				PrintStream p = System.out;
				StringBuilder header = new StringBuilder("classname");
				//wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc
				if(isCheck.Wmc == true ) header.append(",wmc");
				if(isCheck.Dit == true ) header.append(",dit");
				if(isCheck.Noc == true) header.append(",noc");
				if(isCheck.Cbo == true) header.append(",cbo");
				if(isCheck.Rfc == true) header.append(",rfc");
				if(isCheck.Lcom == true) header.append(",lcom");
				if(isCheck.Ca == true) header.append(",ca");
				if(isCheck.Ce == true) header.append(",ce");
				if(isCheck.Npm == true) header.append(",npm");
				if(isCheck.Lcom3 == true) header.append(",lcom3");
				if(isCheck.Loc == true) header.append(",loc");
				if(isCheck.Dam == true) header.append(",dam");
				if(isCheck.Moa == true) header.append(",moa");
				if(isCheck.Mfa == true) header.append(",mfa");
				if(isCheck.Cam == true) header.append(",cam");
				if(isCheck.Ic == true) header.append(",ic");
				if(isCheck.Cbm == true) header.append(",cbm");
				if(isCheck.Amc == true) header.append(",amc");
				p.println(header);
				//Support arrays to read the metrics
				ArrayList<ClassMetricsData>  c = new ArrayList<ClassMetricsData>();
				c = outputClass.data;
				
				for(int i = 0; i < c.size(); i++) {
					StringBuilder metrics = new StringBuilder(c.get(i).getmethodName());
					if(isCheck.Wmc == true ) 	metrics.append("," + c.get(i).getWmc() );
					if(isCheck.Dit == true ) 	metrics.append("," + c.get(i).getDit() );
					if(isCheck.Noc == true)  	metrics.append("," + c.get(i).getNoc() );
					if(isCheck.Cbo == true)  	metrics.append("," + c.get(i).getCbo() );
					if(isCheck.Rfc == true)  	metrics.append("," + c.get(i).getRfc() );
					if(isCheck.Lcom == true) 	metrics.append("," + c.get(i).getLcom() );
					if(isCheck.Ca == true) 		metrics.append("," + c.get(i).getCa() );
					if(isCheck.Ce == true) 	 	metrics.append("," + c.get(i).getCe() );
					if(isCheck.Npm == true) 	metrics.append("," + c.get(i).getNpm() );
					if(isCheck.Lcom3 == true) 	metrics.append("," + c.get(i).getLcom3() );
					if(isCheck.Loc == true) 	metrics.append("," + c.get(i).getLoc() );
					if(isCheck.Dam == true) 	metrics.append("," + c.get(i).getDam() );
					if(isCheck.Moa == true) 	metrics.append("," + c.get(i).getMoa() );
					if(isCheck.Mfa == true) 	metrics.append("," + c.get(i).getMfa() );
					if(isCheck.Cam == true) 	metrics.append("," + c.get(i).getCam() );
					if(isCheck.Ic == true) 		metrics.append("," + c.get(i).getIc() );
					if(isCheck.Cbm == true) 	metrics.append("," + c.get(i).getCbm() );
					if(isCheck.Amc == true) 	metrics.append("," + c.get(i).getAmc() );
					p.println(metrics);	
				}	
				//Convert the temp file using the converter
				System.setOut(console);
				CSVToExcelConverter.csvToXLSX(fileOut,"xls/");
				p.close();
				//The temp file should be deleted...
				JOptionPane.showMessageDialog(null,"SUCCESS - Metrics Export to : result/xls/"+ newfilename + sdf.format(timestamp) + ".xls","",JOptionPane.INFORMATION_MESSAGE); 
				System.out.println("SAVED METRICS IN : " + newfilename + sdf.format(timestamp) + ".xlsx");
				logger.info(Settings.getTime() + "SAVED METRICS IN : " + newfilename + sdf.format(timestamp) + ".xlsx");
	}
			//Save only metric CC
			else {
				String newfilename = outputClass.fileName;
				newfilename = newfilename.replace(".class","");
				newfilename = newfilename.replace(".jar", "");				
				File fileOut = new File(Settings._JARPATH + "/temp/" + newfilename + sdf.format(timestamp) + ".csv");
				PrintStream o = new PrintStream(fileOut);		
				System.setOut(o);
				//Support PrintStream to write to File
				PrintStream p = System.out;
				p.println("classname,methodname,cc");
				ArrayList<ClassMetricsData>  c = new ArrayList<ClassMetricsData>();
				c = outputClass.data;
				//I write the name of the methods
				for(int i = 0; i < c.size(); i++) {
					for (Map.Entry<String, Integer> entry : c.get(i).Cc.entrySet()) {
					   p.println(c.get(i).getmethodName() + "," + entry.getKey().replace(",",";")  + "," + entry.getValue());
					}
				}
				//Convert to XLS
				CSVToExcelConverter.csvToXLSX(fileOut,"CC/xls/");
				System.setOut(console);
				JOptionPane.showMessageDialog(null,"SUCCESS - Metrics Export to : result/CC/xls/"+ newfilename + sdf.format(timestamp) + ".xls","",JOptionPane.INFORMATION_MESSAGE); 
				System.out.println("CC SAVED IN : " + newfilename + sdf.format(timestamp) + ".xlsx");
				logger.info("CC SAVED IN : " + newfilename + sdf.format(timestamp) + ".xlsx");
				}		
	}catch(Exception e) {
		System.setOut(console);
		logger.error(Settings.getTime() + e.getMessage());
		System.out.println("ERROR : " + e);
		}
	}
	
	/**
	 * Export metrics in XML format
	 */
	@Override
	public void exportMetricsXML() {
		// TODO Auto-generated method stub
		System.out.println("METRIC EXPORTS...");
		try {
			ClassMetricsDataContainer outputClass = ClassMetricsDataContainer.getIstance();
			CheckBoxMetrics isCheck = CheckBoxMetrics.getIstance();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");		
	//If I want to save the metrics except CC
	if(isCheck.Cc == false ) 
	{
		String newfilename = outputClass.fileName;
		newfilename = newfilename.replace(".class","");
		newfilename = newfilename.replace(".jar", "");
		final String xmlFilePath = (Settings._JARPATH + "/result/xml/" + newfilename + sdf.format(timestamp) + ".xml");
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        //Root element
        Element root = document.createElement("file");
        document.appendChild(root);
		//Support arrays to read the metrics
			ArrayList<ClassMetricsData>  c = new ArrayList<ClassMetricsData>();
			c = outputClass.data;
			
			for(int i = 0; i < c.size(); i++) 
			{
				 //Class element
		        Element className = document.createElement("class");
		        root.appendChild(className);
		        //Set an attribute to staff element
	            Attr attr = document.createAttribute("name");
	            attr.setValue(c.get(i).getmethodName());
	            className.setAttributeNode(attr);
	            //Checking flag metrics
				if(isCheck.Wmc == true ) 	{
		            Element wmc = document.createElement("wmc");
		            wmc.appendChild(document.createTextNode(Integer.toString(c.get(i).getWmc())));
		            className.appendChild(wmc);
				}
				
				if(isCheck.Dit == true ) {
					 Element dit = document.createElement("dit");
			          dit.appendChild(document.createTextNode(Integer.toString(c.get(i).getDit())));
			          className.appendChild(dit);
					}
				
				if(isCheck.Noc == true) {
					Element noc = document.createElement("noc");
			        noc.appendChild(document.createTextNode(Integer.toString(c.get(i).getNoc())));
			        className.appendChild(noc);
				}
				
				if(isCheck.Cbo == true) {
					Element cbo = document.createElement("cbo");
			        cbo.appendChild(document.createTextNode(Double.toString(c.get(i).getCbo())));
			        className.appendChild(cbo);
					
				}
				if(isCheck.Rfc == true) {
					Element rfc = document.createElement("rfc");
			        rfc.appendChild(document.createTextNode(Integer.toString(c.get(i).getRfc())));
			        className.appendChild(rfc);
				}
				if(isCheck.Lcom == true) {
					Element lcom = document.createElement("lcom");
			        lcom.appendChild(document.createTextNode(Integer.toString(c.get(i).getLcom())));
			        className.appendChild(lcom);
				}
				
				if(isCheck.Ca == true) {
					Element ca = document.createElement("ca");
					ca.appendChild(document.createTextNode(Double.toString(c.get(i).getCa())));
			        className.appendChild(ca);
				
				}
				if(isCheck.Ce == true) {
					Element ce = document.createElement("ce");
			        ce.appendChild(document.createTextNode(Double.toString(c.get(i).getCe())));
			        className.appendChild(ce);
				}
				
				if(isCheck.Npm == true) {
					Element npm = document.createElement("npm");
			        npm.appendChild(document.createTextNode(Integer.toString(c.get(i).getNpm())));
			        className.appendChild(npm);
				}
				if(isCheck.Lcom3 == true) { 	
					Element lcom3 = document.createElement("lcom3");
			        lcom3.appendChild(document.createTextNode(Double.toString(c.get(i).getLcom3())));
			        className.appendChild(lcom3);
				}
				
				if(isCheck.Loc == true) {
					Element loc = document.createElement("loc");
			        loc.appendChild(document.createTextNode(Integer.toString(c.get(i).getLoc())));
			        className.appendChild(loc);
					
				}
				if(isCheck.Dam == true) {
					Element dam = document.createElement("dam");
			        dam.appendChild(document.createTextNode(Double.toString(c.get(i).getDam())));
			        className.appendChild(dam);
				}
				
				if(isCheck.Moa == true) {
					Element moa = document.createElement("moa");
			        moa.appendChild(document.createTextNode(Integer.toString(c.get(i).getMoa())));
			        className.appendChild(moa);
					
				}
				if(isCheck.Mfa == true) {
					Element mfa = document.createElement("mfa");
			        mfa.appendChild(document.createTextNode(Double.toString(c.get(i).getMfa())));
			        className.appendChild(mfa);
				}
				if(isCheck.Cam == true) {
					Element cam = document.createElement("cam");
					cam.appendChild(document.createTextNode(Double.toString(c.get(i).getCam())));
			        className.appendChild(cam);
				
				}
				if(isCheck.Ic == true) {
					Element ic = document.createElement("ic");
			        ic.appendChild(document.createTextNode(Integer.toString(c.get(i).getIc())));
			        className.appendChild(ic);
				
				}
				if(isCheck.Cbm == true) {
					Element cbm = document.createElement("cbm");
			        cbm.appendChild(document.createTextNode(Integer.toString(c.get(i).getCbm())));
			        className.appendChild(cbm);
					
				}
				if(isCheck.Amc == true) {
					Element amc = document.createElement("amc");
			        amc.appendChild(document.createTextNode(Double.toString(c.get(i).getAmc())));
			        className.appendChild(amc);
				} 
			}	
			//Create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));			
            transformer.transform(domSource, streamResult);
        
            JOptionPane.showMessageDialog(null,"SUCCESS - Metrics Export to : result/xml/"+ newfilename + sdf.format(timestamp) + ".xml","",JOptionPane.INFORMATION_MESSAGE);   

			System.out.println("SAVED METRICS IN : " + newfilename + sdf.format(timestamp) + ".xml");
			logger.info(Settings.getTime() + "SAVED METRICS IN : " + newfilename + sdf.format(timestamp) + ".xml");
	}
	//If I want to save only CC
	else 
	{
		String newfilename = outputClass.fileName;
		newfilename = newfilename.replace(".class","");
		newfilename = newfilename.replace(".jar", "");
		
		System.out.println(newfilename);
		final String xmlFilePath = (Settings._JARPATH + "/result/CC/xml/" + newfilename + sdf.format(timestamp) + ".xml");
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        //Root element
        Element root = document.createElement("file");
        document.appendChild(root);
		ArrayList<ClassMetricsData>  c = new ArrayList<ClassMetricsData>();
		c = outputClass.data;
		//I write the name of the methods
		for(int i = 0; i < c.size(); i++) {
			for (Map.Entry<String, Integer> entry : c.get(i).Cc.entrySet()) {
				//Class element
		        Element className = document.createElement("class");
		        root.appendChild(className);
		        //Set an attribute to staff element
	            Attr attr = document.createAttribute("name");
	            attr.setValue(c.get(i).getmethodName());
	            className.setAttributeNode(attr);	      
	            Element cc = document.createElement("cc");
		        cc.appendChild(document.createTextNode((entry.getKey() + " : " + entry.getValue())));
		        className.appendChild(cc);		    
			}
			
		}
		//Create the xml file
        //transform the DOM Object to an XML File
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
        transformer.transform(domSource, streamResult);
        JOptionPane.showMessageDialog(null,"SUCCESS - Metrics Export to : result/CC/xml/"+ newfilename + sdf.format(timestamp) + ".xml","",JOptionPane.INFORMATION_MESSAGE); 
		System.out.println("CC SAVED IN : " + newfilename + sdf.format(timestamp) + ".xml");
		logger.info(Settings.getTime() + "CC SAVED IN : " + newfilename + sdf.format(timestamp) + ".xml");
	}
		}catch(Exception e) {
			logger.error(Settings.getTime() + e.getMessage());
			System.out.println("ERROR : " + e);
		}
	}
	
}