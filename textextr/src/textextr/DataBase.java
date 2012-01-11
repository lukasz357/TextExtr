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
            													  "opis					TEXT(5000)," +
            													  "stanowisko			VARCHAR(255)," +
            													  "id_slowa_klucz		INTEGER NULL," +
            													  "linkDoStrony			VARCHAR(255))");
            
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS   filesInfo(file_id 			INTEGER PRIMARY KEY AUTOINCREMENT," +
            													   "url					VARCHAR (4000) UNIQUE NOT NULL," +
            													   "folder				VARCHAR (20)   NOT NULL,"+
            													   "filename			VARCHAR (4000) NOT NULL)");
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
    public ArrayList<String> getUrls(){
        ArrayList<String> urls = new ArrayList<String>();
        String url;
        
        ResultSet rs;
		try {
			rs = stat.executeQuery("SELECT * FROM filesInfo;");
	        while (rs.next()) {
	            url = rs.getString(2);
	            if(url != null)
	            urls.add(url);
	        }
		} catch (SQLException e) {
			log.error("Błąd podczas pobierania adresu url z bazy.");
			e.printStackTrace();
		}
        return urls;
    }
    public ArrayList<String> getFilenames(){
        ArrayList<String> filenames = new ArrayList<String>();
        String filename;
        
        ResultSet rs;
		try {
			rs = stat.executeQuery("SELECT * FROM filesInfo;");
	        while (rs.next()) {
	            filename = rs.getString(4);
	            if(filename != null)
	            filenames.add(filename);
	        }
		} catch (SQLException e) {
			log.error("Błąd podczas pobierania nazw plików z bazy.");
			e.printStackTrace();
		}
        return filenames;
    }
//    public void addNewFilesInfo(ArrayList<String> urls) {
//        if (urls == null) {
//        	log.error("Nieprawidłowa lista adresów url");
//        }
//        int i = 0;
//        PreparedStatement prep;
//		try {
//			prep = conn.prepareStatement("insert into filesInfo values (?, ?, ?);");
//
//	        while (i < urls.size()) {
//	        	String s = urls.get(i);
//	        	int slashIndex = s.lastIndexOf('/');
//	        	String filename = null;
//	        	if(slashIndex != -1) {
//	        		filename = s.substring(slashIndex +1);
//	        	}
//	            prep.setString(2, s);
//	            prep.setString(3, filename);
//	            prep.addBatch();
//	            i++;
//	        }
//            conn.setAutoCommit(false);
//            prep.executeBatch();
//            conn.setAutoCommit(true);
//        } catch (SQLException ex) {
//            log.error("Nie udało się wczytać nowych adresów url");
//        	ex.printStackTrace();
//        }
//    }
    public void addNewFileInfo(String url, String folder, String filename) {
        try {    
            PreparedStatement prep = conn.prepareStatement("insert into filesInfo values (?, ?, ?, ?);");
            prep.setString(2, url);
            prep.setString(3, folder);
            prep.setString(4, filename);
            prep.addBatch();
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
        	log.error("Podany plik znajduje się już w bazie");
        	ex.printStackTrace();
        }
    }
}
