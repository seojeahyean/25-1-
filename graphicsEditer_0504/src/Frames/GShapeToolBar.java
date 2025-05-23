package Frames;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import Frames.GDrawingPanel.EDrawingType;
import shapes.GPolygon;
//import Frames.GDrawingPanel.EDrawingType;
//import Frames.GDrawingPanel.EShapeType;
import shapes.GRectangle;
import shapes.GShape;



public class GShapeToolBar extends JToolBar {

	//자식

	private static final long serialVersionUID = 1L;
	

	private GDrawingPanel drawingPanel;

	
	// Class에 대한 정의
	public enum EShapeTool { // 이뉴멀로 클래스일때는 대문자 E, new가 된 객체인 경우엔 소문자 e로 선언
		eSelect("select",EDrawingType.e2P, GRectangle.class),
		eRectangle("rectangle", EDrawingType.e2P, GRectangle.class),
		eEllipse("ellipse", EDrawingType.e2P, GRectangle.class), 
		eLine("line", EDrawingType.e2P, GRectangle.class),
		ePolygon("polygon", EDrawingType.eNP, GPolygon.class); 
		
		private String name; 
		private EDrawingType eDrawingType;
		private Class<?> classShape;
		private EShapeTool(String name, EDrawingType eDrawingType, Class<?> classShape) { 
			this.name = name;
			this.eDrawingType = eDrawingType;
			this.classShape = classShape;
		}
		public String getName() {
			return this.name;
			
		}
		public EDrawingType getEDrawingType() {
			return this.eDrawingType;
		}
		public GShape newShape() {
			
	         try {
	             GShape shape = (GShape) classShape.getConstructor().newInstance();
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
		ButtonGroup buttonGroup = new ButtonGroup();
		for (EShapeTool eShapeType: EShapeTool.values()) {
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
	public void associate (GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	private class ActionHandler implements ActionListener {
		 @Override
	        public void actionPerformed(ActionEvent e) {
	            String sShapeType = e.getActionCommand();//~~~여기서 받음
	            EShapeTool eShapeType = EShapeTool.valueOf(sShapeType);
	            drawingPanel.setEShapeTool(eShapeType);
	        }
		
	}
}
