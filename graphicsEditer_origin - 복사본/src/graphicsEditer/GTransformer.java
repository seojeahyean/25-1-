package graphicsEditer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

public class GTransformer {
	private Vector<GRectangle> rectangles;
	GRectangle rectangle;
	private Vector<GPolygon> polygons;
	GPolygon polygon;
	String shapeCommand;
	int offsetX, offsetY;
	boolean isDrawingPolygon = false;

	public void start(Graphics2D graphics, int x, int y, Vector<GRectangle> rectangles, String shapeCommand) {
		this.rectangles = rectangles;
		this.shapeCommand = shapeCommand;// 현재 작업 종류 저장

		if (this.shapeCommand.equals("draw")) {//현재 작업이 draw 라면
			rectangle = new GRectangle();
			rectangle.setPoint1(x, y);
			rectangle.setPoint2(x, y);
		} else if (this.shapeCommand.equals("move")) {//현재 작업이 move라면
			for (GRectangle r : rectangles) {
				if (r.isMoved(x, y)) {
					this.rectangle = r;
					offsetX = x;
					offsetY = y;
					break;
				}
			}
			 if (this.rectangle == null) {
		            System.out.println("No Rectangle Exists.");
		            return; // 사각형이 없으면 아무 작업도 하지 않음
		        }
			
		}else if(this.shapeCommand.equals("rotate")) {
			
		}else if(this.shapeCommand.equals("resize")) {
			
		}
		
	}

	public void drag(Graphics2D graphics, int x, int y) {
		if (this.shapeCommand.equals("draw")) {
			rectangle.draw(graphics);
			rectangle.setPoint2(x, y);
			rectangle.draw(graphics);
		}else if (this.shapeCommand.equals("move")) {
			rectangle.draw(graphics);
			int dx = x - offsetX;
	        int dy = y - offsetY;
	        rectangle.moveBy(dx, dy);
	        rectangle.draw(graphics);
	        offsetX = x;
	        offsetY = y;
		}else if(this.shapeCommand.equals("rotate")) {
			
		}else if(this.shapeCommand.equals("resize")) {
			
		}
		
	}

	public GRectangle finish(Graphics2D graphics, int x, int y) {
	if (this.shapeCommand.equals("draw")) {
			return rectangle;	
		}else if (this.shapeCommand.equals("move")) {
			return rectangle;
		}else if(this.shapeCommand.equals("rotate")) {
			
		}else if(this.shapeCommand.equals("resize")) {
			
		}
	return rectangle;
		
	}

	public void startPolygon(Graphics2D graphics, int x, int y) {
		polygon = new GPolygon();
		System.out.println("polygon dot");
		polygon.pointSet(graphics, x, y);
		this.isDrawingPolygon = true;
	}
	public void dragPolygon(Graphics2D graphics, int x, int y) {
		if(this.isDrawingPolygon) {
			System.out.println("polygon processing");
			Point p = new Point(x,y);
			polygon.whilePolygonDrawing(graphics, p);
		}
		
	}
	public GPolygon finishPolygon(Graphics2D graphics) {
		if(this.isDrawingPolygon) {
			System.out.println("polygon end");
			this.isDrawingPolygon = false;
			return polygon;
		}
		return polygon;
		
	}
}
