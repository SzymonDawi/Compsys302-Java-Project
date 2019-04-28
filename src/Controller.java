import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Controller implements ActionListener{
	private GameEngine Engine = new GameEngine();
	private JPanel Panel;
	private Timer Timer;
	private InputMap InputMap;
	private int KeysPressed;
	private int DeltaX;
	private int DeltaY;
	private String CurrentKey;
	
	public Controller(JFrame f, GameEngine Engine, int Delay) {
		this.Engine = Engine;
		
		Timer = new Timer(Delay, this);
		Timer.setInitialDelay(0);
		
		Panel = (JPanel) f.getContentPane();
		InputMap = Panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	
	public void init() {
		AddAction("Backwards", 0,-10,KeyEvent.VK_W);
		AddAction("Left", -10,0,KeyEvent.VK_A);
		AddAction("Forward", 0,10,KeyEvent.VK_S);
		AddAction("Right", 10,0,KeyEvent.VK_D);
		AddAction("swapWeapon", 0,0, KeyEvent.VK_C);
		AddAction("Escape", 0,0,KeyEvent.VK_ESCAPE);
		AddAction("Enter", 0,0,KeyEvent.VK_ENTER);
		AddAction("Pause", 0,0,KeyEvent.VK_P);
		AddAction("Cheat", 0,0,KeyEvent.VK_PAGE_UP);
	}
	
	//Used for creating a custom action
	public void AddAction(String Name, int DeltaX, int DeltaY, int KeyCode) {
		new ControllerAction(Name, DeltaX, DeltaY, KeyCode, this);
	}
	
	//This method manipulates the game engine depending on the current button pressed
	public void Move(int DeltaX, int DeltaY, ActionEvent e) {
		
		//Behavior of escape key
		if(Engine.GetState() != 2  && CurrentKey == "Escape") {
			System.exit(0);
		}
		
		//selects different behaviors depending on the game state
		if((Engine.GetState() == 0) ||(Engine.GetState() == 1 )|| (Engine.GetState() == 3)|| (Engine.GetState() == 4)|| (Engine.GetState() == 5)|| (Engine.GetState() == 6)|| (Engine.GetState() == 7)) {
				//Menu behavior
				if(CurrentKey == "Left") {
					if(Engine.GetState() == 3 ||Engine.GetSoundMenu().GetSelected() == 1) {
						Engine.SwitchButton(-1);
					}
				}
				else if(CurrentKey == "Right") {
					if(Engine.GetState() == 3 || Engine.GetSoundMenu().GetSelected() == 0) {
						Engine.SwitchButton(1);
					}
				}
				else if(CurrentKey == "Backwards") {
					Engine.SwitchButton(-1);
				}
				else if(CurrentKey == "Forward") {
					Engine.SwitchButton(1);
				}
				else if(CurrentKey == "Enter") {
					Engine.SelectButton();
				}
				CurrentKey = null;
		}
		else if(Engine.GetState() == 2 ) {
			//Game scene behavior
			if(CurrentKey == "swapWeapon") {
				Engine.PlayerSwapWeapon();
				CurrentKey = null;
			}
			else if(CurrentKey == "Enter") {
				Engine.setPlayerAttacking(true);
				CurrentKey = null;
			}
			else if(CurrentKey == "Pause") {
				Engine.SetState(5);
				CurrentKey = null;
			}
			else if(CurrentKey == "Escape") {
				Engine.SetState(7);
				CurrentKey = null;
			
			}
			else if(CurrentKey == "Cheat") {
				Engine.Cheat();
				CurrentKey = null;
			
			}
			Engine.MovePlayer(DeltaX, DeltaY);
			
			Engine.SetCurrentPlayerDirrection(CurrentKey);
		}
		
	}
	
	//this method allow for a faster polling rate when a key its pressed
	public void HandleKeyEvent(int DeltaX, int DeltaY , boolean Pressed ) {
		KeysPressed += Pressed ? 1: -1;
		this.DeltaX += DeltaX;
		this.DeltaY += DeltaY;
		
		if(KeysPressed == 1) {
			Timer.start();
		}
		else if(KeysPressed == 0) {
			Timer.stop();
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Move(DeltaX, DeltaY, e);
	}
	
	public InputMap GetInputMap() {
		return InputMap;
	}
	
	public JPanel GetPanel() {
		return Panel;
	}
	
	public void SetKey(String Key) {
		CurrentKey = Key; 
	}

}