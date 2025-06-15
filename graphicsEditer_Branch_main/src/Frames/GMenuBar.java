package Frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import global.GConstants.EAccountMenuItem;
import global.GConstants.EEditMenuItem;
import global.GConstants.EFileMenuItem;
import global.GConstants.EGraphicMenuItem;
import global.GConstants.EMenuBar;
import menus.GAccountMenu;
import menus.GEditMenu;
import menus.GFileMenu;
import menus.GGraphicMenu;

public class GMenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;
    //components
    private GFileMenu fileMenu;
    private GEditMenu editMenu;
    private GGraphicMenu graphicMenu;
    private GAccountMenu accountMenu;
    //associations
    private GDrawingPanel drawingPanel;
    
    public GMenuBar() {
        this.fileMenu = new GFileMenu();
        this.editMenu = new GEditMenu();
        this.graphicMenu = new GGraphicMenu();
        this.accountMenu = new GAccountMenu();
        
        this.add(this.fileMenu);
        this.add(this.editMenu);
        this.add(this.graphicMenu);
        this.add(this.accountMenu);
        
        this.setVisible(true);
    }

    public void initialize() {
        if (this.drawingPanel != null) {
            this.fileMenu.associate(this.drawingPanel);
            this.editMenu.associate(this.drawingPanel);
            this.graphicMenu.associate(this.drawingPanel);
            this.accountMenu.associate(this.drawingPanel);
            
            this.fileMenu.initialize();
            this.editMenu.initialize();
            this.graphicMenu.initialize();
            
            this.setVisible(true);
        }
    }

    public void associate(GDrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
        this.fileMenu.associate(drawingPanel);
        this.editMenu.associate(drawingPanel);
        this.graphicMenu.associate(drawingPanel);
        this.accountMenu.associate(drawingPanel);
    }
}