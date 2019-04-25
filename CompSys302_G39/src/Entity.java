import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {
	protected int X;
	protected int Y;
	protected int W;
	protected int H;
	protected boolean exists;
	protected Rectangle Bounds;
	protected Rectangle SpecialBounds = null;
	private String Action;
	
	ArrayList<BufferedImage> SpriteList = new ArrayList<BufferedImage>();
	spriteLoader loadPlayer = new spriteLoader();
	Sprite Sprite = null;
	Animation Animation = null;
	
	public Entity() {
		exists = true;
		Bounds = new Rectangle(X,Y,W,H);
	}
	
	public void Move(int DeltaX,int DeltaY) {
		X += DeltaX;
		Y += DeltaY;
	}
	
	public Rectangle GetSpecialBounds() {
		return SpecialBounds;
	}
	
	public Rectangle GetBounds() {
		return Bounds;
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
	
	//Setters
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
		SpecialBounds = new Rectangle(X,Y,W,H);
	}
	
	public void SetBounds(int X,int Y,int W,int H) {
		Bounds = new Rectangle(X,Y,W,H);
	}
}