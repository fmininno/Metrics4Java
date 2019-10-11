package di.uniba.ardimento.codeanl;
/**
 * Classe che implementa Command e rappresenta il comando del calcolo delle metriche
 * @author franc
 *
 */
public class CkjmCalculateMetrics implements Command{

	ToolAnalyzer Ckjmtool;

	public CkjmCalculateMetrics(ToolAnalyzer newTool) {
		Ckjmtool = newTool;
	}
	
	@Override
	public void execute() { 
		// TODO Auto-generated method stub
		Ckjmtool.calculateMetrics();

	}
	
}
