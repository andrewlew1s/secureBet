import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


//MYSQL DB MANAGEMENT
public class database{
	
	public static List<byte[]> trackSalts = new ArrayList<byte[]>();
	byte[] indexByte;

		
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
		
	public static void createPlayer(String first, String last, String email, String password) throws Exception {
		final String var1 = first;
		final String var2 = last;
		final String var3 = email;
		final float defaultBalance = 0;
		byte[] saltID = createSalt();
		trackSalts.add(saltID);
		String saltStr= saltID.toString();

		System.out.println("in db " + saltID);
		System.out.println("string" + saltStr);
		String hashP = generateHash(password, "SHA-256", saltStr);
		System.out.println("hashP" + hashP);

		try {
			Connection con = getConnection();
			PreparedStatement playerdata = con.prepareStatement("INSERT INTO player (first_name, last_name, email, balance) VALUES ('"+var1+"','"+var2+"','"+var3+"',(0))");
			PreparedStatement authdata = con.prepareStatement("INSERT INTO auth (email, password, salt) VALUES ('"+var3+"','"+hashP+"','"+saltID+"')");

			playerdata.executeUpdate();
			authdata.executeUpdate();
		} catch(Exception e) {System.out.println(e);}
			System.out.println("Insert complete.");
	}
	
//	authentication doesn't work yet
//	public static boolean authenticate(String emailID,String hashedPassword) {
//		boolean validator = false;
//		try {
//			Connection con = getConnection();
//			PreparedStatement emailCheck = con.prepareStatement("SELECT email FROM auth");
//			PreparedStatement passwordCheck = con.prepareStatement("SELECT password FROM auth");
//
//			ResultSet emailResult = emailCheck.executeQuery();
//			String x = emailResult.getString("email");
//			System.out.println(x);
//
//			ResultSet passwordResult = passwordCheck.executeQuery();
//			String y = passwordResult.getString("password");
//			System.out.println(y);
//
//			if(emailID == emailResult.getString("email") && hashedPassword == passwordResult.getString("password")) {
//				System.out.println("User authenticated");
//				validator = true;
//			} else {
//				System.out.println("Not authenticated");//DO NOTHING
//			}
//		} catch(Exception e) {System.out.print(e);}	
//		if(validator==true) {
//			return true;
//		} else {
//			return false;
//		}
			
//	}
	
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
			
			}}catch(Exception e) {System.out.print(e);}

		return list;
	}
	
	//tested and works
	public static boolean emailExists(String email) throws Exception{
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT email FROM auth");
			boolean validator = false;
			
			ResultSet result = statement.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
			while(result.next()) {
				array.add(result.getString("email"));
			}
			for(int i=0; i<array.size(); i++) {
				String individualEmails = array.get(i);
				System.out.println(individualEmails);
				

				if(individualEmails.contains(email)){ //if email exists return salt
					System.out.println("true if");
					validator = true;
				
					
//
//					byte[] saltOfEmail = saltEmailResult.getBytes("salt");
//					System.out.println("salt of email: " + saltOfEmail);

				}else {
					validator = false;
				}
			}
			if(validator = true) {
				System.out.println("got here");
				return true;

			} else {
				return false;
			}
		}catch(Exception e) {System.out.print(e + "me");}
		return false;
		
	}
	
	//PASSWORD PROTECTION
	public static String generateHash(String data, String algorithm, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.reset();
		byte[] saltBytes = salt.getBytes("UTF-8");
		digest.update(saltBytes);
		byte[] hash = digest.digest(data.getBytes());
		String hex=DatatypeConverter.printHexBinary(hash);
		return hex;
	}
	
	public static byte[] createSalt() {
		byte[] bytes = new byte[20];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		System.out.println(bytes);
		
		return bytes;
	}
}