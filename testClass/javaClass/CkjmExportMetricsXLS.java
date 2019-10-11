package di.uniba.ardimento.codeanl;
/**
 * Classe che implementa Command, permette di esprotare le metriche in formato XLS
 * @author franc
 *
 */
public class CkjmExportMetricsXLS implements Command {
	
	ToolAnalyzer Ckjmtool;
	
	public CkjmExportMetricsXLS(ToolAnalyzer newTool) {
		Ckjmtool = newTool;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Ckjmtool.exportMetricsXLS();
	}
	
}
