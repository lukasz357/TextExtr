package textextr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HTMLView {
	private static Log log = LogFactory.getLog(HTMLView.class);
	private ArrayList<Advertisement> adverts;
	private String title;
	
	public HTMLView(ArrayList<Advertisement> adverts) {
		this.adverts = adverts;
		this.title = "ZNALEZIONE OGŁOSZENIA";
	}
	
	public void generatePage() {
	    FileWriter fstream = null;
	    try {
	    	String fileName = TextExtr.OUTPUT_HTML_PATH+"/" + getDateTime();
	        fstream = new FileWriter(fileName);
	        BufferedWriter out = new BufferedWriter(fstream);
	        out.write("<html>\n"
	                + "<head>\n"
	                + "<title>" + this.title + "</title>\n"
	                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
	                +" <style type=\"text/css\">"
	                +" .mylen{" 
	                +"			width: 25%;"
	                +"   		vertical-align:text-top;"
	                +"			font-weight: bold;"			
	                +"}"
	                +" .bold{" 
	                +"			font-weight: bold;"		
	                +"}"
	                +" .text-top{" 
	                +"			vertical-align:text-top;"	
	                +"}"
	                +" .center{" 
	                +"			text-align:center;"	
	                +"}"
	                +"</style>"
	                + "</head>\n"
	                + "<body style=\"border:0.2em solid black; width:80%; margin-left: auto; "
	                + "margin-right: auto; margin-top:3em;\">\n");
	        out.write("<center><h2 style=\"margin-top: 0.5em;\">" + this.title + "</h2></center>\n");
//	        out.write("<p class=\"bold\" style=\"margin:2em; font-size: larger\">Znaleziono: "+adverts.size()+" ogłoszeń.</p>");
	        out.write("<div style=\"margin:2em;\">\n");
	        
	        out.write("<table>\n");
	        Advertisement adv;
	        for (int i = 0; i < adverts.size(); i++) {
	        	adv = adverts.get(i);
	        	if(adv.isParsingProblems()) {
		        	out.write("<tr>");
		        	out.write("<td colspan=\"3\" style=\"color: red; text-align: center; font-size: larger\">");
		        	out.write("Poniższe ogłoszenie nie było zgodne z szablonem. Oryginalny plik możesz znależć: <a href=\"" + adv.getUrl() + "\">TUTAJ</a>");
		        	out.write("</td>");
		        	out.write("</tr>\n");
	        	}
	        	out.write("<tr>");
	        	out.write("<td class = \"bold text-top\">"+(i+1)+".</td>");
	        	out.write("<td class = \"mylen\">INSTYTUCJA:</td>");
	        	out.write("<td>" + adv.getInstytucja() + "</td>");
	        	out.write("</tr>\n");
	        	
	        	out.write("<tr>");
	        	out.write("<td>&nbsp;</td>");
	        	out.write("<td class = \"mylen\">MIASTO:</td>");
	        	out.write("<td>" + adv.getMiasto() + "</td>");
	        	out.write("</tr>\n");
	        	
	        	out.write("<tr>");
	        	out.write("<td>&nbsp;</td>");
	        	out.write("<td class = \"mylen\">STANOWISKO:</td>");
	        	out.write("<td>" + adv.getStanowisko() + "</td>");
	        	out.write("</tr>\n");
	        	
	        	out.write("<tr>");
	        	out.write("<td>&nbsp;</td>");
	        	out.write("<td class = \"mylen\">DYSCYPLINA NAUKOWA:</td>");
	        	out.write("<td>" + adv.getDyscyplinaNaukowa() + "</td>");
	        	out.write("</tr>\n");
	        	
	        	out.write("<tr>");
	        	out.write("<td>&nbsp;</td>");
	        	out.write("<td class = \"mylen\">DATA OGŁOSZENIA:</td>");
	        	out.write("<td>" + adv.getDataOgloszenia() + "</td>");
	        	out.write("</tr>\n");
	        	
	        	out.write("<tr>");
	        	out.write("<td>&nbsp;</td>");
	        	out.write("<td class = \"mylen\">TERMIN SKŁADANIA OFERT:</td>");
	        	out.write("<td>" + adv.getTerminSklOfert() + "</td>");
	        	out.write("</tr>\n");
	        	
	        	out.write("<tr>");
	        	out.write("<td>&nbsp;</td>");
	        	out.write("<td class = \"mylen\">LINK DO STRONY:</td>");
	        	out.write("<td><a href=\"" + adv.getLinkDoStrony() + "\">"+adverts.get(i).getLinkDoStrony()+"</a></td>");
	        	out.write("</tr>\n");
	        	
	        	out.write("<tr>");
	        	out.write("<td>&nbsp;</td>");
	        	out.write("<td class = \"mylen\">SŁOWA KLUCZOWE:</td>");
	        	out.write("<td>");
	        	out.write("<ul>");
	        	for(String s : adv.getSlowaklucz())
	        		out.write("<li>" + s + "</li>");
	        	out.write("</ul>");
	        	out.write("</td>");
	        	out.write("</tr>\n");
	        	
	        	out.write("<tr>");
	        	out.write("<td>&nbsp;</td>");
	        	out.write("<td class = \"mylen\">OPIS:</td>");
	        	out.write("<td>" + adv.getOpis() + "</td>");
	        	out.write("</tr>\n");
	        	out.write("<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
	        	
	        }
	        out.write("</table>\n");
	        
	        out.write("</div>\n");
	        out.write("</body>\n");
	        out.write("</html>\n");
	        out.close();
	        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
	        if( desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) {
	        	desktop.open(new File(fileName));
	        }
	    } catch (IOException ex) {
	        log.error("Problem w konwersji do HTML");
	    }
	}
	
	private String getDateTime()  
	{  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");  
	    df.setTimeZone(TimeZone.getTimeZone("GMT+1"));  
	    return df.format(new Date());  
	} 
}
