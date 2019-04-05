import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;

public class GameEngine {
		private ArrayList<Enemy> ListOfEnemies = new ArrayList<Enemy>();
		private ArrayList<Obstacle> ListOfObstacles = new ArrayList<Obstacle>();
		private ArrayList<JButton> ListOfButtons = new ArrayList<JButton>();
		
		enum GameState{
			MENU,
			SCENE,
			CLOSE
		}
		
		private GameState State = GameState.MENU;
		private int CurrentTime = 0;
		private int LastTime = 0;
		
	public void run() {
		Clear();
		
	}
	
	public void Update(float deltaTime) {
		LastTime = CurrentTime;
		CurrentTime = (int)deltaTime;
		run();
	}
	
	public void Clear() {
		ListOfEnemies.clear();
		ListOfObstacles.clear();
	}
	
	public void GameInit() {
		MainMenu();
	}
	
	public void ButtonPressed(String Button) {
		if(Button.compareTo("Start") == 0 ) {
			State = GameState.SCENE;
		}
		else if(Button.compareTo("Continue") == 0 ){
			
		}
		else if(Button.compareTo("Options") == 0 ){
			OptionsMenu();
		}
		else if(Button.compareTo("Exit") == 0 ){
			
		}
		else {
			
		}
	}
	
	//Menus
	private void MainMenu() {
		AddButton("Start", 100, 40, 50, 100);
		AddButton("Continue", 100, 40, 100, 100);
		AddButton("Options", 100, 40, 150, 100);
		AddButton("Exit", 100, 40, 200, 100);
	}
	
	private void OptionsMenu() {
		AddButton("thing2", 100, 40, 50, 100);
		AddButton("thing3", 100, 40, 100, 100);
		AddButton("thing4", 100, 40, 150, 100);
	}
	
	
	//adds entities and menu items
	private void AddEnemy() {
		ListOfEnemies.add(new MeleeEnemy());
	}
	
	private void AddObstacle(int W, int H, int X, int Y) {
		ListOfObstacles.add(new Obstacle(W,H,X,Y));
	}
	
	private void AddButton(String Text, int W, int H, int X, int Y) {
		JButton b = new JButton(Text);
		b.setName(Text);
		b.setAlignmentX(b.CENTER_ALIGNMENT);
		ListOfButtons.add(b);
	}
	
	//getter
	public Enemy GetEnemy(int i) {
		return ListOfEnemies.get(i);
	}
		
	public Obstacle GetObstacle(int i) {
		return ListOfObstacles.get(i);
	}
	
	public JButton GetButton(int i) {
		return ListOfButtons.get(i);
	}
		
	public int GetNumberOfEnemies() {
		return ListOfEnemies.size();
	}
		
	public int GetNumberOfObstacles() {
		return ListOfObstacles.size();
	}
	
	public int GetNumberOfButtons() {
		return ListOfButtons.size();
	}
}