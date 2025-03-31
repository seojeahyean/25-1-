import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.*;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	int x;
	int y;
	int width;
	int height;
	
	public GDrawingPanel() {
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(x,y,width,height);
		 
	}

	public void initialize(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.repaint();
	}
}
