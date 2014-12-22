package common;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Message {
	public static void showMessage(Component parentComponent,  String msg){
		JOptionPane.showMessageDialog(parentComponent, msg);
	}
	public static void printConsole(String str){
		System.out.println(str);
	}
}
