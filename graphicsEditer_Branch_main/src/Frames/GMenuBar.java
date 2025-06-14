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
        //create menus
        this.fileMenu = new GFileMenu();
        this.editMenu = new GEditMenu();
        this.graphicMenu = new GGraphicMenu();
        
        //add menus to menubar
        this.add(this.fileMenu);
        this.add(this.editMenu);
        this.add(this.graphicMenu);
        
        //ensure menubar is visible
        this.setVisible(true);
        System.out.println("메뉴바 생성됨: " + this.getMenuCount() + "개의 메뉴");
    }

    public void initialize() {
        if (this.drawingPanel != null) {
            //associate menus with drawing panel
            this.fileMenu.associate(this.drawingPanel);
            this.editMenu.associate(this.drawingPanel);
            this.graphicMenu.associate(this.drawingPanel);
            
            //initialize menus
            this.fileMenu.initialize();
            this.editMenu.initialize();
            this.graphicMenu.initialize();
            
            //ensure menubar is visible after initialization
            this.setVisible(true);
            System.out.println("메뉴바 초기화됨: " + this.getMenuCount() + "개의 메뉴");
        } else {
            System.out.println("경고: drawingPanel이 null입니다");
        }
    }

    public void associate(GDrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
        System.out.println("메뉴바가 drawingPanel과 연결됨");
    }
}