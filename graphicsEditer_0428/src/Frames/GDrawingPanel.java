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

public class GDrawingPanel extends JPanel {// ���⼭�� �׸� �׸��°� �׽�Ʈ��
	private static final long serialVersionUID = 1L;
	private GTransformer transformer;

	public enum EDrawingType {
		e2P, eNP
	}

	public enum EDrawingState {
		eIdel, e2P, eNP
	}

	private Vector<GShape> shapes; // ���� ������ ������ �迭
	private EShapeType eShapeType; // �׷��� �� ������ �������� ����
	private EDrawingState eDrawingState;// ���� �׸��� ���� ����

	public GDrawingPanel() { //
		MouseHandler mouseHandler = new MouseHandler(); // ���콺�ڵ鷯 ��ü ����
		this.addMouseListener(mouseHandler); // ���콺��鷯 ����
		this.addMouseMotionListener(mouseHandler); // ���콺��Ǹ����� ����

		this.shapes = new Vector<GShape>();// ���� ���� ���� �迭 ����
		this.eShapeType = null;// �ʱ� ���� ���� ���� gShapeToolBar���� �Ѿ���� �ʾ����� null
		this.eDrawingState = EDrawingState.eIdel;// �ʱ� �׸��� ���µ� null
	}

	public void initialize() {
		// TODO Auto-generated method stub

	}

	public void setEShapeType(EShapeType eShapeType) { // ���콺�׼Ǹ����ʿ��� ȣ��
		this.eShapeType = eShapeType;// �׸� ���� ����
	}

	protected void paintComponent(Graphics graphics) { //
		super.paintComponent(graphics);// ���� �׸� JPanel�� �ؾ��� ���� ���⼭�ض� ��� ���� ���� ħ
		for (GShape shape : shapes) {
			shape.draw((Graphics2D) graphics);
		}

	}

	private void startDrawing(int x, int y) {// ���콺 �ڵ鷯���� ���콺 pressed �ÿ� ȣ��
		GShape shape = eShapeType.newShape();
		transformer = new GTransformer(shape, getBackground());// mediator ����
		Graphics2D grapgics2D = (Graphics2D) getGraphics();// �׷��� ����
		grapgics2D.setXORMode(getBackground());// �׷��� ��� ���� ����, XOR��� ����
		transformer.start(grapgics2D, x, y);//
	}

	private void keepDrawing(int x, int y) {
		Graphics2D grapgics2D = (Graphics2D) getGraphics();// �׷��� ����
		grapgics2D.setXORMode(getBackground());// �׷��� ��� ���� ����, XOR��� ����
		transformer.drag(grapgics2D, x, y);
	}

	private void addPoint(int x, int y) {// NPoint�� ���� �׸���

	}

	private GShape finishDrawwing(int x, int y) {
		Graphics2D grapgics2D = (Graphics2D) getGraphics();// �׷��� ����
		grapgics2D.setXORMode(getBackground());// �׷��� ��� ���� ����, XOR��� ����
		return transformer.finish(grapgics2D, x, y);

	}

	private class MouseHandler implements MouseListener, MouseMotionListener {//

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdel) {// �׸��� ���°� eIdel(�ƹ��͵� �׸��� ���� ����)�̶��
				// set transformer
				if (eShapeType == null || eShapeType == EShapeType.eSelect) {// ���� EShapeType.eSelect���(���� �� �����ߴٸ�)
					// �ƹ��͵� ����
				} else {// ���𰡸� �����ߴٸ�
					startDrawing(e.getX(), e.getY());// startDrawing ȣ��
					eDrawingState = EDrawingState.e2P;// �巡�� ����� e2P Ÿ�Ը� �׸��� ������ eDrawingState�� 2��ǥ�� ���� �׸��� �ִٰ� ����
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
				eDrawingState = EDrawingState.eIdel; // ����� ���� �ʱ�ȭ
				repaint(); // ȭ�� �ٽ� �׸���
				transformer = null;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("���콺Entered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("���콺Exited");
		}
	}
}