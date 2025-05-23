package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	private JMenuItem newItem;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem saveAsItem;
	private JMenuItem quitItem;
	public GFileMenu() {
		super("File");
		
		this.newItem = new JMenuItem("new");
		this.openItem = new JMenuItem("open");
		this.saveItem = new JMenuItem("save");
		this.saveAsItem = new JMenuItem("save as");
		this.quitItem = new JMenuItem("quit");
		
		this.add(this.newItem);
		this.add(this.openItem);
		this.add(this.saveItem);
		this.add(this.saveAsItem);
		this.add(this.quitItem);
	}
}
