package textextr;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class URLDownloaderRunnable implements Runnable{
	private static Log log = LogFactory.getLog(URLDownloaderRunnable.class);
	private String url = null;
	private String parameters = null;
	private ArrayList<String> oldUrls = null;
	private ArrayList<String> newFilesNames = null;
	private DataBase db = null;
	private JButton btnAktualizuj;
	private JProgressBar bar;
	private JLabel label;
	public URLDownloaderRunnable(String url, String parameters,DataBase db, JButton btnAktualizuj, JProgressBar bar, JLabel label) {
		this.url = url;
		this.parameters = parameters;
		this.db = db;
		this.btnAktualizuj = btnAktualizuj;
		this.bar = bar;
		this.label = label;
		this.oldUrls = db.getUrls();
		this.newFilesNames = new ArrayList<String>();
	}
	@Override
	public void run(){
		try {
//			this.db = new DataBase();
			int lastSlashIndex = -1;
			int slashBeforeLastSlash = -1;
			String fileName = null;
			String folder = null;
			
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
		        		int idx = fileName.indexOf(folder); //jak jest np. 88888888/88888888_99999999
		        		if(idx !=-1) {
		        			String tmp = fileName.substring(9);
		        			fileName = "1_"+tmp;
		        		}
					}
				}
				File f = new File(TextExtr.PDF_BASE_PATH+"/"+fileName);
				if(f.exists() || newFilesNames.contains(fileName)) {
					SecureRandom random = new SecureRandom();
					String randomPrefix = new BigInteger(130, random).toString(32);
					fileName = randomPrefix += fileName;
				}
				newFilesNames.add(fileName);
//				int id = 0;
				if(!oldUrls.contains(s)) {
					db.addNewFileInfo(s, fileName, folder);
	//				if(id > 0) {
//						URLDownloader.fileDownload(s,TextExtr.PDF_BASE_PATH, fileName);
//						db.updateFileInfo(id, true);
	//				}
				}
				
			}
			
			ArrayList<FileInfo> list = db.getFileInfos();
//			log.info("Rozmiar listy: "+list.size());
			boolean downloaded;
			for(FileInfo f : list) {
				downloaded = false;
//				log.info("Plik pobrany? - " + f.isDownloaded());
				if(!f.isDownloaded()) {
					downloaded = URLDownloader.fileDownload(f.getUrl(),TextExtr.PDF_BASE_PATH, f.getFileName());
					if(downloaded) {
						db.updateFileInfoDownloaded(f.getId(), true);
//						log.info("Udany update pliku: " +f.getId());
					}
				}
			}
			
			String str = "<html>" + "<font color=\"#008000\">" + "<b>" + 
					 "Zakończono pobieranie plików." + "</b>" + "</font>" + "</html>";
			label.setText(str);
			log.info("Zakończono pobieranie plików");
			str = "<html>" + "<font color=\"#008000\">" + "<b>" + 
					 "Trwa przetwarzanie plików." + "</b>" + "</font>" + "</html>";
			label.setText(str);
			ArrayList<Advertisement> adverts = new ArrayList<Advertisement>();
			list = db.getFileInfos();
			PDFToAdvertConverter converter;
			Advertisement adv;
			for(FileInfo fi : list) {
				adv = null;
				if(!fi.isParsed()) {
					converter = new PDFToAdvertConverter(fi.getFolder(), fi.getFileName());
					adv = converter.parseText();
					if(adv != null) {
						adv.setFileInfoID(fi.getId());
						adverts.add(adv);
						db.updateFileInfoParsed(fi.getId(), true);
					}
				}
			}
			db.addAdverts(adverts);

			log.info("Pomyślnie dodano ogłoszenia do bazy.");
			str = "<html>" + "<font color=\"#008000\">" + "<b>" + 
					 "Zakończono." + "</b>" + "</font>" + "</html>";
			label.setText(str);
			bar.setIndeterminate(false);
			btnAktualizuj.setEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Problem podczas aktualizacji pliku");
		}
	}

}
