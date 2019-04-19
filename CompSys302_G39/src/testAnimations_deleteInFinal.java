import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

public class testAnimations_deleteInFinal extends JFrame{
	BufferedImage displaySprite;
	Animation attackBack;
	
	public testAnimations_deleteInFinal() {
		setSize(800,600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		init();
	}
	
	private void init() {
		spriteLoader loadedSprite = new spriteLoader();
		BufferedImage sprite = null;
		
		sprite = loadedSprite.loadSprite("Player");  //change this to change sprite  sheet
		
		Sprite testSprite = new Sprite(sprite);
		
		displaySprite = testSprite.getSprite(64, 0, 64, 64); // test: loads a single sprite
		
		ArrayList<BufferedImage> attackBackSprites = new ArrayList<BufferedImage>(); //a list of all sprites used in the animation
		
		attackBackSprites.add(testSprite.getSprite(0, 59, 59, 64));  //frame 1
		attackBackSprites.add(testSprite.getSprite(59, 59, 59, 64)); //frame 2
		attackBackSprites.add(testSprite.getSprite(117, 59, 59, 64)); //frame 3
		//attackBackSprites.add(testSprite.getSprite(176, 59, 59, 64)); //frame 4
		
		
		
		attackBack = new Animation(attackBackSprites); //bundles the individual sprites into an animation array
		attackBack.setSpeed(200); //animation seed delay in ms
		attackBack.start();
		
	}
	
	Image dbImage;
	Graphics dgb;
	
	@Override
	public void paint(Graphics testCase) {
		dbImage = createImage(getWidth(),getHeight());
		dgb = dbImage.getGraphics();
		paintSprite(dgb);
		testCase.drawImage(dbImage,0,0,null);
	}
	
	public void paintSprite (Graphics testCase){
		if(attackBack != null) {
			attackBack.update(System.currentTimeMillis());
			testCase.drawImage(attackBack.Sprite, 300,300, 100,100,null);
			
		}
		repaint();
	}
	
	public static void main(String[] args) {
		testAnimations_deleteInFinal Test = new testAnimations_deleteInFinal();
	}
}
