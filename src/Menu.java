import java.util.ArrayList;
//this describes the menu behavior
//it allows for selecting different buttons
public class Menu{
	private ArrayList<Button> ListOfButtons = new ArrayList<Button>();

	private int Selected;
	
	public void AddButton(String Text, int X, int Y) {
		Button B = new Button(Text,X,Y);
		ListOfButtons.add(B);
	}

	public void Select(int Location) { 	
		//ButtonSelected();
		if(Selected >= 0) {
			ListOfButtons.get(Selected).Deselect();	
		}
		ListOfButtons.get(Location).Select();
		Selected = Location;
	}
	
	//getters
	public String CurrentButtonName() {
		return ListOfButtons.get(Selected).GetName();
	}
	public int GetSelected() {
		return Selected;
	}
	public int GetNumberOfButtons() {
		return ListOfButtons.size();
	}
	
	public Button GetButton(int i) {
		return ListOfButtons.get(i);
	}	
}
