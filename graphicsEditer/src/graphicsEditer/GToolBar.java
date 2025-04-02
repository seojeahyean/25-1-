package graphicsEditer;

import java.awt.FlowLayout;

import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class GToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	private JRadioButton rectangleButton;
	
	public GToolBar() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.rectangleButton = new JRadioButton("rectangle");
		this.add(this.rectangleButton);
		this.rectangleButton = new JRadioButton("rectangle");
		this.add(this.rectangleButton);
		this.rectangleButton = new JRadioButton("rectangle");
		this.add(this.rectangleButton);
		this.rectangleButton = new JRadioButton("rectangle");
		this.add(this.rectangleButton);
		
		
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
