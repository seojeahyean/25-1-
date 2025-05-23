package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import frames.GDrawingPanel.EDrawingType;
import shapes.GRectangle;
import shapes.GShape;

public class GShapeToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	//component
	
	//associations
	private GDrawingPanel drawingPanel;
	
	public enum EShapeTool{
		eSelect("select",EDrawingType.e2P, GRectangle.class),
		eRectangle("rectangle",EDrawingType.e2P,GRectangle.class),
		eEllispe("ellispe", EDrawingType.e2P,GRectangle.class),
		eLine("line", EDrawingType.e2P,GRectangle.class),
		ePolygon("polygon",EDrawingType.eNP, GRectangle.class);
		
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
			GShape shape;
	         try {
	             shape = (GShape) classShape.getConstructor().newInstance();
	             return shape;
	          } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
	                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
	             e.printStackTrace();
	          }
	          return null;
		}
	}
	
	public GShapeToolBar() {
		
		ButtonGroup buttonGroup = new ButtonGroup();
		for(EShapeTool eShapeType: EShapeTool.values()) {
			JRadioButton radioButton = new JRadioButton(eShapeType.getName());
			ActionHandler actionHandler = new ActionHandler();
			radioButton.addActionListener(actionHandler);
			radioButton.setActionCommand(eShapeType.toString());
		
			buttonGroup.add(radioButton);
			this.add(radioButton);
		}
	}

	public void initialize() {
		
	}

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		
	}
	private class ActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String sShapeType = e.getActionCommand();
			EShapeTool eShapeType = EShapeTool.valueOf(sShapeType);
			drawingPanel.setEShapeTool(eShapeType);
		}
		
	}
}
