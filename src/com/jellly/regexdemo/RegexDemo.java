package com.jellly.regexdemo;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RegexDemo implements ActionListener{
	private JFrame f;//窗体
	//private JDialog d1,d2;//对话框
	private String opendir,savedir;
	private JPanel panel1, panel3, panel4 ;
	private JLabel label1, label2, label3;
	private JButton button1, button2, button3, button4, button5, button6,
			button7;
	private JScrollPane scrollpane1, scrollpane2;
	private JTextArea textarea1, textarea2;  
	private JTextField textfield1;
    
	public static void main(String[] args) {
		RegexDemo demo = new RegexDemo();
		demo.init();   
	}

	void init() {
		f = new MyJFrame("正则表达式工具", 550, 550, 450, 100);
		f.setLayout(new GridBagLayout());
       // f.setResizable(false);  
		label1 = new JLabel("源字符");
		label2 = new JLabel("正则表达式");
		label3 = new JLabel("匹配结果");

		button1 = new JButton("打开");
		button2 = new JButton("清空");
		button3 = new JButton("清空");
		button4 = new JButton("匹配");
		button5 = new JButton("替换");
		button6 = new JButton("清空");
		button7 = new JButton("保存");

		button1.addActionListener(this);
		button1.setActionCommand("btn1");
		button2.addActionListener(this);
		button2.setActionCommand("btn2");
		button3.addActionListener(this);
		button3.setActionCommand("btn3");
		button4.addActionListener(this);
		button4.setActionCommand("btn4");
		button5.addActionListener(this);
		button5.setActionCommand("btn5");
		button6.addActionListener(this);
		button6.setActionCommand("btn6");
		button7.addActionListener(this);
		button7.setActionCommand("btn7");

		textarea1 = new JTextArea();
		textarea2 = new JTextArea();
		scrollpane1 = new JScrollPane(textarea1);
		scrollpane2 = new JScrollPane(textarea2);

		textfield1 = new JTextField(30);
		textarea1.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown()&& e.getKeyCode() == KeyEvent.VK_O) {
					//System.out.println("ctrl +S has pressed");
				 open();//打开
		    }
			}
		});
		textarea2.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown()&& e.getKeyCode() == KeyEvent.VK_S) {
						//System.out.println("ctrl +S has pressed");
					 save();//保存
			    }
			}
		});
		panel1 = new JPanel();
		//panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		//panel5 = new JPanel();

		panel1.add(label1);
		panel1.add(button1);
		panel1.add(button2);

		panel3.add(label2);
		panel3.add(textfield1);
		panel3.add(button3);
		panel4.add(label3);
		panel4.add(button4);
		panel4.add(button5);
		panel4.add(button6);
		panel4.add(button7);

		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridheight = 1;
		gc.insets = new Insets(10, 0, 5, 0);
		f.add(panel1, gc);

		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridheight = 2;
		gc.fill = GridBagConstraints.BOTH;
		gc.weighty =2;
		gc.weightx = 2;
		gc.insets = new Insets(0, 30, 0, 30);
		f.add(scrollpane1, gc);

		gc.gridx = 0;
		gc.gridy = 5;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.BOTH;
	 	gc.weighty =0;
		 gc.weightx = 0;
		gc.insets = new Insets(0, 0, 0, 0);
		//gc.ipadx=1;
		//gc.ipady=1;
		 
		f.add(panel3, gc);

		gc.gridx = 0;
		gc.gridy = 6;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.NONE;
		f.add(panel4, gc);
		gc.ipadx=0;
		gc.ipady=0;
		gc.insets = new Insets(0, 0, 0, 0);

		gc.gridx = 0;
		gc.gridy = 7;
		gc.gridheight = 4;
		gc.fill = GridBagConstraints.BOTH;
		gc.weighty = 2;
		gc.weightx = 2;
		gc.insets = new Insets(0, 30, 30, 30);
		f.add(scrollpane2, gc);
          
		f.setResizable(true);
		f.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "btn1"://打开
             open();
			break;
		case "btn2":
			textarea1.setText("");
			break;
		case "btn3":
          textfield1.setText("");
			break;
		case "btn4":  //点击匹配按钮
		      match();//执行匹配方法
			break;
		case "btn5": //点击替换按钮
            replace();  //执行替换
			break;
		case "btn6"://点击清空按钮 
			textarea2.setText("");
			break;
		case "btn7"://保存
             save();
			break;
		}
	}
	void match(){//匹配
		 String str=textarea1.getText();
	       if(str==null||str.trim().length()==0){
	    	 //弹出提示框
	          alertDialog("提示","请输入源字符",500, 300, 200, 150,"确定");
	    	  return;
	       }
	       String regex=textfield1.getText();
	       if(regex==null||regex.equals("")){
	    	   //弹出提示框
	    	   alertDialog("提示","请输入正则表达式",500, 300, 200, 150,"确定");
	    	   return ;
	       }
	       textarea2.setText("");
	       Pattern p=Pattern.compile(regex);
	       Matcher m=p.matcher(str);
     while(m.find())
     {
       // System.out.println("开始的位置 ："+m.start()+"结束位置："+m.end());
   	   String s =  m.group();
   	// System.out.println(s);
   	  textarea2.append(s+"\n");
     }
     
		
	}
	
	void replace(){//替换
	    	String str=textarea1.getText();
	       if(str==null||str.trim().length()==0){
	    	 //弹出提示框
	          alertDialog("提示","请输入源字符",500, 300, 200, 150,"确定");
	    	  return;
	       }
	       String regex=textfield1.getText();
	       if(regex==null||regex.equals("")){
	    	   //弹出提示框
	    	   alertDialog("提示","请输入正则表达式",500, 300, 200, 150,"确定");
	    	   return ;
	       }
		showTextDialog("提示","请输入正则表达式替换字符",500, 300,250,200,"确定","取消");
		
	}
	
	void open(){//文件打开
		 InputStreamReader  reader = null;
		try {
		 JFileChooser wjopen=null;
		 if(opendir!=null&&opendir.trim().length()>0){
			 wjopen=new JFileChooser(opendir);
		 }else{
			 wjopen=new JFileChooser();
		 }

		wjopen.setDialogTitle("文件打开");
		FileNameExtensionFilter fef1=new FileNameExtensionFilter("文本文件","txt");
		FileNameExtensionFilter fef2=new FileNameExtensionFilter("java源文件","java");
		FileNameExtensionFilter fef3=new FileNameExtensionFilter("c#源文件","cs");
		FileNameExtensionFilter fef4=new FileNameExtensionFilter("c源文件","c");
		FileNameExtensionFilter fef5=new FileNameExtensionFilter("c++源文件","cpp");
		FileNameExtensionFilter fef6=new FileNameExtensionFilter("php源文件","php");
		FileNameExtensionFilter fef7=new FileNameExtensionFilter("JavaScript源文件","js");
		FileNameExtensionFilter fef8=new FileNameExtensionFilter("sql文件","sql");
		wjopen.addChoosableFileFilter(fef1);
		wjopen.addChoosableFileFilter(fef2);
		wjopen.addChoosableFileFilter(fef3);
		wjopen.addChoosableFileFilter(fef4);
		wjopen.addChoosableFileFilter(fef5);
		wjopen.addChoosableFileFilter(fef6);
		wjopen.addChoosableFileFilter(fef7);
		wjopen.addChoosableFileFilter(fef8);
		
		wjopen.setMultiSelectionEnabled(true);
		
		wjopen.setDialogType(JFileChooser.OPEN_DIALOG);
		int value=wjopen.showOpenDialog(null);
		 wjopen.setVisible(true);
		 if(value==JFileChooser.APPROVE_OPTION){//用户点击了打开
			  File[] openfiles= wjopen.getSelectedFiles();//选择的文件
			  if(openfiles!=null){
				  this.opendir=openfiles[0].getParent();
				  for(int i=0;i<openfiles.length;i++){
					   String encoding= FileUtils.getFileEncoding(openfiles[i].getAbsolutePath());
						
						  reader=new InputStreamReader(new FileInputStream(openfiles[i]), encoding);
						   char[] buf=new char[1024];
						   int len=0;
						   while((len=reader.read(buf))>0){
							   textarea1.append (new String(buf,0,len));
						   }
				   }
			  }
				  
		 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		 finally {//关流操作
             try {
                 if (reader != null) {
                	 reader.close();
                 }
             }
             catch (Exception e) {
                   System.out.println("InputStreamReader close IOException" + e.getStackTrace());
             }
       }
	}
	
	void save(){//保存
		BufferedWriter writer=null;
		
		try {
			 JFileChooser wjsave=null;
			 if(savedir!=null&&savedir.trim().length()>0){
				 wjsave=new JFileChooser(savedir);
			 }else{
				 wjsave=new JFileChooser();
			 }
			 wjsave.setDialogTitle("保存到文件");
			 
				FileNameExtensionFilter fef1=new FileNameExtensionFilter("文本文件","txt");
				FileNameExtensionFilter fef2=new FileNameExtensionFilter("java源文件","java");
				FileNameExtensionFilter fef3=new FileNameExtensionFilter("c#源文件","cs");
				FileNameExtensionFilter fef4=new FileNameExtensionFilter("c源文件","c");
				FileNameExtensionFilter fef5=new FileNameExtensionFilter("c++源文件","cpp");
				FileNameExtensionFilter fef6=new FileNameExtensionFilter("php源文件","php");
				FileNameExtensionFilter fef7=new FileNameExtensionFilter("JavaScript源文件","js");
				FileNameExtensionFilter fef8=new FileNameExtensionFilter("sql文件","sql");
				wjsave.addChoosableFileFilter(fef1);
				wjsave.addChoosableFileFilter(fef2);
				wjsave.addChoosableFileFilter(fef3);
				wjsave.addChoosableFileFilter(fef4);
				wjsave.addChoosableFileFilter(fef5);
				wjsave.addChoosableFileFilter(fef6);
				wjsave.addChoosableFileFilter(fef7);
				wjsave.addChoosableFileFilter(fef8);
			 
			 wjsave.setDialogType(JFileChooser.SAVE_DIALOG);
			int value= wjsave.showSaveDialog(null);
			 wjsave.setVisible(true);
			 if(value==JFileChooser.APPROVE_OPTION){//用户点击了保存
				   File savefile= wjsave.getSelectedFile();// 保存的文件
				   savedir= savefile.getParent();//将文件目录保存到成员变量
				 //  writer=new BufferedWriter(new FileWriter(savefile));
				  writer = new BufferedWriter(new FileWriter(savefile));
				  String result=textarea2.getText();
				  String[] temp=result.split("[\\r\\n]");
				  
				  for (int i = 0; i < temp.length; i++) {
					  writer.write(temp[i]);
					  writer.write("\r\n");
				}
				   
			 }
			
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {//关流操作
            try {
                if (writer != null) {
                	writer.close();
                }
            }
            catch (Exception e) {
                  System.out.println("BufferedWriter close IOException" + e.getStackTrace());
            }
      }
	}
	
	
	void alertDialog(String title,String text,int x,int y ,int width,int height,String btntext){//弹出一个框
	  if(title==null||title.trim().length()==0){
		  title="提示";
	  }
	  if(btntext==null||btntext.trim().length()==0){
		  btntext="确定";
	  }
 	  final  JDialog  d=new JDialog(f);
 	  // d.setBounds(480, 300, 200, 150);
 	   d.setBounds(x, y, width, height);
 	   d.setTitle(title);
 	   JLabel label=new JLabel(text);
 	   JButton ok=new JButton(btntext);
 	   ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 d.dispose();
			}
		});
 	   
 	   
 	   d.setLayout(new GridBagLayout());
 		GridBagConstraints gc = new GridBagConstraints();
 		gc.gridx=0;gc.gridy=0;gc.gridheight=1;gc.gridwidth=3;
 		 
 		gc.insets=new Insets(5,5,20,5);
 	   d.add(label,gc);
 	   gc.gridx=0;gc.gridy=1;gc.gridheight=1;gc.gridwidth=1;
 	   gc.insets=new Insets(10,20,10,5);
 	    d.add(ok,gc);
 	    d.setModal(true);
 	    d.setVisible(true);
	    d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	void showTextDialog(String title,String text,int x,int y ,int width,int height,String oktext,String calceltext){//弹出一个框
		
		
		final  JDialog  d=new JDialog(f);
		 d.setBounds(x, y, width, height);
	 	 d.setTitle(title);
	 	JButton okbtn=new JButton(oktext);
	 	JButton cancelbtn=new JButton(calceltext);
	 	final JTextField textfield=new JTextField(15);//输入框
	 	JLabel label=new JLabel(text);
	 	d.setLayout(new GridLayout(3, 1));
	 	cancelbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 d.dispose();
			}
		});
	  okbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			  //  RegexDemo.txt=textfield.getText();
			   // System.out.println(RegexDemo.txt);
				String str=textarea1.getText();
				String regex=textfield1.getText();//
				String replacestr= textfield.getText();
				String s=str.replaceAll(regex, replacestr);
				textarea2.setText(s);
				d.dispose();
			}
		});
	 	okbtn.addActionListener(this);
	 	okbtn.setActionCommand("closeText");
	 	
	 	JPanel panel1=new JPanel();
	 	JPanel panel2=new JPanel();
	 	JPanel panel3=new JPanel();
	 	
	 	panel1.add(label);
	 	panel2.add(textfield);
	 	panel3.add(cancelbtn);
	 	panel3.add(okbtn);
	 	
	 	  d.add(panel1);
	 	  d.add(panel2);
	 	  d.add(panel3);
	 	 d.setModal(true);
	 	 d.setVisible(true);
	 	 d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

 
	 
		
}
