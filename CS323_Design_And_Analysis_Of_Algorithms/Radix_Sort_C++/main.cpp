#include <iostream>
#include <fstream>
using namespace std;

class listNode{
	friend class linkedListStack;
public:
	listNode * nextNode;
	string data;
	
	listNode(string data){
		this->data = data;
		nextNode = NULL;
	}

	~listNode(){}
};




class linkedListStack{
	friend class radixSort;
	listNode * top;
public:
	linkedListStack(){
		top = NULL;
	}

	void push(listNode * newNode){
		newNode->nextNode = top;
		top = newNode;
	}

	listNode * pop(){
		listNode * temp = top;
		top = top->nextNode;
		return temp;
	}

	bool isEmpty(){
		if(top == NULL) return true;
		return false;
	}

	void printStack(){
		listNode * temp;
		temp = top;
		cout << "top of stack -> ";
		while(temp != NULL){
			cout << "(" << temp->data << ") -> ";
			temp = temp->nextNode;
		}
		cout << "(NULL)" << endl;
	}
};




class linkedListQueue{
public:
	friend class radixSort;
	listNode * head;
	listNode * tail;
	listNode * dummyNode;
	linkedListQueue(){
		dummyNode = new listNode("dummyNode");
		head = dummyNode;
		tail = dummyNode;
	}

	void addTail(listNode * temp){
		if(isEmpty()){
			temp->nextNode = head;
			head = temp;
		}
		temp->nextNode = tail;
		tail = temp;
	}

	listNode * deleteHead(){
		listNode * temp;
		listNode * temp2;
		temp = head;
		temp2 = tail;
		if (temp == temp2){
			tail = tail->nextNode;
			head = tail;
		}
		else{
			while(temp != temp2 || temp2->nextNode->data != "dummyNode"){
				if(temp2->nextNode == temp){break;}
				temp2 = temp2->nextNode;
			}
			temp2->nextNode = temp2->nextNode->nextNode;
			head = temp2;
		}
		return temp;
	}

	bool isEmpty(){
		if(tail->data == "dummyNode"){
			return true;
		}
		return false;
	}

	void printQueue(){
		listNode * temp;
		temp = tail;
		cout << "(tail) -> ";
		while(temp != NULL){
			cout << "(" << temp->data << ") -> ";
			temp = temp->nextNode;
		}
		cout << "(Head)" << endl;
	}
};





class radixSort{

public: 
		int currentDigit;
		int currentTable;
		int currentQueue;
		int previousTable;
		int longestStringLength;
		
		linkedListQueue **hashTable;
	radixSort(int maxLength){
		hashTable = new linkedListQueue*[2];
		hashTable[0] = new linkedListQueue[256];
		hashTable[1] = new linkedListQueue[256];
		currentDigit = 0;
		longestStringLength = maxLength;
		
	}
	
	void padString(listNode * temp, int maxLength){
		int count = 0;
		for (int i = 0; i < temp->data.length(); i++){
			count++;
		}
		string newTempString;
		for (int i = 0; i < maxLength; i++){
			if(i < count) {
				newTempString = newTempString + temp->data[i];
			}
			else{
				newTempString = newTempString + "_";
			}
		}
		temp->data = newTempString;
		
	}
	char getVal(listNode * temp){
		
		return temp->data[longestStringLength - currentDigit - 1];
	}
	int hashIndex(char val){
		return (int)val;
	}

	void addTail(listNode * temp, int hashIndex){
		hashTable[currentTable][hashIndex].addTail(temp);
		
	}
	void printTable(){
		for(int i = 0; i < 256; i++){
			if(hashTable[currentTable][i].tail->data != "dummyNode"){
				cout << "Table [" << currentTable << "][" << i << "]: ";
				hashTable[currentTable][i].printQueue();
			}
		}
	}
};




int main(int argc, char** argv){

	ifstream inputFile;
	inputFile.open(argv[1]);

	linkedListStack * myStack = new linkedListStack();
	string temp;
	string longestString;
	int maxStringLength = 0;


	while(inputFile >> temp){
		listNode * newNode = new listNode(temp);
		myStack->push(newNode);
		int x = temp.length();
		if(x > maxStringLength){
			maxStringLength = x;
			longestString = temp;
		}
	}

	cout << "Step 3 print stack: " << endl << endl;

	myStack->printStack();

	cout << endl;

	radixSort * myRadixSort = new radixSort(maxStringLength);


	while(!myStack->isEmpty()){

		listNode *temp = myStack->pop();

		myRadixSort->padString(temp, maxStringLength);
		char val = myRadixSort->getVal(temp);
		int hashIndex = myRadixSort->hashIndex(val);
		
		myRadixSort->addTail(temp, hashIndex);
	}

	cout << endl << "Step 8 print table(before sort): " << endl << endl;
	myRadixSort->printTable();


	myRadixSort->currentDigit++;
	myRadixSort->currentTable = 1;
	myRadixSort->previousTable = 0;
	myRadixSort->currentQueue = 0;


	while(myRadixSort->currentDigit <= maxStringLength){

		while(myRadixSort->currentQueue < 256){

			while(!myRadixSort->hashTable[myRadixSort->previousTable][myRadixSort->currentQueue].isEmpty()){
			
				listNode * temp = myRadixSort->hashTable[myRadixSort->previousTable][myRadixSort->currentQueue].deleteHead();

				char val = myRadixSort->getVal(temp);
				int hashIndex = myRadixSort->hashIndex(val);

				myRadixSort->addTail(temp, hashIndex);

			}

			myRadixSort->currentQueue++;

		}
		
		int temp = myRadixSort->currentTable;
		myRadixSort->currentTable = myRadixSort->previousTable;
		myRadixSort->previousTable = temp;
		myRadixSort->currentDigit++;
		myRadixSort->currentQueue = 0;
	}
	int t = myRadixSort->currentTable;
	myRadixSort->currentTable = myRadixSort->previousTable;
	myRadixSort->previousTable = t;
	myRadixSort->currentDigit++;
	myRadixSort->currentQueue = 0;
	cout << endl << "Step 16 Print currentTable (after sort): " << endl << endl;
	myRadixSort->printTable();

}

