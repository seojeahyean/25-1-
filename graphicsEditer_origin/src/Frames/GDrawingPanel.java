package Frames;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import Frames.GShapeToolBar.EShapeType;
import shapes.GRectangle;
import shapes.GShape;
import transformers.GTransformer;

public class GDrawingPanel extends JPanel {// 여기서는 그림 그리는걸 테스트함
	private static final long serialVersionUID = 1L;
//	boolean isDragging = false;
//	int offsetX, offsetY;

	public enum EDrawingType {
		e2P, eNP
	}

	public enum EDrawingState {
		eIdel, e2P, eNP
	}

	private Vector<GShape> shapes; // 네모를 저장할 빈 벡터
	private EShapeType eShapeType; // 툴바에서 얘를 세팅할 수 있게 함, 툴바에서 도구가 선택되면 여기서 세팅을 하게 함
	private EDrawingState eDrawingState;

	public GDrawingPanel() { // 생성자 진짜로 하려면 마우스 이벤터를 받아야함
		MouseHandler mouseHandler = new MouseHandler(); // 마우스 버튼 이벤트를 다루는 핸들러, 이벤트 핸들러를 드로잉 패널에 붙임, OS에서 온 이벤트를 받게 함
		this.addMouseListener(mouseHandler); // 부품 1개 / 마우스 버튼 리스너
		this.addMouseMotionListener(mouseHandler); // 부품 2개 마우스 모션 / 리스너 나머지 하나는 마우스 휠 리스너

		this.shapes = new Vector<GShape>();
		this.eShapeType = null;
		this.eDrawingState = null;
	}

	public void initialize() {
		// TODO Auto-generated method stub

	}

	public void setEShapeType(EShapeType eShapeType) { // 툴바와 연결?
		this.eShapeType = eShapeType;
	}

	protected void paintComponent(Graphics graphics) { // 나를 그려라, 오버라이딩, JPanel에 페인트 컴퍼넌트가 하는 일을 여기서 하게 함(부모가 해야할 일을 자식이
														// 대체한다는걸 오바라이딩),
		super.paintComponent(graphics);// 나를 그림 JPanel을 해야할 일을 여기서해라 라고 먼저 선빵 침
		for (GShape shape : shapes) { // 저장된 만큼
			shape.draw((Graphics2D) graphics); // 그려라
		}
		// 행위의 확장
	}

	private void startDrawing(int x, int y) {

	}

	private void keepDrawing(int x, int y) {

	}

	private void addPoint(int x, int y) {// NPoint를 위한 그리기

	}

	private void finishDrawwing(int x, int y) {

	}

	private class MouseHandler implements MouseListener, MouseMotionListener {// 드로잉 패널만 쓰는 클래스, OS가 호출할 수 있는 형태로만 만들었음
																				// 그 이름이 마우스 리스너 (인터페이스만 맞춰잠implements
																				// MouseListener/ 밑에 있는 함수들은 몸통을 만듬)

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("마우스Clicked");

		}

		private GTransformer transformer;

		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdel) {
				//set transformer
				if(eShapeType == EShapeType.eSelect) {
				
				}else {
					//set shape
//					GShape shape = eShapeType.newShape();
//					Transformer transformer = new Drawer(shape);
//					transformer.start(e.getX(), e.getY(), getGraphics());
					startDrawing(e.getX(),e.getY());
					eDrawingState = EDrawingState.e2P;
					
				}
				
			}
			
			transformer = new GTransformer(); // 여기가 문제라 뭘 줘야함
			Graphics2D grapgics2D = (Graphics2D) getGraphics();
			grapgics2D.setXORMode(getBackground());
			transformer.start(grapgics2D, e.getX(), e.getY()); // 컴보지션, 클래스 안에서 보면 부모가 다 보임
		
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if(eDrawingState==EDrawingState.e2P) {
				keepDrawing(e.getX(),e.getY());
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("마우스Move");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("마우스Released");
			Graphics2D graphics2D = (Graphics2D) getGraphics();
			graphics2D.setXORMode(getBackground());
			GRectangle rectangle = transformer.finish(graphics2D, e.getX(), e.getY()); // rectangle을 저장
			shapes.add(rectangle);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("마우스Entered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("마우스Exited");
		}
	}
}