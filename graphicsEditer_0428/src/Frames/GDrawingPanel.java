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
	private GTransformer transformer;

	public enum EDrawingType {
		e2P, eNP
	}

	public enum EDrawingState {
		eIdel, e2P, eNP
	}

	private Vector<GShape> shapes; // 도형 정보를 저장할 배열
	private EShapeType eShapeType; // 그려야 할 도형이 무엇인지 저장
	private EDrawingState eDrawingState;// 현재 그리기 상태 저장

	public GDrawingPanel() { //
		MouseHandler mouseHandler = new MouseHandler(); // 마우스핸들러 객체 생성
		this.addMouseListener(mouseHandler); // 마우스행들러 연결
		this.addMouseMotionListener(mouseHandler); // 마우스모션리스너 연결

		this.shapes = new Vector<GShape>();// 도형 정보 저장 배열 생성
		this.eShapeType = null;// 초기 도형 값은 아직 gShapeToolBar에서 넘어오지 않았으니 null
		this.eDrawingState = EDrawingState.eIdel;// 초기 그리기 상태도 null
	}

	public void initialize() {
		// TODO Auto-generated method stub

	}

	public void setEShapeType(EShapeType eShapeType) { // 마우스액션리스너에서 호출
		this.eShapeType = eShapeType;// 그릴 도형 지정
	}

	protected void paintComponent(Graphics graphics) { //
		super.paintComponent(graphics);// 나를 그림 JPanel을 해야할 일을 여기서해라 라고 먼저 선빵 침
		for (GShape shape : shapes) {
			shape.draw((Graphics2D) graphics);
		}

	}

	private void startDrawing(int x, int y) {// 마우스 핸들러에서 마우스 pressed 시에 호출
		GShape shape = eShapeType.newShape();
		transformer = new GTransformer(shape, getBackground());// mediator 생성
		Graphics2D grapgics2D = (Graphics2D) getGraphics();// 그래픽 선언
		grapgics2D.setXORMode(getBackground());// 그래픽 배경 정보 추출, XOR모드 적용
		transformer.start(grapgics2D, x, y);//
	}

	private void keepDrawing(int x, int y) {
		Graphics2D grapgics2D = (Graphics2D) getGraphics();// 그래픽 선언
		grapgics2D.setXORMode(getBackground());// 그래픽 배경 정보 추출, XOR모드 적용
		transformer.drag(grapgics2D, x, y);
	}

	private void addPoint(int x, int y) {// NPoint를 위한 그리기

	}

	private GShape finishDrawwing(int x, int y) {
		Graphics2D grapgics2D = (Graphics2D) getGraphics();// 그래픽 선언
		grapgics2D.setXORMode(getBackground());// 그래픽 배경 정보 추출, XOR모드 적용
		return transformer.finish(grapgics2D, x, y);

	}

	private class MouseHandler implements MouseListener, MouseMotionListener {//

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdel) {// 그리기 상태가 eIdel(아무것도 그리고 있지 않음)이라면
				// set transformer
				if (eShapeType == null || eShapeType == EShapeType.eSelect) {// 만일 EShapeType.eSelect라면(아직 안 선택했다면)
					// 아무것도 안함
				} else {// 무언가를 선택했다면
					startDrawing(e.getX(), e.getY());// startDrawing 호출
					eDrawingState = EDrawingState.e2P;// 드래그 방식은 e2P 타입만 그리기 때문에 eDrawingState에 2좌표형 도형 그리고 있다고 전달
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2P) {
				keepDrawing(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (transformer != null) {
				GShape shape = finishDrawwing(e.getX(), e.getY());
				shapes.add(shape);
				eDrawingState = EDrawingState.eIdel; // 드로잉 상태 초기화
				repaint(); // 화면 다시 그리기
				transformer = null;
			}
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