package textextr;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class Advertisement {
	private static Logger log;
	
	private Date dataOgloszenia;
	private Date terminSklOfert;
	private String dyscyplinaNaukowa;
	private String instytucja;
	private String miasto;
	private String opis;
	private String stanowisko;
	private List<String> slowaklucz;
	private URL linkDoStrony;
	
	public Advertisement(Date data, String dyscNauk, String inst, String url, String miasto, String opis, String stan, Date termin, List<String> sk ) {
		setDataOgloszenia(data);
		setDyscyplinaNaukowa(dyscNauk);
		setInstytucja(inst);
		try {
			setLinkDoStrony(new URL(url));
		} catch (MalformedURLException e) {
			log.error("Błąd z url " + url +" podczas tworzenia ogłoszenia");
		}
		setMiasto(miasto);
		setOpis(opis);
		setStanowisko(stan);
		setTerminSklOfert(termin);
		setSlowaklucz(sk);
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

	public URL getLinkDoStrony() {
		return linkDoStrony;
	}

	public void setLinkDoStrony(URL linkDoStrony) {
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

	public List<String> getSlowaklucz() {
		return slowaklucz;
	}

	public void setSlowaklucz(List<String> slowaklucz) {
		this.slowaklucz = slowaklucz;
	}
}
