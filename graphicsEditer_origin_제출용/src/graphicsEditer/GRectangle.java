package graphicsEditer;

import java.awt.Graphics2D;

class GRectangle {
	int x, y, width, height;

	public GRectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean contains(int mx, int my) {
		return mx >= x && mx <= x + width && my >= y && my <= y + height;
	}

	public void moveTo(int newX, int newY) {
		this.x = newX;
		this.y = newY;
	}

	public void draw(Graphics2D g) {
		g.drawRect(x, y, width, height);
	}
}
