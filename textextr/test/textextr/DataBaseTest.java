package textextr;

import org.junit.Test;

public class DataBaseTest {
	DataBase db;
	@Test
	public void test() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		try{
			db = new DataBase();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
}
