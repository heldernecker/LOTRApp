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
    
    /**
     * Creates a database
     * @param fileName
     */
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
    
    /**
     * Creates a table
     */
    public static void createNewTable() {  
        // SQLite connection string  
        String url = "jdbc:sqlite:LOTRApp.db";  
          
        // SQL statement for creating a new table  
        String sql = "CREATE TABLE IF NOT EXISTS scores (\n"  
                + " id integer PRIMARY KEY,\n"
        		+ " heroes text NOT NULL,\n"
                + " quest text NOT NULL,\n"
                + " deck_url text,\n"
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
     * Insert a new score into database
     * @param score
     */
    public void insert(ScoreModel score) {  
        String sql = "INSERT INTO scores(heroes, quest, deck_url, final_threat, dead_heroes_cost, demage_on_heroes, "
        		+ "rounds_taken, victory_points, final_score) VALUES(?,?,?,?,?,?,?,?,?)";  
   
        try{  
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, score.getHeroes());
            pstmt.setString(2, score.getQuest());
            pstmt.setString(3, score.getDeckURL());
            pstmt.setInt(4, score.getFinalThreat());
            pstmt.setInt(5, score.getDeadHeroesCost());
            pstmt.setInt(6, score.getDemageOnHeroes());
            pstmt.setInt(7, score.getRoundsTaken());
            pstmt.setInt(8, score.getVictoryPoints());
            pstmt.setInt(9, score.getFinalScore());
            pstmt.executeUpdate();  
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Update score in database
     * @param score
     */
    public void update(ScoreModel score) {
    	String sql = "UPDATE scores SET heroes = ?, "
    			+ "quest = ?, "
    			+ "deck_url = ?, "
    			+ "final_threat = ?, "
    			+ "dead_heroes_cost = ?, "
    			+ "demage_on_heroes = ?, "
    			+ "rounds_taken = ?, "
    			+ "victory_points = ?, "
    			+ "final_score = ? "
    			+ "WHERE id = ?";
    	
    	try{  
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, score.getHeroes());
            pstmt.setString(2, score.getQuest());
            pstmt.setString(3, score.getDeckURL());
            pstmt.setInt(4, score.getFinalThreat());
            pstmt.setInt(5, score.getDeadHeroesCost());
            pstmt.setInt(6, score.getDemageOnHeroes());
            pstmt.setInt(7, score.getRoundsTaken());
            pstmt.setInt(8, score.getVictoryPoints());
            pstmt.setInt(9, score.getFinalScore());
            pstmt.setInt(10, score.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Deletes score from database
     * @param score
     */
    public void delete(ScoreModel score) {
    	String sql = "DELETE FROM scores WHERE id = ?";
    	
    	try {
    		Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, score.getId());
            pstmt.executeUpdate();
    	} catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Method to get all scores in database
     * @return
     */
    public LinkedList<ScoreModel> selectAll(){
    	LinkedList<ScoreModel> results = new LinkedList<>();
        String sql = "SELECT * FROM scores";
          
        try {  
            Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
              
            // loop through the result set  
            while (rs.next()) {
            	int id = rs.getInt("id");
            	String heroes = rs.getString("heroes");
                String quest = rs.getString("quest");
                String deckURL = rs.getString("deck_url");
                int finalThreat = rs.getInt("final_threat");
                int deadHeroesCost = rs.getInt("dead_heroes_cost");
                int demageOnHeroes = rs.getInt("demage_on_heroes");
                int roundsTaken = rs.getInt("rounds_taken");
                int victoryPoints = rs.getInt("victory_points");
                
                ScoreModel scoreModel = new ScoreModel(id, heroes, quest, deckURL, finalThreat, deadHeroesCost, 
                		demageOnHeroes, roundsTaken, victoryPoints);
                results.add(scoreModel);
            }
            return results;
        } catch (SQLException e) {  
            System.out.println(e.getMessage());
            return results;
        }
    }
}