import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class Controller {
	public void update(JFrame f, GameEngine Engine, Menu Main, Menu Options) {
		switch (Engine.GetGameState()){
		case 0:
			MainMenuListen(Main, Engine); 
			break;
		case 1:
			OptionsMenuListen(Options, Engine);
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		}
	}
	
	public void MainMenuListen(Menu Main,GameEngine Engine) {
		Main.GetButton(0).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.ButtonPressed(Main.GetButton(0).getName());
			}
		});
		
		Main.GetButton(1).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.ButtonPressed(Main.GetButton(1).getName());
			}
		});
		
		Main.GetButton(2).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.ButtonPressed(Main.GetButton(2).getName());
			}
		});
		
		Main.GetButton(3).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.ButtonPressed(Main.GetButton(3).getName());
			}
		});
	}
	
	private void OptionsMenuListen(Menu Options, GameEngine Engine) {
		Options.GetButton(0).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.ButtonPressed(Options.GetButton(0).getName());
			}
		});
		
		Options.GetButton(1).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.ButtonPressed(Options.GetButton(1).getName());
			}
		});
		
		Options.GetButton(2).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.ButtonPressed(Options.GetButton(2).getName());
			}
		});
		
	}
}