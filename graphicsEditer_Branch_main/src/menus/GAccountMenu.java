package menus;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import global.GConstants.EAccountMenuItem;
import Frames.GDrawingPanel;
import loginInfo.AccountDialog;
import loginInfo.UserManager;

public class GAccountMenu extends JMenu {
    private static final long serialVersionUID = 1L;
    private GDrawingPanel drawingPanel;
    private UserManager userManager;

    public GAccountMenu() {
        super(global.GConstants.EMenuBar.accountMenu.getLabel());
        this.userManager = UserManager.getInstance();
        
        for (EAccountMenuItem eMenuItem: EAccountMenuItem.values()) {
            JMenuItem menuItem = new JMenuItem(eMenuItem.getName());
            menuItem.addActionListener(new AccountMenuHandler());
            menuItem.setActionCommand(eMenuItem.getMethodName());
            this.add(menuItem);
        }
    }

    public void associate(GDrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    private class AccountMenuHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
           
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(drawingPanel);// 언어 변환을 위한 어쩔수 없는 코드
            AccountDialog accountDialog = new AccountDialog(owner, command);
            accountDialog.setVisible(true);
        }
    }
} 