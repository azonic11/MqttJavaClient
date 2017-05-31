package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class DashBoard implements ActionListener {

	Container cp;
	JFrame frame;
	
	public DashBoard(){
		System.out.println("JFrame starting");
		 frame=new JFrame();
		cp=frame.getContentPane();
		cp.setBackground(Color.DARK_GRAY);
		//frame.setSize(new Dimension(400,600));
		
		frame.setPreferredSize(new Dimension(440,400));
		frame.setMinimumSize(new Dimension(100,170));
		
		
		cp.setLayout(new FlowLayout(0));

		cp.add(new MyButton());
	
		
		
	
		
		JMenuBar jMenuBar=new JMenuBar();
		
		JMenu barMenuFiles =new JMenu("Files");
		JMenu barMenuSettings=new JMenu("Settings");
		
		
	
		
		
		
		JMenuItem jMenuItemLoadLayout=new JMenuItem("Load Layout",KeyEvent.VK_L);
		JMenuItem jMenuItemSaveLayout=new JMenuItem("SaveLayout",KeyEvent.VK_S);
		JMenuItem jMenuItemExit=new JMenuItem("Exit",KeyEvent.VK_X);
		JMenuItem jMenuItemSettings=new JMenuItem("Settings",KeyEvent.VK_S);
		JMenuItem jMenuItemAddButton=new JMenuItem("Add Button",KeyEvent.VK_A);
		JMenuItem jMenuItemEditMode=new JMenuItem("Edit mode",KeyEvent.VK_E);
		
		jMenuItemLoadLayout.addActionListener(this);
		jMenuItemSaveLayout.addActionListener(this);
		jMenuItemExit.addActionListener(this);
		jMenuItemSettings.addActionListener(this);
		jMenuItemAddButton.addActionListener(this);
		jMenuItemEditMode.addActionListener(this);
		
		
		
		
		barMenuFiles.add(jMenuItemLoadLayout);
		barMenuFiles.add(jMenuItemSaveLayout);
		barMenuFiles.addSeparator();
		barMenuFiles.add(jMenuItemExit);
		
		barMenuSettings.add(jMenuItemEditMode);
		barMenuSettings.add(jMenuItemAddButton);
		barMenuSettings.add(jMenuItemSettings);
		
		jMenuBar.add(barMenuFiles);
		jMenuBar.add(barMenuSettings);
		
		frame.setJMenuBar(jMenuBar);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="Add Button"){
			
			//JOptionPane.showOptionDialog(null, new AddButton(), "Sirena",
		    //        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
		    //        new String[] { "Aceptar", "Cancelar" }, "Aceptar");
			AddButton addButton=new AddButton();
			
			int result = JOptionPane.showConfirmDialog(
			            frame, // use your JFrame here
			            addButton.getPanel(),
			            "Use a Panel",
			            JOptionPane.OK_CANCEL_OPTION,
			            JOptionPane.PLAIN_MESSAGE);
			System.out.println(result);
			
			if(result==0){
				cp.add(addButton.getButton());
				System.out.println(addButton.getButton().toString());
			}
			
			cp.revalidate();
			
		}
	}
	
	
	
	
	
}
