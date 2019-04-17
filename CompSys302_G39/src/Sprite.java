import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {
	private static BufferedImage SpriteSheet;
	
	public static BufferedImage LoadSprite(String File) {
		BufferedImage Sprite = null;
		
		try {
			Sprite = ImageIO.read(new File("Sprites/"+File+".png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return Sprite;
	}
	
	public static BufferedImage GetSprite(int GridX, int GridY) {
		if(SpriteSheet == null) {
			SpriteSheet = LoadSprite("Sprites/Error.png");
		}
		return SpriteSheet.getSubimage(GridX * 32, GridY *32, 32,32);
	}
}
