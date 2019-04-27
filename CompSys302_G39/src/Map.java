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
	private BufferedImage FullMap = new BufferedImage(3840,3072, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage Img1;
	private Graphics g = FullMap.getGraphics();
	private int Height=12;
	private int Width=15;
	private int dX,dY;

	public void LoadTile(String s, int X, int Y, int Scale) {
		try {
			Img1 = ImageIO.read(new File("sprites/" + s +".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(s.compareTo("Bridge")==0) {
			Image NewImg = Img1.getScaledInstance(256, 512, Image.SCALE_DEFAULT);
			g.drawImage(NewImg,X,Y,null);
		}
		else if(Scale != 0) {
			Image NewImg = Img1.getScaledInstance(Scale, Scale, Image.SCALE_DEFAULT);
			g.drawImage(NewImg,X,Y,null);
		}
		else {
			g.drawImage(Img1,X,Y,null);
		}
	}
	
	public void Update(int dX, int dY) {
		X += dX;
		Y += dY;
		this.dX += dX;
		this.dY += dY;
	}
	
	//loads part of the map
	public BufferedImage LoadMap() {
		BufferedImage OnScreenMap = FullMap.getSubimage(X, Y, 1024,768);
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
	
	//Loads the whole map
		public void init(int Map[][],int W,int H){
			Width = W;
			Height = H;
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
					else if(Map[i][j] == 4) {
						LoadImage("sprites/Sand.png",i,j);
					}
					else if(Map[i][j] == 5) {
						LoadImage("sprites/River_Ocean_2.png",i,j);
					}
					else if(Map[i][j] == 6) {
						LoadImage("sprites/River_Ocean_1.png",i,j);
					}
					else if(Map[i][j] == 7) {
						LoadImage("sprites/River_Mouth_6.png",i,j);
					}
					else if(Map[i][j] == 8) {
						LoadImage("sprites/River_Mouth_7.png",i,j);
					}
					else if(Map[i][j] == 9) {
						LoadImage("sprites/River_Mouth_4.png",i,j);
					}
					else if(Map[i][j] == 10) {
						LoadImage("sprites/River_Mouth_5.png",i,j);
					}
					else if(Map[i][j] == 11) {
						LoadImage("sprites/River_Mouth_2.png",i,j);
					}
					else if(Map[i][j] == 12) {
						LoadImage("sprites/River_Mouth_1.png",i,j);
					}
					else if(Map[i][j] == 13) {
						LoadImage("sprites/River_Edge_Front4.png",i,j);
					}
					else if(Map[i][j] == 14) {
						LoadImage("sprites/River_Edge_Back2.png",i,j);
					}
					else if(Map[i][j] == 15) {
						LoadImage("sprites/River_Edge_Front2.png",i,j);
					}
					else if(Map[i][j] == 16) {
						LoadImage("sprites/River_Edge_Back3.png",i,j);
					}
					else if(Map[i][j] == 17) {
						LoadImage("sprites/River_Edge_Front3.png",i,j);
					}
					else if(Map[i][j] == 18) {
						LoadImage("sprites/River_Edge_Back1.png",i,j);
					}
					else if(Map[i][j] == 19) {
						LoadImage("sprites/River_Edge_Front1.png",i,j);
					}
					else if(Map[i][j] == 31) {
						LoadImage("sprites/Beach_1.png",i,j);
					}
					else if(Map[i][j] == 32) {
						LoadImage("sprites/Beach2.png",i,j);
					}
					else if(Map[i][j] == 33) {
						LoadImage("sprites/Beach3.png",i,j);
					}
				}
			}
		}
}