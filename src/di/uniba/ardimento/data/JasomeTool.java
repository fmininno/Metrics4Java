package di.uniba.ardimento.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import di.uniba.ardimento.gui.mainMenu;
import di.uniba.ardimento.utils.Settings;
import di.uniba.ardimento.utils.XMLtoXLS;

/**
 * Instance of the abstract class ToolAnalyzer, used to use the Jasome script
 * 
 * @author Francesco Mininno
 *
 */
public class JasomeTool extends ToolAnalyzer {
	// ClassLogger
	private static Logger logger = LogManager.getLogger(mainMenu.class);
	boolean error = false;
	boolean done = false;
	
	interface Callback {
		void onDone();
	}
	/**
	 * Method that reads a script file and executes it
	 * 
	 * @param filename file read that will be loaded
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void executeCommands(String filename) throws IOException, InterruptedException {
		done = false;
		// System.out.println("FILE NAME " + filename);
		new Thread(new Runnable() {
		Callback callback = new Callback() {
			public void onDone() {
				done = true;
			}
		};
			public void run() {	
				File tempScript = null;
				try {	
					tempScript = createTempScript(filename);
					//System.out.println("SCRIPT JASOME : " + tempScript.toString());
					ProcessBuilder pb = new ProcessBuilder("bash", tempScript.toString());
					pb.inheritIO();
					Process process = pb.start();										
					process.waitFor();				
				} catch (Exception e) {
					logger.error(Settings.getTime() + e.getMessage());
					System.out.println("ERRORE NELLO SCRIPT JASOME DI " + filename + ":" + e);
					// JOptionPane.showMessageDialog(null,"Error in the Jasome script
					// ("+filename+"): try to select a smaller number of
					// files","Error",JOptionPane.ERROR_MESSAGE);

				}
				finally {
					callback.onDone();
					tempScript.delete();
				}
			
			}
		}).start();
		
		int time = 150;
		while(!done) {
			try {
				Thread.sleep(time);
				System.out.append("|");		
				time+=5;
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		System.out.append("\n");

	}

	/**
	 * Method that creates a script file
	 * 
	 * @param filename file read on which the script will be executed
	 * @return script that will execute jasome on the uploaded file
	 * @throws IOException
	 */
	public File createTempScript(String filename) throws IOException {
		System.out.println("Processing...");
		File tempScript = File.createTempFile("script", null);

		Writer streamWriter = new OutputStreamWriter(new FileOutputStream(tempScript));
		PrintWriter printWriter = new PrintWriter(streamWriter);
		// other commands...
		// printWriter.println("#!/bin/bash");
		// printWriter.println("cd bin");
		// printWriter.println("ls");
		//
		String currentPath = Settings._JARPATH;
		// System.getProperty("user.dir");
		// URL currentPath = getClass().getClassLoader().getResource("jasome_tool/");

		// System.out.println(currentPath);

		printWriter.println("cd " + currentPath + "/jasome_tool");
		// printWriter.println("cd "+ currentPath.getPath());

		String[] outfile = filename.split("/");
		// System.out.println("FILE OUT NAME : " + outfile[outfile.length-1]);
		printWriter.println("bin/jasome " + filename + " --output " + outfile[outfile.length - 1] + ".xml");

		// System.out.println(filename);
		printWriter.close();
		return tempScript;
	}

	/**
	 * Instantiates a JasomeTool by assigning the name and list of work files
	 * 
	 * @param name      name that identifies the jasome tool
	 * @param fileNames file that will be uploaded
	 */
	public JasomeTool(String name, File fileNames[]) {
		setName(name);
		setData(fileNames);
	}

	@Override
	public void calculateMetrics() {
		// TODO Auto-generated method stub
		XMLtoXLS._notProcessable = 0;
		System.out.println("Start JaSoME Tool... ");
		logger.info(Settings.getTime() + "START JASOME TOOL");
		// I read the uploaded files
		File files[] = getData();
		// String with the path of the uploaded file
		String name[] = new String[1];
		name[0] = files[0].getPath();
		// Loop
		if (!files[0].isDirectory()) {
			try {
				executeCommands(name[0]);
				String filename = files[0].getName();
				// COnvert
				XMLtoXLS.convert(filename);
				JOptionPane.showMessageDialog(null, "Task completed", "", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("Done");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(Settings.getTime() + e.getMessage());
				System.out.println("ERROR : " + e);
			} catch (InterruptedException e) {
				logger.error(Settings.getTime() + e.getMessage());
				System.out.println("ERROR : " + e);
			}
		} else {
			if (!controlSubFolder(files[0]) && files[0].list().length > 0 && controlJavaFiles(files[0])) {
				try {
					System.out.println("total number of files to process: " + files[0].list().length);
					executeCommands(name[0]);
					String filename = files[0].getName();
					// Convert
					XMLtoXLS.convert(filename);
					JOptionPane.showMessageDialog(null, "Task completed", "", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("Done");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(Settings.getTime() + e.getMessage());
					System.out.println("ERROR : " + e);
				} catch (InterruptedException e) {
					logger.error(Settings.getTime() + e.getMessage());
					System.out.println("ERROR : " + e);
				}
			} else {
				File dir = new File(files[0].getPath());
				int totalNumFiles = dir.listFiles().length;
				System.out.println("total number of files to process: " + totalNumFiles);
				int count = 0;
				for (final File fileEntry : dir.listFiles()) {
					count++;

					if (fileEntry.isDirectory() && fileEntry.list().length > 0 && controlJavaFiles(fileEntry)) {
						System.out.println("reading file: " + count + "/" + totalNumFiles);
						System.out.println("filename :" + fileEntry.getName());
						try {
							executeCommands(fileEntry.getPath());
							String filename = fileEntry.getName();
							// Covert
							XMLtoXLS.convert(filename);

						} catch (IOException e) {
							// TODO Auto-generated catch block
							logger.error(Settings.getTime() + e.getMessage());
							System.out.println("ERROR : " + e);
						} catch (InterruptedException e) {
							logger.error(Settings.getTime() + e.getMessage());
							System.out.println("ERROR : " + e);
						}
					} else {
						if (error == false) { // if there is a file !=.java extension
							System.out.println("directory not found or is empty");
							error = false;
						}
					}
				}
				JOptionPane.showMessageDialog(null, "Task completed", "", JOptionPane.INFORMATION_MESSAGE);
				if (XMLtoXLS._notProcessable > 0)
					System.out.println("INPUT FILES NOT PROCESSED BY JASOME:" + XMLtoXLS._notProcessable);
				System.out.println("Done");
			}
		}
	}

	@Override
	public void exportMetricsCSV() {
		// TODO Auto-generated method stub

	}

	@Override
	public void exportMetricsXLS() {
		// TODO Auto-generated method stub

	}

	@Override
	public void exportMetricsXML() {
		// TODO Auto-generated method stub

	}

	/**
	 * Control subfolder method
	 * 
	 * @param dir directory input
	 * @return true if there is a subfolder
	 */
	public boolean controlSubFolder(File dir) {
		for (final File fileEntry : dir.listFiles()) {
			if (fileEntry.isDirectory())
				return true;
		}
		return false;
	}

	public boolean controlJavaFiles(File dir) {

		for (final File fileEntry : dir.listFiles()) {
			if (!fileEntry.getName().contains(".java")) {

				System.out.println(
						"FOLDER: " + dir + " contains a non .java file! \nALL FILES MUST HAVE EXTENSION .JAVA !!!");
				System.out.println("no .java FILENAME: " + fileEntry.getName());
				// Delete file != .java
				try {
					Files.deleteIfExists(Paths.get(fileEntry.getPath()));
					System.out.println(Paths.get("the file has been deleted: " + fileEntry.getPath()));
				} catch (IOException e) {
					logger.error(Settings.getTime() + e.getMessage());
					System.out.println("ERRORE IN DELETE NOT FILE .JAVA:" + e);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				error = true;

				// return false;
			}

		}
		return true;
	}
}