package textextr;

import org.junit.Before;
import org.junit.Test;

public class PDFToTextConverterTest {
	PDFToTextConverter conv;
	String fileName;
	@Before
	public void setUp() {
		fileName = TextExtr.BASE_PATH+"/aaaaa.pdf";
		conv = new PDFToTextConverter();
	}
	@Test
	public void testPdftoText() {
		String s = conv.pdftoText(fileName);
		System.out.println(s);
	}

}
