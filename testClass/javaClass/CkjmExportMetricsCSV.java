package di.uniba.ardimento.codeanl;
/**
 * Classe che implementa Command, permette di esprotare le metriche in formato CSV
 * @author franc
 *
 */
public class CkjmExportMetricsCSV implements Command{

	ToolAnalyzer Ckjmtool;

	public CkjmExportMetricsCSV(ToolAnalyzer newTool) {
		Ckjmtool = newTool;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Ckjmtool.exportMetricsCSV();
	}

}
