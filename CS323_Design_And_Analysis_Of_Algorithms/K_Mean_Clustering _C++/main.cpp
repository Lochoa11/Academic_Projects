#include <iostream>
#include <fstream>
#include <cmath>
using namespace std;

class Point{
public: 
	int xCoordinate;
	int yCoordinate;
	int clusterID;
	double distToCentroid;
	bool labelChanged;
	Point(){
		distToCentroid = 0.0;
		labelChanged = false;
	}
	void printPoint(){
	}
};

class Kmean{
	int K;
	struct xyCoord{
		int xCoord;
		int yCoord;
	};
	xyCoord *Kcentroids;
	int numPts;
	Point *pointSet;
	int numRow;
	int numCol;
	int **imageArray;
	int currentPositionInPS;
public: 
	Kmean(int K, int numPts, int numRow, int numCol){
		this->K = K;
		this->numPts = numPts;
		this->numRow = numRow;
		this->numCol = numCol;
		pointSet = new Point[numPts];
		Kcentroids = new xyCoord[K];
		imageArray = new int*[numRow];
		for (int i = 0; i < numRow; i++){
			imageArray[i] = new int[numCol];
			for(int j = 0; j < numRow; j++){
				imageArray[i][j] = 0;
			}
		}
		currentPositionInPS = 0;
	}
	void loadPointSet(int firstNum, int secondNum){
		pointSet[currentPositionInPS].xCoordinate = firstNum;
		
		pointSet[currentPositionInPS].yCoordinate = secondNum;
		
		currentPositionInPS++;
	}
	void assignLabel(){
		pointSet[currentPositionInPS].clusterID = (currentPositionInPS % K)+1;
		
	}
	void mapPointToImage(){
		for (int i = 0; i < numRow; i++){
			imageArray[i] = new int[numCol];
			for(int j = 0; j < numRow; j++){
				imageArray[i][j] = 0;
			}
		}
		for (int i = 0; i < currentPositionInPS; i++){
			imageArray[pointSet[i].xCoordinate][pointSet[i].yCoordinate]= pointSet[i].clusterID;
			
		}
	}
		void kMeanClustering(){
		
		

		//compute centroid
		for (int i = 0; i < K; i++){
			int tempX = 0;
			int tempY = 0;
			int totalNumberInCluster = 0;
			for(int j = 0; j < currentPositionInPS; j++){
				if(pointSet[j].clusterID-1 == i){
					totalNumberInCluster++;
					
					tempX += pointSet[j].xCoordinate;
					tempY += pointSet[j].yCoordinate;
				}
			}
			
			if(totalNumberInCluster != 0){
				Kcentroids[i].xCoord = tempX/totalNumberInCluster;
				Kcentroids[i].yCoord = tempY/totalNumberInCluster;

				
			}
		}

		//calculate distance 
		for(int i = 0; i < currentPositionInPS; i++){

			double minDistance = sqrt(
									  pow(abs(pointSet[i].xCoordinate - Kcentroids[0].xCoord), 2)
									+ pow(abs(pointSet[i].yCoordinate - Kcentroids[0].yCoord), 2)
									);
			if(pointSet[i].distToCentroid == 0.0){
				pointSet[i].distToCentroid = minDistance;
			}
			
			for(int j = 0; j < K; j++){
				double temp = sqrt(
									  pow(abs(pointSet[i].xCoordinate - Kcentroids[j].xCoord), 2)
									+ pow(abs(pointSet[i].yCoordinate - Kcentroids[j].yCoord), 2)
									);
				
				if(temp < minDistance && pointSet[i].distToCentroid > temp){
					minDistance = temp;
					pointSet[i].clusterID = j+1;
					pointSet[i].labelChanged = true;
					pointSet[i].distToCentroid = temp;
					
				}

			}
		}

	}
	bool aLabelHasChanged(){
		for(int i = 0; i < currentPositionInPS; i++){
		
			if(pointSet[i].labelChanged){
				return true;
			}
		}
		return false;
	}
	void displayImage(ofstream &outFile2){
		
		for (int i = 0; i < numRow; i++){
			for (int j = 0; j < numCol; j++){
				if(imageArray[i][j] == 0)outFile2 << " ";
				else outFile2 << imageArray[i][j];
			}
			outFile2 << endl;
		}

		outFile2 << endl;
		outFile2 << endl;
		outFile2 << endl;
		outFile2 << endl;
		outFile2 << endl;
		outFile2 << "*****************************************" << endl;
	}
	void printPointSet(ofstream& outFile){
		outFile << K << endl;
		outFile << currentPositionInPS << endl;
		outFile << numRow << " " << numCol << endl;
		for(int i = 0; i < currentPositionInPS; i++){
			outFile << pointSet[i].xCoordinate << " " << pointSet[i].yCoordinate << " " << 	pointSet[i].clusterID << endl;
		}
	}
	void resetLabels(){
		for(int i = 0; i < currentPositionInPS; i++){
			pointSet[i].labelChanged = false;
		}
	}
	void printCentroid(){
		for(int i = 0; i < K; i++){
			cout << Kcentroids[i].xCoord << " " << Kcentroids[i].yCoord <<  endl;
		}

		cout << endl;
		cout << endl;
		cout << endl;
		cout << endl;
		cout << endl;
	}
};

int main(int argc, char** argv){

	ifstream inputFile;
	inputFile.open(argv[1]);
	ofstream outFile;
	outFile.open(argv[2]);
	ofstream outFile2;
	outFile2.open(argv[3]);


	int K;
	int numPts;
	int numRow;
	int numCol;


	inputFile >> K;
	inputFile >> numPts;
	inputFile >> numRow;
	inputFile >> numCol;

	Kmean *myKmeans = new Kmean(K, numPts, numRow, numCol);



	int firstNum;
	int secondNum;
	while(inputFile >> firstNum){	
		inputFile >> secondNum;
		myKmeans->loadPointSet(firstNum, secondNum);
		myKmeans->assignLabel();
	}

	myKmeans->mapPointToImage();
	myKmeans->displayImage(outFile2);	
	myKmeans->kMeanClustering();	
	myKmeans->displayImage(outFile2);	


	
	while(myKmeans->aLabelHasChanged()){
		myKmeans->resetLabels();
		myKmeans->kMeanClustering();
		myKmeans->mapPointToImage();
		myKmeans->displayImage(outFile2);	
		

	}

	myKmeans->printPointSet(outFile);

}