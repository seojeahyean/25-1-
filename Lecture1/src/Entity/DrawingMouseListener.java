package Entity;
import Model.Shape;
import javax.swing.*;

import Service.SystemManager;

import java.awt.event.*;
import java.awt.*;

public class DrawingMouseListener extends MouseAdapter {
	private DrawingArea drawingArea;
	private int x_click;
	private int  y_click;
	private int x_drop;
	private int y_drop;
	Shape shape = new Shape();
	public DrawingMouseListener(DrawingArea drawingArea) {
		this.drawingArea = drawingArea;
	}
	public void mousePressed(MouseEvent e) {
		System.out.println("���콺�� ���Ƚ��ϴ�");
		System.out.printf("������ X:%d/������ Y:%d",e.getX(),e.getY());
		this.x_click = e.getX();
		this.y_click = e.getY();
		shape.setX_click(x_click);
		shape.setY_click(y_click);
		
	}

    public void mouseReleased(MouseEvent e) {
    	System.out.println("���콺�� �������ϴ�");
    	System.out.printf("������ X:%d/������ Y:%d",e.getX(),e.getY());
    	this.x_drop = e.getX();
    	this.y_drop = e.getY();
    	shape.setX_drop(x_drop);
    	shape.setY_drop(y_drop);
    	SystemManager.drawing(shape);
    	SystemManager.myFrame.getDrawingArea().repaint();
    	
    }
}