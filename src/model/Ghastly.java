package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ghastly extends Actor
{
	final private int xSpeed = -150;
	
	public Ghastly(float xPos, float yPos)
	{
		super(xPos, yPos);
		image = new Image("model/Ghastly.png", 100, 100, false, false);
		imageView = new ImageView(image);
		imageView.relocate(xPos, yPos);	
	}
	
	public void update(double deltaTime)
	{
		xPos += xSpeed * deltaTime;
		imageView.relocate(xPos, yPos);
	}
}
