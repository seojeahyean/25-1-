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
    //associations
    private GDrawingPanel drawingPanel;
    
    public GMenuBar() {
        this.fileMenu = new GFileMenu();
        this.editMenu = new GEditMenu();
        this.graphicMenu = new GGraphicMenu();
        
        this.add(this.fileMenu);
        this.add(this.editMenu);
        this.add(this.graphicMenu);
        
        GAccountMenu accountMenu = new GAccountMenu();
        this.add(accountMenu);
        
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
        for (int i = 0; i < this.getMenuCount(); i++) {
            if (this.getMenu(i) instanceof GFileMenu) {
                ((GFileMenu) this.getMenu(i)).associate(drawingPanel);
            } else if (this.getMenu(i) instanceof GEditMenu) {
                ((GEditMenu) this.getMenu(i)).associate(drawingPanel);
            } else if (this.getMenu(i) instanceof GGraphicMenu) {
                ((GGraphicMenu) this.getMenu(i)).associate(drawingPanel);
            } else if (this.getMenu(i) instanceof GAccountMenu) {
                ((GAccountMenu) this.getMenu(i)).associate(drawingPanel);
            }
        }
    }
}