package di.uniba.ardimento.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import di.uniba.ardimento.gui.mainMenu;

/**
 * Class that allows you to convert an Excel file into CSV
 * @author Francesco  Mininno
 *
 */
public class ExcelReading {
/**
 * Class that allows you to convert an Excel file into CSV
 * @param sheet 
 * @param sheetName
 * @param filename
 */
	private static Logger logger = LogManager.getLogger(mainMenu.class);
private static PrintStream out;
private static PrintStream out2;

    @SuppressWarnings("deprecation")
	public static void main(String [] args) 
    {
    	//ExcelReading app = new ExcelReading();
    	logger.info(Settings.getTime() + "CONVERT JASOME FILES TO CSV");
    	File folder = new File("./result/jasome/");
    	File[] listOfFiles = folder.listFiles();

   for (int i = 0; i < listOfFiles.length; i++) {
    	  if (listOfFiles[i].isFile()) {
    	    System.out.println("File " + listOfFiles[i].getName());
    	    
    	    String path =  Settings._JARPATH + "/result/jasome/" + listOfFiles[i].getName();
    	    DataFormatter formatter = new DataFormatter();
    	    System.out.println(path);
    	    try {			
				InputStream inp = null;					
				inp = new FileInputStream(path);
				Workbook wb = WorkbookFactory.create(inp);
for( int index = 0; index < wb.getNumberOfSheets();index++) {
	   			Sheet sheet = wb.getSheetAt(index);
	   			//Create a new folder 
	   			File newFolder = new File(Settings._JARPATH + "/result/csv/"+listOfFiles[i].getName().replace(".xls", "")+"/");
	   			newFolder.mkdir();
		    	out = new PrintStream(new FileOutputStream(Settings._JARPATH + "/result/csv/"+listOfFiles[i].getName().replace(".xls", "")+"/" +sheet.getSheetName()+"_"+listOfFiles[i].getName().replace(".xls", "") + ".csv"),true, "UTF-8");
		     
		        FormulaEvaluator fe = null;
		        for (int r = 0, rn = sheet.getLastRowNum() ; r <= rn ; r++) {

		            Row row = sheet.getRow(r);

		            if ( row == null ) { out.println(','); continue; }

		            boolean firstCell = true;

		            for (int c = 0, cn = row.getLastCellNum() ; c < cn ; c++) {

		                Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

		                if ( ! firstCell ) out.print(',');

		                if ( cell != null ) {

		                    if ( fe != null ) cell = fe.evaluateInCell(cell);

		                    String value = formatter.formatCellValue(cell);
		                    value = value.replace(",",".");
		                    if ( cell.getCellTypeEnum() == CellType.FORMULA ) {

		                        value = "=" + value;

		                    }

		                    out.print(value);

		                }

		                firstCell = false;

		            }

		            out.println();

		        }
	}	 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(Settings.getTime() + e.getMessage());
				System.out.println("ERROR : " + e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(Settings.getTime() + e.getMessage());
				System.out.println("ERROR : " + e);
			}
    	  
   }}}
    
    @SuppressWarnings("deprecation")
   	public static void convertXLStoCSV(String filename) {
       	//ExcelReading app = new ExcelReading();
       	logger.info(Settings.getTime() + "CONVERT JASOME FILE TO CSV");  
       	String path =  Settings._JARPATH + "/result/jasome/" + filename;
       	DataFormatter formatter = new DataFormatter();
       	System.out.println(path);
       	    try {			
   				InputStream inp = null;					
   				inp = new FileInputStream(path);
   				Workbook wb = WorkbookFactory.create(inp);
   for( int index = 0; index < wb.getNumberOfSheets();index++) {
   	   			Sheet sheet = wb.getSheetAt(index);
   	   			//Create a new folder 
   	   			File newFolder = new File(Settings._JARPATH + "/result/csv/"+filename.replace(".xls", "")+"/");
   	   			newFolder.mkdir();
   		    	out2 = new PrintStream(new FileOutputStream(Settings._JARPATH + "/result/csv/"+filename.replace(".xls", "")+"/" +sheet.getSheetName()+"_"+filename.replace(".xls", "") + ".csv"),true, "UTF-8");
   		     
   		        FormulaEvaluator fe = null;
   		        for (int r = 0, rn = sheet.getLastRowNum() ; r <= rn ; r++) {

   		            Row row = sheet.getRow(r);

   		            if ( row == null ) { out2.println(','); continue; }

   		            boolean firstCell = true;

   		            for (int c = 0, cn = row.getLastCellNum() ; c < cn ; c++) {

   		                Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

   		                if ( ! firstCell ) out2.print(',');

   		                if ( cell != null ) {

   		                    if ( fe != null ) cell = fe.evaluateInCell(cell);

   		                    String value = formatter.formatCellValue(cell);
   		                    value = value.replace(",",".");
   		                    if ( cell.getCellTypeEnum() == CellType.FORMULA ) {

   		                        value = "=" + value;

   		                    }

   		                    out2.print(value);

   		                }

   		                firstCell = false;

   		            }

   		            out2.println();

   		        }
   	}	 
   			} catch (FileNotFoundException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   				logger.error(Settings.getTime() + e.getMessage());
   				System.out.println("ERROR : " + e);
   			} catch (IOException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   				logger.error(Settings.getTime() + e.getMessage());
   				System.out.println("ERROR : " + e);
   			}
       	  
      }
  }