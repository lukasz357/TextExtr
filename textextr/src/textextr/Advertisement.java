package textextr;

import java.util.ArrayList;
import java.util.Date;


public class Advertisement {
//	private static Logger log;
	private int advertID;
	private boolean parsingProblems;
	private int fileInfoID;
	private Date dataOgloszenia;
	private Date terminSklOfert;
	private String dyscyplinaNaukowa;
	private String instytucja;
	private String miasto;
	private String opis;
	private String stanowisko;
	private ArrayList<String> slowaklucz;
	private String linkDoStrony;
	private String url;
	
	public Advertisement(Date data, String dyscNauk, String inst, String url, String miasto, String opis, String stan, Date termin, ArrayList<String>  sk) {
		setDataOgloszenia(data);
		setDyscyplinaNaukowa(dyscNauk);
		setInstytucja(inst);
		setLinkDoStrony(url);
//		try {
//			setLinkDoStrony(new URL(url));
//		} catch (MalformedURLException e) {
//			log.error("Błąd z url " + url +" podczas tworzenia ogłoszenia");
//		}
		setMiasto(miasto);
		setOpis(opis);
		setStanowisko(stan);
		setTerminSklOfert(termin);
		setSlowaklucz(sk);
	}
	
	public Advertisement(int advertID, Date data, String dyscNauk, String inst, String url, String miasto, String opis, String stan, Date termin, ArrayList<String>  sk, boolean parsingProblems, int file_identity) {
		setAdvertID(advertID);
		setDataOgloszenia(data);
		setDyscyplinaNaukowa(dyscNauk);
		setInstytucja(inst);
		setLinkDoStrony(url);
//		try {
//			setLinkDoStrony(new URL(url));
//		} catch (MalformedURLException e) {
//			log.error("Błąd z url " + url +" podczas tworzenia ogłoszenia");
//		}
		setMiasto(miasto);
		setOpis(opis);
		setStanowisko(stan);
		setTerminSklOfert(termin);
		setSlowaklucz(sk);
		setParsingProblems(parsingProblems);
		setFileInfoID(file_identity);
	}
	public Advertisement(int advertID, Date data, String dyscNauk, String inst, String url, String miasto, String opis, String stan, Date termin, ArrayList<String>  sk, boolean parsingProblems, int file_identity, String fileUrl) {
		setAdvertID(advertID);
		setDataOgloszenia(data);
		setDyscyplinaNaukowa(dyscNauk);
		setInstytucja(inst);
		setLinkDoStrony(url);
//		try {
//			setLinkDoStrony(new URL(url));
//		} catch (MalformedURLException e) {
//			log.error("Błąd z url " + url +" podczas tworzenia ogłoszenia");
//		}
		setMiasto(miasto);
		setOpis(opis);
		setStanowisko(stan);
		setTerminSklOfert(termin);
		setSlowaklucz(sk);
		setParsingProblems(parsingProblems);
		setFileInfoID(file_identity);
		setUrl(fileUrl);
	}
	
	public Advertisement(){
		
	}
	public Date getDataOgloszenia() {
		return dataOgloszenia;
	}

	public void setDataOgloszenia(Date dataOgloszenia) {
		this.dataOgloszenia = dataOgloszenia;
	}

	public String getDyscyplinaNaukowa() {
		return dyscyplinaNaukowa;
	}

	public void setDyscyplinaNaukowa(String dyscyplinaNaukowa) {
		this.dyscyplinaNaukowa = dyscyplinaNaukowa;
	}

	public String getInstytucja() {
		return instytucja;
	}

	public void setInstytucja(String instytucja) {
		this.instytucja = instytucja;
	}

	public String getLinkDoStrony() {
		return linkDoStrony;
	}

	public void setLinkDoStrony(String linkDoStrony) {
		this.linkDoStrony = linkDoStrony;
	}

	public String getMiasto() {
		return miasto;
	}

	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getStanowisko() {
		return stanowisko;
	}

	public void setStanowisko(String stanowisko) {
		this.stanowisko = stanowisko;
	}

	public Date getTerminSklOfert() {
		return terminSklOfert;
	}

	public void setTerminSklOfert(Date terminSklOfert) {
		this.terminSklOfert = terminSklOfert;
	}

	public ArrayList<String>  getSlowaklucz() {
		return slowaklucz;
	}

	public void setSlowaklucz(ArrayList<String> slowaklucz) {
		this.slowaklucz = slowaklucz;
	}
	public int getFileInfoID() {
		return fileInfoID;
	}
	public void setFileInfoID(int fileInfoID) {
		this.fileInfoID = fileInfoID;
	}
	public boolean isParsingProblems() {
		return parsingProblems;
	}
	public void setParsingProblems(boolean parsingProblems) {
		this.parsingProblems = parsingProblems;
	}
	public int getAdvertID() {
		return advertID;
	}

	public void setAdvertID(int advertID) {
		this.advertID = advertID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Advertisement [dataOgloszenia=" + dataOgloszenia
				+ ", terminSklOfert=" + terminSklOfert + ", dyscyplinaNaukowa="
				+ dyscyplinaNaukowa + ", instytucja=" + instytucja
				+ ", miasto=" + miasto + ", opis=" + opis + ", stanowisko="
				+ stanowisko + ", slowaklucz=" + slowaklucz + ", linkDoStrony="
				+ linkDoStrony + "]";
	}
}
