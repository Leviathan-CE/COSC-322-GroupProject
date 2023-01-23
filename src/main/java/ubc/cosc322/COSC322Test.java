
package ubc.cosc322;

import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GamePlayer;
//comment


/**
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
/*
 * COSC322Test is both a main and self inference class which extends player functionality 
 * by creating and holding a instance of itself along with server and game data.
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	
    private String userName = null;
    private String passwd = null;
 
    
    /**
     * Any name and passwd 
     * @param userName
      * @param passwd
     */
    //CONSTRUCTOR
    //Initializes GUI components
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	
    	//To make a GUI-based player, create an instance of BaseGameGUI
    	//and implement the method getGameGUI() accordingly
    	this.gamegui = new BaseGameGUI(this);
    }
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test(args[0], args[1]);
    	
    	if(player.getGameGUI() == null) {
    		player.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	player.Go();
                }
            });
    	}
    }
	
   
 

    /*
     * EFFECTS: is called by the server upon a successful connection/login.
     */
    @Override
    public void onLogin() {
    	System.out.println("Congratualations!!! "
    			+ "I am called because the server indicated that the login is successfully");
    	System.out.println("The next step is to find a room and join it: "
    			+ "the gameClient instance created in my constructor knows how!"); 
    	List<Room> rooms = this.gameClient.getRoomList();
    	for(Room room : rooms) {
    		System.out.println(room.getName());
    	}
    	this.gameClient.joinRoom(rooms.get(4).getName());
    }
    
    
    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	//This method will be called by the GameClient when it receives a game-related message
    	//from the server.
	
    	//For a detailed description of the message types and format, 
    	//see the method GamePlayer.handleGameMessage() in the game-client-api document. 
    	    	System.out.println(messageType);
    	    	System.out.println(msgDetails);
    	return true;   	
    }
    
    
    @Override
    public String userName() {
    	return userName;
    }

	@Override
	public GameClient getGameClient() {
		// TODO Auto-generated method stub
		return this.gameClient;
	}
	/*
	 * EFFECTS: GUI Components using awt packages for displaying content to user
	 * 			also is only entry point to modify gui components it contains.
	 * MODIFIES: this.gamegui to display information to screen
	 * RETRUNS: a reference to itself as GUI component
	 */
	@Override
	public BaseGameGUI getGameGUI() {
		this.gamegui.setSize(new Dimension(900,600));
		this.gamegui.setVisible(true);
		// TODO Auto-generated method stub
		return  this.gamegui;
	}

	
	@Override
	public void connect() {
		// TODO Auto-generated method stub
    	gameClient = new GameClient(userName, passwd, this);			
	}

 
}//end of class