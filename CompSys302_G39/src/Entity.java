import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {
	protected int X;
	protected int Y;
	protected int W;
	protected int H;
	
	protected int DX;
	protected int DY;
	
	//this is the collision bounds
	protected int BoundsX;
	protected int BoundsY;
	protected int BoundsW;
	protected int BoundsH;
	
	//this is the bounds for a special action i.e. attack
	protected int SpecialX;
	protected int SpecialY;
	protected int SpecialW;
	protected int SpecialH;
	
	protected boolean exists;
	private String Action;
	
	ArrayList<BufferedImage> SpriteList = new ArrayList<BufferedImage>();
	spriteLoader loadPlayer = new spriteLoader();
	Sprite Sprite = null;
	Animation Animation = null;
	
	public Entity() {
		exists = true;
	}
	
	public void Move(int DeltaX,int DeltaY) {
		X += DeltaX;
		Y += DeltaY;
	}
	
	public void Move() {
		X += DX;
		Y += DY;
		DX = 0;
		DY = 0;
	}
	
	public Rectangle GetSpecialBounds() {
		return new Rectangle(X+SpecialX,Y+SpecialY,SpecialW,SpecialH);
	}
	
	public Rectangle GetBounds() {
		return new Rectangle(X+BoundsX,Y+BoundsY,BoundsW,BoundsH);
	}
	
	public Image GetCurrentSprite() { 
		return Sprite.getSprite(0, 0, W, H);
	}
	
	public String GetAciton() {
		return Action;
	}
	
	protected int GetX() {
		return X;
	}
	
	protected int GetY() {
		return Y;
	}
	
	public int GetWidth() {
		return W;
	}
	
	public int GetHeight() {
		return H;
	}

	public boolean getExists() {
		return exists;
	}
	
	public int GetDX() {
		return DX;
	}
	
	public int GetDY() {
		return DY;
	}
	
	//Setters
	public void SetDX(int X) {
		DX =X;
	}
	
	public void SetDY(int Y) {
		DY =Y;
	}
	
	public void setX(int newX) {
		X = newX;
	}
	
	public void setY(int newY) {
		Y = newY;
	}
	
	public void setExists(boolean newExists) {
		 exists = newExists;
	}
	
	public void SetAction(String s) {
		Action = s;
	}
	
	public void SetSpecialBounds(int X,int Y,int W,int H) {
		SpecialX = X;
		SpecialY = Y;
		SpecialW = W;
		SpecialH = H;
	}
	
	public void SetBounds(int X,int Y,int W,int H) {
		BoundsX = X;
		BoundsY = Y;
		BoundsW = W;
		BoundsH = H;
	}
}