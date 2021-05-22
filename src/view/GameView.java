/**
 * Class that shows the game screen of the game.
 * 
 * @author Kyle Puricelli
 * ITP 368, Spring 2019
 * Final Project
 * kpuricel@usc.edu
 * 
 * Called from the controller.
 */

package view;

import model.Block;
import model.Coin;
import model.Flag;
import model.Ghastly;
import model.Haunter;
import model.Player;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


//========================================================================
// Represents the in game view and controls input
//========================================================================
public class GameView
{
	// ========================================================================
	// Class variables
	// ========================================================================
	private final int height = 800;
	private final int width = 1200;
	
	private int coinCount = 0;
	
	private Controller controller;
	private Scene gameScene;
	private Pane mainPane;
	private Pane gamePane;
	private GameTimer timer;
	private long lastTime;
	
	// Lists of all game objects
	private List<Block> blocks = new ArrayList<Block>();
	private List<Coin> coins = new ArrayList<Coin>();
	
	// Player controller actor
	private Player player;
	
	// NPC ghost
	private Haunter haunter;
	
	// NPC small ghost-s
	private List<Ghastly> ghosts = new ArrayList<Ghastly>();
	
	// Keyboard input
	private BitSet keyboardBitSet = new BitSet();
    private KeyCode leftKey = KeyCode.A;
    private KeyCode rightKey = KeyCode.D;
    private KeyCode shortJumpKey = KeyCode.S;
    private KeyCode jumpKey = KeyCode.SPACE;
    
    // Imageview that saves the background image
    ImageView backgroundImage;
    
    // Label to remind the player that you can't go backwards
    private Label warning;
    
    // Media player object. Even though we never call start or stop outside of
    // the initial setup, if it is not declared here sometimes the Java
    // garbage collector will eat it
    MediaPlayer mediaPlayer;


	// ========================================================================
	// Functions
	// ========================================================================
	
	
	// ========================================================================
	// Constructor created by the controller
	// ========================================================================
	public GameView(Controller controller)
	{
		this.controller = controller;
		mainPane = new Pane();
		setupPanes();
		gameScene = new Scene(mainPane, width, height);
		setupEvents();
	}
	
	
	// ========================================================================
	// ========================================================================
	public void addToCoinCount()
	{
		++coinCount;
	}
	
	
	// ========================================================================
	// ========================================================================
	public List<Block> getBlocks()
	{
		return blocks;
	}
	
	
	// ========================================================================
	// ========================================================================
	public List<Coin> getCoins()
	{
		return coins;
	}
	
	
	// ========================================================================
	// ========================================================================
	public int getNumCoins()
	{
		return coinCount;
	}
	
	
	// ========================================================================
	// ========================================================================
	public Haunter getHaunter()
	{
		return haunter;
	}
	
	
	// ========================================================================
	// ========================================================================
	public List<Ghastly> getGhosts()
	{
		return ghosts;
	}
	

	// ========================================================================
	// Sets up the game pane and game objects
	// ========================================================================
	private void setupPanes()
	{
		timer = new GameTimer();
		gamePane = new Pane();
		gamePane.setMinSize(width, height);
		gamePane.setMaxSize(width, height);
		
		// Set the background image for the game
		Image image = new Image("view/Background.png", width, height, false, false);
		backgroundImage = new ImageView(image);
		gamePane.getChildren().add(backgroundImage);
		
		// Setup the game level
		setupLevel();
		
		mainPane.getChildren().addAll(gamePane);
		warning = new Label("THERE IS NO RETREAT");
		warning.setStyle("-fx-background-color: red");
		warning.setFont(Font.font("Copperplate", 20));
    	gamePane.getChildren().add(warning);
    	warning.setVisible(false);
	}
	
	
	// ========================================================================
	// Sets up the game's level layout
	// ========================================================================
	private void setupLevel()
	{
		// Put 10x screen width of blocks:
		// Each block is 63px wide, so it takes 19.04 blocks to cover 1x screen width
		// => 63 * 19 = 1197 for one screen width
		for (int i = width / 2; i < 1197 * 10; i += 63)
		{
			// Always need the first block to avoid instant death
			if (i != 0 && i % 11 == 0)
			{
				continue;
			}
			Block block = new Block(i, 750);
			gamePane.getChildren().add(block.getImageView());
			blocks.add(block);
		}
		
		// Add a higher row of blocks, with some missing
		for (int i = width / 2 + 100; i < 1197 * 10 - 100; i += 63)
		{
			if (i % 5 == 0 || i % 8 == 0)
			{
				continue;
			}
			Block block = new Block(i, 600);
			gamePane.getChildren().add(block.getImageView());
			blocks.add(block);
		}
		
		// Creating a sort of pyramid here
		// Only put coins here
		for (int i = width / 2 + 200; i < 1197 * 10 - 200; i += 63)
		{
			if (i % 3 == 0 || i % 4 == 0 || i % 5 == 0 || i % 7 == 0 || i % 9 == 0)
			{
				continue;
			}
			Block block = new Block(i, 400);
			gamePane.getChildren().add(block.getImageView());
			blocks.add(block);
			
			// Add a coin at certain intervals (only on top of a block)
			if (i % 2 == 0)
			{
				Coin coin = new Coin(i, 370);
				gamePane.getChildren().add(coin.getImageView());
				coins.add(coin);
			}
		}
		
		// Setup a vertical column of flags at the end
		for (int i = 0; i < height; i += 50)
		{
			Flag flag = new Flag(11970, i);
			gamePane.getChildren().add(flag.getImageView());
		}
		
		// Spawn the player
		player = new Player(width / 2, 650, this, controller);
		gamePane.getChildren().add(player.getImageView());
		
		// Spawn Haunter
		haunter = new Haunter(-256, height / 2 - 150);
		gamePane.getChildren().add(haunter.getImageView());
		
		// Spawn a few Ghastly-s
		// Spawning them far out in the x-direction so they keep coming for the entire
		// duration of play
		for (int i = 30000; i > width + 1000; i -= 2000)
		{
			// Top level
			Ghastly ghastly = new Ghastly(i, 300);
			Ghastly ghastly2 = new Ghastly(i - 500, 500);
			Ghastly ghastly3 = new Ghastly(i - 1000, 650);
			gamePane.getChildren().addAll(ghastly.getImageView(), ghastly2.getImageView(), ghastly3.getImageView());
			ghosts.add(ghastly);
			ghosts.add(ghastly2);
			ghosts.add(ghastly3);
		}
		
		// Start the background music
		String musicFile = "BackgroundMusic.mp3";
		Media sound = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		
		// Setup a new runnable to restart the music if it reaches the end
		// as the mp3 file is ~1 minute
	    mediaPlayer.setOnEndOfMedia(new Runnable() 
	    {
	        @Override
	        public void run() 
	        {
	        	mediaPlayer.seek(Duration.ZERO);
	        	mediaPlayer.play();
	        }
	    }); 
	}


	// ========================================================================
	// Bind keys for play
	// ========================================================================
	public void setupEvents()
	{
        gameScene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        gameScene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
	}
	
	
	// ========================================================================
	// Key press
	// ========================================================================
    private EventHandler<KeyEvent> keyPressedEventHandler = new EventHandler<KeyEvent>() 
    {
        @Override
        public void handle(KeyEvent event) 
        {
            keyboardBitSet.set(event.getCode().ordinal(), true);
        }
    };
    

	// ========================================================================
	// Key release
	// ========================================================================
    private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() 
    {
        @Override
        public void handle(KeyEvent event) 
        {
            keyboardBitSet.set(event.getCode().ordinal(), false);
        }
    };
    
    
	// ========================================================================
	// ========================================================================
    public boolean isMoveLeft() 
    {
        return keyboardBitSet.get(leftKey.ordinal()) && !keyboardBitSet.get(rightKey.ordinal());  
    }

    
	// ========================================================================
	// ========================================================================
    public boolean isMoveRight() 
    {
        return keyboardBitSet.get(rightKey.ordinal()) && !keyboardBitSet.get(leftKey.ordinal());
    }
    
    
	// ========================================================================
	// ========================================================================
    public boolean isJump()
    {
        return keyboardBitSet.get(jumpKey.ordinal());
    }
    
    
	// ========================================================================
	// ========================================================================
    public boolean isShortJump()
    {
        return keyboardBitSet.get(shortJumpKey.ordinal());
    }


	// ========================================================================
	// ========================================================================
	public Scene getScene()
	{
		return gameScene;
	}

	
	// ========================================================================
	// ========================================================================
	public void startGame()
	{
		lastTime = System.nanoTime();
		timer.start();
	}

	// ========================================================================
	// ========================================================================
	public void endGame()
	{
		timer.stop();
	}
	
	
	// ========================================================================
	// AnimationTimer object for handling movement
	// ========================================================================
	class GameTimer extends AnimationTimer
	{
		@Override
		public void handle(long now)
		{
			// Update moving objects each frame
	        long elapsedNanoSeconds = now - lastTime;
	        double deltaTime = elapsedNanoSeconds / 1_000_000_000.0;

	        // Process input
	        if (isMoveRight())
	        {
	        	player.goRight();
	        	gamePane.setTranslateX(-player.getXPos() + width / 2);
	        	
	        	// Also update the background position so it always covers the entire pane
	        	backgroundImage.relocate(player.getXPos() - width / 2, 0);
	        	
	        }
	        // Remind the player that there is no retreat!
	        else if (isMoveLeft())
	        {
	        	warning.relocate(player.getXPos() - warning.getWidth() / 2, height / 2);
	        	warning.setVisible(true);
	        }
	        else
	        {
	        	player.stop();
	        }
	        
	        // Want to be able to run and jump at the same time
	        if (isJump())
	        {
	        	player.jump();
	        }
	        // or short jump
	        else if (isShortJump())
	        {
	        	player.shortJump();
	        }
	  
	        // Update the player and the NPC-s
			player.update(deltaTime);
			haunter.update(deltaTime);
			for (Ghastly g : ghosts)
			{
				g.update(deltaTime);
			}
			lastTime = now;
		}
	}
}
