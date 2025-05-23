package transformers;

import java.awt.Graphics;
import java.awt.Graphics2D;

import shapes.GRectangle;
import shapes.GShape;

public class GMover extends GTransformer {

	private GShape shape;
	private int px, py;
	public GMover(GShape shape) {
		super(shape);
		this.shape = shape;
	}

	public void start(Graphics2D graphics, int x, int y) {
		this.px = x;
		this.py = y;
	}

	public void drag(Graphics2D graphics, int x, int y) {
		int dx = x - px;
		int dy = y - py;

		
		this.shape.getAffineTransform().scale(dx,dy);

		this.px = x;
		this.py = y;
	}

	public void finish(Graphics2D graphics, int x, int y) {
		
	}
	@Override
	public void addPoint(Graphics2D graphics, int x, int y) {
		// TODO Auto-generated method stub
		shape.addPoint(x, y);
	}

}
