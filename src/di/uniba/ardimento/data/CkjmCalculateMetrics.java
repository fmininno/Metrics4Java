package di.uniba.ardimento.data;
/**
 * Class that implements Command, represents the metric calculation command
 * @author Francesco Mininno
 *
 */
public class CkjmCalculateMetrics implements Command{

	ToolAnalyzer Ckjmtool;
	
/**
 * Constructor that initializes CkjmTool
 * @param newTool instantiated tool then ckjm or jasome
 */
	public CkjmCalculateMetrics(ToolAnalyzer newTool) {
		Ckjmtool = newTool;
	}

	@Override
	public void execute() { 
		// TODO Auto-generated method stub
		Ckjmtool.calculateMetrics();
	}
	
}