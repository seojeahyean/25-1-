package Entity;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import Service.SystemManager;

import java.awt.*;

public class DrawingArea extends JPanel{
	private int x_click;
	private int  y_click;
	private int width;
	private int height;
	public boolean rectangle = false;
	public DrawingArea() {
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(600, 700));
		addMouseListener(new DrawingMouseListener(this));
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(x_click,y_click,width,height);//x,y,width,height
	}
	public void readyToDrawRect() {
		this.rectangle=true;
	}
	public void draw(int X, int Y, int x, int y) {
		this.x_click = X;
		this.y_click = Y;
		this.width = Math.abs(X-x);
		this.height = Math.abs(Y-y);
		
	}
	
}
