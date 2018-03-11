package com.jellly.regexdemo;

import java.awt.HeadlessException;

import javax.swing.JFrame;

 
	public class MyJFrame extends JFrame {
		 
		private static final long serialVersionUID = 1L;
		public MyJFrame() throws HeadlessException{
			super();  
		}
		public MyJFrame(String title,int width,int height, int x,int y){
			   super();    
			   this.setTitle(title);
			   this.setSize(width, height);
			   this.setLocation(x, y);
			   this.setResizable(false);
			   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   }
	}

 
