import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class ControllerAction extends AbstractAction implements ActionListener {
	private int DeltaX;
	private int DeltaY;
	private KeyStroke PressedKey;
	private boolean ListeningKey;
	private Controller Control;
	
	public ControllerAction(String Name, int DeltaX, int DeltaY, int KeyCode, Controller Control) {
		super(Name);
		
		this.DeltaX = DeltaX;
		this.DeltaY = DeltaY;
		this.Control = Control;
		
		PressedKey = KeyStroke.getKeyStroke(KeyCode, 0 , false);
		KeyStroke ReleasedKey = KeyStroke.getKeyStroke(KeyCode, 0 , true);
		Control.GetInputMap().put(PressedKey, getValue(Action.NAME));
		Control.GetInputMap().put(ReleasedKey, getValue(Action.NAME));
		Control.GetPanel().getActionMap().put(getValue(Action.NAME), this);
		ListeningKey = true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(ListeningKey) {
			Control.HandleKeyEvent(DeltaX, DeltaY, true);
			Control.GetInputMap().remove(PressedKey);
			ListeningKey =false;
			Control.SetKey((String)getValue(Action.NAME));
		}
		else {
			Control.HandleKeyEvent(-DeltaX, -DeltaY, true);
			Control.GetInputMap().put(PressedKey, getValue(Action.NAME));
			ListeningKey =true;
		}
	}

}
