package Frames;

import javax.swing.JMenuBar;

import menus.GFileMenu;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	//components
	private GFileMenu fileMenu;
	//associations
	private GDrawingPanel drawingPanel;
	
	public GMenuBar() {
		this.fileMenu = new GFileMenu();
		this.add(this.fileMenu);
		this.fileMenu = new GFileMenu();
		this.add(this.fileMenu);
		this.fileMenu = new GFileMenu();
		this.add(this.fileMenu);
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
}
