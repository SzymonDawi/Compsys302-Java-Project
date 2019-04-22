import java.awt.Rectangle;

public class Entity {
	protected int X;
	protected int Y;
	protected int W;
	protected int H;

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

}
