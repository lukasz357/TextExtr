package textextr;

import gui.MainBox;

import java.awt.EventQueue;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.*;

public class TextExtr {
	private static Log log = LogFactory.getLog(TextExtr.class);
	public static String BASE_PATH = "/home/lukasz357/TextExtrBase/";
	
	public TextExtr() {
		// Configure log4j
		PropertyConfigurator.configure(BASE_PATH+"log4j_mine.properties");
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
	
//	private void getNewUrls(String url) {
//		
//	}
}
