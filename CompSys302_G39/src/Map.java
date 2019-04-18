import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Map {
	
	//private Image FullMap;
	private int W, H,X=0,Y=0;
	private BufferedImage FullMap = new BufferedImage(2560,2560, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage Img1;
	private Graphics g = FullMap.getGraphics();
	
	//if the screen is 1024 and the images are scaled to 256 
	//we have and extra 2 tiles of screen for a smooth experience
	private int Height=15;
	private int Width=15;
	private int[][] Map = new int[Width][Height];
	
	//Loads the hole map
	public void init(){
		for(int i =0; i < Width; i++) {
			for(int j = 0;j< Height; j++) {
				if((i%2 ==0)) {
					LoadImage("sprites/Grass1.png",i,j);
				}else {
					LoadImage("sprites/Water.png",i,j);
				}			
			}
		}
		//GetImageDimensions();
	}
	
	//this needs to be changed
	public void Update(int PX, int PY) {
		X = PX;
		Y = PY;
	}
	
	//loads part of the map
	public BufferedImage LoadMap() {
		BufferedImage OnScreenMap = FullMap.getSubimage(0 +X, Y, 500,500);
		return OnScreenMap;
	}
	
	public void LoadImage(String ImageName,int i, int j){
		//ImageIcon ImgIcon = new ImageIcon(ImageName);
		try {
			Img1 = ImageIO.read(new File(ImageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(Img1,j*32,i*32,null);
		//g.setColor(Color.black);
		//g.fillRect(i*10, j, 10, 10);
	}
	
	public void GetImageDimensions() {
		//W = Img.getWidth(null);
		//H = Img.getWidth(null);
	}
	
	public void SetX(int x) {
		X = x;
	}
	
	public void SetY(int y) {
		Y = y;
	}
	
	//getters
	
	public int GetX() {
		return X;
	}
	
	public int GetY() {
		return Y;
	}
}
