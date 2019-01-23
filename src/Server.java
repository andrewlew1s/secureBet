import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class Server 
{	
    public static void main(String args[])
    {
       
        int port = 35791;
        String emailName = null;
        int emailID = 0;
        int emailIDFromAuth = 0;
        int dangerLogin = 0;
		int count = 0;
        String emailToCheck = null;

               
        //Specifies the keystore file which contains the certificate/public key and the private key
        System.out.println(System.getProperty("src/myKeyStore.jks"));
        System.setProperty("javax.net.ssl.keyStore","src/myKeyStore.jks");
        
        try {
			Database.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        //Specifies the password of the keystore file
    	System.out.println(System.getProperty("javax.net.ssl.keyStorePassword"));
        System.setProperty("javax.net.ssl.keyStorePassword","password");
        //This optional and it is just to show the dump of the details of the handshake process 
//        System.setProperty("javax.net.debug","all");               
        try
        {
        	//creates logger
            Log my_log = new Log("log.txt");
            BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
            StringBuilder sb = new StringBuilder();


            //SSLServerSocketFactory establishes the ssl context and and creates SSLServerSocket 
            SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            
            
            //Create usable SSLServerSocket using SSLServerSocketFactory 
            SSLServerSocket sslServerSocket = (SSLServerSocket)sslServerSocketfactory.createServerSocket(port);
            System.out.println("Server Started & Ready to accept Client Connection");
            
            //Wait for the SSL client to connect to this server
            SSLSocket sslSocket = (SSLSocket)sslServerSocket.accept();
            
            //Create InputStream to receive messages send by the client
            DataInputStream inputStream = new DataInputStream(sslSocket.getInputStream());
            //Create OutputStream to send message to client
            
            DataOutputStream outputStream = new DataOutputStream(sslSocket.getOutputStream());
            //Keep sending the client the message you receive unless it sends "stop"
            
            while(true)
            {            	
                String recievedMessage = inputStream.readUTF();
                System.out.println("Client Said : " + recievedMessage);
                
                //SIGNUP FROM CLIENT
                if(recievedMessage.contains("PLAYER:"))
                {
                	my_log.logger.info("Player creation attempted with this data: " + recievedMessage );
                    String[] playerLong = recievedMessage.split("/");
                    String firstNameForCreation = playerLong[1];
                    String lastNameForCreation = playerLong[2];
                    String emailForCreation = playerLong[3];
                    String passwordForCreation = playerLong[4];
                    boolean hasLetter = false;
                    boolean hasDigit = false;
                    
                    //PASSWORD STRENGTH CHECKING
                    if(passwordForCreation.length()>=8) {
                    	
                    	for (int i = 0; i < passwordForCreation.length(); i++)
                        {
                          char x = passwordForCreation.charAt(i);
                          System.out.println("char at i = "+ x);	

                          if (Character.isLetter(x)) {
                        	  hasLetter = true;
                          }                          
                          else if (Character.isDigit(x)) {
                              hasDigit = true;;
                              System.out.println("has digit? = "+ hasDigit);	
                            }
                          
                        }
                    	//CHECK IF EMEAIL ALREADY EXISTS - THEN IF PASSWORD GOOD MAKE A NEW PLAYER
	                    if(Database.emailExists(emailForCreation)!=true) {
	                    	if(hasDigit == true && hasLetter == true) {
		                    	 try {
		                             Database.createPlayer(firstNameForCreation,lastNameForCreation,emailForCreation,passwordForCreation);
		                             outputStream.writeUTF("Made a person well done");
						             my_log.logger.warning("Sign-Up success by: " + firstNameForCreation + " " + lastNameForCreation + "with this email: " + emailForCreation );
		                             }catch (Exception e) {
		         						System.out.println(e);}
		         					}		         								         					
		                	else {
		                    		outputStream.writeUTF("Password is 8 characters+ but either has no letter or no number - please try again");
		         					System.out.println("is 8+ but either has no letters or no numbers");	
		         					}
	                    }else {
	                    	outputStream.writeUTF("Email already exists - please try again");
	                    }
	                } else {
                		outputStream.writeUTF("Password is too short, should be 8+ characters long - please try again");
	                	System.out.println("password too short");
	                	//password too short
	                }
                }
               
               //UNUSED IF TO COLLECT ALL PLAYER DATA FROM DB
               if(recievedMessage.equals("GETDATA:")) {
                	
            	   Database.getConnection();
                	ArrayList<String> player = Database.getData();
                	System.out.println("got data");

        			//Array list to string array to string conversion
        			String array[] = new String[player.size()];              
        			for(int j =0;j<player.size();j++){
        			  array[j] = player.get(j);
        			}
        			String str = String.join(",", array);
        			System.out.println(str);
        			try {
        			outputStream.writeUTF("router1:"+str);
        			System.out.println("Wrote " + str + " to client");
        			}catch (Exception e) {
						System.out.println(e);}
					}
               
               if(recievedMessage.contains("NEWPASS:")) {
 	        	  String[] newPassLong = recievedMessage.split("/");
	              String newPassword = newPassLong[1];
	              byte[] saltID = Database.createSalt();
	      		  String saltStr= saltID.toString();
	              String passToSend = Database.generateHash(newPassword, "SHA-256", saltStr);
	              Database.updatePassword(passToSend, emailIDFromAuth, saltStr);
	              try {
	        			outputStream.writeUTF("Password Changed!");
	        			}catch (Exception e) {
							System.out.println(e);}
               }

               
               //LOGIN REQUEST FROM CLIENT
               if(recievedMessage.contains("LOGIN:"))
               {
               	  
	        	  String[] loginLong = recievedMessage.split("/");
	              emailToCheck = loginLong[1];
	              String passwordToCheck = loginLong[2];
	              boolean validator = false;	              
               	  
	              System.out.println("false login attempts this session: " + dangerLogin);
	              if(dangerLogin<20) {
	            	  if(Database.emailExists(emailToCheck)) {
	            		  Connection con = Database.getConnection();
	            		  PreparedStatement saltEmail = con.prepareStatement("SELECT salt FROM auth WHERE email= '" + emailToCheck + "'");
	            		  ResultSet saltEmailResult = saltEmail.executeQuery();
	            		  
	            		//get salt from row associated w/email and while that is a thing check that the hashed attempted password = hashed true password from DB
	            		  
						while(saltEmailResult.next()) {
							String s = saltEmailResult.getString("salt");
							PreparedStatement passOfEmail = con.prepareStatement("SELECT password FROM auth WHERE email= '" + emailToCheck + "'");
							ResultSet passOfEmailResult = passOfEmail.executeQuery();
							
							while(passOfEmailResult.next()) {
								String truePass = passOfEmailResult.getString("password");
								System.out.println("pass from db: " + truePass);
								String attemptPass = Database.generateHash(passwordToCheck, "SHA-256", s);
								if(truePass.equals(attemptPass)) {
									validator = true;
					               	my_log.logger.warning("login success with this email: " + emailToCheck );
									System.out.println("Password entered is true!");
									
									//VERIFIED SO GET THE DATA
									
									PreparedStatement nameOfEmail = con.prepareStatement("SELECT first_name FROM player WHERE email= '" + emailToCheck + "'");
									ResultSet nameOfEmailResult = nameOfEmail.executeQuery();
									while(nameOfEmailResult.next()) {
										emailName = nameOfEmailResult.getString("first_name");
										System.out.println("Got name from email: " + emailName);
									}
									PreparedStatement balOfEmail = con.prepareStatement("SELECT balance FROM player WHERE email= '" + emailToCheck + "'");
									ResultSet balOfEmailResult = balOfEmail.executeQuery();
									while(balOfEmailResult.next()) {
										String emailBalance = balOfEmailResult.getString("balance");
										System.out.println("Got balance from email: " + emailBalance);
									}
									PreparedStatement idOfEmail = con.prepareStatement("SELECT player_id FROM player WHERE email= '" + emailToCheck + "'");
									ResultSet idOfEmailResult = idOfEmail.executeQuery();
									while(idOfEmailResult.next()) {
										emailID = idOfEmailResult.getInt("player_id");
										System.out.println("Got id from email: " + emailID);
									}
									PreparedStatement idOfEmailFromAuth = con.prepareStatement("SELECT id FROM auth WHERE email= '" + emailToCheck + "'");
									ResultSet idOfEmailFromAuthResult = idOfEmailFromAuth.executeQuery();
									while(idOfEmailFromAuthResult.next()) {
										emailIDFromAuth = idOfEmailFromAuthResult.getInt("id");
										System.out.println("Got id from email for auth: " + emailIDFromAuth);
									}
									outputStream.writeUTF("Good");
	
							} 	if(validator!=true) {
				               	  	my_log.logger.severe("Login was attempted with this email: " + emailToCheck + " but had wrong password. Added to failed login count." );
									System.out.println("Password entered is false!");
									outputStream.writeUTF("Bad");
							}
							}
						}
	            	  }else {
	                   	  my_log.logger.info("login attempted with bad email: " + emailToCheck);
	            		  System.out.println("Incorrect email!");
	            		  outputStream.writeUTF("Incorrect email");
	            	  }
	              }else {
            		  outputStream.writeUTF("Logger has recorded too many login attempts, or an attempt to login to banned account. Start another session.");
                   	  my_log.logger.warning("Too many login attempts in one session!");

	              }
               }
               
               //ADD THE BALANCE (score)
               if(recievedMessage.contains("BALANCE:")) {
            	   	String[] balanceLong = recievedMessage.split("/");
 	              	String balanceToAdd = balanceLong[1];
 	              	int bal = Integer.parseInt(balanceToAdd);
 	              	Database.addBalance(bal, emailID);
            	   
					}
               
               //GET THE BALANCE
               if(recievedMessage.contains("DETAILS:")) {
               		my_log.logger.info("Balance request made by: " + emailName + " at emailID in player table = " + emailID);
            	   	System.out.println("emailID to look for balance: " + emailID);
            	   	int x = Database.getBalance(emailID);
            	   	System.out.print(x);
	              	outputStream.write(x);
           	   
					}
               
           		//check if there have been any SEVERE: logs i.e. bad passwords. 
               if(recievedMessage.contains("LOGIN:")) {
            	   
            	String line = "";
       			
       				try {
       					reader.mark(10000);
       					while((line=reader.readLine())!=null) {
                       		sb.append(line);

                            sb.append(System.getProperty("line.separator"));
                       		System.out.println("line = " + line);
                       		System.out.println("sb = " + sb);

                       		if(line.contains("SEVERE:") && line.contains(emailToCheck)) { //get all severes
                       			count++;
                           		System.out.println("count = " + count);
                           		
                       		}
                       		                      		
       				}
       				                   		  
       			}catch(Exception e) {System.out.println(e);}
       				try {

            			Connection con = Database.getConnection();
            			PreparedStatement updateLoginFail = con.prepareStatement("UPDATE auth SET loginfail= "+ count + " WHERE email= '" + emailToCheck + "'");
            			updateLoginFail.executeUpdate();
            			PreparedStatement loginfailcount = con.prepareStatement("SELECT loginfail FROM auth WHERE email= '" + emailToCheck + "'");
            			ResultSet loginfailcountResult = loginfailcount.executeQuery();
            			while(loginfailcountResult.next()) {
            				int x = loginfailcountResult.getInt("loginfail");
            				dangerLogin = x;
               			System.out.println("dangerLogin(false login count) is:  = " + dangerLogin);
               			count = 0;
               			reader.reset();

            			}
            		}catch(Exception e) {System.out.println(e);}
       				
   				}
               	            	                 
                else
                {
                	//else at end of running a command from the GUI
                	                	
                	System.out.println("Ran server"); //do nothing
                	
                }
               
           	}       
            
               }
        
        catch(Exception ex)
        {
            System.err.println("Error Happened in server: "+ex.toString());
        }
    }
}
