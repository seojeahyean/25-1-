package graphicsEditer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

public class GPolygon {
	private Vector<Point> points = new Vector<>();
	private Point movingPoint;
	public void draw(Graphics2D graphics) {//�������� ������ �׸���
		if(this.points.size() > 1) {//points �迭�� ���� �ΰ� �̻��� ���
			 int[] xPoints = points.stream().mapToInt(p -> p.x).toArray();
	         int[] yPoints = points.stream().mapToInt(p -> p.y).toArray();
	         graphics.drawPolygon(xPoints, yPoints, points.size());
		}
	}
	public void whilePolygonDrawing(Graphics2D graphics, Point movingPoint) {
		if (this.movingPoint != null) {
		graphics.drawLine( points.get(points.size()-1).x,
				 points.get(points.size()-1).y, this.movingPoint.x, this.movingPoint.y	);
		}
		this.movingPoint = movingPoint;
		graphics.drawLine( points.get(points.size()-1).x,
		 points.get(points.size()-1).y, movingPoint.x, movingPoint.y	);
	}
	public void pointSet(Graphics2D graphics, int x, int y) {
		Point p = new Point();
		p.setLocation(x, y);
		points.add(p);
		if(points.size()>1) {//�迭�� �ΰ� �̻��� ���� ����Ǿ� ���� ���
			graphics.drawLine( points.get(points.size()-2).x,
					 points.get(points.size()-2).y, 
					 points.get(points.size()-1).x, 
					 points.get(points.size()-1).y );
		}else {
			Point firstPoint = new Point();
			firstPoint.setLocation(x, y);
			points.add(firstPoint);
		}
	}
}