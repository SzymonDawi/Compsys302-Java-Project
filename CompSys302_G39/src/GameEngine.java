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
	private boolean tutorialRoom;
	private String randomiseFile;
	private int currentGameScore;  //do not make this private (yet)
	private ScoreSaver ScoreEngine = new ScoreSaver();
	private Timer Timer;
	private int remainingTime;
	private boolean bossFightStarted;
	private boolean bossLockedOn;
	private boolean bossDefeated;
	private int deathTimer;
	private int winTimer;
	
	private Player PlayerOne;
	private int PlayerXDir;
	private int PlayerYDir;
	
	private boolean CentreMap = false;
	private Map MainMap = new Map();
	private int MainMapDeltaX = 0;
	private int MainMapDeltaY = 0;
	private Map House1Map = new Map();
	private Map House2Map = new Map();
	private Map House3Map = new Map();
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
	private Sound win = new Sound();
	private Sound timeOut = new Sound();
	private float prevousVolume;
	
	private GameState State;
	private GameState previousState;
	private Physics Physics = new Physics();
	private boolean GameReset = true;
	
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
	
	private boolean IsRunning = true;
	private boolean tick= false;
	private boolean PreviousTick = false;
	private int TickCount = 0;
	private boolean physicsrun = false;
	
	public void run() {
		//selects what functions to run depending on the state
		switch (State){
			case MAINMENU:
				PlayerOne = new Player();
				PlayerOne.SetMainMapX(140);
				PlayerOne.SetMainMapY(250);
				CentreMap = true;
				
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
					if (isPlayerHit()) {
						playerHurt.playSound();
					}
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
							B.lockOnPlayer (PlayerOne.GetX(), PlayerOne.GetY(),TickCount);
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
				bossFightStarted = false;
				bossDefeated = false;
				winTimer = 0;
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
					bossDefeated = true;
					winTimer = 0;
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
	
		tutorialRoom = true;
		CentreMap = true;
		House1MapInit();
		CurrentMap = House1Map;	
		
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
		win.getSound("win");
		timeOut.getSound("timeOut");
		MMMusic.getSound("MMMusic_forgotten-toys");
		MMMusic.loopSound(Clip.LOOP_CONTINUOUSLY);
		MMMusic.setVol( 0.50);
		State = GameState.MAINMENU;
		currentGameScore = 0;
		bossFightStarted = false;
		bossLockedOn = false;
		deathTimer = 0;
		bossDefeated = false;
		winTimer = 0;
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
				{0,0,0,0,0,0,0,0,1,4,31,3,3,3,3}};
	
		MainMap.init(Map,15,8);
		MainMap.SetX(MainMapDeltaX);
		MainMap.SetY(MainMapDeltaY);
		
		if(GameReset) {
			AddPickup("coin", 500-MainMapDeltaX, 500-MainMapDeltaY);
			AddPickup("ammo", 400-MainMapDeltaX, 450-MainMapDeltaY);
			AddPickup("health", 430-MainMapDeltaX, 470-MainMapDeltaY);
			GameReset = false;
		}
		
		AddEnemy(-100-MainMapDeltaX, -100-MainMapDeltaY,"Right","Meele");
		AddEnemy(470-MainMapDeltaX, 500-MainMapDeltaY,"Right","Meele");
		AddEnemy(400-MainMapDeltaX, 300-MainMapDeltaY, "Left","Meele");
		AddEnemy(1000-MainMapDeltaX, 300-MainMapDeltaY,"Left","Meele");
		AddEnemy(750-MainMapDeltaX, 1000-MainMapDeltaY, "Backward","Meele");
		AddEnemy(900-MainMapDeltaX, 1150-MainMapDeltaY, "Backward","Meele");
		AddEnemy(900-MainMapDeltaX, 1300-MainMapDeltaY, "Backward","Meele");
		
		AddObstacle("House_1",174,132,100-MainMapDeltaX,100-MainMapDeltaY,1);
		AddObstacle("House_2",99,99,1000-MainMapDeltaX,1100-MainMapDeltaY,1);
		AddObstacle("House_3_Unlocked",183,132,1600-MainMapDeltaX,1050-MainMapDeltaY,1);
		AddObstacle("House_3",183,132,1600-MainMapDeltaX,1050-MainMapDeltaY,1);
		
		//MainMap.LoadTile("Locked_Door", 1650, 1100, 32);
		MainMap.LoadTile("Path_Up", 118, 232, 64);
		MainMap.LoadTile("Path_Up", 118, 296, 64);
		MainMap.LoadTile("Path_Up", 118, 360, 64);
		MainMap.LoadTile("Path_Down_Right", 118, 424, 64);
		MainMap.LoadTile("Path_Left", 182, 430, 64);
		MainMap.LoadTile("Path_Left", 246, 430, 64);
		MainMap.LoadTile("Path_Left", 310, 430, 64);
		MainMap.LoadTile("Path_Left", 374, 430, 64);
		MainMap.LoadTile("Path_Left", 438, 430, 64);
		MainMap.LoadTile("Path_Left", 502, 430, 64);
		MainMap.LoadTile("Path_Left", 566, 430, 64);
		MainMap.LoadTile("Path_Left", 630, 430, 64);
		MainMap.LoadTile("Path_Up_Left", 694, 440, 64);
		MainMap.LoadTile("Path_Up", 694, 504, 64);
		MainMap.LoadTile("Path_Up", 694, 905, 64);
		MainMap.LoadTile("Bridge", 580, 490, 0);
		MainMap.LoadTile("Path_Up", 694, 969, 64);
		MainMap.LoadTile("Path_Up", 694, 1033, 64);
		MainMap.LoadTile("Path_Up", 694, 1097, 64);
		MainMap.LoadTile("Path_Up", 694, 1161, 64);
		MainMap.LoadTile("Path_Down_Right", 694, 1225, 64);
		MainMap.LoadTile("Path_Left", 758, 1225, 64);
		MainMap.LoadTile("Path_Left", 822, 1225, 64);
		MainMap.LoadTile("Path_Left", 886, 1225, 64);
		MainMap.LoadTile("Path_Left", 950, 1225, 64);
		MainMap.LoadTile("Path_Down_Left", 1014, 1225, 64);
		MainMap.LoadTile("Path_Up", 1014, 1161, 64);
		MainMap.LoadTile("Path_Left", 1046, 1225, 64);
		MainMap.LoadTile("Path_Left", 1110, 1225, 64);
		MainMap.LoadTile("Path_Left", 1174, 1225, 64);
		MainMap.LoadTile("Path_Left", 1238, 1225, 64);
		MainMap.LoadTile("Path_Left", 1302, 1225, 64);
		MainMap.LoadTile("Path_Left", 1366, 1225, 64);
		MainMap.LoadTile("Path_Left", 1430, 1225, 64);
		MainMap.LoadTile("Path_Left", 1494, 1225, 64);
		MainMap.LoadTile("Path_Left", 1558, 1225, 64);
		MainMap.LoadTile("Path_Left", 1622, 1225, 64);
		MainMap.LoadTile("Path_Down_Left", 1686, 1225, 64);
		MainMap.LoadTile("Path_Up", 1686, 1193, 64);
		
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
		AddObstacle("Wall", 88, 88, 512-MainMapDeltaX, 584-MainMapDeltaY, 0);
		
		//Tile 18
		AddObstacle("Wall", 26, 88, 780-MainMapDeltaX, 560-MainMapDeltaY, 0);
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
		
		//Tile 10 and 8 and 6
		AddObstacle("Wall", 128, 8, 2048-MainMapDeltaX, 672-MainMapDeltaY, 0);
		AddObstacle("Wall", 224, 8, 2176-MainMapDeltaX, 680-MainMapDeltaY, 0);
		AddObstacle("Wall", 104, 8, 2400-MainMapDeltaX, 688-MainMapDeltaY, 0);
		AddObstacle("Wall",	56, 8, 2504-MainMapDeltaX, 680-MainMapDeltaY, 0);
		AddObstacle("Wall",	8, 160, 2560-MainMapDeltaX, 680-MainMapDeltaY, 0);
		
		//Beach Tiles
		AddObstacle("Wall",	8, 3072, 2700-MainMapDeltaX, 0-MainMapDeltaY, 0);
		
		//Tile 15
		AddObstacle("Wall",	32, 16, 0-MainMapDeltaX, 872-MainMapDeltaY, 0);
		AddObstacle("Wall",	48, 24, 32-MainMapDeltaX, 880-MainMapDeltaY, 0);
		AddObstacle("Wall",	112, 24, 80-MainMapDeltaX, 888-MainMapDeltaY, 0);
		AddObstacle("Wall",	40, 24, 192-MainMapDeltaX, 872-MainMapDeltaY, 0);
		AddObstacle("Wall",	24, 24, 232-MainMapDeltaX, 856-MainMapDeltaY, 0);

		//Tile 17
		AddObstacle("Wall",	32, 24, 256-MainMapDeltaX, 856-MainMapDeltaY, 0);
		AddObstacle("Wall",	96, 24, 288-MainMapDeltaX, 840-MainMapDeltaY, 0);
		AddObstacle("Wall",	224, 16, 376-MainMapDeltaX, 864-MainMapDeltaY, 0);
		
		//Bridge
		AddObstacle("Wall",	8, 288, 592-MainMapDeltaX, 584-MainMapDeltaY, 0);
		AddObstacle("Wall",	8, 288, 780-MainMapDeltaX, 584-MainMapDeltaY, 0);
		
		//Tile 19
		AddObstacle("Wall",	243, 16, 780-MainMapDeltaX, 864-MainMapDeltaY, 0);
		
		//Tile 17
		AddObstacle("Wall",	32, 24, 1023-MainMapDeltaX, 856-MainMapDeltaY, 0);
		AddObstacle("Wall",	96, 24, 1055-MainMapDeltaX, 840-MainMapDeltaY, 0);
		AddObstacle("Wall",	128, 16, 1151-MainMapDeltaX, 864-MainMapDeltaY, 0);
		
		//Tile 15
		AddObstacle("Wall",	32, 16, 1279-MainMapDeltaX, 872-MainMapDeltaY, 0);
		AddObstacle("Wall",	48, 24, 1311-MainMapDeltaX, 880-MainMapDeltaY, 0);
		AddObstacle("Wall",	112, 24, 1359-MainMapDeltaX, 888-MainMapDeltaY, 0);
		AddObstacle("Wall",	40, 24, 1471-MainMapDeltaX, 872-MainMapDeltaY, 0);
		AddObstacle("Wall",	24, 24, 1511-MainMapDeltaX, 856-MainMapDeltaY, 0);
		
		//TIle 13
		AddObstacle("Wall",	24, 24, 1535-MainMapDeltaX, 864-MainMapDeltaY, 0);
		AddObstacle("Wall",	48, 24, 1559-MainMapDeltaX, 880-MainMapDeltaY, 0);
		AddObstacle("Wall",	88, 24, 1607-MainMapDeltaX, 856-MainMapDeltaY, 0);
		AddObstacle("Wall",	48, 24, 1695-MainMapDeltaX, 864-MainMapDeltaY, 0);
		AddObstacle("Wall",	48, 24, 1743-MainMapDeltaX, 880-MainMapDeltaY, 0);
		
		//Tile 11 and 9
		AddObstacle("Wall",	40, 8, 1791-MainMapDeltaX, 896-MainMapDeltaY, 0);
		AddObstacle("Wall",	48, 8, 1831-MainMapDeltaX, 888-MainMapDeltaY, 0);
		AddObstacle("Wall",	48, 8, 1879-MainMapDeltaX, 880-MainMapDeltaY, 0);
		AddObstacle("Wall",	40, 8, 1927-MainMapDeltaX, 872-MainMapDeltaY, 0);
		AddObstacle("Wall",	48, 8, 1967-MainMapDeltaX, 864-MainMapDeltaY, 0);
		AddObstacle("Wall",	64, 8, 2015-MainMapDeltaX, 856-MainMapDeltaY, 0);
		AddObstacle("Wall",	40, 8, 2079-MainMapDeltaX, 848-MainMapDeltaY, 0);
		AddObstacle("Wall",	24, 8, 2119-MainMapDeltaX, 840-MainMapDeltaY, 0);
		AddObstacle("Wall",	296, 8, 2143-MainMapDeltaX, 832-MainMapDeltaY, 0);
		AddObstacle("Wall",	124, 8, 2439-MainMapDeltaX, 840-MainMapDeltaY, 0);
		
		//MapOutline
		AddObstacle("Wall",	1, 2048, -MainMapDeltaX, -MainMapDeltaY, 0);
		AddObstacle("Wall",	3840, 1, -MainMapDeltaX, -MainMapDeltaY, 0);
		//AddObstacle("Wall",	1, 2048, 3840, 0, 0);
		AddObstacle("Wall",	3840, 1, -MainMapDeltaX, 2048 -MainMapDeltaY, 0);
	}
	
	private void House1MapInit() {
		AddEnemy(-100-MainMapDeltaX, -100-MainMapDeltaY,"Right","Meele");
		
		//MapOutline
		AddObstacle("Wall",	1, 512, 248, 128, 0);
		AddObstacle("Wall",	512, 1, 256,128, 0);
		AddObstacle("Wall",	1, 512, 756, 128, 0);
		AddObstacle("Wall",	512, 1, 256, 640, 0);
		
		PreviousMap = "MainMap";
		AddObstacle("0", 50, 50, 430, 590, 0);
		int[][] Map = {{3,3},
					{20,3}};
		House1Map.init(Map,2,2);
	}
	
	private void House2MapInit() {
		AddEnemy(-100-MainMapDeltaX, -100-MainMapDeltaY,"Right","Meele");
		
		//MapOutline
		AddObstacle("Wall",	1, 512, 248, 128, 0);
		AddObstacle("Wall",	512, 1, 256,128, 0);
		AddObstacle("Wall",	1, 512, 756, 128, 0);
		AddObstacle("Wall",	512, 1, 256, 640, 0);
		
		AddPickup("coin",450, 400);
		AddPickup("ammo", 400, 450);
		AddPickup("health", 430, 470);
		
		if(!PlayerOne.getHasKey()) {
			AddPickup("key", 400, 400);
		}
		
		PreviousMap = "MainMap";
		AddObstacle("0", 50, 50, 430, 590, 0);
		int[][] Map = {{3,3},
					{20,3}};
		House2Map.init(Map,2,2);
	}
	
	private void House3MapInit() {
		AddEnemy(-100-MainMapDeltaX, -100-MainMapDeltaY,"Right","Meele");
		
		//MapOutline
		AddObstacle("Wall",	1, 512, 125, 128, 0);
		AddObstacle("Wall",	768, 1, 125,128, 0);
		AddObstacle("Wall",	1, 512, 895, 128, 0);
		AddObstacle("Wall",	768, 1, 125, 640, 0);
		
		AddEnemy(450, 450,"Right","boss");
		PreviousMap = "MainMap";
		int[][] Map = {{3,3,3},
					  {3,20,3}};
		House3Map.init(Map,3,2);
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
		ListofProjectiles.clear();
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
				deathTimer = 0;
				bossDefeated = false;
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
				PlayerOne.setHasKey(false);
				CentreMap = false;
				House1MapInit();
				CurrentMap = House1Map;
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
	
	public void Cheat() {
		Clear();
		MainMapDeltaX = MainMap.GetDeltaX();
		MainMapDeltaY = MainMap.GetDeltaY();
		House3MapInit();
		enterHouse.playSound();
		CurrentMap = House3Map;
		CentreMap = true;
		PlayerOne.SetMainMapX();
		PlayerOne.SetMainMapY();
		PlayerOne.setX(500);
		PlayerOne.setY(500);
		
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
		
		Rectangle Rect = new Rectangle(PlayerOne.GetX() + DeltaX, PlayerOne.GetY() + DeltaY, PlayerOne.GetWidth(),PlayerOne.GetHeight());
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
			if(CurrentMap.GetX() > CurrentMap.GetMaxX()-1024) {
				DeltaX = 0;
			}	
			if(CurrentMap.GetY() > CurrentMap.GetMaxY()-768) {
				DeltaY = 0;
			}
			
			MoveProjectiles(DeltaX,DeltaY);
			MoveObstacles(DeltaX,DeltaY);
			MovePickups(DeltaX,DeltaY);
			MoveEnemies(DeltaX,DeltaY);
			CurrentMap.Update(DeltaX,DeltaY);
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
			tutorialRoom = false;
			enterHouse.playSound();
			CentreMap = false;
			MainMapInit();
			CurrentMap = MainMap;
			PlayerOne.setX(PlayerOne.GetMainMapX());
			PlayerOne.setY(PlayerOne.GetMainMapY());
		}
		else if(s == "LoadHouse2") {
			Clear();
			tutorialRoom = false;
			MainMapDeltaX = MainMap.GetDeltaX();
			MainMapDeltaY = MainMap.GetDeltaY();
			House2MapInit();
			enterHouse.playSound();
			CurrentMap = House2Map;
			CentreMap = true;
			PlayerOne.SetMainMapX();
			PlayerOne.SetMainMapY();
			PlayerOne.setX(500);
			PlayerOne.setY(500);
		}
		else if(s == "LoadHouse3") {
			if(PlayerOne.getHasKey()) {
				Clear();
				MainMapDeltaX = MainMap.GetDeltaX();
				MainMapDeltaY = MainMap.GetDeltaY();
				House3MapInit();
				enterHouse.playSound();
				CurrentMap = House3Map;
				CentreMap = true;
				PlayerOne.SetMainMapX();
				PlayerOne.SetMainMapY();
				PlayerOne.setX(500);
				PlayerOne.setY(500);
			}
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
	
	private void MoveProjectiles(int DeltaX, int DeltaY) {
		for(int i = 0; i < ListofProjectiles.size(); i++) {
			Projectile P = ListofProjectiles.get(i);
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
		P.setExists(true);
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
		else if(s.compareTo("House_2") == 0){
			O.SetBounds(0, H/2-10 , W-20, H/2);
			O.SetSpecialBounds(33, H-1, 20, 1);
			O.SetAction("LoadHouse2");
		}
		else if(s.compareTo("House_3") == 0){
			O.SetBounds(0, H/2-10 , W-20, H/2);
			O.SetSpecialBounds(80, H, 40, 1);
			O.SetAction("LoadHouse3");
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
	
	public boolean GetTutorialRoom() {
		return tutorialRoom;
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
	
	public boolean isPlayerHit() {
		return Physics.getPlayerHit();
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
		if (remainingTime == 0) {
			PlayerOne.SetDeathMessage("OUT OF TIME");
			timeOut.playSound();
			SetState(6);
		}
		if(bossLockedOn && bossFightStarted) {
			deathTimer++;
			if(deathTimer == 1) {
				deathCount3.playSound();
				
			} else if(deathTimer == 2) {
				deathCount2.playSound();
			
			} else if(deathTimer == 3) {
				deathCount1.playSound();
				
			} else {
				laserDeath.playSound();
				PlayerOne.TakeDamage(5);
			}
		} else {
			deathTimer = 0;
		}
		
		if (bossDefeated) {
			System.out.println("nice");
			winTimer++;
			if (winTimer == 3) {
				PlayerOne.SetDeathMessage("YOU WIN!");
				win.playSound();
				SetState(6);
			}
		}
	}
}