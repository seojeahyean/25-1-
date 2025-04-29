package Frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//내부 요소 정의
	private GMenuBar menuBar;
	private GShapeToolBar toolBar;
	private GDrawingPanel drawingPanel;
	
	public GMainFrame() {
		//본인 attribute 정의
		this.setLocation(100,200);
		this.setSize(600,400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//components 정의
		this.menuBar = new GMenuBar();
		this.setJMenuBar(menuBar);
		
		this.setLayout(new BorderLayout());
		this.toolBar = new GShapeToolBar();
		this.add(toolBar,BorderLayout.NORTH);
		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);
		
	}

	public void initialize() {
		//initialize 함수를 통해 자식 요소 생성 이후의 시점에서 association 정의
		this.toolBar.associate(this.drawingPanel);//toolBar와 menuBar가 drawingPanel의 정보를 참조하도록 정의
		this.menuBar.associate(this.drawingPanel);
		//associated attributes
		this.setVisible(true); 
				
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
		
	}
	

}
