package GameState;

public class Vector2 {
	
	private int[] xy;
	
	public Vector2(int[] coords) {
		if(coords.length != 2)
			throw new IndexOutOfBoundsException("only take vectors of length 2");
		this.xy = new int[] {coords[0],coords[1]};
	}
	
	public Vector2(Vector2 vector) {
		this.xy = new int[] {vector.getx(),vector.gety()};
	}
	
	public Vector2(int x, int y) {
		this.xy = new int[] {x,y};
	}

	public int getx() {return xy[0];}
	public int gety() {return xy[1];}
	
	public Vector2 sub(Vector2 v) {
		return new Vector2(xy[0]-v.getx(),xy[1]-v.gety());
	}
	public Vector2 sub(int x,int y) {
		return new Vector2(xy[0]-x,xy[1]-y);
	}
	
	public Vector2 add(Vector2 v) {
		return new Vector2(xy[0]+v.getx(),xy[1]+v.gety());
	}
	
	public Vector2 add(int x,int y) {
		return new Vector2(xy[0]+x,xy[1]+y);
	}
	public boolean equals(Vector2 v) {
		if(xy[0] ==v.getx() && xy[1] == v.gety())
			return true;
		return false;
	}
}
