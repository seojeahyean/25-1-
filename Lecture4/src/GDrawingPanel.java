import java.awt.Graphics;

import javax.swing.JPanel;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	
	public GDrawingPanel() {
		
	}


	public void initialize() {
		
	}
	protected void paintComponent(Graphics graphics) {//JPaneldmf ghkrwkdgks
		//GDrawingPanel이 해야할 일을 대신 했음
		super.paintComponent(graphics);
		this.draw(graphics);
		
	}
	public void draw(Graphics graphics) {
		graphics.drawRect(10,10,50,50);
	}
}
