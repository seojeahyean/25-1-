package Frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import loginInfo.LoginDialog;

import global.GConstants;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private GMenuBar menuBar;
	private GShapeToolBar toolBar;
	private GDrawingPanel drawingPanel;
	
	public GMainFrame() {
		//attribute
		this.setLocation(GConstants.GMainFrame.eX.value, GConstants.GMainFrame.eY.value);//GConstants를 통해 상수를 전달받음
		this.setSize(GConstants.GMainFrame.eW.value, GConstants.GMainFrame.eH.value);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//components
		LoginDialog loginDialog = new LoginDialog(this);// GMainFrame을 부모로 하는 다이얼로그 생성
		loginDialog.setVisible(true);  // 모달 다이얼로그가 닫힐 때까지 대기

		if (loginDialog.isLoginSuccess()) {  // 다이얼로그가 닫힌 후 로그인 성공 여부 확인------------------->로그인이 성공이라면
			this.menuBar = new GMenuBar();
			this.setJMenuBar(this.menuBar);			
			this.setLayout(new BorderLayout());
			this.toolBar = new GShapeToolBar();
			this.add(toolBar, BorderLayout.NORTH);
			this.drawingPanel = new GDrawingPanel();
			this.add(drawingPanel, BorderLayout.CENTER);
			this.setVisible(true);//GMainFrame의 내부 요소 생성
		} else {//-------------------------------------------------------------------------------->로그인이 실패라면
			System.exit(0);  // 프로그램 종료
		}
	}

	public void initialize() {
		//associate
		this.toolBar.associate(this.drawingPanel);
		this.menuBar.associate(this.drawingPanel);
		
		//initialize components
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
		
		//show frame
		this.setVisible(true); 
	}
}
