package textextr;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class HTMLViewTest {
	private ArrayList<Advertisement> adverts;
	private DataBase db;
	
	@Before
	public void setUp() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		this.db = new DataBase();
		this.adverts = new ArrayList<Advertisement>(db.getAdvertisements("adiunkt").values());
	}
	@Test
	public void test() {

		
		HTMLView view = new HTMLView(adverts);
		
		view.generatePage();
	}

}
