import java.awt.image.BufferedImage;



public class Sprite {
	public BufferedImage Sprite;
	
	public Sprite(BufferedImage spriteSheet) {
		this.Sprite = spriteSheet;
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		if(Sprite == null) {
			return null;
		}
		BufferedImage currentSprite = Sprite.getSubimage(x, y, width,  height);
		return currentSprite;
	}
}
