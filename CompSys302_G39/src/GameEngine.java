import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameEngine{
	//stores the current scene
	private String previousKeyPress = "S";
	private ArrayList<Enemy> ListOfEnemies = new ArrayList<Enemy>();
	private ArrayList<Obstacle> ListOfObstacles = new ArrayList<Obstacle>();
	private Player PlayerOne = new Player();
	private Map MainMap = new Map();
	
	private Menu MainMenu = new Menu();
	private Menu SoundMenu = new Menu();
	private Menu OptionsMenu = new Menu();
	private Menu CurrentMenu = new Menu();
	
	private Sound buttonClick = new Sound();
	private Sound buttonSwitch = new Sound();
	private Sound MMMusic = new Sound();
	private float prevousVolume;
	
	private GameState State;
	private GameState previousState;
	private Physics Physics = new Physics(ListOfEnemies,ListOfObstacles,PlayerOne,MainMap);
	
	enum GameState{
		MAINMENU,
		OPTIONSMENU,
		SOUNDMENU,
		SCENE,
		CLOSE
	}
	
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
				previousState =  GameState.MAINMENU;
				CurrentMenu = OptionsMenu;
				break;
			case SOUNDMENU:
				previousState =  GameState.OPTIONSMENU;
				CurrentMenu = SoundMenu;
				break;
			case SCENE:
				//AddObstacle(100,100,50,50);
				//AddObstacle(100,100,500,0);
				break;
			case CLOSE:
				IsRunning = false;
				break;
		}
	}
	
	public void init() {
		MainMap.init();
		MainMenuInit();
		OptionsMenuInit();
		SoundMenuInit();
		buttonClick.getSound("beep");
		buttonSwitch.getSound("switchButton");
		MMMusic.getSound("menu");
		MMMusic.loopSound(Clip.LOOP_CONTINUOUSLY);
		MMMusic.setVol( 0.50);
		State = GameState.MAINMENU;
		AddObstacle(100,100,100,100);
	}
			
	private void MainMenuInit() {
		MainMenu.AddButton("Play", (500/2-50), 100);
		MainMenu.AddButton("Continue", (500/2-50), 200);
		MainMenu.AddButton("Options", (500/2-50), 300);
		MainMenu.AddButton("Exit", (500/2-50), 400);
		
		MainMenu.Select(0);
	}
	
	private void OptionsMenuInit() {
		OptionsMenu.AddButton("Sound settings", (500/2-50), 100);
		OptionsMenu.AddButton("Option 2", (500/2-50), 200);
		OptionsMenu.AddButton("Option 3", (500/2-50), 300);
		OptionsMenu.AddButton("Back", (500/2-50), 400);
		
		OptionsMenu.Select(0);
	}
	
	private void SoundMenuInit() {
		SoundMenu.AddButton("<-", (500/4-25), 100);
		SoundMenu.AddButton("->", (500-100), 100);
		SoundMenu.AddButton("Mute", (500/2-50), 200);
		SoundMenu.AddButton("Back", (500/2-50), 300);
		
		SoundMenu.Select(0);
	}
	
	private void Clear() {
		//clears the scene
		ListOfEnemies.clear();
		ListOfObstacles.clear();
	}
	
	public void SwitchButton(int Delta) {
		buttonSwitch.playSound();
		if(CurrentMenu.GetSelected() == 0 && Delta <0) {
			return;
		}
		else if(CurrentMenu.GetSelected() == CurrentMenu.GetNumberOfButtons()-1 && Delta >0) {
			return;
		}
		CurrentMenu.Select(CurrentMenu.GetSelected() + Delta);
	}
	
	public void SelectButton() {
		buttonClick.playSound();
		switch (CurrentMenu.CurrentButtonName()){
		
			case "Play":
				State = GameState.SCENE;
				MMMusic.stopSound();
				break;
			case "Continue":
				break;
			case "Options":
				State = GameState.OPTIONSMENU;
				break;
				
			case "Sound settings":
				State = GameState.SOUNDMENU;
				break;
				
			case "Exit":
				State = GameState.CLOSE;
				break;
				
			case "Back":
				State = previousState;
				break;
		
			case "<-":
				if (MMMusic.isMute() == false) {
				MMMusic.setVol(MMMusic.getVol() -0.02);
				}
				break;
			
			case "->":
				if (MMMusic.isMute() == false) {
				MMMusic.setVol(MMMusic.getVol() +0.02);
				}
				break;
				
			case "Mute":
				if (MMMusic.isMute() == false) {
					MMMusic.setVol(prevousVolume);
					MMMusic.Mute(true);
					MMMusic.stopSound();
				} else {
					MMMusic.loopSound(Clip.LOOP_CONTINUOUSLY);
					MMMusic.setVol(prevousVolume);
					MMMusic.Mute(false);
				}
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
	
	public void MovePlayer(int DeltaX,int DeltaY) {
		boolean MoveMap = false;
		Rectangle Rect = new Rectangle(PlayerOne.GetX() + DeltaX, PlayerOne.GetY() + DeltaY, 10,10);
				if(Rect.intersects(new Rectangle(9,9,480,445))){
					if(!Physics.PlayerCollisions(DeltaX, DeltaY)) {
						PlayerOne.Move(DeltaX, DeltaY);
					}
				}
				else {
					MoveMap = true;
				}
				
				if(MoveMap && !Physics.PlayerCollisions(DeltaX, DeltaY)) {
					MoveObstacles(DeltaX,DeltaY);
					MainMap.Update(DeltaX,DeltaY);
				}
	}
	
	private void MoveObstacles(int DeltaX, int DeltaY) {
		for(int i = 0; i < ListOfObstacles.size(); i++) {
			Obstacle O = ListOfObstacles.get(i);
			O.Move(-DeltaX, -DeltaY);
		}
	}
	
	//adds entities
	private void AddEnemy() {
		ListOfEnemies.add(new MeleeEnemy());
	}
	
	private void AddObstacle(int W, int H, int X, int Y) {
		ListOfObstacles.add(new Obstacle(W,H,X,Y));
	}
	
	public String getVolume() {
		int volumePercent = (int) (MMMusic.getVol()*100);
		if(MMMusic.isMute()) {
			return "0";
		}
		return Integer.toString(volumePercent);
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
		else if(i == 3) {
			State =GameState.SOUNDMENU;
		}
		else {
			State =GameState.CLOSE;
		}
	}
	
	public void SetCurrentPlayerDirrection(String keyLog) {
		 previousKeyPress = keyLog;
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
	
	public Menu GetSoundMenu() {
		return SoundMenu;
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
	
	public Map Getlevel() {
		return MainMap;
	}
	
	public String GetCurrentPlayerDirrection() {
		return previousKeyPress;
	}
	
	public int GetState() {
		switch (State){
		case MAINMENU:
			return 0;
		case OPTIONSMENU:
			return 1;
		case SCENE:
			return 2;
		case SOUNDMENU:
			return 3;
		case CLOSE:
			return 4;
		}
		return -1;
	}
}