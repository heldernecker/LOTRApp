import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ScoreModel {
	private int id;
	private String heroes;
	private String quest;
	private int finalThreat;
	private int deadHeroesCost;
	private int demageOnHeroes;
	private int roundsTaken;
	private int victoryPoints;
	private int finalScore;
	private String deckURL;
	private Hyperlink deckLink;
	
	public ScoreModel(String heroes, String quest, int finalThreat, int deadHeroesCost, int demageOnHeroes,
			int roundsTaken, int victoryPoints) {
		super();
		this.heroes = heroes;
		this.quest = quest;
		this.finalThreat = finalThreat;
		this.deadHeroesCost = deadHeroesCost;
		this.demageOnHeroes = demageOnHeroes;
		this.roundsTaken = roundsTaken;
		this.victoryPoints = victoryPoints;
		this.finalScore = finalThreat + deadHeroesCost + demageOnHeroes + (10 * roundsTaken) - victoryPoints;
		
		deckURL = "https://ringsdb.com/decks";
		deckLink = new Hyperlink("Deck");
		
		deckLink.setOnAction(e -> {
			Stage webStage = new Stage();
			WebView webView = new WebView();
			
			webView.getEngine().load(deckURL);

	        VBox vBox = new VBox(webView);
	        Scene scene = new Scene(vBox, 960, 600);

	        webStage.setScene(scene);
	        webStage.show();
		});
	}

	public ScoreModel(int id, String heroes, String quest, int finalThreat, int deadHeroesCost, int demageOnHeroes,
			int roundsTaken, int victoryPoints) {
		super();
		this.id = id;
		this.heroes = heroes;
		this.quest = quest;
		this.finalThreat = finalThreat;
		this.deadHeroesCost = deadHeroesCost;
		this.demageOnHeroes = demageOnHeroes;
		this.roundsTaken = roundsTaken;
		this.victoryPoints = victoryPoints;
		this.finalScore = finalThreat + deadHeroesCost + demageOnHeroes + (10 * roundsTaken) - victoryPoints;
		
		deckURL = "https://ringsdb.com/decks";
		deckLink = new Hyperlink("Deck");
		
		deckLink.setOnAction(e -> {
			Stage webStage = new Stage();
			WebView webView = new WebView();
			
			webView.getEngine().load(deckURL);

	        VBox vBox = new VBox(webView);
	        Scene scene = new Scene(vBox, 960, 600);

	        webStage.setScene(scene);
	        webStage.show();
		});
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeroes() {
		return heroes;
	}

	public void setHeroes(String heroes) {
		this.heroes = heroes;
	}

	public String getQuest() {
		return quest;
	}

	public void setQuest(String quest) {
		this.quest = quest;
	}

	public int getFinalThreat() {
		return finalThreat;
	}

	public void setFinalThreat(int finalThreat) {
		this.finalThreat = finalThreat;
	}

	public int getDeadHeroesCost() {
		return deadHeroesCost;
	}

	public void setDeadHeroesCost(int deadHeroesCost) {
		this.deadHeroesCost = deadHeroesCost;
	}

	public int getDemageOnHeroes() {
		return demageOnHeroes;
	}

	public void setDemageOnHeroes(int demageOnHeroes) {
		this.demageOnHeroes = demageOnHeroes;
	}

	public int getRoundsTaken() {
		return roundsTaken;
	}

	public void setRoundsTaken(int roundsTaken) {
		this.roundsTaken = roundsTaken;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	public int getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}

	public String getDeckURL() {
		return deckURL;
	}

	public void setDeckURL(String deckURL) {
		this.deckURL = deckURL;
	}

	public Hyperlink getDeckLink() {
		return deckLink;
	}

	public void setDeckLink(Hyperlink deckLink) {
		this.deckLink = deckLink;
	}

	@Override
	public String toString() {
		return "ScoreModel [id=" + id + ", heroes=" + heroes + ", quest=" + quest + ", finalThreat=" + finalThreat
				+ ", deadHeroesCost=" + deadHeroesCost + ", demageOnHeroes=" + demageOnHeroes + ", roundsTaken="
				+ roundsTaken + ", victoryPoints=" + victoryPoints + ", finalScore=" + finalScore + "]";
	}

	
	
	
}
