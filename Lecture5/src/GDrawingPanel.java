import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import java.awt.event.MouseEvent;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public GDrawingPanel() {
		mouseHandler mouseHandler = new mouseHandler();
		this.addMouseListener(mouseHandler);
	}

	public void initialize() {

	}

	protected void paintComponent(Graphics graphics) {// JPaneldmf ghkrwkdgks
		// GDrawingPanel이 해야할 일을 대신 했음
		super.paintComponent(graphics);

	}

	public void draw(int x, int y, int width, int height) {
		Graphics2D graphics = (Graphics2D) this.getGraphics();
		graphics.setXORMode(getBackground());
		graphics.drawRect(x, y, width, height);
	}

	private class mouseHandler implements MouseListener, MouseMotionListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("마우스클릭");

		}

		private int x1, y1;

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("마우스 Pressed");
			this.x1 = e.getX();
			this.y1 = e.getY();
			this.x2 = e.getX();
			this.y2 = e.getY();

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("마우스Released");

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("마우스Entered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("마우스클릭");
		}

		private int x2, y2;
		private int ox2, oy2;

		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("마우스드래그");
			draw(x1, y1, ox2 - x1, oy2 - y1); // 이전 위치 지우기
			this.ox2 = x2; 
			this.oy2 = y2;
			this.x2 = e.getX();
			this.y2 = e.getY();
			draw(x1, y1, x2 - x1, y2 - y1);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("마우스이동");
		}
	}
}