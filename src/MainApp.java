import java.util.LinkedList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainApp extends Application {	
	private DBServices dbServices = new DBServices();
	
	private GridPane mainPane = new GridPane();
	private Button newRecord = new Button("Add New Score");
	private HBox options = new HBox(newRecord);
	
	@SuppressWarnings("rawtypes")
	private TableView tableView = new TableView();
    
	@SuppressWarnings("unchecked")
	@Override
    public void start(Stage theStage) {
		DBServices.createNewTable();
		
    	TableColumn<String, ScoreModel> questCol = new TableColumn<>("Quest");
    	questCol.setCellValueFactory(new PropertyValueFactory<>("quest"));
    	questCol.setPrefWidth(300);
    	
    	TableColumn<String, ScoreModel> heroesCol = new TableColumn<>("Heroes");
    	heroesCol.setCellValueFactory(new PropertyValueFactory<>("heroes"));
    	heroesCol.setPrefWidth(250);
    	
    	TableColumn<Integer, ScoreModel> scoreCol = new TableColumn<>("Score");
    	scoreCol.setCellValueFactory(new PropertyValueFactory<>("finalScore"));
    	scoreCol.setPrefWidth(150);
    	scoreCol.setStyle( "-fx-alignment: CENTER;");
    	
    	tableView.getColumns().add(questCol);
    	tableView.getColumns().add(heroesCol);
    	tableView.getColumns().add(scoreCol);
    	
    	loadScores(dbServices.selectAll());
    	tableView.setPrefWidth(705);
    	tableView.setPrefHeight(240);
    	
    	options.setAlignment(Pos.BASELINE_CENTER);
        
        newRecord.setOnAction(e -> {
        	showAddScene();
        });
               
        mainPane.add(tableView, 0, 0, 1, 1);
        mainPane.add(options, 0, 1, 1, 1);
        mainPane.setVgap(10);
    	
        BorderPane fullPane = new BorderPane(mainPane);
        BorderPane.setMargin(mainPane, new Insets(10, 10, 10, 10));
        Scene scene = new Scene(fullPane);
        theStage.setTitle("Lord of the Rings - Card Game Score Records");
        theStage.setScene(scene);
        theStage.show();
    }    
    
    public void showAddScene() {
    	GridPane addPane = new GridPane();
    	addPane.setVgap(10);
    	
    	BorderPane fullPane = new BorderPane(addPane);
    	BorderPane.setMargin(addPane, new Insets(10, 10, 10, 10));
    	
    	Scene addScene = new Scene(fullPane);
    	Stage addStage = new Stage();
    	Button addScore = new Button("Add Score");
    	Button cancel = new Button("Cancel");
    	
    	// Hero line
    	Label heroesL = new Label("Heroes:");
    	heroesL.setPrefWidth(60);
    	TextField heroesTF = new TextField();
    	heroesTF.setPrefWidth(320);
    	HBox heroes = new HBox(heroesL, heroesTF);
    	heroes.setSpacing(10);
    	
    	// Quest line
    	Label questL = new Label("Quest:");
    	questL.setPrefWidth(60);
    	TextField questTF = new TextField();
    	questTF.setPrefWidth(320);
    	HBox quest = new HBox(questL, questTF);
    	quest.setSpacing(10);
    	
    	double labelsW = 130;
    	// Line 3 - Final Threat and Rounds Taken
    	Label finalThreatL = new Label("Final Threat:");
    	finalThreatL.setPrefWidth(labelsW);
    	TextField finalThreatTF = new TextField();
    	finalThreatTF.setPrefWidth(50);
    	
    	Label roundsL = new Label("Rounds Taken:");
    	roundsL.setPrefWidth(labelsW);
    	TextField roundsTF = new TextField();
    	roundsTF.setPrefWidth(50);
    	
    	HBox line3 = new HBox(finalThreatL, finalThreatTF, roundsL, roundsTF);
    	line3.setSpacing(10);
    	
    	// Line 4 - Damage on Heroes and Dead Heroes Cost
    	Label damageL = new Label("Damage on Heroes:");
    	damageL.setPrefWidth(labelsW);
    	TextField demageTF = new TextField();
    	demageTF.setPrefWidth(50);
    	
    	Label deadHeroesCostL = new Label("Dead Heroes Cost:");
    	deadHeroesCostL.setPrefWidth(labelsW);
    	TextField deadHeroesCostTF = new TextField();
    	deadHeroesCostTF.setPrefWidth(50);
    	
    	HBox line4 = new HBox(damageL, demageTF, deadHeroesCostL, deadHeroesCostTF);
    	line4.setSpacing(10);
    	
    	// Line 5 - Victory Points
    	Label victoryL = new Label("Victory Points:");
    	victoryL.setPrefWidth(labelsW);
    	TextField victoryTF = new TextField();
    	victoryTF.setPrefWidth(50);
    	
    	HBox line5 = new HBox(victoryL, victoryTF);
    	line5.setSpacing(10);
    	
    	// Buttons line
    	HBox buttonHBox = new HBox(cancel, addScore);
    	buttonHBox.setAlignment(Pos.BASELINE_RIGHT);
    	buttonHBox.setSpacing(10);
    	
    	Separator separator1 = new Separator(Orientation.HORIZONTAL);
    	Separator separator2 = new Separator(Orientation.HORIZONTAL);
    	
    	addPane.add(heroes, 0, 0);
    	addPane.add(quest, 0, 1);
    	addPane.add(separator1, 0, 2);
    	addPane.add(line3, 0, 3);
    	addPane.add(line4, 0, 4);
    	addPane.add(line5, 0, 5);
    	addPane.add(separator2, 0, 6);
    	addPane.add(buttonHBox, 0, 7);
    	
    	addScore.setOnAction(e -> {
    		try {    			
    			ScoreModel score = new ScoreModel(heroesTF.getText(), questTF.getText(), 
    					Integer.parseInt(finalThreatTF.getText()), Integer.parseInt(deadHeroesCostTF.getText()), 
    					Integer.parseInt(demageTF.getText()), Integer.parseInt(roundsTF.getText()), 
    					Integer.parseInt(victoryTF.getText()));
    			
    			dbServices.insert(score);
    			
    			System.out.println("Score inserted: " + score.toString());
    			loadScores(dbServices.selectAll());
    			addStage.close();
    		} catch (NumberFormatException ex) {
    			System.out.println("Please enter only numbers after the second field");
    		}    		
    	});
    	
    	cancel.setOnAction(e -> {
    		addStage.close();
    	});
    	
    	addStage.setTitle("Add new score");
    	addStage.setScene(addScene);
    	addStage.show();
    }    
    
    @SuppressWarnings("unchecked")
	public void loadScores(LinkedList<ScoreModel> scores) {
    	tableView.getItems().clear();
    	for (ScoreModel score : scores) {
    		tableView.getItems().add(score);
    	}
    }

}