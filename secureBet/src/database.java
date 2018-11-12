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
	
	public static void createPlayer(String first, String last, String email) throws Exception {
		final String var1 = first;
		final String var2 = last;
		final String var3 = email;
		final float defaultBalance = 0;
		try {
			Connection con = getConnection();
			PreparedStatement playerdata = con.prepareStatement("INSERT INTO player (first_name, last_name, email, balance) VALUES ('"+var1+"','"+var2+"','"+var3+"',(0))");
			
			playerdata.executeUpdate();
		} catch(Exception e) {System.out.println(e);}
		finally {
			System.out.println("Insert complete.");
		}
	}
	
}