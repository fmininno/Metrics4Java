package di.uniba.ardimento.codeanl;

import java.io.File;

/**
 * ToolAnalyzer rappresenta la classe astratta dei tool che verranno integrati nel progetto,
 * ogni tool ha un nome ed un array di file come default.
 * @author franc
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
