package di.uniba.ardimento.data;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import di.uniba.ardimento.gui.mainMenu;
import di.uniba.ardimento.utils.Settings;

/**
 * Factory class to instantiate the various tools that will be used :
 * - CKJM 
 * - JASOME
 * @author Francesco Mininno
 *
 */
public class ToolFactory {
	private static Logger logger = LogManager.getLogger(mainMenu.class);
	/**
	 * Constructor that instantiates a specific ToolAnalyzer based on the toolName received
	 * @param toolName tool identifier
	 * @param data array of files the tool will work on
	 * @return
	 */
	public ToolAnalyzer useTool(String toolName, File data[]) {
		ToolAnalyzer tool = null;
		try
		{
			switch(toolName) 
			{
		case "CKJM": return new CkjmTool(toolName,data);
		case "JASOME" : return new JasomeTool(toolName,data);
			}
		}catch(Exception e) {
			logger.error(Settings.getTime() + e.getMessage());
			System.out.println("ERROR LOADING TOOL : " + e);
		}
		return tool;
	}
}