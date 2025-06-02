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

	// components
	private Vector<GShape> shapes;
	// working area
	private GTransformer transformer;
	private GShape currentShape;
	private GShape selectedShape;
	private EShapeTool eShapeTool;
	private EDrawingState eDrawingState;
	private boolean bUpdated;

	public GDrawingPanel() {
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);

		this.shapes = new Vector<GShape>();
		this.currentShape = null;
		this.selectedShape = null;
		this.eShapeTool = null;
		this.eDrawingState = EDrawingState.eIdle;
		this.bUpdated = false;
	}

	public void initialize() {
		this.shapes.clear();
		this.repaint();
	}

	// getter and setter
	public Vector<GShape> getShapes() {
		return this.shapes;
	}

	public void setShapes(Object shapes) {
		this.shapes = (Vector<GShape>) shapes;
		this.repaint();
	}

	public void setEShapeTool(EShapeTool eShapeTool) {
		this.eShapeTool = eShapeTool;
	}

	public boolean isUpdated() {
		return this.bUpdated;
	}
	public void setBUpdated(boolean bUpdated) {
		this.bUpdated = bUpdated;
		
	}

	// methods
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g2 = (Graphics2D) graphics;
		for (GShape shape : this.shapes) {
			shape.draw((Graphics2D) graphics);
		}
	}

	private GShape onShape(int x, int y) {
		for (GShape shape : this.shapes) {
			if (shape.contains(x, y)) {
				return shape;
			}
		}
		return null;
	}

	private void startTransform(int x, int y) {
		this.currentShape = eShapeTool.newShape();
		this.shapes.add(this.currentShape);
		if (this.eShapeTool == EShapeTool.eSelect) {
			this.selectedShape = onShape(x, y);
			if (this.selectedShape == null) {
				this.transformer = new GDrawer(this.currentShape);
			} else if (this.selectedShape.getESelectedAnchor() == EAnchors.eMM) {
				this.transformer = new GMover(this.selectedShape);
			} else if (this.selectedShape.getESelectedAnchor() == EAnchors.eRR) {
				this.transformer = new GRotater(this.selectedShape);
			} else {
				this.transformer = new GResizer(this.selectedShape);
			}
		} else {
			this.transformer = new GDrawer(this.currentShape);
		}
		this.transformer.start((Graphics2D) getGraphics(), x, y);
	}

	private void keepTransform(int x, int y) {
		this.transformer.drag((Graphics2D) getGraphics(), x, y);
		this.repaint();
	}

	private void addPoint(int x, int y) {
		this.transformer.addPoint((Graphics2D) getGraphics(), x, y);
	}

	private void finishTransform(int x, int y) {
		this.transformer.finish((Graphics2D) getGraphics(), x, y);
		this.selectShape(this.currentShape);
		if (this.eShapeTool == EShapeTool.eSelect) {
			this.shapes.remove(this.shapes.size() - 1);
			for (GShape shape : this.shapes) {
				if (this.currentShape.contains(shape)) {
					shape.setSelected(true);
				} else {
					shape.setSelected(false);
				}
			}
		}
		this.bUpdated = true;
		this.repaint();
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
			if (e.getClickCount() == 1) {
				this.mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {
				this.mouse2Clicked(e);
			}
		}

		private void mouse1Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (eShapeTool.getEPoints() == EPoints.e2P) {
					startTransform(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2P;
				} else if (eShapeTool.getEPoints() == EPoints.eNP) {
					startTransform(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNP;
				}
			} else if (eDrawingState == EDrawingState.e2P) {
				finishTransform(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			} else if (eDrawingState == EDrawingState.eNP) {
				addPoint(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2P) {
				keepTransform(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eNP) {
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
