import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MouseInput {
	private GameEngine Engine = new GameEngine();
	
	public void update(JFrame f, GameEngine Engine, Menu Main, Menu Options) {
		this.Engine = Engine;
		//This switch statement changes what inputs we listen to.
		//If the game is in the main menu state, the controller will look for actions on the buttons
		//if its in the options menu state it looks for the options button actions.
		//if its in the scene state it looks for keyboard presses.
		switch (Engine.GetGameState()){
		case 0://main menu
			MainMenuListen(Main); 
			break;
		case 1://options menu
			OptionsMenuListen(Options);
			break;
		case 2://Scene
			break;
		case 3://Exit
			break;
		}
	}
	
	public void MainMenuListen(Menu Main) {
		//Adds a listener to each button so that when you click it 
		//it tells the game engine what to do.
		Main.GetButton(0).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.SetState(2);//goes to playing state
			}
		});
		
		Main.GetButton(1).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				//Engine.SetState();
			}
		});
		
		Main.GetButton(2).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.SetState(1);//goes to options menu state
			}
		});
		
		Main.GetButton(3).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.SetState(3);//goes to close state
			}
		});
	}
	
	private void OptionsMenuListen(Menu Options) {
		//Adds an action listener to each button and tells the game engine 
		//what to do.
		Options.GetButton(0).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
			}
		});
		
		Options.GetButton(1).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
			}
		});
		
		Options.GetButton(2).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.SetState(0);//goes to options state
			}
		});
		
	}
}

