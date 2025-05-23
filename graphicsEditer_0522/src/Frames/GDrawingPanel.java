package Frames;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import global.GConstants.EAnchors;
import global.GConstants.EShapeTool;
import shapes.GShape;
import shapes.GShape.EPoints;
import transformers.GDrawer;
import transformers.GMover;
import transformers.GResizer;
import transformers.GTransformer;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int ANCHOR_SIZE = 6;

	public enum EDrawingState {
		eIdle, e2P, eNP
	}

	private Vector<GShape> shapes;
	private GTransformer transformer;
	private GShape currentShape;
	private GShape selectedShape;
	private EShapeTool eShapeTool;
	private EDrawingState eDrawingState;

	public GDrawingPanel() {
		MouseHandler mouseHandler = new MouseHandler();// 마우스 핸들러 생성
		this.addMouseListener(mouseHandler); // 마우스 핸들러 연결
		this.addMouseMotionListener(mouseHandler);// 마우스 모션 리스너 연결

		this.currentShape = null;// 초기값 설정 및 생성
		this.selectedShape = null;
		this.shapes = new Vector<GShape>();
		this.eShapeTool = null;
		this.eDrawingState = EDrawingState.eIdle;// 초시 그리기 상태는 eIdle

	}

	public void initialize() {
	}

	public void setEShapeTool(EShapeTool eShapeTool) {// GShapeToolBar에서 호출, 선택된 버튼의 Enum을 전달: eShapeTool에 저장
		this.eShapeTool = eShapeTool;

	}

	protected void paintComponent(Graphics graphics) {// paint()가 호출되면 작동
		super.paintComponent(graphics);
		Graphics2D g2 = (Graphics2D) graphics;
		for (GShape shape : this.shapes) {// shapes 배열 내에 들어있는 모든 도형을 그리기
			shape.draw((Graphics2D) graphics);// shape의 draw 함수 실행
		}

	}

	private GShape onShape(int x, int y) {// 좌표가 도형의 위에 있는지 판단하는 메서드
		for (GShape shape : this.shapes) {// shape들이 들어있는 배열 shapes를 순회
			if (shape.contains(x, y)) {// shape.contains를 실행
				return shape;// 포함 되어있다면 해당 shape 반환
			}
		}
		return null;// 포함하는 shape이 없다면 null 리턴
	}

	private void startTransform(int x, int y) {// 변환 시작
		// set shape
		this.currentShape = eShapeTool.newShape();// 변환하고자 하는 currentShape을 eShapeTool.newShape을 통해 생성
		this.shapes.add(this.currentShape);// shapes 배열에 currentShape 추가
		if (this.eShapeTool == EShapeTool.eSelect) {// 만일 선택된 버튼이 select 라면
			this.selectedShape = onShape(x, y);// 새 도형이 아닌 selectedShape에 대해서 처리, 해당 마우스 포인터 위치에 도형이 있나 판별
			if (this.selectedShape == null) {// 마우스가 빈곳을 눌렀다면
				this.transformer = new GDrawer(this.currentShape);// 선택도 빈 곳을 잡고 그리면 네모는 그려줘야 하기 때문에 그리기 객체 생성
			} else if (this.selectedShape.getESelectedAnchor() == EAnchors.eMM) {
				this.transformer = new GMover(this.selectedShape);// 이동 객체 생성
			} else if (this.selectedShape.getESelectedAnchor() == EAnchors.eRR) {
				this.transformer = null;
			} else {
				this.transformer = new GResizer(this.selectedShape);
			}

		} else {// 만일 선택된 버튼이 select가 아니라면
			this.transformer = new GDrawer(this.currentShape);// transformer를 그리기 도구로 설정
		}
		this.transformer.start((Graphics2D) getGraphics(), x, y);// transformer에게 작동 시작 선언
	}

	private void keepTransform(int x, int y) {// 변환 도중
		this.transformer.drag((Graphics2D) getGraphics(), x, y);// transformer에게 drag중임을 선언
		this.repaint();// 화면 재구성
	}

	private void addPoint(int x, int y) {
		this.transformer.addPoint((Graphics2D) getGraphics(), x, y);
	}

	private void finishTransform(int x, int y) {// 변환 종료
		this.transformer.finish((Graphics2D) getGraphics(), x, y);// transformer에게 변환 종료를 선언
		this.selectShape(this.currentShape);
		if (this.eShapeTool == EShapeTool.eSelect) {// 그중에 선택된 버튼이 select일 경우에는
			this.shapes.remove(this.shapes.size() - 1);// 제일 마지막에 그린 도형만 삭제, because 선택을 위한 드래그에서는 도형이 남아있을이유 없음
			for(GShape shape: this.shapes) {
				if(this.currentShape.contains(shape)) {
					shape.setSelected(true);
				}else {
					shape.setSelected(false);
				}
			}
		}
		this.repaint();// 화면 다시 그리기
	}

	private void selectShape(GShape shape) {
		for (GShape otherShape : this.shapes) {
			otherShape.setSelected(false);
		}
		shape.setSelected(true);
	}

	private void changeCursor(int x, int y) {
		if (this.eShapeTool == EShapeTool.eSelect) {
			this.selectedShape = onShape(x, y);
			if (this.selectedShape == null) {
				this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			} else {
				EAnchors eAnchor = this.selectedShape.getESelectedAnchor();
				this.setCursor(eAnchor.getCursor());
			}
		}
	}

	private class MouseHandler implements MouseListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {// 화면에 1회 클릭시
				this.mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {// 화면에 2회 클릭시
				this.mouse2Clicked(e);
			}
		}

		private void mouse1Clicked(MouseEvent e) {// 1회 클릭시
			if (eDrawingState == EDrawingState.eIdle) {// 그리기 상태가 eIdle이라면
				// set transformer
				if (eShapeTool.getEPoints() == EPoints.e2P) {// eIdle이고 e2P 그려야 함
					startTransform(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2P;
				} else if (eShapeTool.getEPoints() == EPoints.eNP) {// eIdle이고 eNP 그려야 함
					startTransform(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNP;
				}

			} else if (eDrawingState == EDrawingState.e2P) {// 그리기 상태가 e2P를 그리는 중이라면
				finishTransform(e.getX(), e.getY());// 변환 종료
				eDrawingState = EDrawingState.eIdle;// 그리기 상태 eIdle로 다시 변경
			} else if (eDrawingState == EDrawingState.eNP) {// 그리기 상태가 eNP라면
				addPoint(e.getX(), e.getY());// 점 추가
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {// 마우스 이동시
			if (eDrawingState == EDrawingState.e2P) {// 그리기 상태가 e2P라면
				keepTransform(e.getX(), e.getY());// keepTransform 실행
			} else if (eDrawingState == EDrawingState.eNP) {// 그리기 상태가 eNP 라면
				keepTransform(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			}
		}

		private void mouse2Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNP) {
				finishTransform(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseDragged(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}
