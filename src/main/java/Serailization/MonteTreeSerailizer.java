package Serailization;

import java.util.ArrayList;

import GameState.Node;
import Simulation.Nueron;

import java.io.*;

/**
 * MonteTree serailizer write to data folder and read from data folder 
 * Note; static fields will not be serialized. 
 * another note: if you get a bunch of errors the most liekly 
 * cause is you change something in that class and will need to delete
 * the savedfile and run again. this is because each time you change a 
 * serailezed files non static fields you change how the file is read and thus 
 * causes a error when it doesn't expect a new field or a missing field.
 *
 */
public class MonteTreeSerailizer {
	 private static final String filepath= "src\\main\\java\\Serailization\\Data\\";
	 private static final String filepathMac= "src/main/java/Serailization/Data/";
	 public static String fileName ="test.txt";
	 
	 public String getFileName() {return fileName;}
	 public void setFileName(String newFile) {fileName = newFile;}
	 
	 /**
	  * save Nueron Hueristic values
	  * @param node
	  */
	 public static void saveNueron(Nueron node, String fileName) {
		 File data = new File(filepath+fileName);
			if(!data.exists())
				try {
					data.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			try {
				FileOutputStream write = new FileOutputStream(data);
				ObjectOutputStream objOut = new ObjectOutputStream(write);
				objOut.writeObject(node);
				objOut.close();
				System.out.println("saved");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(data.exists() +";"+ data.getAbsolutePath());
	 }
	 /**
	  * load hueristic Constants from nueron
	  * @return
	  */
	 public static Nueron LoadNueron(String fileName){
			File data = new File(filepath+fileName);
			Nueron tree = null;
			if(!data.exists())
				try {
					data.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			try {
				FileInputStream write = new FileInputStream(data);
				ObjectInputStream objOut = new ObjectInputStream(write);
				Object obj =  objOut.readObject();
				if(obj instanceof Nueron)
					tree = (Nueron) obj;
				objOut.close();
				System.out.println("Loaded");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(data.exists() +";"+ data.getAbsolutePath());
			
			return tree;
			
		}
	 
	 /**
	  * serailzies and saves to file list of nodes
	  * @param tree
	  */
	public static void SaveTree(ArrayList<Node> tree) {
		File data = new File(filepath+fileName);
		if(!data.exists())
			try {
				data.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		try {
			FileOutputStream write = new FileOutputStream(data);
			ObjectOutputStream objOut = new ObjectOutputStream(write);
			objOut.writeObject(tree);
			objOut.close();
			System.out.println("saved");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(data.exists() +";"+ data.getAbsolutePath());
	}
	
	/**
	 * read from serzilized file
	 * @return arraylist of nodes
	 */
	public static ArrayList<Node> LoadTree(){
		File data = new File(filepath+fileName);
		ArrayList<Node> tree = null;
		if(!data.exists())
			try {
				data.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		try {
			FileInputStream write = new FileInputStream(data);
			ObjectInputStream objOut = new ObjectInputStream(write);
			Object obj =  objOut.readObject();
			if(obj instanceof ArrayList<?>)
				tree = (ArrayList<Node>) obj;
			objOut.close();
			System.out.println("Loaded");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(data.exists() +";"+ data.getAbsolutePath());
		
		return tree;
		
	}
	
	public static void main(String[] args) {
		Integer[] state = new Integer[] {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		ArrayList<Integer> x = new ArrayList<>();
		for( int i : state) {
			x.add(i);
		}
		Node n = new Node(x);
		Node n2 = new Node(x);
		ArrayList<Node> tree = new ArrayList<>();
		tree.add(n);
		tree.add(n2);
		System.out.println(tree.size());
		SaveTree(tree);
		tree.clear();
		tree = LoadTree();
		System.out.println(tree.size());
	}
}
