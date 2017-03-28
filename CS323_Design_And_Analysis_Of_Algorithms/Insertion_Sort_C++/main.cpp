#include <iostream>
#include <fstream>
#include <sstream>
// #include <string>
// #include <locale>

using namespace std;

class listNode{
public:
	friend class linkedList;
	string data;
	string nextWord;
	int count;
	listNode * nextNode;

	listNode(){
	}
	listNode(string x){
		data = x;
		count = 0;
		nextNode = NULL;
	}

	~listNode(){}
};

class linkedList{
public:
	listNode * listHead;
	
	linkedList(){
		listNode * dummy = new listNode();
		listHead = dummy;
		dummy->data = "dummy";
		
	}

	int compare(string x, string y){
		for(int i = 0; i <= x.length(); i++){
			x[i] = tolower(x[i]);
		}

		for(int i = 0; i <= y.length(); i++){
			y[i] = tolower(y[i]);
		}		

		for(int i = 0; i <= x.length(); i++){
			if(x[i] < y[i]){
				return -1;
			}
			else if(x[i] > y[i]){
				return 1;
			}
			else{
				continue;
			}
		}
		return 0;
	}

	listNode * findSpot(string x){
		listNode *spot;
		spot = listHead;
		// cout << listHead->data;
		while(spot->nextNode != NULL){
			if(compare(spot->nextNode->data, x) == -1){
				spot = spot->nextNode;
			}
			else if(compare(spot->nextNode->data, x) == 1){	
				return spot;
			}
			else{
				spot->nextNode->count++;
				break;
			}
		}
		return spot;
	}
	
	void listInsert(listNode *spot, listNode *newNode){
		newNode->nextNode = spot->nextNode;
		spot->nextNode = newNode;
	}

	void outputToFile(ofstream& fout, string x){
		listNode *spot;
		spot = listHead;
		fout << "Inserting data: (" << x << ")\n";
		fout << "listHead ";

		while(spot->nextNode!=NULL){
			fout << "-->(" << spot->data << ", " << spot->nextNode->data << ")";
			spot = spot->nextNode;
		}
		fout << "\n" << "\n";
	}

	void printToConsole(){
		listNode *spot;
		spot = listHead;
		cout << spot->data;
		while(spot->nextNode != NULL){
			cout << "( " <<  spot->nextNode->data << " )-->";
			spot = spot->nextNode;
		}
	}

	bool exists(string x){
		listNode * spot;
		spot = listHead;
		while(spot->nextNode != NULL){
			if(spot->nextNode->data == x){
				return true;
			}
			spot = spot->nextNode;
		}
		return false;
	}

	~linkedList(){}
};

int main(int argc, char** argv){
	
	ifstream commonWordInputFile;
	commonWordInputFile.open(argv[1]);
	
	linkedList * commonWordList = new linkedList();
	
	ofstream commonWordListOutputFile;
	commonWordListOutputFile.open(argv[3]);
	
	string x;

	while(commonWordInputFile >> x){
		listNode * spot;
		spot = commonWordList->findSpot(x);
		if(commonWordList->exists(x)){
			continue;
		}
		listNode * newNode = new listNode(x);
		commonWordList->listInsert(spot, newNode);
		commonWordList->outputToFile(commonWordListOutputFile, x);
		
	}
	commonWordListOutputFile.close();

	linkedList * englishTextData = new linkedList();

	ifstream englishTextDataInputFile;
	englishTextDataInputFile.open(argv[2]);

	ofstream englishTextDataOutputFile;
	englishTextDataOutputFile.open(argv[4]);
}
