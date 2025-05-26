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
		eNew("�� ����","newPanel"),
		eOpen("����","open"),
		eSave("����","save"),
		eSaveAs("�ٸ� �̸����� ����","saveAs"),
		ePrint("����Ʈ","print"),
		eQuit("����","quit");
		
		private String name;//toolTip�� �� �� �ְ�, hot key??�� �� �� ����
		private String methodName;
		
		private EFileMenuItem(String name,String MethodName) {
			this.name = name;
			this.methodName = MethodName;
		}public String getName() {
			return this.name;
		}
		public String getMethodName() {
			return this.methodName;
		}
	}
	public enum EShapeTool { // �̴��ַ� Ŭ�����϶��� �빮�� E, new�� �� ��ü�� ��쿣 �ҹ��� e�� ����
		eSelect("select", EPoints.e2P, GRectangle.class), eRectangle("rectangle", EPoints.e2P, GRectangle.class),
		eEllipse("ellipse", EPoints.e2P, GRectangle.class), eLine("line", EPoints.e2P, GRectangle.class),
		ePolygon("polygon", EPoints.eNP, GPolygon.class);

		private String name;// ��ư�̸�
		private EPoints ePoints;// �׸��� ���
		private Class<?> classShape;// �׸����� �ϴ� ���� Ŭ����

		private EShapeTool(String name, EPoints eDrawingType, Class<?> classShape) { // ������
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
				GShape shape = (GShape) classShape.getConstructor().newInstance();// ������ ���������� classShape�� <?>
				// �� ����Ǿ� � Ÿ���� ������ �� �� �� �� ���� ������ �̿� ���� ���·� ����
				// getConstructor() == () ->�ŰԺ����� ���� �����ڸ� ����
				// newInstance() == ������ new�� ����= ��ü�� ������
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
