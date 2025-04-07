package graphicsEditer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int x1, y1, x2, y2;
	private boolean isDrawing = false;
	private GRectangle rect = null;
	private boolean isMoving = false;
	private int offsetX, offsetY;

	public GDrawingPanel() {
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler); // 踰꾪듉 由ъ뒪�꼫
		this.addMouseMotionListener(mouseHandler);

	}

	public void initialize() {
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		if (rect != null) {
			Graphics2D g2 = (Graphics2D) graphics;
			rect.draw(g2);
		}
	}

	public void draw(int x, int y, int w, int h) {
		Graphics2D graphics = (Graphics2D) this.getGraphics();
		graphics.setXORMode(getBackground());
		graphics.drawRect(x, y, w, h);
		graphics.dispose();
	}

	private class MouseHandler implements MouseListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("mouseClicked");

		}

//		private int x1,y1;
		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("mousePressed");
//			this.x1=e.getX();
//			this.y1=e.getY();
//			this.x2=this.x1;
//			this.y2=this.y1;
			int mx = e.getX(), my = e.getY();
			if (rect != null && rect.contains(mx, my)) {
				isMoving = true;
				offsetX = mx - rect.x;
				offsetY = my - rect.y;
			} else {
				isDrawing = true;
				x1 = mx;
				y1 = my;
				x2 = x1;
				y2 = y1;
			}
		}

//		private int x2, y2; // �씠�젏 ���옣
//		private int ox2, oy2;

		@Override
		public void mouseDragged(MouseEvent e) { // XOR濡� 洹몃젮�빞�맖
			System.out.println("mouseDragged");
//			//erase
//			draw(x1,y1,x2-x1,y2-y1); 
//			//draw
//			this.x2=e.getX();
//			this.y2=e.getY();
//			draw(x1,y1,x2-x1,y2-y1);
			if (isDrawing) {
				// [변경] 드래그 도중 임시 사각형 업데이트
				x2 = e.getX();
				y2 = e.getY();
				rect = new GRectangle(Math.min(x1, x2), Math.min(y1, y2),
						Math.abs(x2 - x1), Math.abs(y2 - y1));
				repaint(); // [추가]
			} else if (isMoving && rect != null) {
				// [추가] 사각형 이동 처리
				int newX = e.getX() - offsetX;
				int newY = e.getY() - offsetY;
				rect.moveTo(newX, newY);
				repaint();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("mouseMoved");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("mouseReleased");
			isDrawing = false;
			isMoving = false;

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
