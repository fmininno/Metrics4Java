package di.uniba.ardimento.data;
/**
 * Class that implements Command, allows you to export the metrics in CSV format
 * @author Francesco Mininno
 *
 */
public class CkjmExportMetricsCSV implements Command{

	ToolAnalyzer Ckjmtool;
/**
 *  Constructor that initializes CkjmTool
 * @param newTool instantiated tool then ckjm or jasome
 */
	public CkjmExportMetricsCSV(ToolAnalyzer newTool) {
		Ckjmtool = newTool;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Ckjmtool.exportMetricsCSV();
	}
	
}