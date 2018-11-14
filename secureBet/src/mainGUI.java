import java.awt.Color;
import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



class mainGUI{
	public static void main(String[] args) throws Exception{
		
		subclass x = new subclass();
		x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		x.setSize(600,300);
		x.setBackground(Color.WHITE);
		x.setVisible(true);
//		database db = new database();
	}
	
}