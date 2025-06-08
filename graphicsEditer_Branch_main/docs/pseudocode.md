# 그래픽 에디터 Pseudo code

## 1. initialize 단계

### 메인 프레임 (GMainFrame) 초기화

GMainFrame.initialize():
    // 컴포넌트 생성 및 설정
    menuBar = new GMenuBar()
    toolBar = new GShapeToolBar()
    drawingPanel = new GDrawingPanel()
    
    // 레이아웃 설정
    setLayout(new BorderLayout())
    add(toolBar, NORTH)
    add(drawingPanel, CENTER)
    setJMenuBar(menuBar)
    
    // 컴포넌트 연결
    toolBar.associate(drawingPanel)
    menuBar.associate(drawingPanel)
    
    // 컴포넌트 초기화
    menuBar.initialize()
    toolBar.initialize()
    drawingPanel.initialize()
    
    // 프레임 표시
    setVisible(true)


## 2. 도형 그리기 프로세스

### 도구 선택 및 그리기 초기화
```pseudocode
GToolBar.selectTool(도구타입):
    현재선택도구 = 도구타입
    drawingPanel.setCurrentTool(도구타입)
    return 현재선택도구

GDrawingPanel.setCurrentTool(도구타입):
    currentTool = ShapeFactory.createTool(도구타입)
    return currentTool
```

### 마우스 이벤트 처리 및 도형 생성
```pseudocode
GDrawingPanel.mousePressed(e):
    startP = new Point(e.getX(), e.getY())
    currentShape = ShapeFactory.createShape(currentTool)
    currentShape.setStartPoint(startP)
    return currentShape

GDrawingPanel.mouseDragged(e):
    currentP = new Point(e.getX(), e.getY())
    currentShape.setEndPoint(currentP)
    drawingPanel.repaint()  // 임시 도형 그리기

GDrawingPanel.mouseReleased(e):
    endP = new Point(e.getX(), e.getY())
    currentShape.setEndPoint(endP)
    shapeList.add(currentShape)  // 완성된 도형을 목록에 추가
    drawingPanel.repaint()  // 최종 도형 그리기
```

## 3. 도형 변형 프로세스

### 선택 및 핸들 표시
```pseudocode
GDrawingPanel.selectShape(Point p):
    selectedShape = null
    FOR shape IN shapeList.reverse():  // 위에 있는 도형부터 검사
        IF shape.contains(p):
            selectedShape = shape
            transformer = TransformerFactory.create(selectedShape)
            transformer.showHandle()
            break
    return selectedShape

GTransformer.showHandle():
    handles = createHandles()  // 8개의 크기조절 핸들과 1개의 회전 핸들
    drawingPanel.repaint()
```

### 크기 조절 처리
```pseudocode
GTransformer.resize(Point p):
    // 핸들 위치에 따라 크기 조절 방향 결정
    direction = getResizeDirection(selectedHandle)
    
    // 새로운 크기 계산
    newBounds = calculateNewBounds(p, direction)
    
    // 도형 크기 업데이트
    selectedShape.setBounds(newBounds)
    updateHandlePositions()
    drawingPanel.repaint()
    
    return selectedShape
```

### 회전 처리
```pseudocode
GTransformer.rotate(Point p):
    // 회전 중심점 계산
    centerP = selectedShape.getCenter()
    
    // 회전 각도 계산
    angle = calculateAngle(centerP, p)
    
    // 도형 회전 적용
    selectedShape.setRotation(angle)
    updateHandlePositions()
    drawingPanel.repaint()
    
    return selectedShape
```

## 4. 파일 관리 프로세스

### 저장 프로세스
```pseudocode
GMenuBar.save():
    fileChooser = new JFileChooser()
    IF fileChooser.showSaveDialog() == APPROVE:
        file = fileChooser.getSelectedFile()
        return FileManager.saveToFile(file)

FileManager.saveToFile(file):
    // 도형 목록을 직렬화하여 저장
    shapeData = serializeShapes(drawingPanel.getShapeList())
    writeToFile(file, shapeData)
    return 저장성공여부
```

### 열기 프로세스
```pseudocode
GMenuBar.open():
    fileChooser = new JFileChooser()
    IF fileChooser.showOpenDialog() == APPROVE:
        file = fileChooser.getSelectedFile()
        return FileManager.loadFromFile(file)

FileManager.loadFromFile(file):
    // 파일에서 도형 데이터 읽기
    shapeData = readFromFile(file)
    shapeList = deserializeShapes(shapeData)
    
    // 그리기 패널 업데이트
    drawingPanel.setShapeList(shapeList)
    drawingPanel.repaint()
    return 로드성공여부
```

## 주요 클래스 구조

- GMainFrame: 메인 윈도우 프레임
  - 전체 UI 컴포넌트 관리
  - 초기화 프로세스 제어
  - 컴포넌트 간 연결 관리

- GMenuBar: 메뉴 바
  - 메뉴 아이템 관리
  - 드로잉 패널과 연결

- GShapeToolBar: 도구 모음
  - 도형 도구 버튼 관리
  - 드로잉 패널과 연결

- GDrawingPanel: 그리기 영역
  - 그리기 작업 수행
  - 마우스 이벤트 처리

## 향후 개발 계획

1. 기본 도형 그리기 구현
   - 각 도형별 그리기 알고리즘 구현
   - 도형 스타일 속성 관리

2. 선택 도구 구현
   - 다중 선택 지원
   - 그룹화/그룹해제 기능

3. 실행 취소/다시 실행 기능
   - Command 패턴 적용
   - 작업 이력 관리

4. 파일 저장/불러오기 구현
   - 직렬화 방식 구현
   - 다양한 파일 포맷 지원 