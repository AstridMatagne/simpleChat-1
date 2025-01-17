// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;

import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String loginID, String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try{
    	if(message.startsWith("#")) {
    		handleCommand(message);
    	}
    	else {
    		sendToServer(message);
    	}
    }
    catch(IOException e){
    	clientUI.display("Could not send message to server.  Terminating client.");
    	quit();
    }
  }
  //we create a method that implements the different commands
  private void handleCommand (String cmd) { 
	  if(cmd.equals("#quit")) {
		  clientUI.display("client will quit");
		  quit();
	  }
	  else if(cmd.equals("#logoff")) {
		  try {
			  if(this.isConnected()) {
				  this.closeConnection();
			  }
			  else {
				  System.out.println("the client is already disconnected");
			  }
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("there is a problem with the disconnection");
		}
	  }
	  else if(cmd.equals("#login")) {
		  if(this.isConnected()) {
			  System.out.println("the client is already connected");
			  }
		  else {
			  try {
				  this.openConnection();
			  } catch (IOException e) {
				  // TODO Auto-generated catch block
				  System.out.println("there is a problem with the connection");
			  }
		  }
	  }
	  else if(cmd.equals("#sethost")) {
		  if(this.isConnected()) {
			  System.out.println("the client is still connected");
		  }
		  else {
			  super.setHost((this.getHost())); 
			  System.out.println("Port set to " + getHost());
		  }
	  }
	  else if(cmd.equals("#setport")) {
		  if(this.isConnected()) {
			  System.out.println("the client is still connected");
		  }
		  else {
			  super.setPort((this.getPort())); 
			  System.out.println("Port set to " + getPort());
		  }
	  			
	  }
	  else if(cmd.equals("#getport")) {
		  System.out.println("Current port is " + this.getPort());
		  
	  }
	  else if(cmd.equals("#gethost")) {
		  System.out.println("Current port is " + this.getHost());
	  }
	  
  }
  
  
  //methods connectionClosed() and connectionException()
  @Override
  public void connectionClosed() {
	  clientUI.display("connection closed");
  }
  
  public void connectionException(Exception exception) {
	  clientUI.display("the server has stopped");
	  System.exit(0);
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
