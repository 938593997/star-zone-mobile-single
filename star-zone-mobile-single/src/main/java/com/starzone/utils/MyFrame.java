package com.starzone.utils;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
 
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
 
public class MyFrame {
	static JFrame jframe;	//窗体
	static JMenuBar jmenuBar;		//菜单栏
	static JMenu jmenu1,jmenu2,jmenu3,jmenu4;		//菜单
	static JTextArea text;	//文本框
	static JMenuItem newFile,open,save,saveAs,closeItem,copy,cut,stick,next;	//子菜单
	static JFileChooser openFile,saveFile;		//打开和保存文件窗口
	static JScrollPane scroll;		//=滚动条
	static Clipboard clip;	//复制、剪切、黏贴对象
	
	//窗体的基础类
	public static void init(){
		
		//基本窗体设置
		jframe = new JFrame("记事本");
		jframe.setBounds(300,200,650,500);
		
		jmenuBar = new JMenuBar();	//菜单栏
		text = new JTextArea();		//多行文本框
		
		//主菜单
		jmenu1 = new JMenu("文件");
		jmenu2 = new JMenu("编辑");
		jmenu3 = new JMenu("格式");
		
		//将主菜单分别加入到菜单栏
		jmenuBar.add(jmenu1);
		jmenuBar.add(jmenu2);
		jmenuBar.add(jmenu3);
		
		//子菜单
		newFile = new JMenuItem("新建");
		open = new JMenuItem("打开");
		save = new JMenuItem("保存");
		saveAs = new JMenuItem("另存为");
		closeItem = new JMenuItem("退出");
		
		copy = new JMenuItem("复制");
		cut = new JMenuItem("剪切");
		stick = new JMenuItem("黏贴");
		next = new JMenuItem("下一步");
		
		//将子菜单加入到主菜单中
		jmenu1.add(newFile);
		jmenu1.add(open);
		jmenu1.add(save);
		jmenu1.add(saveAs);
		jmenu1.add(closeItem);
		
		jmenu2.add(copy);
		jmenu2.add(cut);
		jmenu2.add(stick);
		
		//设置滚动条（需要的时候出现）
		scroll = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
				,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		jframe.setJMenuBar(jmenuBar);
//		jframe.add(text);
		jframe.add(scroll);
		
		//响应
		MyEvent();
		
		
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	//响应类
	public static void MyEvent(){
		//新建
		newFile.addActionListener(new ActionListener(){
 
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				text.setText(null);
			}
			
		});
		
		//打开
		open.addActionListener(new ActionListener(){
 
			@SuppressWarnings("resource")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame jframeOpenCode = new JFrame("请输入秘钥");
				JMenuBar jmenuBarOpenCode = new JMenuBar();	//菜单栏
				jmenu4 = new JMenu("操作");
				jmenuBarOpenCode.add(jmenu4);
				jframeOpenCode.setBounds(300,200,300,300);
				JTextArea textOpenCode = new JTextArea();		//多行文本框
				//设置滚动条（需要的时候出现）
				scroll = new JScrollPane(textOpenCode,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
						,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				jframeOpenCode.add(scroll);
				jframeOpenCode.setVisible(true);
				jframeOpenCode.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jframeOpenCode.setJMenuBar(jmenuBarOpenCode);
				jmenu4.add(next);
				next.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(textOpenCode.getText().replace(" ", "") != null && !"".equals(textOpenCode.getText().replace(" ", ""))){
							jframeOpenCode.setVisible(false);
							openFile = new JFileChooser();
							openFile.showOpenDialog(null);
							String filePath = openFile.getSelectedFile().getAbsolutePath();
							File file = new File(filePath);
							
							try {
								//从文件读取数据
								BufferedReader read = new BufferedReader(new FileReader(file));
								String str = null;
								//将读取到的数据存放在文本框区域内
								while((str = read.readLine()) != null){
									text.append(str+"\n");
								}
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else{
							System.out.println("没进");
							JOptionPane.showMessageDialog(null, "提示消息", "请输入秘钥",JOptionPane.WARNING_MESSAGE);  
						}
					}
				});
			}
			
		});
		
		//保存
		save.addActionListener(new ActionListener(){
 
			@SuppressWarnings("resource")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveFile = new JFileChooser();
				
			/*	saveFile.showOpenDialog(null);
				BufferedWriter bw ;
				try {
					bw = new BufferedWriter(new FileWriter(saveFile.getSelectedFile().getPath()));
					bw.write(text.getText());
					bw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				
				
				
				saveFile.showSaveDialog(null);
				String fileName = saveFile.getSelectedFile().getAbsolutePath()+".txt";
				File file = new File(fileName);
				//若文件不存在则新建，否则输出“文件已存在”
				if(!file.exists()){
					try {
						file.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					System.out.println("文件已存在");
				}
				
				//文本框区域的内容
				String content = text.getText();
				
				//将文本区域中的内容保存到文件中
				try {
					FileOutputStream out = new FileOutputStream(file);
					byte[] contentByte = content.getBytes();
					out.write(contentByte, 0, contentByte.length);
//					BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//					writer.write(content);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		//退出
		closeItem.addActionListener(new ActionListener(){
 
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		
		//复制
		copy.addActionListener(new ActionListener(){
 
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clip = new Clipboard(text.getSelectedText());
			}
			
		});
		
		//剪切
		cut.addActionListener(new ActionListener(){
 
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				clip = new Clipboard(text.getSelectedText());
				text.replaceRange("", text.getSelectionStart(), text.getSelectionEnd());
			}
			
		});
		
		//黏贴
		stick.addActionListener(new ActionListener(){
 
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String str = clip.getName();
				text.replaceRange(str, text.getSelectionStart(), text.getSelectionEnd());
			}
			
		});
	}
	
	//主函数
	public static void main(String[] args){
		init();
	}
}