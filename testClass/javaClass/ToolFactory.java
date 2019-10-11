package di.uniba.ardimento.codeanl;

import java.io.File;
/**
 * Factory per istanziare i vari tool che verranno utilizzati
 * - CKJM 
 * - ... 
 * @author franc
 *
 */
public class ToolFactory {
	/**
	 * Costruttore che istanzia un determinato ToolAnalyzer in base al toolName ricevuto.
	 * @param toolName
	 * @param data
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
			System.out.println("Error loading tool : " + e);
		}
		return tool;
	}
}
		/*
		//if(toolName.equals("CKJM")) 
		//{
			//return new CkjmTool(toolName, data);
		//}
		//else if(toolName.equals("JASOME")) 
		//	{
			//return new JasomeTool(toolName,data);
			//}
		//}
		*/
	


