import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameEngine{
	//stores the current scene
	private ArrayList<Enemy> ListOfEnemies = new ArrayList<Enemy>();
	private ArrayList<Obstacle> ListOfObstacles = new ArrayList<Obstacle>();
	private Player PlayerOne = new Player();
		
	enum GameState{
		MAINMENU,
		OPTIONSMENU,
		SCENE,
		CLOSE
	}
		
	private GameState State = GameState.SCENE;
	private int CurrentTime = 0;
	private int LastTime = 0;
	private boolean IsRunning = true;
	
	public void run() {
		//selects what functions to run depending on the state
		switch (State){
			case MAINMENU:
				break;
			case OPTIONSMENU:
				break;
			case SCENE:
				AddObstacle(100,100,0,0);
				//AddObstacle(100,100,500,0);
				break;
			case CLOSE:
				IsRunning = false;
				break;
		}
	}
		
	public void Update(float deltaTime) {
		//does nothing really
		//just calls run
		LastTime = CurrentTime;
		CurrentTime = (int)deltaTime;
		run();
	}
	
	public void Clear() {
		//clears the scene
		ListOfEnemies.clear();
		ListOfObstacles.clear();
	}
	
	public void MovePlayer(int DeltaX,int DeltaY) {
		PlayerOne.Move(DeltaX, DeltaY);
	}
	//adds entities
	private void AddEnemy() {
		ListOfEnemies.add(new MeleeEnemy());
	}
	
	private void AddObstacle(int W, int H, int X, int Y) {
		ListOfObstacles.add(new Obstacle(W,H,X,Y));
	}
	
	private void AddPlayer() {
		PlayerOne = new Player();
	}
	
	//setters
	public void SetState(int i) {
		if(i == 0) {		
			State = GameState.MAINMENU;
		}
		else if(i == 1) {
			State = GameState.OPTIONSMENU;
		}
		else if(i == 2) {
			State =GameState.SCENE;
		}
		else {
			State =GameState.CLOSE;
		}
	}
	
	public void Close() {
		IsRunning = false;
	}
	
	//getter
	
	public boolean IsRunning() {
		return IsRunning;
	}
	public Enemy GetEnemy(int i) {
		return ListOfEnemies.get(i);
	}
		
	public Obstacle GetObstacle(int i) {
		return ListOfObstacles.get(i);
	}
	
	public int GetNumberOfEnemies() {
		return ListOfEnemies.size();
	}
		
	public int GetNumberOfObstacles() {
		return ListOfObstacles.size();
	}
	
	public Player GetPlayer() {
		return PlayerOne;
	}
	
	public int GetGameState() {
		switch (State){
		case MAINMENU:
			return 0;
		case OPTIONSMENU:
			return 1;
		case SCENE:
			return 2;
		case CLOSE:
			return 3;
		}
		return -1;
	}
}