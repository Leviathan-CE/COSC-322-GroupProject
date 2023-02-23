
package ubc.cosc322;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import GameState.GameBoardState;
import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;
import ygraph.ai.smartfox.games.amazons.HumanPlayer;

/**
 * An example illustrating how to implement a GamePlayer
 * 
 * @author Yong Gao (yong.gao@ubc.ca) Jan 5, 2021
 *
 */
/*
 * COSC322Test is both a main and self inference class which extends player
 * functionality by creating and holding a instance of itself along with server
 * and game data.
 */
public class COSC322Test extends GamePlayer {

	private GameClient gameClient = null;
	private BaseGameGUI gamegui = null;

	private String userName = null;
	private String passwd = null;

	private GameBoardState[] chessBoard = new GameBoardState[2];
	private int turn = 1; //even is black odd is white turn and queen number

	/**
	 * Any name and password
	 * 
	 * @param userName
	 * @param passwd
	 */
	// CONSTRUCTOR
	// Initializes GUI components
	public COSC322Test(String userName, String passwd) {
		this.userName = userName;
		this.passwd = passwd;

		// To make a GUI-based player, create an instance of BaseGameGUI
		// and implement the method getGameGUI() accordingly
		this.gamegui = new BaseGameGUI(this);
	}

	/**
	 * The main method	 * 
	 * @param args for name and passwd (current, any string would work)
	 */
	public static void main(String[] args) {
		//COSC322Test player = new COSC322Test(args[0], args[1]);
		 HumanPlayer player = new HumanPlayer();
		if (player.getGameGUI() == null) {
			player.Go();

		} else {
			BaseGameGUI.sys_setup();
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					player.Go();
				}
			});
		}
	}
	@Override
	public BaseGameGUI getGameGUI() {return this.gamegui;}
	@Override
	public void connect() {	gameClient = new GameClient(userName, passwd, this);	}
	@Override
	public String userName() {return userName;}
	@Override
	public GameClient getGameClient() {	return this.gameClient;	}
	

	/*
	 * EFFECTS: is called by the server upon a successful connection/login then
	 * joins room 4 if able
	 */
	@Override
	public void onLogin() {
		System.out.println(
				"Congratualations!!! " + "I am called because the server indicated that the login is successfully");
		System.out.println("The next step is to find a room and join it: "
				+ "the gameClient instance created in my constructor knows how!");
		if (gamegui != null) {
			gamegui.setRoomInformation(gameClient.getRoomList());
		}

	}
	/**
	 * EFFECTS: message handler works only for when AI model using a instance of COSC322Test
	 * @param msgDetails holds extractable and mutable information about the servers state.
	 * @param messageType determines who's turn it is GAME_STATE_BAORD is initail 
	 * setup while GAME_ACTION_MOVE is the a game player action input move.
	 * Their are other type which are yet to be known.
	 * @return  returns true when a action is sent to server or successfully received from server
	 * false otherwise.
	 */
	@Override
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
		// This method will be called by the GameClient when it receives a game-related
		// message
		// from the server.

		// For a detailed description of the message types and format,
		// see the method GamePlayer.handleGameMessage() in the game-client-api
		// document.
		System.out.println("!!!!!!!!!!!");
		if (gamegui != null) {

			switch (messageType) {
			case GameMessage.GAME_STATE_BOARD:
				System.out.println("ENEMY MOVE GET THINGY");
				this.getGameGUI().setGameState((ArrayList<Integer>) (msgDetails.get(AmazonsGameMessage.GAME_STATE)));
				ArrayList<Integer> GottenGameState = (ArrayList<Integer>) (msgDetails.get(AmazonsGameMessage.GAME_STATE));
				chessBoard[0] = new GameBoardState(GottenGameState);
				System.out.println(chessBoard.toString());
				GameBoardState.countQueens();

				break;
			/*
			 * when a move occurs update game state and update local board to match
			 */
			case GameMessage.GAME_ACTION_MOVE:
				this.gamegui.updateGameState(msgDetails);
				System.out.println("MY MOVE TURRRNRNRRNRNRNNRNR");
				
				//fetch the newest move and store their values in x,y lists
				ArrayList<Integer> queenPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
				ArrayList<Integer> newQueenPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
				ArrayList<Integer> arrowPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
				//update local game state to match the new state
				//keep in mind the position 1a is the top left corner for console representation
				chessBoard[0].setPosValue(0, queenPos.get(0), queenPos.get(1));
				chessBoard[0].setPosValue(turn % 2 + 1, newQueenPos.get(0), newQueenPos.get(1));
				chessBoard[0].setPosValue(3, arrowPos.get(0), arrowPos.get(1));
				//TODO: detect illegal move from action factory
				//<code> start detect here
				
				boolean isValid = getIfMoveIsValid(queenPos.get(1), queenPos.get(0), newQueenPos.get(1), newQueenPos.get(0), arrowPos.get(1), arrowPos.get(0));
				if(!isValid) {System.out.println("it is invalid move");}
				
				//TODO: from action factory calculate move and send it to server
				//<code> start for action
				chessBoard[0].print();
				GameBoardState.countQueens();
				break;
			default:
				assert (false);
				break;
			}
		}

		return true;
	}
	
	public boolean getIfMoveIsValid (int qx1, int qy1, int qx2, int qy2, int ax, int ay) {
		if(ifMoveIsValid(qx1, qy1, qx2, qy2)) {
			if(ifMoveIsValid(qx2, qy2, ax, ay)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean ifMoveIsValid (int qx1, int qy1, int qx2, int qy2) {
		boolean isValid = false;
		int[][] board = chessBoard[1].copyCurrentBoard;
		if ((qy1 == qy2) && (qx1 == qx2)) {
			return isValid;
		}
		
		if( qy1 == qy2) {
			int start = qx1 < qx2? qx1: qx2;
			int end = qx1 < qx2? qx2: qx1;
			for(int i = start + 1; i <= end; i++) {
				if(board[i][qy1] !=0 ) return isValid;
			}
			isValid = true;
			return isValid;
		}
		
		if (qx1 == qx2) {
			int start = qy1 < qy2? qy1: qy2;
			int end = qy1 < qy2? qy2: qy1;
			for(int i = start + 1; i <= end; i++) {
				if(board[qx1][i] !=0 ) return isValid;
			}
			isValid = true;
			return isValid;
		}
		
		if(Math.abs(qx2 - qx1) == Math.abs(qy2 - qy1)) {
			if((qx1 < qx2) && (qy1 < qy2)) {
				for(int i = qx1 + 1, j = qy1 + 1; i <= qx2 && j <=qy2; i++, j++) {
					if(board[i][j] !=0 ) return isValid;
				}
			}
			else if ((qx1 < qx2) && (qy1 > qy2)) {
				for(int i = qx1 + 1, j = qy1 - 1; i <= qx2 && j <=qy2; i++, j--) {
					if(board[i][j] !=0 ) return isValid;
				}
				
			}
			else if ((qx1 > qx2) && (qy1 < qy2)) {
				for(int i = qx2 + 1, j = qy2 + 1; i <= qx1 && j <=qy1; i++, j++) {
					if(board[i][j] !=0 ) return isValid;
				}
				
			}
			else if ((qx1 > qx2) && (qy1 > qy2)) {
				for(int i = qx2 + 1, j = qy2 - 1; i <= qx1 && j <=qy1; i++, j--) {
					if(board[i][j] !=0 ) return isValid;
				}
				
			}
			isValid = true;
			return isValid;
		}
		
		return isValid;
	}
	

	/*
	 * EFFECTS: GUI Components using awt packages for displaying content to user
	 * also is only entry point to modify gui components it contains. MODIFIES:
	 * this.gamegui to display information to screen RETRUNS: a reference to itself
	 * as GUI component
	 */
	

}// end of class
