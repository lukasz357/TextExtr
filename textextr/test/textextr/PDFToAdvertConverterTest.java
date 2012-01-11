package textextr;

import junit.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

public class PDFToAdvertConverterTest {
	private static Log log = LogFactory.getLog(PDFToAdvertConverterTest.class);
	private PDFToAdvertConverter conv ;
	private DataBase db;
	ArrayList<String> fileNames;
	@Before
	public void setUp() throws ClassNotFoundException {
		this.conv = new PDFToAdvertConverter("20111102", "1_20120109_APS_adiunkt_pedagogika.pdf");
		Class.forName("org.sqlite.JDBC");
		this.db = new DataBase();
		fileNames = db.getFilenames();
	}
	
//	@Test
//	public void testInstytucja() {
//		System.out.println(conv.getInstytucja());
//	}
//	
//	@Test
//	public void testMiasto() {
//		System.out.println(conv.getMiasto());
//	}
//	
//	@Test
//	public void testStanowisko() {
//		System.out.println(conv.getStanowisko());
//	}
//	
//	@Test
//	public void testLinkDoStrony() {
//		System.out.println(conv.getLinkDoStrony());
//	}
//	
//	@Test
//	public void testOpis() {
//		System.out.println(conv.getOpis());
//	}
//	
//	@Test
//	public void testDate() throws ParseException{
//		System.out.println(conv.getDataOgloszenia());
//	}
//	@Test
//	public void testTermin() throws ParseException{
//		System.out.println(conv.getTerminSkladaniaOfert());
//	}
//	@Test
//	public void testSlowaKluczowe() {
//		for(String s: conv.getSlowaKluczowe())
//			System.out.println(s);
//	}
	
	@Test public void convertionTest() {
		PDFToAdvertConverter cnv;
		String miasto, stanowisko, linkDoStrony, opis;
		Date dataOgloszenia, terminSkladaniaOfert;
		String [] slowaKluczowe;
		for(String s : fileNames) {
			log.info("************************** " + s +" **************************");
			cnv = new PDFToAdvertConverter("20111102", s);
			print("//===== MIASTO:");
			if((miasto = cnv.getMiasto()) != null)
				print(miasto);
			print("//===== STANOWISKO:");
			if((stanowisko = cnv.getStanowisko()) != null)
				print(stanowisko);
			print("//===== LINK DO STRONY:");
			if((linkDoStrony = cnv.getLinkDoStrony())!= null)
				print(linkDoStrony);
			print("//===== DATA OGŁOSZENIA:");
			if((dataOgloszenia = cnv.getDataOgloszenia()) != null)
				print(dataOgloszenia.toString());
			print("//===== TERMIN SKŁADANIA OFERT:");
			if((terminSkladaniaOfert = cnv.getTerminSkladaniaOfert()) != null )
				print(terminSkladaniaOfert.toString());
			print("//===== SŁOWA KLUCZOWE:");
			if((slowaKluczowe = cnv.getSlowaKluczowe()) != null)
				for(String ss : slowaKluczowe)
					print(ss);
			print("//===== OPIS:");
			if((opis = cnv.getOpis()) != null)
				print(opis);
		}
	}
	
	private void print(String s) {
		System.out.println(s);
	}
//	@Test
//	public void testConvertDate() {
//		String [] l = new String[] {"11 Styczeń 2009", 
//									"22 Styczen 2345", 
//									"22 styczen 99", 
//									"23 stycznia 2222", 
//									"23 Luty 2222", 
//									"23 lutego 2222",
//									"23 Marzec 2222",
//									"22 marca 2222",
//									"22 Kwiecień 2222",
//									"22 kwiecien 2222",
//									"22 kwietnia 2222",									
//									"22 maj 2222",
//									"22 maja 2222",
//									"22 czerwiec 2222",
//									"22 czerwca 2222",
//									"22 lipiec 2222",
//									"22 lipca 2222",
//									"22 sierpień 2222",
//									"22 sierpnia 2222",									
//									"22 wrzesień 2222", 
//									"22 wrzesnia 2222", 
//									"22 września 2222", 
//									"22 pażdziernik 2222", 
//									"22 pazdziernika 2222", 
//									"22 listopad 2222", 
//									"22 Listopada 2222", 
//									"22 grudzień 2222", 
//									"22 grudnia 2222"};
//		assertEquals(conv.convertDate(l[0]), "01");
//		assertEquals(conv.convertDate(l[1]), "01");
//		assertEquals(conv.convertDate(l[2]), "01");
//		assertEquals(conv.convertDate(l[3]), "01");
//		
//		assertEquals(conv.convertDate(l[4]), "02");
//		assertEquals(conv.convertDate(l[5]), "02");
//		
//		assertEquals(conv.convertDate(l[6]), "03");
//		assertEquals(conv.convertDate(l[7]), "03");
//		
//		assertEquals(conv.convertDate(l[8]), "04");
//		assertEquals(conv.convertDate(l[9]), "04");
//		assertEquals(conv.convertDate(l[10]), "04");
//		
//		assertEquals(conv.convertDate(l[11]), "05");
//		assertEquals(conv.convertDate(l[12]), "05");
//		
//		assertEquals(conv.convertDate(l[13]), "06");
//		assertEquals(conv.convertDate(l[14]), "06");
//		
//		assertEquals(conv.convertDate(l[15]), "07");
//		assertEquals(conv.convertDate(l[16]), "07");
//		
//		assertEquals(conv.convertDate(l[17]), "08");
//		assertEquals(conv.convertDate(l[18]), "08");
//		
//		assertEquals(conv.convertDate(l[19]), "09");
//		assertEquals(conv.convertDate(l[20]), "09");
//		assertEquals(conv.convertDate(l[21]), "09");
//		
//		assertEquals(conv.convertDate(l[22]), "10");
//		assertEquals(conv.convertDate(l[23]), "10");
//		
//		assertEquals(conv.convertDate(l[24]), "11");
//		assertEquals(conv.convertDate(l[25]), "11");
//		
//		assertEquals(conv.convertDate(l[26]), "12");
//		assertEquals(conv.convertDate(l[27]), "12");
//	}

}
