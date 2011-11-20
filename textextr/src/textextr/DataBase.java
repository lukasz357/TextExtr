package textextr;
import java.sql.*;

import org.apache.log4j.Logger;

public class DataBase {
    private Connection conn;
    private Statement stat;
    private static Logger log;
    public DataBase() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:TextExtrDB.db");
            stat = conn.createStatement();
//            stat.executeUpdate("drop table if exists people;");
//            stat.executeUpdate("create table people (name, occupation);");
        } catch (SQLException ex) {
            log.error("Blad w konstruktorze DataBase()");
        }
    }
}
