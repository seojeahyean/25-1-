package graphicsEditer;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GEditMenu extends JMenu {
private static final long serialVersionUID = 1L;
	
private JMenuItem property;
private JMenuItem undo;
private JMenuItem redo;

public GEditMenu() {
	super("Edit");
	this.property = new JMenuItem("Property");
	this.undo = new JMenuItem("Undo");
	this.redo = new JMenuItem("Redo");
	
	this.add(this.property);
	this.add(this.undo);
	this.add(this.redo);
}
}
