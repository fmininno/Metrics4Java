package di.uniba.ardimento.gui;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Point;
import di.uniba.ardimento.utils.ClassMetricsDataContainer;
import di.uniba.ardimento.utils.Settings;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * Class representing the frame of the production of the metric reports
 * @author Francesco Mininno
 *
 */
public class reportMenu extends JFrame{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(mainMenu.class);
	
	public static class FrameDragListener extends MouseAdapter {

        private final JFrame frame;
        private Point mouseDownCompCoords = null;

        public FrameDragListener(JFrame frame) {
            this.frame = frame;
        }

        public void mouseReleased(MouseEvent e) {
            mouseDownCompCoords = null;
        }

        public void mousePressed(MouseEvent e) {
            mouseDownCompCoords = e.getPoint();
        }

        public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        }
    }
	
	ClassMetricsDataContainer outputClass = ClassMetricsDataContainer.getIstance();
public String nameFile[] = new String [1];
/*---------------------*/
	/* Graphic elements ------------ */
	/* PANELS */
		private JPanel panel;
		private JPanel panel_1;
	/* LABELS */
		private JLabel lblTitle;
		private JLabel lblSelectFileclass;
		private JButton btnBack;
		private JComboBox<String> cmbFiles;
		private JTable tbMetrics;
		private JScrollPane scrollPane;
	/**----------------------
	 * Create the application.
	 */
	public reportMenu() {
		getContentPane().setFont(new Font("Tahoma", Font.BOLD, 10));
		getContentPane().setBackground(Color.WHITE);
		setUndecorated(true);
        FrameDragListener frameDragListener = new FrameDragListener(this);
        addMouseListener(frameDragListener);
        addMouseMotionListener(frameDragListener);

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/* PROPRIETA' ELEMENTI GRAFICI */
		setBounds(100, 100, 730,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 0, 730, 29);
		getContentPane().add(panel);
		
		lblTitle = new JLabel("PRODUCE REPORTS");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(lblTitle);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 27, 730, 87);
		panel_1.setLayout(null);
		getContentPane().add(panel_1);
		
		lblSelectFileclass = new JLabel("Select a metric file to view");
		lblSelectFileclass.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSelectFileclass.setBounds(10, 22, 275, 14);
		panel_1.add(lblSelectFileclass);
		
		cmbFiles = new JComboBox<String>();
		cmbFiles.setToolTipText("Select a file to view");
		cmbFiles.setBounds(10, 48, 491, 17);
		panel_1.add(cmbFiles);
		//Path of CSV files
		File dir = new File(Settings._JARPATH + "/result/csv/");
        FilenameFilter filter = new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return name.endsWith(".csv");
            }
        };
        String[] var = dir.list(filter);
        
        if (var == null)
        {
       		System.out.println("JCOMBO BOX Directory is INCORRECT or does not exist!");
       		
        }
        else
        {
        	if ( var.length == 0 ) cmbFiles.addItem("No result files to report");
            for (int i=0; i<var.length; i++) //populate the combo
            {
                String filename = var[i];  
              
                cmbFiles.addItem(filename);        
            }
            
        }
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.GRAY);
		panel_6.setBounds(0, 0, 186, 21);

		JLabel lblSelectTool = new JLabel("SELECT TOOL");
		lblSelectTool.setForeground(Color.BLACK);
		lblSelectTool.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_6.add(lblSelectTool);
	
		btnBack = new JButton("");
		btnBack.setToolTipText("Go to main menu'");
		btnBack.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/back_big.png")));
		btnBack.setBounds(10, 455, 38, 38);
		getContentPane().add(btnBack);
		btnBack.setForeground(Color.RED);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
			
			JButton button = new JButton("");
			button.setToolTipText("Exit");
			button.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/close_big.png")));
			
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(1);
				}
			});
			
			button.setForeground(Color.RED);
			button.setFont(new Font("Tahoma", Font.BOLD, 11));
			button.setBounds(685, 455, 38, 38);
			getContentPane().add(button);
			
			scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 114, 730, 330);
			getContentPane().add(scrollPane);
			
			tbMetrics = new JTable();
			tbMetrics.setFont(new Font("Dialog", Font.BOLD, 12));
			scrollPane.setViewportView(tbMetrics);
			tbMetrics.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tbMetrics.setFillsViewportHeight(true);
			tbMetrics.setCellSelectionEnabled(true);
			tbMetrics.setColumnSelectionAllowed(true);
			tbMetrics.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
		
	
//LISTENERS
			
		/* BACK */ 
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					
				}
			});
		/* READ FILE CSV TO TABLE */	
			cmbFiles.addActionListener(new ActionListener() {
				private BufferedReader br;

				public void actionPerformed(ActionEvent e) {
					
					  String filePath = cmbFiles.getSelectedItem().toString();
				        File file = new File(Settings._JARPATH + "/result/csv/" + filePath);
				        System.out.println(filePath);
				        DefaultTableModel modelx = (DefaultTableModel) tbMetrics.getModel();
				        modelx.setRowCount(0);
				        try {
				        	logger.info(Settings.getTime() + "REPORT FILE : " + file.getName());
				            br = new BufferedReader(new FileReader(file));
				            // get the first line
				            // get the columns name from the first line
				            // set columns name to the jtable model
				            String firstLine = br.readLine().trim();
				            String[] columnsName = firstLine.split(",");
				            System.out.println(columnsName[1]);
				            DefaultTableModel model = (DefaultTableModel)tbMetrics.getModel();
				            model.setColumnIdentifiers(columnsName);
				           
				            // get lines from txt file
				            Object[] tableLines = br.lines().toArray();
				         
				            // extratct data from lines
				            // set data to jtable model
				            
				            for(int i = 0; i < tableLines.length; i++)
				            {
				                String line = tableLines[i].toString().trim();
				                String[] dataRow = line.split(",");
				                model.addRow(dataRow);	          
				            }
				            
				            //fix the column width
				            tbMetrics.getColumnModel().getColumn(0).setPreferredWidth(400);
				            for ( int i = 1 ; i < tbMetrics.getColumnModel().getColumnCount(); i++) {
				            	tbMetrics.getColumnModel().getColumn(i).setPreferredWidth(100);
				            }
				            
				            //tbMetrics.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
				          
				           
				            	
				        } catch (Exception ex) {
				        	logger.error(Settings.getTime() + ex.getMessage());
				        	System.out.println("ERROR : " + ex);
				            //Logger.getLogger(TextFileDataToJTable.class.getName()).log(Level.SEVERE, null, ex);
				        }
				}
			});	
	}
	
	public void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 15; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        if(width > 300)
	            width=300;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
}