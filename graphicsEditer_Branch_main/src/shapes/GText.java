package shapes;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JOptionPane;

public class GText extends GShape {
	private String innerText;
	private Rectangle2D textbox;
	private double origX, origY;

	public GText() {
		super(new Rectangle2D.Float(0, 0, 0, 0));
		innerText = "";
		textbox = (Rectangle2D) getShape();
	}

	@Override
	public void setPoint(int x, int y) {
		// TODO Auto-generated method stub
		textbox.setFrame(x, y, 0, 0);
		origX =x;
		origY = y;
	}

	@Override
	public void dragPoint(int x, int y) {
		// TODO Auto-generated method stub
		double ox = Math.min(origX, x);
		double oy = Math.min(origY, y);
		double w  = Math.abs(x - origX);
		double h  = Math.abs(y - origY);
		textbox.setFrame(ox, oy, w, h);

	}

	@Override
	public void addPoint(int x, int y) {
		editText();
	}

	public void editText() {
		String input = JOptionPane.showInputDialog(null, "Enter text:", innerText);
		if (input != null) {
			innerText = input;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		if (!innerText.isEmpty()) {
			g.setFont(new Font("Dialog", Font.PLAIN, 12));
			
			double x =  textbox.getX() + 4;
			double y =  textbox.getY() + g.getFontMetrics().getAscent() + 4;
			
			Point2D.Double pt = new Point2D.Double(x, y);
			Point2D transformed = getAffineTransform().transform(pt, null);
			
			g.drawString(innerText, (float)transformed.getX(), (float)transformed.getY());
		}
	}
}
