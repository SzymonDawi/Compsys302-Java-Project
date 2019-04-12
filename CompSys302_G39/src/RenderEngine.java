
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderEngine {
	private JFrame f = new JFrame();
	private BufferedImage Screen = new BufferedImage(600,600,BufferedImage.TYPE_INT_RGB);
	private Map Map = new Map();
	
	RenderEngine(JFrame Frame){
		f = Frame;
	}

	
	
	public void render(GameEngine Engine, JPanel Main, JPanel Options) {
		Graphics2D g = Screen.createGraphics();	
		Map.init();

		int Height;
		int Width;
		int X;
		int Y;
		int i;
		
		JPanel panel = new JPanel() {
			@Override 
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Screen,0,0,null);
			}
		};
		
		if(Engine.GetGameState() == 3) {
			f.getContentPane().removeAll();
			for(i= 0;i < Engine.GetNumberOfObstacles(); i++) {
				Height = Engine.GetObstacle(i).GetHeight();
				Width = Engine.GetObstacle(i).GetWidth();
				X = Engine.GetObstacle(i).GetX();
				Y = Engine.GetObstacle(i).GetY();
				//g.setColor(Color.RED);
				//g.fillRect(X, Y, Width, Height);
				DoDrawing(g);
			}
		}
		
		if(Engine.GetGameState() == 0) {
			f.setContentPane(Main);
		}
		else if(Engine.GetGameState() == 1) {
			f.setContentPane(Options);
		}
		else if(Engine.GetGameState() == 3) {
			f.setContentPane(panel);
		}
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400,500);
		f.setVisible(true);
	}
	
	private void DoDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(Map.GetImage(),Map.GetX(),Map.GetY(),f);
	}
}