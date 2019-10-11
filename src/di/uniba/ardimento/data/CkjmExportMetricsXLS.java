package di.uniba.ardimento.data;
/**
 * Class that implements Command, allows you to export metrics in XLS format
 * @author Francesco Mininno
 *
 */
public class CkjmExportMetricsXLS implements Command {
	
	ToolAnalyzer Ckjmtool;
/**	
 *  Constructor that initializes CkjmTool
 * @param newTool instantiated tool then ckjm or jasome
 */
	public CkjmExportMetricsXLS(ToolAnalyzer newTool) {
		Ckjmtool = newTool;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Ckjmtool.exportMetricsXLS();
	}	
	
}