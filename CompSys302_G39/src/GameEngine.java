import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameEngine{
	//stores the current scene
	private ArrayList<Enemy> ListOfEnemies = new ArrayList<Enemy>();
	private ArrayList<Obstacle> ListOfObstacles = new ArrayList<Obstacle>();
	private Player PlayerOne = new Player();
	private Map MainMap = new Map();
	private Menu MainMenu = new Menu();
	private Menu OptionsMenu = new Menu();
	private Menu CurrentMenu = new Menu();
	
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
					CurrentMenu = MainMenu;	
				break;
			case OPTIONSMENU:
				CurrentMenu = OptionsMenu;
				break;
			case SCENE:
				System.out.println("nice");
				MainMap.Update(PlayerOne.GetX(), PlayerOne.GetY());
				//AddObstacle(100,100,0,0);
				//AddObstacle(100,100,500,0);
				break;
			case CLOSE:
				System.out.println("BYe");
				IsRunning = false;
				break;
		}
	}
	
	public void init() {
		MainMap.init();
		MainMenuInit();
		OptionsMenuInit();
	}
			
	private void MainMenuInit() {
		MainMenu.AddButton("Play", (500/2-50), 100);
		MainMenu.AddButton("Continue", (500/2-50), 200);
		MainMenu.AddButton("Options", (500/2-50), 300);
		MainMenu.AddButton("Exit", (500/2-50), 400);
		
		MainMenu.Select(0);
	}
	
	private void OptionsMenuInit() {
		OptionsMenu.AddButton("Option 1", (500/2-50), 100);
		OptionsMenu.AddButton("Option 2", (500/2-50), 200);
		OptionsMenu.AddButton("Option 3", (500/2-50), 300);
		OptionsMenu.AddButton("Back", (500/2-50), 400);
		
		OptionsMenu.Select(0);
	}
	
	private void Clear() {
		//clears the scene
		ListOfEnemies.clear();
		ListOfObstacles.clear();
	}
	
	public void SwitchButton(int Delta) {
		CurrentMenu.Select(CurrentMenu.GetSelected() + Delta);	
	}
	
	public void SelectButton() {
		switch (CurrentMenu.GetButton(CurrentMenu.GetSelected()).GetName()){
			case "Play":
				SetState(2);
			case "Continue":
				
			case "Options":
				SetState(1);
			case "Exit":
				SetState(3);
			case "Back":
				SetState(0);
		}
	}
	
	public void Update(float deltaTime) {
		//does nothing really
		//just calls run
		LastTime = CurrentTime;
		CurrentTime = (int)deltaTime;
		run();
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
	public Menu GetMainMenu() {
		return MainMenu;
	}
	
	public Menu GetOptionsMenu() {
		return OptionsMenu;
	}
	
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
	
	public BufferedImage Getlevel() {
		return MainMap.LoadMap();
	}
	
	public int GetState() {
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