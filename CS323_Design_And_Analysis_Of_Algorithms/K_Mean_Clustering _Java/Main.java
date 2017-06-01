import java.util.*;
import java.io.*;

public class Main{
	public static void main(String[] args) {
		try{
			File inputFile = new File(args[0]);
			Scanner in = new Scanner(inputFile);
			
			int k;
			int numOfPts;
			int numOfRows;
			int numOfCols;
			int num1;
			int num2;

			k = Integer.parseInt(in.next());
			numOfPts = Integer.parseInt(in.next());
				
			numOfRows = Integer.parseInt(in.next());
			numOfCols = Integer.parseInt(in.next());
			
			Kmean myKmean = new Kmean(k, numOfPts, numOfRows, numOfCols);

			while(in.hasNext()){
				num1 = Integer.parseInt(in.next());
				num2 = Integer.parseInt(in.next());
				myKmean.loadPointSet(num1, num2);
			}

			myKmean.assignLabel();
			myKmean.mapPointToImage();
			myKmean.displayImage(args[1]);
			myKmean.kMeanClustering();
			myKmean.displayImage(args[1]);

			while(myKmean.aLabelHasChanged()){
				myKmean.resetLabels();
				myKmean.kMeanClustering();
				myKmean.mapPointToImage();
			}

			myKmean.printPointSet();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

class Point{
	int x;
	int y;
	int clustID;
	double distToCent;
	boolean labelChanged;
	public Point(){
		distToCent = 0.0;
		labelChanged = false;
	}
	public void setX(int num1){
		x = num1;
	}
	public void setY(int num2){
		y = num2;
	}
	public void setClusterID(int id){
		clustID = id;
	}
	void printPoint(){}
}

class Kmean{
	int k;
	int numOfPts;
	int numOfRows;
	int numOfCols;
	int num1;
	int num2;
	Point []pointSet;
	Point []kCentroids;
	int [][]imageArray;
	int currentPositionInPS;
	public Kmean(int k, int numOfPts, int numOfRows, int numOfCols){
		this.k = k;
		this.numOfPts = numOfPts;
		this.numOfRows = numOfRows;
		this.numOfCols = numOfCols;
		pointSet = new Point[numOfPts];
		kCentroids = new Point[k];
		for (int i = 0; i < k; i++) {
			kCentroids[i] = new Point();
		}
		imageArray = new int[numOfRows][];
		for (int i = 0; i < numOfRows; i++) {
			imageArray[i] = new int[numOfCols];
			for (int j = 0; j < numOfCols; j++) {
				imageArray[i][j] = 0;
			}
		}
		currentPositionInPS = 0;

	}
	public void loadPointSet(int num1, int num2){
		pointSet[currentPositionInPS] = new Point();
		pointSet[currentPositionInPS].setX(num1);
		pointSet[currentPositionInPS].setY(num2);
		currentPositionInPS++;
	}
	public void assignLabel(){
		for(int i = 0; i < currentPositionInPS; i++){
			pointSet[i].setClusterID(i%k+1);
		}
	}
	public void mapPointToImage(){
		for (int i = 0; i < numOfRows; i++){
			for (int j = 0; j < numOfCols; j++){
				imageArray[i][j] = 0;
			}
		}

		for (int i = 0; i < currentPositionInPS; i++) {
			imageArray[pointSet[i].x][pointSet[i].y] = pointSet[i].clustID;
		}
	}
	public void kMeanClustering(){

		// compute kCentroids
		for (int i = 1; i < k+1; i++){
			int tempX = 0;
			int tempY = 0;
			int totalNumInCluster = 0;
			for(int j = 0; j < currentPositionInPS; j++){
				if(pointSet[j].clustID == i){
					totalNumInCluster++;
					tempX += pointSet[j].x;
					tempY += pointSet[j].y;
				}
			}
			if(totalNumInCluster != 0){
				kCentroids[i-1].setX(tempX/totalNumInCluster);
				kCentroids[i-1].setY(tempY/totalNumInCluster);
			}
		}

		//calculate distance
		for (int i = 0; i < currentPositionInPS; i++) {
			double minDistance = Math.sqrt(
					Math.pow(Math.abs(pointSet[i].x - kCentroids[0].x), 2)
					+Math.pow(Math.abs(pointSet[i].y - kCentroids[0].x), 2)
				);
			if (pointSet[i].distToCent == 0.0) {
				pointSet[i].distToCent = minDistance;
			}
			for(int j = 0 ; j < k; j++){
				double temp = Math.sqrt(
					Math.pow(Math.abs(pointSet[i].x - kCentroids[0].x), 2)
					+Math.pow(Math.abs(pointSet[i].y - kCentroids[0].x), 2)
				);
				if(temp < minDistance && pointSet[i].distToCent > temp){
					minDistance = temp;
					pointSet[i].clustID = j+1;
					pointSet[i].labelChanged = true;
					pointSet[i].distToCent = temp;
				}
			}
		}
	}
	public boolean aLabelHasChanged(){
		for (int i = 0; i < currentPositionInPS; i++) {
			if (pointSet[i].labelChanged) {
				return true;	
			}
		}
		return false;
	}
	public void displayImage(String outFile2){
		try{
			PrintWriter out = new PrintWriter(new FileWriter(outFile2, true));

			for (int i = 0; i < numOfRows; i++) {
				for (int j =0; j < numOfCols; j++) {
					if (imageArray[i][j] == 0) {
						out.print(" ");
					}
					else{
						out.print(imageArray[i][j]);
					}
				}
				out.println();
			}

			out.println("****************************************");
			out.close();
		}
		catch(Exception e){
			System.out.println("Error in displayImage");
		}
	}

	public void printPointSet(String outFile1){
		try{
			PrintWriter out = new PrintWriter(new FileWriter(outFile1, true));

			out.println(k+"");
			out.println(currentPositionInPS+"");
			out.println(numOfRows + " " + numOfCols+"");
			for(int i = 0; i < currentPositionInPS; i++){
				out.println(pointSet[i].x + " " + pointSet[i].y + " " + pointSet[i].clustID);
			}
		}
		catch(Exception e){
			System.out.println("Error in printPointSet");
		}
	}
	public void resetLabels(){
		for (int i = 0; i < currentPositionInPS; i++) {
			pointSet[i].labelChanged = false;
		}
	}
}
