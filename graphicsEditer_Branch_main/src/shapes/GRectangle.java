package shapes;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class GRectangle extends GShape {
	private Rectangle2D rectangle;
    private double origX, origY;//마우스 포인트 음수 좌표 설정을 위해 필요

	public GRectangle() {
		super(new Rectangle2D.Float(0, 0, 0, 0));
		this.rectangle = (Rectangle2D) this.getShape();
	
	}

	public void setPoint(int x, int y) {
		this.rectangle.setFrame(x, y, 0, 0);
		origX = x;
        origY = y;
	}

	public void dragPoint(int x, int y) {
		double ox = Math.min(origX, x);
		double oy = Math.min(origY, y);
		double w  = Math.abs(x - origX);
		double h  = Math.abs(y - origY);
		this.rectangle.setFrame(ox, oy, w, h);
	}

	@Override
	public void addPoint(int x, int y) {

	}



}
