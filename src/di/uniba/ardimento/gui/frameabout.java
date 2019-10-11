package di.uniba.ardimento.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Desktop;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import di.uniba.ardimento.utils.Settings;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
/**
 * Class representing the frame with software information
 * @author Francesco Mininno
 *
 */
public class frameabout extends JFrame{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(mainMenu.class);
	/**
	 * Create the application.
	 */
	public frameabout() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {	
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle("Metrics4Java 0.0.1");
		setBounds(100, 100, 730, 500);
		getContentPane().setLayout(null);
		
		

		JLabel lblNewLabel = new JLabel("<html><div align=justify><b>Version</b> : 0.0.1 <br> \n"
				+ "<b>Authors</b> : <br>"
				+ "\n<b>Company</b> : DIB Uniba (Computer Science Department - University of Bari Aldo Moro) <br><br>\n"
				+ "<b>About</b> : Metrics4Java is a software that calculates the java code evaluation metrics, performs evaluation tests using statistical and graphical function. <br><br>This product includes software developed by other open source projects including : <ul>" 
				+ "<li><b>CKJM extended</b> : The program CKJM extended calculates nineteen size and structure software metrics by processing the bytecode of compiled Java files. "
				+ "<li><b>JaSoMe</b> : Java Source Metrics, Jasome (JAH-suhm, rhymes with awesome) is a source code analyzer that mines internal quality metrics from projects based on source code alone. This distinguishes Jasome from similar tools by not requiring the project first be compiled, or even compilable."
				+ "<li><b>RCaller</b> : RCaller is a software library which is developed to simplify calling R from Java. </ul></div></html>");
		
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(29, 36, 656, 274);
		getContentPane().add(lblNewLabel);
		//
		try {
			final URI uri = new URI("https://github.com/rodhilton/jasome");
			final URI uri2 = new URI("https://github.com/mjureczko/CKJM-extended");
			final URI uri3 = new URI("https://github.com/jbytecode/rcaller");
			final URI uri4 = new URI("https://github.com/fmininno");
			final URI uri5 = new URI("https://www.uniba.it/docenti/ardimento-pasquale/");
			
		    class OpenUrlAction implements ActionListener {
		      @Override public void actionPerformed(ActionEvent e) {
		        open(uri);
		      }
		    }
		    class OpenUrlAction2 implements ActionListener {
			      @Override public void actionPerformed(ActionEvent e) {
			    	 
			        open(uri2);
			      }
			    }
		    class OpenUrlAction3 implements ActionListener {
			      @Override public void actionPerformed(ActionEvent e) {
			    	
			        open(uri3);
			      }
			    }
		    class OpenUrlAction4 implements ActionListener {
			      @Override public void actionPerformed(ActionEvent e) {
			    	
			        open(uri4);
			      }
			    }
		    class OpenUrlAction5 implements ActionListener {
			      @Override public void actionPerformed(ActionEvent e) {
			    	
			        open(uri5);
			      }
			    }
		    
	    JButton button = new JButton();
	    button.setText("<HTML><div align=justify>JaSoMe v0.6.8-alpha <FONT color=\"#000099\"><U>git page link</U></FONT> Rod Hilton.</div></HTML>");
	    button.setHorizontalAlignment(SwingConstants.LEFT);
	    button.setBorderPainted(false);
	    button.setBounds(46, 337, 361, 20);
	    button.setOpaque(false);
	    button.setBackground(Color.WHITE);
	    button.setToolTipText(uri.toString());
	    button.addActionListener(new OpenUrlAction());
	    getContentPane().add(button);
	    
	    JButton button_1 = new JButton();
	    button_1.setToolTipText(uri2.toString());
	    button_1.setText("<HTML><div align=justify>CKJM-extended v2.2 <FONT color=\"#000099\"><U>git page link</U></FONT> Diomidis Spinellis.</div></HTML>");
	    button_1.setOpaque(false);
	    button_1.setHorizontalAlignment(SwingConstants.LEFT);
	    button_1.setBorderPainted(false);
	    button_1.setBackground(Color.WHITE);
	    button_1.addActionListener(new OpenUrlAction2());
	    button_1.setBounds(46, 312, 396, 20);
	    getContentPane().add(button_1);
	    
	    JButton button_2 = new JButton();
	    button_2.setToolTipText(uri3.toString());
	    button_2.setText("<HTML><div align=justify>RCaller <FONT color=\"#000099\"><U>git page link</U></FONT> Mehmet Hakan Satman (jbytecode).</div></HTML>");
	    button_2.setOpaque(false);
	    button_2.setHorizontalAlignment(SwingConstants.LEFT);
	    button_2.setBorderPainted(false);
	    button_2.setBackground(Color.WHITE);
	    button_2.addActionListener(new OpenUrlAction3());
	    button_2.setBounds(46, 362, 495, 20);
	    getContentPane().add(button_2);
	    
	    JButton button_pardimento = new JButton();
		button_pardimento.setToolTipText("https://www.uniba.it/docenti/ardimento-pasquale/");
		button_pardimento.setText("<HTML><div align=justify>, Pasquale Ardimento (<FONT color=\"#000099\"><U>personal web page</U></FONT>)</div></HTML>");
		button_pardimento.setOpaque(false);
		button_pardimento.setHorizontalAlignment(SwingConstants.LEFT);
		button_pardimento.setFont(new Font("Dialog", Font.PLAIN, 12));
		button_pardimento.setBorderPainted(false);
		button_pardimento.setBackground(Color.WHITE);
		button_pardimento.addActionListener(new OpenUrlAction5());
		button_pardimento.setBounds(322, 48, 396, 20);
		getContentPane().add(button_pardimento);
		
		JButton button_fmininno = new JButton();
		button_fmininno.setFont(new Font("Dialog", Font.PLAIN, 12));
		button_fmininno.setToolTipText("https://github.com/fmininno");
		button_fmininno.setText("<HTML><div align=justify>Francesco Mininno (<FONT color=\"#000099\"><U>personal web page</U></FONT>)</div></HTML>");
		button_fmininno.setOpaque(false);
		button_fmininno.setHorizontalAlignment(SwingConstants.LEFT);
		button_fmininno.setBorderPainted(false);
		button_fmininno.setBackground(Color.WHITE);
		button_fmininno.addActionListener(new OpenUrlAction4());
		button_fmininno.setBounds(80, 48, 396, 20);
		getContentPane().add(button_fmininno);
	    JLabel lblProjectsDownloadPage = new JLabel("Projects download page :");
	    lblProjectsDownloadPage.setFont(new Font("Dialog", Font.ITALIC, 12));
	    lblProjectsDownloadPage.setBounds(46, 292, 235, 15);
	    getContentPane().add(lblProjectsDownloadPage);
	    
	    
	  
		}catch(Exception e) {
			logger.error(Settings.getTime() + e.getMessage());
			System.out.println(e);
		}
		
	  }
	/**
	 * Open an URI
	 * @param uri
	 */
	  private static void open(URI uri) {
	    if (Desktop.isDesktopSupported()) {
	      try {
	        Desktop.getDesktop().browse(uri);
	      } catch (IOException e) { 
	    	  logger.error(Settings.getTime() + e.getMessage());
	    	  System.out.println("ERROR : " + e);
	    	  /* TODO: error handling */ }
	    } else { /* TODO: error handling */ }
	}
}