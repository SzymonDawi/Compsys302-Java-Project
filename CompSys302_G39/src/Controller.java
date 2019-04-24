import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class Controller implements ActionListener{
	private GameEngine Engine = new GameEngine();
	//private JFrame f;
	private JPanel Panel;
	private Timer Timer;
	private InputMap InputMap;
	private int KeysPressed;
	private int DeltaX;
	private int DeltaY;
	private String CurrentKey;
	boolean OneKey =true;
	
	public Controller(JFrame f, GameEngine Engine, int Delay) {
		this.Engine = Engine;
		
		Timer = new Timer(Delay, this);
		Timer.setInitialDelay(0);
		
		Panel = (JPanel) f.getContentPane();
		InputMap = Panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	
	public void init() {
		AddAction("Backwards", 0,-5,KeyEvent.VK_W);
		AddAction("Left", -5,0,KeyEvent.VK_A);
		AddAction("Forward", 0,5,KeyEvent.VK_S);
		AddAction("Right", 5,0,KeyEvent.VK_D);
		AddAction("SwapWeapons", 0,0,KeyEvent.VK_C);
		AddAction("Escape", 0,0,KeyEvent.VK_ESCAPE);
		AddAction("Enter", 0,0,KeyEvent.VK_ENTER);
	}
	
	public void AddAction(String Name, int DeltaX, int DeltaY, int KeyCode) {
		new ControllerAction(Name, DeltaX, DeltaY, KeyCode, this);
	}
	
	public void Move(int DeltaX, int DeltaY, ActionEvent e) {
		
		if(CurrentKey == "Escape") {
			Engine.SetState(4);
			System.exit(0);
		}
		
		if((Engine.GetState() == 0) ||(Engine.GetState() == 1 )|| (Engine.GetState() == 3)) {
				if(CurrentKey == "Backwards") {
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
			Engine.MovePlayer(DeltaX, DeltaY);
			if (CurrentKey == "SwapWeapon") {Engine.PlayerSwapWeapon();}
			System.out.print(CurrentKey + "\n");
			Engine.SetCurrentPlayerDirrection(CurrentKey);
		}
		
	}
	
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
	
	public void SetOneKey(boolean b) {
		OneKey = b;
	}
}
//	private void SceneListen(JFrame f) {
//		JPanel Panel = (JPanel) f.getContentPane();
//		AddKeyBind(Panel, "ESCAPE", Close);
//		AddKeyBind(Panel, "W", MoveW);
//		AddKeyBind(Panel, "A", MoveA);
//		AddKeyBind(Panel, "S", MoveS);
//		AddKeyBind(Panel, "D", MoveD);
//		AddKeyBind(Panel, "ENTER", Enter);
//	}
//	
//	private void AddKeyBind(JComponent Panel, String Key, Action A) {
//		InputMap InputMap= Panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
//		ActionMap ActionMap = Panel.getActionMap();
//		
//		InputMap.put(KeyStroke.getKeyStroke(Key), Key);
//		ActionMap.put(Key, A);
//	}
//	
//	Action Close = new AbstractAction() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			Engine.SetState(4);
//		}
//	};
//
//	Action Enter = new AbstractAction() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			if(Engine.GetState() == 0 || Engine.GetState() == 1 || Engine.GetState() == 3) {
//				Engine.SelectButton();
//			}	
//			else if(Engine.GetState() == 2 ) {
//			
//			}
//		}
//	};
//	
//	Action MoveW = new AbstractAction() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			if(Engine.GetState() == 0 || Engine.GetState() == 1 || Engine.GetState() == 3) {
//				Engine.SwitchButton(-1);
//			}
//			else if(Engine.GetState() == 2 ) {
//				Engine.MovePlayer(0, -3);
//			}
//		}
//	};
//	
//	Action MoveA = new AbstractAction() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			if(Engine.GetState() == 0 || Engine.GetState() == 1 ) {
//				
//			}
//			else if(Engine.GetState() == 2 ) {
//				Engine.MovePlayer(-3, 0);
//			}
//			else if(Engine.GetState() == 3 && Engine.GetSoundMenu().GetSelected() == 1) {
//				Engine.SwitchButton(-1);
//			}
//		}
//	};
//	
//	Action MoveS = new AbstractAction() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			if(Engine.GetState() == 0 || Engine.GetState() == 1 ) {
//				Engine.SwitchButton(1);
//			}
//			else if(Engine.GetState() == 3) {
//				if(Engine.GetSoundMenu().GetSelected() == 0 ) {
//					Engine.SwitchButton(2);	
//				}
//				else {
//					Engine.SwitchButton(1);	
//				}
//				
//			}
//			else if(Engine.GetState() == 2 ) {
//				Engine.MovePlayer(0, 3);
//			}
//		}
//	};
//	
//	Action MoveD = new AbstractAction() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			if(Engine.GetState() == 0 || Engine.GetState() == 1 ) {
//				
//			}
//			else if(Engine.GetState() == 2 ) {
//				Engine.MovePlayer(3, 0);
//			}
//			else if(Engine.GetState() == 3 && Engine.GetSoundMenu().GetSelected() == 0) {
//				Engine.SwitchButton(1);
//			}
//		}
//	};
//}