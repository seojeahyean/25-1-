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
import global.GConstants.EMenuBar;
import shapes.GShape;
import global.GConstants.EFileConstants;
import global.GConstants.EFileDialog;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private File dir;
	private File file;
	
	private GDrawingPanel drawingPanel;

	public GFileMenu() {
		super(EMenuBar.fileMenu.getLabel());
		ActionHandler actionHandler = new ActionHandler();
		for (EFileMenuItem eMenuItem: EFileMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getName());
			menuItem.setActionCommand(eMenuItem.getMethodName());
			menuItem.addActionListener(actionHandler);
			menuItem.setToolTipText(eMenuItem.getToolTipText());
			this.add(menuItem);
		}
		this.dir = new File(EFileConstants.defaultDirectory.getValue());
		this.file = new File(EFileConstants.defaultFileName.getValue());
	}

	public void initialize() {
		this.dir = new File(EFileConstants.defaultDirectory.getValue());
		this.file = new File(EFileConstants.defaultFileName.getValue());
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
			int reply = JOptionPane.showConfirmDialog(
				this.drawingPanel, 
				EFileDialog.saveConfirm.getText()
			);
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
		fileChooser.setDialogTitle(EFileDialog.openFileTitle.getText());
		fileChooser.setFileFilter(new FileNameExtensionFilter(
			EFileConstants.fileDescription.getValue(), 
			EFileConstants.fileExtension.getValue()
		));

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
				JOptionPane.showMessageDialog(
					this, 
					EFileDialog.fileReadError.getText() + e.getMessage(), 
					EFileDialog.openErrorTitle.getText(),
					JOptionPane.ERROR_MESSAGE
				);
				e.printStackTrace();
			}
		}
	}

	public void save() {
		System.out.println("save");
		if(this.file == null) {
			this.saveAs();
		}else {
			if(!this.saveAs()) {
				try {
					FileOutputStream fileOutputStream = new FileOutputStream(this.file);
					BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

					objectOutputStream.writeObject((this.drawingPanel.getShapes()));
					objectOutputStream.close();
					this.drawingPanel.setBUpdated(false);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(
						this, 
						EFileDialog.saveError.getText() + e.getMessage(), 
						EFileDialog.saveErrorTitle.getText(),
						JOptionPane.ERROR_MESSAGE
					);
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
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			EFileConstants.fileDescription.getValue(),
			EFileConstants.fileExtension.getValue()
		);
		chooser.setFileFilter(filter);
		int result = chooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			this.file = chooser.getSelectedFile();
			if (!this.file.getName().endsWith(EFileConstants.fileExtension.getValue())) {
				this.file = new File(this.file.getAbsolutePath() + "." + EFileConstants.fileExtension.getValue());
			}
			this.dir = this.file.getParentFile();
			this.save();
		} else if (result == JFileChooser.CANCEL_OPTION) {
			bCancel = true;
		}
		return bCancel;
	}

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
