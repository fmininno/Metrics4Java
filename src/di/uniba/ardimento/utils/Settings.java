package di.uniba.ardimento.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import di.uniba.ardimento.gui.mainMenu;

/**
 * Class that contains global utility variables and methods for the system
 * @author Francesco Mininno
 *
 */
public class Settings {
	public static final String _JARPATH = jarPath();
	private static Logger logger = LogManager.getLogger(mainMenu.class);
	/**
	 * Method that returns the path of the jar to access resources
	 * @return fixedPath Path of Jar
	 */
	private static String jarPath() {
		String path = Settings.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String pathArray[] = path.split("/");
		String fixedPath = "";
		for (int i=0; i < pathArray.length-1;i++) {			
			fixedPath = fixedPath + "/"+ pathArray[i];
		}
		return fixedPath;
	}
	
	/**
	 * Verify that there are folders needed to start the program
	 * @return flag 0 there are all folders, flag 1 there is not one or more folders
	 */
	public static int deployJar() {
		logger.info(getTime() + "DEPLOY CONTROL");
		ArrayList<String> folders = new ArrayList<>();
		folders.add("jasome_tool");
		folders.add("result");
		folders.add("rScript");
		folders.add("temp");
		ArrayList<String> subJasomeTool = new ArrayList<>();
		subJasomeTool.add("bin");
		subJasomeTool.add("lib");
		ArrayList<String> subResult = new ArrayList<>();
		subResult.add("CC");
		subResult.add("csv");
		subResult.add("jasome");
		subResult.add("xls");
		subResult.add("xml");
		ArrayList<String> subCC = new ArrayList<>();
		subCC.add("csv");
		subCC.add("xls");
		subCC.add("xml");
		ArrayList<String> subrScript = new ArrayList<>();
		subrScript.add("plots");
		subrScript.add("wTest");
	   int[] flag = {0,0,0,0,0};
	   //File dir = new File(Settings._JARPATH);

		   flag[0] = checkFolders(Settings._JARPATH,folders);
		   //System.out.println("FLAG 0: "+ flag[0]);
		   flag[1] = checkFolders(Settings._JARPATH + "/jasome_tool/",subJasomeTool);
		   //System.out.println("FLAG 1: "  + flag[1]);
		   flag[2] = checkFolders(Settings._JARPATH + "/result/",subResult);
		   //System.out.println("FLAG 2: "  + flag[2]);
		   flag[3] = checkFolders(Settings._JARPATH + "/result/CC/",subCC);
		   //System.out.println("FLAG 3: "  + flag[3]);
		   flag[4] = checkFolders(Settings._JARPATH + "/rScript/", subrScript);
		   //System.out.println("FLAG 4: "  + flag[4]);
	   
	   for (int i = 0; i < flag.length ; i++) {
	   //System.out.println("FLAG : "  + flag[i]);
	   //JOptionPane.showMessageDialog(null, "FLAGS : "  + flag[i]);
		   if ( flag[i] == 0) {
			   return 1; //exit program
			   //JOptionPane.showMessageDialog(null, "The program will be closed!");
			   //System.exit(1);
		   }
		  }
	  return 0;
	}
	
	/**
	 * Verifies that the name of a folder is present in the dirlist of the current path
	 * @param path path of dir
	 * @param arr array of names to check
	 * @return a flag
	 */
	static int checkFolders(String path, ArrayList<String> arr) {
		File dir = new File(path);
		if (! dir.exists() ) return 0;
		boolean close = false;
		boolean check = false;
		//Take first folder name to check in dir
		for (int i = 0; i < arr.size() ; i++) {
		check = false;
		String folderToCheck = arr.get(i);
	
		//Compare folder with dir list
		for (final File fileEntry : dir.listFiles()) {
			
			System.out.println("Compare : " + fileEntry.getName() + " " + folderToCheck);
			
			if (folderToCheck.compareTo(fileEntry.getName()) == 0) {
				check = true; 
			
			}
		 }
		
		if (check == false ) {
			logger.error(getTime() + "MISSING FILE OR FOLDER : " + folderToCheck + " IN : " + path);
			System.out.println("MISSING FILE OR FOLDER : " + folderToCheck + " IN : " + path);
			JOptionPane.showMessageDialog(null,"MISSING FILE OR FOLDER : " + folderToCheck + " IN : " + path,"Error",JOptionPane.ERROR_MESSAGE);
			close = true;
			
			}
		}
		if (close == true) return 0;
		else return 1;
	}
	
/**
 * Method to clear temp directory used to export Ckjm metrics files	
 */
	public static void deleteTempFolder() {
	 try
     { 
		 logger.info(getTime() + "CLEANING TEMP FOLDER");
		 File dir = new File(Settings._JARPATH + "/temp/");
		 if(dir.list().length > 0) {
			 for (final File fileEntry : dir.listFiles()) {
				 String path = Settings._JARPATH + "/temp/" + fileEntry.getName();
				 //if S.O. is Linux 
				 System.out.println(System.getProperty("os.name"));
				 if(System.getProperty("os.name").contains("Linux"))  Files.deleteIfExists(Paths.get(path)); 
				 else 
				 {
					 path = path.replace("//", "");
					 //System.out.println(path);
					 Files.deleteIfExists(Paths.get(path)); 
				 }
			 }
		 }
     } 
     catch(NoSuchFileException e) 
     { 
    	 logger.error(getTime() + "THE TEMP FOLDER DOES NOT EXIST");
         System.out.println("No such file/directory exists"); 
     } 
     catch(DirectoryNotEmptyException e) 
     { 
    	 logger.info(getTime() + "DIRECTORY IS NOT EMPTY");
         System.out.println("Directory is not empty."); 
     } 
     catch(IOException e) 
     { 
    	 logger.error(getTime() + "INVALID PERMISSIONS");
         System.out.println("Invalid permissions."); 
     } 
       
     //System.out.println("Deletion successful."); 
 } 
/**
 * Method that return current time
 * @return
 */
	public static String getTime() {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss] ");
        return sdf.format(cal.getTime());
	}
}