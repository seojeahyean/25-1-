package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import global.GConstants.EAnchors;

public abstract class GShape {
	private final static int ANCHOR_W = 10;
	private final static int ANCHOR_H = 10;

	public enum EPoints {
		e2P, eNP
	}
	


	private Shape shape;
	private Ellipse2D[] anchors;// 0512
	private boolean bSelected;// 0512
	private EAnchors eSelectedAnchor;// 0512
	private AffineTransform affineTransform;
	private int px, py;

	public AffineTransform getAffineTransform() {
		return this.affineTransform;
	}
	public GShape(Shape shape) {
		this.shape = shape;
		this.anchors = new Ellipse2D[EAnchors.values().length - 1];
		this.bSelected = false;
		this.eSelectedAnchor = null;
		this.affineTransform = new AffineTransform();
		for (int i = 0; i < this.anchors.length; i++) {
			this.anchors[i] = new Ellipse2D.Double();
		}
	}

	public Shape getShape() {
		return this.shape;
	}
	public Shape getTransformedShape() {
		return this.affineTransform.createTransformedShape(this.shape);
	}

	// getters and setters
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}

	public boolean isShelected() {
		return this.bSelected;
	}

	public EAnchors getESelectedAnchor() {
		return this.eSelectedAnchor;
	}

	// methods
	private void setAnchors() {
		Rectangle bounds = this.shape.getBounds();
		int dx = bounds.x;
		int by = bounds.y;
		int bw = bounds.width;
		int bh = bounds.height;

		int cx = 0;
		int cy = 0;
		for (int i = 0; i < this.anchors.length; i++) {
			switch (EAnchors.values()[i]) {
			case eSS:
				cx = dx + bw / 2;
				cy = by + bh;
				break;
			case eSE:
				cx = dx + bw;
				cy = by + bh;
				break;
			case eSW:
				cx = dx;
				cy = by + bh;
				break;
			case eNN:
				cx = dx + bw / 2;
				cy = by;
				break;
			case eNE:
				cx = dx + bw;
				cy = by;
				break;
			case eNW:
				cx = dx;
				cy = by;
				break;
			case eEE:
				cx = dx + bw;
				cy = by + bh / 2;
				break;
			case eWW:
				cx = dx;
				cy = by + bh / 2;
				break;
			case eRR:
				cx = dx + bw / 2;
				cy = by - 30;
				break;
			default:
				break;
			}
			
			anchors[i].setFrame(cx - ANCHOR_W / 2, cy - ANCHOR_H / 2, ANCHOR_W, ANCHOR_H);

		}

	}

	public void draw(Graphics2D graphics2D) {
		Shape transformedShape = this.affineTransform.createTransformedShape(shape);
		graphics2D.draw(transformedShape);
		if (bSelected) {
			// draw anchors
			this.setAnchors();
			for (int i = 0; i < this.anchors.length; i++) {
				Shape transformedAnchor = this.affineTransform.createTransformedShape(anchors[i]);
				Color penColor = graphics2D.getColor();
				graphics2D.setColor(graphics2D.getBackground());
				graphics2D.fill(transformedAnchor);
				graphics2D.setColor(penColor);
				graphics2D.draw(transformedAnchor);

			}

		}
	}

	public boolean contains(int x, int y) {
		if (bSelected) {
			for (int i = 0; i < this.anchors.length; i++) {
				Shape transformedAnchor = this.affineTransform.createTransformedShape(anchors[i]);
				if (transformedAnchor.contains(x, y)) {
					this.eSelectedAnchor = EAnchors.values()[i];
					return true;

				}
			}
		}
		Shape transformedShape = this.affineTransform.createTransformedShape(shape);
		if(transformedShape.contains(x, y)) {
			this.eSelectedAnchor = EAnchors.eMM;
			return true;
		}
		return false;
	}
	public boolean contains(GShape shape) {
		return this.shape.contains(shape.getShape().getBounds());
		
	};

	public abstract void setPoint(int x, int y);

	public abstract void addPoint(int x, int y);

	public abstract void dragPoint(int x, int y);
	public Rectangle getBounds() {
		return this.shape.getBounds();
	}
}
