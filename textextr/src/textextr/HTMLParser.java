package textextr;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser{
	public HTMLParser(ArrayList<String> urls) throws IOException {
		Elements links = new Elements();
		for (String u : urls) {
			Document doc = Jsoup.connect(u).get();
			links.addAll(doc.select("a[href]"));
		}
		
	}
	
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
	public void getLinksByDescription( String description, String word) {
		try {
			for(Element e: this.links) {
				String s = e.text();
				String l = e.attr("abs:href");
				if(l.contains(word)) {
					list.add(l);
				}
				else if(s.contains(description)) {
					list.add(l);
				}
			}
		}catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<URL> getPdfList() throws IOException {
		ArrayList<URL> pdfList = new ArrayList<URL>();
		for(String url : list) {
			URLConnection con = new URL( url ).openConnection();
			con.connect();
			InputStream is = con.getInputStream();
			pdfList.add(con.getURL());
			is.close();
			
		}
		return pdfList;
	}
	public void printLinks() {
		print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
	}
	
	public void printMatchingLinks() {
		for( String s : list) {
			System.out.println(s);
		}
	}
	
	private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
	
	private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
	}
	private final ArrayList<String> list = new ArrayList<String>();
	private final ArrayList<String> urls = new ArrayList<String>(5);
	Document doc;
	Elements links;
	String url;
	String keyword;
	String description;
	String word;

	private Elements tmpList = new Elements();


}
