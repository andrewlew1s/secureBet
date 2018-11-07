import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

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
	private JTextField username;
	private JPasswordField password;
	private JPanel panel;
	private String s = "";
	private String s2 = "";
	private String usernameinformation;
	private String passwordinformation;
	
	public subclass() throws Exception{
		
		super("Betting Simulator");//Title of program
		setBackground(Color.WHITE);
		setLayout(new FlowLayout());
		
		database db = new database();
		ArrayList<String> player = db.getData();
		//Array list to string array to string conversion
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
	   				
		thehandler handler = new thehandler();//builds an action listener object - handles enter key or mouse click events
		bet1.addActionListener(handler);
		bet2.addActionListener(handler);
		open.addActionListener(handler);
		close.addActionListener(handler);
		
	}
	
	//this is a class that handles events
	//actionPerformed is built in - executed automatically whenever an event occurs
	//this event is enter, whenever enter occurs it will create empty string
	//test what we want to change the string to, then output
	private class thehandler implements ActionListener{
		
		public void actionPerformed(ActionEvent event){
			
			String string = "";
			//event default binds to enter key but can be changed
			if(event.getSource()==bet1){
				string=String.format("You bet: %s", event.getActionCommand()); //getActionCommand = get text from a location
				JOptionPane.showMessageDialog(null, string);
			}
			else if (event.getSource()==bet2){
				string=String.format("You bet: %s", event.getActionCommand()); 
				JOptionPane.showMessageDialog(null, string);
			}
			else if (event.getSource()==open){
				JOptionPane.showConfirmDialog(
			            null, panel, "login", JOptionPane.OK_CANCEL_OPTION);
					//store login data here for now (it displays):
				 	usernameinformation = s.concat(username.getText());
				    passwordinformation = s2.concat(new String(password.getPassword()));
					string=String.format("Username: " + usernameinformation + " Password: " + passwordinformation); 
					JOptionPane.showMessageDialog(null, string);
				// TODO incorporate actual login w/DB later
			}
			else if (event.getSource()==close)
				 JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?","Warning",JOptionPane.YES_NO_OPTION);
				// TODO incorporate actual logout w/DB later
		}
	}
}




