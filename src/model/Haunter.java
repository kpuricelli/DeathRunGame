package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Haunter extends Actor
{
	final private int xSpeed = 200;
	
	public Haunter(float xPos, float yPos)
	{
		super(xPos, yPos);
		image = new Image("model/Haunter.png", 512, 512, false, false);
		imageView = new ImageView(image);
		imageView.relocate(xPos, yPos);	
	}
	
	public void update(double deltaTime)
	{
		xPos += xSpeed * deltaTime;
		imageView.relocate(xPos, yPos);
	}
}
