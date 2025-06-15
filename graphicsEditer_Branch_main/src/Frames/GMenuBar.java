package Frames;

import javax.swing.JMenuBar;
import javax.swing.JMenu;

import menus.GFileMenu;
import menus.GEditMenu;
import menus.GGraphicMenu;

public class GMenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;
    //components
    private GFileMenu fileMenu;
    private GEditMenu editMenu;
    private GGraphicMenu graphicMenu;
    //associations
    private GDrawingPanel drawingPanel;
    
    public GMenuBar() {
        this.fileMenu = new GFileMenu();
        this.editMenu = new GEditMenu();
        this.graphicMenu = new GGraphicMenu();
        
        this.add(this.fileMenu);
        this.add(this.editMenu);
        this.add(this.graphicMenu);
        
        this.setVisible(true);
    }

    public void initialize() {
        if (this.drawingPanel != null) {
            this.fileMenu.associate(this.drawingPanel);
            this.editMenu.associate(this.drawingPanel);
            this.graphicMenu.associate(this.drawingPanel);
            
            this.fileMenu.initialize();
            this.editMenu.initialize();
            this.graphicMenu.initialize();
            
            this.setVisible(true);
        } else {
        }
    }

    public void associate(GDrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }
}