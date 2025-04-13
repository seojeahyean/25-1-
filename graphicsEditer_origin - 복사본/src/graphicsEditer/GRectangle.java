package graphicsEditer;

import java.awt.Graphics2D;

public class GRectangle {
	private int x1, y1, x2, y2;

	public void setPoint1(int x, int y) {// ������ ��ġ ��� ����
		this.x1 = x;
		this.y1 = y;
	}

	public void setPoint2(int x, int y) {// ������ ũ�� ��� ����
		this.x2 = x;
		this.y2 = y;
	}

	public void draw(Graphics2D graphics2D) {// �׸��� �Լ�
		graphics2D.drawRect(x1, y1, x2 - x1, y2 - y1);
	}
	public boolean isMoved(int x, int y) {
	    int left = Math.min(x1, x2);
	    int right = Math.max(x1, x2);
	    int top = Math.min(y1, y2);
	    int bottom = Math.max(y1, y2);

	    return (x >= left && x <= right && y >= top && y <= bottom);
	}
	public void moveBy(int dx, int dy) {
	    this.x1 += dx;
	    this.y1 += dy;
	    this.x2 += dx;
	    this.y2 += dy;
	}
}
