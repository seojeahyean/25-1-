package transformers;

import java.awt.Graphics;
import java.awt.Graphics2D;

import shapes.GRectangle;
import shapes.GShape;

public class GDrawer extends GTransformer {

	private GShape shape;

	public GDrawer(GShape shape) {
		super(shape);
		this.shape = shape;
	}

	public void start(Graphics2D graphics, int x, int y) {
		shape.setPoint(x, y);
	}

	public void drag(Graphics2D graphics, int x, int y) {

		shape.dragPoint(x, y);

	}

	public void finish(Graphics2D graphics, int x, int y) {
		
	}
	@Override
	public void addPoint(Graphics2D graphics, int x, int y) {
		// TODO Auto-generated method stub
		shape.addPoint(x, y);
	}

}
