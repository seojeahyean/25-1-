package frames;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import frames.GShapeToolBar.EShapeTool;
import shapes.GShape;
import transformers.GDrawer;
import transformers.GTransformer;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public enum EDrawingType{
		e2P,
		eNP
	}
	public enum EDrawingState {
		eIdel, e2P, eNP
	}
	
	private Vector<GShape> shapes;
	private GTransformer transformer;
	private GShape currentShape;
	private EShapeTool eShapeTool; // 제약조건
	private EDrawingState eDrawingState; //상태
	
	public GDrawingPanel() {
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler); //버튼 리스너
		this.addMouseMotionListener(mouseHandler);
		
		this.shapes = new Vector<GShape>(); //저장0개
		this.eShapeTool = null;
		this.eDrawingState = EDrawingState.eIdel;
	} 
	
	public void initialize() {
	}
	
	public void setEShapeTool(EShapeTool eShapeTool) {
		this.eShapeTool =eShapeTool;
	}
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		for(GShape shape : this.shapes) {
			shape.draw((Graphics2D)graphics);
		}
	}
	private void startDrawing(int x, int y) {
		//set shape
		this.currentShape = eShapeTool.newShape();
		this.shapes.add(this.currentShape);
		this.transformer = new GDrawer(this.currentShape);
		this.transformer.start((Graphics2D) getGraphics(), x, y);
	}

	private void keepDrawing(int x, int y) {
		this.transformer.drag((Graphics2D) getGraphics(), x, y);
		this.repaint();
	}

	private void addPoint(int x, int y) {

	}

	private void finishDrawing(int x, int y) {
		this.transformer.finish((Graphics2D) getGraphics(), x, y);
	}
	
	
	private class MouseHandler implements MouseListener,MouseMotionListener{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 1) {
				this.mouse1Clicked(e);
				}else if(e.getClickCount() == 2) {
					this.mouse1Clicked(e);
				}
			}
		
		private void mouse1Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdel) { // 현재의 상태
				//set transformer
				if(eShapeTool.getEDrawingType() == EDrawingType.e2P) { // 제약조건
					startDrawing(e.getX(),e.getY());
					eDrawingState = EDrawingState.e2P;
				} else if(eDrawingState == EDrawingState.e2P) {
					finishDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.eIdel;
				}
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if(eDrawingState==EDrawingState.e2P) {
				keepDrawing(e.getX(),e.getY());
			}
		}
		private void mouse2Clicked(MouseEvent e) {
			
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
