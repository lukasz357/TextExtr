package textextr;

import java.io.IOException;

import org.junit.Test;


public class HTMLParserTest {

	@Test
	public void test() throws IOException {
		HTMLParser p = new HTMLParser("http://www.nauka.gov.pl/app/wyszukiwarka,24.html", "filed_0=&filed_1=&filed_2=&filed_3=&filed_4=");
		p.getAllDatas();
		p.extractPDFUrls();
		for(String s : p.getUrls()) {
			System.out.println(s);
		}
	}

}
