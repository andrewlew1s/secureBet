import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


//MYSQL DB MANAGEMENT
public class Database{
	
	static boolean validator = false;
		
	public static Connection getConnection() throws Exception{
		try {
			System.out.println("connection beginning");
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/bet_sim" + "?verifyServerCertificate=FALSE" + "&useSSL=FALSE" + "&requireSSL=FALSE";
			String username = "andrew";
			String password = "123456";
			Class.forName(driver);
			
			System.out.println("before driver call");
			MysqlDataSource dataSource = new MysqlDataSource();
			
			dataSource.setUser(username);
			dataSource.setPassword(password);
			dataSource.setURL(url);

			Connection conn = dataSource.getConnection();
			System.out.println("Connected");
			return conn;
			
			
		} catch(Exception e) {System.out.println(e);
		
		}
		return null;
	}
	
	public static ArrayList<String> getData() throws Exception{
		try {
			System.out.println("about to connect");
			Connection con = getConnection();
			System.out.println("connection attempted");
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
			con.close();
			return array;
			
			
		}catch(Exception e) {System.out.println(e);}
		
		return null;
		
	}
	
	public static int getBalance(int id) throws Exception {		
		try {
			Connection con = getConnection();
			PreparedStatement balance = con.prepareStatement("SELECT balance FROM player WHERE player_id = " + id);
			ResultSet balanceResult = balance.executeQuery();
			while(balanceResult.next()) {
				int x = balanceResult.getInt("balance");
				return x;
			}
			
			
		}catch(Exception e) {System.out.println(e);}
		return 0;
	}
	
	public static void addBalance(int bal, int id) throws Exception {
		
		try {
			Connection con = getConnection();
			PreparedStatement balance = con.prepareStatement("UPDATE player SET balance= "+ bal + " WHERE player_id = " + id);
			balance.executeUpdate();
			
		}catch(Exception e) {System.out.println(e);}
	}
	
	public static void updatePassword(String new_password, int id, String salt) throws Exception {
		
		try {
			Connection con = getConnection();
			PreparedStatement updatePass = con.prepareStatement("UPDATE auth SET password= '"+ new_password + "' WHERE id= " + id);
			updatePass.executeUpdate();
			PreparedStatement updateSalt = con.prepareStatement("UPDATE auth SET salt= '"+ salt + "' WHERE id= " + id);
			updateSalt.executeUpdate();
		}catch(Exception e) {System.out.println(e);}
	}
	
		
	public static void createPlayer(String first, String last, String email, String password) throws Exception {
		final String var1 = first;
		final String var2 = last;
		final String var3 = email;
		byte[] saltID = createSalt();
		String saltStr= saltID.toString();

		System.out.println("in db " + saltID);
		System.out.println("string" + saltStr);
		String hashP = generateHash(password, "SHA-256", saltStr);
		System.out.println("first" + var1);
		System.out.println("last" + var2);
		System.out.println("email" + var3);
		System.out.println("hashP" + hashP);

		try {
			Connection con = getConnection();
			PreparedStatement playerdata = con.prepareStatement("INSERT INTO player (first_name, last_name, email, balance) VALUES ('"+var1+"','"+var2+"','"+var3+"',(0))");
			PreparedStatement authdata = con.prepareStatement("INSERT INTO auth (email, password, salt, loginfail) VALUES ('"+var3+"','"+hashP+"','"+saltStr+"',(0))");

			playerdata.executeUpdate();
			authdata.executeUpdate();
		} catch(Exception e) {System.out.println(e);}
			System.out.println("Insert complete.");
	}
	
	//Returns salt as string array - not that helpful
	public static List<Integer> getSaltId() {
		List<Integer> list = new ArrayList<Integer>();
		
		try {
			Connection con = getConnection();
			PreparedStatement saltIDCheck = con.prepareStatement("SELECT id FROM auth");
			PreparedStatement saltCheck = con.prepareStatement("SELECT salt FROM auth");
			ResultSet saltIDResult = saltIDCheck.executeQuery();
			ResultSet saltResult = saltCheck.executeQuery();
			while(saltIDResult.next()) {
				list.add(saltIDResult.getInt("id"));
				list.add(saltResult.getInt("salt"));
				System.out.println(list);
				con.close();
			
			}}catch(Exception e) {System.out.print(e);}
		
		
		return list;
	}
	
	//EMAIL EXISTS
	public static boolean emailExists(String email) throws Exception{
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT email FROM auth");
			validator = false;
			
			ResultSet result = statement.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
			while(result.next()) {
				array.add(result.getString("email"));
			}
			for(int i=0; i<array.size(); i++) {
				String individualEmails = array.get(i);
				System.out.println(individualEmails);
				

				if(individualEmails.equals(email)){ //if email exists 
					System.out.println("true if");
					validator = true;
				}else {
				}
			}
				if(validator == true) {
					System.out.println("got here");
					return true;
	
				} else {
					return false;
				}
			
		}catch(Exception e) {System.out.print(e + "me");}
		
		return false;
		
	}
		
	//PASSWORD HASHING
	public static String generateHash(String data, String algorithm, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.reset();
		byte[] saltBytes = salt.getBytes("UTF-8");
		digest.update(saltBytes);
		byte[] hash = digest.digest(data.getBytes());
		String hexOriginal=DatatypeConverter.printHexBinary(hash);
		String hex2=hexOriginal;
		for(int i=0;i<=249999;i++) {
			hex2 = generateMultipleHashNoSalt(hex2,algorithm);			
//			System.out.print("password hash at i is" +i+ " = " + hex2); 
		}
		return hex2; 
	}
	
	public static String generateMultipleHashNoSalt(String data, String algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.reset();
		byte[] hash = digest.digest(data.getBytes());
		String hex=DatatypeConverter.printHexBinary(hash);
//		System.out.println(hex);
		return hex;
	}
	
	public static byte[] createSalt() {
		byte[] bytes = new byte[20];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);		
		return bytes;
	}
}