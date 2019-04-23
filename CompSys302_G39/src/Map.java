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
	private int X=0,Y=0;
	private BufferedImage FullMap = new BufferedImage(3584,3072, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage Img1;
	private Graphics g = FullMap.getGraphics();
	private int Height=12;
	private int Width=14;
	private int dX,dY;
	private int[][] Map = {{0,0,0,0,0,0,0,0,0,1,2,3,3,3,3},
						   {0,0,0,0,0,0,0,0,0,1,2,3,3,3,3},
						   {0,0,0,0,0,0,0,0,0,1,0,3,3,3,3},
						   {0,0,0,0,0,0,0,0,0,1,0,3,3,3,3},
						   {0,0,0,0,0,0,0,0,0,1,2,3,3,3,3},
						   {0,0,0,0,0,0,0,0,0,1,2,3,3,3,3},
						   {0,0,0,0,0,0,0,0,0,1,2,3,3,3,3},
						   {0,0,0,0,0,0,0,0,0,1,2,3,3,3,3},
						   {0,0,0,0,0,0,0,0,0,1,2,3,3,3,3},
						   {0,0,0,0,0,0,0,0,0,1,2,3,3,3,3},
						   {0,0,0,0,0,0,0,0,0,1,2,3,3,3,3},
						   {0,0,0,0,0,0,0,0,0,1,2,3,3,3,3}};
	
	//Loads the whole map
	public void init(){
		for(int i =0; i < Height; i++) {
			for(int j = 0;j< Width; j++) {
				if(Map[i][j] == 0) {
					LoadImage("sprites/Grass1.png",i,j);
				}
				else if(Map[i][j] == 1) {
					LoadImage("sprites/Grass_Beach.png",i,j);
				}
				else if(Map[i][j] == 2) {
					LoadImage("sprites/Error.png",i,j);
				}
				else if(Map[i][j] == 3) {
					LoadImage("sprites/Water.png",i,j);
				}
			}
		}
	}
	
	

	//this needs to be changed
	public void Update(int dX, int dY) {
		X += dX;
		Y += dY;
		this.dX += dX;
		this.dY += dY;
	}
	
	//loads part of the map
	public BufferedImage LoadMap() {
		BufferedImage OnScreenMap = FullMap.getSubimage(X, Y, 500,500);
		return OnScreenMap;
	}
	
	public void LoadImage(String ImageName,int i, int j){
		try {
			Img1 = ImageIO.read(new File(ImageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image NewImg = Img1.getScaledInstance(256, 256, Image.SCALE_DEFAULT);
		g.drawImage(NewImg,j*256,i*256,null);
		//g.setColor(Color.black);
		//g.fillRect(i*10, j, 10, 10);
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
	
	public int GetDeltaX() {
		return dX;
	}
	
	public int GetDeltaY() {
		return dY;
	}
	
	public int GetMaxX() {
		return Width*256;
	}
	
	public int GetMaxY() {
		return Height*256;
	}
}
