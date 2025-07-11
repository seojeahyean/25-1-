package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import Frames.GDrawingPanel;
import global.GConstants.EEditMenuItem;
import global.GConstants.EMenuBar;

public class GEditMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private GDrawingPanel drawingPanel;
	
	public GEditMenu() {
		super(EMenuBar.editMenu.getLabel());
		
		ActionHandler actionHandler = new ActionHandler();
		for(EEditMenuItem eMenuItem: EEditMenuItem.values()) {
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
	
	private void property() {
		System.out.println("property");
	}
	
	private void undo() {
		System.out.println("undo");
	}
	
	private void redo() {
		System.out.println("redo");
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
			EEditMenuItem eMenuItem = EEditMenuItem.valueOf(event.getActionCommand());
			invokeMethod(eMenuItem.getMethodName());
		}
	}
} 