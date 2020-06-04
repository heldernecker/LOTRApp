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
	private Button seeRecord = new Button("Details");
	private Button updateRecord = new Button("Update");
	private HBox options = new HBox(newRecord, seeRecord, updateRecord);
	
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
    	options.setSpacing(10);
        
        newRecord.setOnAction(e -> {
        	openScoreDetails(1, null);
        });
        
        seeRecord.setOnAction(e -> {
        	ScoreModel score = (ScoreModel) tableView.getSelectionModel().getSelectedItem();
        	openScoreDetails(2, score);
        });
        
        updateRecord.setOnAction(e -> {
        	ScoreModel score = (ScoreModel) tableView.getSelectionModel().getSelectedItem();
        	openScoreDetails(3, score);
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
	
	/**
	 * Opens new scene allowing the user to add, edit or visualize a score
	 * @param opt
	 * @param scoreModel
	 */
	public void openScoreDetails(int opt, ScoreModel scoreModel) {
		String title = "";
		GridPane addPane = new GridPane();
    	addPane.setVgap(10);
    	
    	BorderPane fullPane = new BorderPane(addPane);
    	BorderPane.setMargin(addPane, new Insets(10, 10, 10, 10));
    	
    	Scene addScene = new Scene(fullPane);
    	Stage addStage = new Stage();    	
    	
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
    	HBox buttonHBox = new HBox();
    	
    	buttonHBox.setAlignment(Pos.BASELINE_RIGHT);
    	buttonHBox.setSpacing(10);
    	
    	Separator separator1 = new Separator(Orientation.HORIZONTAL);
    	Separator separator2 = new Separator(Orientation.HORIZONTAL);
    	
    	if (opt == 1) { // Users will be able to add a new score
    		title = "Add New Score";
    		Button addScore = new Button("Add Score");
        	Button cancel = new Button("Cancel");
        	buttonHBox.getChildren().addAll(cancel, addScore);
        	
    		addScore.setOnAction(e -> {
        		try {    			
        			ScoreModel score = new ScoreModel(heroesTF.getText(), questTF.getText(), 
        					Integer.parseInt(finalThreatTF.getText()), Integer.parseInt(deadHeroesCostTF.getText()), 
        					Integer.parseInt(demageTF.getText()), Integer.parseInt(roundsTF.getText()), 
        					Integer.parseInt(victoryTF.getText()));
        			
        			dbServices.update(score);
        			
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
    	} else {    		
    		Button close = new Button("Close");
    		buttonHBox.getChildren().add(close);
    		
    		heroesTF.setText(scoreModel.getHeroes());        	
        	questTF.setText(scoreModel.getQuest());        	
        	finalThreatTF.setText(scoreModel.getFinalThreat() + "");       	    	
        	roundsTF.setText(scoreModel.getRoundsTaken() + "");        	
        	demageTF.setText(scoreModel.getDemageOnHeroes() + "");        	
        	deadHeroesCostTF.setText(scoreModel.getDeadHeroesCost() + "");        	
        	victoryTF.setText(scoreModel.getVictoryPoints() + "");        	
        	
        	close.setOnAction(e -> {
        		addStage.close();
        	});
        	
        	if (opt == 2) { // User will just see the details
        		title = "Score Details";
        		
            	heroesTF.setDisable(true);            	
            	questTF.setDisable(true);            	
            	finalThreatTF.setDisable(true);           	
            	roundsTF.setDisable(true);            	
            	demageTF.setDisable(true);            	
            	deadHeroesCostTF.setDisable(true);             	
            	victoryTF.setDisable(true);
        		
        	} else if (opt == 3) { // Users will be able to update the score
        		title = "Update Score";
        		
        		Button update = new Button("Update");
        		buttonHBox.getChildren().add(update);
        		
        		update.setOnAction(e -> {
        			try {    			
            			ScoreModel score = new ScoreModel(scoreModel.getId(), heroesTF.getText(), questTF.getText(), 
            					Integer.parseInt(finalThreatTF.getText()), Integer.parseInt(deadHeroesCostTF.getText()), 
            					Integer.parseInt(demageTF.getText()), Integer.parseInt(roundsTF.getText()), 
            					Integer.parseInt(victoryTF.getText()));
            			
            			dbServices.update(score);
            			
            			System.out.println("Score updated: " + score.toString());
            			loadScores(dbServices.selectAll());
            			addStage.close();
            		} catch (NumberFormatException ex) {
            			System.out.println("Please enter only numbers after the second field");
            		}
        		});
        	}
    	}
    	
    	addPane.add(heroes, 0, 0);
    	addPane.add(quest, 0, 1);
    	addPane.add(separator1, 0, 2);
    	addPane.add(line3, 0, 3);
    	addPane.add(line4, 0, 4);
    	addPane.add(line5, 0, 5);
    	addPane.add(separator2, 0, 6);
    	addPane.add(buttonHBox, 0, 7);
    	
    	addStage.setTitle(title);
    	addStage.setScene(addScene);
    	addStage.show();
	}
    
    /**
     * Method to load the data when the app at first run and also to update after a score is inserted
     * @param scores
     */
    @SuppressWarnings("unchecked")
	public void loadScores(LinkedList<ScoreModel> scores) {
    	tableView.getItems().clear();
    	for (ScoreModel score : scores) {
    		tableView.getItems().add(score);
    	}
    }

}