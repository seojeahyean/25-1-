package graphicsEditer;

import java.awt.Graphics2D;

public class GRectangle {
	private int x1, y1, x2, y2;

	public void setPoint1(int x, int y) {// 내부의 위치 요소 접근
		this.x1 = x;
		this.y1 = y;
	}

	public void setPoint2(int x, int y) {// 내부의 크기 요소 접근
		this.x2 = x;
		this.y2 = y;
	}

	public void draw(Graphics2D graphics2D) {// 그리기 함수
		graphics2D.drawRect(x1, y1, x2 - x1, y2 - y1);
	}
}
