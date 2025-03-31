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
	private JComboBox<String> GShape;
	private JButton actionButton;

	public GToolBar() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));// 왼쪽으로 당겨와라
		this.rectangleButton = new JRadioButton("rectangle");
		this.triangleButton = new JRadioButton("triangle");
		this.ovalButton = new JRadioButton("oval");
		this.polygonButton = new JRadioButton("polygon");
		this.textBoxButton = new JRadioButton("textbox");// 버튼 생성

		this.group.add(this.rectangleButton);// 버튼들 그룹화
		this.group.add(this.triangleButton);
		this.group.add(this.ovalButton);
		this.group.add(this.polygonButton);
		this.group.add(this.textBoxButton);

		GShape = new JComboBox<>();// GShape 생성
		GShape.addItem("Draw");
		GShape.addItem("Move");// 리스트들 담기
		GShape.addItem("Resize");
		GShape.addItem("Rotate");

		GShape.setEnabled(false);// 초기엔 비활성화

		rectangleButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean selected = e.getStateChange() == ItemEvent.SELECTED;
				GShape.setEnabled(selected);
				actionButton.setEnabled(selected);
			}
		});

		GShape = new JComboBox<>();
		GShape.addItem("Draw");
		GShape.addItem("Move");
		GShape.addItem("Resize");
		GShape.addItem("Rotate");

		GShape.setEnabled(false);
		actionButton = new JButton("Execute");
		actionButton.setEnabled(false);

		actionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedAction = (String) GShape.getSelectedItem();
				if (selectedAction != null) {
					System.out.println(selectedAction + " 동작 실행됨!");

				}
				if (selectedAction.equals("Draw")) {
					DrawRect(selectedAction);
				}
			}

		});

		this.add(this.rectangleButton);
		this.add(this.triangleButton);
		this.add(this.ovalButton);
		this.add(this.polygonButton);
		this.add(this.textBoxButton);
		this.add(this.GShape);
		this.add(this.actionButton);
	}

	public void initialize() {
		// TODO Auto-generated method stub

	}

	private void DrawRect(String shape) {
		GMain.initialize(shape);
		System.out.println(shape+"전달");
	}
}
