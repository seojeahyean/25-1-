package Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	DrawingArea drawingArea = new DrawingArea();//drawingArea 객체 생성
	ButtonArea buttonArea = new ButtonArea();//buttonArea 객체 생성
	

	public MyFrame() {
		setTitle("그림판");//창 이름 지정
		setSize(600, 800);//창 크기 설정	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//창을 닫았을 시 프로그램 종료
		setLocationRelativeTo(null);//그그그ㅡ극 창의 초기 위치 지정
		setResizable(false);//사이즈 재 조정 불가 지정

		Container container = this.getContentPane();//container 객체 생성
		container.setLayout(new BorderLayout());//container에 레이아웃 설정

		container.add(buttonArea, BorderLayout.NORTH);//buttonArea 추가
		container.add(drawingArea, BorderLayout.CENTER);//drawingArea 추가
		setVisible(true);
	}
	public DrawingArea getDrawingArea() {
		return drawingArea;//현재 화면을 구성하는 drawArea 전달
	}

}
