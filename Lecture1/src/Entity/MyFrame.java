package Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	DrawingArea drawingArea = new DrawingArea();//drawingArea ��ü ����
	ButtonArea buttonArea = new ButtonArea();//buttonArea ��ü ����
	

	public MyFrame() {
		setTitle("�׸���");//â �̸� ����
		setSize(600, 800);//â ũ�� ����	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//â�� �ݾ��� �� ���α׷� ����
		setLocationRelativeTo(null);//�ױױפѱ� â�� �ʱ� ��ġ ����
		setResizable(false);//������ �� ���� �Ұ� ����

		Container container = this.getContentPane();//container ��ü ����
		container.setLayout(new BorderLayout());//container�� ���̾ƿ� ����

		container.add(buttonArea, BorderLayout.NORTH);//buttonArea �߰�
		container.add(drawingArea, BorderLayout.CENTER);//drawingArea �߰�
		setVisible(true);
	}
	public DrawingArea getDrawingArea() {
		return drawingArea;//���� ȭ���� �����ϴ� drawArea ����
	}

}
