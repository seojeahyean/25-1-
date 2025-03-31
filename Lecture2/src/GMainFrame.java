
import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;// �޴���, ����, ����� �г� ����
	
	
	public GMainFrame() {
		//attributes
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(100,200);//���� ��ȯ �� ��ġ ����
		this.setSize(600,400);//���� ũ�� ����
		this.setResizable(false);//ũ�� ������ �Ұ�
		
		//component
		LayoutManager layoutManager = new BorderLayout();//���̾ƿ� �޴��� ����
		this.setLayout(layoutManager);//this�� ���̾ƿ� �޴��� ����
		
		this.menuBar = new GMenuBar();//�޴��� ����
		this.setJMenuBar(menuBar);
		
		this.toolBar = new GToolBar();
		this.add(toolBar,BorderLayout.NORTH);//���� ���� ���� ��ܿ� ����
		
		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);//����� �г� ���� �� �߾Ӻο� ����
	}

	public void initialize(String shape) {//ȭ�� �� ������ ���Ǵ� �޼���
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize(10,10,50,50);
		
	}
}
