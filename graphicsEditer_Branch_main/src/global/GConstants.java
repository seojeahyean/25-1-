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
		public final static int Y = 200;
		public final static int W = 600;
		public final static int H = 400;
		
	}

	public enum EFileMenuItem{
		eNew("새 파일","newPanel"),
		eOpen("열기","open"),
		eSave("저장","save"),
		eSaveAs("다른 이름으로 저장","saveAs"),
		ePrint("프린트","print"),
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
	public enum EShapeTool { // 클래스 모델링 E, new로 시작하는 클래스 이름 모델링 규칙을 따르는 클래스
		eSelect("select", EPoints.e2P, GRectangle.class), eRectangle("rectangle", EPoints.e2P, GRectangle.class),
		eEllipse("ellipse", EPoints.e2P, GRectangle.class), eLine("line", EPoints.e2P, GRectangle.class),
		ePolygon("polygon", EPoints.eNP, GPolygon.class);

		private String name;// 클래스 이름
		private EPoints ePoints;// 포인트 타입
		private Class<?> classShape;// 클래스 타입

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
				GShape shape = (GShape) classShape.getConstructor().newInstance();// 클래스 타입의 생성자를 호출하여 객체를 생성
				// 이 부분은 추가적인 검사를 해야 할 수도 있습니다.
				// getConstructor() == () -> 생성자 메서드를 찾아냄
				// newInstance() == new를 호출하여 객체를 생성
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
