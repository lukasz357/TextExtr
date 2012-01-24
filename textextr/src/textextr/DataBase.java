package textextr;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
//            stat.executeUpdate("CREATE TEMP TABLE adIds(adId INTEGER)");
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
        PreparedStatement prep, prep2, prep3;
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
        
//            conn.setAutoCommit(false);
            prep.executeBatch();
//            conn.setAutoCommit(true);
            int fileInfoID, id;
          HashMap<Integer, Integer> ids = new HashMap<Integer, Integer>();
            ResultSet rs = stat.executeQuery("SELECT * FROM adverts");
            while(rs.next()) {
            	id = rs.getInt(1);
            	fileInfoID = rs.getInt(11);
            	ids.put(fileInfoID, id);
            }
            prep3 = conn.prepareStatement("INSERT INTO slowaKluczowe VALUES (?, ?, ?);");
            int idd;
            for(Advertisement a : adverts) {
            	idd = a.getFileInfoID();           	
            	if(a.getSlowaklucz() != null) {
	            	for(String s : a.getSlowaklucz()) {
	            		if(s != null) {
		            		prep3.setString(2, s);
		            		prep3.setInt(3, ids.get(idd));
		            		prep3.addBatch();
	            		}
	            	}
            	}
            }

          conn.setAutoCommit(false);
          prep3.executeBatch();
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
    
    public HashMap<Integer, Advertisement> getAdvertisements(String position) {
    	PreparedStatement prep, prep2;
    	int ad_id;
    	Date dataOgloszenia;
    	Date terminSklOfert;
    	String dyscyplinaNaukowa;
    	String instytucja;
    	String miasto;
    	String opis;
    	String stanowisko;
    	String linkDoStrony;
    	boolean parsingProblems;
    	int file_identity;
    	String url;
    	HashMap<Integer, Advertisement> adverts = new HashMap<Integer, Advertisement>();
    	try {
    		prep = conn.prepareStatement("SELECT * FROM adverts left outer join filesInfo on adverts.file_identity = filesInfo.file_id WHERE UPPER(adverts.stanowisko) = UPPER(?)");
    		prep.setString(1, position);
    		ResultSet rs = prep.executeQuery();
    		while(rs.next()) {
    			ad_id = rs.getInt(1);
    			dataOgloszenia = rs.getDate(2);
    			terminSklOfert = rs.getDate(3);
    			dyscyplinaNaukowa = rs.getString(4);
    			instytucja = rs.getString(5);
    			miasto = rs.getString(6);
    			opis = rs.getString(7);
    			stanowisko = rs.getString(8);
    			linkDoStrony = rs.getString(9);
    			parsingProblems = rs.getBoolean(10);
    			file_identity = rs.getInt(11);
    			url = rs.getString(13);
    			Advertisement adv = new Advertisement(ad_id, dataOgloszenia, dyscyplinaNaukowa, instytucja, linkDoStrony, miasto, opis, stanowisko, terminSklOfert, new ArrayList<String>(), parsingProblems, file_identity, url);
    			adverts.put(ad_id, adv);
    		}
    		
    		ResultSet rset = stat.executeQuery("SELECT * FROM slowaKluczowe");
    		int adID;
    		String sl;
    		while(rset.next()) {
    			sl = rset.getString(2);
    			adID = rset.getInt(3);
    			if(adverts.containsKey(adID)) {
    				adverts.get(adID).getSlowaklucz().add(sl);
    			}
    		}
    		
    	}catch (SQLException e) {
			log.error("Nieudało się pobtać ogłoszeń.");
			e.printStackTrace();
		}
    	return adverts;
    }
    
    public HashMap<Integer, Advertisement> getAdvertisements(Advertisement advert) {
    	System.out.println("In getAdvertisements");
    	PreparedStatement prep, prep2;
    	int ad_id;
    	Date dataOgloszenia;
    	long dataOglosz = 0;
    	Date terminSklOfert;
    	long termSklOf = 0;
    	String dyscyplinaNaukowa;
    	String instytucja;
    	String miasto;
    	String opis;
    	String stanowisko;
    	String linkDoStrony;
    	String slowo;
    	boolean parsingProblems;
    	int file_identity;
    	String url;
    	String statement = "";
    	String restOfStatement = "";
    	HashMap<Integer, Advertisement> adverts = new HashMap<Integer, Advertisement>();
    	ArrayList<Integer> adIds = new ArrayList<Integer>();
    	if((slowo = advert.getSlowaklucz().get(0)).length() > 0) {
    		
    		String query = "CREATE TEMPORARY TABLE adIds AS SELECT DISTINCT ad_identity as adId FROM slowaKluczowe WHERE UPPER(slowo) LIKE UPPER(\"%"+ slowo+"%\")";
    		try {
				stat.executeUpdate(query);
//				int id;
//				while(resultSet.next()) {
//					id = resultSet.getInt(1);
//					adIds.add(id);
//					System.out.println(id);
//				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
    		

    	}
    	

    	String baseStatement ="";
    	if(slowo.length() > 0) {
    		baseStatement = "SELECT * FROM adverts left outer join filesInfo on adverts.file_identity = filesInfo.file_id join adIds on adIds.adId = adverts.ad_id ";
    	}
    	else {
    		baseStatement = "SELECT * FROM adverts left outer join filesInfo on adverts.file_identity = filesInfo.file_id ";
    	}

    	boolean withWhere = false;
    	if((stanowisko = advert.getStanowisko()).length() > 0) {
    		withWhere = true;
    		restOfStatement += restOfStatement.length() > 0 ? " AND UPPER(adverts.stanowisko) LIKE UPPER(\"%"+stanowisko+"%\")" : " UPPER(adverts.stanowisko) LIKE UPPER(\"%"+stanowisko+"%\")";
    	}
    	if((miasto = advert.getMiasto()).length() > 0) {
    		withWhere = true;
    		restOfStatement += restOfStatement.length() > 0 ? " AND UPPER(adverts.miasto) LIKE UPPER(\"%"+miasto+"%\")" : " UPPER(adverts.miasto) LIKE UPPER(\"%"+miasto+"%\")";
    	}
    	if((instytucja = advert.getInstytucja()).length() > 0) {
    		withWhere = true;
    		restOfStatement += restOfStatement.length() > 0 ? " AND UPPER(adverts.instytucja) LIKE UPPER(\"%"+instytucja+"%\")" : " UPPER(adverts.instytucja) LIKE UPPER(\"%"+instytucja+"%\")";
    	}
    	if((dyscyplinaNaukowa = advert.getDyscyplinaNaukowa()).length() > 0) {
    		withWhere = true;
    		restOfStatement += restOfStatement.length() > 0 ? " AND UPPER(adverts.dyscyplinaNaukowa) LIKE UPPER(\"%"+dyscyplinaNaukowa+"%\")" : " UPPER(adverts.dyscyplinaNaukowa) LIKE UPPER(\"%"+dyscyplinaNaukowa+"%\")";
    	}
    	
    	
    	if((advert.getDataOgloszenia()) != null) {
    		dataOglosz = advert.getDataOgloszenia().getTime();
    		dataOgloszenia = new java.sql.Date(dataOglosz);
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(dataOgloszenia);
    		cal.add(Calendar.DATE, -1);
    		java.sql.Date newDate = new Date(cal.getTime().getTime());

//    		System.out.println("Data: "+dataOgloszenia.toString());
    		restOfStatement += restOfStatement.length() > 0 ? " AND adverts.dataOgloszenia > "+newDate.getTime() : "adverts.dataOgloszenia > "+newDate.getTime();
    	}
    	if((advert.getTerminSklOfert()) != null) {
    		termSklOf = advert.getTerminSklOfert().getTime();
    		terminSklOfert = new java.sql.Date(termSklOf);
    		
    		restOfStatement += restOfStatement.length() > 0 ? " AND adverts.terminSklOfert <= "+terminSklOfert.getTime() : "adverts.terminSklOfert > "+terminSklOfert.getTime();
    	}
    	if(withWhere) {
    		baseStatement += "WHERE";
    	}
    	statement = baseStatement + restOfStatement;
    	System.out.println(statement);
    	try {
    		ResultSet rs = stat.executeQuery(statement);
    		while(rs.next()) {
    			ad_id = rs.getInt(1);
    			dataOgloszenia = rs.getDate(2);
    			terminSklOfert = rs.getDate(3);
    			dyscyplinaNaukowa = rs.getString(4);
    			instytucja = rs.getString(5);
    			miasto = rs.getString(6);
    			opis = rs.getString(7);
    			stanowisko = rs.getString(8);
    			linkDoStrony = rs.getString(9);
    			parsingProblems = rs.getBoolean(10);
    			file_identity = rs.getInt(11);
    			url = rs.getString(13);
    			Advertisement adv = new Advertisement(ad_id, dataOgloszenia, dyscyplinaNaukowa, instytucja, linkDoStrony, miasto, opis, stanowisko, terminSklOfert, new ArrayList<String>(), parsingProblems, file_identity, url);
    			adverts.put(ad_id, adv);
    		}
    		
    		ResultSet rset = stat.executeQuery("SELECT * FROM slowaKluczowe");
    		int adID;
    		String sl;
    		while(rset.next()) {
    			sl = rset.getString(2);
    			adID = rset.getInt(3);
    			if(adverts.containsKey(adID)) {
    				adverts.get(adID).getSlowaklucz().add(sl);
    			}
    		}
    		
    	}catch (SQLException e) {
			log.error("Nieudało się pobrać ogłoszeń.");
			e.printStackTrace();
		}
    	return adverts;
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
