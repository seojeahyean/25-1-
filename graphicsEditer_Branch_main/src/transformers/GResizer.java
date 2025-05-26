
package transformers;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import global.GConstants.EAnchors;

import global.GConstants.EAnchors;
import shapes.GShape;

public class GResizer extends GTransformer { //파사드?

	private GShape shape;
//	private Rectangle bounds;
	private EAnchors eResizeAnchor;

	private int px, py;
	private int cx, cy; //앵커의 기준점

	public GResizer(GShape shape) {
		super(shape);
		this.shape = shape;
		this.eResizeAnchor=null;

	}
	
	public void start(Graphics2D graphics, int x, int y) {


		this.px = x;
		this.py = y;
		Rectangle r = this.shape.getBounds();

		EAnchors eSelectedAnchor = this.shape.getESelectedAnchor();
		switch (eSelectedAnchor) {
			case eNW: eResizeAnchor =EAnchors.eSE; cx=r.x+r.width;   cy=r.y+r.height;    break;
			case eWW: eResizeAnchor =EAnchors.eEE; cx=r.x+r.width;   cy=r.y+r.height/2;  break;
			case eSW: eResizeAnchor =EAnchors.eNE; cx=r.x+r.width;   cy=r.y;             break;
			case eSS: eResizeAnchor =EAnchors.eNN; cx=r.x+r.width/2; cy=r.y;             break;
			case eSE: eResizeAnchor =EAnchors.eNW; cx=r.x;           cy=r.y;             break;
			case eEE: eResizeAnchor =EAnchors.eWW; cx=r.x;           cy=r.y+r.height/2;  break;
			case eNE: eResizeAnchor =EAnchors.eSW; cx=r.x;           cy=r.y+r.height;    break;
			case eNN: eResizeAnchor =EAnchors.eSS; cx=r.x+r.width/2; cy=r.y+r.height;    break;
			default: break;
		}

	}
	public void drag(Graphics2D graphics, int x, int y) {

		double dx = 0; double dy = 0;
		switch (eResizeAnchor) {
			case eNW: dx = (x-px);   dy=(y-py);     break;
			case eWW: dx = (x-px);   dy = 0;        break;
			case eSW: dx = (x-px);   dy = -(y-py);  break;
			case eSS: dx = 0; 	     dy = -(y-py);  break;
			case eSE: dx = -(x-px);  dy = -(y-py);  break;
			case eEE: dx = -(x-px);  dy = 0;        break;
			case eNE: dx = -(x-px);  dy = (y-py);   break;
			case eNN: dx = 0; 	     dy = (y-py);   break;
			default:
				break;
		}
		Shape transformedShape = this.shape.getTransformedShape();
		
		//얼마만큼 늘어났냐
		double w1 = transformedShape.getBounds().width;
		double w2 = dx+w1;
		double h1 = transformedShape.getBounds().height;
		double h2 = dy+h1;

		double xScale = w2/w1;
		double yScale = h2/h1;

		this.shape.getAffineTransform().translate(cx, cy);
		this.shape.getAffineTransform().scale(xScale, yScale);
		this.shape.getAffineTransform().translate(-cx, -cy);


		px = x;
		py = y;
	}

	public void finish(Graphics2D graphics, int x, int y) {
	}

	@Override
	public void addPoint(Graphics2D graphics, int x, int y) {
		// TODO Auto-generated method stub
		shape.addPoint(x, y);
	}
}
