package GameState;
/**
 * A helper class that contains Move information 
 * of a past and current play made to a game state
 * in a format for GameBoardState and Node. doens't work with 
 * server packages
 * 
 */
public class MoveInfo {

	private int[] oldQPos;
	private int[] newQPos;
	private int[] arrow;
	private int playerID = 0;
	
	public MoveInfo(int[] oldQ, int[] newQ, int[] arrw, int playerID) {
		oldQPos = oldQ;
		newQPos = newQ;
		arrow = arrw;
		this.playerID = playerID;
	}
	
	/**
	 * 
	 * @return color id of this move
	 */
	public int getPlayerID() {return playerID;}
	/**
	 * 
	 * @return old queen position
	 */
	public int[] getOldQPos() {return oldQPos;}
	/**
	 * 
	 * @return the move made
	 */
	public int[] getNewQPos() {return newQPos;}
	/**
	 * 
	 * @return the Arrow placed
	 */
	public int[] getArrow() {return arrow;}
	
	/**
	 * deep copies the object
	 * @param info : object to copy
	 */
	public void copy(MoveInfo info) {
		if (info.oldQPos != null)
			oldQPos = new int[] { info.oldQPos[0], info.newQPos[1] };
		if (info.newQPos != null)
			newQPos = new int[] { info.newQPos[0], info.newQPos[1] };
		if (info.arrow != null)
			arrow = new int[] { info.newQPos[0], info.arrow[1] };
	}
}
