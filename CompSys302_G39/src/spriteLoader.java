import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class spriteLoader {
	
	public BufferedImage loadSprite(String file) {
		BufferedImage spriteSheet = null;
		
		try {
			spriteSheet = ImageIO.read(new File("sprites/" + file + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return spriteSheet;
	}
}
