import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class GToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;

	ButtonGroup group = new ButtonGroup();

	JRadioButton rectangleButton;
	JRadioButton triangleButton;
	JRadioButton ovalButton;
	JRadioButton polygonButton;
	JRadioButton textBoxButton;
	GShape gShape;
	private GMainFrame mainFrame;

	public GToolBar(GMainFrame mainFrame) {

		// attributes�� ���� ����
		this.mainFrame = mainFrame;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));// �������� ��ܿͶ�

		// component�� ���� ����
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

		gShape = new GShape();// GShape ����

		gShape.setEnabled(false);// �ʱ⿣ ��Ȱ��ȭ

		gShape.setEnabled(false);

		this.add(this.rectangleButton);
		this.add(this.triangleButton);
		this.add(this.ovalButton);
		this.add(this.polygonButton);
		this.add(this.textBoxButton);
		this.add(this.gShape);

		rectangleButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean selected = e.getStateChange() == ItemEvent.SELECTED;
				gShape.setEnabled(selected);
			}
		});

		gShape.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectAction = (String) gShape.getSelectedItem();
				if (selectAction != null) {
					System.out.println(selectAction + " ���� �����!");

					if (selectAction.equals("Draw")) {
						DrawRect(selectAction);
						mainFrame.initialize();
					} else if (selectAction.equals("Move")) {
						// Move ��� ����
					}
				}
			}
		});
	}

	public void initialize() {
		// TODO Auto-generated method stub

	}

	private void DrawRect(String shape) {
		mainFrame.initialize();
		System.out.println(shape + "����");
	}

}
