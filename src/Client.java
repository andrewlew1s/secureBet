import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.security.Security;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
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
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.xml.bind.DatatypeConverter;

//Echo server
public class Client
{	
		
	public static void main(String args[]) throws Exception
    {
		
        int serverPort = 35791;
        //The Server Address
        String serverName = "localhost";
        //This adds the trustStore file which contains the certificate & public of the server, and also error handling
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        System.setProperty("javax.net.ssl.trustStore","src/myTrustStore.jts");
        //This adds the password of the trustStore file
        System.setProperty("javax.net.ssl.trustStorePassword","password");
        //This is optional apparently - shows dump of the details of the handshake process 
//        System.setProperty("javax.net.debug","all");
	        try
	        {
	            //This establishes the SSL context and and creates SSLSocket 
	            SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
	            //Create usable SSLSocket using the SSLServerFactory 
	            SSLSocket sslSocket = (SSLSocket)sslsocketfactory.createSocket(serverName,serverPort);
	            //Creates an OutputStream that sends messages to the server
	            DataOutputStream outputStream = new DataOutputStream(sslSocket.getOutputStream());
	            //Creates an InputStream that reads messages sent by the server
	            DataInputStream inputStream = new DataInputStream(sslSocket.getInputStream());
	            //Print the first message send by the server after being connected
	//            System.out.println(inputStream.readUTF());
	            //Keep sending sending unless the it is "stop"
	            	
	            	class subclass2 extends JFrame{ 
	            		
	            		private JLabel game1;
	            		private JLabel game2;
	            		private JTextField bet1;
	            		private JTextField bet2;
	            		private JMenuBar menuBar;
	            		private JTextPane gamePane;
	            		private JTextPane gamePane2;
	            		private JMenu loginpage;
	            		private JMenu authPage;
	            		private JMenuItem open;
	            		private JMenuItem close;
	            		private JMenuItem signup;
	            		private JMenuItem mydetails;
	            		private JMenuItem resetpassword;
	            		private JTextField username;
	            		private JTextField emailaddr;
	            		private JTextField first_name;
	            		private JTextField last_name;
	            		private JPasswordField password;
	            		private JPasswordField password2;
	            		private JPasswordField new_password;
	            		private JPanel panel;
	            		private JPanel panel2;
	            		private JPanel panel3;
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
	            		private String passwordInfo = "";
	            		private String passwordattempt;
	            		public boolean isAuthenticated = false;

	            		public subclass2() throws Exception{
	            			
	            			super("Secure Game Simulator");//Title of program
	            			setBackground(Color.WHITE);
	            			setResizable(false);
	            		
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
	            			loginpage.addSeparator();
	            			resetpassword = new JMenuItem("Reset Password");
	            			loginpage.add(resetpassword);
	            			loginpage.addSeparator();
	            			mydetails = new JMenuItem("User Details");
	            			loginpage.add(mydetails);
	            			setJMenuBar(menuBar);	            			            			
	            			
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
	            	  	    
	            	  	    //Update Password pop-up
	            	  		panel3 = new JPanel(new BorderLayout(5, 5));
	            	  		JPanel label3 = new JPanel(new GridLayout(0, 1, 2, 2));
	            	  	    label3.add(new JLabel("New Password:", SwingConstants.RIGHT));
	            	  	    panel3.add(label3, BorderLayout.WEST);
	            	  	    JPanel controls3 = new JPanel(new GridLayout(0, 1, 2, 2));
	            	  	    new_password = new JPasswordField(20);
	            	  	    controls3.add(new_password);
	            	  	    panel3.add(controls3, BorderLayout.CENTER);
	            		   				
	            			thehandler handler = new thehandler();//builds an action listener object - handles enter key or mouse click events
//	            			bet1.addActionListener(handler);
//	            			bet2.addActionListener(handler);
	            			open.addActionListener(handler);
	            			close.addActionListener(handler);
	            			signup.addActionListener(handler);
	            			mydetails.addActionListener(handler);
	            			resetpassword.addActionListener(handler);	            				            				            			
	            		}
	            			            			            		
	            		//this is a class that handles events
	            		//actionPerformed is built in - executed automatically whenever an event occurs
	            		//this event is enter, whenever enter occurs it will create empty string
	            		//test what we want to change the string to, then output
	            		class thehandler implements ActionListener{
	            			
	            			public void actionPerformed(ActionEvent event){
	            				
	            				String string = "";

	            				if(event.getSource()==mydetails){
	            					
	            					if(isAuthenticated == true) {
	            						try {
	            							outputStream.writeUTF("DETAILS:");
	            							int localBalance = inputStream.read();
	            							System.out.println("if it got the balance from server: " + localBalance);
	            							string = Integer.toString(localBalance);
			            					JOptionPane.showMessageDialog(null, "You scored " + string + " points in your last game!");

	            						}catch (Exception e) {
	            							System.out.println(e);
	            						}
	            					}else {
		            					string="You aren't logged in!";
		            					JOptionPane.showMessageDialog(null, string);
	            					}
	            				}
	            				else if (event.getSource()==resetpassword){
	            					if(isAuthenticated == true) {	            					
		            					string=String.format("You are about to reset your password: %s", event.getActionCommand()); //getActionCommand = get text from a location
		            					JOptionPane.showConfirmDialog(null, panel3, "Reset Password", JOptionPane.DEFAULT_OPTION);
	            					    passwordInfo = s6.concat(new String(new_password.getPassword()));
	            						string=String.format("NEWPASS:"+"/"+passwordInfo); 
	            						JOptionPane.showMessageDialog(null, string);
	            						try { //stores info to DB and creates a "player"
	            							outputStream.writeUTF(string);
	            							String s1 = inputStream.readUTF();
	            							JOptionPane.showMessageDialog(null, s1, "Database Reaction", getDefaultCloseOperation());
	            						} catch (Exception e) {
	            							System.out.println(e);
	            						}
	            					}else {
		            					string="You aren't logged in!";
		            					JOptionPane.showMessageDialog(null, string);
	            					}

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
	            						string=String.format("PLAYER:"+"/"+firstNameInfo + "/" + lastNameInfo + "/" + emailInfo + "/" + passwordInfo); 
	            						JOptionPane.showMessageDialog(null, string);
	            						try { //stores info to DB and creates a "player"
	            							outputStream.writeUTF(string);
	            							String s1 = inputStream.readUTF();
	            							JOptionPane.showMessageDialog(null, s1, "Database Reaction", getDefaultCloseOperation());
//	            							System.out.println(inputStream.readUTF());
//	            							db.createPlayer(firstNameInfo, lastNameInfo, emailInfo, passwordInfo);
	            						} catch (Exception e) {
	            							System.out.println(e);
	            						}
	            				}
	            				//login w/mySQL DB passwords are SHA-256 HASHED W/SALT
	            				else if (event.getSource()==open) {
	            					JOptionPane.showConfirmDialog(
	            				            null, panel, "login", JOptionPane.DEFAULT_OPTION);
	            						//store login data here for now (it displays):
	            					 	usernameattempt = s.concat(username.getText());
	            					    passwordattempt = s2.concat(new String(password.getPassword()));
	            						string=String.format("LOGIN:" + "/" + usernameattempt + "/" + passwordattempt); 
	            						JOptionPane.showMessageDialog(null, string);
	            						String hashPassAttempt;

	            						try { 
	            							outputStream.writeUTF(string);
	            							String s1 = inputStream.readUTF();
	            							System.out.println(s1);
	            							if( s1.contains("Good")){
	            								System.out.println("got to password read in client");
	            								JOptionPane.showMessageDialog(null, "Login succesful", "Success", getDefaultCloseOperation());
	            								isAuthenticated = true;
	            								System.out.println("does auth change? was false now is: " + isAuthenticated);
	            							} else if(s1.contains("Bad")) {	            								
	            								JOptionPane.showMessageDialog(null, "Login fail due to bad password", "Error message", getDefaultCloseOperation());
	            								isAuthenticated = false;
	            							} else if(s1.contains("Incorrect email")) {
	            						
	            								JOptionPane.showMessageDialog(null, "Login fail due to bad email", "Error message", getDefaultCloseOperation());
	            							} else if(s1.contains("Too many login attempts")) {
	            								JOptionPane.showMessageDialog(null, "Too many login attempts! You are banned from logging in!", "Ban message", getDefaultCloseOperation());

	            							} else if(s1.contains("Logger has recorded")) {
	            								JOptionPane.showMessageDialog(null, "Logger has recorded too many login attempts, or an attempt to login to banned account. Start another session.", "Error message", getDefaultCloseOperation());

	            							}
	            					
	            						}catch (Exception e) {
	            							System.out.println("login error: " + e);}
	            						}			
	            				else if (event.getSource()==close) {
	            					 JOptionPane.showConfirmDialog(null, "You are logging out!","Warning", JOptionPane.DEFAULT_OPTION);
	            					 isAuthenticated = false;
	            					// TODO incorporate actual logout w/DB later
	            				}							
	            			}
	            		}
	            		
	            		
	            	}
	            	
	            	
	            	
	            	class GameBoard extends JPanel implements KeyListener{
	            		
	            		public int balance = 1;
	            		private final int HEIGHT = 300;
	            		private final int WIDTH = 400;
	            		private final int TARGETX = 100;
	            		private final int TARGETY = 200;
	            		private int NEWX = 150;
	            		private int NEWY = 250;
	            		private final int PLAYERWIDTH = 30;
	            		private final int PLAYERHEIGHT = 10;
	            		private final int MS = 12;
	            		private int dx = WIDTH/2;
	            		private int dy = HEIGHT;
	            		private boolean enemyAlive = false;
	            		private boolean initialEnemyState = true;
	            		private boolean balanceBlock = false;
	            		private Ellipse2D player = new Ellipse2D.Double(dx, dy, PLAYERWIDTH, PLAYERHEIGHT);
	            		private Rectangle2D laser = new Rectangle2D.Double(-5, -5, 0, 0);
	            		
	            		
	            		private JLabel balanceTag;
	            		private String balanceString = "0";
	            		
	            		
	            		public GameBoard(){
	            			
	            			this.addKeyListener(this);
	            			this.setBackground(Color.white);
	            			this.setFocusable(true);
	            			balanceTag = new JLabel(balanceString);
	            			add(balanceTag);			            		            				            		
	            		}

						public void moveEllipse(KeyEvent evt) {
							switch (evt.getKeyCode()) {
							case KeyEvent.VK_LEFT:
								if(dx>0) {
									dx -= MS;
								}
								player.setFrame(dx, dy, PLAYERWIDTH, PLAYERHEIGHT);
								break;							
							case KeyEvent.VK_RIGHT:
								if(dx < this.WIDTH - this.PLAYERWIDTH - this.MS) {
									dx += MS;
								}
								player.setFrame(dx, dy, PLAYERWIDTH, PLAYERHEIGHT);
								break;
							case KeyEvent.VK_UP:
								if(dy > 0) {
									dy -= MS;
								}
								player.setFrame(dx, dy, PLAYERWIDTH, PLAYERHEIGHT);
								break;
							case KeyEvent.VK_DOWN:
								if(dy < HEIGHT) {
									dy += MS;
								}
								player.setFrame(dx, dy, PLAYERWIDTH, PLAYERHEIGHT);
								break;
							case KeyEvent.VK_R: //restart game
								this.enemyAlive = true;
								this.initialEnemyState = false;
								NEWX = randomX();
								NEWY = randomY();
								try {
									String balanceString = Integer.toString(balance);
									outputStream.writeUTF("BALANCE:" + "/" + balanceString);
								} catch (IOException e) {
									e.printStackTrace();
								}
								balance = 0;
								System.out.println(NEWX+" x/y "+ NEWY);
								break;
							case KeyEvent.VK_F://fire on enemy
								fire();
									if(!enemyAlive) {
										System.out.println("got to enemy not alive after fire");

										this.initialEnemyState = false;
										this.enemyAlive = true;
										NEWX = randomX();
										NEWY = randomY();
										System.out.println(NEWX+" x/y "+ NEWY);
									}
								repaint();
								break;
							case KeyEvent.VK_S://scan for new enemies
									if(!enemyAlive) {
										System.out.println("scanning for enemy");

										this.initialEnemyState = false;
										this.enemyAlive = true;
										NEWX = randomX();
										NEWY = randomY();
										System.out.println(NEWX+" x/y "+ NEWY);
									}
								repaint();
								break;
							// TODO Auto-generated method stub
							}
						}

						@Override
						public void paintComponent(Graphics graphics) {
							isDead();
							super.paintComponent(graphics);
							Graphics2D gr = (Graphics2D) graphics;
							gr.setColor(Color.blue);
							gr.fill(player);
							gr.draw(player);		
							gr.draw(laser);
							
							if (initialEnemyState) {									
						            drawTank(graphics, TARGETX, TARGETY);
							}else {						            
									//								
						            }
							if(enemyAlive) {
									drawTank(graphics, NEWX, NEWY);
							}
							
							}													
						
						private int randomX() {
							Random r = new Random();
							int low = 10;
							int high = 325;
							int result = r.nextInt(high-low) + low;
							return result;
						}
						
						private int randomY() {
							Random r = new Random();
							int low = 10;
							int high = 300;
							int result = r.nextInt(high-low) + low;
							return result;
						}

						private void drawTank(Graphics g, int x, int y) {
							g.setColor(Color.red);
							g.fillRect(x, y, PLAYERWIDTH, PLAYERHEIGHT);
					        g.draw3DRect(x, y, PLAYERWIDTH, PLAYERHEIGHT, true);
						}

						private void isDead() {
							
							if(overlaps(NEWX, NEWY, PLAYERWIDTH, PLAYERHEIGHT, laser)) {
								enemyAlive = false;
								balanceBlock = false;
								//Prevents balance increasing after laser hits the first time
								if(!balanceBlock) {
									balance++;
			            			
								}
								balanceBlock = true;
								
								System.out.println("balance is: " + balance);
							// TODO Auto-generated method stub
							}else if(overlaps(TARGETX, TARGETY, PLAYERWIDTH, PLAYERHEIGHT, laser)) {
								enemyAlive = false;
								initialEnemyState = false;								
								System.out.println(balance);
							} 
						}
						
						public void fire() {
					        laser.setRect(dx + PLAYERWIDTH/2, 0, 1, dy);
					        
					    	}

						private boolean overlaps(int x, int y, int width, int height, Rectangle2D r) {
							 return x < r.getX() + r.getWidth() && x + width > r.getX()
						                && y < r.getY() + r.getHeight() && y + height > r.getY();
						    
						}
					
						@Override
						public void keyReleased(KeyEvent e) {
							 laser.setRect(-50, -50, 0, 0);  //hide it
							 repaint();
						    }

						@Override
						public void keyTyped(KeyEvent e) {
							System.out.println("You typed: " + e.getKeyChar()) ;
							balanceString = Integer.toString(balance);
	            			balanceTag.setText(balanceString);
						}

						@Override
						public void keyPressed(KeyEvent e) {
							moveEllipse(e);
							repaint();
							
						}
												
	            	}
	            		            			            
		            {
		            	subclass2 x = new subclass2();
		            	x.setLayout(new BorderLayout());
		            	final JPanel GamePanel = new GameBoard();
		            	x.add(GamePanel);
		            	x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        		x.setBounds(10,10,400,400);
		        		x.setBackground(Color.WHITE);
		        		x.setVisible(true);          			
		        		
            					        				        				        		
		            }       
	        }
	        catch(Exception ex)
	        {
	            System.err.println("Error Happened : "+ex.toString());
	        }
    }
}