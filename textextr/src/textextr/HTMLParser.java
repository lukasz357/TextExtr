package textextr;
import java.io.IOException;

import org.apache.log4j.*;
import org.jsoup.select.Elements;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class HTMLParser{
//	private static Logger log = Logger.getLogger( HTMLParser.class );
	
	private int max_page;
	private int recordsNumber;
	private String url;
	private String parameters;
	private Data tmpData;
	private ArrayList<Data> allData;
	private HTTPRequestPoster HTTPReqPst;
	private Elements links;
	private ArrayList<String> urls;
	
	public HTMLParser(String url, String parameters) throws IOException {
		this.parameters = parameters;
		this.url = url;
		HTTPReqPst = new HTTPRequestPoster();
		
		String s = HTTPRequestPoster.sendGetRequest(url, parameters+"&page="+"1");
		tmpData = new Gson().fromJson(s, Data.class);
		
		max_page = tmpData.getMax_page();
		recordsNumber = tmpData.getRecords();
		
		urls = new ArrayList<String>(recordsNumber);
		allData = new ArrayList<Data>(max_page);
	}
	
	public void getAllDatas() {
		String s, parameters;
		
		for(int i = 1; i <= max_page; i++) {
			parameters = this.getParameters()+"&page="+i;
			s = HTTPRequestPoster.sendGetRequest(this.getUrl(), parameters);
			allData.add(new Gson().fromJson(s, Data.class));
		}
	}
	
	public void extractPDFUrls() {
		for(Data data : this.getAllData()) {
			for(Dane d :data.getData()) {
				urls.add(d.getFiled_6());
			}
		}
	}

	public ArrayList<Data> getAllData() {
		return allData;
	}
	public void setAllData(ArrayList<Data> allData) {
		this.allData = allData;
	}
	
	public HTTPRequestPoster getHTTPReqPst() {
		return HTTPReqPst;
	}
	public void setHTTPReqPst(HTTPRequestPoster p) {
		this.HTTPReqPst = p;
	}


	public Elements getLinks() {
		return links;
	}
	public void setLinks(Elements links) {
		this.links = links;
	}


	public Data getTmpData() {
		return tmpData;
	}
	public void setTmpData(Data data) {
		this.tmpData = data;
	}


	public ArrayList<String> getUrls() {
		return urls;
	}
	public void setUrls(ArrayList<String> urls) {
		this.urls = urls;
	}


	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

//=========================
	class Data {
		private List<String> headers;
		private int records;
		private int cur_page;
		private int max_page;
		private List<Dane> data;
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
	}
//=========================
	class Dane {
		private Long id;
		private String filed_0;
		private String filed_1;
		private String filed_2;
		private String filed_3;
		private String filed_4;
		private String filed_5;
		private String filed_6;
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
	}
}
