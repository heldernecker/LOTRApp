import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  
import java.sql.Statement;  
   
public class CreateTable {  
   
    public static void createNewTable() {  
        // SQLite connection string  
        String url = "jdbc:sqlite:LOTRApp.db";  
          
        // SQL statement for creating a new table  
        String sql = "CREATE TABLE IF NOT EXISTS scores (\n"  
                + " id integer PRIMARY KEY,\n"
        		+ " heroes text NOT NULL,\n"
                + " quest text NOT NULL,\n"
        		+ " final_threat integer NOT NULL,\n"
                + " dead_heroes_cost integer,\n"
        		+ " demage_on_heroes integer,\n"
                + " rounds_taken integer,\n"
        		+ " victory_points integer,\n"
                + " final_score integer\n"
                + ");";
          
        try{  
            Connection conn = DriverManager.getConnection(url);  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());
        }  
    }  
   
    /** 
     * @param args the command line arguments 
     */  
    public static void main(String[] args) {  
        createNewTable();  
    }  
   
}