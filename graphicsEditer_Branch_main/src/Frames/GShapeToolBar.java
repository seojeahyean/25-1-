package Frames;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.GConstants.EShapeTool;

public class GShapeToolBar extends JToolBar {

	// 자식

	private static final long serialVersionUID = 1L;

	private GDrawingPanel drawingPanel;

	// Class에 대한 정의


	public GShapeToolBar() {
		setLayout(new FlowLayout(FlowLayout.LEFT));// 레이아웃 지정
		ButtonGroup buttonGroup = new ButtonGroup();// 버튼 그룹 설정
		for (EShapeTool eShapeType : EShapeTool.values()) {// 개선된 for문을 통해 라디오버튼 내부 객체 생성
			
			JRadioButton radioButton = new JRadioButton(eShapeType.getLabel());// getName을 통해 버튼 생성
			
			ActionHandler actionHandler = new ActionHandler();// 액션핸들러 생성
			radioButton.addActionListener(actionHandler);// 액션핸들러 연결
			radioButton.setActionCommand(eShapeType.toString());// 액션이 일어나면 () 를 보내라 ~~~여기서 보내서

			buttonGroup.add(radioButton);// 버튼그룹에 등록
			this.add(radioButton); // 자식으로 등록
		}

	}

	public void initialize() {
		JRadioButton JButton = (JRadioButton) this.getComponent(EShapeTool.eSelect.ordinal());//초기값 'select' 버튼 선언
		JButton.doClick();//선택
	}

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String sShapeType = e.getActionCommand();// ~~~여기서 받음
			EShapeTool eShapeType = EShapeTool.valueOf(sShapeType);
			drawingPanel.setEShapeTool(eShapeType);//drawingPanel으로 선택된 버튼의 정보와 같은 Enum인 EShapeTool을 보냄
		}

	}
}