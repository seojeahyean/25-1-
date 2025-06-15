package global;

import Frames.GMainFrame;

public class GMain {
	
	public static void main(String[] args) {
		//create aggregation hierarchy
		GConstants constants = new GConstants();  
		GMainFrame mainFrame = new GMainFrame();
		mainFrame.initialize();
		
	}

}
