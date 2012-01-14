package textextr;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
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
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS   filesInfo(file_id 			INTEGER PRIMARY KEY AUTOINCREMENT," +
            												     	  "url				VARCHAR (4000) UNIQUE," +
            												     	  "fileName			VARCHAR (2000)," +
            												     	  "folder			VARCHAR (2000)," +
            												     	  "downloaded		BOOLEAN," +
            												     	  "parsed			BOOLEAN)");
            
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS 	 adverts(ad_id 				INTEGER PRIMARY KEY AUTOINCREMENT," +
            													    "dataOgloszenia 		DATETIME," +
            													    "terminSklOfert		DATETIME," +
            													    "dyscyplinaNaukowa	TEXT(2000)," +
            													    "instytucja			VARCHAR(2000)," +
            													    "miasto				VARCHAR(500)," +
            													    "opis				TEXT(5000)," +
            													    "stanowisko			VARCHAR(2000)," +
            													    "linkDoStrony		VARCHAR(3000)," +
            													    "parsingProblems	BOOLEAN," +
            													    "file_identity		INTEGER," +
            													    "FOREIGN KEY(file_identity) REFERENCES filesInfo(file_id))");
            
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS   slowaKluczowe(id_slowa_klucz		INTEGER PRIMARY KEY AUTOINCREMENT," +
						  												  "slowo				VARCHAR (4000)," +
						  												  "ad_identity			INTEGER," +
						  												  "FOREIGN KEY(ad_identity) REFERENCES adverts(ad_id))");
            
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
    public ArrayList<FileInfo> getFileInfos(){
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        int id;
        String url, fileName, folder;
        boolean downloaded;
        boolean parsed;
        
        ResultSet rs;
		try {
			rs = stat.executeQuery("SELECT * FROM filesInfo;");
	        while (rs.next()) {
	        	id = rs.getInt(1);
	        	url = rs.getString(2);
	            fileName = rs.getString(3);
	            folder = rs.getString(4);
	            downloaded = rs.getBoolean(5);
	            parsed = rs.getBoolean(6);
	            fileInfos.add(new FileInfo(id, url, fileName, folder, downloaded, parsed));
	        }
		} catch (SQLException e) {
			log.error("Błąd podczas pobierania informacji o plikach z bazy.");
			e.printStackTrace();
		}
        return fileInfos;
    }
    
    public boolean addAdverts(ArrayList<Advertisement> adverts) {
    	boolean ok = false;
        PreparedStatement prep;
        try {
			prep = conn.prepareStatement("INSERT INTO adverts VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
	
	        for(Advertisement a : adverts) {
	            prep.setDate(2, new java.sql.Date(a.getDataOgloszenia().getTime()));
	            prep.setDate(3, new java.sql.Date(a.getTerminSklOfert().getTime()));
	            prep.setString(4, a.getDyscyplinaNaukowa());
	            prep.setString(5, a.getInstytucja());
	            prep.setString(6, a.getMiasto());
	            prep.setString(7, a.getOpis());
	            prep.setString(8, a.getStanowisko());
	            prep.setString(9, a.getLinkDoStrony());
	            prep.setBoolean(10, a.isParsingProblems());
	            prep.setInt(11, a.getFileInfoID());
	            prep.addBatch();
	        }
        
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
            ok = true;
        } catch (SQLException ex) {
            log.error("Problem podczas wczytywania do bazy");
            ex.printStackTrace();
        }
    	return ok;
    }
    
    public void addNewFileInfo(String url, String fileName, String folder) {
    	try {
            PreparedStatement prep = conn.prepareStatement("insert into filesInfo values (?, ?, ?, ?, ?, ?);");
            prep.setString(2, url);
            prep.setString(3, fileName);
            prep.setString(4, folder);
            prep.setBoolean(5, false);
            prep.setBoolean(6, false);
            prep.addBatch();
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
            
        } catch (SQLException ex) {
        	log.info("Nie wczytano informacji: " + url +" - url znajdował się już w bazie");
        	ex.printStackTrace();
        }
    }
    
    public boolean updateFileInfoDownloaded(int id, boolean downloaded) {
    	PreparedStatement prep;
    	boolean ok = false;
		try {
			prep = conn.prepareStatement("UPDATE filesInfo SET downloaded = ? WHERE file_id = ?;");
	    	prep.setBoolean(1, downloaded);
	    	prep.setInt(2, id);
            prep.addBatch();
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
	        ok = true;
		} catch (SQLException e) {
			log.error("Nieudany update informacji o pliku - boolean downloaded");
			e.printStackTrace();
		}
		return ok;
    }
    
    public boolean updateFileInfoParsed(int id, boolean downloaded) {
    	PreparedStatement prep;
    	boolean ok = false;
		try {
			prep = conn.prepareStatement("UPDATE filesInfo SET parsed = ? WHERE file_id = ?;");
	    	prep.setBoolean(1, downloaded);
	    	prep.setInt(2, id);
            prep.addBatch();
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
	        ok = true;
		} catch (SQLException e) {
			log.error("Nieudany update informacji o pliku - boolean parsed");
			e.printStackTrace();
		}
		return ok;
    }
    
    
    public void rollback() {
    	try {
			stat.executeQuery("ROLLBACK");
		} catch (SQLException e) {
			log.info("nie było żadnej transakcji");
			e.printStackTrace();
		}
    }
}
