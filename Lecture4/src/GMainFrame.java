
import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;// �޴���, ����, ����� �г� ����

	public GMainFrame() {
		// attributes
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(100, 200);// ���� ��ȯ �� ��ġ ����
		this.setSize(600, 400);// ���� ũ�� ����
		this.setResizable(false);// ũ�� ������ �Ұ�

		// component
		
		this.menuBar = new GMenuBar();// �޴��� ����
		this.setJMenuBar(menuBar);
		
		LayoutManager layoutManager = new BorderLayout();// ���̾ƿ� �޴��� ����
		this.setLayout(layoutManager);// this�� ���̾ƿ� �޴��� ����

		this.toolBar = new GToolBar();
		this.add(toolBar, BorderLayout.NORTH);// ���� ���� ���� ��ܿ� ����

		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);// ����� �г� ���� �� �߾Ӻο� ����

		// associated attributes
		this.setVisible(true);// mainFrame setVisible
	}

	public void initialize() {// ȭ�� �� ������ ���Ǵ� �޼���
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();

	}
}
