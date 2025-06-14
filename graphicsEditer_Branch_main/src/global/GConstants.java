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
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GShape;

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
        graphicsMenu("");

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
        public final static String DS = "Shape file (*.shape)";
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
        eEllipse("ellipse", GShape.EPoints.e2P, GRectangle.class),
        eLine("line", GShape.EPoints.e2P, GRectangle.class),
        ePolygon("polygon", GShape.EPoints.eNP, GPolygon.class);

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
        // 추후 확장 가능
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
}
