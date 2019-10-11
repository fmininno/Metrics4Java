package di.uniba.ardimento.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Point;
import di.uniba.ardimento.data.CkjmCalculateMetrics;
import di.uniba.ardimento.data.CkjmExportMetricsCSV;
import di.uniba.ardimento.data.CkjmExportMetricsXLS;
import di.uniba.ardimento.data.CkjmExportMetricsXML;
import di.uniba.ardimento.data.JasomeCalculateMetrics;
import di.uniba.ardimento.data.ToolAction;
import di.uniba.ardimento.data.ToolAnalyzer;
import di.uniba.ardimento.data.ToolFactory;
import di.uniba.ardimento.utils.CheckBoxMetrics;
import di.uniba.ardimento.utils.ClassMetricsDataContainer;
import di.uniba.ardimento.utils.Settings;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import java.awt.SystemColor;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JToggleButton;
/**
 * Frame class of the metric calculation
 * @author Francesco Mininno
 *
 */
public class dataMenu extends JFrame{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(mainMenu.class);
	public static int csvFormatJasome = 0;
	public static int xmlFormatJasome = 0;
	public static JTextArea txtrWaitingForOperations;
	ClassMetricsDataContainer outputClass = ClassMetricsDataContainer.getIstance();
	CheckBoxMetrics isCheck = CheckBoxMetrics.getIstance();
	
/* Graphic elements------------ */
/* PANELS */
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	
/* LABELS */
	private JLabel lblTitle;
	//private JLabel lblFile; 
	private JLabel lblSelectFileclass;
	
/* BUTTONS */
	private JButton btnOpen;
	private JButton btnElaborate;
	private JButton btnBack;
	private JButton btnExit;
	private JToggleButton btnXls;
	private JToggleButton btnXml;
	private JToggleButton btnCsv;
/* CHECKBOX */
	private JCheckBox chckbxAmc;
	private JCheckBox chckbxLcom;
	private JCheckBox chckbxMoa;
	private JCheckBox chckbxMfa;
	private JCheckBox chckbxCam;
	private JCheckBox chckbxIc;
	private JCheckBox chckbxCbm;
	private JCheckBox chckbxWmc;
	private JCheckBox chckbxDit;
	private JCheckBox chckbxNoc;
	private JCheckBox chckbxCbo;
	private JCheckBox chckbxCc;
	private JCheckBox chckbxCa;
	public JCheckBox chckbxCe;
	private JCheckBox chckbxNpm;
	private JCheckBox chckbxLoc;
	private JCheckBox chckbxLcom3;
	private JCheckBox chckbxRfc;
	private JCheckBox chckbxDam;
/* RADIO BUTTON */
	private JRadioButton rdbtnJasome = new JRadioButton("JASOME");
	private JRadioButton rdbtnCkjm = new JRadioButton("CKJM");
/* --------------------- */
	
/* Work variables */
	private File fileSelected[] = new File[1];
	public String nameFile[] = new String [1];
	//Tool factory instantiates the tool to use based on which tool was selected
	private ToolFactory toolFactory = new ToolFactory();
	//Inizializzo il tool
	private ToolAnalyzer tool = null;	
	//private JButton btnCsv;
	private JTextField textFileName;

/*---------------------*/
	/**
	 * Create the application.
	 */
	public dataMenu() {
		getContentPane().setFont(new Font("Dialog", Font.PLAIN, 8));
		getContentPane().setBackground(Color.WHITE);
		setUndecorated(true);
		//setResizable(true);
		FrameDragListener frameDragListener = new FrameDragListener(this);
	    addMouseListener(frameDragListener);
	    addMouseMotionListener(frameDragListener);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		txtrWaitingForOperations = new JTextArea();
		txtrWaitingForOperations.setEditable(false);
		txtrWaitingForOperations.setText("Waiting for operation... \n");
		txtrWaitingForOperations.setForeground(Color.GREEN);
		txtrWaitingForOperations.setFont(new Font("Dialog", Font.PLAIN, 12));
		final JScrollPane scroll = new JScrollPane(txtrWaitingForOperations);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txtrWaitingForOperations.setBackground(Color.BLACK);
		txtrWaitingForOperations.setBounds(0, 322, 730, 121);
		scroll.setBounds(0, 339, 730, 104);
		getContentPane().add(scroll);
		
		// Get the default system.out
		final PrintStream sysOut = System.out;

		// Replace System.out with our own PrintStream
		System.setOut(new PrintStream(new OutputStream() {
		    public void write(int b) throws
		            IOException {
		        // write to the text area
		        txtrWaitingForOperations.append(String.valueOf((char)b));
		        txtrWaitingForOperations.setCaretPosition(txtrWaitingForOperations.getDocument().getLength());
		        // write to the default System.out
		        sysOut.write(b);
		    }
		}));
	/* PROPERTIES OF GRAPHIC ELEMENTS */
		setBounds(100, 100, 730, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 0, 730, 29);
		getContentPane().add(panel);
		
		lblTitle = new JLabel("CALCULATE METRICS\r\n");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(lblTitle);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 27, 730, 82);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		btnOpen = new JButton("OPEN");
		btnOpen.setForeground(UIManager.getColor("Button.foreground"));
		btnOpen.setFont(new Font("Dialog", Font.BOLD, 9));
		btnOpen.setToolTipText("Open the file on which you want to calculate the metrics");
		btnOpen.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/openfile.png")));
		
		btnOpen.setBounds(588, 29, 130, 35);
		panel_1.add(btnOpen);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		
		lblSelectFileclass = new JLabel("Open  .class or .jar file :");
		lblSelectFileclass.setBounds(12, 50, 314, 14);
		panel_1.add(lblSelectFileclass);
		lblSelectFileclass.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JLabel lblSelectMetricTools = new JLabel("Please, select the metric tool to use :");
		lblSelectMetricTools.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSelectMetricTools.setBounds(12, 12, 304, 17);
		panel_1.add(lblSelectMetricTools);		
		panel_4 = new JPanel();
		panel_4.setBorder(null);
		panel_4.setBackground(new Color(255, 255, 255));
		panel_4.setBounds(255, -27, 186, 70);
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		rdbtnCkjm.setToolTipText("Tool to calculate CK metrics");
		rdbtnCkjm.setForeground(Color.RED);
		rdbtnCkjm.setSelected(true);
		rdbtnCkjm.setBounds(23, 37, 66, 23);
		rdbtnCkjm.setBackground(new Color(255, 255, 255));
		panel_4.add(rdbtnCkjm);
		rdbtnJasome.setToolTipText("Tool to calculate multiple code quality metrics");
		rdbtnJasome.setBounds(91, 37, 95, 23);
		rdbtnJasome.setBackground(new Color(255, 255, 255));
		panel_4.add(rdbtnJasome);
		
		textFileName = new JTextField("file name");
		textFileName.setBounds(193, 48, 380, 17);
		panel_1.add(textFileName);
		textFileName.setEditable(false);
		textFileName.setForeground(Color.BLUE);
		textFileName.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 10));
		textFileName.setColumns(10);
		rdbtnJasome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnJasome.setForeground(Color.RED);
				rdbtnCkjm.setForeground(Color.BLACK);
				rdbtnCkjm.setSelected(false);
				showJasomePanel();
			}
		});
		
		rdbtnCkjm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnCkjm.setForeground(Color.RED);
				rdbtnJasome.setForeground(Color.BLACK);
				rdbtnJasome.setSelected(false);
				showCKJMPanel();
				
			}
		});
		
		btnBack = new JButton("");
		btnBack.setToolTipText("Go to main menu'");
		btnBack.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/back_big.png")));
		btnBack.setBounds(10, 455, 38, 38);
		getContentPane().add(btnBack);
		btnBack.setForeground(Color.RED);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		btnExit= new JButton("");
		btnExit.setToolTipText("Exit");
		btnExit.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/close_big.png")));
		btnExit.setForeground(Color.RED);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExit.setBounds(685, 455, 38, 38);
		getContentPane().add(btnExit);	
		
		panel_2 = new JPanel();
		panel_2.setBounds(0, 111, 730, 145);
		getContentPane().add(panel_2);
		panel_2.setBackground(Color.LIGHT_GRAY);
			panel_2.setLayout(null);
		
		/*CHECK BOX */
			chckbxAmc = new JCheckBox("AMC");
			chckbxAmc.setToolTipText("Average Method Complexity (quality oriented extension to C&ampK metric suite)");
			chckbxAmc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxAmc.isSelected()) {
					
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxAmc.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxAmc.setBounds(10, 30, 63, 40);
			chckbxAmc.setSelected(true);
			chckbxAmc.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxAmc);
			
			chckbxDit = new JCheckBox("DIT");
			chckbxDit.setToolTipText("Depth of Inheritance Tree");
			chckbxDit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxDit.isSelected()) {
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxDit.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxDit.setBounds(80, 30, 63, 40);
			chckbxDit.setSelected(true);
			chckbxDit.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxDit);
			
			chckbxNoc = new JCheckBox("NOC");
			chckbxNoc.setToolTipText("Number of Children");
			chckbxNoc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxNoc.isSelected()) {
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxNoc.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxNoc.setBounds(150, 30, 63, 40);
			chckbxNoc.setSelected(true);
			chckbxNoc.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxNoc);
			
			chckbxCbo = new JCheckBox("CBO");
			chckbxCbo.setToolTipText("Coupling between object classes");
			chckbxCbo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxCbo.isSelected()) {
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxCbo.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxCbo.setBounds(220, 30, 63, 40);
			chckbxCbo.setSelected(true);
			chckbxCbo.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxCbo);
			
			chckbxLcom = new JCheckBox("LCOM");
			chckbxLcom.setToolTipText("Lack of cohesion in methods");
			chckbxLcom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxLcom.isSelected()) {
			
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxLcom.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxLcom.setBounds(290, 30, 63, 40);
			chckbxLcom.setSelected(true);
			chckbxLcom.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxLcom);
			
			chckbxCa = new JCheckBox("CA");
			chckbxCa.setToolTipText("Afferent coupling (not a C&K metric)");
			chckbxCa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
					if(chckbxCa.isSelected()) {
						chckbxCc.setSelected(false);
					}else {
					
					}
				}
			});
			chckbxCa.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxCa.setBounds(360, 30, 63, 40);
			chckbxCa.setSelected(true);
			chckbxCa.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxCa);
			
			chckbxCe = new JCheckBox("CE");
			chckbxCe.setToolTipText("Efferent coupling (not a C&K metric)");
			chckbxCe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxCe.isSelected()) {
						
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxCe.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxCe.setBounds(500, 80, 43, 40);
			chckbxCe.setSelected(true);
			chckbxCe.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxCe);
			
			chckbxNpm = new JCheckBox("NPM");
			chckbxNpm.setToolTipText("Number of Public Methods for a class (not a C&K metric; not a C&K metric; CIS: Class Interface Size in the QMOOD metric suite)");
			chckbxNpm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxNpm.isSelected()) {
						
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxNpm.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxNpm.setBounds(360, 80, 63, 40);
			chckbxNpm.setSelected(true);
			chckbxNpm.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxNpm);
			
			chckbxLcom3 = new JCheckBox("LCOM3");
			chckbxLcom3.setToolTipText("Lack of cohesion in methods Henderson-Sellers version");
			chckbxLcom3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxLcom3.isSelected()) {
					
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxLcom3.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxLcom3.setBounds(290, 80, 70, 40);
			chckbxLcom3.setSelected(true);
			chckbxLcom3.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxLcom3);
			
			chckbxLoc = new JCheckBox("LOC");
			chckbxLoc.setToolTipText("Lines of Code (not a C&K metric)");
			chckbxLoc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxLoc.isSelected()) {
					
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxLoc.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxLoc.setBounds(640, 30, 63, 40);
			chckbxLoc.setSelected(true);
			chckbxLoc.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxLoc);
			
			chckbxDam = new JCheckBox("DAM");
			chckbxDam.setToolTipText("Data Access Metric (QMOOD metric suite)");
			chckbxDam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxDam.isSelected()) {
						
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxDam.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxDam.setBounds(10, 80, 63, 40);
			chckbxDam.setSelected(true);
			chckbxDam.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxDam);
			
			chckbxMoa = new JCheckBox("MOA");
			chckbxMoa.setToolTipText("Measure of Aggregation (QMOOD metric suite)");
			chckbxMoa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxMoa.isSelected()) {
						
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxMoa.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxMoa.setBounds(80, 80, 63, 40);
			chckbxMoa.setSelected(true);
			chckbxMoa.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxMoa);
			
			chckbxMfa = new JCheckBox("MFA");
			chckbxMfa.setToolTipText("Measure of Functional Abstraction (QMOOD metric suite)");
			chckbxMfa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxMfa.isSelected()) {
						
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxMfa.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxMfa.setBounds(150, 80, 63, 40);
			chckbxMfa.setSelected(true);
			chckbxMfa.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxMfa);
			
			chckbxCam = new JCheckBox("CAM");
			chckbxCam.setToolTipText("Cohesion Among Methods of Class (QMOOD metric suite)");
			chckbxCam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxCam.isSelected()) {
						
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxCam.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxCam.setBounds(220, 80, 63, 40);
			chckbxCam.setSelected(true);
			chckbxCam.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxCam);
			
			chckbxIc = new JCheckBox("IC");
			chckbxIc.setToolTipText("Inheritance Coupling (quality oriented extension to C&ampK metric suite)");
			chckbxIc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxIc.isSelected()) {
					
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxIc.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxIc.setBounds(500, 30, 43, 40);
			chckbxIc.setSelected(true);
			chckbxIc.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxIc);
			
			chckbxCbm = new JCheckBox("CBM");
			chckbxCbm.setToolTipText("Coupling Between Methods (quality oriented extension to C&ampK metric suite)");
			chckbxCbm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxCbm.isSelected()) {
						
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxCbm.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxCbm.setBounds(430, 80, 63, 40);
			chckbxCbm.setSelected(true);
			chckbxCbm.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxCbm);
			
			chckbxWmc = new JCheckBox("WMC");
			chckbxWmc.setToolTipText("Weighted methods per class (NOM: Number of Methods in the QMOOD metric suite)");
			chckbxWmc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxWmc.isSelected()) {
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxWmc.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxWmc.setBounds(430, 30, 63, 40);
			chckbxWmc.setSelected(true);
			chckbxWmc.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxWmc);
			
			chckbxRfc = new JCheckBox("RFC");
			chckbxRfc.setToolTipText("Response for a Class");
			chckbxRfc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxRfc.isSelected()) {
	
						chckbxCc.setSelected(false);
					}
				}
			});
			chckbxRfc.setFont(new Font("Dialog", Font.BOLD, 11));
			chckbxRfc.setBounds(570, 30, 63, 40);
			chckbxRfc.setSelected(true);
			chckbxRfc.setBackground(Color.LIGHT_GRAY);
			panel_2.add(chckbxRfc);
				
				chckbxCc = new JCheckBox("CC ");
				chckbxCc.setBounds(658, 108, 45, 40);
				panel_2.add(chckbxCc);
				chckbxCc.setToolTipText("McCabe's Cyclomatic Complexity");
				chckbxCc.setForeground(Color.RED);
				
				chckbxCc.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxCc.isSelected() == true ) {			
								chckbxAmc.setSelected(false);
								chckbxLcom.setSelected(false);
								chckbxMoa.setSelected(false);
								chckbxMfa.setSelected(false);
								chckbxCam.setSelected(false);
								chckbxIc.setSelected(false);
								chckbxCbm.setSelected(false);
								chckbxWmc.setSelected(false);
								chckbxDit.setSelected(false);
								chckbxNoc.setSelected(false);
								chckbxCbo.setSelected(false);
								chckbxCa.setSelected(false);
								chckbxCe.setSelected(false);
								chckbxNpm.setSelected(false);
								chckbxLoc.setSelected(false);
								chckbxLcom3.setSelected(false);
								chckbxRfc.setSelected(false);
								chckbxDam.setSelected(false);
						}
					}
				});
				chckbxCc.setFont(new Font("Dialog", Font.BOLD, 11));
				chckbxCc.setBackground(Color.LIGHT_GRAY);
				
				JPanel panel_5 = new JPanel();
				panel_5.setBackground(Color.DARK_GRAY);
				panel_5.setBounds(0, 0, 730, 20);
				panel_2.add(panel_5);
				panel_5.setLayout(null);
				
				JLabel lblSelectMetricsTo = new JLabel("Select metrics to calculate :");
				lblSelectMetricsTo.setBounds(10, 3, 189, 15);
				lblSelectMetricsTo.setForeground(Color.WHITE);
				lblSelectMetricsTo.setFont(new Font("Dialog", Font.BOLD, 12));
				panel_5.add(lblSelectMetricsTo);
				
					panel_3 = new JPanel();
					panel_3.setBounds(0, 256, 730, 82);
					getContentPane().add(panel_3);
					panel_3.setBackground(new Color(240, 240, 240));
					panel_3.setLayout(null);
					panel_3.setEnabled(false);
					
					JPanel panel_6 = new JPanel();
					panel_6.setLayout(null);
					panel_6.setBackground(Color.DARK_GRAY);
					panel_6.setBounds(0, 0, 730, 20);
					panel_3.add(panel_6);
					
					JLabel lblSelectTheExport = new JLabel("Select the export format and, then, click on Elaborate");
					lblSelectTheExport.setForeground(Color.WHITE);
					lblSelectTheExport.setFont(new Font("Dialog", Font.BOLD, 12));
					lblSelectTheExport.setBounds(10, 3, 447, 15);
					panel_6.add(lblSelectTheExport);
					btnCsv = new JToggleButton("");
					btnCsv.setToolTipText("Export metrics in CSV format");
					btnCsv.setEnabled(false);
					btnCsv.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/File-CSV-icon.png")));
					
					btnCsv.setBounds(10, 36, 40, 40);
					panel_3.add(btnCsv);
					
					btnXls = new JToggleButton("");
					btnXls.setToolTipText("Export metrics in XLSX format");
					btnXls.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/xls.png")));
					btnXls.setEnabled(false);
					//btnXls.addActionListener(new ActionListener() {
						//public void actionPerformed(ActionEvent e) {
						//}
					//});
					btnXls.setBounds(60, 36, 40, 40);
					panel_3.add(btnXls);
					
					btnXml = new JToggleButton("");
					btnXml.setBounds(110, 36, 40, 40);
					panel_3.add(btnXml);
					btnXml.setToolTipText("Export metrics in XML format");
					btnXml.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/xmlb.png")));
					btnXml.setEnabled(false);
					
					btnElaborate = new JButton("ELABORATE");
					btnElaborate.setBounds(588, 41, 130, 35);
					panel_3.add(btnElaborate);
					btnElaborate.setToolTipText("Calculate and export the metrics");
					btnElaborate.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/elaborate_big.png")));
					
					btnElaborate.setEnabled(false);
					btnElaborate.setForeground(UIManager.getColor("Button.foreground"));
					btnElaborate.setFont(new Font("Dialog", Font.BOLD, 9));
					
					
					btnElaborate.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
						if(fileSelected[0] != null) { 	
							if (rdbtnCkjm.isSelected() ) {
							isCheck.checkMetrics(
									chckbxAmc.isSelected(),
									chckbxLcom.isSelected(),
									chckbxMoa.isSelected(),
									chckbxMfa.isSelected(),
									chckbxCam.isSelected(),
									chckbxIc.isSelected(),
									chckbxCbm.isSelected(),
									chckbxWmc.isSelected(),
									chckbxDit.isSelected(),
									chckbxNoc.isSelected(),
									chckbxCbo.isSelected(),
									chckbxCc.isSelected(),
									chckbxCa.isSelected(),
									chckbxCe.isSelected(),
									chckbxNpm.isSelected(),
									chckbxLoc.isSelected(),
									chckbxLcom3.isSelected(),
									chckbxRfc.isSelected(),
									chckbxDam.isSelected()
									);
							if(btnCsv.isSelected()==true || btnXml.isSelected()==true|| btnXls.isSelected()==true) {
								elaborate();}
							else {
								System.out.println("Please, select at least one export format!");
						        JOptionPane.showMessageDialog(null,"Please, select at least one export format!","",JOptionPane.INFORMATION_MESSAGE); 

								
							}
				
							
							}else if (rdbtnJasome.isSelected() ) {
								elaborate();
							}
						}else {
							JOptionPane.showMessageDialog(null, "Error : no selected file !");

							System.out.println("Select a file");
							}
						}
					
					});
			
		
			
			
//LISTENERS
		/* BACK */ 
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);			
				}
			});
		/*EXIT */
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(1);
				}
			});
		/* OPEN FILE */
		/* EXPORT */ 
		/* ELABORATE */
//--------------------
	}
	
/* Funzioni */
/**
 * I open a specific file via the filechooser and assign it to fileSelected [0]
 */
	public void openFile() {
		JFileChooser fileChooser = new JFileChooser(new File(Settings._JARPATH + "/testClass"));
		
		fileChooser.addChoosableFileFilter(new FileFilter() {
		
		    public String getDescription() {
		    	if ( rdbtnCkjm.isSelected() ) return "JAVA BYTECODE (*.jar, *.class)";
		    	if ( rdbtnJasome.isSelected() ) return "JAVA BYTECODE or PROJECT DIRECTORY ( *.java )";
		    	return "Error";
		    }
		 
		    public boolean accept(File f) {
		    	
		        if (f.isDirectory()) {
		            return true;
		        } else {
		        	if ( rdbtnCkjm.isSelected() ) 
		        	{
		        		
		            return f.getName().toLowerCase().endsWith(".jar") || f.getName().toLowerCase().endsWith(".class");
		            }
		        	if ( rdbtnJasome.isSelected() ) 		     	
		        	{	       
		        	fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);	
		            return f.getName().toLowerCase().endsWith(".java");
		            }
		        }
				return false;
		    }
		});
		
		fileChooser.setAcceptAllFileFilterUsed(false);
	    fileChooser.showOpenDialog(dataMenu.this);
		fileSelected[0] = fileChooser.getSelectedFile();
		textFileName.setText(fileSelected[0].getName());
		System.out.println("You have selected the file : " + fileSelected[0].getName());
	    System.out.println(fileChooser.getSelectedFile());	
		btnElaborate.setEnabled(true);
		if(rdbtnCkjm.isSelected()){
			setPanelEnabled(panel_3,true);
		}
		if(rdbtnJasome.isSelected()) {
			setPanelEnabled(panel_3,true);
			btnCsv.setSelected(false);
			btnXml.setSelected(false);
			btnCsv.setEnabled(true);
			btnXml.setEnabled(true);
			btnXls.setEnabled(false);
			btnXls.setSelected(true);
			btnXls.setDisabledIcon(new ImageIcon(getClass().getClassLoader().getResource("images/xls.png")));
			
		}	
	}
	/**
	 * Metric calculation function using the selected tool
	 */
	public void elaborate() {	
	//if(fileSelected[0].getName() != "") {	
		//Ckjm
		if(rdbtnCkjm.isSelected()) {
			tool = toolFactory.useTool("CKJM",fileSelected);	
			//COMMAND : CKJM 
			try {
				outputClass.resetData();
				//new Thread(new Runnable() {
				    //public void run(){
						//Reset the array that analyzes the file and saves the metrics
						//outputClass.resetData();
						CkjmCalculateMetrics onCommand = new CkjmCalculateMetrics(tool);
						ToolAction onAction = new ToolAction(onCommand);
						onAction.action();
						//}
					//}).start();
				//System.out.println("Number of analyzed classes = " + outputClass.data.size());
				//JOptionPane.showMessageDialog(null, "Success!" + " Number of classes analyzed = " + outputClass.data.size(),"",JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception e) {
				logger.error(Settings.getTime() + e.getMessage());
				 JOptionPane.showMessageDialog(null, "ERROR : "+e);
			}
			//EXPORT
			if (btnCsv.isSelected()) exportCSV();
			if (btnXml.isSelected()) exportXML(); 
			if (btnXls.isSelected()) exportXLS();
			
		} 
		//Jasome 
		if(rdbtnJasome.isSelected()) {
			tool = toolFactory.useTool("JASOME", fileSelected);
			 
			//COMMAND : JASOME 
			try {
				outputClass.resetData();
				//flag format for Jasome Script
				if(btnCsv.isSelected()) {
					 //EXPORT JASOME CSV
					csvFormatJasome = 1;
				 }else csvFormatJasome = 0;
				
				if(btnXml.isSelected()) {
					xmlFormatJasome = 1;
				}else xmlFormatJasome = 0;
				//
				
				new Thread(new Runnable() {
					
				    public void run(){
					JasomeCalculateMetrics onCommand = new JasomeCalculateMetrics(tool);
					ToolAction onAction = new ToolAction(onCommand);
					onAction.action();
					
					}
				}).start();
				
				
			}catch(Exception e) {
				logger.error(Settings.getTime() + e.getMessage());
				 JOptionPane.showMessageDialog(null, "ERROR : "+e);
			}
			
		 
		
		}
	  //}else System.out.println("Select a file");
		
	    //JOptionPane.showMessageDialog(null, "SUCCESS - Metrics Export in ");   
		//JOptionPane.showMessageDialog(null, "Success!" + " Metrics Export!");
	}
	//Metrics export functions in different formats
	public void exportCSV() {
		try {	
			CkjmExportMetricsCSV onCommand = new CkjmExportMetricsCSV(tool);
			ToolAction onAction = new ToolAction(onCommand);
			onAction.action();
			//System.out.println("Number of analyzed classes = " + outputClass.data.size());
			//JOptionPane.showMessageDialog(null, "Success!" + " Metrics Export!");
			
		}catch(Exception e) {
			logger.error(Settings.getTime() + e.getMessage());
			 JOptionPane.showMessageDialog(null, "ERROR : "+e);
		}
	}
	
	public void exportXLS() {
		try {	
			CkjmExportMetricsXLS onCommand = new CkjmExportMetricsXLS(tool);
			ToolAction onAction = new ToolAction(onCommand);
			onAction.action();
			//System.out.println("Number of analyzed classes = " + outputClass.data.size());
			//JOptionPane.showMessageDialog(null, "Success!" + " Metrics Export!");
			
		}catch(Exception e) {
			logger.error(Settings.getTime() + e.getMessage());
			 JOptionPane.showMessageDialog(null, "ERROR : "+e);
		}
	}
	
	public void exportXML() {
		try {	
			CkjmExportMetricsXML onCommand = new CkjmExportMetricsXML(tool);
			ToolAction onAction = new ToolAction(onCommand);
			onAction.action();
			//System.out.println("Number of analyzed classes = " + outputClass.data.size());
			//JOptionPane.showMessageDialog(null, "Success!" + " Metrics Export!");
			
		}catch(Exception e) {
			logger.error(Settings.getTime() + e.getMessage());
			 JOptionPane.showMessageDialog(null, "ERROR : "+e);
		}
	}
	//
	
	//Enable graphic elements for the Jasome tool	
	public void showJasomePanel() {
	setPanelEnabled(panel_2,false);
	setPanelEnabled(panel_3,false);
	btnCsv.setEnabled(false);
	btnXml.setEnabled(false);
	btnXls.setEnabled(false);
	//setPanelEnabled(panel_5,false);
	//setPanelEnabled(panel_7,false);
	

	lblSelectFileclass.setText("Open .java file :");
	fileSelected[0] = null;
	btnElaborate.setEnabled(false);
	textFileName.setText("file name");
	}
	
	//Enable graphic elements for the CKJM tool	
	public void showCKJMPanel() {
		setPanelEnabled(panel_2,true);
		setPanelEnabled(panel_3,false);
		btnXls.setDisabledIcon(null);
		btnXls.setSelected(false);
		btnCsv.setDisabledIcon(null);
		btnCsv.setSelected(false);
		//setPanelEnabled(panel_5,true);
		//setPanelEnabled(panel_7,true);
	lblSelectFileclass.setText("Open  .class or .jar file :");
	fileSelected[0]=null;
	btnElaborate.setEnabled(false);
	textFileName.setText("file name");
	}
	
	/**
	 * Activate the components of a JPanel
	 * @param panel
	 * @param isEnabled
	 */
	void setPanelEnabled(JPanel panel, Boolean isEnabled) {
	    panel.setEnabled(isEnabled);

	    Component[] components = panel.getComponents();

	    for (Component component : components) {
	        if (component instanceof JPanel) {
	            setPanelEnabled((JPanel) component, isEnabled);
	        }
	        component.setEnabled(isEnabled);
	    }
	}
	
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
}