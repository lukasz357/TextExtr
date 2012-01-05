package textextr;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class DataBase {
	private static Log log = LogFactory.getLog(DataBase.class);
    private Connection conn;	
    private Statement stat;
    public DataBase() {
    	PropertyConfigurator.configure(TextExtr.BASE_PATH+"log4j_mine.properties");
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:TextExtrDB.db");
            stat = conn.createStatement();
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS adverts(ad_id 				INTEGER PRIMARY KEY," +
            													  "dataOgloszenia 		DATETIME," +
            													  "terminSklOfert		DATETIME," +
            													  "dyscyplinaNaukowa	TEXT(1000)," +
            													  "instytucja			VARCHAR(255)," +
            													  "miasto				VARCHAR(255)," +
            													  "opis					TEXT(1000)," +
            													  "stanowisko			VARCHAR(255)," +
            													  "id_slowa_klucz		INTEGER NULL," +
            													  "linkDoStrony			VARCHAR(255))");
            
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS   urls(link_id 				INTEGER PRIMARY KEY," +
            													   "url					VARCHAR (4000)");
        } catch (SQLException ex) {
            log.error("Blad w konstruktorze DataBase()");
        	ex.printStackTrace();
        }
        catch(NullPointerException e) {
        	log.error("Null pointer w konstruktorze bazy danych");
        	e.printStackTrace();
        }
        log.info("Pomyślnie zainicjowano bazę danych");
    }
    public ArrayList<String>getUrls(){
        ArrayList<String> urls = new ArrayList<String>();
        String url;
        
        ResultSet rs;
		try {
			rs = stat.executeQuery("SELECT * FROM urls;");
	        while (rs.next()) {
	            url = rs.getString(1);
	            if(url != null)
	            urls.add(url);
	        }
		} catch (SQLException e) {
			log.error("Błąd podczas pobierania adresu url z bazy.");
		}
        return urls;
    }
    public void addNewUrls(ArrayList<String> urls) {
        if (urls == null) {
        	log.error("Nieprawidłowa lista adresów url");
        }
        int i = 0;
        PreparedStatement prep;
		try {
			prep = conn.prepareStatement("insert into urls values (?);");

	        while (i < urls.size() - 1) {
	            prep.setString(1, urls.get(i));
	            prep.addBatch();
	            i++;
	        }

            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            log.error("Nie udało się wczytać nowych adresów url");
        }
    }
}
