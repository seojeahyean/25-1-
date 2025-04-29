package shapes;

import java.awt.Polygon;
import Frames.GDrawingPanel.EDrawingType;

public class GPolygon extends GShape {
	private Polygon polygon;

	public GPolygon() {
		this.polygon = new Polygon();
		this.shape = polygon;
	}

	public void addPoint(int x, int y) {
		this.polygon.addPoint(x, y);
	}

	@Override
	public EDrawingType getDrawingType() {
		return EDrawingType.eNP;
	}

	@Override
	public void setPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}
}