package di.uniba.ardimento.data;
/**
 * Interface in which the methods that a given tool uses are defined
 * @author Francesco Mininno
 *
 */
public interface InterfaceTool {
	public void calculateMetrics();
	public void exportMetricsCSV();
	public void exportMetricsXLS();
	public void exportMetricsXML();

}
