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
	public URLDownloaderRunnable(String url, String parameters,DataBase db, JProgressBar bar, JLabel label) {
		this.url = url;
		this.parameters = parameters;
		this.oldUrls = db.getUrls();
		this.db = db;
		this.bar = bar;
		this.label = label;
	}
	@Override
	public void run(){
		try {
			int lastSlashIndex = -1;
			int slashBeforeLastSlash = -1;
			String fileName = null;
			String folder = null;
			String result = "";
			
			HTMLParser p = new HTMLParser(url, parameters);
			p.getAllDatas();
			p.extractPDFUrls();
			
			for (String s : p.getUrls()) {
				slashBeforeLastSlash = s.indexOf("/Praca_dla_naukowcow/") + 20;
				lastSlashIndex = s.lastIndexOf('/');

				if ((lastSlashIndex == slashBeforeLastSlash) && (slashBeforeLastSlash != -1 && lastSlashIndex != -1) ) {
					String tmp = s.substring(lastSlashIndex+1);
					int underline = tmp.indexOf('_');
					folder = tmp.substring(0, underline);
					fileName = "1_"+tmp.substring(underline +1);
				}
					
				else{
					if(slashBeforeLastSlash != -1 && lastSlashIndex != -1) {
						folder = s.substring(slashBeforeLastSlash+1, lastSlashIndex);
		        		fileName = s.substring(lastSlashIndex +1);
		        		int idx = fileName.indexOf(folder);
		        		if(idx !=-1) {
		        			String tmp = fileName.substring(9);
		        			fileName = "1_"+tmp;
		        		}
					}
				}
				if(!oldUrls.contains(s)) {
					db.addNewFileInfo(s, folder, fileName);
					URLDownloader.fileDownload(s,TextExtr.PDF_BASE_PATH, fileName);
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
			e.printStackTrace();
			log.error("Problem podczas downloadu pliku");
		}
	}

}
