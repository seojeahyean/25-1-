package graphicsEditer;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GGraphicMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private JMenuItem lineThicknessItem;
	private JMenuItem lineStyleItem;
	private JMenuItem fontStyleItem;
	private JMenuItem fontSizeItem;
	
	public GGraphicMenu() {
		
		super("Graphic");
		this.lineThicknessItem = new JMenuItem("Line thickness");
		this.lineStyleItem = new JMenuItem("Line style");
		this.fontStyleItem = new JMenuItem("Font style");
		this.fontSizeItem = new JMenuItem("Font size");
		
		this.add(this.lineThicknessItem);
		this.add(this.lineStyleItem);
		this.add(this.fontStyleItem);
		this.add(this.fontSizeItem);
	}
}