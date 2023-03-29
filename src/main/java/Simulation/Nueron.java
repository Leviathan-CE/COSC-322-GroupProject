package Simulation;

import java.io.Serializable;

/**
 * 
 * @author Levi
 *
 */
public class Nueron implements Serializable{


	private static final long serialVersionUID = 167L;
	double[] Wieghts = null;
	Nueron parent = null;
	Nueron child = null;
	int ID = 0;
	private static int count = 0;
	
	public Nueron(double[] wieghts) {
		ID = count++;
		this.Wieghts = new double[wieghts.length];
		for(int i = 0 ;i < wieghts.length;i++) {
			this.Wieghts[i] = wieghts[i];
		}
	}
	
	public Nueron(Nueron newNode) {
		ID = count++;
		Wieghts = new double[newNode.Wieghts.length];
		for(int i = 0 ;i < newNode.Wieghts.length;i++) {
			Wieghts[i] = newNode.Wieghts[i];
		}
		this.parent = newNode;
		newNode.child = this;
	}
	
	public Nueron next() {
		return child;
	}
	public Nueron prevoius() {
		return parent;
	}
	public double[] getWieghts() {
		return Wieghts;
	}
}
