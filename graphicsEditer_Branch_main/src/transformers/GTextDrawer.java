package transformers;

import java.awt.Graphics;
import java.awt.Graphics2D;

import shapes.GRectangle;
import shapes.GShape;
import shapes.GText;

public class GTextDrawer extends GDrawer {

	private GShape shape;

	public GTextDrawer(GText text) {
		super(text);
		this.shape = text;
	}


@Override
	public void finish(Graphics2D graphics, int x, int y) {
		   super.finish(graphics, x, y);
		   ((GText) shape).editText();
	}



}
