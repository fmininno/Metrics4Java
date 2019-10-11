package di.uniba.ardimento.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import di.uniba.ardimento.rInsideJava.Rtest;
import di.uniba.ardimento.utils.ClassMetricsDataContainer;
import di.uniba.ardimento.utils.Settings;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class testMenu extends JFrame {
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
/* Work variables*/
	String _TOOL = "JASOME";
/*---------------------*/
	/* Graphic elements ------------ */
	/* PANELS */
		private JPanel panel;
		private JPanel panel_1;
	/* LABELS */
		private JLabel lblTitle;
		private JButton btnBack;
		private JButton btnBoxPlot;
		private JScrollPane scrollPane;
		private JScrollPane scrollPane_1;
		private JLabel lblFileSelectedb;
		private JTextField textFileB;
		private JLabel lblFileSelecteda;
		private JTextField textFileA;
		private JList<String> list;
		private JList<String> list_1;
		private JPanel panel_5;
		private JLabel lblSelectTestTo;
		private JPanel panel_7;
		private JLabel lblSelectGraphTo;
	/**----------------------
	 * Create the application.
	 */
	public testMenu() {
		
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
		
		final JTextArea txtrWaitingForOperations = new JTextArea();
		txtrWaitingForOperations.setEditable(false);
		txtrWaitingForOperations.setText("Waiting for operation... \n");
		txtrWaitingForOperations.setForeground(Color.GREEN);
		txtrWaitingForOperations.setFont(new Font("Dialog", Font.PLAIN, 12));
		final JScrollPane scroll = new JScrollPane(txtrWaitingForOperations);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txtrWaitingForOperations.setBackground(Color.BLACK);
		txtrWaitingForOperations.setBounds(0, 322, 730, 121);
		scroll.setBounds(0, 338, 730, 105);
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
				
		/* PROPRIETA' ELEMENTI GRAFICI */
		setBounds(100, 100, 730, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 0, 730, 29);
		getContentPane().add(panel);
		
		lblTitle = new JLabel("RUN TEST");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(lblTitle);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 27, 730, 43);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Please, first select a file from list A, then from list B");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNewLabel.setBounds(12, 12, 706, 35);
		panel_1.add(lblNewLabel);
		
		//Path of Jasome xls files
		File dir = new File(Settings._JARPATH + "/result/jasome/");
        DefaultListModel<String> model = new DefaultListModel<String>();
        	 for (final File fileEntry : dir.listFiles()) {
        	     
        	    //System.out.println(fileEntry.getName());
        	    model.addElement(fileEntry.getName());
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
			button.setForeground(Color.RED);
			button.setFont(new Font("Tahoma", Font.BOLD, 11));
			button.setBounds(685, 455, 38, 38);
			getContentPane().add(button);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(0, 68, 730, 177);
			getContentPane().add(panel_2);
			panel_2.setLayout(null);
			
			//Liste
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 12, 350, 110);
			panel_2.add(scrollPane);
			list =  new JList<String>(model);
			scrollPane.setViewportView(list);
			list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(372, 12, 350, 110);
			panel_2.add(scrollPane_1);
			
			list_1 =  new JList<String>(/*model*/);
			scrollPane_1.setViewportView(list_1);
			list_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			
			textFileB = new JTextField();
			textFileB.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 10));
			textFileB.setEditable(false);
			textFileB.setColumns(10);
			textFileB.setBounds(371, 149, 350, 19);
			panel_2.add(textFileB);
			
			textFileA = new JTextField();
			textFileA.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 10));
			textFileA.setEditable(false);
			textFileA.setColumns(10);
			textFileA.setBounds(10, 149, 350, 19);
			panel_2.add(textFileA);
			
			lblFileSelectedb = new JLabel("File selected (B) : ");
			lblFileSelectedb.setBounds(372, 125, 121, 15);
			panel_2.add(lblFileSelectedb);
			lblFileSelectedb.setForeground(Color.BLUE);
			lblFileSelectedb.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
			
			lblFileSelecteda = new JLabel("File selected (A) : ");
			lblFileSelecteda.setBounds(10, 125, 121, 15);
			panel_2.add(lblFileSelecteda);
			lblFileSelecteda.setForeground(Color.BLUE);
			lblFileSelecteda.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
			
			JPanel panel_3 = new JPanel();
			panel_3.setBackground(UIManager.getColor("Panel.background"));
			panel_3.setBounds(0, 245, 365, 95);
			getContentPane().add(panel_3);
			
			
			panel_3.setLayout(null);
			
			JButton btnTest = new JButton("<html>\n<p align=\"center\">\nWilcoxon<br>\nMan<br> \nWhitney</p></html>");
			btnTest.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/test-icon.png")));
			btnTest.setFont(new Font("Dialog", Font.PLAIN, 11));
			btnTest.setToolTipText("Wilcoxon-Mann-Whitney");
			btnTest.setBounds(12, 32, 110, 51);
			
			panel_3.add(btnTest);
			
			panel_5 = new JPanel();
			panel_5.setLayout(null);
			panel_5.setBackground(Color.DARK_GRAY);
			panel_5.setBounds(0, 0, 365, 20);
			panel_3.add(panel_5);
			
			lblSelectTestTo = new JLabel("Select test to run :");
			lblSelectTestTo.setForeground(Color.WHITE);
			lblSelectTestTo.setFont(new Font("Dialog", Font.BOLD, 12));
			lblSelectTestTo.setBounds(10, 3, 189, 15);
			panel_5.add(lblSelectTestTo);
			
			JPanel panel_4 = new JPanel();
			panel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel_4.setLayout(null);
			panel_4.setBackground(UIManager.getColor("Panel.background"));
			panel_4.setBounds(365, 245, 365, 95);
			getContentPane().add(panel_4);
			
			btnBoxPlot = new JButton("<html><p align=\"right\">\nMetrics BoxPlots</p></html>");
			btnBoxPlot.setToolTipText("Generate metrics boxplots");
			btnBoxPlot.setBounds(12, 32, 110, 51);
			panel_4.add(btnBoxPlot);
			btnBoxPlot.setFont(new Font("Dialog", Font.PLAIN, 11));
			btnBoxPlot.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/boxploticon.png")));
			
			panel_7 = new JPanel();
			panel_7.setLayout(null);
			panel_7.setBackground(Color.DARK_GRAY);
			panel_7.setBounds(0, 0, 365, 20);
			panel_4.add(panel_7);
			
			lblSelectGraphTo = new JLabel("Select graph to generate :");
			lblSelectGraphTo.setForeground(Color.WHITE);
			lblSelectGraphTo.setFont(new Font("Dialog", Font.BOLD, 12));
			lblSelectGraphTo.setBounds(10, 3, 189, 15);
			panel_7.add(lblSelectGraphTo);
			
			btnBoxPlot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (!(textFileA.getText().compareTo("") == 0) && !(textFileB.getText().compareTo("") == 0)) {
						try {
							Rtest.generatePlots(textFileA.getText(), textFileB.getText(),_TOOL);
						} catch (EncryptedDocumentException e) {
							// TODO Auto-generated catch block
							logger.error(Settings.getTime() + e.getMessage());
							System.out.println("ERROR : " + e);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							logger.error(Settings.getTime() + e.getMessage());
							System.out.println("ERROR : " + e);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Select the files to run the boxplot","",JOptionPane.INFORMATION_MESSAGE);
					}
				
				}
			});
//LISTENERS
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(1);
				}
			});
			
			btnTest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (!(textFileA.getText().compareTo("") == 0) && !(textFileB.getText().compareTo("") == 0)) {
						try {
							Rtest.generateTest(textFileA.getText(), textFileB.getText(),_TOOL);
						} catch (EncryptedDocumentException e) {
							// TODO Auto-generated catch block
							logger.error(Settings.getTime() + e.getMessage());
							System.out.println("ERROR : " + e);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							logger.error(Settings.getTime() + e.getMessage());
							System.out.println("ERROR : " + e);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Select the files to run the test","",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			
			 list.addListSelectionListener(new ListSelectionListener() {

		            @Override
		            public void valueChanged(ListSelectionEvent arg0) {
		                if (!arg0.getValueIsAdjusting()) {
		                //Load xls files with equals number of Rows and Columns
		                textFileB.setText("");
		                System.out.println("You have selected the file: " + list.getSelectedValue().toString());
		                getRowsAndCols(list.getSelectedValue().toString());
		                //
		                textFileA.setText(list.getSelectedValue().toString());
		                  
		                }
		            }
		        });
			 
			 list_1.addListSelectionListener(new ListSelectionListener() {
				 
		            @Override
		            public void valueChanged(ListSelectionEvent arg0) {
		            	
		                if (!arg0.getValueIsAdjusting()) {
		                
		                if(list_1.getSelectedValue() != null ) textFileB.setText(list_1.getSelectedValue().toString());
		                else textFileB.setText("");
		                }
		            }
		        });
		/* BACK */ 
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					
				}
			});
	}

	public void getRowsAndCols(String fileNameA) {
		String _FILEPATH = Settings._JARPATH + "/result/jasome/";
		boolean isEmpty = true;
		try {
			Workbook workbookA = WorkbookFactory.create(new File(_FILEPATH + fileNameA));
			int[] nRowsA = new int[3];
			int[] nColsA = new int[3];
			//Get total number of rows and cols of the XLS file
			for ( int i = 0; i<3; i++ ) {
				nRowsA[i] = workbookA.getSheetAt(i).getLastRowNum() + 1;
				nColsA[i] = workbookA.getSheetAt(i).getRow(0).getLastCellNum();
			}
			DefaultListModel<String> model = new DefaultListModel<String>();
			File dir = new File(_FILEPATH);
			
			for (final File fileEntry : dir.listFiles()) {
			      //System.out.println(fileEntry.getName());
			      Workbook workbookB = WorkbookFactory.create(new File(_FILEPATH + fileEntry.getName()));
			 
					int[] nRowsB = new int[3];
					int[] nColsB = new int[3];
					
					//Get total number of rows and cols of the XLS file
					for ( int i = 0; i<3; i++ ) {
						nRowsB[i] = workbookB.getSheetAt(i).getLastRowNum() + 1;
						nColsB[i] = workbookB.getSheetAt(i).getRow(0).getLastCellNum();
					}
				
				int canAdd = 1;
				//System.out.println("FileNameB = "  + fileEntry.getName());
				
				for ( int i = 0; i < 3; i++) {
					//System.out.println("Number rows file A & B (Sheet["+i+"] = " + nRowsA[i] +" " + nRowsB[i]);	
					//System.out.println("Number of columns A & B (Sheet["+i+"] = " + nColsA[i] +" " + nColsB[i]);
					 
					if(nRowsA[i] != nRowsB[i] || nColsA[i] != nColsB[i]) canAdd = 0;
					
				}
				
				if (fileEntry.getName().compareTo(fileNameA) == 0 ) canAdd = 0;
				if(canAdd == 1) {
					isEmpty = false;
					model.addElement(fileEntry.getName());
				}
			      
			  }
			
			if(isEmpty == true) {
				model.addElement("");
				System.out.println("There are no testable files for the selected file");
			}
			
			else System.out.println(model.getSize() + " testable files were found with the selected file");
			list_1.setModel(model);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			logger.error(Settings.getTime() + e.getMessage());
			System.out.println("ERROR : " + e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Settings.getTime() + e.getMessage());
			System.out.println("ERROR : " + e);
		} 
	}
}