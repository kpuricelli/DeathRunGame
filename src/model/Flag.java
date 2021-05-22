package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Flag extends Actor
{
	public Flag(float xPos, float yPos)
	{
		super(xPos, yPos);
		image = new Image("model/Flag.png", 40, 40, false, false);
		imageView = new ImageView(image);
		imageView.relocate(xPos, yPos);	
	}
}
