package GameState;

public class MoveInfo {

	private int[] oldQPos;
	private int[] newQPos;
	private int[] arrow;

	public MoveInfo(int[] oldQ, int[] newQ, int[] arrw) {
		oldQPos = oldQ;
		newQPos = newQ;
		arrow = arrw;
	}
	
	public int[] getOldQPos() {return oldQPos;}
	public int[] getNewQPos() {return newQPos;}
	public int[] getArrow() {return arrow;}
	
	public void copy(MoveInfo info) {
		if (info.oldQPos != null)
			oldQPos = new int[] { info.oldQPos[0], info.newQPos[1] };
		if (info.newQPos != null)
			newQPos = new int[] { info.newQPos[0], info.newQPos[1] };
		if (info.arrow != null)
			arrow = new int[] { info.newQPos[0], info.arrow[1] };
	}
}
