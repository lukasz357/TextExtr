package textextr;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class URLDownloaderRunnable implements Runnable{
	private static Log log = LogFactory.getLog(URLDownloaderRunnable.class);
	private String url = null;
	private String parameters = null;
	private ArrayList<String> oldUrls = null;
	private DataBase db = null;
	private JProgressBar bar;
	private JLabel label;
	public URLDownloaderRunnable(String url, String parameters, ArrayList<String> oldUrls, DataBase db, JProgressBar bar, JLabel label) {
		this.url = url;
		this.parameters = parameters;
		this.oldUrls = oldUrls;
		this.db = db;
		this.bar = bar;
		this.label = label;
	}
	@Override
	public void run(){
		try {
			String result = "";
			HTMLParser p = new HTMLParser(url, parameters);
			p.getAllDatas();
			p.extractPDFUrls();
			for (String s : p.getUrls()) {
				if(!oldUrls.contains(s)) {
					db.addNewUrl(s);
					URLDownloader.fileDownload(s,TextExtr.BASE_PATH);
				}
				else {
					result += result.length() < 1 ? s : ", "+s;
				}
			}
			bar.setIndeterminate(false);
			String str = "<html>" + "<font color=\"#008000\">" + "<b>" + 
					 "Zakończono." + "</b>" + "</font>" + "</html>";
			label.setText(str);
			log.debug("Zakończono pobieranie plików");
		} catch (Exception e) {
			log.error("Problem podczas downloadu pliku");
		}
	}

}
