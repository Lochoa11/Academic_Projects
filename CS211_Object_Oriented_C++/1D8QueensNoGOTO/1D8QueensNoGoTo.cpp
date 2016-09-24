#include <iostream>
#include <cstdlib>
#include <cmath>

using namespace std;

bool ok (int board[], int c){

	for(int i = 0; i < c; i++){
		if(board[c] == board[i] || (c - i) == abs(board[c] - board[i])){ 
			return false;
		}
	}

	return true;

}

void backTrack(int &c){
	
	c--;
	if(c == -1) exit(1);

}

void print(int board[], int counter){

	cout << endl;

	for(int i = 0; i < 8; i++){
		for(int j = 0; j < 8; j++){
			if(board[j] == i){
				cout << 1 << " ";
			}
			else{
				cout << 0 << " ";
			}
		}
		cout << endl;
	}

	cout << "Solution # " << counter << endl;

}



int main (){
	int board[8], c = 1, counter = 0;
	bool fromBackTrack = false;

	board[0] = 0;

	while(true){

		while(c < 8){

			if(!fromBackTrack){
				board[c] = -1;
			}

			fromBackTrack = false;

			while(board[c] < 8){
				board[c]++;
				if(board[c] == 8){
					backTrack(c);
					continue;
				}
				if(ok(board, c)){
					c++;
					break;
				}
			}
		}		
		print(board, ++counter);
		cout << endl;
		backTrack(c);
		fromBackTrack = true;
	}
	return 0;
}