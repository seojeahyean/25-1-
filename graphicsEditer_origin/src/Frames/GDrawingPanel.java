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
//	boolean isDragging = false;
//	int offsetX, offsetY;

	public enum EDrawingType {
		e2P, eNP
	}

	public enum EDrawingState {
		eIdel, e2P, eNP
	}

	private Vector<GShape> shapes; // �׸� ������ �� ����
	private EShapeType eShapeType; // ���ٿ��� �긦 ������ �� �ְ� ��, ���ٿ��� ������ ���õǸ� ���⼭ ������ �ϰ� ��
	private EDrawingState eDrawingState;

	public GDrawingPanel() { // ������ ��¥�� �Ϸ��� ���콺 �̺��͸� �޾ƾ���
		MouseHandler mouseHandler = new MouseHandler(); // ���콺 ��ư �̺�Ʈ�� �ٷ�� �ڵ鷯, �̺�Ʈ �ڵ鷯�� ����� �гο� ����, OS���� �� �̺�Ʈ�� �ް� ��
		this.addMouseListener(mouseHandler); // ��ǰ 1�� / ���콺 ��ư ������
		this.addMouseMotionListener(mouseHandler); // ��ǰ 2�� ���콺 ��� / ������ ������ �ϳ��� ���콺 �� ������

		this.shapes = new Vector<GShape>();
		this.eShapeType = null;
		this.eDrawingState = null;
	}

	public void initialize() {
		// TODO Auto-generated method stub

	}

	public void setEShapeType(EShapeType eShapeType) { // ���ٿ� ����?
		this.eShapeType = eShapeType;
	}

	protected void paintComponent(Graphics graphics) { // ���� �׷���, �������̵�, JPanel�� ����Ʈ ���۳�Ʈ�� �ϴ� ���� ���⼭ �ϰ� ��(�θ� �ؾ��� ���� �ڽ���
														// ��ü�Ѵٴ°� ���ٶ��̵�),
		super.paintComponent(graphics);// ���� �׸� JPanel�� �ؾ��� ���� ���⼭�ض� ��� ���� ���� ħ
		for (GShape shape : shapes) { // ����� ��ŭ
			shape.draw((Graphics2D) graphics); // �׷���
		}
		// ������ Ȯ��
	}

	private void startDrawing(int x, int y) {

	}

	private void keepDrawing(int x, int y) {

	}

	private void addPoint(int x, int y) {// NPoint�� ���� �׸���

	}

	private void finishDrawwing(int x, int y) {

	}

	private class MouseHandler implements MouseListener, MouseMotionListener {// ����� �гθ� ���� Ŭ����, OS�� ȣ���� �� �ִ� ���·θ� �������
																				// �� �̸��� ���콺 ������ (�������̽��� ������implements
																				// MouseListener/ �ؿ� �ִ� �Լ����� ������ ����)

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("���콺Clicked");

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
			
			transformer = new GTransformer(); // ���Ⱑ ������ �� �����
			Graphics2D grapgics2D = (Graphics2D) getGraphics();
			grapgics2D.setXORMode(getBackground());
			transformer.start(grapgics2D, e.getX(), e.getY()); // �ĺ�����, Ŭ���� �ȿ��� ���� �θ� �� ����
		
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if(eDrawingState==EDrawingState.e2P) {
				keepDrawing(e.getX(),e.getY());
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("���콺Move");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("���콺Released");
			Graphics2D graphics2D = (Graphics2D) getGraphics();
			graphics2D.setXORMode(getBackground());
			GRectangle rectangle = transformer.finish(graphics2D, e.getX(), e.getY()); // rectangle�� ����
			shapes.add(rectangle);
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