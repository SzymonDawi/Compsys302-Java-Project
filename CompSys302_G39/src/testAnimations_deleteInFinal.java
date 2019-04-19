import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

public class testAnimations_deleteInFinal extends JFrame{
	BufferedImage displaySprite;
	
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
		
		displaySprite = testSprite.getSprite(64, 0, 64, 64);
		
	}
	
	@Override
	public void paint(Graphics testCase) {
		testCase.drawImage(displaySprite,100,100, null);
		repaint();
	}
	
	public static void main(String[] args) {
		testAnimations_deleteInFinal Test = new testAnimations_deleteInFinal();
	}
}
