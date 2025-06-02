package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;

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
		/*try {
			    FileInputStream fileInputStream = new FileInputStream("file");
				BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
				ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
				this.drawingPanel.setShapes(objectInputStream.readObject());
				objectInputStream.close();
				
			} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		  */
		 
		System.out.println("open");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("���� �닿린");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Shape ���� (*.shape)", "shape"));
		
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				FileInputStream fileInputStream = new FileInputStream(
					fileChooser.getSelectedFile().getAbsolutePath());
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				
				@SuppressWarnings("unchecked")
				Vector<GShape> shapes = (Vector<GShape>) objectInputStream.readObject();
				this.drawingPanel.setShapes(shapes);
				this.drawingPanel.repaint();
				
				objectInputStream.close();
				fileInputStream.close();
			} catch (IOException | ClassNotFoundException e) {
				JOptionPane.showMessageDialog(this, 
					"���쇱�� �� �� ���듬����: " + e.getMessage(),
					"�닿린 �ㅻ�",
					JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
	public void save() {
		System.out.println("save");

		try {
						/*FileOutputStream fileOutputStream = new FileOutputStream("file");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream); */
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
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("�ㅻⅨ �대��쇰� ����");
		
		// ���� ���� �ㅼ�� (���μ��媛� .shape�� ���쇰� ����)
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Shape ���� (*.shape)", "shape");
		fileChooser.setFileFilter(filter);
		
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				// ���μ��媛� ���쇰㈃ .shape ���μ�� 異�媛�
				if (!filePath.toLowerCase().endsWith(".shape")) {
					filePath += ".shape";
				}
				
				FileOutputStream fileOutputStream = new FileOutputStream(filePath);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
				
				objectOutputStream.writeObject(this.drawingPanel.getShapes());
				objectOutputStream.close();
				
			} catch(IOException e) {
				JOptionPane.showMessageDialog(this, 
					"���� ���� 以� �ㅻ�媛� 諛������듬����: " + e.getMessage(),
					"���� �ㅻ�",
					JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
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
