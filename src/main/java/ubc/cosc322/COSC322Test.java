
package ubc.cosc322;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import GameState.GameBoardState;
import GameState.Timer;

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
	private int turn = 1; // even is black odd is white turn and queen number
	private int ourColor = 0;
	private final static String KEY = "BOB";

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
	 * The main method
	 * 
	 * @param args for name and passwd (current, any string would work)
	 */
	public static void main(String[] args) {
		COSC322Test player = new COSC322Test(args[0], args[1]);
		// HumanPlayer player = new HumanPlayer();
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

	/*
	 * EFFECTS: GUI Components using awt packages for displaying content to user
	 * also is only entry point to modify gui components it contains. MODIFIES:
	 * this.gamegui to display information to screen RETRUNS: a reference to itself
	 * as GUI component
	 */
	@Override
	public BaseGameGUI getGameGUI() {
		return this.gamegui;
	}

	@Override
	public void connect() {
		gameClient = new GameClient(userName, passwd, this);
	}

	@Override
	public String userName() {
		return userName;
	}

	@Override
	public GameClient getGameClient() {
		return this.gameClient;
	}

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
	 * EFFECTS: message handler works only for when AI model using a instance of
	 * COSC322Test
	 * 
	 * @param msgDetails  holds extractable and mutable information about the
	 *                    servers state.
	 * @param messageType determines who's turn it is GAME_STATE_BAORD is initail
	 *                    setup while GAME_ACTION_MOVE is the a game player action
	 *                    input move. Their are other type which are yet to be
	 *                    known.
	 * @return returns true when a action is sent to server or successfully received
	 *         from server false otherwise.
	 * 
	 * @note This method will be called by the GameClient when it receives a
	 *       game-related message from the server.
	 * 
	 *       For a detailed description of the message types and format, see the
	 *       method GamePlayer.handleGameMessage() in the game-client-api document.
	 */
	@Override
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {

		System.out.println("!!!!!!!!!!!");
		if (gamegui != null) {

			switch (messageType) {

			case GameMessage.GAME_STATE_BOARD:
				System.out.println("ENEMY MOVE GET THINGY");
				this.getGameGUI().setGameState((ArrayList<Integer>) (msgDetails.get(AmazonsGameMessage.GAME_STATE)));
				ArrayList<Integer> GottenGameState = (ArrayList<Integer>) (msgDetails
						.get(AmazonsGameMessage.GAME_STATE));
				// set initial local state of GameBoard
				chessBoard[0] = new GameBoardState(GottenGameState);
				chessBoard[0].updateQueenPoses();
				chessBoard[0].printQPoses();

				System.out.println(chessBoard[0].toString());
				System.out.println("h1 = " + chessBoard[0].geth1());
				break;

			case GameMessage.GAME_ACTION_START:
				Timer.start();
				System.out.println("GAME START");
				String blackUserName = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
				System.out.println(blackUserName);
				if (blackUserName.equalsIgnoreCase(KEY))
					ourColor = 2;
				else {
					ourColor = 1;
				}

				if (ourColor == 2) {
					System.out.println("we start make move");
					// make our move
					int[] targetQueenToMove = new int[] { chessBoard[0].getQueenPosition2().get(0)[0],
							chessBoard[0].getQueenPosition2().get(0)[1] };
					ArrayList<ArrayList<Integer>> SenderOBJ = chessBoard[0].MoveQueen(2, targetQueenToMove,
							new int[] { 1, 1 }, new int[] { 1, 2 });
					
					boolean isValid2 = chessBoard[0].getIfMoveIsValid(SenderOBJ.get(0).get(0), SenderOBJ.get(0).get(1), SenderOBJ.get(1).get(0), SenderOBJ.get(1).get(1), SenderOBJ.get(2).get(0), SenderOBJ.get(2).get(1));
					if(!isValid2) {System.out.println("it is invalid move");}
					// send move to serve/opponent
					System.out.println("send move to server");
					this.gameClient.sendMoveMessage(SenderOBJ.get(0), SenderOBJ.get(1), SenderOBJ.get(2));
					this.gamegui.updateGameState(SenderOBJ.get(0), SenderOBJ.get(1), SenderOBJ.get(2));

				}
				System.out.println(Timer.currentTime());
				break;

			/*
			 * when a move occurs update game state and update local board to match
			 */
			case GameMessage.GAME_ACTION_MOVE:
				this.gamegui.updateGameState(msgDetails);
				System.out.println("MY MOVE TURRRNRNRRNRNRNNRNR");
				System.out.println(AmazonsGameMessage.GAME_STATE);
				System.out.println("turn: " + turn);

				// if(turn != 1) {
				// fetch the newest move from Opponent and store their values in x,y lists
				ArrayList<Integer> queenPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
				ArrayList<Integer> newQueenPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
				ArrayList<Integer> arrowPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
				

				//is the opponent move a valid move?
				boolean isValid = chessBoard[0].getIfMoveIsValid(queenPos.get(1), queenPos.get(0), newQueenPos.get(1), newQueenPos.get(0), arrowPos.get(1), arrowPos.get(0));
				if(!isValid) {System.out.println("it is invalid move");}
				
				//TODO: from action factory calculate move and send it to server
				//<code> start for action
				chessBoard[0].print();
				chessBoard[0].countQueens();
				
				// update local game state to match the new state
				// mutate data to readable
				int[] QnPos = new int[] { queenPos.get(0), queenPos.get(1) };
				int[] newQnPos = new int[] { newQueenPos.get(0), newQueenPos.get(1) };
				int[] arrw = new int[] { arrowPos.get(0), arrowPos.get(1) };
				// move queen that opponent moved
				chessBoard[0].MoveQueen(chessBoard[0].getCurBoard()[QnPos[0]][QnPos[1]], QnPos, newQnPos, arrw);
				chessBoard[0].countQueens();
				

				// copy board state
				chessBoard[1] = new GameBoardState(chessBoard[0].getCurBoard());
				chessBoard[1].updateQueenPoses();
				chessBoard[1].printQPoses();

				// TODO: calculate what our best move is and check if it is legal

				// make our move
				int[] targetQueenToMove;
				ArrayList<ArrayList<Integer>> SenderOBJ;
				if (ourColor == 2) {
					targetQueenToMove = new int[] { chessBoard[1].getQueenPosition2().get(0)[0],
							chessBoard[1].getQueenPosition2().get(0)[1] };
					SenderOBJ = chessBoard[1].MoveQueen(2, targetQueenToMove,
							new int[] { 1, 1 }, new int[] { 1, 2 });

				}
				else {
					targetQueenToMove = new int[] { chessBoard[1].getQueenPosition1().get(1)[0],
							chessBoard[1].getQueenPosition1().get(1)[1] };
					SenderOBJ = chessBoard[1].MoveQueen(1, targetQueenToMove,
							new int[] { 1, 1 }, new int[] { 1, 2 });

					
				}


				
				boolean isValid2 = chessBoard[1].getIfMoveIsValid(SenderOBJ.get(0).get(0), SenderOBJ.get(0).get(1), SenderOBJ.get(1).get(0), SenderOBJ.get(1).get(1), SenderOBJ.get(2).get(0), SenderOBJ.get(2).get(1));
				if(!isValid2) {System.out.println("it is invalid move");}
				// send move to serve/opponent
				this.gameClient.sendMoveMessage(SenderOBJ.get(0), SenderOBJ.get(1), SenderOBJ.get(2));
				this.gamegui.updateGameState(SenderOBJ.get(0), SenderOBJ.get(1), SenderOBJ.get(2));

				// print console output
				chessBoard[1].print();
				chessBoard[1].countQueens();
				// }
				turn++;
				break;
			default:
				assert (false);
				break;
			}
		}

		return true;
	}
	
	
	

}// end of class
