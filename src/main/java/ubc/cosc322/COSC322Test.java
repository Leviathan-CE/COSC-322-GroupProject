
package ubc.cosc322;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ActionFactory.ActionFactory;
import GameState.GameBoardState;
import GameState.MoveInfo;
import GameState.Node;
import MiniMax.MiniMaxSearch;
import Search.DepthFirstSearch;
import Serailization.MonteTreeSerailizer;
import Simulation.Nueron;
import Util.Timer;
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

	private Node chessBoard;
	private int turn = 1; // 1 = black 2 = white
	private int ourColor = 0;
	private int notOurColor = 0;
	private static String KEY = "BOB";

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
	 * @param args for name and passwd (current, any string would work)
	 */
	public static void main(String[] args) {
		COSC322Test player = new COSC322Test(args[0], args[1]);
		KEY = args[0];

		//HumanPlayer player = new HumanPlayer();

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
	 *       Note: This method will be called by the GameClient when it receives a
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
				//System.out.println("ENEMY MOVE GET THINGY");
				this.getGameGUI().setGameState((ArrayList<Integer>) (msgDetails.get(AmazonsGameMessage.GAME_STATE)));
				ArrayList<Integer> GottenGameState = (ArrayList<Integer>) (msgDetails.get(AmazonsGameMessage.GAME_STATE));
				// set initial local state of GameBoard
				chessBoard = new Node(GottenGameState);
				chessBoard.updateQueenPoses();
				chessBoard.printQPoses();
				break;

			case GameMessage.GAME_ACTION_START:
				Timer.start();
				System.out.println("------GAME START-------");
				String blackUserName = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
				System.out.println(blackUserName);
				
				//load constraints from file
				Nueron Constants_black = null;
				Nueron Constants_white = null;
			
				//attempt to load weights
				try {
					String fileName = "constants.txt";
					Constants_black = MonteTreeSerailizer.LoadNueron(fileName);
					Constants_white = MonteTreeSerailizer.LoadNueron(fileName);

					System.out.println("loaded values");
				}catch(Exception e) {
					System.out.println("file not found not being created yet");
					Constants_black = new Nueron(MoveSequence.Cb);
					Constants_white = new Nueron(MoveSequence.Cw);
				}
				//apply weight constraints to heuristics
				MoveSequence.Cb = Constants_black.getWieghts(); //balck 
				MoveSequence.Cw = Constants_black.getWieghts(); //white
				
				
				//determine who is what color
				if (blackUserName.equalsIgnoreCase(KEY)) {
					ourColor = 1;
					notOurColor = 2;
					System.out.println("ourColor : "+ourColor);				
				
					//print 					
					for(int i = 0; i < MoveSequence.Cb.length;i++) {
						System.out.print(MoveSequence.Cb[i]+" : ");
					}					
				
				} else {
					ourColor = 2;
					notOurColor = 1;
					System.out.println();
					for(int i = 0; i < MoveSequence.Cw.length;i++) {
						System.out.print(MoveSequence.Cw[i]+" : ");
					}

				}
				//if we are black we go first 
				if (ourColor == 1) {						
					chessBoard = MoveSequence.GenerateMove(chessBoard, ourColor,turn);
						MoveSequence.sendPackageToServer(this.gamegui, this.gameClient,
								MoveSequence.setSenderObj(chessBoard.moveInfo.getOldQPos(), chessBoard.moveInfo.getNewQPos(), chessBoard.moveInfo.getArrow()) );
				}
				System.out.println(Timer.currentTime());
				System.out.println("------Start Next TURNS-------");
				break;

			/*
			 * when a move occurs update game state and update local board to match
			 */
			case GameMessage.GAME_ACTION_MOVE:
				this.gamegui.updateGameState(msgDetails);
				System.out.println("------ENEMY TURN----------");
				System.out.println(AmazonsGameMessage.GAME_STATE);
				System.out.println("turn: " + turn);
				Timer.start();

				// if(turn != 1) {
				// fetch the newest move from Opponent and store their values in x,y lists
				ArrayList<Integer> queenPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
				ArrayList<Integer> newQueenPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
				ArrayList<Integer> arrowPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);


				// update local game state to match the new state
				// mutate data to readable
				int[] QnPos = new int[] { queenPos.get(0) - 1, queenPos.get(1) - 1 };
				int[] newQnPos = new int[] { newQueenPos.get(0) - 1, newQueenPos.get(1) - 1 };
				int[] arrw = new int[] { arrowPos.get(0) - 1, arrowPos.get(1) - 1 };
				System.out.println("old Q pos; "+QnPos[0]+";"+QnPos[1]);
				System.out.println("old Q pos; "+newQnPos[0]+";"+newQnPos[1]);
				System.out.println("old Q pos; "+arrw[0]+";"+arrw[1]);
				
				// is the opponent move a valid move?
				boolean isValidQueen = chessBoard.checkIfPathIsClear(QnPos, newQnPos);
				boolean isValidArrow = chessBoard.checkIfPathIsClear(newQnPos,arrw);
				if (!isValidQueen || !isValidArrow ) {
					System.out.println("---------------------------------------");
					System.out.println("---------------------------------------");
					System.out.println("---------------------------------------");
					System.out.println("OPPONENT made a INVALID move");
					System.out.println("---------------------------------------");
					System.out.println("---------------------------------------");
					System.out.println("---------------------------------------");
					throw new RuntimeException("ENEMY LOSE");
				}
//							

				//apply opponent move
				chessBoard.setPosValue(0, QnPos[0], QnPos[1]);
				chessBoard.setPosValue(notOurColor, newQnPos[0], newQnPos[1]);
				chessBoard.setPosValue(3, arrw[0], arrw[1]);
				chessBoard.updateQueenPoses();
				chessBoard.countQueens();
				chessBoard.print();
				System.out.println("--------end of enemy turn-----------");
				turn++;
				//generate our move
				chessBoard = MoveSequence.GenerateMove(chessBoard, ourColor, turn);
				isValidQueen = chessBoard.checkIfPathIsClear(chessBoard.moveInfo.getOldQPos(),
						chessBoard.moveInfo.getNewQPos());
				isValidArrow = chessBoard.checkIfPathIsClear(chessBoard.moveInfo.getNewQPos(),
						chessBoard.moveInfo.getArrow());
				//checkout our move is valid
				if (!isValidQueen || !isValidArrow ) {
					System.out.println("---------------------------------------");
					System.out.println("---------------------------------------");
					System.out.println("---------------------------------------");
					System.out.println("WE HAD DONE A OOPS and made a INVALID move");
					System.out.println("---------------------------------------");
					System.out.println("---------------------------------------");
					System.out.println("---------------------------------------");
					throw new RuntimeException("WE LOOSE");
				}
				
				System.out.println("Time sec: "+ Timer.currentTime());
				MoveSequence.sendPackageToServer(this.gamegui, this.gameClient,
						MoveSequence.setSenderObj(chessBoard.moveInfo.getOldQPos(), chessBoard.moveInfo.getNewQPos(), chessBoard.moveInfo.getArrow()) );
				System.out.println("update local");

				break;
			default:
				assert (false);
				break;
			}
		}

		return true;
	}

}// end of class
