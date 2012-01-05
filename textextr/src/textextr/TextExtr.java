package textextr;

import gui.MainBox;

import java.awt.EventQueue;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.*;

public class TextExtr {
	public static String BASE_PATH = "/home/lukasz357/TextExtrBase/";
	private static Logger log;
	
	public TextExtr() {
		// Configure log4j
		PropertyConfigurator.configure(BASE_PATH+"log4j_mine.properties");
		log = Logger.getLogger( TextExtr.class );
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainBox window = new MainBox();
					window.getFrmTextextr().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		log.debug("Log4j has been initialized");
	}
	
}
