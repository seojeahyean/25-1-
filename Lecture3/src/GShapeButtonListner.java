import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GShapeButtonListner implements ItemListener {
	private GToolBar gToolBar;
	public GShapeButtonListner(GToolBar gToolBar) {
		this.gToolBar = gToolBar;
	}

	@Override

	public void itemStateChanged(ItemEvent e) {
		boolean selected = e.getStateChange() == ItemEvent.SELECTED;
		GMainFrame.readyToShape(selected);
//		gShape.setEnabled(selected);
	}

}
