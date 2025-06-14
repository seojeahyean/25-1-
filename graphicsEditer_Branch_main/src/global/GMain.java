package global;

import Frames.GMainFrame;

public class GMain {
	
	public static void main(String[] args) {
		//create aggregation hierarchy
		GConstants constants = new GConstants();  // GConstants 초기화
		GMainFrame mainFrame = new GMainFrame();
		//tree traverse (DFS)
		mainFrame.initialize();
		
	}

}
