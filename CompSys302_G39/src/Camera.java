
public class Camera {
	private int MaxOffSetX;
	private int MaxOffSetY;
	private int MinOffSetX = 0;
	private int MinOffSetY = 0;
	private int CamX;
	private int CamY;
	
	public void Update(Player PlayerOne) {
		CamX = PlayerOne.GetX() - 1024/2;
		CamY = PlayerOne.GetY() - 768/2;
		
		if(CamX > MaxOffSetX) {
			CamX = MaxOffSetX;
		}
		else if(CamX < MinOffSetX) {
			CamX = 0;
		}
		else if(CamY > MaxOffSetY) {
			CamY = MaxOffSetY;
		}
		else if(CamY < MinOffSetY) {
			CamY = 0;
		}
	}
	
	//Setters
	public void SetMaxX(int X) {
		MaxOffSetX = X;
	}
	
	public void SetMaxY(int Y) {
		MaxOffSetY = Y;
	}
	
	//Getters
	public int GetCamX() {
		return CamX;
	}
	
	public int GetCamY() {
		return CamY;
	}
}
