package di.uniba.ardimento.data;
/**
 * Class that implements Command, represents the metric calculation command
 * @author Francesco Mininno
 *
 */
public class JasomeCalculateMetrics implements Command{
	ToolAnalyzer Jasometool;
/**
 * Constructor that initializes JasomeTool
 * @param newTool instantiated tool then ckjm or jasome
 */
	public JasomeCalculateMetrics(ToolAnalyzer newTool) {
		Jasometool = newTool;
	}
	
	@Override
	public void execute() { 
		// TODO Auto-generated method stub
		Jasometool.calculateMetrics();

	}

}