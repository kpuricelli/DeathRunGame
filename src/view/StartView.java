/**
 * Class that shows the start screen of the game.
 * 
 * @author Kyle Puricelli
 * ITP 368, Spring 2019
 * Final Project
 * kpuricel@usc.edu
 * 
 * Called from the controller.
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

public class StartView
{
	private Scene startScene;
	private VBox mainPane;
	final private int mainPaneHeight = 800;
	final private int mainPaneWidth = 1200;
	private Label titleLabel;
	private Label instructions;
	private Button startButton;

	public StartView(Controller controller)
	{
		mainPane = new VBox();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setSpacing(30);
		mainPane.setStyle("-fx-background-color: black");
		titleLabel = new Label("DEATH RUN");
		titleLabel.setFont(Font.font("Copperplate", 100));
		titleLabel.setTextFill(Color.RED);
		titleLabel.setAlignment(Pos.CENTER);
		
		instructions = new Label("THERE IS ONE GHOST COMING AT YOU FROM THE LEFT HAND SIDE\n"
				+ "THERE ARE AN UNKNOWN NUMBER OF GHOSTS COMING AT YOU FROM THE RIGHT HAND SIDE\n"
				+ "THERE ARE 35 POSSIBLE COINS TO COLLECT\n"
				+ "PRESS 'D' TO RUN RIGHT, 'S' TO SHORT JUMP, AND THE SPACEBAR TO REGULAR JUMP\n"
				+ "RUNNING LEFT IS FORBIDDEN\n"
				+ "IF YOU TOUCH ANY GHOST, YOU DIE\n"
				+ "IF YOU FALL DOWN ANY HOLES, YOU DIE\n"
				+ "COLLCT AS MANY COINS AS POSSIBLE WHILE AVOIDING DEATH\n"
				+ "GOOD LUCK\n");
		instructions.setFont(Font.font("Copperplate", 20));
		instructions.setTextFill(Color.RED);
		instructions.setTextAlignment(TextAlignment.CENTER);
		
		startButton = new Button("BEGIN");
		startButton.setStyle("-fx-background-color: red");
		startButton.setFont(Font.font("Copperplate", 20));
		mainPane.getChildren().addAll(titleLabel, instructions, startButton);
		startButton.setOnAction(e -> 
		{
			controller.showGameScene();
		});
		startScene = new Scene(mainPane, mainPaneWidth, mainPaneHeight);
	}

	public Scene getScene()
	{
		return startScene;
	}
}
