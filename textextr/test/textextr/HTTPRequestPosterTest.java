package textextr;

import org.junit.Test;

public class HTTPRequestPosterTest {

	private String result;
	private HTTPRequestPoster p;
	@Test
	public void test() {
		setP(new HTTPRequestPoster());
		result = HTTPRequestPoster.sendGetRequest("http://www.nauka.gov.pl/app/wyszukiwarka,24.html", "filed_0=&filed_1=&filed_2=&filed_3=&filed_4=");
		System.out.println(result);
	}

	public HTTPRequestPoster getP() {
		return p;
	}

	public void setP(HTTPRequestPoster p) {
		this.p = p;
	}

}
