package textextr;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataBaseTest {
	private static Log log = LogFactory.getLog(DataBaseTest.class);
	DataBase db;
	ArrayList<String> list;
	
	@Before
	public void setUp() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		db = new DataBase();
		list= new ArrayList<String>();
	}
	
	@After
	public void tearDown() {
		db = null;
		list = null;
	}
	
	@Test
	public void test() throws ClassNotFoundException {
		try{
			list.add("sss");
			list.add("sdas");
			db.addNewUrls(list);
			
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldReturnListOfUrls() {
		list = db.getUrls();
		if(list == null) {
			log.error("Nie udało się pobrać adresów url z bazy");
		}
		else {
			for(String s : list)
				System.out.println(s);
		}
	}
}
