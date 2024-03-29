//This is a basic button class
public class Button {
	private int X;
	private int Y;
	private String Name;
	private boolean IsSelected=false;
	
	public Button(String Text, int X, int Y) {
		Name = Text;
		this.X = X;
		this.Y = Y;
	}
	
	public void Select() {
		IsSelected = true;
	}
	
	public void Deselect() {
		IsSelected = false;
	}
	
	//Getters
	public boolean Selected() {
		return IsSelected;
	}
	
	public String GetName() {
		return Name;
	}
	
	public int GetX() {
		return X;
	}
	
	public int GetY() {
		return Y;
	}
}
