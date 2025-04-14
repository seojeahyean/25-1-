package Frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private GMenuBar menuBar;
	private GShapeToolBar toolBar;
	private GDrawingPanel drawingPanel;
	
	public GMainFrame() {
		//attribute
		this.setLocation(100,200);
		this.setSize(600,400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//components
		this.menuBar = new GMenuBar();
		this.setJMenuBar(menuBar);
		
		this.setLayout(new BorderLayout());
		this.toolBar = new GShapeToolBar();
		this.add(toolBar,BorderLayout.NORTH);
		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);
		
	}

	public void initialize() {
		//associate
		this.toolBar.associate(this.drawingPanel);
		this.menuBar.associate(this.drawingPanel);
		//associated attributes
		this.setVisible(true); 
				
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
		
	}

}
