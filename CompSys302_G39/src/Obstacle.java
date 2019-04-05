
public class Obstacle {
	private int Width;
	private int Height;
	private int[] Position = {0,0};//x,y
	
	Obstacle(int W,int H,int X, int Y){
		Width = W;
		Height = H;
		Position[0] = X;
		Position[1] = Y;
	}
	
	//setter 
	public void SetPosition(int x,int y) {
		Position[0] = x;
		Position[1] = y;
	}
	
	//getters
	public int GetWidth() {
		return Width;
	}
	
	public int GetHeight() {
		return Height;
	}
	
	public int GetX() {
		return Position[0];
	}
	
	public int GetY() {
		return Position[1];
	}
}
