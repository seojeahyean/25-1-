package Frames;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

//import Frames.GDrawingPanel.EDrawingType;
//import Frames.GDrawingPanel.EShapeType;
import shapes.GRectangle;
import shapes.GShape;



public class GShapeToolBar extends JToolBar {

	//자식

	private static final long serialVersionUID = 1L;
	

	private GDrawingPanel drawingPanel;
	
	public enum EDrawingType {
		e2P,  //2점일 때/ 이 값이 eShape로 들어가야함
		eNP	  //n개의 점일 때
	}
	
	// Class에 대한 정의
	public enum EShapeType { //
		eSelect("select",EDrawingType.e2P, GRectangle.class),
		eRectangle("rectangle", EDrawingType.e2P, GRectangle.class), //
		eEllipse("ellipse", EDrawingType.e2P, GRectangle.class), //
		eLine("line", EDrawingType.e2P, GRectangle.class),
		ePolygon("polygon", EDrawingType.eNP, GRectangle.class); //
		
		private String name; 
		private EDrawingType eDrawingType;
		private Class<?> classShape;
		private EShapeType(String name, EDrawingType eDrawingType, Class<?> classShape) { 
			this.name = name;
			this.eDrawingType = eDrawingType;
			this.classShape = classShape;
		}
		public String getName() {
			return this.name;
			
		}
		public EDrawingType eDrawingType() {
			return this.eDrawingType;
		}
		public GShape newShape() {
			GShape shape;
	         try {
	             shape = (GShape) classShape.getConstructor().newInstance();
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
		//this.setLayout(new FlowLayout(FlowLayout.LEFT)); 
		
		setLayout(new FlowLayout(FlowLayout.LEFT));// 버튼을 왼쪽으로 땡
		ButtonGroup buttonGroup = new ButtonGroup();//라디오 버튼 그룹 생성
		for (EShapeType eShapeType: EShapeType.values()) {//EShapeType 내부의 
			JRadioButton radioButton = new JRadioButton(eShapeType.getName());
			ActionHandler actionHandler = new ActionHandler();
			radioButton.addActionListener(actionHandler);
			radioButton.setActionCommand(eShapeType.toString());//액션이 일어나면 () 를 보내라 ~~~여기서 보내서
			
			buttonGroup.add(radioButton);
			this.add(radioButton); //자식으로 등록
			System.out.println("a");
		}

	
		
	}

	public void initialize() {
	
	}
	public void associate (GDrawingPanel drawingPanel) {//GMainFrame에서 이루어지는 association 과정의 메서드
		this.drawingPanel = drawingPanel;
	}
	private class ActionHandler implements ActionListener {
		 @Override
	        public void actionPerformed(ActionEvent e) {
	            String sShapeType = e.getActionCommand();//~~~여기서 받음
	            EShapeType eShapeType = EShapeType.valueOf(sShapeType);//받아온 String을 통해 해당 이름을 가진 EShapeType을 
	            //valueOf로 찾아냄
	            drawingPanel.setEShapeType(eShapeType);
	            //찾아낸 eShapeType 객체를 drawingPanel.setEShapeType을 통해 drawingPanel에 전달
	        }
		
	}
}
