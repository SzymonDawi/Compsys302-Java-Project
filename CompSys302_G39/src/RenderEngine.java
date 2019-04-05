import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderEngine {
	private JFrame f = new JFrame();
	private BufferedImage Screen = new BufferedImage(600,600,BufferedImage.TYPE_INT_RGB);
	
	RenderEngine(JFrame Frame){
		f = Frame;
	}
	
	public void render(GameEngine Engine) {
		Graphics2D g = Screen.createGraphics();
		
		int Height;
		int Width;
		int X;
		int Y;
		int i;
		
		for(i= 0;i < Engine.GetNumberOfObstacles(); i++) {
			Height = Engine.GetObstacle(i).GetHeight();
			Width = Engine.GetObstacle(i).GetWidth();
			X = Engine.GetObstacle(i).GetX();
			Y = Engine.GetObstacle(i).GetY();
			g.setColor(Color.RED);
			g.fillRect(X, Y, Width, Height);
		}
		
		JPanel panel = new JPanel() {
			@Override 
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Screen,0,0,null);
			}
		};
		
		for(i =0; i < Engine.GetNumberOfButtons(); i++) {
			panel.add(Box.createRigidArea(new Dimension(0, 60)));
			panel.add(Engine.GetButton(i));
			//Engine.GetButton(i).setVisible(true);
		}
		
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(panel);
		f.setSize(400,500);
		f.setVisible(true);
		
	}
}
