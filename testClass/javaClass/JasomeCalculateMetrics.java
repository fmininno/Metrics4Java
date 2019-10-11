package di.uniba.ardimento.codeanl;

public class JasomeCalculateMetrics implements Command{
	ToolAnalyzer Jasometool;

	public JasomeCalculateMetrics(ToolAnalyzer newTool) {
		Jasometool = newTool;
	}
	
	@Override
	public void execute() { 
		// TODO Auto-generated method stub
		Jasometool.calculateMetrics();

	}

}
