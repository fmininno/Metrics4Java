package di.uniba.ardimento.codeanl;
/**
 * Classe che implementa Command, permette di esprotare le metriche in formato XML
 * @author franc
 *
 */
public class CkjmExportMetricsXML implements Command{

	ToolAnalyzer Ckjmtool;
	public CkjmExportMetricsXML(ToolAnalyzer newTool) {
		Ckjmtool = newTool;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Ckjmtool.exportMetricsXML();
		
	}
	

}
