package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;

public class GShape {
	protected Shape shape;
	
	public GShape() {	
	}
	
	public void draw(Graphics2D graphics2D) {
		graphics2D.draw(shape);
	}

}
