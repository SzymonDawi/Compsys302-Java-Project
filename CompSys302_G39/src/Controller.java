import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class Controller {
	public void update(JFrame f, GameEngine Engine) {
		MainMenuListen(Engine);
	}
	
	public void MainMenuListen(GameEngine Engine) {
		Engine.GetButton(0).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.ButtonPressed(Engine.GetButton(0).getName());
				System.out.println("nice, you pressed " + Engine.GetButton(0).getName());
			}
		});
		
		Engine.GetButton(1).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.ButtonPressed(Engine.GetButton(1).getName());
				System.out.println("nice, you pressed " + Engine.GetButton(1).getName());
			}
		});
		
		
		Engine.GetButton(2).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.ButtonPressed(Engine.GetButton(2).getName());
				System.out.println("nice, you pressed " + Engine.GetButton(2).getName());
			}
		});
		
		Engine.GetButton(3).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent Event) {
				Engine.ButtonPressed(Engine.GetButton(3).getName());
				System.out.println("nice, you pressed " + Engine.GetButton(3).getName());
			}
		});
	}
}