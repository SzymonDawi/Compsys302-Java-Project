import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu{
	private JPanel Panel = new JPanel();
	private ArrayList<JButton> ListOfButtons = new ArrayList<JButton>();
	
	public void AddButton(String Text, int W, int H, int X, int Y) {
		Panel.add(Box.createRigidArea(new Dimension(0, 60)));
		JButton b = new JButton(Text);
		b.setName(Text);
		b.setAlignmentX(b.CENTER_ALIGNMENT);
		ListOfButtons.add(b);
		Panel.add(b);
	}
	
	public JButton GetButton(int i) {
		return ListOfButtons.get(i);
	}
	
	public JPanel GetPanel() {
		Panel.setLayout(new BoxLayout(Panel,BoxLayout.Y_AXIS));
		return Panel;
	}
}
