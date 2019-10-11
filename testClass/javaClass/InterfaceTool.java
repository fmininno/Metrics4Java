package di.uniba.ardimento.codeanl;
/**
 * Interfaccia in cui sono definiti i metodi che un determinato tool userà
 * @author franc
 *
 */
public interface InterfaceTool {
	//Calcola metriche
	public void calculateMetrics();
	/*
	 * Salva metriche in diversi formati
	 */
	public void exportMetricsCSV();
	public void exportMetricsXLS();
	public void exportMetricsXML();

}
