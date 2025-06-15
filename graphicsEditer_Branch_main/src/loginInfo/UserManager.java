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

public class UserManager {
    private static final String USER_DATA_FILE = "loginInfo/users.xml";

    // XML 태그 이름을 위한 enum
    private enum EUser {
        name,
        id,
        password,
        language
    }

    // 사용자 검증 (로그인)
    public boolean checkUserData(String id, String password) {
        return isIdExists(id) && isPasswordCorrect(id, password);
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }
    }

    // XML 요소에서 텍스트 추출
    private String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
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
            e.printStackTrace();
        }
    }

    // XML 요소 추가
    private void addElement(Document document, Element parent, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }
}
