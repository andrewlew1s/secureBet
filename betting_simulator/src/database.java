import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//MYSQL DB MANAGEMENT
public class database{
	public static Connection getConnection() throws Exception{
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/bet_sim";
			String username = "andrew";
			String password = "123456";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url,username,password);
			System.out.println("Connected");
			return conn;
			
		} catch(Exception e) {System.out.println(e);
		
		}
		return null;
	}
	
	public static ArrayList<String> getData() throws Exception{
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT * FROM player");
			
			ResultSet result = statement.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
			while(result.next()) {
				array.add(result.getString("first_name"));
				array.add(result.getString("last_name"));
				array.add(result.getString("email"));
				array.add(result.getString("balance"));
				array.add(result.getString("player_id"));
			}
			System.out.println(array);
			return array;
			
		}catch(Exception e) {System.out.println(e);}
		
		return null;
		
	}
	
}