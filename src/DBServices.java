import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
   
public class DBServices {   
    private Connection connect() {
        // SQLite connection string  
        String url = "jdbc:sqlite:LOTRApp.db";  
        Connection conn = null;  
        try {  
            conn = DriverManager.getConnection(url);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
        return conn;  
    }
    
    public static void createNewDatabase(String fileName) {  
    	   
        String url = "jdbc:sqlite:" + fileName;  
   
        try {  
            Connection conn = DriverManager.getConnection(url);  
            if (conn != null) {  
                DatabaseMetaData meta = conn.getMetaData();  
                System.out.println("The driver name is " + meta.getDriverName());  
                System.out.println("A new database has been created."); 
            }  
   
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
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
  
    public void insert(ScoreModel score) {  
        String sql = "INSERT INTO scores(heroes, quest, final_threat, dead_heroes_cost, demage_on_heroes, "
        		+ "rounds_taken, victory_points, final_score) VALUES(?,?,?,?,?,?,?,?)";  
   
        try{  
            Connection conn = this.connect();  
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, score.getHeroes());
            pstmt.setString(2, score.getQuest());
            pstmt.setInt(3, score.getFinalThreat());
            pstmt.setInt(4, score.getDeadHeroesCost());
            pstmt.setInt(5, score.getDemageOnHeroes());
            pstmt.setInt(6, score.getRoundsTaken());
            pstmt.setInt(7, score.getVictoryPoints());
            pstmt.setInt(8, score.getFinalScore());
            pstmt.executeUpdate();  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public LinkedList<String> selectAllString(){
    	LinkedList<String> results = new LinkedList<>();
        String sql = "SELECT * FROM scores";
          
        try {  
            Connection conn = this.connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {                
                String quest = rs.getString("quest");
                String heroes = rs.getString("heroes");
                int score = rs.getInt("final_score");
                
                String line = quest + " | " + heroes + " | " + score;
                results.add(line);
            }
            return results;
        } catch (SQLException e) {  
            System.out.println(e.getMessage());
            return results;
        }
    }
    
    public LinkedList<ScoreModel> selectAll(){
    	LinkedList<ScoreModel> results = new LinkedList<>();
        String sql = "SELECT * FROM scores";
          
        try {  
            Connection conn = this.connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {                
                String quest = rs.getString("quest");
                String heroes = rs.getString("heroes");
                int score = rs.getInt("final_score");
                
                ScoreModel scoreModel = new ScoreModel(heroes, quest, score);
                results.add(scoreModel);
            }
            return results;
        } catch (SQLException e) {  
            System.out.println(e.getMessage());
            return results;
        }
    }
}