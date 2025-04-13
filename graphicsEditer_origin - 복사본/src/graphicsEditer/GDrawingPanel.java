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
	private Vector<GRectangle> rectangles;// 사각형들을 저장할 공간 설정
	private Vector<GPolygon> polygons;//폴리곤들을 저장할 공간 설정
	String selectedText;// 현재 선택된 도형
	String shapeCommand;// 현재 선택된 명령어
	private MouseListener currentMouseListener = null;// 이전에 사용한 마우스 리스너
	private MouseMotionListener currentMouseMotionListener = null;// 이전에 사용한 마우스 모션리스너
	private JPopupMenu gShape;// 우클릭 시 튀어나오는 리스트
	private GMainFrame gMainFrame;// 부모의 메서드를 부르기 위한 객체 받기

	public GDrawingPanel(GMainFrame gMainFrame) {
		this.gMainFrame = gMainFrame;
		this.rectangles = new Vector<GRectangle>();// Vector 생성
		gShape = new JPopupMenu();
		this.shapeCommand = "draw";// 초기의 설정은 draw 로 설정

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

	protected void paintComponent(Graphics graphics) {// paint()가 실행되면 이 함수가 실행
		super.paintComponent(graphics);// drawingPanel 그리기
		for (GRectangle rectangle : rectangles) {// rectangle이 남아있다면 그리기
			rectangle.draw((Graphics2D) graphics);
		}
		/* 아직 GPolygon이 구현이 안되어있음.
		for(GPolygon polygon : polygons) {//
//			polygon.draw((Graphics2D) graphics);
		}
		*/
	}

	public void readyToDraw(String selectedText) {// 어떤 도형을 그릴 지 설정-GMainFrame에서 호출
		// 어떤 도형을 받는지에 따라서 다른 MouseHandler를 받는다.
		this.selectedText = selectedText;// 그릴 도형 받기
		if (currentMouseListener != null || currentMouseMotionListener != null) {// 이전에 존재하는 마우스핸들러가 있다면 제거
			this.removeMouseListener(currentMouseListener);
			this.removeMouseMotionListener(currentMouseMotionListener);
		} // 1

		if (selectedText == "rectangle") {// 라디오버튼에서 rectangle을 선택했을 경우
			MouseHandler_Rectangle mouseHandler = new MouseHandler_Rectangle(this);
			this.addMouseListener(mouseHandler);
			this.addMouseMotionListener(mouseHandler);
			currentMouseListener = mouseHandler;
			currentMouseMotionListener = mouseHandler;
			// 2-1

		} else if (selectedText == "triangle") {// 라디오버튼에서 triangle을 선택했을 경우
			MouseHandler_Triangle mouseHandler = new MouseHandler_Triangle();
			this.addMouseListener(mouseHandler);
			this.addMouseMotionListener(mouseHandler);
			currentMouseListener = mouseHandler;
			currentMouseMotionListener = mouseHandler;
			// 2-2
		} else if (selectedText == "oval") {// 라디오버튼에서 oval을 선택했을 경우
			MouseHandler_Oval mouseHandler = new MouseHandler_Oval();
			this.addMouseListener(mouseHandler);
			this.addMouseMotionListener(mouseHandler);
			currentMouseListener = mouseHandler;
			currentMouseMotionListener = mouseHandler;
			// 2-3

		} else if (selectedText == "polygon") {// 라디오버튼에서 polygon을 선택했을 경우
			MouseHandler_Polygon mouseHandler = new MouseHandler_Polygon(this);
			this.addMouseListener(mouseHandler);
			this.addMouseMotionListener(mouseHandler);
			currentMouseListener = mouseHandler;
			currentMouseMotionListener = mouseHandler;
			// 2-4

		} else if (selectedText == "textbox") {// 라디오버튼에서 textbox을 선택했을 경우
			MouseHandler_Textbox mouseHandler = new MouseHandler_Textbox();
			this.addMouseListener(mouseHandler);
			this.addMouseMotionListener(mouseHandler);
			currentMouseListener = mouseHandler;
			currentMouseMotionListener = mouseHandler;
			// 2-5
		}
	}

	private class GShapeActionListener implements ActionListener {// GShape 메뉴(draw, resize, move, rotate)의
																	// ActionListener
		private GDrawingPanel g;

		public GShapeActionListener(GDrawingPanel g) {
			this.g = g;
		}

		@Override
		public void actionPerformed(ActionEvent e) {// 버튼 동작 시 GMainFrame의 isShapeCommand(shapeCommand); 호출

			JMenuItem clickedButton = (JMenuItem) e.getSource();
			g.shapeCommand = clickedButton.getText();// 부모의 shapeCommand를 클릭한 버튼의 이름으로 설정
			g.gMainFrame.isShapeCommand(g.shapeCommand);// gMainFrmae.isShapeCommand 호출

		}

	}

	private class MouseHandler_Rectangle implements MouseListener, MouseMotionListener {// 라디오버튼에서 사각형이 선택되었을 때
																						// drawingPanel에 장착되는

		private GDrawingPanel gDrawingPanel;
		private GTransformer transformer;

		public MouseHandler_Rectangle(GDrawingPanel gDrawingPanel) {
			this.gDrawingPanel = gDrawingPanel;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {// 좌클릭시 팝업/아니라면 transformer.start()
			if (e.isPopupTrigger()) {// 좌클릭 시 팝업 리스트
				gShape.show(e.getComponent(), e.getX(), e.getY());
				return;
			}
			
			transformer = new GTransformer();// 중재자 생성
			Graphics2D graphics2D = (Graphics2D) getGraphics();// 드로잉패널의 그래픽 정보
			graphics2D.setXORMode(getBackground());
			transformer.start(graphics2D, e.getX(), e.getY(), rectangles, gDrawingPanel.shapeCommand);
			// 그래픽정보, 마우스 위치, 존재하는 사각형 정보, 현재 수행 작업 전달
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
			if (e.isPopupTrigger()) {// 좌클릭 시 팝업
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
			
			Graphics2D graphics2D = (Graphics2D) getGraphics();// 드로잉패널의 그래픽 정보
			graphics2D.setXORMode(getBackground());
			
			transformer.dragPolygon(graphics2D, e.getX(),e.getY());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
			Graphics2D graphics2D = (Graphics2D) getGraphics();// 드로잉패널의 그래픽 정보
			graphics2D.setXORMode(getBackground());
			if(e.getClickCount()==2) {//더블 클릭시 종료
				
				transformer.finishPolygon(graphics2D);
				return;
			}
			//더블클릭이 아니라면
			transformer.startPolygon(graphics2D,e.getX(),e.getY());
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger()) {// 좌클릭 시 팝업 리스트
				gShape.show(e.getComponent(), e.getX(), e.getY());
				return;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {// 좌클릭 시 팝업
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
