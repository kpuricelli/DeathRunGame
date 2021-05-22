/**
 * Class representing a block in the game.
 * 
 * @author Kyle Puricelli
 * ITP 368, Spring 2019
 * Final Project
 * kpuricel@usc.edu
 * 
 * Used in GameView and Player for collisions.
 */

package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block extends Actor
{

	public Block(float xPos, float yPos)
	{
		super(xPos, yPos);
		image = new Image("model/Block.png");
		imageView = new ImageView(image);
		imageView.relocate(xPos, yPos);	
	}
}
