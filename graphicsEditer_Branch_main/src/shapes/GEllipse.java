package shapes;

import java.awt.geom.Ellipse2D;

public class GEllipse extends GShape {
    private Ellipse2D ellipse;
    private double origX, origY;
    public GEllipse() {
        super(new Ellipse2D.Float(0, 0, 0, 0));
        this.ellipse = (Ellipse2D) this.getShape();
    }

public void setPoint(int x, int y) {
    this.origX = x;
    this.origY = y;
    this.ellipse.setFrame(x, y, 0, 0);


}
public void dragPoint(int x, int y) {
    double ox = Math.min(origX, x);
    double oy = Math.min(origY, y);
    double w  = Math.abs(x - origX);
    double h  = Math.abs(y - origY);
    ellipse.setFrame(ox, oy, w, h);
}
public void addPoint(int x, int y) {
    this.ellipse.setFrame(x, y, 0, 0);
}
}