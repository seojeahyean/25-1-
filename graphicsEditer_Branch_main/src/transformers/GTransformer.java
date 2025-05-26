package transformers;

import java.awt.Graphics;
import java.awt.Graphics2D;

import shapes.GRectangle;
import shapes.GShape;

public abstract class GTransformer {

	protected GShape shape;
	public GTransformer(GShape shape) {
		this.shape= shape;
	}
	
	public abstract void start(Graphics2D graphics, int x, int y) ;
	public abstract void drag(Graphics2D graphics, int x, int y);
	public abstract void finish(Graphics2D graphics, int x, int y);

	public abstract void addPoint(Graphics2D graphics, int x, int y);
	
}
