package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class LayoutManager {

	
	public static boolean saveLayout(DashBoard board) {

		JFileChooser c = new JFileChooser();
		int rVal = c.showSaveDialog(c);
		
		if (rVal == JFileChooser.APPROVE_OPTION) {

			try {
				FileOutputStream fos = new FileOutputStream(
				c.getCurrentDirectory().toString() + "/" + c.getSelectedFile().getName());
				System.out.println(c.getCurrentDirectory().toString());
				System.out.println(c.getSelectedFile().getName());
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(board);
				oos.flush();
				oos.close();
				fos.close();
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}

	public static DashBoard loadLayout() {

		JFileChooser openFile = new JFileChooser();

		int result = openFile.showOpenDialog(openFile);
		if (result == 0) {
			try {

				FileInputStream fis = new FileInputStream(openFile.getSelectedFile().getAbsolutePath());
				ObjectInputStream oin = new ObjectInputStream(fis);
				DashBoard board = (DashBoard) oin.readObject();
				oin.close();
				fis.close();
				//autoSaves(board);
				return board;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Can't load this file");
				return null;
			}
		}
		return null;
	}

	
	public static void saveLastState(DashBoard db){
		try{
			FileOutputStream fos=new FileOutputStream("C:/Users/"+System.getProperty("user.name")+"/temp");
			ObjectOutputStream oop = new ObjectOutputStream(fos);
			oop.writeObject(db);
			oop.flush();
			oop.close();
			fos.close();
		
		}catch(IOException exc){
			exc.printStackTrace();
			System.out.println(exc.getMessage());
			System.out.println("can't write saves");
		}
		
	}
	
	public static DashBoard getLastState(){
		
		try {
			FileInputStream fis = new FileInputStream("C:/Users/" + System.getProperty("user.name") + "/temp");
			ObjectInputStream ois = new ObjectInputStream(fis);
			//List<MyButton> buttons = (List<MyButton>) ois.readObject();
			DashBoard db=(DashBoard)ois.readObject();
			
			ois.close();
			//fis.close();
			
			return db; 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("can't load dash board 'getLastState'");
		}
		return null;
		
		
	}
}
