#include <iostream>
#include <cmath>
using namespace std;


bool Ok (int crossBoard [], int col){
	int adjacent[8][5] = {

		{-1, -1, -1, -1, -1}, 
		{ 0, -1, -1, -1, -1}, 
		{ 0,  1, -1, -1, -1}, 
		{ 0,  2, -1, -1, -1},
		{ 1,  2, -1, -1, -1},
		{ 1,  2,  3,  4, -1},
		{ 2,  3,  5, -1, -1},
		{ 4,  5,  6, -1, -1},

	};

	for(int i = 0; i < col; i++){//if the number was used before
		for(int j = col; j > i; j--){
			if(crossBoard[j] == crossBoard[i])
				return false;
		}
	}



	for(int i = 0; i <= col; i++){
		for(int j = 0; j <= 4; j++){
			if(adjacent[i][j] < 0){
				break;
			}

			if(abs(crossBoard[adjacent[i][j]] - crossBoard[i]) == 1) return false;

			}
	}
/*
	for(int i = 0; i <= col; i++){
		for (int j = ; j > 0; j++){

		}

	}
*/
	return true;


}

void backTrack(int &col){
	col--;
	if(col == -1) exit(1);
}

void print(int crossBoard [], int counter){
	cout << "  " << crossBoard[1] << " " << crossBoard[4];
	cout << endl;
	cout << crossBoard[0] << " "<< crossBoard[2] << " "<< crossBoard[5] << " "<< crossBoard[7] << " ";
	cout << endl;
	cout << "  " << crossBoard[3] << " " << crossBoard[6] << endl;

	/*	
		for(int r = 0; r < 3; r++){
			for(int c = 0; c < 4; c++){
				if (r != 1){

					if((r == 0) && (c == 1)){
						cout << " " << crossBoard[1] << " ";
					}
					else if((r == 0) && (c == 2)){
						cout << crossBoard[4] << " ";
					}
					else if((r == 2) && (c == 1)){
						cout << " " << crossBoard[3] << " ";
					}
					else if((r == 2) && (c == 2)){
						cout << crossBoard[6] << " ";
					}
					else {
						cout << " ";					}
				}
				else {
					cout << crossBoard[c] << " "; 
				}
			}
			cout << endl;
		}

	for(int i = 0; i < 8; i++){
		cout << crossBoard[i] << " ";
	}*/
	cout << "Solution: " << counter << endl;
	
}

int main(){
	int crossBoard[8];
	int c = 0, counter = 0;
	bool fromBackTrack = false;

		while(true){

			while(c < 8){

				if(!fromBackTrack){
					crossBoard[c] = -1;
				}

				fromBackTrack = false;

				while(crossBoard[c] < 8){
					crossBoard[c]++;
					if(crossBoard[c] == 8){
						backTrack(c);

						continue;
					}
					if(Ok(crossBoard, c)){
						c++;
						break;
					}
				}
			}
		
		print(crossBoard, ++counter);
		cout << endl;
		backTrack(c);
		fromBackTrack = true;
		
		}

	return 0;
}

/*
while (c >= 0){
	if (new col){
		q[c] = 0;
		new col = false;
	}
	else q[c]++;
	if(c == 8)
		print;
		c--;
	if(ok){
		if(q[c] == 7) c--;
		else{
			c++;
			new col
		}
	}
}
*/