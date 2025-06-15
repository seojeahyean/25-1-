package loginInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import global.GConstants.ELoginDialog;
import global.GConstants;

public class LoginDialog extends JDialog {
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private UserManager userManager;
    private boolean loginSuccess = false;   

    public LoginDialog(Frame parent) {
        super(parent, ELoginDialog.title.getText(), true);  // "로그인" - 로그인 창 제목
        userManager = UserManager.getInstance();  // 싱글톤 인스턴스 사용
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new GridLayout(4, 1, 5, 5));
        
        // 아이디 입력
        idField = new JTextField(20);
        add(new JLabel(ELoginDialog.idLabel.getText()));  // "아이디:" - 아이디 입력 라벨
        add(idField);
        
        // 비밀번호 입력
        passwordField = new JPasswordField(20);
        add(new JLabel(ELoginDialog.passwordLabel.getText()));  // "비밀번호:" - 비밀번호 입력 라벨
        add(passwordField);
        
        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        // 로그인 버튼
        loginButton = new JButton(ELoginDialog.loginButton.getText());  // "로그인" - 로그인 버튼
        loginButton.addActionListener(new LoginButtonListener());
        buttonPanel.add(loginButton);
        
        // 회원가입 버튼
        signupButton = new JButton(ELoginDialog.signupButton.getText());  // "회원가입" - 회원가입 버튼
        signupButton.addActionListener(new SignupButtonListener());
        buttonPanel.add(signupButton);
        
        add(buttonPanel);
        
        pack();
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            
            if (userManager.checkUserData(id, password)) {
                userManager.login(id);  // 로그인 성공 시 현재 사용자 ID 설정
                
                // 사용자의 언어 설정 불러오기
                String userLanguage = userManager.getUserLanguage(id);
                if (userLanguage != null) {
                    GConstants gConstants = new GConstants();
                    gConstants.setLanguage(userLanguage);
                }
                
                JOptionPane.showMessageDialog(LoginDialog.this, 
                    ELoginDialog.loginSuccess.getText());
                loginSuccess = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginDialog.this, 
                    ELoginDialog.loginFail.getText());
                loginSuccess = false;
            }
        }
    }

    private class SignupButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SignupDialog signupDialog = new SignupDialog((Frame)getOwner());
            signupDialog.setVisible(true);
        }
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }
}