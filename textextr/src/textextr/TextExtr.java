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
					Class.forName("org.sqlite.JDBC");
					DataBase db = new DataBase();
					ArrayList<String> urls = db.getUrls();
					Runnable r = new URLDownloaderRunnable("http://www.nauka.gov.pl/app/wyszukiwarka,24.html", "filed_0=&filed_1=&filed_2=&filed_3=&filed_4=", urls, db);
					Thread t = new Thread(r);
					t.start();
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
