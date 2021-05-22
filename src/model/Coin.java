/**
 * Class representing a coin in the game.
 * 
 * @author Kyle Puricelli
 * ITP 368, Spring 2019
 * Final Project
 * kpuricel@usc.edu
 * 
 * Used in GameView and Player for collisions.
 * Used in GameView to keep track of how many coins a player has accumulated
 */

package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Coin extends Actor
{
	public Coin(float xPos, float yPos)
	{
		super(xPos, yPos);
		image = new Image("model/Coin.png");
		imageView = new ImageView(image);
		imageView.relocate(xPos, yPos);	
	}
	
	public void removeImage()
	{
		imageView.relocate(0, 0);
	}
}
