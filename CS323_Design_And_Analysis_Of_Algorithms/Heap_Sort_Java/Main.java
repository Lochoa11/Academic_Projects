import java.util.*;
import java.io.*;

public class Main{
	public static void main(String []args){
		if(args.length != 3){
			System.out.println("wrong amount of inputs");
			System.exit(1);
		}
		try{
			File inputFile = new File(args[0]);
			Scanner in = new Scanner(inputFile);
			int counter = 0;

			while(in.hasNext()){
				in.next();
				counter++;
			}
			PQSortClass myPQSortClass = new PQSortClass(counter+1);
			myPQSortClass.buildPQAry(inputFile, args[1]);

			myPQSortClass.deleteRoot(args[1], args[2]);
		}
		catch(Exception e){
			System.out.println(e.getClass().getName());
		}
	}
}

class PQSortClass{
	int [] myPQAry;	
	int lastposition;
	PQSortClass(int counter){
		myPQAry = new int[counter];
		myPQAry[0] = 0;
		lastposition = counter - 1;
	}
	public void buildPQAry(File inputFile, String outputFile){
		int x;
		try{
			Scanner in = new Scanner(inputFile);
			while(in.hasNext()){
				x = Integer.parseInt(in.next());
				// System.out.println("" + x);
				insertOneDataItem(x);
				printAry(outputFile);
			}
		}
		catch(Exception e){
			System.out.println("File not found Exception in buildPQAry");
		}
	}

	void insertOneDataItem (int x){
		if(lastposition == myPQAry[0]){
			return;
		}// check if Array is full
		myPQAry[0]++;
		myPQAry[myPQAry[0]] = x;
		bubbleUp();
	}

	void bubbleUp(){
		for(int i = myPQAry[0]; i > 1; i/=2){
			if (myPQAry[i/2] > myPQAry[i]) {
				int temp = myPQAry[i];
				myPQAry[i] = myPQAry[i/2];
				myPQAry[i/2] = temp;
			}
			else{
				break;
			}
		}
	}

	void printAry(String outputFile){
		try{
			PrintWriter out = new PrintWriter(new FileWriter(outputFile, true));
			for (int i = 1; i <= myPQAry[0]; i++) {
				out.print(myPQAry[i] + " ");
			}
			out.println("");
			out.close();
		}
		catch(Exception e){
			System.out.println("Exception in printAry");
		}
	}

	void deleteRoot(String outputFile, String outputFile2){
		if(myPQAry[0] == 0){return;}//heap is empty
		while(myPQAry[0] > 0){
			printAry(outputFile);
			printToFile2(outputFile2);
			myPQAry[1] = myPQAry[myPQAry[0]];
			myPQAry[myPQAry[0]] = 0;
			myPQAry[0]--;
			bubbleDown();
		}
	}

	void bubbleDown(){
		for(int i = 1; i <= myPQAry[0];){
		//if there are no child or no left leaf
			if(i*2>myPQAry[0]){
				return;
			}
			if(myPQAry[i] >= myPQAry[i*2] && (i*2)+1> myPQAry[0] && myPQAry[i*2]>0){
			//left leaf is smaller that parent no right child
				int temp = myPQAry[i];
				myPQAry[i] = myPQAry[i*2];
				myPQAry[i*2] = temp;
				return;
			}
			if(myPQAry[i] >= myPQAry[i*2] && myPQAry[i*2] <= myPQAry[(i*2)+1] && myPQAry[i*2] > 0){
			//left child smaller than right and parent is bigger than left child
				int temp = myPQAry[i];
				myPQAry[i] = myPQAry[i*2];
				myPQAry[i*2] = temp;
				i*=2;
				continue;	
			}
			if(myPQAry[i] >= myPQAry[(i*2)+1] && myPQAry[(i*2)+1] <= myPQAry[i*2] && myPQAry[(i*2)+1]>0){
			//right child is smaller than left and parent is bigger than right child
					int temp = myPQAry[i];
					myPQAry[i] = myPQAry[(i*2)+1];
					myPQAry[(i*2)+1] = temp;
					i*=2;
					i++;
					continue;
			}
			break;
		}

	}
	void printToFile2(String outputFile2){
		try{
			PrintWriter out = new PrintWriter(new FileWriter(outputFile2, true));
			out.println("Deleting: " + myPQAry[1]);
			out.close();
		}catch(Exception e){
			System.out.println("Error in printToFile2");
		}
	}
}