import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;

public class GameEngine {
		private ArrayList<Enemy> ListOfEnemies = new ArrayList<Enemy>();
		private ArrayList<Obstacle> ListOfObstacles = new ArrayList<Obstacle>();
		
		enum GameState{
			MAINMENU,
			OptionsMENU,
			SCENE,
			CLOSE
		}
		
		private GameState State = GameState.MAINMENU;
		private int CurrentTime = 0;
		private int LastTime = 0;
		
	public void run() {
		AddObstacle(100,100,0,0);
		switch (State){
			case MAINMENU:
				break;
			case OptionsMENU:
				break;
			case SCENE:
				break;
			case CLOSE:
				break;
		}
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
	
	public void ButtonPressed(String Button) {
		
		if(Button.compareTo("Start") == 0 ) {
			State = GameState.SCENE;
		}
		else if(Button.compareTo("Continue") == 0 ){
			
		}
		else if(Button.compareTo("Options") == 0 ){
			State = GameState.OptionsMENU;
		}
		else if(Button.compareTo("Exit") == 0 ){
			
		}
		else if(Button.compareTo("Back") == 0 ){
			State = GameState.MAINMENU;
		}
	}
	
	//adds entities and menu items
	private void AddEnemy() {
		ListOfEnemies.add(new MeleeEnemy());
	}
	
	private void AddObstacle(int W, int H, int X, int Y) {
		ListOfObstacles.add(new Obstacle(W,H,X,Y));
	}
	
	//getter
	public Enemy GetEnemy(int i) {
		return ListOfEnemies.get(i);
	}
		
	public Obstacle GetObstacle(int i) {
		return ListOfObstacles.get(i);
	}
	
	//public JButton GetButton(int i) {
		//return ListOfButtons.get(i);
	//}
		
	public int GetNumberOfEnemies() {
		return ListOfEnemies.size();
	}
		
	public int GetNumberOfObstacles() {
		return ListOfObstacles.size();
	}
	
	public int GetGameState() {
		switch (State){
		case MAINMENU:
			return 0;
		case OptionsMENU:
			return 1;
		case SCENE:
			return 3;
		case CLOSE:
			return 4;
		}
		return 0;
	}
}