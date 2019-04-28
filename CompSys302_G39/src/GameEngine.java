import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;



public class GameEngine implements ActionListener{
	//stores the current scene
	private ArrayList<Enemy> ListOfEnemies = new ArrayList<Enemy>();
	private ArrayList<Obstacle> ListOfObstacles = new ArrayList<Obstacle>();
	private ArrayList<Projectile> ListofProjectiles = new ArrayList<Projectile>();
	private ArrayList<pickups> ListOfPickups = new ArrayList<pickups>();
	
	private String currentKeyPress = "Forward";
	private boolean isPlayerAttacking = false;
	private boolean standingStill = true;
	private String randomiseFile;
	private int currentGameScore;  //do not make this private (yet)
	private ScoreSaver ScoreEngine = new ScoreSaver();
	private Timer Timer;
	private int remainingTime;
	private boolean bossFightStarted;
	private boolean bossLockedOn;
	private int deathTimer;
	
	private Player PlayerOne;
	private int PlayerXDir;
	private int PlayerYDir;
	
	private boolean CentreMap = false;
	private Map MainMap = new Map();
	private int MainMapDeltaX = 0;
	private int MainMapDeltaY = 0;
	private Map House1Map = new Map();
	private Map CurrentMap;
	private String PreviousMap;
	
	private Menu MainMenu = new Menu();
	private Menu SoundMenu = new Menu();
	private Menu ScoreMenu = new Menu();
	private Menu PauseMenu = new Menu();
	private Menu OptionsMenu = new Menu();
	private Menu CurrentMenu = new Menu();
	private Menu DeadMenu = new Menu();
	private Menu CloseMenu = new Menu();
	private boolean LoadingMenu;
	
	private Sound buttonClick = new Sound();
	private Sound enemyDeath = new Sound();
	private Sound bossDeath = new Sound();
	private Sound buttonSwitch = new Sound();
	private Sound swapWeapon = new Sound();
	private Sound deathCount3 = new Sound();
	private Sound deathCount2 = new Sound();
	private Sound deathCount1 = new Sound();
	private Sound laserDeath = new Sound();
	private Sound playerHurt = new Sound();
	private Sound enemyHurt = new Sound();
	private Sound gameOver = new Sound();
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
		DEAD,
		CLOSE
	}
	
	private int CurrentTime = 0;
	private int LastTime = 0;
	private boolean IsRunning = true;
	private boolean tick= false;
	private boolean PreviousTick = false;
	private int TickCount = 0;
	private boolean physicsrun = false;
	
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
				Timer.start();
				Physics.Update(this);
				
				if(tick != PreviousTick) {
					PreviousTick = tick;
					TickCount++;
					physicsrun = false;
				}
				
				if(TickCount == 10) {
					TickCount = 0;
				}
				
				if(tick&& !physicsrun) {
					for(int i =0; i<ListofProjectiles.size(); i++) {
						Projectile P = ListofProjectiles.get(i);
						P.Move();
					}
					
					CheckIfDead();
					for(int i = 0; i < ListOfEnemies.size(); i++) {
						if (ListOfEnemies.get(i).getType() == "boss") {
							boss B = (boss) ListOfEnemies.get(i);
							
							if(B.detectPlayer(PlayerOne.GetX(),PlayerOne.GetY())) {
							
								playerDetected.getSound("bossWakeup");
								playerDetected.setVol(0.4);
								playerDetected.playSound();
								bossFightStarted = true;
							}
							
							if(!Physics.OtherCollisions(B)) {
								B.Move();
								B.triangulatePlayer(PlayerOne.GetX(),PlayerOne.GetY(),TickCount);
			
							}
							B.lockOnPlayer (PlayerOne.GetX(), PlayerOne.GetY());
							int crosshairDifferenceX = Math.abs(B.getCrosshairX()-PlayerOne.GetX());
							int crosshairDifferenceY = Math.abs(B.getCrosshairY()-PlayerOne.GetY());
							if (crosshairDifferenceX <10 && crosshairDifferenceY <10) {
								bossLockedOn = true;
							} else {
								bossLockedOn = false;
							}
						} else {
							Enemy E = ListOfEnemies.get(i);
//							System.out.println(E.GetX());
//							System.out.println(E.GetY());
							if(E.detectPlayer(PlayerOne.GetX(),PlayerOne.GetY())) {
								randomiseFile = String.valueOf((int)(Math.random() *5 +1));
							
								playerDetected.getSound("detected_"+randomiseFile);
								playerDetected.setVol(0.3);
								playerDetected.playSound();
							}
							if(!Physics.OtherCollisions(E)) {
								E.Move();
								E.triangulatePlayer(PlayerOne.GetX(),PlayerOne.GetY(),TickCount);
			
							}
						}
					}
					
					physicsrun = true;
					PlayerOne.SetIsAttacking(false);
				}
				//AddObstacle(100,100,50,50);
				//AddObstacle(100,100,1024,0);
				break;
				
			case PAUSED:
				Timer.stop();
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
				
			case DEAD:
				Timer.stop();
				CurrentMenu = DeadMenu;
				if(LoadingMenu) {
					CurrentMenu.Select(0);
					LoadingMenu = false;
				}
				break;
		}
	}
	
	private void CheckIfDead() {
		if(PlayerOne.GetHealth() == 0) {
			gameOver.playSound();
			SetState(6);
		}
		
		for(int i = 0; i < ListOfEnemies.size(); i++) {
			Enemy E = ListOfEnemies.get(i);
			if(E.GetHealth() == 0) {
				if(ListOfEnemies.get(i).getType() == "normal") {
					enemyDeath.playSound();
					currentGameScore+= 200;
			
				} else {
					bossDeath.playSound();
					bossFightStarted = false;
					currentGameScore+= 2000;
				}
				ListOfEnemies.remove(i);
			}
		}
	}
	
	public void init() {
		PlayerOne = new Player();
		MainMenuInit();
		OptionsMenuInit();
		ScoreMenuInit();
		SoundMenuInit();
		PauseMenuInit();
		CloseMenuInit();
		DeadMenuInit();
		
		AddPickup("coin", 500, 500);
		AddPickup("key", 700,700);
		AddPickup("ammo", 400, 450);
		AddPickup("health", 430, 470);
		
		MainMapInit();
		CurrentMap = MainMap;
		
		buttonClick.getSound("beep");
		buttonSwitch.getSound("switchButton");
		swapWeapon.getSound("Player_Swap_Weapons");
		rangedAttack.getSound("Player_Ranged_Attack");
		meleeAttack.getSound("Player_Melee_Attack");
		pickupItem.getSound("pickup");
		enterHouse.getSound("EnterDoor");
		playerHurt.getSound("player_hurt");
		enemyHurt.getSound("enemy_hit");
		gameOver.getSound("gameOver");
		noAmmo.getSound("noAmmo");
		enemyDeath.getSound("enemy_die");
		bossDeath.getSound("bossDeath");
		deathCount3.getSound("bossCountdown3");
		deathCount2.getSound("bossCountdown2");
		deathCount1.getSound("bossCountdown1");
		laserDeath.getSound("laserDeath");
		MMMusic.getSound("MMMusic_forgotten-toys");
		MMMusic.loopSound(Clip.LOOP_CONTINUOUSLY);
		MMMusic.setVol( 0.50);
		State = GameState.MAINMENU;
		currentGameScore = 0;
		bossFightStarted = false;
		bossLockedOn = false;
		deathTimer = 0;
	}
	
	private void MainMapInit() {
		int[][] Map = {
				{0,0,0,0,0,0,0,0,1,4,33,3,3,3,3},
				{0,0,0,0,0,0,0,0,1,4,32,3,3,3,3},
				{14,16,14,18,14,16,14,12,10,8,6,3,3,3,3},
				{15,17,19,19,17,15,13,11,9,7,5,3,3,3,3},
				{0,0,0,0,0,0,0,0,1,4,32,3,3,3,3},
				{0,0,0,0,0,0,0,0,1,4,31,3,3,3,3},
				{0,0,0,0,0,0,0,0,1,4,33,3,3,3,3},
				{0,0,0,0,0,0,0,0,1,4,31,3,3,3,3},
				{0,0,0,0,0,0,0,0,1,4,32,3,3,3,3},
				{0,0,0,0,0,0,0,0,1,4,33,3,3,3,3},
				{0,0,0,0,0,0,0,0,1,4,31,3,3,3,3},
				{0,0,0,0,0,0,0,0,1,4,33,3,3,3,3}};
	
		MainMap.init(Map,15,12);
		MainMap.SetX(MainMapDeltaX);
		MainMap.SetY(MainMapDeltaY);
		
		AddObstacle("House_1",174,132,100-MainMapDeltaX,100-MainMapDeltaY,1);
		
		//Tile 14
		AddObstacle("Wall", 256, 88, 0-MainMapDeltaX, 584-MainMapDeltaY, 0);
		
		//Tile 16
		AddObstacle("Wall", 32, 72, 256-MainMapDeltaX, 584-MainMapDeltaY, 0);
		AddObstacle("Wall", 8, 84, 288-MainMapDeltaX, 576-MainMapDeltaY, 0);
		AddObstacle("Wall", 16, 84, 296-MainMapDeltaX, 568-MainMapDeltaY, 0);
		AddObstacle("Wall", 80, 84, 312-MainMapDeltaX, 560-MainMapDeltaY, 0);
		AddObstacle("Wall", 32, 84, 392-MainMapDeltaX, 576-MainMapDeltaY, 0);
		AddObstacle("Wall", 72, 84, 424-MainMapDeltaX, 584-MainMapDeltaY, 0);
		AddObstacle("Wall", 16, 80, 496-MainMapDeltaX, 584-MainMapDeltaY, 0);
		
		//Tile 14
		AddObstacle("Wall", 128, 88, 512-MainMapDeltaX, 584-MainMapDeltaY, 0);
		
		//Tile 18
		AddObstacle("Wall", 40, 88, 768-MainMapDeltaX, 560-MainMapDeltaY, 0);
		AddObstacle("Wall", 8, 88, 808-MainMapDeltaX, 568-MainMapDeltaY, 0);		
		AddObstacle("Wall", 208, 80, 816-MainMapDeltaX, 576-MainMapDeltaY, 0);
		
		//Tile 14
		AddObstacle("Wall", 256, 88, 1024-MainMapDeltaX, 584-MainMapDeltaY, 0);	
		
		//Tile 16
		AddObstacle("Wall", 32, 72, 1280-MainMapDeltaX, 584-MainMapDeltaY, 0);
		AddObstacle("Wall", 8, 84, 1312-MainMapDeltaX, 576-MainMapDeltaY, 0);
		AddObstacle("Wall", 16, 84, 1320-MainMapDeltaX, 568-MainMapDeltaY, 0);
		AddObstacle("Wall", 80, 84, 1336-MainMapDeltaX, 560-MainMapDeltaY, 0);
		AddObstacle("Wall", 32, 84, 1416-MainMapDeltaX, 576-MainMapDeltaY, 0);
		AddObstacle("Wall", 72, 84, 1448-MainMapDeltaX, 584-MainMapDeltaY, 0);
		AddObstacle("Wall", 16, 80, 1520-MainMapDeltaX, 584-MainMapDeltaY, 0);
		
		//Tile 14
		AddObstacle("Wall", 256, 88, 1536-MainMapDeltaX, 584-MainMapDeltaY, 0);	
		
		//Tile 12
		AddObstacle("Wall", 64, 64, 1792-MainMapDeltaX, 584-MainMapDeltaY, 0);
		AddObstacle("Wall", 16, 56, 1856-MainMapDeltaX, 600-MainMapDeltaY, 0);
		AddObstacle("Wall", 24, 48, 1872-MainMapDeltaX, 608-MainMapDeltaY, 0);
		AddObstacle("Wall", 24, 40, 1896-MainMapDeltaX, 616-MainMapDeltaY, 0);
		AddObstacle("Wall", 24, 32, 1920-MainMapDeltaX, 624-MainMapDeltaY, 0);
		AddObstacle("Wall", 32, 32, 1944-MainMapDeltaX, 640-MainMapDeltaY, 0);
		AddObstacle("Wall", 32, 32, 1976-MainMapDeltaX, 648-MainMapDeltaY, 0);
		AddObstacle("Wall", 40, 16, 2008-MainMapDeltaX, 664-MainMapDeltaY, 0);
		
		//Tile 10 and 8
		AddObstacle("Wall", 128, 8, 2048-MainMapDeltaX, 672-MainMapDeltaY, 0);
		AddObstacle("Wall", 224, 8, 2176-MainMapDeltaX, 680-MainMapDeltaY, 0);
		AddObstacle("Wall", 104, 8, 2400-MainMapDeltaX, 688-MainMapDeltaY, 0);
		AddObstacle("Wall",	56, 8, 2504-MainMapDeltaX, 680-MainMapDeltaY, 0);
		
		//Beach Tiles
		AddObstacle("Wall",	8, 3072, 2700-MainMapDeltaX, 0-MainMapDeltaY, 0);
		
		AddEnemy(470-MainMapDeltaX, 510-MainMapDeltaY,"Left","normal");
		AddEnemy(200-MainMapDeltaX, 700-MainMapDeltaY, "Backward", "boss");
		
		MainMap.LoadTile("Bridge", 580, 490, 0);
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
	
	
	private void DeadMenuInit() {
		
		DeadMenu.AddButton("Ok", (1024/2-100), 600);
		
		DeadMenu.Select(0);
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
				remainingTime = 300;
				Timer = new Timer(1000, this);
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
			case "Ok":
				ScoreEngine.compareCurrentToHigh(currentGameScore);
				Clear();
				init();
				PlayerOne.SetHealth(PlayerOne.GetMaxHealth());
				State = GameState.MAINMENU;
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
	
	public void Update(boolean tick) {
		this.tick = tick;
		run();
	}
	
	public void MovePlayer(int DeltaX,int DeltaY) {
		boolean MoveMap = false;
		int MapX=50;
		int MapY=50;
		boolean PlayerMove = !Physics.PlayerCollisions(DeltaX, DeltaY);
		if(DeltaX !=0 && DeltaY!=0){
			if(currentKeyPress == "Left" ||currentKeyPress == "Right") {
				DeltaY =0;
			}
			else {
				DeltaX =0;
			}
		}
		
		PlayerXDir = DeltaX;
		PlayerYDir = DeltaY;
		SetCurrentKey();
		
		
		if(CentreMap) {
			MapX = (1024 - CurrentMap.GetMaxX())/2 +10;
			MapY = (718- CurrentMap.GetMaxY())/2 + 25;
		}
		
		Rectangle Rect = new Rectangle(PlayerOne.GetX() + CurrentMap.GetDeltaX() +DeltaX, PlayerOne.GetY() + CurrentMap.GetDeltaY()+ DeltaY, 25,25);
		if(Rect.intersects(new Rectangle(MapX, MapY, CurrentMap.GetMaxX()-64, CurrentMap.GetMaxY()-62)))	{	
			Rect = new Rectangle(PlayerOne.GetX() + DeltaX, PlayerOne.GetY() + DeltaY, PlayerOne.GetWidth(),PlayerOne.GetHeight());
				if(Rect.intersects(new Rectangle(50,50,828,524))){	
					if(PlayerMove) {
							PlayerOne.Move(DeltaX, DeltaY);
						}
				}
				else {
					if(CurrentMap.GetDeltaX() >= 0 || CurrentMap.GetDeltaY() >= 0 ) {
						MoveMap = true;
					}
				}
				
				if(MoveMap && PlayerMove) {
					MoveObstacles(DeltaX,DeltaY);
					MovePickups(DeltaX,DeltaY);
					MoveEnemies(DeltaX,DeltaY);
					CurrentMap.Update(DeltaX,DeltaY);	
				}	
		}
	}
	
	public void PickupItem(int i) {
		currentGameScore += ListOfPickups.get(i).getPickupValue();
		pickupItem.playSound();
		switch(ListOfPickups.get(i).getType()) {
		case "ammo":
			PlayerOne.SetAmmo(PlayerOne.getAmmo() + 5);
			break;
		case "health":
			PlayerOne.SetHealth(PlayerOne.GetHealth() + 4);
			break;
		case "key":
			PlayerOne.setHasKey(true);
			break;
		}
		ListOfPickups.remove(i);
	}
	
	public void ObstacleAction(String s) {
		if(s == "LoadHouse1") {
			Clear();
			MainMapDeltaX = MainMap.GetDeltaX();
			MainMapDeltaY = MainMap.GetDeltaY();
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
	private void AddEnemy(int X, int Y, String Direction, String type) {
		Enemy E;
		if (type == "boss") {
			 E = new boss(X,Y, Direction); 
		} else {
			 E = new MeleeEnemy(X,Y, Direction); 
		}
		E.SetBounds(0,0,32,32);
		E.setAggro(false);
		ListOfEnemies.add(E);
	}
	
	public void AddProjectile(boolean Friendly, int X, int Y, int W, int H,String s) {
		Projectile P = new Projectile(Friendly, X, Y, W, H);
		P.SetBounds(0, 0, W, H);
		int Dx=0;
		int Dy=0;
		if(currentKeyPress.compareTo("Forward")==0) {
			Dy = 5;
		}
		else if(currentKeyPress.compareTo("Backwards")==0) {
			Dy = -5;
		}
		else if(currentKeyPress.compareTo("Left")==0) {
			Dx = -5;
		}
		else if(currentKeyPress.compareTo("Right")==0) {
			Dx = 5;
		}
		P.SetDX(Dx);
		P.SetDY(Dy);
		ListofProjectiles.add(P);
	}
	
	private void AddObstacle(String s, int W, int H, int X, int Y,int Frames) {
		Obstacle O = new Obstacle(s,W,H,X,Y,Frames);
		if(s.compareTo("House_1") == 0){
			O.SetBounds(0, H/2-20 , W-20, H/2);
			O.SetSpecialBounds(36, H+1, 20, 2);
			O.SetAction("LoadHouse1");
		}
		else if(s.compareTo("0") == 0) {
			O.SetSpecialBounds(0, 0, W, H);
			O.SetAction("ExitTo"+PreviousMap);
		}
		else if(s.compareTo("Wall") == 0) {
			O.SetBounds(0, 0 , W, H);
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
		else if(i == 6) {
			State =GameState.DEAD;
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
	
	private void SetCurrentKey() {
		if(PlayerXDir > 0) {
			currentKeyPress = "Right";
		}
		else if(PlayerXDir < 0) {
			currentKeyPress = "Left";
		}
		else if(PlayerYDir > 0) {
			currentKeyPress = "Forward";
		}
		else if(PlayerYDir > 0) {
			currentKeyPress = "Backwards";
		}
	}
	
	public void setPlayerAttacking(boolean playerAttack) {
		isPlayerAttacking = playerAttack;
		PlayerOne.SetIsAttacking(true);
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
	
	public Menu GetDeadMenu() {
		return DeadMenu;
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
	
	public Projectile GetProjectile(int i) {
		return ListofProjectiles.get(i);
	}
	
	public pickups GetPickup(int i) {
		return ListOfPickups.get(i);
	}
	
	public boolean GetBossFightStatus() {
		return bossFightStarted;
	}
	
	public boolean GetBossLockedOnStatus() {
		return bossLockedOn;
	}
	
	public ArrayList<Enemy> GetListOfEnemies() {
		return ListOfEnemies;
	}
	
	public ArrayList<Obstacle> GetListOfObstacles() {
		return ListOfObstacles;
	}
	
	public ArrayList<Projectile> GetListOfProjectile(){
		return ListofProjectiles;
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
	
	public int GetNumberOfProjectiles() {
		return ListofProjectiles.size();
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
	
	public String GetRemainingTime() {
		String TimeLeft = String.format("%03d", remainingTime);
		return TimeLeft;
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
		case DEAD:
			return 6;
		case CLOSE:
		return 7;
	}
		return -1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		remainingTime -= 1;
		if(bossLockedOn) {
			deathTimer++;
			if(deathTimer == 1) {
				deathCount3.playSound();
				
			} else if(deathTimer == 2) {
				deathCount2.playSound();
			
			} else if(deathTimer == 3) {
				deathCount1.playSound();
				
			} else {
				laserDeath.playSound();
				PlayerOne.Kill();
			}
		} else {
			deathTimer = 0;
		}
	}
}