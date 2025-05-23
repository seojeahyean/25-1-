package Frames;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import Frames.GShapeToolBar.EShapeTool;
import shapes.GShape;
import shapes.GPolygon;
import transformers.GTransformer;
import transformers.GDrawer;
import transformers.GeNPDrawer;

public class GDrawingPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public enum EDrawingType { e2P, eNP }
    public enum EDrawingState { eIdel, e2P, eNP }

    private Vector<GShape> shapes;
    private EShapeTool      eShapeTool;
    private EDrawingState   eDrawingState;
    private GTransformer    transformer;
    private GShape          currentShape;

    // ���콺 �̵� �� ������ ��ǥ �����
    private int mouseX, mouseY;

    public GDrawingPanel() {
        MouseHandler mouseHandler = new MouseHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);

        shapes        = new Vector<>();
        eShapeTool    = null;
        eDrawingState = null;
    }

    public void initialize() {
        // �ʱ�ȭ�� �ʿ��ϸ� ���⿡ �ۼ�
    }

    public void setEShapeTool(EShapeTool tool) {
        this.eShapeTool    = tool;
        this.eDrawingState = EDrawingState.eIdel;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;

        // ����� ��� ���� �׸���
        for (GShape shape : shapes) {
            shape.draw(g2);
        }

        // ������ ���: ������ ���� �� �� ���� Ŀ�� ���ἱ �׸���
        if (eDrawingState == EDrawingState.eNP
            && currentShape instanceof GPolygon) {
            java.awt.Polygon poly = ((GPolygon) currentShape).getPolygon();
            int n = poly.npoints;
            if (n > 0) {
                int lx = poly.xpoints[n-1];
                int ly = poly.ypoints[n-1];
                g2.drawLine(lx, ly, mouseX, mouseY);
            }
        }
    }

    // �׸��� ���� (2-����Ʈ �Ǵ� ������ ��� ���⼭ currentShape ����)
    private void startDrawing(int x, int y) {
        currentShape = eShapeTool.newShape();
        shapes.add(currentShape);

        if (eShapeTool.getEDrawingType() == EDrawingType.e2P) {
            transformer = new GDrawer(currentShape);
        } else {
            transformer = new GeNPDrawer(currentShape);
        }
        transformer.start((Graphics2D) getGraphics(), x, y);
    }

    // �巡�� �� ���� (2-����Ʈ ����)
    private void keepDrawing(int x, int y) {
        transformer.drag((Graphics2D) getGraphics(), x, y);
        repaint();
    }

    // ������ ������ �� �߰�
    private void addPoint(int x, int y) {
        ((GPolygon) currentShape).addPoint(x, y);
        repaint();
    }

    // �׸��� �Ϸ�
    private void finishDrawing(int x, int y) {
        transformer.finish((Graphics2D) getGraphics(), x, y);
        if (currentShape instanceof GPolygon) {
            ((GPolygon) currentShape).close();
        }
        repaint();
    }

    private class MouseHandler implements MouseListener, MouseMotionListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            // ������ ��尡 �ƴϸ� ����
            if (eShapeTool == null ||
                eShapeTool.getEDrawingType() != EDrawingType.eNP) return;

            // ù Ŭ��: ������ ����
            if (eDrawingState == EDrawingState.eIdel) {
                startDrawing(e.getX(), e.getY());
                eDrawingState = EDrawingState.eNP;
            }
            else {
                if (e.getClickCount() == 2) {
                    // ����Ŭ��: ������ ����
                    finishDrawing(e.getX(), e.getY());
                    eDrawingState = EDrawingState.eIdel;
                } else {
                    // �̱�Ŭ��: �߰� ������ �߰�
                    addPoint(e.getX(), e.getY());
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // 2-����Ʈ ���: press��drag��release �� �׸���
            if (eShapeTool != null
             && eDrawingState == EDrawingState.eIdel
             && eShapeTool.getEDrawingType() == EDrawingType.e2P) {
                startDrawing(e.getX(), e.getY());
                eDrawingState = EDrawingState.e2P;
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (eDrawingState == EDrawingState.e2P) {
                keepDrawing(e.getX(), e.getY());
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            if (eDrawingState == EDrawingState.eNP) {
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (eDrawingState == EDrawingState.e2P) {
                finishDrawing(e.getX(), e.getY());
                eDrawingState = EDrawingState.eIdel;
            }
        }

        @Override public void mouseEntered(MouseEvent e) { }
        @Override public void mouseExited(MouseEvent e)  { }
    }
}
