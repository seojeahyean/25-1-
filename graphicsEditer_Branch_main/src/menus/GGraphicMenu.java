package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import Frames.GDrawingPanel;
import global.GConstants.EGraphicMenuItem;
import global.GConstants.EMenuBar;

public class GGraphicMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private GDrawingPanel drawingPanel;
	
	public GGraphicMenu() {
		super(EMenuBar.graphicsMenu.getLabel());
		
		ActionHandler actionHandler = new ActionHandler();
		for(EGraphicMenuItem eMenuItem: EGraphicMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getName());
			menuItem.setActionCommand(eMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
	}
	
	public void initialize() {
	}
	
	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	private void lineThickness() {
		System.out.println("lineThickness");
	}
	
	private void lineStyle() {
		System.out.println("lineStyle");
	}
	
	private void fontStyle() {
		System.out.println("fontStyle");
	}
	
	private void fontSize() {
		System.out.println("fontSize");
	}
	
	private void invokeMethod(String methodName) {
		try {
			Method method = this.getClass().getDeclaredMethod(methodName);
			method.invoke(this);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			EGraphicMenuItem eMenuItem = EGraphicMenuItem.valueOf(event.getActionCommand());
			invokeMethod(eMenuItem.getMethodName());
		}
	}
} 