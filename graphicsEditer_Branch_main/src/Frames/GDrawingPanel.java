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
import transformers.GRotater;
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
		MouseHandler mouseHandler = new MouseHandler();// 留��곗�� �몃�ㅻ�� ����
		this.addMouseListener(mouseHandler); // 留��곗�� �몃�ㅻ�� �곌껐
		this.addMouseMotionListener(mouseHandler);// 留��곗�� 紐⑥�� 由ъ�ㅻ�� �곌껐

		this.currentShape = null;// 珥�湲곌� �ㅼ�� 諛� ����
		this.selectedShape = null;
		this.shapes = new Vector<GShape>();
		this.eShapeTool = null;
		this.eDrawingState = EDrawingState.eIdle;// 珥�湲� 洹몃━湲� ������ eIdle

	}

	public void initialize() {
	}
	public Vector<GShape> getShapes(){
		return this.shapes;
	}
	public void setShapes(Vector<GShape> shapes) {
		this.shapes = shapes;
	}

	public void setEShapeTool(EShapeTool eShapeTool) {// GShapeToolBar���� �몄�, ������ 踰��쇱�� Enum�� ����: eShapeTool�� ����
		this.eShapeTool = eShapeTool;

	}

	protected void paintComponent(Graphics graphics) {// paint()媛� �몄���硫� ����
		super.paintComponent(graphics);
		Graphics2D g2 = (Graphics2D) graphics;
		for (GShape shape : this.shapes) {// shapes 諛곗�� �댁�� �ㅼ�댁���� 紐⑤�� ������ 洹몃━湲�
			shape.draw((Graphics2D) graphics);// shape�� draw �⑥�� �ㅽ��
		}

	}

	private GShape onShape(int x, int y) {// 醫���媛� ������ ���� ����吏� ���⑦���� 硫�����
		for (GShape shape : this.shapes) {// shape�ㅼ�� �ㅼ�댁���� 諛곗�� shapes瑜� ����
			if (shape.contains(x, y)) {// shape.contains瑜� �ㅽ��
				return shape;// �ы�� ���댁���ㅻ㈃ �대�� shape 諛���
			}
		}
		return null;// �ы�⑦���� shape�� ���ㅻ㈃ null 由ы��
	}

	private void startTransform(int x, int y) {// 蹂��� ����
		// set shape
		this.currentShape = eShapeTool.newShape();// 蹂�����怨��� ���� currentShape�� eShapeTool.newShape�� �듯�� ����
		this.shapes.add(this.currentShape);// shapes 諛곗�댁�� currentShape 異�媛�
		if (this.eShapeTool == EShapeTool.eSelect) {// 留��� ������ 踰��쇱�� select �쇰㈃
			this.selectedShape = onShape(x, y);// �� ������ ���� selectedShape�� ���댁�� 泥�由�, �대�� 留��곗�� �ъ�명�� ��移��� ������ ���� ��蹂�
			if (this.selectedShape == null) {// 留��곗�ㅺ� 鍮�怨녹�� �����ㅻ㈃
				this.transformer = new GDrawer(this.currentShape);// ������ 鍮� 怨녹�� �↔� 洹몃━硫� �ㅻえ�� 洹몃�ㅼ��� ��湲� ��臾몄�� 洹몃━湲� 媛�泥� ����
			} else if (this.selectedShape.getESelectedAnchor() == EAnchors.eMM) {
				this.transformer = new GMover(this.selectedShape);// �대�� 媛�泥� ����
			} else if (this.selectedShape.getESelectedAnchor() == EAnchors.eRR) {
				this.transformer = new GRotater(this.selectedShape);
			} else {
				this.transformer = new GResizer(this.selectedShape);
			}

		} else {// 留��� ������ 踰��쇱�� select媛� �����쇰㈃
			this.transformer = new GDrawer(this.currentShape);// transformer瑜� 洹몃━湲� ��援щ� �ㅼ��
		}
		this.transformer.start((Graphics2D) getGraphics(), x, y);// transformer��寃� ���� ���� ����
	}

	private void keepTransform(int x, int y) {// 蹂��� ��以�
		this.transformer.drag((Graphics2D) getGraphics(), x, y);// transformer��寃� drag以����� ����
		this.repaint();// ��硫� �ш뎄��
	}

	private void addPoint(int x, int y) {
		this.transformer.addPoint((Graphics2D) getGraphics(), x, y);
	}

	private void finishTransform(int x, int y) {// 蹂��� 醫�猷�
		this.transformer.finish((Graphics2D) getGraphics(), x, y);// transformer��寃� 蹂��� 醫�猷�瑜� ����
		this.selectShape(this.currentShape);
		if (this.eShapeTool == EShapeTool.eSelect) {// 洹몄��� ������ 踰��쇱�� select�� 寃쎌�곗����
			this.shapes.remove(this.shapes.size() - 1);// ���� 留�吏�留��� 洹몃┛ ����留� ����, because ������ ���� ����洹몄������ ������ �⑥�������댁�� ����
			for(GShape shape: this.shapes) {
				if(this.currentShape.contains(shape)) {
					shape.setSelected(true);
				}else {
					shape.setSelected(false);
				}
			}
		}
		this.repaint();// ��硫� �ㅼ�� 洹몃━湲�
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
			if (e.getClickCount() == 1) {// ��硫댁�� 1�� �대┃��
				this.mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {// ��硫댁�� 2�� �대┃��
				this.mouse2Clicked(e);
			}
		}

		private void mouse1Clicked(MouseEvent e) {// 1�� �대┃��
			if (eDrawingState == EDrawingState.eIdle) {// 洹몃━湲� ����媛� eIdle�대�쇰㈃
				// set transformer
				if (eShapeTool.getEPoints() == EPoints.e2P) {// eIdle�닿� e2P 洹몃�ㅼ�� ��
					startTransform(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2P;
				} else if (eShapeTool.getEPoints() == EPoints.eNP) {// eIdle�닿� eNP 洹몃�ㅼ�� ��
					startTransform(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNP;
				}

			} else if (eDrawingState == EDrawingState.e2P) {// 洹몃━湲� ����媛� e2P瑜� 洹몃━�� 以��대�쇰㈃
				finishTransform(e.getX(), e.getY());// 蹂��� 醫�猷�
				eDrawingState = EDrawingState.eIdle;// 洹몃━湲� ���� eIdle濡� �ㅼ�� 蹂�寃�
			} else if (eDrawingState == EDrawingState.eNP) {// 洹몃━湲� ����媛� eNP�쇰㈃
				addPoint(e.getX(), e.getY());// �� 異�媛�
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {// 留��곗�� �대����
			if (eDrawingState == EDrawingState.e2P) {// 洹몃━湲� ����媛� e2P�쇰㈃
				keepTransform(e.getX(), e.getY());// keepTransform �ㅽ��
			} else if (eDrawingState == EDrawingState.eNP) {// 洹몃━湲� ����媛� eNP �쇰㈃
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
