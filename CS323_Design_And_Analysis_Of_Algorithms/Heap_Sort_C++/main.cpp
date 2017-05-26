#include <iostream>
#include <fstream>
#include <sstream>

using namespace std;

class PQSort{

	int * PQArray;
	int lastPositionInArray;

public: 
	PQSort(int x){
		PQArray = new int[x];
		PQArray[0] = 0;
		lastPositionInArray = x-1;
	}
	~PQSort(){}

	void buildPQAry(ifstream& inputFile, ofstream& outFile1){
		int data;

		while(inputFile >> data){
			insertOneDataItem(data);
			printPQArray(outFile1);
		}
	}	

	void insertOneDataItem(int data){
		if(isFull()){
			return;
		}
		PQArray[0]++;
		PQArray[PQArray[0]] = data;
		// cout << PQArray[PQArray[0]] << " " << PQArray[0] << endl;
		bubbleUp();
	}

	void bubbleUp(){
		for(int i = PQArray[0]; i/2 > 0; i/=2){
			if(PQArray[i] < PQArray[i/2]){
				int temp = PQArray[i];
				PQArray[i] = PQArray[i/2];
				PQArray[i/2] = temp;
			}
			else{
				return;
			}
		}
	}

	bool isFull(){
		if(PQArray[0] == lastPositionInArray){
			cout << "isFull";
			return true;
		}
		return false;
	}

	void printPQArray(ofstream& outFile){

		for(int i = 1; i <= 10; i++){
			if(i>PQArray[0])break;
			outFile << PQArray[i] << "  -->  ";
			// cout << PQArray[i] << " --> "; 
		}
		// cout << endl;
		outFile << endl;
		
	}

	void deletePQAry(ofstream& outFile2, ofstream& outFile1){
		if(isEmpty())return;
		while(!isEmpty()){
			printRoot(outFile2);
			PQArray[1] = PQArray[PQArray[0]];
			PQArray[0]--;
			bubbleDown();
			printPQArray(outFile1);
		}
	}
	void printRoot(ofstream& outFile2){
		outFile2 << "Deleting: " << PQArray[1] << endl;
	}
	bool isEmpty(){
		if(PQArray[0] == 0)return true;
		return false;
	}

	void bubbleDown(){
		for(int i = 1; i <= lastPositionInArray;){
			if(PQArray[i*2] > PQArray[0]){
				return;
			}
			if(PQArray[i*2+1] > PQArray[0]){
				if(PQArray[i] > PQArray[i*2]){
					int temp = PQArray[i];
					PQArray[i] = PQArray[i*2];
					PQArray[i*2] = temp;
					i *= 2;
				}
			}
			if(PQArray[i*2] < PQArray[i*2+1]){
				if(PQArray[i] > PQArray[i*2]){
					int temp = PQArray[i];
					PQArray[i] = PQArray[i*2];
					PQArray[i*2] = temp;
					i *= 2;
				}
			}
			else if(PQArray[i*2+1] < PQArray[i*2]){
				if(PQArray[i] > PQArray[i*2+1]){
					int temp = PQArray[i];
					PQArray[i] = PQArray[i*2+1];
					PQArray[i*2+1] = temp;
					i*=2;
					i++;
				}
			}
			break;
		}
	}
};


int main(int argc, char** argv){
	
	ifstream inputFile;
	inputFile.open(argv[1]);

	ofstream outFile1;
	outFile1.open(argv[2]);

	ofstream outFile2;
	outFile2.open(argv[3]);

	int count = 0;
	int temp = 0;
	while(inputFile >> temp){
		count++;
	}
	inputFile.close();


	PQSort * myPQSort = new PQSort(count+1);
	
	inputFile.open(argv[1]);
	myPQSort->buildPQAry(inputFile, outFile1);
	inputFile.close();

	myPQSort->deletePQAry(outFile2, outFile1);

	outFile1.close();
	outFile2.close();

}