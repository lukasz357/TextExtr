package textextr;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class URLDownloaderRunnable implements Runnable{
	private static Log log = LogFactory.getLog(URLDownloaderRunnable.class);
	private String url = null;
	private String parameters = null;
	private ArrayList<String> oldUrls = null;
	private DataBase db = null;
	public URLDownloaderRunnable(String url, String parameters, ArrayList<String> oldUrls, DataBase db) {
		this.url = url;
		this.parameters = parameters;
		this.oldUrls = oldUrls;
		this.db = db;
	}
	@Override
	public void run() {
		try {
			HTMLParser p = new HTMLParser(url, parameters);
			p.getAllDatas();
			p.extractPDFUrls();
			for (String s : p.getUrls()) {
				if(!oldUrls.contains(s)) {
					db.addNewUrl(s);
					URLDownloader.fileDownload(s,TextExtr.BASE_PATH);
				}
			}
			log.debug("Zakończono pobieranie plików");
//			db.addNewUrls(newUrls);
		} catch (Exception e) {
			log.error("Problem podczas downloadu pliku");
		}

	}

}
