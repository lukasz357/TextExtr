package textextr;

import java.util.ArrayList;

import org.junit.Test;

public class DataBaseTest {
	DataBase db;
	@Test
	public void test() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		try{
			db = new DataBase();
			ArrayList<String> list= new ArrayList<String>();
			list.add("sss");
			list.add("sdas");
			db.addNewUrls(list);
			
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
}
