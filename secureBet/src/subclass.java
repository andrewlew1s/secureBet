import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.xml.bind.DatatypeConverter;


public class subclass extends JFrame{ 
	
	private JLabel game1;
	private JLabel game2;
	private JTextField bet1;
	private JTextField bet2;
	private JMenuBar menuBar;
	private JTextPane gamePane;
	private JTextPane gamePane2;
	private JMenu loginpage;
	private JMenuItem open;
	private JMenuItem close;
	private JMenuItem signup;
	private JTextField username;
	private JTextField emailaddr;
	private JTextField first_name;
	private JTextField last_name;
	private JPasswordField password;
	private JPasswordField password2;
	private JPanel panel;
	private JPanel panel2;
	private String s = "";
	private String s2 = "";
	private String s3 = "";
	private String s4 = "";
	private String s5 = "";
	private String s6 = "";
	private String usernameattempt;
	private String emailInfo;
	private String firstNameInfo;
	private String lastNameInfo;
	private String passwordInfo;
	private String passwordattempt;

	public subclass() throws Exception{
		
		super("Betting Simulator");//Title of program
		setBackground(Color.WHITE);
		setLayout(new FlowLayout());
		
		database db = new database();
		ArrayList<String> player = db.getData();
		//Array list to string array to string conversion. Why is life
		String array[] = new String[player.size()];              
		for(int j =0;j<player.size();j++){
		  array[j] = player.get(j);
		}
		String str = String.join(",", array);

		
		//display game data in these panes
		String[] initString =
	        { /* ...  fill array with initial data from API  ... */ };
		gamePane = new JTextPane();
		StyledDocument doc = gamePane.getStyledDocument();
		try {
			doc.insertString(0, str, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JScrollPane paneScrollPane = new JScrollPane(gamePane);
        paneScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        paneScrollPane.setPreferredSize(new Dimension(250, 155));
        paneScrollPane.setMinimumSize(new Dimension(10, 10));
		add(paneScrollPane, BorderLayout.CENTER);

        gamePane2 = new JTextPane();
		StyledDocument doc2 = gamePane2.getStyledDocument();
		try {
			doc2.insertString(0, "This is game 2 (we can fill this with API data to show scores, stats, etc.)", null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JScrollPane paneScrollPane2 = new JScrollPane(gamePane2);
        paneScrollPane2.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		paneScrollPane2.setPreferredSize(new Dimension(250, 155));
		paneScrollPane2.setMinimumSize(new Dimension(10, 10));
		add(paneScrollPane2, BorderLayout.CENTER);

        
        //JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,paneScrollPane,paneScrollPane2);

		
		menuBar = new JMenuBar();//Menu bar at top of app
		loginpage = new JMenu("User");
		menuBar.add(loginpage);
		open = new JMenuItem("Login");
		loginpage.add(open);
		loginpage.addSeparator();
		close = new JMenuItem("Logout");
		loginpage.add(close);
		loginpage.addSeparator();
		signup = new JMenuItem("Sign Up");
		loginpage.add(signup);
		setJMenuBar(menuBar);
		
		game1 = new JLabel("Enter a bet for Game 1: ");
		add(game1);
		
		bet1 = new JTextField("", 5);
		add(bet1);
		
		game2 = new JLabel("Enter a bet for Game 2: ");
		add(game2);
		
		bet2 = new JTextField("", 5);
		add(bet2);
		
		
		//Login pop-up
		panel = new JPanel(new BorderLayout(5, 5));
		JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    label.add(new JLabel("E-Mail", SwingConstants.RIGHT));
	    label.add(new JLabel("Password", SwingConstants.RIGHT));
	    panel.add(label, BorderLayout.WEST);
	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
	    username = new JTextField(20);
	    controls.add(username);
	    password = new JPasswordField(20);
	    controls.add(password);
	    panel.add(controls, BorderLayout.CENTER);
	    
	    //Sign Up pop-up
  		panel2 = new JPanel(new BorderLayout(5, 5));
  		JPanel label2 = new JPanel(new GridLayout(0, 1, 2, 2));
  	    label2.add(new JLabel("First Name", SwingConstants.RIGHT));
  	    label2.add(new JLabel("Last Name", SwingConstants.RIGHT));
	    label2.add(new JLabel("E-Mail", SwingConstants.RIGHT));
  	    label2.add(new JLabel("Password", SwingConstants.RIGHT));
  	    panel2.add(label2, BorderLayout.WEST);
  	    JPanel controls2 = new JPanel(new GridLayout(0, 1, 2, 2));
  	    first_name = new JTextField(20);
  	    controls2.add(first_name);
  	    last_name = new JTextField(20);
  	    controls2.add(last_name);
  	    emailaddr = new JTextField(20);
  	    controls2.add(emailaddr);
  	    password2 = new JPasswordField(20);
  	    controls2.add(password2);
  	    panel2.add(controls2, BorderLayout.CENTER);
	   				
		thehandler handler = new thehandler();//builds an action listener object - handles enter key or mouse click events
		bet1.addActionListener(handler);
		bet2.addActionListener(handler);
		open.addActionListener(handler);
		close.addActionListener(handler);
		signup.addActionListener(handler);
		
	}
	
	//this is a class that handles events
	//actionPerformed is built in - executed automatically whenever an event occurs
	//this event is enter, whenever enter occurs it will create empty string
	//test what we want to change the string to, then output
	private class thehandler implements ActionListener{
		
		public void actionPerformed(ActionEvent event){
			
			String string = "";
			database db = new database();

			if(event.getSource()==bet1){
				string=String.format("You bet: %s", event.getActionCommand()); //getActionCommand = get text from a location
				JOptionPane.showMessageDialog(null, string);
			}
			else if (event.getSource()==bet2){
				string=String.format("You bet: %s", event.getActionCommand()); 
				JOptionPane.showMessageDialog(null, string);
			}
			//Sign Up functional
			else if (event.getSource()==signup){
				JOptionPane.showConfirmDialog(
			            null, panel2, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
					//store login data here for now (it displays):
				 	firstNameInfo = s3.concat(first_name.getText());
				 	lastNameInfo = s4.concat(last_name.getText());
				 	emailInfo = s5.concat(emailaddr.getText());
				    passwordInfo = s6.concat(new String(password2.getPassword()));
					string=String.format("First Name: " + firstNameInfo + " Last Name: " + lastNameInfo + "Email: " + emailInfo + "Password: " + passwordInfo); 
					JOptionPane.showMessageDialog(null, string);
					try {
						db.createPlayer(firstNameInfo, lastNameInfo, emailInfo, passwordInfo);
					} catch (Exception e) {
						System.out.println(e);
					}
			}
			//login w/DB in progress CAN DO IT VIA TRACKSALTS!!
			else if (event.getSource()==open) {
				JOptionPane.showConfirmDialog(
			            null, panel, "login", JOptionPane.OK_CANCEL_OPTION);
					//store login data here for now (it displays):
				 	usernameattempt = s.concat(username.getText());
				    passwordattempt = s2.concat(new String(password.getPassword()));
					string=String.format("Username: " + usernameattempt + " Password: " + passwordattempt); 
					JOptionPane.showMessageDialog(null, string);
					String hashPassAttempt;
					String hex = null;
					try {
						boolean authenticated = false;
						if(db.emailExists(usernameattempt)) {
							
							Connection con = db.getConnection();
							PreparedStatement saltEmail = con.prepareStatement("SELECT salt FROM auth WHERE email= '" + usernameattempt + "'");
							System.out.println("prepared statement: " + saltEmail);
							byte[] hashSalt = new byte[20];

							ResultSet saltEmailResult = saltEmail.executeQuery();
							while(saltEmailResult.next()) {
								String s = saltEmailResult.getString("salt");
								System.out.println(db.generateHash(passwordattempt, "SHA-256", s));							

//								List<byte[]> array2 = new ArrayList<>(20);
//								for(int i =0;i<array2.size(); i++) {
//								    baos.write(hashSalt);
//								    hashSalt = baos.toByteArray();
//								}
//								s = array2.get(0);
//								byte[] saltBytes = s.getBytes("UTF-8");
//								for (int i = 0; i < array2.size() ; ++i)
//								{
//								hashSalt[i] = Byte.valueOf(s);
//								}
//								
//								hashPassAttempt = db.generateHash(passwordattempt, "SHA-256", saltBytes);
								
								System.out.println("this is a hash string of bytes!:" + s);

//								System.out.println("this is the password attempt!" + hashPassAttempt);
							
								
								

							}}
					}catch (Exception e) {
						System.out.println(e);
					}
			}
			
			else if (event.getSource()==close) {
				 JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?","Warning",JOptionPane.YES_NO_OPTION);
				// TODO incorporate actual logout w/DB later
			}
					
		}
	}
	}



			

////List<byte[]> userSalts= db.trackSalts;
//hashPassAttempt = db.generateHash(passwordattempt, "SHA-256", userSalt);
//System.out.println(hashPassAttempt);
//authenticated = db.authenticate(usernameattempt, hashPassAttempt);
//if(authenticated==true) {
//System.out.println("Correct user info");
//} else {
//System.out.println("Incorrect user info");
//}
////} catch (NoSuchAlgorithmException e) {
//////catch block
////e.printStackTrace();
////}



