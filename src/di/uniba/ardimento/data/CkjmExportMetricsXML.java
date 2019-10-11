package di.uniba.ardimento.data;
/**
 * Class that implements Command, allows you to export metrics in XML format
 * @author Francesco Mininno
 *
 */
public class CkjmExportMetricsXML implements Command{

	ToolAnalyzer Ckjmtool;
	/**
	 * Constructor that initializes CkjmTool
	 * @param newTool instantiated tool then ckjm or jasome
	 */
	public CkjmExportMetricsXML(ToolAnalyzer newTool) {
		Ckjmtool = newTool;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Ckjmtool.exportMetricsXML();
		
	}
	
}