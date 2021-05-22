/**
 * Class representing a player in the game. Inherits from Actor.
 * 
 * @author Kyle Puricelli
 * ITP 368, Spring 2019
 * Final Project
 * kpuricel@usc.edu
 * 
 * Used in GameView.
 */
package model;

import java.util.List;

import controller.Controller;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.GameView;

public class Player extends Actor
{
	Controller controller;
	boolean inAir;
	boolean jump;
	boolean spacePressed;
	double ySpeed;
	double xSpeed;
	GameView game;
	

	public Player(float xPos, float yPos, GameView game, Controller controller)
	{
		super(xPos, yPos);
		image = new Image("model/Player.png");
		imageView = new ImageView(image);
		imageView.relocate(xPos, yPos);	
		spacePressed = false;
		jump = false;
		xSpeed = 0;
		ySpeed = 0;
		inAir = true;
		this.game = game;
		this.controller = controller;
	}
	
	public void goLeft()
	{
		xSpeed = -300.0;
	}
	
	
	public void goRight()
	{
		xSpeed = 300.0;
	}
	
	public void stop()
	{
		xSpeed = 0;
	}
	
	public void jump()
	{
		if (!inAir)
		{
			inAir = true;
			ySpeed = -800;
		}		
	}
	
	public void shortJump()
	{
		if (!inAir)
		{
			inAir = true;
			ySpeed = -400;
		}
	}
	
	public void update(double deltaTime)
	{		
		xPos += xSpeed * deltaTime;
		yPos += ySpeed * deltaTime;
		
		// Left limit
		if (xPos < 5)
		{
			xPos = 5;
		}
		
		// Check if the player fell down one of the holes
		if (yPos > 800)
		{
			controller.showDeathScene(game.getNumCoins());
		}
		
		// Show win scene at right limit
		if (xPos > 11970)
		{
			controller.showWinScene(game.getNumCoins());
		}
		
		// Collision checks
		List<Block> blocks = game.getBlocks();
		List<Coin> coins = game.getCoins();
		
		boolean noCollision = true;
		Bounds playerBounds = imageView.getBoundsInParent();
		for (Block b : blocks)
		{
			// Using the bounding boxes, check for collisions between the player and the blocks
			Bounds blockBounds = b.getImageView().getBoundsInParent();
			if (playerBounds.intersects(blockBounds))
			{
				noCollision = false;
				double dx1 = blockBounds.getMinX() - playerBounds.getMaxX();
				double dx2 = blockBounds.getMaxX() - playerBounds.getMinX();
				double dy1 = blockBounds.getMinY() - playerBounds.getMaxY();
				double dy2 = blockBounds.getMaxY() - playerBounds.getMinY();
				
				double dx = 0;
				if (Math.abs(dx1) < Math.abs(dx2))
				{
					dx = dx1;
				}
				else
				{
					dx = dx2;
				}

				double dy = 0;
				if (Math.abs(dy1) < Math.abs(dy2))
				{
					dy = dy1;
				}
				else
				{
					dy = dy2;
				}

				// Are we closer to top or bottom?
				if (Math.abs(dy) < Math.abs(dx))
				{
					if (dy < 0.0f)
					{
						// Landed on top
						ySpeed = 0;
						inAir = false;
					}
					else if (ySpeed < 0)
					{
						ySpeed = 0.0f;
					}
				}
			}
		}
		
		// Check collision with the coins
		for (Coin c : coins)
		{
			if (playerBounds.intersects(c.getImageView().getBoundsInParent()))
			{
				c.removeImage();
				game.addToCoinCount();
			}
		}
		
		// Check collision with the giant ghost
		if (playerBounds.intersects(game.getHaunter().getImageView().getBoundsInParent()))
		{
			controller.showDeathScene(game.getNumCoins());
		}
		
		// Check collision with the tiny ghosts
		for (Ghastly g : game.getGhosts())
		{
			if (playerBounds.intersects(g.getImageView().getBoundsInParent()))
			{
				controller.showDeathScene(game.getNumCoins());
			}
		}
		
		// Update position
		imageView.relocate(xPos, yPos);
		
		// Cheap way of implementing gravity
		if (inAir || noCollision)
		{
			ySpeed += 2000.0 * deltaTime;
		}	
	}
}
