
import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;// 메뉴바, 툴바, 드로잉 패널 보유
	
	
	public GMainFrame() {
		//attributes
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(100,200);//본인 소환 시 위치 지정
		this.setSize(600,400);//본인 크기 지정
		this.setResizable(false);//크기 재조정 불가
		
		//component
		LayoutManager layoutManager = new BorderLayout();//레이아웃 메니저 생성
		this.setLayout(layoutManager);//this에 레이아웃 메니저 적용
		
		this.menuBar = new GMenuBar();//메뉴바 생성
		this.setJMenuBar(menuBar);
		
		this.toolBar = new GToolBar();
		this.add(toolBar,BorderLayout.NORTH);//툴바 생성 이후 상단에 적용
		
		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);//드로잉 패널 생성 후 중앙부에 적용
	}

	public void initialize(String shape) {//화면 재 구성시 사용되는 메서드
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize(10,10,50,50);
		
	}
}
