package textextr;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class URLDownloaderRunnable implements Runnable{
	private static Log log = LogFactory.getLog(URLDownloaderRunnable.class);
	private ArrayList<String> urls;
	public URLDownloaderRunnable(ArrayList<String> urls) {
		this.urls = urls;
	}
	@Override
	public void run() {
		for (String s : urls) {
			try {
				URLDownloader.fileDownload(s,TextExtr.BASE_PATH);
			} catch (Exception e) {
				log.error("Problem podczas downloadu pliku z adresu: " + s);
			}
		}
	}

}
