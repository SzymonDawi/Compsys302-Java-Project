
public class Projectile extends Entity{
	private boolean Friendly;
	
	public Projectile(boolean Friendly, int X, int Y, int W, int H) {
		this.Friendly = Friendly;
		this.W = W;
		this.H = H;
		this.X = X;
		this.Y = Y;
	}	
	
	//if it can hit the player
	public boolean getFriendly() {
		return Friendly;
	}
	
	@Override
	public void Move() {
		X += DX;
		Y += DY;
	}
}
