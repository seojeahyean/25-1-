package Frames;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import Frames.GDrawingPanel.EShapeType;

public class GShapeToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;

	// components
	private JRadioButton rectangleButton;

	// associations
	private GDrawingPanel drawingPanel;

	public GShapeToolBar() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		for (EShapeType eShapeType : EShapeType.values()) {
			JRadioButton radioButton = new JRadioButton(eShapeType.getName());
			ActionHandler actionHandler = new ActionHandler();
			radioButton.addActionListener(actionHandler);
			radioButton.setActionCommand(eShapeType.toString());
			this.add(radioButton);
		}

	}

	public void initialize() {

	}

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String sShapeType = e.getActionCommand();
			EShapeType eShapeType = EShapeType.valueOf(sShapeType);
			drawingPanel.setEShapeType(eShapeType);
		}

	}
}
