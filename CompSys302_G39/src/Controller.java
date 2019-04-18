import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Controller{
	private GameEngine Engine = new GameEngine();
	
	public void update(JFrame f, GameEngine Engine) {
		this.Engine = Engine;
		//This switch statement changes what inputs we listen to.
		//If the game is in the main menu state, the controller will look for actions on the buttons
		//if its in the options menu state it looks for the options button actions.
		//if its in the scene state it looks for keyboard presses.
//		switch (Engine.GetState()){
//		case 0://main menu
//			break;
//		case 1://options menu
//			break;
//		case 2://Scene
//			SceneListen(f);
//			break;
//		case 3://Exit
//			break;
//		}
		SceneListen(f);
	}
		
	private void SceneListen(JFrame f) {
		JPanel Panel = (JPanel) f.getContentPane();
		AddKeyBind(Panel, "ESCAPE", Close);
		AddKeyBind(Panel, "W", MoveW);
		AddKeyBind(Panel, "A", MoveA);
		AddKeyBind(Panel, "S", MoveS);
		AddKeyBind(Panel, "D", MoveD);
		AddKeyBind(Panel, "ENTER", Enter);
	}
	
	private void AddKeyBind(JComponent Panel, String Key, Action A) {
		InputMap InputMap= Panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap ActionMap = Panel.getActionMap();
		
		InputMap.put(KeyStroke.getKeyStroke(Key), Key);
		ActionMap.put(Key, A);
	}
	
	Action Close = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Engine.SetState(3);
		}
	};

	Action Enter = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(Engine.GetState() == 0 || Engine.GetState() == 1 ) {
				Engine.SelectButton();
			}	
			else if(Engine.GetState() == 2 ) {
			
			}
		}
	};
	
	Action MoveW = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(Engine.GetState() == 0 || Engine.GetState() == 1 ) {
				Engine.SwitchButton(-1);
				System.out.println("up");
			}
			else if(Engine.GetState() == 2 ) {
				Engine.MovePlayer(0, -5);
			}
		}
	};
	
	Action MoveA = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(Engine.GetState() == 0 || Engine.GetState() == 1 ) {
				
			}
			else if(Engine.GetState() == 2 ) {
				Engine.MovePlayer(-5, 0);
			}
		}
	};
	
	Action MoveS = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(Engine.GetState() == 0 || Engine.GetState() == 1 ) {
				Engine.SwitchButton(1);
				System.out.println("Down");
			}
			else if(Engine.GetState() == 2 ) {
				Engine.MovePlayer(0, 5);
			}
		}
	};
	
	Action MoveD = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(Engine.GetState() == 0 || Engine.GetState() == 1 ) {
				
			}
			else if(Engine.GetState() == 2 ) {
				Engine.MovePlayer(5, 0);
			}
		}
	};
}