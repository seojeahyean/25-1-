package shapes;

import java.awt.Graphics2D;
import java.awt.Polygon;

public class GPolygon extends GShape {
    private Polygon polygon;
    private boolean closed = false;

    public GPolygon() {
        polygon = new Polygon();
        this.shape = polygon;
    }

    public void addPoint(int x, int y) {
        polygon.addPoint(x, y);
    }

    public void close() {
        closed = true;
    }

    @Override
    public void draw(Graphics2D g) {
        int n = polygon.npoints;
        for (int i = 0; i < n - 1; i++) {
            g.drawLine(
                polygon.xpoints[i], polygon.ypoints[i],
                polygon.xpoints[i+1], polygon.ypoints[i+1]
            );
        }
        if (closed && n > 1) {
            g.drawLine(
                polygon.xpoints[n-1], polygon.ypoints[n-1],
                polygon.xpoints[0],   polygon.ypoints[0]
            );
        }
    }

    public Polygon getPolygon() {
        return polygon;
    }
}