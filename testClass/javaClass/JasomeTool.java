package di.uniba.ardimento.codeanl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

public class JasomeTool extends ToolAnalyzer {
	
	public void executeCommands(String filename) throws IOException, InterruptedException {

	    File tempScript = createTempScript(filename);

	    try {
	        ProcessBuilder pb = new ProcessBuilder("bash", tempScript.toString());
	        pb.inheritIO();
	        Process process = pb.start();
	        process.waitFor();
	    } finally {
	        tempScript.delete();
	    }
	}

	public File createTempScript(String filename) throws IOException {
	    File tempScript = File.createTempFile("script", null);

	    Writer streamWriter = new OutputStreamWriter(new FileOutputStream(
	            tempScript));
	    PrintWriter printWriter = new PrintWriter(streamWriter);

	    //printWriter.println("#!/bin/bash");
	   // printWriter.println("cd bin");
	    //printWriter.println("ls");
	    //printWriter.println("cd /home/francesco/Scrivania/jasome_tool");
	    printWriter.println("cd /home/francesco/eclipse-workspace/JavaMetrics/jasome_tool");
	    printWriter.println("bin/jasome "+ filename + " --output esempio.xml");
	    printWriter.close();

	    return tempScript;
	}
	
	/**
	 * Istanzia un JasomeTool assegnandogo il nome e la lista di file di lavoro
	 * @param name
	 * @param fileNames
	 */
	public JasomeTool(String name,File fileNames[]) {
		setName(name);
		//Carico i nomi dei files
		setData(fileNames);
	}
	
	@Override
	public void calculateMetrics() {
		// TODO Auto-generated method stub
		System.out.println("CALCOLO METRICHE... ");
		//Leggo i files caricati
		File files[] = getData();
		//Stringa col PATH del file caricato
		String name[] = new String[1];
		name[0] = files[0].getPath();
		try {
			executeCommands(name[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//executeBashCommand("bin/jasome " + name[0]+ " --output es.xml");
		
		//System.out.println(name[0]);
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
	
	

}
