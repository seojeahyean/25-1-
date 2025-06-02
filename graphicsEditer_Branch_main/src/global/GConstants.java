package global;

import java.awt.Cursor;
import java.lang.reflect.InvocationTargetException;

import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GShape;
import shapes.GShape.EPoints;

public class GConstants {
	public final class GMainFrame{
		public final static int X = 100;
		public final static int Y = 100;
		public final static int W = 1000;
		public final static int H = 700;
		
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
