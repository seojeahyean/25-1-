import java.awt.Graphics;
import javax.swing.JPanel;

public class GDrawingPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    int x;
    int y;
    int width;
    int height;
    GMainFrame mainFrame;
    Graphics graphics;
    public GDrawingPanel(GMainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.addMouseListener(new DrawingMousListener(this));
    }

        public void initialize() {
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.graphics = graphics;
        graphics.drawRect(x, y, width, height);
    }

    // �簢���� �׸��� �޼���
    public void draw( int x, int y, int width, int height) {
       this.x= x;
       this.y = y;
       this.width = width;
       this.height = height;
        repaint();
    }

    // �簢���� �̵���Ű�� �޼���
    public void move(int dx, int dy) {
        this.x = x + dx;
        this.y = y + dy;
        repaint();
       
    }
}
