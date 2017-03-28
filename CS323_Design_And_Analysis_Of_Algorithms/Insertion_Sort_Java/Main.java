import java.util.*;
import java.io.*;

public class Main{

	public static void main(String [] args){
		
		try{
			File inputFile = new File(args[0]);
			Scanner in = new Scanner(inputFile);

			String x;

			linkedList Cwordlist = new linkedList();

			while(in.hasNext()){
				x = in.next();
				listNode spot;
				listNode newNode = new listNode(x);
				spot = Cwordlist.findSpot(x);
				if(Cwordlist.exists(x)){
					continue;
				}			
				Cwordlist.listInsert(spot, newNode);
				Cwordlist.outputToFile(args[1], x);
			}
			// listNode hello;
			// hello = Cwordlist.listHead;
			// while(hello.nextNode != null){
			// 	System.out.print(hello.data);
			// }
		}
		catch(IOException e){
			System.out.println("Error exception!!");
			System.exit(1);
		}
		
	}

}

class listNode{
	String data;
	int count;
	listNode nextNode;
	public listNode(){}
	public listNode(String x){
		count = 0;
		data = x;
	}
}

class linkedList{
	listNode listHead;

	public linkedList(){
		listNode dummyNode = new listNode("dummy");
		listHead = dummyNode;
	}
	public listNode findSpot(String x){
		listNode spot;
		spot = listHead;

		while(spot.nextNode != null){
			if(compare(spot.nextNode.data, x) == -1){
				spot = spot.nextNode;
			}
			else if(compare(spot.nextNode.data, x) == 1){
				return spot;
			}
			else{
				spot.nextNode.count++;
				break;
			}
		}
		return spot;
	}

	int compare(String x, String y){
		x = x.toLowerCase();
		y = y.toLowerCase();

		for(int i = 0; i < x.length()&& i < y.length(); i++){
			if(x.charAt(i) < y.charAt(i)){
				return -1;
			}
			else if(x.charAt(i) > y.charAt(i)){
				return 1;
			}
			else{
				continue;
			}
		}
		if(x.length() < y.length()){
			return -1;
		}
		if(x.length() > y.length()){
			return 1;
		}
		return 0;
	}

	public void listInsert(listNode spot, listNode newNode){
		newNode.nextNode = spot.nextNode;
		spot.nextNode = newNode;
	}

	public Boolean exists(String x){
		listNode spot;

		spot = listHead;

		while(spot.nextNode != null){
			if(spot.nextNode.data == x) return true;
			spot = spot.nextNode;
		}
		return false;
	}

	public void outputToFile(String outFile, String x){
		try{
			PrintWriter out = new PrintWriter(new FileWriter(outFile, true));
			out.println("");
			out.println("Inserting: (" + x + ")");
			out.print("listHead ");
			listNode spot = listHead;

			while(spot.nextNode != null){
				out.print(" --> (" + spot.data +", " + spot.nextNode.data + ")");
				spot = spot.nextNode;
			}
			out.println("");
			out.close();
		}
		catch(IOException e){
			System.out.println("io exception outputToFile function");
		}
	}

}

