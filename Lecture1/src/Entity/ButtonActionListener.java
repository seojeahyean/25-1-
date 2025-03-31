package Entity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Service.SystemManager;

public class ButtonActionListener implements ActionListener {
	private ButtonArea B;
public ButtonActionListener(ButtonArea B) {
	this.B = B;
}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == B.resetButton) {
			System.out.println("1");
		} else if (e.getSource() == B.rectangleButton) {
			System.out.println("0");
			SystemManager.readyRectangle();
		}
	}

}
