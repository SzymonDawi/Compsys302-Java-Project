
import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class SwingTEST {
	public static void main(String[] args) {
		JFrame f= new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pane = new JPanel() {
			@Override 
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d=(Graphics2D)g;
				g2d.setColor(Color.RED);
				g.drawLine(5, 30,380,30);
				
			}
		};
		
		f.add(pane);
		f.setSize(400,210);
		f.setVisible(true);
	}
	
}

