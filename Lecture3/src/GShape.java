import javax.swing.JComboBox;

public class GShape extends JComboBox<String> {
	private static final long serialVersionUID = 1L;

	public GShape() {
		super(new String[]{"Draw", "Move", "Resize", "Rotate"});
	}
}
