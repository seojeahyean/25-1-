package Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonArea extends JPanel {

	JButton resetButton = new JButton("��� �����");// ��� ����� ��ư
	JButton rectangleButton = new JButton("�簢��");// �簢�� �׸��� ��ư

	public ButtonArea() {
		setLayout(new FlowLayout(FlowLayout.LEFT));// ���̾ƿ� ����
		resetButton.setPreferredSize(new Dimension(100, 30));// ���¹�ư ũ�� ����
		rectangleButton.setPreferredSize(new Dimension(100, 30));// �簢�� �׸��� ��ư ũ�� ����

		resetButton.addActionListener(new ButtonActionListener(this));// ���� ��ư�� acctionListner ����
		rectangleButton.addActionListener(new ButtonActionListener(this));// �簢�� �׸��� ��ư�� acctionListner ����

		add(resetButton);// ���¹�ư ����
		add(rectangleButton);// �簢�� �׸��� ��ư ����

	}

}
