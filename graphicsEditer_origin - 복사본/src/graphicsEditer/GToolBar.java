package graphicsEditer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class GToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	GMainFrame g;
	ButtonGroup group = new ButtonGroup();
	JRadioButton rectangleButton;
	JRadioButton triangleButton;
	JRadioButton ovalButton;
	JRadioButton polygonButton;
	JRadioButton textBoxButton;
	
	public GToolBar(GMainFrame g) {
		this.g = g;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.rectangleButton = new JRadioButton("rectangle");
		this.triangleButton = new JRadioButton("triangle");
		this.ovalButton = new JRadioButton("oval");
		this.polygonButton = new JRadioButton("polygon");
		this.textBoxButton = new JRadioButton("textbox");// ��ư ����

		this.group.add(this.rectangleButton);// ��ư�� �׷�ȭ
		this.group.add(this.triangleButton);
		this.group.add(this.ovalButton);
		this.group.add(this.polygonButton);
		this.group.add(this.textBoxButton);
		
		this.add(this.rectangleButton);
		this.add(this.triangleButton);
		this.add(this.ovalButton);
		this.add(this.polygonButton);
		this.add(this.textBoxButton);
		
		ActionHandler actionHandler = new ActionHandler();
		
		rectangleButton.addActionListener(actionHandler);
	    triangleButton.addActionListener(actionHandler);
	    ovalButton.addActionListener(actionHandler);
	    polygonButton.addActionListener(actionHandler);
	    textBoxButton.addActionListener(actionHandler);
		
	}
	private class ActionHandler implements ActionListener {//

		@Override
		public void actionPerformed(ActionEvent e) {//��ư�� ������ ������ư ���ο��� � ��ư�� ���ȴ��� GMainFrame���� String selectedText ����
			JRadioButton selectedButton =  (JRadioButton) e.getSource();
			String selectedText = selectedButton.getText();
			g.readyToDraw(selectedText);

		}
		
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
