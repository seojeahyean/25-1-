package Service;
import Model.Shape;
import Entity.MyFrame;

public class SystemManager {
	public static MyFrame myFrame;//myFrame 생성
	public SystemManager() {

	}

	public void initialize() {//시작
		myFrame = new MyFrame();
	}

	public static void readyRectangle() {//
		myFrame.getDrawingArea().readyToDrawRect();
	}
	public static void drawing(Shape shape) {
		if(myFrame.getDrawingArea().rectangle)
		myFrame.getDrawingArea().draw(shape.getX_click(),shape.getY_click(),
				shape.getX_drop(),shape.getY_drop());
	}
}
