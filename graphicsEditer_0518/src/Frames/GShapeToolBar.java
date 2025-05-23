package Frames;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import shapes.GPolygon;
//import Frames.GDrawingPanel.EDrawingType;
//import Frames.GDrawingPanel.EShapeType;
import shapes.GRectangle;
import shapes.GShape;
import shapes.GShape.EPoints;

public class GShapeToolBar extends JToolBar {

	// 자식

	private static final long serialVersionUID = 1L;

	private GDrawingPanel drawingPanel;

	// Class에 대한 정의
	public enum EShapeTool { // 이뉴멀로 클래스일때는 대문자 E, new가 된 객체인 경우엔 소문자 e로 선언
		eSelect("select", EPoints.e2P, GRectangle.class), eRectangle("rectangle", EPoints.e2P, GRectangle.class),
		eEllipse("ellipse", EPoints.e2P, GRectangle.class), eLine("line", EPoints.e2P, GRectangle.class),
		ePolygon("polygon", EPoints.eNP, GPolygon.class);

		private String name;// 버튼이름
		private EPoints ePoints;// 그리기 방식
		private Class<?> classShape;// 그리고자 하는 도형 클래스

		private EShapeTool(String name, EPoints eDrawingType, Class<?> classShape) { // 생성자
			this.name = name;
			this.ePoints = eDrawingType;
			this.classShape = classShape;
		}

		public String getName() {
			return this.name;

		}

		public EPoints getEPoints() {
			return this.ePoints;
		}

		public GShape newShape() {

			try {
				GShape shape = (GShape) classShape.getConstructor().newInstance();// 지금의 시점에서는 classShape이 <?>
				// 로 선언되어 어떤 타입의 도형이 올 지 알 수 없기 때문에 이와 같은 형태로 선언
				// getConstructor() == () ->매게변수가 없는 생성자를 통해
				// newInstance() == 기존의 new와 같음= 객체를 만들어라
				return shape;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	public GShapeToolBar() {
		setLayout(new FlowLayout(FlowLayout.LEFT));// 레이아웃 지정
		ButtonGroup buttonGroup = new ButtonGroup();// 버튼 그룹 설정
		for (EShapeTool eShapeType : EShapeTool.values()) {// 개선된 for문을 통해 라디오버튼 내부 객체 생성
			
			JRadioButton radioButton = new JRadioButton(eShapeType.getName());// getName을 통해 버튼 생성
			
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
