package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Settings {

	public boolean save(DashBoard board) {

		JFileChooser c = new JFileChooser();
		// Demonstrate "Save" dialog:
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
				return true;
			} catch (Exception e) {
				System.out.println("cant save");
				return false;
			}
		}
		return false;
	}

	public List load() {

		JFileChooser openFile = new JFileChooser();

		int result = openFile.showOpenDialog(openFile);
		if (result == 0) {
			try {

				FileInputStream fis = new FileInputStream(openFile.getSelectedFile().getAbsolutePath());
				ObjectInputStream oin = new ObjectInputStream(fis);
				DashBoard board = (DashBoard) oin.readObject();
				//autoSaves(board);
				return board.buttonSet;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Can't load this file");
				return null;
			}
		}
		return null;
	}

	public void autoSaves(DashBoard db){
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
}
