package di.uniba.ardimento.rInsideJava;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import com.github.rcaller.datatypes.DataFrame;
import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import di.uniba.ardimento.gui.mainMenu;
import di.uniba.ardimento.utils.Settings;
/**
 * Class that generates the tests using R
 * @author Francesco Mininno
 *
 */
public class Rtest {
	
	private static Workbook workbookA;
	private static Workbook workbookB;
	private static 	int NMAXROW = 0;
	private static 	int NMAXCOL = 0;
	private static String FNAMEA = "";
	private static String FNAMEB = "";
	private static String FLAG = "";
	private static String _FILEPATH = "";
	
	private static Logger logger = LogManager.getLogger(mainMenu.class);
	/**
	 * Method that reads two excel files and invokes the method to do Wilcoxon-Mann-Whitney test for each sheet
	 * @param fileNameA	File Excel A
	 * @param fileNameB File Excel B
	 * @param tool		identifies the type of excel file analyzed according to the tool 
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public static void generateTest(String fileNameA, String fileNameB,String tool) throws EncryptedDocumentException, IOException {
		System.out.println("THE TEST IS STARTED...");
		logger.info(Settings.getTime() + "START TEST : " + fileNameA + "-" + fileNameB );
		
		if(tool=="JASOME") _FILEPATH = Settings._JARPATH + "/result/jasome/";
		if(tool=="CKJM") _FILEPATH = Settings._JARPATH + "/result/xls/";
			workbookA = WorkbookFactory.create(new File(_FILEPATH + fileNameA)); 
			workbookB = WorkbookFactory.create(new File(_FILEPATH + fileNameB));
			FNAMEA = fileNameA;
			FNAMEB = fileNameB;
			// Retrieving the number of sheets in the Workbook A
	        System.out.println("Workbook "+fileNameA + " has " + workbookA.getNumberOfSheets() + " Sheets : ");

	        Iterator<Sheet> sheetIteratorA = workbookA.sheetIterator();
	        System.out.println("Retrieving Sheets using Iterator");
	        while (sheetIteratorA.hasNext()) {
	            Sheet sheet = sheetIteratorA.next();
	            System.out.println("=> " + sheet.getSheetName());
	        }
		    //
		    // Retrieving the number of sheets in the Workbook B
	        System.out.println("Workbook "+fileNameB + " has " + workbookB.getNumberOfSheets() + " Sheets : ");

	        Iterator<Sheet> sheetIteratorB = workbookB.sheetIterator();
	        System.out.println("Retrieving Sheets using Iterator");
	        while (sheetIteratorB.hasNext()) {
	            Sheet sheet = sheetIteratorB.next();
	            System.out.println("=> " + sheet.getSheetName());
	        }
	 //
	 new Thread(new Runnable() {
	        public void run(){
	        // Getting the Sheet at index zero ( PACKAGES METRICS ) 
	        if(tool == "JASOME") //test tool type of results files
	        {
	        Sheet sheetPkgA = workbookA.getSheetAt(0);
	        Sheet sheetClassA =  workbookA.getSheetAt(1);
	        Sheet sheetMethodA = workbookA.getSheetAt(2);
	        
	        Sheet sheetPkgB = workbookB.getSheetAt(0);
	        Sheet sheetClassB =  workbookB.getSheetAt(1);
	        Sheet sheetMethodB= workbookB.getSheetAt(2);
	        LinkedHashMap <String, ArrayList<Double> > hmapPkgA;
	        LinkedHashMap <String, ArrayList<Double> > hmapPkgB;
	        LinkedHashMap <String, ArrayList<Double> > hmapClassA;
	        LinkedHashMap <String, ArrayList<Double> > hmapClassB;
	        LinkedHashMap <String, ArrayList<Double> > hmapMethodA;
	        LinkedHashMap <String, ArrayList<Double> > hmapMethodB;  
	        
	        //-----------PACKAGE METRICS ----------------------/
	        
	        //System.out.println("\n"+fileNameA);
	        hmapPkgA = readSheet(sheetPkgA);
	        //System.out.println("\n"+fileNameB);
	        hmapPkgB = readSheet(sheetPkgB);
	        doTest(hmapPkgA,hmapPkgB,"P");
	       
	        
	        //-----------CLASSES METRICS ----------------------/
	        //System.out.println("\n"+fileNameA);
	        hmapClassA = readSheet(sheetClassA);
	        //System.out.println("\n"+fileNameB);
	        hmapClassB = readSheet(sheetClassB);
	        doTest(hmapClassA,hmapClassB,"C");   
	        
	        
	        //-----------METHOD METRICS ----------------------/
	        //System.out.println("\n"+fileNameA);
	        hmapMethodA = readSheet(sheetMethodA);
	        //System.out.println("\n"+fileNameB);
	        hmapMethodB = readSheet(sheetMethodB);
	        doTest(hmapMethodA,hmapMethodB,"M");
	        
	        }
	        if(tool == "CKJM") {
	        	Sheet sheetA= workbookA.getSheetAt(0);
	            Sheet sheetB = workbookB.getSheetAt(0);
	            LinkedHashMap <String, ArrayList<Double> > hmapA;
	            LinkedHashMap <String, ArrayList<Double> > hmapB;
	            /*-----------METRICS ----------------------*/
	            //System.out.println("\n"+fileNameA);
	            hmapA = readSheet(sheetA);
	            //System.out.println("\n"+fileNameB);
	            hmapB = readSheet(sheetB);
	            doTest(hmapA,hmapB,"C");
	        }
	        
	        String filenameA = FNAMEA;
			String filenameB = FNAMEB;
			filenameA = filenameA.replace(".xlsx", "");
			filenameB = filenameB.replace(".xlsx", "");
			filenameA = filenameA.replace(".xls", "");
			filenameB = filenameB.replace(".xls", "");
	
			
    JOptionPane.showMessageDialog(null, "SUCCESS - Result test file generated in the /rScript/wTest/"+filenameA+"-"+filenameB+" directory","",JOptionPane.INFORMATION_MESSAGE);   
	System.out.println("END TEST...");
	logger.info(Settings.getTime() + "SUCCESS - Result test file generated in the /rScript/wTest/"+filenameA+"-"+filenameB+" directory");
	}
	}).start();
}
	/**
	 * Method which for each sheet invokes the method to calculate the R tests
	 * @param mpA	map file excel A
	 * @param mpB	map file excel B
	 * @param flag	sheet identifier
	 */
	public static void doTest(LinkedHashMap <String, ArrayList<Double> >mpA, LinkedHashMap <String, ArrayList<Double> > mpB,String flag) {
		//System.out.println("START");
		Iterator<Entry<String, ArrayList<Double>> > itA = mpA.entrySet().iterator();
		Iterator<Entry<String, ArrayList<Double>> > itB = mpB.entrySet().iterator();
		if ( flag == "P") FLAG = "Package";
		if ( flag == "C") FLAG = "Classes";
		if ( flag == "M") FLAG = "Methods";
		
		ArrayList<String> labelsA = new ArrayList<String>();
		ArrayList<String> labelsB = new ArrayList<String>();
	
		while (itA.hasNext() && itB.hasNext() ) {
			
			Entry<String, ArrayList<Double>> pairA = (Map.Entry<String, ArrayList<Double>>) itA.next();
			Entry<String, ArrayList<Double>> pairB = (Map.Entry<String, ArrayList<Double>>) itB.next();
			//System.out.println("A : ");
			//System.out.println(pairA.getKey() + " = " + pairA.getValue());
			//System.out.println("B : ");
			//System.out.println(pairB.getKey() + " = " + pairB.getValue());
			
			if(pairA!=null || !pairA.getKey().isEmpty() ) {
				String labelA = pairA.getKey();
				if(labelA!=null) labelsA.add(labelA);
				//String labelB = pairB.getKey();	
			}
       
	}//RUN R TEST WITH LABELS A E B
		String[] arrA = new String[labelsA.size()];
		arrA = labelsA.toArray(arrA);
		
		String[] arrB = new String[labelsB.size()];
		arrB = labelsB.toArray(arrB);
		
		runTest(arrA,mpA,mpB,flag);
	}
	
	/**
	 * Method that runs the R script for Wilcoxon-Mann-Whitney tests
	 * @param arrA	array containing the sheet labels, used as a script support
	 * @param mpA	map of the excel file sheet A
	 * @param mpB	map of the excel file sheet B
	 * @param flag	sheet identifier
	 */
	@SuppressWarnings("unused")
	public static void runTest(String[] arrA,LinkedHashMap<String, ArrayList<Double> >mpA, LinkedHashMap <String, ArrayList<Double> > mpB,String flag) {
		try {
			logger.info(Settings.getTime() + "START CORRECTNESS TEST ("+flag+")");
			System.out.println(Settings.getTime() + "START CORRECTNESS TEST ("+flag+")");
		      /**
		       * Creating an instance of RCaller
		       */
		      RCaller caller = RCaller.create();
		      RCode code = RCode.create();
		    
		      double[] _test = new double[] {0.00};
		      double[] _delta = new double[]{0.00};
		      double[] _magnitude = new double[] {0.00};
		      
		      int index_sheet = 0;
				if(flag=="P") index_sheet = 1;
				if(flag=="C") index_sheet = 2;
				if(flag=="M") index_sheet = 3;
			
		      /**
		       * Converting Java arrays to R arrays
		       */
		      code.addRCode("library('readxl')");
		      code.addRCode("library('writexl')");
		      //code.addRCode("colnames(mydataA[1])");
		      
		      /* Arrays */
		      code.addDoubleArray("test", _test);
		      code.addDoubleArray("delta", _delta);
		      code.addDoubleArray("magnitude", _magnitude);

		//System.out.println("Foglio : " + index_sheet);
		Iterator<Entry<String, ArrayList<Double>> > itA = mpA.entrySet().iterator();
		Iterator<Entry<String, ArrayList<Double>> > itB = mpB.entrySet().iterator();
		
		int count = 0;
		while (itA.hasNext() && itB.hasNext() ) {
			
			Entry<String, ArrayList<Double>> pairA = (Map.Entry<String, ArrayList<Double>>) itA.next();
			Entry<String, ArrayList<Double>> pairB = (Map.Entry<String, ArrayList<Double>>) itB.next();
			//Convert in double[]
			//Fix if pairA and pairB are empty to no crash
			//Or is correct ||
			if(pairA.getValue().isEmpty() || pairB.getValue().isEmpty()) {
				ArrayList<Double> fix_array = new ArrayList<Double>();
				fix_array.add(0.0);
				pairA.setValue(fix_array);
				pairB.setValue(fix_array);
			}
			//Or is correct ||
			//System.out.println("PRIMA - VETTORE A : "+ pairA.getKey()+" " + pairA.getValue());
			if(!pairA.getValue().isEmpty() || !pairB.getValue().isEmpty()) {
				
				double[] _x = convert(pairA.getValue());
				double[] _y = convert(pairB.getValue());
				//System.out.println("VETTORE A ?: "+ pairA.getKey()+" " + pairA.getValue());
				code.addDoubleArray("x",_x);
				code.addDoubleArray("y",_y);
				//System.out.println("VERIFICA X:" + _x[_x.length-1]);
				//System.out.println("VERIFICA Y:" + _y[_y.length-1]);
				 //code.addRCode("risultatoP<-wilcox.test(as.numeric(mydataA$"+arrA[j]+"), as.numeric(mydataB$"+arrB[j]+"))");
			      code.addRCode("risultatoP<-wilcox.test(x,y)");
			      code.addRCode("risultatoP$p.value");
			      
			      code.addRCode("test[1]<-risultatoP$p.value");
			      
			      code.addRCode("library(effsize)");
			      code.addRCode("treatment <-x");
			      code.addRCode("treatment = as.numeric(treatment)");
			      code.addRCode("control <-y");
			      code.addRCode("risultatoCliff<-cliff.delta(treatment, control)");
			      code.addRCode("delta[1]<-risultatoCliff$estimate");
			      code.addRCode("magnitude[1]<-risultatoCliff$magnitude");
			      code.addRCode("cliff.delta(treatment, control)");
		
			 code.addRCode("data.frame(test, delta, magnitude)");
			 code.addRCode("risultati_"+count+" = data.frame(test, delta, magnitude)");
			 code.addRCode("test = c(0.00)");   
			 code.addRCode("test");
			 code.addRCode("delta");
			 code.addRCode("magnitude");
			 //Index of results
			 count++;
			
			}
	     
		}
			
		//Loop to write results 
		 //Write XLS FILE
		String lista ="";
		lista="write_xlsx(list(";
		for(int j=0;j<=arrA.length-1;j++) {
		//System.out.println("LABEL " + arrA[j]);
		lista = lista.concat(""+arrA[j].replace("*","star")+" = risultati_"+j+"");
		if(j!=arrA.length-1) lista = lista.concat(",");
		
		}   
		//
		String nameFileA=FNAMEA.replace(".xlsx", "");
		nameFileA = nameFileA.replace(".xls", "");
		String nameFileB=FNAMEB.replace(".xlsx", "");
		nameFileB = nameFileB.replace(".xls", "");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");
		//Create a new folder 
		File newFolder = new File(Settings._JARPATH + "/rScript/wTest/"+nameFileA+"-"+nameFileB+"/");
		newFolder.mkdir();
		
		lista = lista.concat("),'" + Settings._JARPATH+ "/rScript/wTest/"+nameFileA+"-"+nameFileB+"/"+flag+"_ResultC"+sdf.format(timestamp)+".xlsx' )");
		//System.out.println(lista);
		
		code.addRCode(lista);
		caller.setRCode(code);
		
		int lastLabel = arrA.length-1;
		//System.out.println("last label" + lastLabel);
		
		caller.runAndReturnResult("risultati_"+ lastLabel);

		      /**
		       * Getting R results as XML
		       * for debugging issues.
		       */
		      System.out.println(caller.getParser().getXMLFileAsString());
		      logger.info(Settings.getTime() + "CORRECTNESS TEST PERFORMED WITH SUCCESS");
		    } catch (Exception e) {
		    	logger.error(Settings.getTime() + "R SCRIPT ERROR : "+ e.getMessage());
		    	System.out.println("Error in the correctness test R script : " + e);
		      //Logger.getLogger(Example4.class.getName()).log(Level.SEVERE, e.getMessage());
		      //JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		    }
	}
	
	/**
	 * Method that reads two excel files and invokes the method to create boxplots for each sheet
	 * @param fileNameA file excel A
	 * @param fileNameB file excel B
	 * @param tool		identifies the type of excel file analyzed according to the tool
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public static void generatePlots(String fileNameA, String fileNameB,String tool) throws EncryptedDocumentException, IOException {
	logger.info(Settings.getTime() + "START BOXPLOT GENERATION : " + fileNameA + " - " +  fileNameB);
	
	if(tool=="JASOME") _FILEPATH = Settings._JARPATH + "/result/jasome/";
	if(tool=="CKJM") _FILEPATH = Settings._JARPATH + "/result/xls/";
	
		workbookA = WorkbookFactory.create(new File(_FILEPATH + fileNameA)); 
		workbookB = WorkbookFactory.create(new File(_FILEPATH + fileNameB));
		FNAMEA = fileNameA;
		FNAMEB = fileNameB;
		// Retrieving the number of sheets in the Workbook A
        System.out.println("Workbook "+fileNameA + " has " + workbookA.getNumberOfSheets() + " Sheets : ");

        Iterator<Sheet> sheetIteratorA = workbookA.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIteratorA.hasNext()) {
            Sheet sheet = sheetIteratorA.next();
            System.out.println("=> " + sheet.getSheetName());
        }
	    //
	    // Retrieving the number of sheets in the Workbook B
        System.out.println("Workbook "+fileNameB + " has " + workbookB.getNumberOfSheets() + " Sheets : ");

        Iterator<Sheet> sheetIteratorB = workbookB.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIteratorB.hasNext()) {
            Sheet sheet = sheetIteratorB.next();
            System.out.println("=> " + sheet.getSheetName());
        }
       //
new Thread(new Runnable() {
	
  public void run(){
        // Getting the Sheet at index zero ( PACKAGES METRICS ) 
        if(tool == "JASOME") //test tool type of results files
        {
        Sheet sheetPkgA = workbookA.getSheetAt(0);
        Sheet sheetClassA =  workbookA.getSheetAt(1);
        Sheet sheetMethodA = workbookA.getSheetAt(2);
        
        Sheet sheetPkgB = workbookB.getSheetAt(0);
        Sheet sheetClassB =  workbookB.getSheetAt(1);
        Sheet sheetMethodB= workbookB.getSheetAt(2);
        LinkedHashMap <String, ArrayList<Double> > hmapPkgA;
        LinkedHashMap <String, ArrayList<Double> > hmapPkgB;
        LinkedHashMap <String, ArrayList<Double> > hmapClassA;
        LinkedHashMap <String, ArrayList<Double> > hmapClassB;
        LinkedHashMap <String, ArrayList<Double> > hmapMethodA;
        LinkedHashMap <String, ArrayList<Double> > hmapMethodB;    
        
        //-----------PACKAGE METRICS ----------------------/

        //System.out.println("\n"+fileNameA);
        hmapPkgA = readSheet(sheetPkgA);
        //System.out.println("\n"+fileNameB);
        hmapPkgB = readSheet(sheetPkgB);   
        printMaps(hmapPkgA,hmapPkgB,"P");
        //-----------CLASSES METRICS ----------------------/
        //System.out.println("\n"+fileNameA);
        hmapClassA = readSheet(sheetClassA);
        //System.out.println("\n"+fileNameB);
        hmapClassB = readSheet(sheetClassB);
        printMaps(hmapClassA,hmapClassB,"C");
    
        
        //-----------METHOD METRICS ----------------------/
        //System.out.println("\n"+fileNameA);
        hmapMethodA = readSheet(sheetMethodA);
        //System.out.println("\n"+fileNameB);
        hmapMethodB = readSheet(sheetMethodB);
        printMaps(hmapMethodA,hmapMethodB,"M");
          
        }
        if(tool == "CKJM") {
        	Sheet sheetA= workbookA.getSheetAt(0);
            Sheet sheetB = workbookB.getSheetAt(0);
            LinkedHashMap <String, ArrayList<Double> > hmapA;
            LinkedHashMap <String, ArrayList<Double> > hmapB;
            /*-----------METRICS ----------------------*/
            //System.out.println("\n"+fileNameA);
            hmapA = readSheet(sheetA);
            //System.out.println("\n"+fileNameB);
            hmapB = readSheet(sheetB);
            printMaps(hmapA,hmapB,"C");
        }
        String filenameA = FNAMEA;
		String filenameB = FNAMEB;
				filenameA = filenameA.replace(".xlsx", "");
				filenameB = filenameB.replace(".xlsx", "");
				filenameA = filenameA.replace(".xls", "");
				filenameB = filenameB.replace(".xls", "");
				
        JOptionPane.showMessageDialog(null, "SUCCESS - BoxPlots generated in the /rScript/plots/"+filenameA+"-"+filenameB+" directory","",JOptionPane.INFORMATION_MESSAGE);     
        logger.info(Settings.getTime() + "SUCCESS - BoxPlots generated in the /rScript/plots/"+filenameA+"-"+filenameB+" directory");
        System.out.println("END TEST...");
     }
     
 }).start();

	}
	
	/**
	 * Method that reads metric labels and value from a MAP and writes to arrays needed for the R script
	 * @param mpA	Map of the first excel file
	 * @param mpB	Map of the second excel file
	 * @param flag	Sheet identifier
	 */
	public static void printMaps( LinkedHashMap <String, ArrayList<Double> >mpA, LinkedHashMap <String, ArrayList<Double> > mpB,String flag) {
	try {
		
		System.out.println("PLOTS GENERATION...");
		Iterator<Entry<String, ArrayList<Double>> > itA = mpA.entrySet().iterator();
		Iterator<Entry<String, ArrayList<Double>> > itB = mpB.entrySet().iterator();
		if ( flag == "P") FLAG = "Package";
		if ( flag == "C") FLAG = "Classes";
		if ( flag == "M") FLAG = "Methods";
		
		logger.info(Settings.getTime() + "PLOTS GENERATION ("+ FLAG+ ")");
		
		while (itA.hasNext() && itB.hasNext() ) {
			Entry<String, ArrayList<Double>> pairA = (Map.Entry<String, ArrayList<Double>>) itA.next();
			Entry<String, ArrayList<Double>> pairB = (Map.Entry<String, ArrayList<Double>>) itB.next();
			//System.out.println("A : ");
			//System.out.println(pairA.getKey() + " = " + pairA.getValue());
			//System.out.println("B : ");
			//System.out.println(pairB.getKey() + " = " + pairB.getValue());
			String labelA ="";
			String labelB = "";
			//Fix pairA and pairB to no crash
			if(pairA!=null || pairB!=null) {
				if(!pairA.getKey().isEmpty() ) labelA = pairA.getKey();
				if(!pairA.getKey().isEmpty() ) labelB = pairB.getKey();
			}
			
			if(pairA.getValue()!=null || (pairB.getValue()!=null) ) {
				if(pairA.getValue().isEmpty()) {
					ArrayList<Double> fix_array = new ArrayList<Double>();
					fix_array.add(0.0);
					pairA.setValue(fix_array);
				}
				
			Double[] numbersA  = pairA.getValue().toArray(new Double[pairA.getValue().size()]);		
	        for(double s : numbersA){  
	           // System.out.println(s);  
	        	} 
			
			if(pairB.getValue().isEmpty()) {
					ArrayList<Double> fix_array = new ArrayList<Double>();
					fix_array.add(0.0);
					pairB.setValue(fix_array);
				}
			
	        Double[] numbersB  = pairB.getValue().toArray(new Double[pairB.getValue().size()]);  
	        for(double s : numbersB){  
	          //  System.out.println(s);  
	        	} 
	        
			createBoxPlot(labelA,labelB,numbersA,numbersB);
			}
	
		   	    
		}
		System.out.println("END GENERATE PLOTS...");
	}catch(Exception e) {
		//logger.error(Settings.getTime() + "PLOTS GENERATION ERROR : " + e.getMessage());
		//...there is a pointer error to null that you can ignore
		//System.out.println("ERROR : "+e);
	}
}

	/**
	 * Method that generates a MAP by reading the sheet
	 * @param sheet	sheet of the uploaded excel file
	 * @return
	 */
	public static LinkedHashMap <String, ArrayList<Double> > readSheet(Sheet sheet) {
	    DataFormatter dataFormatter = new DataFormatter();
		//System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
        //Last Row index
        NMAXROW = sheet.getLastRowNum();
        Iterator<Row> rowIterator = sheet.rowIterator();
        //Arrays work
        String [][]  titles_arr = new String[12000][12000];
        Double[][] arr = new Double[12000][12000];
        
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            
            // Now let's iterate over the columns of the current row
            Iterator<Cell> cellIterator = row.cellIterator();
           
            while (cellIterator.hasNext()) {
            	
                Cell cell = cellIterator.next();
               
                String cellValue = dataFormatter.formatCellValue(cell);
                //System.out.print("[" + cell.getRowIndex() + "]" + "[" +cell.getColumnIndex()+"]" +"="+ cellValue + "\t");
                String number = cellValue.replace(",", ".");
                //System.out.println(number);
                if(cell.getColumnIndex() > 0 && cell.getRowIndex() > 0 ) 
                {	
                	//Values Matrix
                	arr[cell.getRowIndex()][cell.getColumnIndex()] = Double.parseDouble(number);
                	//System.out.println("\n\nCELLA : " + arr[cell.getRowIndex()][cell.getColumnIndex()]);
                }else {
                	
                	if(cell.getRowIndex() != 1 && cell.getColumnIndex() > 0) {
                		
	                	//Array labels
	                	 titles_arr[cell.getRowIndex()][cell.getColumnIndex()] = cellValue;
	                	 //System.out.println("ARRAY TITLES :" +cell.getRowIndex()+cell.getColumnIndex()+ "="+cellValue);
	                	 if(NMAXCOL < cell.getColumnIndex()) NMAXCOL = cell.getColumnIndex(); //indice > di colonne
                	}
                }
            }}
        //Print
       //System.out.println("SHEET: " + sheet.getSheetName());
        LinkedHashMap <String, ArrayList<Double> > hmap = fixArrays(arr,titles_arr);
        return hmap;
     
	}
/**
 * Generate HashMap from Arrays to build BoxPlot 
 * @param metrics		Metric matrix
 * @param metricsLabels	Metric labels matrix
 * @return
 */
public static LinkedHashMap< String,ArrayList<Double> > fixArrays(Double[][] metrics,String[][] metricsLabels) {
	LinkedHashMap <String, ArrayList<Double> > hmap = new  LinkedHashMap <String, ArrayList<Double> >();
	
	ArrayList<Double> numbers=new ArrayList<Double>();//Creating arraylist.
	//System.out.println("\n GENERO HASH");
	for(int j = 1; j <=NMAXCOL; j++) {
		numbers=new ArrayList<Double>(); //reset array
		for (int i = 1; i <= NMAXROW; i++) {	
			if(metrics[i][j]!=null) {
		
			numbers.add(metrics[i][j]);
			//System.out.println(metrics[i][j]);
			}
		}
		hmap.put(metricsLabels[0][j], numbers);
		}
	
	 return hmap;
	}
	
	/**
	 * Method that the R script performs to generate boxplots
	 * @param labelA	Metric label A current
	 * @param labelb	Metric label B current
	 * @param arrA		Array that contains the metric A values
	 * @param arrB		Array that contains the metric B values
	 */
	public static void createBoxPlot(String labelA, String labelb, Double[] arrA, Double[] arrB)  {
		//System.out.println("ARRAY A : " + Arrays.toString(arrA));
		//System.out.println("ARRAY B : " + Arrays.toString(arrB));
		try {
			logger.info(Settings.getTime() + "PLOTS GENERATION FOR METRICS ( "+labelA +"-"+ labelb + " ) - R SCRIPT START");
	        RCaller caller = RCaller.create();
	        RCode code = RCode.create();
	        code.addRCode("gc()");
	        code.clear();
	        //Popolate dataframe
	        Object[][] objects = new Object[][] {arrA, arrB};
	        String[] names = new String[] {"A", "B"};
	        DataFrame dataFrame = DataFrame.create(objects, names);
	        code.addDataFrame("data", dataFrame);
	        File file = code.startPlot();
	        //System.out.println("ETICHETTA : "+ labelA);

	        code.addRCode("boxplot(c(data),main=c('"+FLAG+"'),ylab='"+labelA+"(score)',col=(c('gold','lightgreen')), cex.main=1.00, cex.lab=1.50, cex.axis=1.50)");;
	    
	        System.out.println(code.toString());
	        code.endPlot();

	        caller.setRCode(code);
	        caller.runAndReturnResultOnline("boxplot(data)");
	        //code.showPlot(file);
	        Image image = ImageIO.read(file);
	        BufferedImage bimage = toBufferedImage(image);
	        saveToFile(bimage,labelA);
	        
		} catch (Exception e) {
			logger.error(Settings.getTime() + "ERROR IN THE SCRIPT OF PLOTS GENERATION FOR METRICS ( "+labelA +"-"+ labelb + " ) :" + e.getMessage());
			System.out.println("Error in the R script generating plots : " + e);
		      //Logger.getLogger(SimplePlot.class.getName()).log(Level.SEVERE, e.getMessage());
		      //JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		    }
	
	  }
	
	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	/**
	 * Save a bufferimage (boxplot) in png
	 * @param img			Buffer of image
	 * @param metricName	Name of image
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void saveToFile(BufferedImage img,String metricName)  throws FileNotFoundException, IOException {
		String filenameA = FNAMEA;
		String filenameB = FNAMEB;
				filenameA = filenameA.replace(".xlsx", "");
				filenameB = filenameB.replace(".xlsx", "");
				filenameA = filenameA.replace(".xls", "");
				filenameB = filenameB.replace(".xls", "");

				//Craate a new folder
				File newFolder = new File(Settings._JARPATH + "/rScript/plots/"+filenameA+"-"+filenameB);
				newFolder.mkdir();
				newFolder = new File(Settings._JARPATH + "/rScript/plots/"+filenameA+"-"+filenameB+"/"+FLAG);
				newFolder.mkdir();
				//
				//Save image
		        File outputfile = new File(Settings._JARPATH + "/rScript/plots/"+filenameA+"-"+filenameB+"/"+FLAG+"/"+metricName+".png");
		    ImageIO.write(img, "png", outputfile);
		    }	 

	 public static double[] convert(ArrayList<Double> array) {
		double[] new_array = new double[array.size()];
		for (int i=0; i<array.size();i++) {
			if(array.get(i)!=null) new_array[i] = array.get(i);
			//else new_array[i] = 0;
			//System.out.println("Converted element :" + new_array[i]);
		}
		 
		return new_array;
		 
	 }

}