
public class GMain {
	public static GMainFrame mainFrame;
	public static void main(String[] args) {
		mainFrame = new GMainFrame();//GMainFrame ����
		mainFrame.setVisible(true);//mainFrame setVisible
		
	}

	public static void initialize(String shape) {
		mainFrame.initialize(shape);
	}
}
