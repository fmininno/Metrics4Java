package di.uniba.ardimento.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import di.uniba.ardimento.gui.mainMenu;
/**
 * Class that converts a CSV file to XLS
 * @author Francesco Mininno
 *
 */
public class CSVToExcelConverter {
	private static Logger logger = LogManager.getLogger(mainMenu.class);
	private static BufferedReader br;
	private static XSSFWorkbook workBook;
	public static void csvToXLSX(File fName,String CC) {
	    try {
	    	logger.info(Settings.getTime() + "CSV IN XLS COVERSION");
	        String csvFileAddress = fName.getAbsolutePath();
	        String xlsxFileAddress = Settings._JARPATH + "/result/"+ CC + getFileNameWithoutExtension(fName) +  ".xlsx";
	        workBook = new XSSFWorkbook();
	        XSSFSheet sheet = workBook.createSheet("CC");
	        String currentLine=null;
	        int RowNum=0;
	        br = new BufferedReader(new FileReader(csvFileAddress));
	        while ((currentLine = br.readLine()) != null) {
	            String str[] = currentLine.split(",");
	           
	            XSSFRow currentRow=sheet.createRow(RowNum);
	            for(int i=0;i<str.length;i++){
	                currentRow.createCell(i).setCellValue(str[i]);
	            }
	            RowNum++;
	        }

	        FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
	        workBook.write(fileOutputStream);
	        fileOutputStream.close();
	       
	        System.out.println("Done");
	    } catch (Exception ex) {
	    	logger.error(Settings.getTime() + "CSV IN XLS COVERSION ERROR : " + ex.getMessage());
	        System.out.println(ex.getMessage()+"Exception in try");
	    }
	}
/**
 * Allows to get the filname without extension
 * @param file
 * @return 
 */
	private static String getFileNameWithoutExtension(File file) {
        String fileName = "";
 
        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                fileName = name.replaceFirst("[.][^.]+$", "");
            }
        } catch (Exception e) {
        	logger.error(Settings.getTime() + e.getMessage());
        	System.out.println("ERROR : " + e);
        
            fileName = "";
        }
 
        return fileName;
 
    }
}	