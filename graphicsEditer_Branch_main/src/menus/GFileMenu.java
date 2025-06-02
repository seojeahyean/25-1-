package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
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
	private File dir;
	private File file;
	
	private GDrawingPanel drawingPanel;

	public GFileMenu() {
		super("File");

		ActionHandler actionHandler = new ActionHandler();
		for (EFileMenuItem eMenuItem : EFileMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getName());
			menuItem.setActionCommand(eMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
	}

	public void initialize() {
		this.dir = new File("C:\\Users\\NAM\\전공 자료\\work\\패턴중심적사고와프로그래밍\\directory");
		this.file = new File("newFile.gvs");
	}

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;

	}

	public void newPanel() {
		System.out.println("newPanel");
		if(!this.close()) {
			this.drawingPanel.initialize();
		}
	}

	public boolean close() {
		boolean bCancel = false;
		if (this.drawingPanel.isUpdated()) {
			int reply = JOptionPane.NO_OPTION;
			reply = JOptionPane.showConfirmDialog(this.drawingPanel, "변경내용을 저장 할까요?");
			if (reply == JOptionPane.CANCEL_OPTION) {
				bCancel = true;
			} else if (reply == JOptionPane.OK_OPTION) {
				this.saveAs();
			}
		}
		return bCancel;
	}

	public void open() {

		System.out.println("open");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("파일 열기");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Shape 파일 (*.shape)", "shape"));

		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				FileInputStream fileInputStream = new FileInputStream(fileChooser.getSelectedFile().getAbsolutePath());
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

				@SuppressWarnings("unchecked")
				Vector<GShape> shapes = (Vector<GShape>) objectInputStream.readObject();
				this.drawingPanel.setShapes(shapes);

				objectInputStream.close();
				fileInputStream.close();
			} catch (IOException | ClassNotFoundException e) {
				JOptionPane.showMessageDialog(this, "파일을 읽을 수 없습니다: " + e.getMessage(), "열기 오류",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	public void save() {
		System.out.println("save");
		if(this.file == null) {
			if(this.saveAs()) {
				try {
					/*
					 * FileOutputStream fileOutputStream = new FileOutputStream("file");
					 * ObjectOutputStream objectOutputStream = new
					 * ObjectOutputStream(fileOutputStream);
					 */
					FileOutputStream fileOutputStream = new FileOutputStream(this.file);
					BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

					objectOutputStream.writeObject((this.drawingPanel.getShapes()));
					objectOutputStream.close();
					this.drawingPanel.setBUpdated(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean saveAs() {
		System.out.println("SaveAs");
		boolean bCancel = false;
		JFileChooser chooser = new JFileChooser(this.dir);
		chooser.setSelectedFile(this.file);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data","gvs");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this.drawingPanel);
		if(returnVal==JFileChooser.APPROVE_OPTION) {
			this.dir = chooser.getCurrentDirectory();
			this.file = chooser.getSelectedFile();
			this.save();
		}else {
			bCancel = true;
		}
		return bCancel;
	}
//		JFileChooser chooser = new JFileChooser(this.dir);
//		chooser.setDialogTitle("다른 이름으로 저장");
//
//		// 파일 필터 설정 (확장자가 .shape인 파일만 저장 가능)
//		FileNameExtensionFilter filter = new FileNameExtensionFilter("Shape 파일 (*.shape)", "shape");
//		chooser.setFileFilter(filter);
//
//		int result = chooser.showSaveDialog(this);
//		if (result == JFileChooser.APPROVE_OPTION) {
//			try {
//				String filePath = chooser.getSelectedFile().getAbsolutePath();
//				// 확장자가 없는 경우 .shape 확장자 붙이기
//				if (!filePath.toLowerCase().endsWith(".shape")) {
//					filePath += ".shape";
//				}
//
//				FileOutputStream fileOutputStream = new FileOutputStream(filePath);
//				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//				ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
//
//				objectOutputStream.writeObject(this.drawingPanel.getShapes());
//				objectOutputStream.close();
//
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(this, "파일 저장 중 오류가 발생했습니다: " + e.getMessage(), "저장 오류",
//						JOptionPane.ERROR_MESSAGE);
//				e.printStackTrace();
//			}
//		}
//	}

	public void print() {
		System.out.println("print");
	}

	public void quit() {
		System.out.println("quit");
		if(!this.close()) {
			System.exit(0);
		}
	}

	private void invokeMethod(String methodName) {
		try {
			this.getClass().getMethod(methodName).invoke(this);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException exception) {
			exception.printStackTrace();
		}

	}

	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {

			EFileMenuItem eFileMenuItem = EFileMenuItem.valueOf(event.getActionCommand());
			invokeMethod(eFileMenuItem.getMethodName());

		}
	}

}
