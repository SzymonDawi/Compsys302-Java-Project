import java.awt.Image;

import javax.swing.ImageIcon;

public class Map {
	
	private Image Img;
	private int W, H,X,Y;
	
	public void init(){
		LoadImage("sprites/Beach_1.png");
		GetImageDimensions();
	}
	
	public void LoadImage(String ImageName) {
		ImageIcon ImgIcon = new ImageIcon(ImageName);
		Img = ImgIcon.getImage().getScaledInstance(256,256, Image.SCALE_DEFAULT);
	}
	
	public void GetImageDimensions() {
		W = Img.getWidth(null);
		H = Img.getWidth(null);
	}
	
	public void SetX(int x) {
		X = x;
	}
	
	public void SetY(int y) {
		Y = y;
	}
	
	//getters
	public Image GetImage() {
		return Img;
	}
	
	public int GetX() {
		return X;
	}
	
	public int GetY() {
		return Y;
	}
}
