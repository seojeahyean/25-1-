package loginInfo;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.swing.JOptionPane;
import global.GConstants;
import global.GConstants.EErrorMessage;
import global.GConstants.EUser;

public class UserManager {
    private static final String USER_DATA_FILE = "loginInfo/users.xml";
    private static final String USER_DATA_DIR = "loginInfo";

    private static UserManager instance;

    private String currentUserId = null;

    public UserManager() {
        initializeUserDataFile();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private void initializeUserDataFile() {
        try {
            // 디렉토리 생성
            File directory = new File(USER_DATA_DIR);
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new RuntimeException("Failed to create directory: " + USER_DATA_DIR);
                }
            }

            // 파일이 없으면 기본 XML 구조 생성
            File file = new File(USER_DATA_FILE);
            if (!file.exists()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.newDocument();
                
                Element rootElement = document.createElement("users");
                document.appendChild(rootElement);
                
                saveDocument(document);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                EErrorMessage.initUserData.getText() + ": " + e.getMessage(),  // 사용자 데이터 파일 초기화 중 오류가 발생했습니다
                EErrorMessage.errorTitle.getText(),  // 오류
                JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Failed to initialize user data file", e);
        }
    }

    // 사용자 검증 (로그인)
    public boolean checkUserData(String id, String password) {
        try {
            return isIdExists(id) && isPasswordCorrect(id, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                EErrorMessage.loginProcess.getText() + ": " + e.getMessage(),  // 로그인 처리 중 오류가 발생했습니다
                EErrorMessage.errorTitle.getText(),  // 오류
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ID 존재 여부 확인
    private boolean isIdExists(String id) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(USER_DATA_FILE));

            NodeList userNodes = document.getElementsByTagName("user");
            for (int i = 0; i < userNodes.getLength(); i++) {
                Element userElement = (Element) userNodes.item(i);
                String userId = getElementText(userElement, EUser.id.name());
                if (userId.equals(id)) {
                    return true;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                EErrorMessage.userInfoCheck.getText() + ": " + e.getMessage(),  // 사용자 정보 확인 중 오류가 발생했습니다
                EErrorMessage.errorTitle.getText(),  // 오류
                JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // 비밀번호 일치 여부 확인
    private boolean isPasswordCorrect(String id, String password) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(USER_DATA_FILE));

            NodeList userNodes = document.getElementsByTagName("user");
            for (int i = 0; i < userNodes.getLength(); i++) {
                Element userElement = (Element) userNodes.item(i);
                String userId = getElementText(userElement, EUser.id.name());
                if (userId.equals(id)) {
                    String userPassword = getElementText(userElement, EUser.password.name());
                    return userPassword.equals(password);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                EErrorMessage.passwordCheck.getText() + ": " + e.getMessage(),  // 비밀번호 확인 중 오류가 발생했습니다
                EErrorMessage.errorTitle.getText(),  // 오류
                JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // 회원가입
    public boolean signUp(String name, String id, String password, String language) {
        try {
            // ID 중복 검사
            if (isIdExists(id)) {
                return false;
            }

            // User 객체 생성
            User newUser = new User(name, id, password, language);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(USER_DATA_FILE));

            Element rootElement = document.getDocumentElement();
            Element userElement = document.createElement("user");

            // User 객체의 정보를 XML에 추가
            addElement(document, userElement, EUser.name.name(), newUser.getName());
            addElement(document, userElement, EUser.id.name(), newUser.getId());
            addElement(document, userElement, EUser.password.name(), newUser.getPassword());
            addElement(document, userElement, EUser.language.name(), newUser.getLanguage());

            rootElement.appendChild(userElement);
            saveDocument(document);

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                EErrorMessage.signupProcess.getText() + ": " + e.getMessage(),  // 회원가입 처리 중 오류가 발생했습니다
                EErrorMessage.errorTitle.getText(),  // 오류
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // XML 요소에서 텍스트 추출
    private String getElementText(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    // XML 문서 저장
    private void saveDocument(Document document) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(USER_DATA_FILE));
            transformer.transform(source, result);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                EErrorMessage.fileSave.getText() + ": " + e.getMessage(),  // 파일 저장 중 오류가 발생했습니다
                EErrorMessage.errorTitle.getText(),  // 오류
                JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Failed to save document", e);
        }
    }

    // XML 요소 추가
    private void addElement(Document document, Element parent, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    // XML 태그 이름을 위한 enum
    private enum EUser {
        name,
        id,
        password,
        language
    }

    // 언어 변경
    public boolean changeLanguage(String newLanguage) {
        try {
            System.out.println("=== Language Change Debug ===");
            System.out.println("Attempting to change language to: " + newLanguage);
            System.out.println("Current user ID: " + getCurrentUserId());
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(USER_DATA_FILE));

            NodeList userNodes = document.getElementsByTagName("user");
            System.out.println("Number of users found: " + userNodes.getLength());
            
            for (int i = 0; i < userNodes.getLength(); i++) {
                Element userElement = (Element) userNodes.item(i);
                String userId = getElementText(userElement, EUser.id.name());
                System.out.println("Checking user ID: " + userId);
                
                if (userId.equals(getCurrentUserId())) {
                    System.out.println("Found matching user");
                    NodeList languageNodes = userElement.getElementsByTagName(EUser.language.name());
                    if (languageNodes.getLength() == 0) {
                        System.out.println("No language node found, creating new one");
                        Element languageElement = document.createElement(EUser.language.name());
                        languageElement.setTextContent(newLanguage);
                        userElement.appendChild(languageElement);
                    } else {
                        System.out.println("Updating existing language node");
                        languageNodes.item(0).setTextContent(newLanguage);
                    }
                    
                    saveDocument(document);
                    System.out.println("Language changed successfully for user: " + userId);
                    
                    // GConstants의 언어 설정도 업데이트
                    GConstants gConstants = new GConstants();
                    gConstants.setLanguage(newLanguage);
                    
                    return true;
                }
            }
            System.out.println("User not found with ID: " + getCurrentUserId());
        } catch (Exception e) {
            System.out.println("Error changing language: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                EErrorMessage.fileSave.getText() + ": " + e.getMessage(),
                EErrorMessage.errorTitle.getText(),
                JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // 이름 변경
    public boolean changeName(String newName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(USER_DATA_FILE));

            NodeList userNodes = document.getElementsByTagName("user");
            for (int i = 0; i < userNodes.getLength(); i++) {
                Element userElement = (Element) userNodes.item(i);
                String userId = getElementText(userElement, EUser.id.name());
                if (userId.equals(getCurrentUserId())) {
                    Node nameNode = userElement.getElementsByTagName(EUser.name.name()).item(0);
                    nameNode.setTextContent(newName);
                    saveDocument(document);
                    return true;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                EErrorMessage.fileSave.getText() + ": " + e.getMessage(),
                EErrorMessage.errorTitle.getText(),
                JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // 비밀번호 변경
    public boolean changePassword(String currentPassword, String newPassword) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(USER_DATA_FILE));

            NodeList userNodes = document.getElementsByTagName("user");
            for (int i = 0; i < userNodes.getLength(); i++) {
                Element userElement = (Element) userNodes.item(i);
                String userId = getElementText(userElement, EUser.id.name());
                if (userId.equals(getCurrentUserId())) {
                    String storedPassword = getElementText(userElement, EUser.password.name());
                    if (storedPassword.equals(currentPassword)) {
                        Node passwordNode = userElement.getElementsByTagName(EUser.password.name()).item(0);
                        passwordNode.setTextContent(newPassword);
                        saveDocument(document);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                EErrorMessage.fileSave.getText() + ": " + e.getMessage(),
                EErrorMessage.errorTitle.getText(),
                JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void login(String userId) {
        this.currentUserId = userId;
    }

    public void logout() {
        this.currentUserId = null;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public String getUserLanguage(String userId) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(USER_DATA_FILE));

            NodeList userNodes = document.getElementsByTagName("user");
            for (int i = 0; i < userNodes.getLength(); i++) {
                Element userElement = (Element) userNodes.item(i);
                String id = getElementText(userElement, EUser.id.name());
                if (id.equals(userId)) {
                    return getElementText(userElement, EUser.language.name());
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting user language: " + e.getMessage());
        }
        return null;
    }
}
