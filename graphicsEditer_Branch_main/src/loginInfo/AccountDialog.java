package loginInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import global.GConstants.*;
import global.GConstants;
import java.io.File;
import java.io.IOException;
import Frames.GMainFrame;

public class AccountDialog extends JDialog {
    private JTextField nameField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JComboBox<String> languageComboBox;
    private UserManager userManager;
    private String command;

    public AccountDialog(Frame owner, String command) {
        super(owner, true);
        this.command = command;
        this.userManager = UserManager.getInstance();
        initialize();
    }

    private void initialize() {
        setTitle(GConstants.getText(EAccountDialog.valueOf("title_" + command)));
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        switch (command) {
            case "changeLanguage":
                initializeLanguageChange(mainPanel, gbc);
                break;
            case "changeName":
                initializeNameChange(mainPanel, gbc);
                break;
            case "changePassword":
                initializePasswordChange(mainPanel, gbc);
                break;
            case "logout":
                handleLogout();
                break;
        }

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void initializeLanguageChange(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel(GConstants.getText(EAccountDialog.languageLabel)), gbc);

        gbc.gridx = 1;
        languageComboBox = new JComboBox<>(new String[]{"한국어", "English", "日本語"});
        panel.add(languageComboBox, gbc);

        JButton confirmButton = new JButton(GConstants.getText(EAccountDialog.confirm));
        confirmButton.addActionListener(e -> handleLanguageChange());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(confirmButton, gbc);
    }

    private void initializeNameChange(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel(GConstants.getText(EAccountDialog.nameLabel)), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);

        JButton confirmButton = new JButton(GConstants.getText(EAccountDialog.confirm));
        confirmButton.addActionListener(e -> handleNameChange());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(confirmButton, gbc);
    }

    private void initializePasswordChange(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel(GConstants.getText(EAccountDialog.currentPasswordLabel)), gbc);

        gbc.gridx = 1;
        currentPasswordField = new JPasswordField(20);
        panel.add(currentPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel(GConstants.getText(EAccountDialog.newPasswordLabel)), gbc);

        gbc.gridx = 1;
        newPasswordField = new JPasswordField(20);
        panel.add(newPasswordField, gbc);

        JButton confirmButton = new JButton(GConstants.getText(EAccountDialog.confirm));
        confirmButton.addActionListener(e -> handlePasswordChange());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(confirmButton, gbc);
    }

    private void handleLanguageChange() {
        String selectedLanguage = (String) languageComboBox.getSelectedItem();
        System.out.println("=== Language Change Dialog Debug ===");
        System.out.println("Selected language from combo box: " + selectedLanguage);
        
        // 언어 코드로 변환
        String languageCode;
        switch (selectedLanguage) {
            case "한국어":
                languageCode = "kr";
                break;
            case "English":
                languageCode = "en";
                break;
            case "日本語":
                languageCode = "jp";
                break;
            default:
                languageCode = "kr";
        }
        System.out.println("Converted language code: " + languageCode);
        
        // GConstants의 언어 설정 먼저 변경
        GConstants gConstants = new GConstants();
        gConstants.setLanguage(languageCode);
        
        // UserManager를 통해 XML 파일 업데이트
        if (userManager.changeLanguage(languageCode)) {
            System.out.println("Language change successful in UserManager");
            JOptionPane.showMessageDialog(this,
                GConstants.getText(EAccountDialog.languageChangeSuccess),
                GConstants.getText(EAccountDialog.successTitle),
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            restartApplication();
        } else {
            System.out.println("Language change failed in UserManager");
            JOptionPane.showMessageDialog(this,
                GConstants.getText(EAccountDialog.languageChangeFail),
                GConstants.getText(EAccountDialog.failTitle),
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleNameChange() {
        String newName = nameField.getText().trim();
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                GConstants.getText(EAccountDialog.nameEmpty),
                GConstants.getText(EAccountDialog.failTitle),
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userManager.changeName(newName)) {
            JOptionPane.showMessageDialog(this,
                GConstants.getText(EAccountDialog.nameChangeSuccess),
                GConstants.getText(EAccountDialog.successTitle),
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                GConstants.getText(EAccountDialog.nameChangeFail),
                GConstants.getText(EAccountDialog.failTitle),
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handlePasswordChange() {
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());

        if (currentPassword.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                GConstants.getText(EAccountDialog.passwordEmpty),
                GConstants.getText(EAccountDialog.failTitle),
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userManager.changePassword(currentPassword, newPassword)) {
            JOptionPane.showMessageDialog(this,
                GConstants.getText(EAccountDialog.passwordChangeSuccess),
                GConstants.getText(EAccountDialog.successTitle),
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                GConstants.getText(EAccountDialog.passwordChangeFail),
                GConstants.getText(EAccountDialog.failTitle),
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this,
            GConstants.getText(EAccountDialog.logoutConfirm),
            GConstants.getText(EAccountDialog.title_logout),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (choice == JOptionPane.YES_OPTION) {
            userManager.logout();
            dispose();
            
            // 메인 프레임 찾기
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
            
            // 새로운 메인 프레임 생성
            GMainFrame mainFrame = new GMainFrame();
            mainFrame.initialize();
        }
    }

    private void restartApplication() {
        System.out.println("Restarting application...");
        try {
            // 현재 JVM 종료 전에 새로운 프로세스 시작
            String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            String className = "Frames.GMainFrame";
            
            ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
            builder.start();
            
            // 잠시 대기 후 현재 JVM 종료
            Thread.sleep(1000);
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Error restarting application: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void showLoginDialog() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
        
        LoginDialog loginDialog = new LoginDialog(null);
        loginDialog.setVisible(true);
    }
} 