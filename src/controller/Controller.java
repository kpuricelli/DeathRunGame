/**
 * Class that controls the overall flow of the game. Contains main.
 * 
 * @author Kyle Puricelli
 * ITP 368, Spring 2019
 * Final Project
 * kpuricel@usc.edu
 * 
 * Calls every class.
 */
package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.DeathView;
import view.GameView;
import view.StartView;
import view.WinView;

public class Controller extends Application
{
	private Stage primaryStage;
	
	private StartView startView; 
	private GameView gameView;
	private DeathView deathView;
	private WinView winView;


	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DEATH RUN");
		setupScenes();
		showStartScene();
	}

	public Stage getStage()
	{
		return primaryStage;
	}

	public void setupScenes()
	{
		startView = new StartView(this);
		gameView = new GameView(this);
		deathView = new DeathView(this);
		winView = new WinView(this);
	}

	public void showStartScene()
	{
		primaryStage.setScene(startView.getScene());
		primaryStage.show();
	}

	public void showGameScene()
	{
		primaryStage.setScene(gameView.getScene());
		gameView.startGame();
	}

	public void showDeathScene(int numCoins)
	{
		gameView.endGame();
		primaryStage.setScene(deathView.getScene(numCoins));
	}
	
	public void showWinScene(int numCoins)
	{
		gameView.endGame();
		primaryStage.setScene(winView.getScene(numCoins));
	}
}
