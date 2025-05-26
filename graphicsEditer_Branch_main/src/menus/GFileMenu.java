package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import Frames.GDrawingPanel;
import global.GConstants.EFileMenuItem;
import shapes.GShape;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private GDrawingPanel drawingPanel;
	
	public GFileMenu() {
		super("File");
		
		ActionHandler actionHandler = new ActionHandler();
		for(EFileMenuItem eMenuItem: EFileMenuItem.values()	) {
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
	public void newPanel() {
		System.out.println("newPanel");
	}
	public void open() {
		System.out.println("open");
	}
	public void save() {
		System.out.println("save");

		try {
			FileOutputStream fileOutputStream = new FileOutputStream("file");
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
			
			objectOutputStream.writeObject((this.drawingPanel.getShapes()));
			objectOutputStream.close();					
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void saveAs() {
		System.out.println("SaveAs");
	}
	public void print() {
		System.out.println("print");
	}
	public void quit() {
		System.out.println("quit");
	}
	private void invokeMethod(String methodName) {
		try {
			this.getClass().getMethod(methodName).invoke(this);
		}catch(NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|InvocationTargetException exception) {
			exception.printStackTrace();
		}
		
		

	
	}
	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			
			EFileMenuItem eFileMenuItem = EFileMenuItem.valueOf(event.getActionCommand());
			invokeMethod(eFileMenuItem.getMethodName());
			
			
		}
	}

}
