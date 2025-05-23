package transformers;

import java.awt.Graphics2D;

import shapes.GShape;

public class GResizer extends GTransformer{
	private GShape shape;
	private int px, py;
	public GResizer(GShape shape) {
		super(shape);
		this.shape = shape;
	}

	@Override
	public void start(Graphics2D graphics, int x, int y) {
//		this.px = x;
//		this.py = y;
//		
	}

	@Override
	public void drag(Graphics2D graphics, int x, int y) {
		this.shape.dragPoint(x, y);
	}

	@Override
	public void finish(Graphics2D graphics, int x, int y) {
		
	}

	@Override
	public void addPoint(Graphics2D graphics, int x, int y) {
		
	}

}
