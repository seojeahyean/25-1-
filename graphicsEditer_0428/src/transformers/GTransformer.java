package transformers;

import java.awt.Color;
import java.awt.Graphics2D;
import shapes.GShape;

public class GTransformer {

    private GShape shape;
    private Color backgroundColor;
    
    public GTransformer(GShape shape, Color backgroundColor) {
        this.shape = shape;
        this.backgroundColor = backgroundColor;
    }

    public void start(Graphics2D graphics, int x, int y) {
    	graphics.setXORMode(backgroundColor);
        shape.setPoint(x, y);
    }

    public void drag(Graphics2D graphics, int x, int y) {
    	graphics.setXORMode(backgroundColor);
        shape.draw(graphics);   
        shape.dragPoint(x, y);    
        shape.draw(graphics);    
    }


    public GShape finish(Graphics2D graphics, int x, int y) {
    	graphics.setXORMode(backgroundColor);
        shape.dragPoint(x, y);
        return shape;
    }
}