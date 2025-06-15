package loginInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import global.GConstants.ESignupDialog;

public class SignupDialog extends JDialog {
    private JTextField nameField;
    private JTextField idField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> languageComboBox;
    private JButton signupButton;
    private JButton cancelButton;
    private UserManager userManager;

    public SignupDialog(Frame parent) {
        super(parent, ESignupDialog.title.getText(), true);  // "회원가입" - 회원가입 창 제목
        userManager = new UserManager();
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new GridLayout(6, 1, 5, 5));
        
        // 이름 입력
        nameField = new JTextField(20);
        add(new JLabel(ESignupDialog.nameLabel.getText()));  // "이름:" - 이름 입력 라벨
        add(nameField);
        
        // 아이디 입력
        idField = new JTextField(20);
        add(new JLabel(ESignupDialog.idLabel.getText()));  // "아이디:" - 아이디 입력 라벨
        add(idField);
        
        // 비밀번호 입력
        passwordField = new JPasswordField(20);
        add(new JLabel(ESignupDialog.passwordLabel.getText()));  // "비밀번호:" - 비밀번호 입력 라벨
        add(passwordField);
        
        // 비밀번호 확인
        confirmPasswordField = new JPasswordField(20);
        add(new JLabel(ESignupDialog.confirmPasswordLabel.getText()));  // "비밀번호 확인:" - 비밀번호 확인 라벨
        add(confirmPasswordField);
        
        // 언어 선택
        String[] languages = {"한국어", "영어", "일본어"};
        languageComboBox = new JComboBox<>(languages);
        add(new JLabel(ESignupDialog.languageLabel.getText()));  // "선호 언어:" - 언어 선택 라벨
        add(languageComboBox);
        
        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        signupButton = new JButton(ESignupDialog.signupButton.getText());  // "가입하기" - 회원가입 버튼
        cancelButton = new JButton(ESignupDialog.cancelButton.getText());  // "취소" - 취소 버튼
        
        signupButton.addActionListener(new SignupButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());
        
        buttonPanel.add(signupButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);
        
        pack();
    }

    private class SignupButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String language = (String) languageComboBox.getSelectedItem();
            
            // 입력 검증
            if (name.isEmpty() || id.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(SignupDialog.this, 
                    ESignupDialog.emptyField.getText());  // "모든 필드를 입력해주세요." - 빈 필드 경고 메시지
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(SignupDialog.this, 
                    ESignupDialog.passwordMismatch.getText());  // "비밀번호가 일치하지 않습니다." - 비밀번호 불일치 경고 메시지
                return;
            }
            
            // 회원가입 처리
            if (userManager.signUp(name, id, password, language)) {
                JOptionPane.showMessageDialog(SignupDialog.this, 
                    ESignupDialog.signupSuccess.getText());  // "회원가입이 완료되었습니다." - 회원가입 성공 메시지
                dispose();
            } else {
                JOptionPane.showMessageDialog(SignupDialog.this, 
                    ESignupDialog.signupFail.getText());  // "회원가입에 실패했습니다. 다시 시도해주세요." - 회원가입 실패 메시지
            }
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}