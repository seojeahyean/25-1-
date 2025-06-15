package shapes;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class GLine extends GShape {
    private Line2D line;
    private double origX, origY;
    public GLine() {
        super(new Line2D.Float(0, 0, 0, 0));
        this.line = (Line2D) this.getShape();
    }

    public void setPoint(int x, int y) {
        this.origX = x;
        this.origY = y;
        this.line.setLine(x, y, x, y);
    }

    public void dragPoint(int x, int y) {
        // 선의 경우 시작점은 고정하고 끝점만 이동
       
        this.line.setLine(origX, origY, x, y);
    }

    @Override
    public void addPoint(int x, int y) {
        // 선은 2점으로 그리므로 이 메서드는 사용하지 않음
    }
}