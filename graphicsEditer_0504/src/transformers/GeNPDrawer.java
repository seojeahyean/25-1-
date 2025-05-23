package transformers;

import java.awt.Graphics2D;

import shapes.GPolygon;
import shapes.GShape;

public class GeNPDrawer extends GTransformer {
	
	public GeNPDrawer(GShape shape) {
		super(shape);
		this.polygon = (GPolygon) shape;
	}

	private GPolygon polygon;

	@Override
	public void start(Graphics2D graphics, int x, int y) {
		// TODO Auto-generated method stub
		 polygon.addPoint(x, y);
	}

	@Override
	public void drag(Graphics2D graphics, int x, int y) {
		java.awt.Polygon awt = polygon.getPolygon();
		        int lastX = awt.xpoints[awt.npoints - 1];
		       int lastY = awt.ypoints[awt.npoints - 1];
		 graphics.drawLine(lastX, lastY, x, y);
	}

	@Override
	public void finish(Graphics2D graphics, int x, int y) {
		// TODO Auto-generated method stub
		polygon.addPoint(x, y);
	}
	
	
}
