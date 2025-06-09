package global;

import java.awt.Cursor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GShape;
import shapes.GShape.EPoints;

public class GConstants {

	public void readFrromFile(String fileName) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			File file = new File(fileName);
			Document document = builder.parse(file);
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for(int i = 0; i<nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					if(node.getNodeName().equals(EMainFrame.class.getSimpleName())) {
						GMainFrame.setValues(node);
					}else if(node.getNodeName().equals(EMenu.class.getSimpleName())) {
						EMenu.setValues(node);
					}else if(node.getNodeName().equals(EFileNemuItem.class.getSimpleName())){
						EFileNemuItem.setValues(node);
					}else if(node.getNodeName().equals(EEditMenuItem.class.getSimpleName())){
						EEditMenuItem.setValues(node);
					}else if(node.getNodeName().equals(EGraphicsMenuItem.class.getSimpleName())){
						EGraphicsMenuItem.setValues(node);
					}else if(node.getNodeName().equals(EToolBarButton.class.getSimpleName())){
						EToolBarButton.setValues(node);
					}
				}
			}
			
		}catch() {
			
		}
	}
	
	public enum GMainFrame{
		eX(0),
		eY(0),
		eW(0),
		eH(0);
		private int value;
		private GMainFrame(int value) {
			this.value = value;
		}
		public int getValue() {
			return this.value;
		}
		
	public void setValues(Node node) {
		for(GMainFrame gMainFrame: GMainFrame.values()) {
			Node attribute = node.getAttributes().getNamedItem(gMainFrame.name());
			gMainFrame.value = Integer.parseInt(attribute.getNodeValue());
		}
	}
		
	}

	public enum EFileMenuItem{
		eNew("새 파일","newPanel"),
		eOpen("열기","open"),
		eSave("저장","save"),
		eSaveAs("다른 이름으로 저장","saveAs"),
		ePrint("인쇄하기","print"),
		eClose("닫기","close"),
		eQuit("종료","quit");
		
		private String name;
		private String toolTipText;
		private String methodName;
		
		private EFileMenuItem(String name,String MethodName) {
			this.name = name;
			this.methodName = MethodName;
		}
		public String getName() {
			return this.name;
		}
		public String getMethodName() {
			return this.methodName;
		}
	}

	public enum EEditMenuItem {
		eProperty("Property", "property"),
		eUndo("Undo", "undo"),
		eRedo("Redo", "redo");
		
		private String name;
		private String methodName;
		
		private EEditMenuItem(String name, String methodName) {
			this.name = name;
			this.methodName = methodName;
		}
		public String getName() {
			return this.name;
		}
		public String getMethodName() {
			return this.methodName;
		}
	}

	public enum EGraphicMenuItem {
		eLineThickness("Line Thickness", "lineThickness"),
		eLineStyle("Line Style", "lineStyle"),
		eFontStyle("Font Style", "fontStyle"),
		eFontSize("Font Size", "fontSize");
		
		private String name;
		private String methodName;
		
		private EGraphicMenuItem(String name, String methodName) {
			this.name = name;
			this.methodName = methodName;
		}
		public String getName() {
			return this.name;
		}
		public String getMethodName() {
			return this.methodName;
		}
	}

	public enum EShapeTool { // 대부분의 E, new문을 사용하는 경우 대부분의 문제를 해결할 수 있는 방법입니다.
		eSelect("select", EPoints.e2P, GRectangle.class), eRectangle("rectangle", EPoints.e2P, GRectangle.class),
		eEllipse("ellipse", EPoints.e2P, GRectangle.class), eLine("line", EPoints.e2P, GRectangle.class),
		ePolygon("polygon", EPoints.eNP, GPolygon.class);

		private String name;// 대부분의 문제를 해결할 수 있는 방법입니다.
		private EPoints ePoints;// 대부분의 문제를 해결할 수 있는 방법입니다.
		private Class<?> classShape;// 대부분의 문제를 해결할 수 있는 방법입니다.

		private EShapeTool(String name, EPoints eDrawingType, Class<?> classShape) { // 대부분의 문제를 해결할 수 있는 방법입니다.
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
				GShape shape = (GShape) classShape.getConstructor().newInstance();// 대부분의 문제를 해결할 수 있는 방법입니다.
				// 이 부분은 대부분의 문제를 해결할 수 있는 방법입니다.
				// getConstructor() == () -> 대부분의 문제를 해결할 수 있는 방법입니다.
				// newInstance() == new 대부분의 문제를 해결할 수 있는 방법입니다.
				return shape;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	public enum EAnchors {
		eNN(new Cursor(Cursor.N_RESIZE_CURSOR)), eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
		eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)), eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
		eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)), eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
		eEE(new Cursor(Cursor.E_RESIZE_CURSOR)), eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
		eRR(new Cursor(Cursor.HAND_CURSOR)), eMM(new Cursor(Cursor.MOVE_CURSOR));

		private Cursor cursor;

		private EAnchors(Cursor cursor) {
			this.cursor = cursor;
		}

		public Cursor getCursor() {
			// TODO Auto-generated method stub
			return this.cursor;
		}

	}
}
