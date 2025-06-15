package global;

import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Frames.GDrawingPanel;
import Frames.GMainFrame;
import Frames.GMenuBar;
import Frames.GShapeToolBar;
import shapes.GEllipse;
import shapes.GLine;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GShape;
import shapes.GText;

public class GConstants {
    private static final String CONFIG_FILE_KR = "src/LanguageSetting/config_kr.xml";
	//한국어 파일 경로
    private static final String CONFIG_FILE_EN = "src/LanguageSetting/config_en.xml";
    //영어 파일 경로
	private static final String CONFIG_FILE_JP = "src/LanguageSetting/config_jp.xml";
	//일본어 파일 경로
	private static String currentConfigFile = CONFIG_FILE_KR;
	
    //기본 파일 경로(한국어)
    public GConstants() {//GConstants 클래스 생성자
        readFromFile(currentConfigFile);
    }
    
    public void setLanguage(String language) {// 외부에서 호출하면 언어 설정을 변경할 수 있도록 하는 메서드
        if (language.equals("en")) {
            currentConfigFile = CONFIG_FILE_EN;
		}else if (language.equals("jp")) {
            currentConfigFile = CONFIG_FILE_JP;
		}else {
            currentConfigFile = CONFIG_FILE_KR;
        }
        readFromFile(currentConfigFile);
    }
    
    public void readFromFile(String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //xml 파싱을 위한 팩토리 객체
			DocumentBuilder builder = factory.newDocumentBuilder();
			//DOM 파서를 생성할 빌더 생성
            File file = new File(fileName);
			//Dom을 통해 읽을 File 객체 생성
            Document document = builder.parse(file);
			//파일을 읽어 DOM트리로 파싱, 루트 노드를 찾음
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            //최상위 노드의 자식 노드들을 탐색
			for(int i = 0; i<nodeList.getLength(); i++) {//루트노드의 자식들에 대해서 모두 수행
                Node node = nodeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {// 해당 XML파일에 있는 노드가 "요소"를 나타내는 노드인지 여부 파악
				// 아마도 XML파일이 깨져있는지 아닌지, 형식에 문제가 없는지를 파악하는 것 같음
                    if(node.getNodeName().equals(GMainFrame.class.getSimpleName())) {
                        GMainFrame.setValues(node);
                    }else if(node.getNodeName().equals(GMenuBar.class.getSimpleName())) {
                        EMenuBar.setValues(node);
                    }else if(node.getNodeName().equals(EFileMenuItem.class.getSimpleName())){
                        EFileMenuItem.setValues(node);
                    }else if(node.getNodeName().equals(EEditMenuItem.class.getSimpleName())){
                        EEditMenuItem.setValues(node);
                    }else if(node.getNodeName().equals(EGraphicMenuItem.class.getSimpleName())){
                        EGraphicMenuItem.setValues(node);
                    }else if(node.getNodeName().equals(GDrawingPanel.class.getSimpleName())){
                        // GDrawingPanel.setValues(node);
                    }else if(node.getNodeName().equals(GShapeToolBar.class.getSimpleName())){
                        EShapeTool.setValues(node);
                    } else if(node.getNodeName().equals(ELoginDialog.class.getSimpleName())) {
                        ELoginDialog.setValues(node);
                    } else if(node.getNodeName().equals(ESignupDialog.class.getSimpleName())) {
                        ESignupDialog.setValues(node);
                    } else if(node.getNodeName().equals(EAccountMenuItem.class.getSimpleName())) {
                        EAccountMenuItem.setValues(node);
                    } else if(node.getNodeName().equals(EAccountDialog.class.getSimpleName())) {
                        EAccountDialog.setValues(node);
                    }
					//getSimpleName()을 사용하는 이유는 단지 "GMainFrame"으로 비교해도 되지만, 리펙토링 또는 오타 방지를 위해서 클래스의 이름을 정확하게 가져오기 위해 사용
                }
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

	//////////////////////////////////////////////////////////////////////
    public enum GMainFrame {
        eX(0),
        eY(0),
        eW(0),
        eH(0);

        public int value;

        private GMainFrame(int value) {
            this.value = value;
        }
        public int getValue() {
            return this.value;
        }
        public static void setValues(Node node) { 
            for(GMainFrame eMainFrame: GMainFrame.values()) {
                Node attribute = node.getAttributes().getNamedItem(eMainFrame.name());
                eMainFrame.value = Integer.parseInt(attribute.getNodeValue());
            }
        }
    }

	//////////////////////////////////////////////////////////////////////
    public enum EMenuBar {
        fileMenu(""),
        editMenu(""),
        graphicsMenu(""),
        accountMenu("");  // 계정 메뉴 추가

        private String label;

        private EMenuBar(String label) {
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public static void setValues(Node node) {
            for(EMenuBar eMenuBar: EMenuBar.values()) {
                Node attribute = node.getAttributes().getNamedItem(eMenuBar.name());
                if (attribute != null) {
                    eMenuBar.setLabel(attribute.getNodeValue());
                }
            }
        }
    }

	//////////////////////////////////////////////////////////////////////
    public final class GFileMenu {
        public final static String SS = ".shape";
        public final static String S = "shape";
        public final static String DS = "shape file (*.shape)";
    }
	//////////////////////////////////////////////////////////////////////
    public enum EFileMenuItem {
        eNew("", "newPanel"),
        eOpen("", "open"),
        eSave("", "save"),
        eSaveAs("", "saveAs"),
        ePrint("", "print"),
        eClose("", "close"),
        eQuit("", "quit");

        private String name;
        private final String methodName;
        private String toolTipText;

        private EFileMenuItem(String name, String methodName) {
            this.name = name;
            this.methodName = methodName;
            this.toolTipText = "";
        }

        public String getName() {
            return this.name;
        }

        public String getMethodName() {
            return this.methodName;
        }

        public String getToolTipText() {
            return this.toolTipText;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setToolTipText(String toolTipText) {
            this.toolTipText = toolTipText;
        }

        public static void setValues(Node node) {
            for(EFileMenuItem eMenuItem: EFileMenuItem.values()) {
                Node attribute = node.getAttributes().getNamedItem(eMenuItem.name());
                if (attribute != null) {
                    eMenuItem.setName(attribute.getNodeValue());
                }
                Node toolTipNode = node.getAttributes().getNamedItem("toolTipText");
                if (toolTipNode != null) {
                    eMenuItem.setToolTipText(toolTipNode.getNodeValue());
                }
            }
        }
    }
//////////////////////////////////////////////////////////////////////
    public enum EEditMenuItem {
        eUndo("", "undo"),
        eRedo("", "redo"),
        eProperty("", "property");

        private String name;
        private final String methodName;
        private String toolTipText;

        private EEditMenuItem(String name, String methodName) {
            this.name = name;
            this.methodName = methodName;
            this.toolTipText = "";
        }

        public String getName() {
            return this.name;
        }

        public String getMethodName() {
            return this.methodName;
        }

        public String getToolTipText() {
            return this.toolTipText;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setToolTipText(String toolTipText) {
            this.toolTipText = toolTipText;
        }

        public static void setValues(Node node) {
            for(EEditMenuItem eMenuItem: EEditMenuItem.values()) {
                Node attribute = node.getAttributes().getNamedItem(eMenuItem.name());
                if (attribute != null) {
                    eMenuItem.setName(attribute.getNodeValue());
                }
                Node toolTipNode = node.getAttributes().getNamedItem("toolTipText");
                if (toolTipNode != null) {
                    eMenuItem.setToolTipText(toolTipNode.getNodeValue());
                }
            }
        }
    }
//////////////////////////////////////////////////////////////////////
    public enum EGraphicMenuItem {
        eLineThickness("", "lineThickness"),
        eLineStyle("", "lineStyle"),
        eFontStyle("", "fontStyle"),
        eFontSize("", "fontSize");

        private String name;
        private final String methodName;
        private String toolTipText;

        private EGraphicMenuItem(String name, String methodName) {
            this.name = name;
            this.methodName = methodName;
            this.toolTipText = "";
        }

        public String getName() {
            return this.name;
        }

        public String getMethodName() {
            return this.methodName;
        }

        public String getToolTipText() {
            return this.toolTipText;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setToolTipText(String toolTipText) {
            this.toolTipText = toolTipText;
        }

        public static void setValues(Node node) {
            for(EGraphicMenuItem eMenuItem: EGraphicMenuItem.values()) {
                Node attribute = node.getAttributes().getNamedItem(eMenuItem.name());
                if (attribute != null) {
                    eMenuItem.setName(attribute.getNodeValue());
                }
                Node toolTipNode = node.getAttributes().getNamedItem("toolTipText");
                if (toolTipNode != null) {
                    eMenuItem.setToolTipText(toolTipNode.getNodeValue());
                }
            }
        }
    }

	//////////////////////////////////////////////////////////////////////
    public enum EShapeTool {
        eSelect("select", GShape.EPoints.e2P, GRectangle.class),
        eRectangle("rectangle", GShape.EPoints.e2P, GRectangle.class),
        eEllipse("ellipse", GShape.EPoints.e2P, GEllipse.class),
        eLine("line", GShape.EPoints.e2P, GLine.class),
        ePolygon("polygon", GShape.EPoints.eNP, GPolygon.class),
    	eText("text", GShape.EPoints.e2P, GText.class);

        private final String name;
        private String label;
        private GShape.EPoints ePoints;
        private Class<?> classShape;
        private EShapeTool(String name, GShape.EPoints ePoints, Class<?> classShape) {
            this.name = name;
            this.ePoints = ePoints;
            this.classShape = classShape;
            this.label = name;
        }
        public String getName() {
            return this.name;
        }
        public GShape.EPoints getEPoints() {
            return this.ePoints;
        }
        public GShape newShape() {
            GShape shape;
            try {
                shape = (GShape) classShape.getConstructor().newInstance();
                return shape;
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                     | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
            return null;
        }
        public String getLabel() { 
            return label;
        }
        public void setLabel(String label) { 
            this.label = label; 
        }
        public static void setValues(Node node) {
            for(EShapeTool eShapeTool: EShapeTool.values()) {
                Node attribute = node.getAttributes().getNamedItem(eShapeTool.name());
                if (attribute != null) {
                    eShapeTool.setLabel(attribute.getNodeValue());
                }
            }
        }
    }
	//////////////////////////////////////////////////////////////////////		
    public enum EDrawingPanel {
     
    }

	//////////////////////////////////////////////////////////////////////
    public enum EAnchors {
        eNN(new Cursor(Cursor.N_RESIZE_CURSOR)),
        eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
        eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
        eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
        eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),
        eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
        eEE(new Cursor(Cursor.E_RESIZE_CURSOR)),
        eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
        eRR(new Cursor(Cursor.HAND_CURSOR)),
        eMM(new Cursor(Cursor.MOVE_CURSOR));

        private Cursor cursor;
        private EAnchors(Cursor cursor) {
            this.cursor = cursor;
        }
        public Cursor getCursor() {
            return this.cursor;
        }
    }
    //////////////////////////////////////////////////////////////////////
    public enum ELoginDialog {
        title(""),
        idLabel(""),
        passwordLabel(""),
        loginButton(""),
        signupButton(""),
        loginSuccess(""),
        loginFail("");
    
        private String text;
    
        private ELoginDialog(String text) {
            this.text = text;
        }
    
        public String getText() {
            return this.text;
        }
    
        public void setText(String text) {
            this.text = text;
        }
    
        public static void setValues(Node node) {
            for(ELoginDialog eLoginDialog: ELoginDialog.values()) {
                Node attribute = node.getAttributes().getNamedItem(eLoginDialog.name());
                if (attribute != null) {
                    eLoginDialog.setText(attribute.getNodeValue());
                }
            }
        }
    }
    //////////////////////////////////////////////////////////////////////
    public enum ESignupDialog {
        title(""),
        nameLabel(""),
        idLabel(""),
        passwordLabel(""),
        confirmPasswordLabel(""),
        languageLabel(""),
        signupButton(""),
        cancelButton(""),
        signupSuccess(""),
        signupFail(""),
        emptyField(""),
        passwordMismatch("");
    
        private String text;
    
        private ESignupDialog(String text) {
            this.text = text;
        }
    
        public String getText() {
            return this.text;
        }
    
        public void setText(String text) {
            this.text = text;
        }
    
        public static void setValues(Node node) {
            for(ESignupDialog eSignupDialog: ESignupDialog.values()) {
                Node attribute = node.getAttributes().getNamedItem(eSignupDialog.name());
                if (attribute != null) {
                    eSignupDialog.setText(attribute.getNodeValue());
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////
    public enum EErrorMessage {
        initUserData(""),      // 사용자 데이터 파일 초기화 중 오류가 발생했습니다
        loginProcess(""),      // 로그인 처리 중 오류가 발생했습니다
        userInfoCheck(""),     // 사용자 정보 확인 중 오류가 발생했습니다
        passwordCheck(""),     // 비밀번호 확인 중 오류가 발생했습니다
        signupProcess(""),     // 회원가입 처리 중 오류가 발생했습니다
        fileSave(""),         // 파일 저장 중 오류가 발생했습니다
        errorTitle("");       // 오류
    
        private String text;
    
        private EErrorMessage(String text) {
            this.text = text;
        }
    
        public String getText() {
            return this.text;
        }
    
        public void setText(String text) {
            this.text = text;
        }
    
        public static void setValues(Node node) {
            for(EErrorMessage eErrorMessage: EErrorMessage.values()) {
                Node attribute = node.getAttributes().getNamedItem(eErrorMessage.name());
                if (attribute != null) {
                    eErrorMessage.setText(attribute.getNodeValue());
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////
    public enum EAccountMenuItem {
        changeLanguage("", "changeLanguage"),  // 언어 변경
        changeName("", "changeName"),         // 이름 변경
        changePassword("", "changePassword"), // 비밀번호 변경
        logout("", "logout");                 // 로그아웃

        private String name;
        private final String methodName;
        private String toolTipText;

        private EAccountMenuItem(String name, String methodName) {
            this.name = name;
            this.methodName = methodName;
            this.toolTipText = "";
        }

        public String getName() {
            return this.name;
        }

        public String getMethodName() {
            return this.methodName;
        }

        public String getToolTipText() {
            return this.toolTipText;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setToolTipText(String toolTipText) {
            this.toolTipText = toolTipText;
        }

        public static void setValues(Node node) {
            for(EAccountMenuItem eMenuItem: EAccountMenuItem.values()) {
                Node attribute = node.getAttributes().getNamedItem(eMenuItem.name());
                if (attribute != null) {
                    eMenuItem.setName(attribute.getNodeValue());
                }
                Node toolTipNode = node.getAttributes().getNamedItem("toolTipText");
                if (toolTipNode != null) {
                    eMenuItem.setToolTipText(toolTipNode.getNodeValue());
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////
    public enum EAccountDialog {
        // Dialog titles
        title_changeLanguage("Change Language"),
        title_changeName("Change Name"),
        title_changePassword("Change Password"),
        title_logout("Logout"),
        successTitle("Success"),
        failTitle("Error"),

        // Labels
        languageLabel("Language:"),
        nameLabel("New Name:"),
        currentPasswordLabel("Current Password:"),
        newPasswordLabel("New Password:"),
        confirm("Confirm"),

        // Messages
        languageChangeSuccess("Language changed successfully"),
        languageChangeFail("Failed to change language"),
        nameChangeSuccess("Name changed successfully"),
        nameChangeFail("Failed to change name"),
        passwordChangeSuccess("Password changed successfully"),
        passwordChangeFail("Failed to change password"),
        nameEmpty("Please enter a new name"),
        passwordEmpty("Please fill in all password fields"),
        logoutConfirm("Are you sure you want to logout?");

        private String text;

        EAccountDialog(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public static void setValues(Node node) {
            for(EAccountDialog dialog: EAccountDialog.values()) {
                Node attribute = node.getAttributes().getNamedItem(dialog.name());
                if (attribute != null) {
                    dialog.text = attribute.getNodeValue();
                }
            }
        }
    }

    public static String getText(EAccountDialog dialog) {
        return dialog.getText();
    }

    private static final String USER_DATA_FILE = "loginInfo/users.xml";

    public enum EUser {
        id("id"),
        password("password"),
        name("name"),
        language("language");

        private String tagName;

        EUser(String tagName) {
            this.tagName = tagName;
        }

        public String getTagName() {
            return tagName;
        }
    }
}