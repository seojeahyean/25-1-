package transformers;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import global.GConstants.EAnchors;
import shapes.GShape;

public class GRotater extends GTransformer {

	private GShape shape;
	private int px, py;
	private Point2D center;

	public GRotater(GShape shape) {
		super(shape);
		this.shape = shape;

	}

	public void start(Graphics2D graphics, int x, int y) {
		this.px = x;
		this.py = y;

		this.center = new Point2D.Double(shape.getTransformedShape().getBounds().getCenterX(),
				shape.getTransformedShape().getBounds().getCenterY());
		this.shape.setOriginalShape(this.shape.getShape());
		
		

	}

	public void drag(Graphics2D graphics, int x, int y) {
		double angle1 = Math.atan2(py - center.getY(), px - center.getX());
		double angle2 = Math.atan2(y - center.getY(), x - center.getX());
		double theta = angle2-angle1;
		this.shape.getAffineTransform().rotate(theta, center.getX(), center.getY());
		px = x;
		py = y;
		
	}

	public void finish(Graphics2D graphics, int x, int y) {

	}

	@Override
	public void addPoint(Graphics2D graphics, int x, int y) {
		// TODO Auto-generated method stub
		shape.addPoint(x, y);
	}

}
