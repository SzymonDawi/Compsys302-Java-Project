import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameEngine{
	//stores the current scene
	private boolean tick= false;
	private String currentKeyPress = "Forward";
	private boolean isPlayerAttacking = false;
	private boolean standingStill = true;
	private String randomiseFile;
	private int currentGameScore;  //do not make this private (yet)
	private ArrayList<Enemy> ListOfEnemies = new ArrayList<Enemy>();
	private ArrayList<Obstacle> ListOfObstacles = new ArrayList<Obstacle>();
	private ArrayList<pickups> ListOfPickups = new ArrayList<pickups>();
	private Player PlayerOne = new Player();
	private ScoreSaver ScoreEngine = new ScoreSaver();
	
	private boolean CentreMap = false;
	private Map MainMap = new Map();
	private Map House1Map = new Map();
	private Map CurrentMap;
	private String PreviousMap;
	
	private Menu MainMenu = new Menu();
	private Menu SoundMenu = new Menu();
	private Menu ScoreMenu = new Menu();
	private Menu PauseMenu = new Menu();
	private Menu OptionsMenu = new Menu();
	private Menu CurrentMenu = new Menu();
	private Menu CloseMenu = new Menu();
	private boolean LoadingMenu;
	
	private Sound buttonClick = new Sound();
	private Sound buttonSwitch = new Sound();
	private Sound swapWeapon = new Sound();
	private Sound MMMusic = new Sound();
	private Sound meleeAttack = new Sound();
	private Sound rangedAttack = new Sound();
	private Sound enterHouse = new Sound();
	private Sound pickupItem = new Sound();
	private Sound noAmmo = new Sound();
	private Sound playerDetected = new Sound();
	private float prevousVolume;
	
	private GameState State;
	private GameState previousState;
	private Physics Physics = new Physics();
	private boolean OtherCollision = false;
	
	enum GameState{
		MAINMENU,
		OPTIONSMENU,
		SOUNDMENU,
		SCOREMENU,
		SCENE,
		PAUSED,
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
				if(LoadingMenu) {
					CurrentMenu.Select(0);
					LoadingMenu = false;
				}
				break;
			case OPTIONSMENU:
				previousState =  GameState.MAINMENU;
				CurrentMenu = OptionsMenu;
				if(LoadingMenu) {
					CurrentMenu.Select(0);
					LoadingMenu = false;
				}
				break;
			case SOUNDMENU:
				previousState =  GameState.OPTIONSMENU;
				CurrentMenu = SoundMenu;
				if(LoadingMenu) {
					CurrentMenu.Select(0);
					LoadingMenu = false;
				}
				break;
			case SCOREMENU:
				previousState =  GameState.MAINMENU;
				CurrentMenu = ScoreMenu;
				if(LoadingMenu) {
					CurrentMenu.Select(0);
					LoadingMenu = false;
				}
				break;
			case SCENE:
				Physics.Update(this);
				OtherCollision = Physics.OtherCollisions();
				
				if(tick) {
				}
				//AddObstacle(100,100,50,50);
				//AddObstacle(100,100,1024,0);
				break;
				
			case PAUSED:
				previousState =  GameState.SCENE;
				CurrentMenu = PauseMenu;
				if(LoadingMenu) {
					CurrentMenu.Select(0);
					LoadingMenu = false;
				}
				break;
				
			case CLOSE:
				previousState =  GameState.SCENE;
				CurrentMenu = CloseMenu;
				if(LoadingMenu) {
					CurrentMenu.Select(0);
					LoadingMenu = false;
				}
				break;
		}
	}
	
	public void init() {
		MainMenuInit();
		OptionsMenuInit();
		ScoreMenuInit();
		SoundMenuInit();
		PauseMenuInit();
		CloseMenuInit();
		
		AddPickup("coin", 500, 500);
		AddPickup("coin", 460, 500);
		AddPickup("coin", 460, 460);
		AddEnemy(470, 510);
		AddEnemy(200, 700);
		
		MainMapInit();
		CurrentMap = MainMap;
		
		buttonClick.getSound("beep");
		buttonSwitch.getSound("switchButton");
		swapWeapon.getSound("Player_Swap_Weapons");
		rangedAttack.getSound("Player_Ranged_Attack");
		meleeAttack.getSound("Player_Melee_Attack");
		pickupItem.getSound("pickup");
		enterHouse.getSound("EnterDoor");
		noAmmo.getSound("noAmmo");
		MMMusic.getSound("MMMusic_forgotten-toys");
		MMMusic.loopSound(Clip.LOOP_CONTINUOUSLY);
		MMMusic.setVol( 0.50);
		State = GameState.MAINMENU;
		currentGameScore = 0;
		
	}
	
	private void MainMapInit() {
		
		AddObstacle("House_1",174,132,100,100,1);
		AddObstacle("0", 50, 50, 504, 590, 0);

		int[][] Map = {
					{0,0,0,0,0,0,0,0,1,4,2,3,3,3,3},
					{0,0,0,0,0,0,0,0,1,4,2,3,3,3,3},
					{0,0,0,18,14,16,14,12,10,8,6,3,3,3,3},
					{0,0,0,19,17,15,13,11,9,7,5,3,3,3,3},
					{0,0,0,0,0,0,0,0,1,4,2,3,3,3,3},
					{0,0,0,0,0,0,0,0,1,4,2,3,3,3,3},
					{0,0,0,0,0,0,0,0,1,4,2,3,3,3,3},
					{0,0,0,0,0,0,0,0,1,4,2,3,3,3,3},
					{0,0,0,0,0,0,0,0,1,4,2,3,3,3,3},
					{0,0,0,0,0,0,0,0,1,4,2,3,3,3,3},
					{0,0,0,0,0,0,0,0,1,4,2,3,3,3,3},
					{0,0,0,0,0,0,0,0,1,4,2,3,3,3,3}};
		
		MainMap.init(Map,15,12);
		MainMap.LoadTile("Path_Up", 118, 232, 64);
		MainMap.LoadTile("Path_Up", 118, 296, 64);
		MainMap.LoadTile("Path_Up", 118, 360, 64);
	}
	
	private void House1MapInit() {
		PreviousMap = "MainMap";
		AddObstacle("0", 50, 50, 504, 590, 0);
		int[][] Map = {{3,3},
					{3,3}};
		House1Map.init(Map,2,2);
	}
			
	private void MainMenuInit() {
		MainMenu.AddButton("Play", (1024/2-100), 100);
		MainMenu.AddButton("High Scores", (1024/2-100), 200);
		MainMenu.AddButton("Options", (1024/2-100), 300);
		MainMenu.AddButton("Exit", (1024/2-100), 400);
		
		MainMenu.Select(0);
	}
	
	private void OptionsMenuInit() {
		OptionsMenu.AddButton("Music", (1024/2-100), 100);
		OptionsMenu.AddButton("Option 2", (1024/2-100), 200);
		OptionsMenu.AddButton("Option 3", (1024/2-100), 300);
		OptionsMenu.AddButton("Back", (1024/2-100), 400);
		
		OptionsMenu.Select(0);
	}
	
	private void SoundMenuInit() {
		SoundMenu.AddButton("<-", (1024/4-25), 100);
		SoundMenu.AddButton("->", (1024*3/4), 100);
		SoundMenu.AddButton("Mute", (1024/2-50), 200);
		SoundMenu.AddButton("Back", (1024/2-50), 300);
		
		SoundMenu.Select(0);
	}
	
	private void PauseMenuInit() {
	
		PauseMenu.AddButton("Back", (1024/2-100), 300);
		
		PauseMenu.Select(0);
	}
	
	private void ScoreMenuInit() {
		ScoreMenu.AddButton("Back", (1024/3+50), 600);
		
		ScoreMenu.Select(0);
	}
	
	private void CloseMenuInit() {
		CloseMenu.AddButton("Exit", (1024/2-100), 250);
		CloseMenu.AddButton("Back", (1024/2-100), 400);
		CloseMenu.Select(0);
	}
	
	private void Clear() {
		//clears the scene
		ListOfEnemies.clear();
		ListOfObstacles.clear();
		ListOfPickups.clear();
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
			case "High Scores":
				LoadingMenu = true;
				State = GameState.SCOREMENU;
				break;
			case "Options":
				LoadingMenu = true;
				State = GameState.OPTIONSMENU;
				break;
				
			case "Music":
				LoadingMenu = true;
				State = GameState.SOUNDMENU;
				break;
				
			case "Exit":
				ScoreEngine.compareCurrentToHigh(currentGameScore);
				System.exit(0);
				//State = GameState.CLOSE;
				break;
				
			case "Back":
				LoadingMenu = true;
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
		if(deltaTime > 4) {
			tick = !tick;
		}
		run();
	}
	
	public void MovePlayer(int DeltaX,int DeltaY) {
		boolean MoveMap = false;
		boolean PlayerMove = !Physics.PlayerCollisions(DeltaX, DeltaY);
		int MapX=50;
		int MapY=50;
		
		if(DeltaX !=0 && DeltaY!=0){
			if(currentKeyPress == "Left" ||currentKeyPress == "Right") {
				DeltaY = 0;
			}
			else {
				DeltaX =0;
			}
		}
		
		if(CentreMap) {
			MapX = (1024 - CurrentMap.GetMaxX())/2 +10;
			MapY = (718- CurrentMap.GetMaxY())/2 + 25;
		}
		
		Rectangle Rect = new Rectangle(PlayerOne.GetX() + CurrentMap.GetDeltaX() +DeltaX, PlayerOne.GetY() + CurrentMap.GetDeltaY()+ DeltaY, 25,25);
		if(Rect.intersects(new Rectangle(MapX, MapY, CurrentMap.GetMaxX()-64, CurrentMap.GetMaxY()-62)))	{	
			Rect = new Rectangle(PlayerOne.GetX() + DeltaX, PlayerOne.GetY() + DeltaY, PlayerOne.GetWidth(),PlayerOne.GetHeight());
				if(Rect.intersects(new Rectangle(50,50,928,624))){	
					if(PlayerMove) {
							PlayerOne.Move(DeltaX, DeltaY);
						}
				}
				else {
					if(CurrentMap.GetDeltaX() >= 0 || CurrentMap.GetDeltaY() >= 0 ) {
						MoveMap = true;
					}
				}
				
				if(MoveMap && !Physics.PlayerCollisions(DeltaX, DeltaY)) {
					MoveObstacles(DeltaX,DeltaY);
					MovePickups(DeltaX,DeltaY);
					MoveEnemies(DeltaX,DeltaY);
					CurrentMap.Update(DeltaX,DeltaY);	
				}	
				
				if(PlayerMove) {
					for(int i = 0; i < ListOfEnemies.size(); i++) {
						Enemy E = ListOfEnemies.get(i);
						if(E.detectPlayer(PlayerOne.GetX(),PlayerOne.GetY())) {
							randomiseFile = String.valueOf((int)(Math.random() *5 +1));
							
							playerDetected.getSound("detected_"+randomiseFile);
							playerDetected.setVol(0.3);
							playerDetected.playSound();
						}
						E.triangulatePlayer (PlayerOne.GetX(),PlayerOne.GetY());
					}		
				}
		}
	}
	
	public void PickupItem(int i) {
		currentGameScore += ListOfPickups.get(i).getPickupValue();
		pickupItem.playSound();
		ListOfPickups.remove(i);
	}
	
	public void ObstacleAction(String s) {
		if(s == "LoadHouse1") {
			Clear();
			House1MapInit();
			enterHouse.playSound();
			CurrentMap = House1Map;
			CentreMap = true;
			PlayerOne.SetMainMapX();
			PlayerOne.SetMainMapY();
			PlayerOne.setX(500);
			PlayerOne.setY(500);
		}
		else if(s.compareTo("ExitToMainMap") ==0){
			Clear();
			enterHouse.playSound();
			CentreMap = false;
			MainMapInit();
			CurrentMap = MainMap;
			PlayerOne.setX(PlayerOne.GetMainMapX());
			PlayerOne.setY(PlayerOne.GetMainMapY());
		}
	}
	
	//Moving entities when the map moves 
	private void MoveObstacles(int DeltaX, int DeltaY) {
		for(int i = 0; i < ListOfObstacles.size(); i++) {
			Obstacle O = ListOfObstacles.get(i);
			O.Move(-DeltaX, -DeltaY);
		}
	}
	
	private void MovePickups(int DeltaX, int DeltaY) {
		for(int i = 0; i < ListOfPickups.size(); i++) {
			pickups P = ListOfPickups.get(i);
			P.Move(-DeltaX, -DeltaY);
		}
	}
	
	private void MoveEnemies(int DeltaX, int DeltaY) {
		for(int i = 0; i < ListOfEnemies.size(); i++) {
			Enemy E = ListOfEnemies.get(i);
			E.Move(-DeltaX, -DeltaY);
			
		}
	}
	
	//adds entities
	private void AddEnemy(int X, int Y) {
		Enemy E = new MeleeEnemy(X,Y, "Left");
		E.SetBounds(0,0,64,64);
		ListOfEnemies.add(E);
	}
	
	private void AddObstacle(String s, int W, int H, int X, int Y,int Frames) {
		Obstacle O = new Obstacle(s,W,H,X,Y,Frames);
		if(s == "House_1"){
			O.SetBounds(0, H/2, W-15, H/2 -8);
			O.SetSpecialBounds(36, H+1, 8, 1);
			O.SetAction("LoadHouse1");
		}
		else if(s == "0") {
			O.SetSpecialBounds(X, Y, W, H);
			O.SetAction("ExitTo"+PreviousMap);
		}
		ListOfObstacles.add(O);
	}
	
	
	private void AddPickup(String type, int X, int Y) {
		ListOfPickups.add(new pickups(type,X,Y));
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
		else if(i == 4) {
			State =GameState.SCOREMENU;
		}
		else if(i == 5) {
			State =GameState.PAUSED;
		}
		
		else {
			State =GameState.CLOSE;
		}
	}
	
	public void SetCurrentPlayerDirrection(String keyLog) {
		if (keyLog != null) {
		 currentKeyPress = keyLog;
		 standingStill = false;
		} else {
			standingStill = true;
		}
	}
	
	public void setPlayerAttacking(boolean playerAttack) {
		isPlayerAttacking = playerAttack;
	}
	
	public boolean playerIsStandingStill() {
		return standingStill;
	}
	
	public void Close() {
		IsRunning = false;
	}
	
	public void PlayerSwapWeapon() {
		swapWeapon.playSound();
		if(PlayerOne.getWeaponType() == "melee") {
			 PlayerOne.setWeaponType("ranged");
		 } else {
			 PlayerOne.setWeaponType("melee");
		 }
	}
	
	public String getVolume() {
		int volumePercent = (int) (MMMusic.getVol()*100);
		if(MMMusic.isMute()) {
			return "0";
		}
		return Integer.toString(volumePercent);
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
	
	public Menu GetScoreMenu() {
		return ScoreMenu;
	}
	
	public Menu GetPauseMenu() {
		return PauseMenu;
	}
	
	public Menu GetCloseMenu() {
		return CloseMenu;
	}
	

	public ScoreSaver GetScoreEngine() {
		return ScoreEngine;
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
	
	public pickups GetPickup(int i) {
		return ListOfPickups.get(i);
	}
	
	public ArrayList<Enemy> GetListOfEnemies() {
		return ListOfEnemies;
	}
	
	public ArrayList<Obstacle> GetListOfObstacles() {
		return ListOfObstacles;
	}
	
	public ArrayList<pickups> GetListOfPickups(){
		return ListOfPickups;
	}
	
	public int GetNumberOfEnemies() {
		return ListOfEnemies.size();
	}
		
	public int GetNumberOfObstacles() {
		return ListOfObstacles.size();
	}
	
	public int GetNumberOfPickups() {
		return ListOfPickups.size();
	}
	
	public Player GetPlayer() {
		return PlayerOne;
	}
	
	public String GetScore() {
		String Score = String.format("%06d", currentGameScore);
		return Score;
	}
	
	public boolean getPlayerAttacking() {
		return isPlayerAttacking;
	}
	
	public boolean PlayerAttack() {
		if (isPlayerAttacking) {
		if(PlayerOne.getWeaponType() == "melee") {
			meleeAttack.playSound();
			//insert attack melee animation
		} else {
			if (PlayerOne.getAmmo() == 0) {
				noAmmo.playSound();
				isPlayerAttacking = false;
				return false;
			} else {
				rangedAttack.playSound();
				//insert attack ranged animation
			}
		}
		PlayerOne.attack();
		isPlayerAttacking = false;
		return true;	
		}
		return false;
	}
	
	public int GetPlayerAttackLocation(String XorY) {
		int x = 0;
		int y = 0;
		switch (currentKeyPress) {
		case "Right":
			x = 64;
		break;
		
		case "Backwards":
			y = -64;
		break;
		
		case "Left":
			x = -64;
		break;
		
		case "Forward":
			y = 64;
		break;	
		}
		if (XorY == "x") {
			return x+PlayerOne.GetX();
		}
		return y+PlayerOne.GetY();
	}
	
	public Map Getlevel() {
		return CurrentMap;
	}
	
	public boolean GetCentreMap() {
		return CentreMap;
	}
	
	public String GetCurrentPlayerDirrection() {
		return currentKeyPress;
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
		case SCOREMENU:
			return 4;
		case PAUSED:
			return 5;
		case CLOSE:
		return 6;
	}
		return -1;
	}
}