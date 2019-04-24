import java.awt.Rectangle;

public class Entity {
	protected int X;
	protected int Y;
	protected int W;
	protected int H;
	protected boolean exists;
	

	public void Move(int DeltaX,int DeltaY) {
		X += DeltaX;
		Y += DeltaY;
	}
	
	public Rectangle GetBounds() {
		return new Rectangle(X,Y,W,H);
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
	
	public void setX(int newX) {
		X = newX;
	}
	
	public void setY(int newY) {
		Y = newY;
	}
	
	public void setExists(boolean newExists) {
		 exists = newExists;
	}
	
	public Entity() {
		exists = true;
	}
}
