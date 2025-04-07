package graphicsEditer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public GDrawingPanel() {
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler); //버튼 리스너
		this.addMouseMotionListener(mouseHandler);
	}
	
	public void initialize() {
	}
	
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
	}
	
	public void draw(int x, int y, int w, int h) {
		Graphics2D graphics =(Graphics2D) this.getGraphics();
		graphics.setXORMode(getBackground());
		graphics.drawRect(x, y, w, h);
	}
	
	private class MouseHandler implements MouseListener,MouseMotionListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("mouseClicked");
			
			
		}
		private int x1,y1;
		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("mousePressed");
			this.x1=e.getX();
			this.y1=e.getY();
			this.x2=this.x1;
			this.y2=this.y1;
		}
		private int x2,y2; // 이점 저장
		private int ox2,oy2; 
		@Override
		public void mouseDragged(MouseEvent e) { //XOR로 그려야됨
			System.out.println("mouseDragged");
			//erase
			draw(x1,y1,x2-x1,y2-y1); 
			//draw
			this.x2=e.getX();
			this.y2=e.getY();
			draw(x1,y1,x2-x1,y2-y1);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("mouseMoved");
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("mouseReleased");
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("mouseEntered");
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("mouseExited");
			
		}

		
	}
}
