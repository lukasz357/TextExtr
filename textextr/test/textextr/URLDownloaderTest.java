package textextr;

//import static org.junit.Assert.*;

import org.junit.Test;

public class URLDownloaderTest {

	@Test
	public void test() throws Exception {
		HTMLParser p = new HTMLParser("http://www.nauka.gov.pl/app/wyszukiwarka,24.html", "filed_0=&filed_1=&filed_2=&filed_3=&filed_4=");
		p.getAllDatas();
		p.extractPDFUrls();
		for (String s : p.getUrls()) {
			URLDownloader.fileDownload(s,"/home/lukasz357/BazaPDF");
		}
	}

}
