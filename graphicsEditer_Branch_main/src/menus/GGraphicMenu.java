package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import global.GConstants.EGraphicMenuItem;

public class GGraphicMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	public GGraphicMenu() {
		super("Graphic");
		
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