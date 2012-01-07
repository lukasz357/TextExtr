package textextr;

import org.junit.Before;
import org.junit.Test;

public class PDFToTextConverterTest {
	PDFToTextConverter conv;
	String filename;
	@Before
	public void setUp() {
		filename = TextExtr.BASE_PATH+"/1_20120106.pdf";
		conv = new PDFToTextConverter();
	}
	@Test
	public void testPdftoText() {
		String s = conv.pdftoText(filename);
		System.out.println(s);
	}

}
