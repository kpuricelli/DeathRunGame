/**
 * Abstract class representing every base object in the game.
 * 
 * @author Kyle Puricelli
 * ITP 368, Spring 2019
 * Final Project
 * kpuricel@usc.edu
 * 
 * Used in GameView.
 */
package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Actor
{
	protected double xPos;
	protected double yPos;
	protected Image image;
	protected ImageView imageView;
	
	public Actor(float xPos, float yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public ImageView getImageView()
	{
		return imageView;
	}
	
	public double getXPos()
	{
		return xPos;
	}
}
