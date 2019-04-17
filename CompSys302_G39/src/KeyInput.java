import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{
	GameEngine Engine = new GameEngine();
	
	public KeyInput(GameEngine Engine) {
		this.Engine = Engine;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int Key = e.getKeyCode();
		
		if(Key == KeyEvent.VK_ESCAPE) {
			Engine.SetState(3);
		}
		else if(Key == KeyEvent.VK_W) {
			Engine.MovePlayer(0, 1);
		}
		else if(Key == KeyEvent.VK_A) {
			Engine.MovePlayer(-1, 0);
		}
		else if(Key == KeyEvent.VK_S) {
			Engine.MovePlayer(0, -1);
		}
		else if(Key == KeyEvent.VK_D) {
			Engine.MovePlayer(1, 0);
		}
		//System.out.println(Key);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int Key = e.getKeyCode();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
