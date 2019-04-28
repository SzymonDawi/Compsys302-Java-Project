import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//Loads Sprite sheet
public class spriteLoader {
	public BufferedImage loadSprite(String file) {
		BufferedImage spriteSheet = null;
		
		if(file == "0") {
			return spriteSheet;
		}
		
		try {
			spriteSheet = ImageIO.read(new File("sprites/" + file + ".png"));
		} catch (IOException e) {

		}
		return spriteSheet;
	}
}
