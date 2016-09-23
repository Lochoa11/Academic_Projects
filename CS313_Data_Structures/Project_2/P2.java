
// 1.  P2.java - should read the file (car data file I sent) to create Car objects ( your program should be able to handle any file with similar data) 
// 2.  It should insert same set of cars into 3 different Hash table with chaining ( keeping track of time to hash the item into the table, for analysis)
// 3. Print each hash table using given display or traversal methods and average time it takes for hashing using each data structure
// 4. Submit P2.java, Car.java, Color.java and if you modified Data structures I sent.

//NOTE: to see Collision take look at HashCode in Car.java
//displays will change


import java.io.*;
import java.util.*;

public class P2{
	public static void main(String[] args) throws IOException {
		
		File inputFile = new File("cars.txt");
		Scanner in = new Scanner(inputFile);
		//Scanning car.txt file

		String [] makes = new String[100];
		String [] models = new String[100];
		int [] years = new int[100];
		Color [] colors = new Color[100];
		//array to separate make model year color
		
		in.useDelimiter(",|\\n");
		//markers that separate txt into make model year color
		
		
		int wordCounter  = 0;
		int makeCounter  = 0;
		int modelCounter = 0;
		int yearCounter  = 0;
		int colorCounter = 0;
		//counters for each make model year color array


		while(in.hasNext()){
			//keep scanning txt file if there is more

			if(wordCounter % 4 == 0){
				//store int make array

				makes[makeCounter] = in.next();
				makeCounter++;
				wordCounter++;
				continue;
			}
			if(wordCounter % 4 == 1){
				//store int model array

				models[modelCounter] = in.next();
				modelCounter++;
				wordCounter++;
				continue;
			}
			if(wordCounter % 4 == 2){
				//store int year array

				years[yearCounter] = Integer.parseInt(in.next());
				yearCounter++;
				wordCounter++;
				continue;
			}
			if(wordCounter % 4 == 3){
				//store int color array

				colors[colorCounter] = new Color(in.next());
				colorCounter++;
				wordCounter++;
				continue;
			}	
		}
		//store make model year color into 4 different arrays

		
		// System.out.println("MAKES");
		// for (int i = 0; i < 57; i++) {
		// 	System.out.println(makes[i]);
		// }

		// System.out.println("MODELS");
		// for (int i = 0; i < 57; i++) {
		// 	System.out.println(models[i]);
		// }

		// System.out.println("YEARS");
		// for (int i = 0; i < 57; i++) {
		// 	System.out.println(years[i]);
		// }

		// System.out.println("COLORS");
		// for (int i = 0; i < 57; i++) {
		// 	System.out.println(colors[i].getColor());
		// }
		//uncomment these ^ lines to see make sure makes are in makes array

		
		Car [] myCarArray = new Car[57];
		// Temp Car Array

		for (int i = 0; i < 57; i++) {
			myCarArray[i] = new Car(makes[i], models[i], years[i], colors[i], i);	
		}
		//initialize temp array and set cars in.
		


//*************************************************************************************Linked List
		System.out.println("Linked List HASHTABLE");
		LinkedList []arrayOfCar = new LinkedList[100];
		//create array of 100 Linked Lists

		for (int i = 0; i < 57; i++) {
			arrayOfCar[i] = new LinkedList();
		}
		//creating references for each Linked List in the "HashTable"

		long lltimeBefore = System.nanoTime();
		//Start time

		for (int i = 0; i < 57; i++) {
			arrayOfCar[myCarArray[i].hashCode()].insertAtStart(myCarArray[i]);
		}
		//insert new cars into the HashTable
		
		long lltimeAfter = System.nanoTime();
		//End time

		for (int i = 0; i < 57; i++) {
			arrayOfCar[i].display();
		}
		//Display what the Linked Lists look like

		System.out.println("***************LinkedList insertion time took " + (lltimeAfter - lltimeBefore) + " nano seconds***************");
		//calculate runtime of insertion
		
//*************************************************************************************BST
		System.out.println("BST HASHTABLE");
		BST [] bstArrayOfCars = new BST[100];
		//create array of 100 BST

		for (int i = 0; i < 57 ; i++) {
			bstArrayOfCars[i] = new BST();
		}
		// creating references 

		long bstBefore = System.nanoTime();
		//start time

		for (int i = 0 ; i < 57; i++) {
			bstArrayOfCars[myCarArray[i].hashCode()].insert(myCarArray[i]);
		}
		//hasCode determines location in bstArrayOfCars

		long bstAfter = System.nanoTime();
		//end time

		bstArrayOfCars[1].inorder();
		             //^ Change this number to see each array position and determine if CHAINING is occuring
		

		System.out.println("***************BST insertion time took " + (bstAfter - bstBefore) + " nano seconds***************");
		//print time
		

//*************************************************************************************AVL
		System.out.println("AVL HASHTABLE");
		AVLTree [] avlCars = new AVLTree[100];
		//create array of 100 AVL

		for (int i = 0; i < 57 ; i++) {
			avlCars[i] = new AVLTree();			
		}
		//creating references 

		long avlBefore = System.nanoTime();
		//start time

		for (int i = 0; i < 57; i++) {
			avlCars[myCarArray[i].hashCode()].insert(myCarArray[i]);
		}
		//hasCode determines location in avlCars

		long avlAfter = System.nanoTime();
		//end time

		avlCars[0].inorder();
		      //^Change this number to see each array position and determine if CHAINING is occuring
		System.out.println("***************AVL insertion time took " + (avlAfter - avlBefore) + " nano seconds***************");		
		//print time

	} 
}
