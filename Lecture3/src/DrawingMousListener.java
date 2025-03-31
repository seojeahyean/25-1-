import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DrawingMousListener extends MouseAdapter implements MouseListener {
	private GDrawingPanel drawingPanel;
	private int x_click;
	private int y_click;
	private int x_drop;
	private int y_drop;
	private boolean move = false;
	private boolean draw = false;

	public DrawingMousListener(GDrawingPanel drawingPanel) {

		this.drawingPanel = drawingPanel;

	}

	public void mousePressed(MouseEvent e) {
		System.out.println("마우스가 눌렸습니다");
		System.out.printf("시작점 X:%d/시작점 Y:%d", e.getX(), e.getY());
		this.x_click = e.getX();
		this.y_click = e.getY();
		if((drawingPanel.x <= x_click && x_click <= drawingPanel.width+drawingPanel.x)
				&&(drawingPanel.y <= y_click && y_click <= drawingPanel.height+drawingPanel.y)) {//최초 선택한 영역이 사각형의 내부일 때
			this.move = true;
		}else {
			
		}

	}

	public void mouseReleased(MouseEvent e) {
		System.out.println("마우스가 때졌습니다");
		System.out.printf("시작점 X:%d/시작점 Y:%d", e.getX(), e.getY());
		this.x_drop = e.getX();
		this.y_drop = e.getY();
		if(this.move) {
			drawingPanel.mainFrame.moveRect(x_drop-x_click, y_drop-y_click);
			this.move = false;
		}
	}
}
