package di.uniba.ardimento.data;

import java.io.File;

/**
 * ToolAnalyzer represents the abstract class of the tools that will be integrated into the project,
 * each tool has a name and an array of files as default
 * @author Francesco Mininno
 *
 */
public abstract class ToolAnalyzer implements InterfaceTool{

	private String name;
	private File data[];
	
	public void setName(String toolName) {name = toolName;}	
	public String getName() { return name;}
	
	public void setData(File files[]) { data = files;};
	public File[] getData() { return data;};
		
}