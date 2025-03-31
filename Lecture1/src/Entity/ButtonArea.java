package Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonArea extends JPanel {

	JButton resetButton = new JButton("모두 지우기");// 모두 지우기 버튼
	JButton rectangleButton = new JButton("사각형");// 사각형 그리기 버튼

	public ButtonArea() {
		setLayout(new FlowLayout(FlowLayout.LEFT));// 레이아웃 정의
		resetButton.setPreferredSize(new Dimension(100, 30));// 리셋버튼 크기 정의
		rectangleButton.setPreferredSize(new Dimension(100, 30));// 사각형 그리기 버튼 크기 정의

		resetButton.addActionListener(new ButtonActionListener(this));// 리셋 버튼에 acctionListner 지정
		rectangleButton.addActionListener(new ButtonActionListener(this));// 사각형 그리기 버튼에 acctionListner 지정

		add(resetButton);// 리셋버튼 생성
		add(rectangleButton);// 사각형 그리기 버튼 생성

	}

}
