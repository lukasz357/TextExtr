package textextr;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.google.gson.Gson;
import java.util.List;


public class HTMLParser{
	public HTMLParser() throws IOException {
		setLinks2(new Elements());
		setP(new HTTPRequestPoster());
		String s = HTTPRequestPoster.sendGetRequest("http://www.nauka.gov.pl/app/wyszukiwarka,24.html", "filed_0=&filed_1=&filed_2=&filed_3=&filed_4=");
		setData(new Gson().fromJson(s, Data.class));
	}	
		
public void printData() {
	List<Dane> da = data.getData();
	for (Dane d : da) {
		System.out.println(d.id);
		System.out.println(d.filed_0);
		System.out.println(d.filed_1);
		System.out.println(d.filed_2);
		System.out.println(d.filed_3);
		System.out.println(d.filed_4);
		System.out.println(d.filed_5);
		System.out.println(d.filed_6);
	}
}
//		Elements links = new Elements();
//		for (String u : urls) {
//			Document doc = Jsoup.connect(u).get();
//			links.addAll(doc.select("a[href]"));
//		}
		
//	}
	
//	public HTMLParser(String url, String keyword, String description, String word) throws IOException {
//		this.url = url;
//		this.keyword = keyword;
//		this.description = description;
//		this.word = word;
//	}

//	public void getLinksThatContains(String keyword) throws IOException {
//		for(Element link : links) {
//			String s = link.attr("abs:href");
//			if(s.contains("/"+keyword+"/")) {			
//				Document doc = Jsoup.connect(s).get();
//				Elements els = doc.select("a[href]");
//				tmpList.addAll(els);
//			}
//		}	
//	}
//	public void getLinksByDescription( String description, String word) {
//		try {
//			for(Element e: this.links) {
//				String s = e.text();
//				String l = e.attr("abs:href");
//				if(l.contains(word)) {
//					list.add(l);
//				}
//				else if(s.contains(description)) {
//					list.add(l);
//				}
//			}
//		}catch(NullPointerException e) {
//			System.out.println(e.getMessage());
//		}
//	}
//
//	public ArrayList<URL> getPdfList() throws IOException {
//		ArrayList<URL> pdfList = new ArrayList<URL>();
//		for(String url : list) {
//			URLConnection con = new URL( url ).openConnection();
//			con.connect();
//			InputStream is = con.getInputStream();
//			pdfList.add(con.getURL());
//			is.close();
//			
//		}
//		return pdfList;
//	}
//	public void printLinks() {
//		print("\nLinks: (%d)", links.size());
//        for (Element link : links) {
//            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
//        }
//	}
//	
//	public void printMatchingLinks() {
//		for( String s : list) {
//			System.out.println(s);
//		}
//	}
//	
//	private static void print(String msg, Object... args) {
//        System.out.println(String.format(msg, args));
//    }
//	
//	private static String trim(String s, int width) {
//        if (s.length() > width)
//            return s.substring(0, width-1) + ".";
//        else
//            return s;
//	}
	
	public HTTPRequestPoster getP() {
		return p;
	}
	public void setP(HTTPRequestPoster p) {
		this.p = p;
	}


	public Elements getLinks2() {
		return links2;
	}


	public void setLinks2(Elements links2) {
		this.links2 = links2;
	}


	public Data getData() {
		return data;
	}


	public void setData(Data data) {
		this.data = data;
	}


	class Data {
		private List<String> headers;
		public List<String> getHeaders() {
			return headers;
		}
		public void setHeaders(List<String> headers) {
			this.headers = headers;
		}
		public int getRecords() {
			return records;
		}
		public void setRecords(int records) {
			this.records = records;
		}
		public int getCur_page() {
			return cur_page;
		}
		public void setCur_page(int cur_page) {
			this.cur_page = cur_page;
		}
		public int getMax_page() {
			return max_page;
		}
		public void setMax_page(int max_page) {
			this.max_page = max_page;
		}
		public List<Dane> getData() {
			return data;
		}
		public void setData(List<Dane> data) {
			this.data = data;
		}
		private int records;
		private int cur_page;
		private int max_page;
		private List<Dane> data;
		
		
	}
	class Dane {
		private Long id;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getFiled_0() {
			return filed_0;
		}
		public void setFiled_0(String filed_0) {
			this.filed_0 = filed_0;
		}
		public String getFiled_1() {
			return filed_1;
		}
		public void setFiled_1(String filed_1) {
			this.filed_1 = filed_1;
		}
		public String getFiled_2() {
			return filed_2;
		}
		public void setFiled_2(String filed_2) {
			this.filed_2 = filed_2;
		}
		public String getFiled_3() {
			return filed_3;
		}
		public void setFiled_3(String filed_3) {
			this.filed_3 = filed_3;
		}
		public String getFiled_4() {
			return filed_4;
		}
		public void setFiled_4(String filed_4) {
			this.filed_4 = filed_4;
		}
		public String getFiled_5() {
			return filed_5;
		}
		public void setFiled_5(String filed_5) {
			this.filed_5 = filed_5;
		}
		public String getFiled_6() {
			return filed_6;
		}
		public void setFiled_6(String filed_6) {
			this.filed_6 = filed_6;
		}
		private String filed_0;
		private String filed_1;
		private String filed_2;
		private String filed_3;
		private String filed_4;
		private String filed_5;
		private String filed_6;
	}
	
	
	
	
//	private final ArrayList<String> list = new ArrayList<String>();
	Document doc;
	Elements links;
	String url;
	String keyword;
	String description;
	String word;
	private Data data;
	private HTTPRequestPoster p;
	private Elements links2;


}
