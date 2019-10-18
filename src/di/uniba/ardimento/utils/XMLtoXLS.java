package di.uniba.ardimento.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import di.uniba.ardimento.gui.dataMenu;
import di.uniba.ardimento.gui.mainMenu;
/**
 * Class that allows you to convert the XML file generated by Jasome into an XLS file
 * @author Francesco Mininno
 *
 */
public class XMLtoXLS {

	private static Logger logger = LogManager.getLogger(mainMenu.class);
	private static HSSFWorkbook workbook;
	public static int _notProcessable = 0;
	
	public static void  convert(String filename){ 
		logger.info(Settings.getTime() + "XML TO XLS CONVERSION");
		//System.out.println("FILE NAME C "+ filename);
		
		XLSJasomeFormat dataDocument = new XLSJasomeFormat();
		TreeMap<String,String> pkgMetrics = new TreeMap<String,String>();
		TreeMap<String,String> clsMetrics = new TreeMap<String,String>();
		TreeMap<String,String> methodMetrics = new TreeMap<String,String>();
		String _name = ""; //Package
		String _nameClass="";
		//URL pathExample = XMLtoXLS.class.getClassLoader().getResource("jasome_tool/");	
	    try (
	 
	        BufferedReader reader = Files.newBufferedReader(Paths.get(Settings._JARPATH + "/jasome_tool/"+filename+".xml"), Charset.forName("UTF-8"))) 
	    {
	        String line = StringUtils.EMPTY;
	        while ((line = reader.readLine()) != null) {
	    
	            if(line.contains("<Packages>"))
	            	//System.out.println(line);
	            {
	            	//Read package name
	            	String tagPackage = line.trim();
	            	boolean endPackages = false;
	            	//addato || tagpackage.compareto /package
	            	while(tagPackage.compareTo("<Packages>") == 0  || tagPackage.compareTo("</Package>") == 0 && endPackages == false ) 
	            	{
	            		String nextLine = reader.readLine().trim();
	            		
		                _name = extractValue(nextLine, "<Package name=\"", "\">");
		 boolean endFile = false; //verifico che il prossimo tag è fine packages
		 if(_name.compareTo("</Packages>") == 0 ) endFile=true; //add
		           if (nextLine.compareTo("</Package>") != 0 && endFile==false) 
		           {
		        	   //MOD
		        	   pkgMetrics = new TreeMap<String,String>();
		        	   
		                //System.out.println("Package name:"+ _name);
		                String tagMetrics = reader.readLine().trim();		     
		                //System.out.println("Metrics of package:"+_name);
		                if( tagMetrics.compareTo("<Metrics>")==0) 
		                {
		                	nextLine = reader.readLine().trim();
		                	while(nextLine.compareTo("</Metrics>") !=0) 
		                	{
		 	            	 String _metricName = takeSymbol(nextLine,"name=");
		 	            	 String _metricValue = takeSymbol(nextLine,"value=");
		 	            	 //Populate the hashmap with the package metrics
		 	            	 pkgMetrics.put(_metricName,_metricValue);
		 	         
		 	            	 //System.out.println(_metricName + "=" + _metricValue);
		 	            	 nextLine = reader.readLine().trim();		 	         
		                	}
		                	//System.out.println("end Package :" +_name);
		                	//package metrics termination
		      
		                }
		                //Assign a package to dataDocument.packages, and the related metrics
		                dataDocument.packages.put(_name,pkgMetrics);
		                //System.out.println("HASHMAP - PACKAGE METRICS" + dataDocument.packages);
		            
		                String tagClasses = reader.readLine().trim();
		                
		                if(tagClasses.compareTo("<Classes>") == 0)
		                {	
		                	//Control variable
		                	boolean endClasses = false;
		                	while(tagClasses.compareTo("<Classes>") == 0 || tagClasses.compareTo("</Class>") == 0 && endClasses == false)
		                	{			               
		                	  //System.out.println("CLASS - TagCLasses : ");
		                	  //System.out.println(tagClasses);
		                
		                	  String nextline3 = reader.readLine().trim();
	                if (nextline3.compareTo("</Classes>") != 0 ) 
	                {
			           	  
		                	  _nameClass = takeSymbol(nextline3.trim(), "<Class name=");
		                 	 //System.out.println("Class : " + _nameClass);
		                	  
		                	  String tagMetricsClass = reader.readLine().trim();
		                	  //System.out.println("Metrics of Class : "+ _nameClass);
		                	  if( tagMetricsClass.compareTo("<Metrics>")==0) 
				                {
				                	nextLine = reader.readLine().trim();
				                	clsMetrics = new TreeMap<String,String>();
				                	while(nextLine.compareTo("</Metrics>") !=0) 
				                	{
				 	            	 String _metricName = takeSymbol(nextLine,"name=");
				 	            	 String _metricValue = takeSymbol(nextLine,"value=");
				 	            	 
				 	            	 //Load hashmap with the metrics of a class
				 	            	 clsMetrics.put(_metricName,_metricValue);
				 	            	 
				 	            	 //System.out.println(_metricName + " = " + _metricValue);
				 	            	 nextLine = reader.readLine().trim();
				 	         
				                	}
				                	//System.out.println("end Class :" +_nameClass);
				                	//Write a class with its metrics
				                	dataDocument.classes.put(_name+"."+_nameClass,clsMetrics);
				                	//System.out.println("METRICHE CLASSE : " + _nameClass + clsMetrics);
				                	//System.out.println("HASH-MAP - Class Metrics : "+dataDocument.classes);
				                	//class metrics termination
				      
				                }
		                	  String tagMethods = reader.readLine().trim();
		                	  //System.out.println("Verifico : " + tagMethods);
		                	  if(tagMethods.compareTo("<Methods>") == 0)
		                	  {	
		                		  //Control variable 
		                		  boolean endClass = false;
		                		  while(tagMethods.compareTo("<Methods>") == 0 || tagMethods.compareTo("</Method>") == 0 && endClass == false)
		                		  {	
		                			  String nextline2 = reader.readLine().trim();
		                			  if (nextline2.compareTo("</Methods>") != 0 ) 
		                			  {   //If there are methods            			  
			                			  String _methodName = takeSymbol(nextline2, "name=");
			                			  //System.out.println(_methodName);
			                			  String tagMetricsMethod= reader.readLine().trim();
			                			  //System.out.println("Metrics of method : "+ _methodName);
					                	  if( tagMetricsMethod.compareTo("<Metrics>")==0) 
							                {					             
					                		  	methodMetrics = new TreeMap<String,String>();
							                	nextLine = reader.readLine().trim();
							                	while(nextLine.compareTo("</Metrics>") !=0) 
							                	{
							 	            	 String _metricName = takeSymbol(nextLine,"name=");
							 	            	 String _metricValue = takeSymbol(nextLine,"value=");
							 	            	 //Load method metrics
							 	            	 methodMetrics.put(_metricName,_metricValue);
							 	            	 //System.out.println(_metricName +" = " + _metricValue);
							 	            	 nextLine = reader.readLine().trim();
							 	         
							                	}
							                	//System.out.println("end Method :" +_methodName);
							                	//Load method metrics
							                	dataDocument.methods.put(_name+"."+_nameClass+"-"+_methodName,methodMetrics);
							                	//System.out.println("HASHMAP - METHOD METRICS : " + dataDocument.methods);
							                	//class metric termination 
							      
							                }
					                	 
					                	  tagMethods = reader.readLine().trim();//read next line after </Metrics> ie </Method>
					                						                	  
		                			  }else endClass = true; //otherwise end of class 	  
		                		  }
		                		  
		                	  }
		                	
		                	//read </Class> - finished the Methods of the previous class
		                	  tagClasses = reader.readLine().trim();
		                	  //System.out.println("Next class : " + tagClasses);
		              }else endClasses = true;
		                	}
		                  
		                }//System.out.println("End Packages");
		                //else others tags
		                
		                //1
		                tagPackage = reader.readLine().trim();
		                //System.out.println("netx package :" + tagPackage);
	            	}else {
	            		//System.out.println("FINE PACKAGES = TRUE");
	            		endPackages = true;} //otherwise end of packages
	            }
	          }
	           
	        }
	        //End elaboration
	        logger.info(Settings.getTime() + "XML TO XLS CONVERSION TERMINATED");
	        //System.out.println("END OF PACKAGE CONVERSION : " + dataDocument.packages.keySet());
	        //System.out.println("PACKAGE METRICS : " + 
	        		//dataDocument.packages);
	        //System.out.println("CLASS METRICS : " + 
	        		//dataDocument.classes);
	        //System.out.println("METHODS METRICS : " + 
	        		//dataDocument.methods);
	        
	        //Generate the XLS file
	        generateXLS(dataDocument,filename);
	    } catch (IOException e) {
	    		//Count jasome not processable input files
	    		_notProcessable++;
	    		System.out.println("ERROR: JASOME CANNOT PROCESS INPUT");
	    		//System.out.println("not processable update : " + _notProcessable);
	    		logger.error(Settings.getTime() + "JASOME OUTPUT " + filename +".xml ERROR :" + e.getMessage());
		        //JOptionPane.showMessageDialog(null,"Error in the parsing XML metrics result file to XLS","Error",JOptionPane.ERROR_MESSAGE);
		    }
	}
	/**
	 * Generate an xls file given an XLSJasomeFormat
	 * @param data instance of XLSJasomeFormat that will be processed and transformed into XLS format
	 * @param filename name of the xls file that will be generated
	 */
	private static void generateXLS(XLSJasomeFormat data,String filename) {
		try {
			logger.info(Settings.getTime() + "XLS GENERATION BY JASOME FORMAT : " + filename);
			System.out.println("generating xls...");
			int countc = 0;
			int countp = 0;
			int countm = 0;
			System.out.println("packages : "+countp+ "/" + data.packages.size());
			System.out.println("classes : "+countc+ "/" + data.classes.size());
			System.out.println("method : "+countm+ "/" + data.methods.size());
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");
			String xlsFileName;
			if (filename.contains(".java")) {
				xlsFileName = filename.replace(".java","") + sdf.format(timestamp) + ".xls";
			}else {
			     xlsFileName = filename + ".xls";
			}	
			FileOutputStream fileOut = new FileOutputStream(Settings._JARPATH + "/result/jasome/" + xlsFileName); 
			workbook = new HSSFWorkbook();
		//<Package
			HSSFSheet pkgSheet = workbook.createSheet("PACKAGES");
			int row = 0;
			HSSFRow rowTitle = pkgSheet.createRow(row);
			int i=0;
			//Load the first line -Name-Metrics
			HSSFCell cellTitle = rowTitle.createCell(0);
			cellTitle.setCellValue("Package-name");
			//Create an array that contains the names of the metrics
			String metricsNameAll = "A,CCRC,Ca,Ce,DMS,I,NOC,NOI,PkgRCi,PkgTCi,TLOC";	
			String[] metricsName = metricsNameAll.split(",");
			
			//Scroll through the Maps where the classes are stored with their metrics
			for (Map.Entry<String,TreeMap<String,String> > entry : data.packages.entrySet() )
			{
				//pkgName
				System.out.println(entry.getKey() + entry.getValue());
				countp++;
				System.out.println("packages : "+countp+ "/" + data.packages.size());
				
				i=1;
				for (int j = 0; j<metricsName.length; j++) 
				{
					cellTitle=rowTitle.createCell(i);					
					cellTitle.setCellValue(metricsName[j]);
					i++;
				}

				row++; //increase the line
			
				//System.out.println("ROW VALUE: "+ row);
			
				HSSFRow rowValue = pkgSheet.createRow(row); 
				HSSFCell cell = rowValue.createCell(0); 
				cell.setCellValue(entry.getKey()); 
				
				for( Map.Entry<String,String> entryX : entry.getValue().entrySet() ) 
				{
					//System.out.println("row : " + row +" = "+ entryX.getValue());
					int j = 1;
					while( rowTitle.getCell(j).getStringCellValue().compareTo(entryX.getKey() ) != 0 ) 
					{	
						//System.out.println("TITLE :" + rowTitle.getCell(j).getStringCellValue() + "   TREESET:" + entryX.getKey() );
						j++;	
					}
					//System.out.println("Cell index : " + j);
						cell = rowValue.createCell(j);
						cell.setCellValue(entryX.getValue());	
				}	
			}
			
		//Package>
		
		//-------------//
			
		//<Classi
			HSSFSheet classSheet = workbook.createSheet("CLASSES");
			row= 0;
			rowTitle = classSheet.createRow(row);
			
			//Load the first line -Name-Metrics
			cellTitle = rowTitle.createCell(0);
			cellTitle.setCellValue("Class-name");
			//Create an array that contains the names of the metrics
			metricsNameAll = "AHF,AIF,Aa,Ad,Ai,Ait,Ao,Av,ClRCi,ClTCi,DIT,HMd,HMi,LCOM*,MHF,MIF,Ma,Md,Mi,Mit,Mo,NF,NM,NMA,NMI,NOA,NOCh,NOD,NOL,NOPa,NMIR,NORM,NPF,NPM,NSF,NSM,PF,PMR,PMd,PMi,RTLOC,SIX,TLOC,WMC";		
			metricsName = metricsNameAll.split(",");
		
			//Scroll through the Maps where the classes are stored with their metrics
			for (Map.Entry<String,TreeMap<String,String> > entry : data.classes.entrySet() )
			{
			//className
			System.out.println(entry.getKey() + entry.getValue());
			countc++;
			System.out.println("classes : "+countc+ "/" + data.classes.size());
		
		
				i=1;
				for (int j = 0; j<metricsName.length; j++) 
				{
					cellTitle=rowTitle.createCell(i);					
					cellTitle.setCellValue(metricsName[j]);
					i++;
				}

				row++; //increase the line
			
				//System.out.println("ROW VALUE: "+ row);
			
				HSSFRow rowValue = classSheet.createRow(row); 
				HSSFCell cell = rowValue.createCell(0); 
				cell.setCellValue(entry.getKey()); 
				
				for( Map.Entry<String,String> entry3 : entry.getValue().entrySet() ) 
				{
					//System.out.println("row : " + row +" = "+ entry3.getValue());
					int j = 1;
					while( rowTitle.getCell(j).getStringCellValue().compareTo(entry3.getKey() ) != 0 ) 
					{	
						//System.out.println("TITLE :" + rowTitle.getCell(j).getStringCellValue() + "   TREESET:" + entry3.getKey() );
						j++;	
					}
					//System.out.println("Cell index : " + j);
						cell = rowValue.createCell(j);
						cell.setCellValue(entry3.getValue());	
				}	
			}
		//Classi>
		//-------------------//
		
		//<Metodi
			HSSFSheet methodsSheet = workbook.createSheet("METHODS");
			row= 0;
			rowTitle = methodsSheet.createRow(row);
			
			//Load the first line -Name-Metrics
			cellTitle = rowTitle.createCell(0);
			cellTitle.setCellValue("Method-name");
			//Create an array that contains the names of the metrics
			metricsNameAll = "Ci,Di,Fin,Fout,IOVars,MCLC,NBD,NCOMP,NOP,NVAR,Si,TLOC,VG";		
			metricsName = metricsNameAll.split(",");
		
			//Scroll through the maps where the classes are stored with their metrics
			for (Map.Entry<String,TreeMap<String,String> > entry : data.methods.entrySet() )
			{	
				//methodname
				System.out.println(entry.getKey() + entry.getValue());
				countm++;
				System.out.println("method : "+countm+ "/" + data.methods.size());
				
				
				i=1;
				for (int j = 0; j<metricsName.length; j++) 
				{
					cellTitle=rowTitle.createCell(i);
					cellTitle.setCellValue(metricsName[j]);
					i++;
				}

				row++; 
			
				//System.out.println("ROW VALUE: "+ row);
			
				HSSFRow rowValue = methodsSheet.createRow(row); 
				HSSFCell cell = rowValue.createCell(0); 
				cell.setCellValue(entry.getKey().replace("-", " ")); 
				
				for( Map.Entry<String,String> entry3 : entry.getValue().entrySet() ) 
				{
					//System.out.println("row : " + row +" = "+ entry3.getValue());
					int j = 1;
					while( rowTitle.getCell(j).getStringCellValue().compareTo(entry3.getKey() ) != 0 ) 
					{	
						//System.out.println("TITLE :" + rowTitle.getCell(j).getStringCellValue() + "   TREESET:" + entry3.getKey() );
						j++;	
					}
					//System.out.println("row index: " + j);
						cell = rowValue.createCell(j);
						cell.setCellValue(entry3.getValue());	
				}	
			}	
		
		//Metodi>	
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			System.out.println("Metrics export to: "+xlsFileName);
			
			//Convert xls o csv
			if(dataMenu.csvFormatJasome==1 ) {
				try {
					ExcelReading.convertXLStoCSV(xlsFileName);
					System.out.println("csv files auto-generated in the folder : result/csv/"+ xlsFileName.replace(".xls",""));
				}catch(Exception e) {
					System.out.println("ERROR : " + e);
				}	
			}
			
			if(dataMenu.xmlFormatJasome==1) {
				//System.out.println("xml file generated in the folder : result/xml/");
				//InputStream inp = new FileInputStream(Settings._JARPATH + "/jasome_tool/" + filename+".xml");
				//Copia file xml prima di eliminarlo
				
			}

	        Files.deleteIfExists(Paths.get(Settings._JARPATH + "/jasome_tool/" + filename+".xml")); 
			System.out.println("Success");
			logger.info(Settings.getTime() + "METRICS FILE GENERATED : " + xlsFileName);
					
		}catch(Exception e){
			logger.error(Settings.getTime() + e.getMessage());
			System.out.println("ERROR : " + e);
		}
		
	}
	/**
	 * Extracts a value between two strings
	 * @param line the string on which the function is applied
	 * @param prefix prefix removed
	 * @param postfix postfix removed
	 * @return
	 */
    private static String extractValue(String line, String prefix, String postfix) {
        String value = line.trim().replaceAll(prefix, "");
        value = value.replaceAll(postfix, "");
        return value;
    }
   /**
    * Extracts a value by replacing the symbol
    * @param line the string on which the function is applied
    * @param symbol symbolo replaced
    * @return
    */
    private static String takeSymbol(String line,String symbol) {
    	String tempString = line;
    	String string2 = tempString.trim().replaceAll("\"","-");
    	
    	String[] words = string2.trim().split("-");
    	//System.out.prin0tln(words);
    	for(int i = 0; i < words.length; i++) {
    		//System.out.println("word:"+words[i]);
    		if (words[i].compareTo(" "+symbol)==0) return words[i+1]; 
    		else if(words[i].compareTo(symbol) == 0) return words[i+1]; //Variant for reading class name  
    	}
    	return words[words.length-4];
    }
    
}