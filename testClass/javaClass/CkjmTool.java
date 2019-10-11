package di.uniba.ardimento.codeanl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.tools.ant.util.FileUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import di.uniba.ardimento.gui.dataMenu;
import di.uniba.ardimento.utils.CSVToExcelConverter;
import di.uniba.ardimento.utils.CheckBoxMetrics;
import di.uniba.ardimento.utils.ClassMetricsData;
import di.uniba.ardimento.utils.ClassMetricsDataContainer;
import di.uniba.ardimento.utils.PrintResultsOnVideo;
import gr.spinellis.ckjm.CkjmOutputHandler;
import gr.spinellis.ckjm.MetricsFilter;
import gr.spinellis.ckjm.PrintPlainResults;
import gr.spinellis.ckjm.ant.PrintXmlResults;
/**
 * Istanza delle classe astratta ToolAnalyzer, usata per utilizzare la libreria CKJM.
 * @author franc
 *
 */
public class CkjmTool extends ToolAnalyzer { 
	
	/**
	 * Istanzia un CkjmTool assegnando il nome e la lista di file di lavoro
	 * @param name
	 * @param fileNames
	 */
	public CkjmTool(String name,File fileNames[]) {
		setName(name);
		//Carico i nomi dei files
		setData(fileNames);
	}
	
	/*
	 * Calcolo delle metriche
	 */
	@Override
	public void calculateMetrics() {
		
		System.out.println("CALCOLO METRICHE... ");
		//PrintStream console = System.out; 
		//Leggo i files caricati
		File files[] = getData();
		//Stringa col PATH del file caricato
		String name[] = new String[1];
		name[0] = files[0].getPath();
		//Scrivo su file assegnado il name del file caricato
		  try {
			//Classe singleton dove salvare le metriche
			ClassMetricsDataContainer outputClass = ClassMetricsDataContainer.getIstance();
			outputClass.setFileName(files[0].getName());
			//verifico nome file salavato
			System.out.println("ClassMetricsDataContainer name : " + outputClass.fileName);
			//Setto lo stream => File
			//PrintStream o = new PrintStream(new File("./result/" + files[0].getName() + ".txt"));
			//System.out.println(files[0]);
			/*
			 * Imposto o come printstream
			 */
			//System.setOut(o);
			  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//handler = new PrintXmlResults(new PrintStream(System.out));
		//usavo questo ---> CkjmOutputHandler handler = new PrintPlainResults(System.out);
		CkjmOutputHandler handler = new PrintResultsOnVideo(System.out);
		//Calcolo metriche utilizzando la variabile ( name ) che contiene il path del file caricato
		try{
			MetricsFilter.runMetrics(name, handler, true);
			//Messaggio di verifica
			
			//System.setOut(console);	
			System.out.println("SUCCESS");
		}catch(Exception e) {
			//System.setOut(console);	
			System.out.println("Errore nel calcolo delle metriche :" + e);
		}
	}
	/**
	 * Esporta le metriche in un file csv
	 */
	@Override
	public void exportMetricsCSV() {
		System.out.println("EXPORT METRICHE...");
		PrintStream console = System.out;
		try {
			ClassMetricsDataContainer outputClass = ClassMetricsDataContainer.getIstance();
			CheckBoxMetrics isCheck = CheckBoxMetrics.getIstance();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			
	/*
	 * Se voglio salvare le metriche tranne CC
	 */
	if(isCheck.Cc == false ) {
		File fileOut = new File("./result/" + outputClass.fileName + sdf.format(timestamp) + ".csv");
			PrintStream o = new PrintStream(fileOut);
			System.setOut(o);
			//PrintStream di appoggio per scrivere su File
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
			//p.println("classname,wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc");
			//Array di appoggio per leggere le metriche
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
				/*
				 p.println(
						 c.get(i).getmethodName() +
						 "," + c.get(i).getWmc() + "," + c.get(i).getDit() + "," + c.get(i).getNoc() + "," + c.get(i).getCbo() + "," + c.get(i).getRfc() +
        				 "," + c.get(i).getLcom() + "," +c.get(i).getCa()  + "," + c.get(i).getCa() + "," + c.get(i).getNpm()  + "," + c.get(i).getLcom3() +
        			     "," + c.get(i).getLoc()  + "," + c.get(i).getDam()  + "," + c.get(i).getMoa()  + "," + c.get(i).getMfa()  + "," + c.get(i).getCam() +
        			     "," + c.get(i).getIc()  + "," +  c.get(i).getCbm()  + "," + c.get(i).getAmc());
        			     */
			}
		
			System.setOut(console);
			System.out.println("SAVED METRICS IN : " + outputClass.fileName + sdf.format(timestamp) + ".csv");
	}
	/**
	 * Se voglio salvere CC salver� solo CC
	 */
	else {
		File fileOut = new File("./result/CC/" + outputClass.fileName + sdf.format(timestamp) + ".csv");
		PrintStream o = new PrintStream(fileOut);
		System.setOut(o);
		//PrintStream di appoggio per scrivere su File
		PrintStream p = System.out;
		p.println("classname,methodname,cc");
		
		ArrayList<ClassMetricsData>  c = new ArrayList<ClassMetricsData>();
		c = outputClass.data;
		//Scrivo il nome dei metodi 
		for(int i = 0; i < c.size(); i++) {
			for (Map.Entry<String, Integer> entry : c.get(i).Cc.entrySet()) {
			   p.println(c.get(i).getmethodName() + "," + entry.getKey() + "," + entry.getValue());
			}
		}
		
		System.setOut(console);
		System.out.println("CC SAVED IN : " + outputClass.fileName + sdf.format(timestamp) + ".csv");
	}
		}catch(Exception e) {
			System.setOut(console);
			System.out.println("ERRORE : " + e);
		}
		
	}
/**
 * Export metriche in formato .xls
 */
	@Override
	public void exportMetricsXLS() {
		
		
		System.out.println("EXPORT METRICHE...");
		PrintStream console = System.out;
		try {
			ClassMetricsDataContainer outputClass = ClassMetricsDataContainer.getIstance();
			CheckBoxMetrics isCheck = CheckBoxMetrics.getIstance();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			/*
			 * Se voglio salvare le metriche tranne CC
			 */
			if(isCheck.Cc == false ) {
				File fileOut = new File("./temp/" + outputClass.fileName + sdf.format(timestamp) + ".csv");
				PrintStream o = new PrintStream(fileOut);
				
				System.setOut(o);
				//PrintStream di appoggio per scrivere su File
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
				//p.println("classname,wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc");
				//Array di appoggio per leggere le metriche
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
				//Passo il file temp al converter
				System.setOut(console);
				CSVToExcelConverter.csvToXLSX(fileOut,"");
		
				p.close();
				if(fileOut.delete()){
	    			System.out.println(fileOut.getName() + " is deleted!");
	    		}else{
	    			System.out.println("Delete operation is failed.");
	    		}
				
				System.out.println("SAVED METRICS IN : " + outputClass.fileName + sdf.format(timestamp) + ".xls");
	}
			/**
			 * Salvo solo CC
			 */
			else {
				File fileOut = new File("./temp/" + outputClass.fileName + sdf.format(timestamp) + ".csv");
				PrintStream o = new PrintStream(fileOut);
			
				System.setOut(o);
				//PrintStream di appoggio per scrivere su File
				PrintStream p = System.out;
				p.println("classname,methodname,cc");
				
				ArrayList<ClassMetricsData>  c = new ArrayList<ClassMetricsData>();
				c = outputClass.data;
				//Scrivo il nome dei metodi 
				for(int i = 0; i < c.size(); i++) {
					for (Map.Entry<String, Integer> entry : c.get(i).Cc.entrySet()) {
					   p.println(c.get(i).getmethodName() + "," + entry.getKey() + "," + entry.getValue());
					}
				}
				//Converto in XLS
				CSVToExcelConverter.csvToXLSX(fileOut,"CC/");
				//
				System.setOut(console);
				System.out.println("CC SAVED IN : " + outputClass.fileName + sdf.format(timestamp) + ".xls");
				}		
	}catch(Exception e) {
		System.setOut(console);
		System.out.println("ERRORE : " + e);
		}
	}
	
	/**
	 * Esporta le metriche in formato XML
	 */
	@Override
	public void exportMetricsXML() {
		
		// TODO Auto-generated method stub
		System.out.println("EXPORT METRICHE...");
		try {
			ClassMetricsDataContainer outputClass = ClassMetricsDataContainer.getIstance();
			CheckBoxMetrics isCheck = CheckBoxMetrics.getIstance();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			
	/*
	 * Se voglio salvare le metriche tranne CC
	 */
	if(isCheck.Cc == false ) {
		final String xmlFilePath = ("./result/" + outputClass.fileName + sdf.format(timestamp) + ".xml");
		
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        // root element
        Element root = document.createElement("file");
        document.appendChild(root);
        
		//wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc
		//Array di appoggio per leggere le metriche
			ArrayList<ClassMetricsData>  c = new ArrayList<ClassMetricsData>();
			c = outputClass.data;
			
			for(int i = 0; i < c.size(); i++) {
				 // class element
		        Element className = document.createElement("class");
		        root.appendChild(className);
		        // set an attribute to staff element
	            Attr attr = document.createAttribute("name");
	            attr.setValue(c.get(i).getmethodName());
	            className.setAttributeNode(attr);
	            // WMC
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
			// create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));
			
            transformer.transform(domSource, streamResult);
      
			System.out.println("SAVED METRICS IN : " + outputClass.fileName + sdf.format(timestamp) + ".xml");
	}
	/**
	 * Se voglio salvere CC salver� solo CC
	 */
	else {
		final String xmlFilePath = ("./result/CC/" + outputClass.fileName + sdf.format(timestamp) + ".xml");
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        // root element
        Element root = document.createElement("file");
        document.appendChild(root);

		//p.println("classname,methodname,cc");
		
		ArrayList<ClassMetricsData>  c = new ArrayList<ClassMetricsData>();
		c = outputClass.data;
		//Scrivo il nome dei metodi 
		for(int i = 0; i < c.size(); i++) {
			for (Map.Entry<String, Integer> entry : c.get(i).Cc.entrySet()) {
				// class element
		        Element className = document.createElement("class");
		        root.appendChild(className);
		        // set an attribute to staff element
	            Attr attr = document.createAttribute("name");
	            attr.setValue(c.get(i).getmethodName());
	            className.setAttributeNode(attr);
	            
	            Element cc = document.createElement("cc");
		        cc.appendChild(document.createTextNode((entry.getKey() + " : " + entry.getValue())));
		        className.appendChild(cc);
		        
			   //p.println(c.get(i).getmethodName() + "," + entry.getKey() + "," + entry.getValue());
			}
			
		}
		// create the xml file
        //transform the DOM Object to an XML File
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
		
        transformer.transform(domSource, streamResult);
		
	
		System.out.println("CC SAVED IN : " + outputClass.fileName + sdf.format(timestamp) + ".xml");
	}
		}catch(Exception e) {
		
			System.out.println("ERRORE : " + e);
		}
	}
	
}

