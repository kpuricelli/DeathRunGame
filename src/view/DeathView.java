/**
 * Class that shows the end screen of the game.
 * 
 * @author Kyle Puricelli
 * ITP 368, Spring 2019
 * Final Project
 * kpuricel@usc.edu
 * 
 * Called when the player dies.
 */
package view;

import controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class DeathView
{
	private Scene endScene;
	private VBox mainPane;
	private final int mainPaneHeight = 800;
	private final int mainPaneWidth = 1200;
	private Label label;
	private Label coins;
	private Button quit;

	public DeathView(Controller controller)
	{
		mainPane = new VBox();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setSpacing(30);
		mainPane.setStyle("-fx-background-color: black");
		label = new Label("YOU DIED");
		label.setTextAlignment(TextAlignment.CENTER);
		label.setWrapText(true);
		label.setFont(Font.font("Copperplate", 100));
		label.setTextFill(Color.RED);
		label.setAlignment(Pos.CENTER);	
		mainPane.getChildren().addAll(label);
		endScene = new Scene(mainPane, mainPaneWidth, mainPaneHeight);
	}

	public Scene getScene(int numCoins)
	{
		coins = new Label("COLLECTED " + Integer.toString(numCoins) + " COINS");
		coins.setTextAlignment(TextAlignment.CENTER);
		coins.setWrapText(true);
		coins.setFont(Font.font("Copperplate", 20));
		coins.setTextFill(Color.RED);
		coins.setAlignment(Pos.CENTER);	
		
		quit = new Button("EXIT");
		quit.setStyle("-fx-background-color: red");
		quit.setFont(Font.font("Copperplate", 20));
		quit.setOnAction(e -> 
		{
			System.exit(1);
		});
		
		mainPane.getChildren().addAll(coins, quit);
		return endScene;
	}
}
