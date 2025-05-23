package shapes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public abstract class GShape {
	private final static int ANCHOR_W = 10;
	private final static int ANCHOR_H = 10;

	public enum EPoints {
		e2P, eNP
	}

	public enum EAnchors {
		eNN(new Cursor(Cursor.N_RESIZE_CURSOR)), // 위쪽 방향의 리사이즈 0
		eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)), // 우상 1
		eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)), // 좌상 2
		eSS(new Cursor(Cursor.S_RESIZE_CURSOR)), // 하단 3
		eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)), // 우하 4
		eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)), // 좌하 5
		eEE(new Cursor(Cursor.E_RESIZE_CURSOR)), // 우측 6
		eWW(new Cursor(Cursor.W_RESIZE_CURSOR)), // 좌측 7
		eRR(new Cursor(Cursor.HAND_CURSOR)), // 회전 8
		eMM(new Cursor(Cursor.MOVE_CURSOR));// 내부 요소9

		private Cursor cursor;

		private EAnchors(Cursor cursor) {
			this.cursor = cursor;
		}

		public Cursor getCursor() {
			// TODO Auto-generated method stub
			return this.cursor;
		}

	}

	private Shape shape;
	private Ellipse2D[] anchors;// 0512
	private boolean bSelected;// 0512
	private EAnchors eSelectedAnchor;// 0512
	private AffineTransform affineTransform;
	private int px, py;

	public GShape(Shape shape) {
		this.shape = shape;
		this.anchors = new Ellipse2D[EAnchors.values().length - 1];
		this.bSelected = false;
		this.eSelectedAnchor = null;
		this.affineTransform = new AffineTransform();
		for (int i = 0; i < this.anchors.length; i++) {
			this.anchors[i] = new Ellipse2D.Double();// 각 앵커들에게 그려질 도형 생성
		}
	}

	public Shape getShape() {
		return this.shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	// getters and setters
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}

	public boolean isShelected() {
		return this.bSelected;
	}

	public EAnchors getESelectedAnchor() {
		return this.eSelectedAnchor;
	}

	// methods
	private void setAnchors() {// 각 앵커들에 할당된 타원의 위치 지정
		Rectangle bounds = this.shape.getBounds();
		int bx = bounds.x;
		int by = bounds.y;
		int bw = bounds.width;
		int bh = bounds.height;

		int cx = 0;
		int cy = 0;
		for (int i = 0; i < this.anchors.length; i++) {
			switch (EAnchors.values()[i]) {
			case eSS:
				cx = bx + bw / 2;
				cy = by + bh;
				break;
			case eSE:
				cx = bx + bw;
				cy = by + bh;
				break;
			case eSW:
				cx = bx;
				cy = by + bh;
				break;
			case eNN:
				cx = bx + bw / 2;
				cy = by;
				break;
			case eNE:
				cx = bx + bw;
				cy = by;
				break;
			case eNW:
				cx = bx;
				cy = by;
				break;
			case eEE:
				cx = bx + bw;
				cy = by + bh / 2;
				break;
			case eWW:
				cx = bx;
				cy = by + bh / 2;
				break;
			case eRR:
				cx = bx + bw / 2;
				cy = by - 30;
				break;
			default:
				break;
			}
			anchors[i].setFrame(cx - ANCHOR_W / 2, cy - ANCHOR_H / 2, ANCHOR_W, ANCHOR_H);

		}

	}

	public void draw(Graphics2D graphics2D) {
		graphics2D.draw(this.shape);
		if (bSelected) {
			// draw anchors
			this.setAnchors();
			for (int i = 0; i < this.anchors.length; i++) {
				Color penColor = graphics2D.getColor();
				graphics2D.setColor(graphics2D.getBackground());
				graphics2D.fill(anchors[i]);
				graphics2D.setColor(penColor);
				graphics2D.draw(anchors[i]);

			}

		}
	}

	public boolean contains(int x, int y) {
		if (bSelected) {
			for (int i = 0; i < this.anchors.length; i++) {
				if (anchors[i].contains(x, y)) {
					this.eSelectedAnchor = EAnchors.values()[i];
					return true;

				}
			}
		}
		if (this.shape.contains(x, y)) {
			this.eSelectedAnchor = EAnchors.eMM;
			return true;
		}
		return false;
	}

	public EAnchors containsAnchor(int x, int y) {
		if (bSelected) {
			for (int i = 0; i < this.anchors.length; i++) {
				if (anchors[i].contains(x, y)) {
					this.eSelectedAnchor = EAnchors.values()[i];
					return eSelectedAnchor;

				}
			}

		}
		return null;
	}

	public abstract void setPoint(int x, int y);

	public abstract void addPoint(int x, int y);

	public abstract void dragPoint(int x, int y);

	public void translate(int tx, int ty) {

		this.affineTransform.translate(tx, ty);
	}
}
