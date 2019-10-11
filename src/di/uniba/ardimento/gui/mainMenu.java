package di.uniba.ardimento.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.SimpleLayout;
import di.uniba.ardimento.utils.Settings;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Point;
/**
 * Class representing the start menu
 * @author Francesco Mininno
 *
 */
public class mainMenu {
	/**
	 * Global logger ( main )
	 */
	static Logger logger = LogManager.getLogger(mainMenu.class);
	private JFrame frmCode;
	private JPanel panelTitle;
	private JLabel labelLogo;
	private JLabel lblCodeAnalyzer;
	/**
	 * Launch the application.
	 */
	
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
	
	public static void main(String[] args) {
	
		//load log4.properties filepath
		PropertyConfigurator.configure(mainMenu.class.getClassLoader().getResource("log4j.properties").getPath());
	
		//setting up a FileAppender dynamically...
	    SimpleLayout layout = new SimpleLayout();
	   
	    FileAppender appender;
		try {
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");
			appender = new FileAppender(layout,Settings._JARPATH + "/logs/"+sdf.format(timestamp)+".log");
			logger.addAppender(appender);
			//logger.setLevel(Level.DEBUG);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    
	  
        BasicConfigurator.configure(); 
        //
        
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {			
					mainMenu window = new mainMenu();
					window.frmCode.setUndecorated(true);
					FrameDragListener frameDragListener = new FrameDragListener(window.frmCode);
				    window.frmCode.addMouseListener(frameDragListener);
				    window.frmCode.addMouseMotionListener(frameDragListener);
					window.frmCode.setVisible(true);
				} catch (Exception e) {
					logger.error(Settings.getTime() + e.getMessage());
					System.out.println("ERROR : " + e);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/**
		 * Deploy program
		 */
		if (Settings.deployJar()==1) {
			logger.error(Settings.getTime() + "DEPLOY ERROR - APPLICATION CLOSED");
			JOptionPane.showMessageDialog(null, "The program will be closed!","",JOptionPane.INFORMATION_MESSAGE);
			System.exit(1);
		}else {
			logger.info(Settings.getTime() + "START NEW SESSION");
		}
		//
		Settings.deleteTempFolder();
		//
		
		frmCode = new JFrame();
		frmCode.setTitle("Metrics4Java");
		frmCode.getContentPane().setBackground(Color.WHITE);
		frmCode.getContentPane().setLayout(null);
		
		panelTitle = new JPanel();
		panelTitle .setBackground(Color.BLUE);
		panelTitle .setBounds(0, 0, 246, 98);
		frmCode.getContentPane().add(panelTitle);
		panelTitle .setLayout(null);
		
		labelLogo = new JLabel("");
		labelLogo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/logo_clean.png")));
		labelLogo.setBounds(-56, -94, 369, 276);
		panelTitle .add(labelLogo);
		
		lblCodeAnalyzer = new JLabel("Metrics4Java");
		lblCodeAnalyzer.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCodeAnalyzer.setBounds(10, 50, 128, 61);
		panelTitle .add(lblCodeAnalyzer);
		
		JButton btnNewButton = new JButton("CALCULATE METRICS");
		btnNewButton.setToolTipText("Calculate and export the quality metrics of the software using the metric tools");
		btnNewButton.setBounds(29, 110, 180, 42);
		frmCode.getContentPane().add(btnNewButton);
		
		JButton btnReport = new JButton("PRODUCE REPORTS");
		btnReport.setToolTipText("Produce and view reports of the calculated metric files");
		btnReport.setBounds(29, 160, 180, 42);
		frmCode.getContentPane().add(btnReport);
		
		JButton btnTest = new JButton("RUN TEST");
		btnTest.setToolTipText("Run and export tests or graphs on the produced metrics files\n\n");
		btnTest.setBounds(29, 210, 180, 42);
		frmCode.getContentPane().add(btnTest);
		
		JButton btnX = new JButton();
		btnX.setToolTipText("Exit");
	
		btnX.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/close_big.png")));
		btnX.setForeground(Color.RED);
		btnX.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnX.setBounds(200, 260, 38, 38);
		frmCode.getContentPane().add(btnX);
		
		frmCode.setBounds(100, 100, 243, 324);
		frmCode.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmCode.setJMenuBar(menuBar);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmInfo = new JMenuItem("Info");
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameabout frmInfo = new frameabout();
				frmInfo.setVisible(true);		
			}
		});
		mnHelp.add(mntmInfo);
	
/* LISTNERS */
	/* 1) ELABORATE MENU : dataMenu */
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataMenu dataMenu = new dataMenu();
				dataMenu.setVisible(true);
				
			}
		});
	/* 2) REPORT MENU : reportMenu */
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				reportMenu reportMenu = new reportMenu();
				reportMenu.setVisible(true);
				
			}
		});
	/* 3) TEST MENU : testMenu */
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testMenu testMenu = new testMenu();
				testMenu.setVisible(true);
				
			}
		});

	/* 4) EXIT */
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
	}
	
}