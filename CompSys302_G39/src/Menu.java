import java.util.ArrayList;

public class Menu{
	private ArrayList<Button> ListOfButtons = new ArrayList<Button>();
	private int Selected;
	private int Location;
	
	public void AddButton(String Text, int X, int Y) {
		Button B = new Button(Text,X,Y);
		ListOfButtons.add(B);
	}
	
	private void ButtonSelected() {
		for(int i =0; i < ListOfButtons.size(); i++) {
			if(ListOfButtons.get(i).Selected()) {
				Selected = i;
				System.out.println(i);
				return;
			}
		}
		Selected = -1;
	}
	
	public void Select(int Location) { 
		ButtonSelected();
		if(Selected >= 0) {
			ListOfButtons.get(Selected).ToggleSelect();	
		}
		ListOfButtons.get(Location).ToggleSelect();	
	}
	
	//getters
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
