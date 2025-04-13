package graphicsEditer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Vector<GRectangle> rectangles;// �簢������ ������ ���� ����
	private Vector<GPolygon> polygons;//��������� ������ ���� ����
	String selectedText;// ���� ���õ� ����
	String shapeCommand;// ���� ���õ� ��ɾ�
	private MouseListener currentMouseListener = null;// ������ ����� ���콺 ������
	private MouseMotionListener currentMouseMotionListener = null;// ������ ����� ���콺 ��Ǹ�����
	private JPopupMenu gShape;// ��Ŭ�� �� Ƣ����� ����Ʈ
	private GMainFrame gMainFrame;// �θ��� �޼��带 �θ��� ���� ��ü �ޱ�

	public GDrawingPanel(GMainFrame gMainFrame) {
		this.gMainFrame = gMainFrame;
		this.rectangles = new Vector<GRectangle>();// Vector ����
		gShape = new JPopupMenu();
		this.shapeCommand = "draw";// �ʱ��� ������ draw �� ����

		JMenuItem drawButton = new JMenuItem("draw");
		JMenuItem moveButton = new JMenuItem("move");
		JMenuItem rotateButton = new JMenuItem("rotate");
		JMenuItem resizeButton = new JMenuItem("resize");

		GShapeActionListener gShapeActionListener = new GShapeActionListener(this);

		drawButton.addActionListener(gShapeActionListener);
		moveButton.addActionListener(gShapeActionListener);
		rotateButton.addActionListener(gShapeActionListener);
		resizeButton.addActionListener(gShapeActionListener);

		gShape.add(drawButton);
		gShape.addSeparator();
		gShape.add(moveButton);
		gShape.add(rotateButton);
		gShape.add(resizeButton);

	}

	public void initialize() {
	}

	protected void paintComponent(Graphics graphics) {// paint()�� ����Ǹ� �� �Լ��� ����
		super.paintComponent(graphics);// drawingPanel �׸���
		for (GRectangle rectangle : rectangles) {// rectangle�� �����ִٸ� �׸���
			rectangle.draw((Graphics2D) graphics);
		}
		/* ���� GPolygon�� ������ �ȵǾ�����.
		for(GPolygon polygon : polygons) {//
//			polygon.draw((Graphics2D) graphics);
		}
		*/
	}

	public void readyToDraw(String selectedText) {// � ������ �׸� �� ����-GMainFrame���� ȣ��
		// � ������ �޴����� ���� �ٸ� MouseHandler�� �޴´�.
		this.selectedText = selectedText;// �׸� ���� �ޱ�
		if (currentMouseListener != null || currentMouseMotionListener != null) {// ������ �����ϴ� ���콺�ڵ鷯�� �ִٸ� ����
			this.removeMouseListener(currentMouseListener);
			this.removeMouseMotionListener(currentMouseMotionListener);
		} // 1

		if (selectedText == "rectangle") {// ������ư���� rectangle�� �������� ���
			MouseHandler_Rectangle mouseHandler = new MouseHandler_Rectangle(this);
			this.addMouseListener(mouseHandler);
			this.addMouseMotionListener(mouseHandler);
			currentMouseListener = mouseHandler;
			currentMouseMotionListener = mouseHandler;
			// 2-1

		} else if (selectedText == "triangle") {// ������ư���� triangle�� �������� ���
			MouseHandler_Triangle mouseHandler = new MouseHandler_Triangle();
			this.addMouseListener(mouseHandler);
			this.addMouseMotionListener(mouseHandler);
			currentMouseListener = mouseHandler;
			currentMouseMotionListener = mouseHandler;
			// 2-2
		} else if (selectedText == "oval") {// ������ư���� oval�� �������� ���
			MouseHandler_Oval mouseHandler = new MouseHandler_Oval();
			this.addMouseListener(mouseHandler);
			this.addMouseMotionListener(mouseHandler);
			currentMouseListener = mouseHandler;
			currentMouseMotionListener = mouseHandler;
			// 2-3

		} else if (selectedText == "polygon") {// ������ư���� polygon�� �������� ���
			MouseHandler_Polygon mouseHandler = new MouseHandler_Polygon(this);
			this.addMouseListener(mouseHandler);
			this.addMouseMotionListener(mouseHandler);
			currentMouseListener = mouseHandler;
			currentMouseMotionListener = mouseHandler;
			// 2-4

		} else if (selectedText == "textbox") {// ������ư���� textbox�� �������� ���
			MouseHandler_Textbox mouseHandler = new MouseHandler_Textbox();
			this.addMouseListener(mouseHandler);
			this.addMouseMotionListener(mouseHandler);
			currentMouseListener = mouseHandler;
			currentMouseMotionListener = mouseHandler;
			// 2-5
		}
	}

	private class GShapeActionListener implements ActionListener {// GShape �޴�(draw, resize, move, rotate)��
																	// ActionListener
		private GDrawingPanel g;

		public GShapeActionListener(GDrawingPanel g) {
			this.g = g;
		}

		@Override
		public void actionPerformed(ActionEvent e) {// ��ư ���� �� GMainFrame�� isShapeCommand(shapeCommand); ȣ��

			JMenuItem clickedButton = (JMenuItem) e.getSource();
			g.shapeCommand = clickedButton.getText();// �θ��� shapeCommand�� Ŭ���� ��ư�� �̸����� ����
			g.gMainFrame.isShapeCommand(g.shapeCommand);// gMainFrmae.isShapeCommand ȣ��

		}

	}

	private class MouseHandler_Rectangle implements MouseListener, MouseMotionListener {// ������ư���� �簢���� ���õǾ��� ��
																						// drawingPanel�� �����Ǵ�

		private GDrawingPanel gDrawingPanel;
		private GTransformer transformer;

		public MouseHandler_Rectangle(GDrawingPanel gDrawingPanel) {
			this.gDrawingPanel = gDrawingPanel;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {// ��Ŭ���� �˾�/�ƴ϶�� transformer.start()
			if (e.isPopupTrigger()) {// ��Ŭ�� �� �˾� ����Ʈ
				gShape.show(e.getComponent(), e.getX(), e.getY());
				return;
			}
			
			transformer = new GTransformer();// ������ ����
			Graphics2D graphics2D = (Graphics2D) getGraphics();// ������г��� �׷��� ����
			graphics2D.setXORMode(getBackground());
			transformer.start(graphics2D, e.getX(), e.getY(), rectangles, gDrawingPanel.shapeCommand);
			// �׷�������, ���콺 ��ġ, �����ϴ� �簢�� ����, ���� ���� �۾� ����
		}

		@Override
		public void mouseDragged(MouseEvent e) {

			Graphics2D graphics2D = (Graphics2D) getGraphics();
			graphics2D.setXORMode(getBackground());
			transformer.drag(graphics2D, e.getX(), e.getY());
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {// ��Ŭ�� �� �˾�
				gShape.show(e.getComponent(), e.getX(), e.getY());
				return;
			}

			Graphics2D graphics2D = (Graphics2D) getGraphics();
			graphics2D.setXORMode(getBackground());
			GRectangle rectangle = transformer.finish(graphics2D, e.getX(), e.getY());
			if(gDrawingPanel.shapeCommand.equals("draw")) {
			rectangles.add(rectangle);
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}

	private class MouseHandler_Triangle implements MouseListener, MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private class MouseHandler_Oval implements MouseListener, MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private class MouseHandler_Polygon implements MouseListener, MouseMotionListener {

		private GDrawingPanel gDrawingPanel;
		private GTransformer transformer = new GTransformer();
		
		public MouseHandler_Polygon (GDrawingPanel gDrawingPanel) {
			this.gDrawingPanel = gDrawingPanel;
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			
			Graphics2D graphics2D = (Graphics2D) getGraphics();// ������г��� �׷��� ����
			graphics2D.setXORMode(getBackground());
			
			transformer.dragPolygon(graphics2D, e.getX(),e.getY());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
			Graphics2D graphics2D = (Graphics2D) getGraphics();// ������г��� �׷��� ����
			graphics2D.setXORMode(getBackground());
			if(e.getClickCount()==2) {//���� Ŭ���� ����
				
				transformer.finishPolygon(graphics2D);
				return;
			}
			//����Ŭ���� �ƴ϶��
			transformer.startPolygon(graphics2D,e.getX(),e.getY());
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger()) {// ��Ŭ�� �� �˾� ����Ʈ
				gShape.show(e.getComponent(), e.getX(), e.getY());
				return;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {// ��Ŭ�� �� �˾�
				gShape.show(e.getComponent(), e.getX(), e.getY());
				return;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private class MouseHandler_Textbox implements MouseListener, MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
