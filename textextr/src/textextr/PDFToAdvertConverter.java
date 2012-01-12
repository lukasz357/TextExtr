package textextr;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dateFormat.W3CDateFormat;
public class PDFToAdvertConverter {
	private static Log log = LogFactory.getLog(PDFToAdvertConverter.class);
	private Advertisement advert;
	private String status;
	private String text;
	private String folder;
	private String fileName;
	public PDFToAdvertConverter(String folder, String fileName){
		this.advert = new Advertisement();
		this.text = new PDFToTextConverter().pdftoText(TextExtr.PDF_BASE_PATH+"/"+fileName);
		this.folder = folder;
		this.fileName = fileName;
		this.status = "";
	}
		
	public Advertisement parseText() {
		advert.setInstytucja(getInstytucja());
		advert.setMiasto(getMiasto());
		advert.setStanowisko(getStanowisko());
		advert.setDataOgloszenia(getDataOgloszenia());
		advert.setTerminSklOfert(getTerminSkladaniaOfert());
		advert.setDyscyplinaNaukowa(getDyscyplinaNaukowa());
		advert.setLinkDoStrony(getLinkDoStrony());
		advert.setSlowaklucz(getSlowaKluczowe());
		advert.setOpis(getOpis());
		return advert;
	}
	
	public String getInstytucja() {
		String instytucja = "";
		String replaced = "";
		int inst = text.indexOf("INSTYTUCJA");
		if( inst == -1 ) {
			inst = text.indexOf("Instytucja");
		}
		if( inst == -1 ) {
			inst = text.indexOf("instytucja");
		}
		int mst = text.indexOf("MIASTO");
		if(mst == -1) {
			mst = text.indexOf("Miasto");
		}
		if(mst == -1) {
			mst = text.indexOf("miasto");
		}
		if(inst != -1 && mst != -1) {
			instytucja = text.substring(inst + 11, mst - 1);
			replaced = instytucja.replace("…", "").replace(".", "").replaceAll("(?s) ", " ").replace("–", "");
			instytucja = replaced;
		}
		else {
			status += status.length() < 1 ? "INSTYTUCJA:" : ", INSTYTUCJA:";
//			log.error("!!! "+fileName+ " --- INSTYTUCJA - nie zastosowano się do szablonu");
		}
		return instytucja.trim();
	}
	
	public String getMiasto() {
		String miasto  = "";
		String replaced = "";
		int mst = text.indexOf("MIASTO");
		if(mst == -1) {
			mst = text.indexOf("Miasto");
		}
		if(mst == -1) {
			mst = text.indexOf("miasto");
		}
		int stn = text.indexOf("STANOWISKO");
		if(stn == -1) {
			stn = text.indexOf("stanowisko");
		}
		if(stn == -1) {
			stn = text.indexOf("Stanowisko");
		}
		if(mst != -1 && stn != -1) {
			miasto = text.substring(mst + 7, stn - 1);
			replaced = miasto.replace("…", "").replace(".", "").replace("–", "");
			miasto = replaced.trim();
			if(miasto.matches("(?i).*warsz.*")){
				miasto = "Warszawa";
			}else if (miasto.matches("(?i)wroc.*")){
				miasto = "Wrocław";
			}else if (miasto.matches("(?i)opol.*")){
				miasto = "Opole";
			}else if (miasto.matches("(?i)rzesz.*")){
				miasto = "Rzeszów";
			}else if (miasto.matches("(?i)[Łl].d.*")){
				miasto = "Łódź";
			}else if (miasto.matches("(?i)bydgosz.*")){
				miasto = "Bydgoszcz";
			}else if (miasto.matches("(?i)lubli.*")){
				miasto = "Lublin";
			}else if (miasto.matches("(?i)Zielona.*")){
				miasto = "Zielona Góra";
			}else if (miasto.matches("(?i)toru.*")){
				miasto = "Toruń";
			}else if (miasto.matches("(?i)Gda.*")){
				miasto = "Gdańsk";
			}else if (miasto.matches("(?i)Bielsko.*")){
				miasto = "Bielsko-biała";
			}else if (miasto.matches("(?i)Bia[lł]a P.*")){
				miasto = "Biała Podlaska";
			}else if (miasto.matches("(?i)gdyni.*")){
				miasto = "Gdynia";
			}else if (miasto.matches("(?i)krak.*")){
				miasto = "Kraków";
			}else if (miasto.matches("(?i)szcze.*")){
				miasto = "Szczecin";
			}else if (miasto.matches("(?i)Kiel.*")){
				miasto = "Kielce";
			}else if (miasto.matches("(?i)Gliwi.*")){
				miasto = "Gliwice";
			}else if (miasto.matches("(?i)Olszt.*")){
				miasto = "Olsztyn";
			}else if (miasto.matches("(?i)Pozn.*")){
				miasto = "Poznań";
			}else if (miasto.matches("(?i)Kosza.*")){
				miasto = "Koszalin";
			}else if (miasto.matches("(?i)Cz.sto.*")){
				miasto = "Częstochowa";
			}else if (miasto.matches("(?i)Bia.yst.*")){
				miasto = "Białystok";
			}else if (miasto.matches("(?i)Siedl.*")){
				miasto = "Siedlce";
			}else if (miasto.matches("(?i)Katow.*")){
				miasto = "Katowice";
			}else if (miasto.matches("(?i)Zabrz.*")){
				miasto = "Zabrze";
			}else if (miasto.matches("(?i)Legnic.*")){
				miasto = "Legnica";
			}else if (miasto.matches("(?i)Ruda [Śs].*")){
				miasto = "Ruda Śląska";
			}else if (miasto.length() == 0){
				miasto = null;
			}else{
				status += status.length() < 1 ? "MIASTO:" : ", MIASTO:";
//				log.info("Nie dopasowano miasta: " + miasto + " z listy");//lub inne
			}
		}
		else {
			
//			log.error("!!! "+fileName+ " --- MIASTO: - nie zastosowano się do szablonu");
		}
		return miasto;
	}
	
	public String getStanowisko() {
		String stanowisko = "";
		String replaced = "";
		int stn = text.indexOf("STANOWISKO");
		if(stn == -1) {
			stn = text.indexOf("stanowisko");
		}
		if(stn == -1) {
			stn = text.indexOf("Stanowisko");
		}
		int dsc = text.indexOf("DYSCYPLINA NAUKOWA");
		if(dsc == -1) {
			dsc = text.indexOf("dyscyplina naukowa");
		}
		if(dsc == -1) {
			dsc = text.indexOf("Dyscyplina naukowa");
		}
		if(dsc == -1)
			dsc = text.indexOf("DZIEDZINA SZTUKI");

		if(stn != -1 && dsc != -1) {
			stanowisko = text.substring(stn + 11, dsc - 1).trim();
			replaced = stanowisko.replace("…", "").replace(".", "").replace("–", "");
			stanowisko = replaced.trim();
			if(stanowisko != null) {
				if(stanowisko.length() == 0) {
					stanowisko = "NIE PODANO STANOWISKA";
				}else if(stanowisko.matches("(?i)(?s).*asyst.*")){
					stanowisko = "Asystent";
				}else if (stanowisko.matches("(?i)(?s).*adiun.*")){
					stanowisko = "Adiunkt";
				}else if (stanowisko.matches("(?i)(?s).*instrukt.*")){
					stanowisko = "Instruktor";
				}else if (stanowisko.matches("(?i)(?s).*starszy w.*")){
					stanowisko = "Starszy wykładowca";
				}else if (stanowisko.matches("(?i)(?s).*wyk[lł].*")){
					stanowisko = "Wykładowca";
				}else if (stanowisko.matches("(?i)(?s).*profesor.*nad.*")){
					stanowisko = "Profesor nadzwyczajny";
				}else if (stanowisko.matches("(?i)(?s).*profesor.*zw.*")){
					stanowisko = "Profesor zwyczajny";
				}else if (stanowisko.matches("(?i)(?s).*prof.*")){
					stanowisko = "Profesor";
				}else if (stanowisko.matches("(?i)(?s).*kierown.*")){
					stanowisko = "Kierownik";
				}else if (stanowisko.matches("(?i)(?s).*lekto.*")){
					stanowisko = "Lektor";
				}else{
					log.info("Nie dopasowano stanowiska: " + stanowisko + " z listy");
				}
			}
		}
		else {
			status += status.length() < 1 ? "STANOWISKO:" : ", STANOWISKO:";
//			log.error("!!! "+fileName+ " --- STANOWISKO: - nie zastosowano się do szablonu");
		}
		return stanowisko;
	}
	
	public String getDyscyplinaNaukowa() {
		String dyscyplinaNaukowa = "";
		String replaced = "";
		
		int dsc = text.indexOf("DYSCYPLINA NAUKOWA");
		if(dsc == -1) {
			dsc = text.indexOf("dyscyplina naukowa");
		}
		if(dsc == -1) {
			dsc = text.indexOf("Dyscyplina naukowa");
		}
		int dt = text.indexOf("DATA OGŁOSZENIA");
		if(dt == -1) {
			dt = text.indexOf("data ogłoszenia");
		}
		if(dt == -1) {
			dt = text.indexOf("Data ogłoszenia");
		}
		if(dsc != -1 &&dt != -1) {
			dyscyplinaNaukowa = text.substring(dsc + 19, dt -1);
			replaced = dyscyplinaNaukowa.replace("…", "").replace(".", "").replace("–", "");
			dyscyplinaNaukowa = replaced.trim();
		}
		else {
			status += status.length() < 1 ? "DYSCYPLINA NAUKOWA:" : ", DYSCYPLINA NAUKOWA:";
		}
		return dyscyplinaNaukowa;
	}
	
	public String getLinkDoStrony() {
		String linkDoStrony = "";
		String replaced = "";
		int lnk = text.indexOf("LINK DO STRONY");
		if(lnk == -1) {
			lnk = text.indexOf("Link do strony");
		}
		if(lnk == -1) {
			lnk = text.indexOf("link do strony");
		}
		int slkl = text.indexOf("SŁOWA KLUCZOWE");
		if(slkl == -1) {
			slkl = text.indexOf("słowa kluczowe");
		}
		if(slkl == -1) {
			slkl = text.indexOf("Słowa kluczowe");
		}
		if(lnk != -1 && slkl != -1){
			linkDoStrony = text.substring(lnk + 15 , slkl - 1);
			replaced = linkDoStrony.replace("..", "").replace("–", "");
			linkDoStrony = replaced.trim();
		}
		else {
			status += status.length() < 1 ? "LINK DO STRONY:" : ", LINK DO STRONY:";
//			log.error("!!! "+fileName+ " --- LINK DO STRONY: - nie zastosowano się do szablonu");
		}
		return linkDoStrony;
	}
	
	public ArrayList<String> getSlowaKluczowe() {
		String [] slowaKluczowe = null;
		ArrayList<String> slKluczowe = new ArrayList<String>();
		String slowa = "";
		String replaced = "";
		String trimmed = "";
		int slkl = text.indexOf("SŁOWA KLUCZOWE");
		if(slkl == -1) {
			slkl = text.indexOf("Słowa kluczowe");
		}
		if(slkl == -1) {
			slkl = text.indexOf("słowa kluczowe");
		}
		int op = text.indexOf("OPIS");
		if(op == -1) {
			op = text.indexOf("Opis");
		}
		if(slkl != -1 && op != -1) {
			slowa = text.substring(slkl + 15, op -1).trim();
			replaced = slowa.replaceAll("[?!.,;:\\//]+", "#");
			slowaKluczowe = replaced.split("#");
			if(slowaKluczowe == null) 
				log.error("Brak słów kluczowych");
			else {
				slKluczowe = new ArrayList<String>(Arrays.asList(slowaKluczowe));
				for(int i = 0; i < slKluczowe.size(); i++) {
					String slowo = slKluczowe.get(i);
					if(slowo.length() <2)
						slKluczowe.remove(i);
					else {
						trimmed = slowo.trim();
						slKluczowe.set(i, trimmed);
					}
				}
			}
		}
		else {
			status += status.length() < 1 ? "SŁOWA KLUCZOWE:" : ", SŁOWA KLUCZOWE:";
//			log.error("!!! "+fileName+ " --- SŁOWA KLUCZOWE: - nie zastosowano się do szablonu");
		}
		return slKluczowe;
	}
	
	public String getOpis() {
		String opis = "";
		String replaced = "";
		int op = text.indexOf("OPIS");
		if(op == -1) {
			op = text.indexOf("Opis");
		}
		if(op != -1) {
			opis = text.substring(op + 36);
			replaced = opis.replace("", "-  ").replace("..", "");
			opis = replaced.trim();
		}
		else {
			status += status.length() < 1 ? "OPIS:" : ", OPIS:";
//			log.error("!!! "+fileName+ " --- OPIS: - nie zastosowano się do szablonu");
		}
		return opis;
	}
	
//	public Date getDataOgloszenia() throws ParseException {
//		int dt = text.indexOf("DATA OGŁOSZENIA:");
//		int tskl = text.indexOf("TERMIN SKŁADANIA OFERT:");
//		String s = "";
//		String data = text.substring(dt + 16, tskl - 1);
//		String trimmed = data.replace("r.", "").trim();
//		String converted = convertDate(trimmed);
//		DateFormat fmt = new W3CDateFormat(); 
//		try {
//			s = fmt.format(fmt.parse(converted));
//		} catch (ParseException e) {
//			log.error("Błąd podczas parsowania daty:" + s);
//		}
//		return fmt.parse(s);
//	}

	public Date getDataOgloszenia() {
		DateFormat fmt = new W3CDateFormat();
		Date d = null;
		String s = "";
		try {
			s = fmt.format(fmt.parse(folder));
			d =  fmt.parse(s);
		} catch (ParseException e) {
			log.error("problem z parsowaniem daty ogłoszenia: " + folder);
			e.printStackTrace();
		}
		return d;
	}
	public Date getTerminSkladaniaOfert() {
		DateFormat fmt = new W3CDateFormat();
		Date d = null;
		String data = null;
		String s = "";
		try {
			int idx = fileName.indexOf('_');
			data = fileName.substring(idx+1, idx+9);
			s = fmt.format(fmt.parse(data));
			d =  fmt.parse(s);
		} catch (ParseException e) {
			log.error("problem z parsowaniem daty terminu składania ofert: " + data);
			e.printStackTrace();
		}
		return d;
	}
	
	public String getStatus() {
		return status;
	}
//	public String convertDate(String date) {
//		String s = date.trim();
//		String [] splited = s.split(" ");
//		for(String ss : splited)
//			System.out.println(ss);
//		String miesiac = splited[1];
//		if(miesiac.matches("[S|s]tycz[eń|en|nia]")) {
//			miesiac = "01";
//		}
//		else if(miesiac.matches("[L|l]ut[y|ego]"))
//			splited[1] = "02";
//		else if(miesiac.matches("[M|m]mar[zec|ca]"))
//			splited[1] = "03";
//		else if(miesiac.matches("[K|k]wie[cien|cień|tnia]"))
//			splited[1] = "04";
//		else if(miesiac.matches("[M|m]a[j|ja]"))
//			splited[1] = "05";
//		else if(miesiac.matches("[C|c]zerw[iec|ca]"))
//			splited[1] = "06";
//		else if(miesiac.matches("[L|l]ip[iec|ca]"))
//			splited[1] = "07";
//		else if(miesiac.matches("[S|s]ierp[ień|ien|nia]"))
//			splited[1] = "08";
//		else if(miesiac.matches("[W|w]rze[sień|sien|śnia|snia]"))
//			splited[1] = "09";
//		else if(miesiac.matches("[Pp]a.dziernik[.a]"))
//			splited[1] = "10";
//		else if(miesiac.matches("[Ll]istopad[.a]"))
//			splited[1] = "11";
//		else if(miesiac.matches("[Gg]rudzie.|[Gg]rudnia"))
//			splited[1] = "12";
//		else
//			log.error("Bład przy zamianie daty tekstowej na liczbową");
//		
////		return splited[0] +"." + splited[1] + "." + splited[2];
//		return splited[1];
//	}
	
}
