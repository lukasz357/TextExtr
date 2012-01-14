package textextr;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class PDFToAdvertConverterWithDataBaseAccess {
	private DataBase db;
	private ArrayList<FileInfo> fileInfos;
	private ArrayList<Advertisement> adverts;
	@Before
	public void setUp() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		this.db = new DataBase();
		this.fileInfos = db.getFileInfos();
		this.adverts = new ArrayList<Advertisement>();
	}
	
	@Test
	public void conversionTest() {
		PDFToAdvertConverter conv;
		for(FileInfo fi : fileInfos) {
			conv = new PDFToAdvertConverter(fi.getFolder(), fi.getFileName());
			Advertisement adv = conv.parseText();
			adverts.add(adv);
		}
		
		for(Advertisement ad : adverts) {
			System.out.println(ad.toString());
		}
	}
}
