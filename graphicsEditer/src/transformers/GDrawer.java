package transformers;

import java.awt.Graphics2D;

import shapes.GRectangle;
import shapes.GShape;

public class GDrawer extends GTransformer {

	private GRectangle rectangle;

	public GDrawer(GShape shape) {
		super(shape);
		this.rectangle =(GRectangle) shape;
	}
	
	public void start(Graphics2D graphics, int x, int y) {
		rectangle.setPoint(x, y);
		rectangle.dragPoint(x, y);
	}

	public void drag(Graphics2D graphics, int x, int y) {
		rectangle.dragPoint(x, y);
	}
	
	public void finish(Graphics2D graphics, int x, int y) {
		
	}

}
