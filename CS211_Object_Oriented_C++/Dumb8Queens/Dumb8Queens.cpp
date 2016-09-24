#include <iostream> 
using namespace std;

bool ok(int board [][8], int r, int c){

	for(int col = c-1; col > 0; col--){
		int row = 0;
		while(board[row][col] != 1){
			row++;
		}
		for(int i = col-1; i >= 0; i--){
			if(board[row][i] == 1){
				return false;
			}
		}//row test

		for(int i = 1; (row-i) >= 0 && (col-i) >= 0; i++){
			if(board[row - i][col - i] == 1) return false;
			
		}//up diagnal test (going to the left)

		for(int i = 1; (row + i) < r && (col-i) >= 0; i++){
			if(board[row + i][col - i] == 1) return false;
		}// down diagnal test

	}

	return true;
}

void print(int board [][8], int r, int c, int count){

	for(int i = 0; i < r; i++){
		for(int j = 0; j < c; j++){
			if(board[i][j] == 1) cout << board[i][j] << " ";
			else cout << board[i][j] << " ";
		}
		cout << endl;
	}


	cout << "This is solution # " << count << endl;
	cout << endl;
}

int main( ){ 
	
	int board[8][8]={0};
	int count = 0;
	
	for(int i0 =0; i0 <8; i0 ++){
		for(int i1 =0; i1 <8; i1 ++){ 
			for(int i2 =0; i2 <8; i2 ++){
				for(int i3 =0; i3 <8; i3 ++){ 
					for(int i4 =0; i4 <8; i4 ++){
						for(int i5 =0; i5 <8; i5 ++){
							for(int i6 =0; i6 <8; i6 ++){ 
								for(int i7 =0; i7 <8; i7 ++){ 
									// use the indices of the loops to set a configuration in array board ...
									// if this configuration is conflict-free, print the count and the board if(ok(board)) print(board, ++count);
									// reset the board for the next configuration ...
									board[i0][0] = 1;
									board[i1][1] = 1;
									board[i2][2] = 1;
									board[i3][3] = 1;
									board[i4][4] = 1;
									board[i5][5] = 1;
									board[i6][6] = 1;
									board[i7][7] = 1;


									if(ok(board, 8, 8)){ 
										print(board, 8, 8, ++count);
									}
									board[i0][0] = 0;
									board[i1][1] = 0;
									board[i2][2] = 0;
									board[i3][3] = 0;
									board[i4][4] = 0;
									board[i5][5] = 0;
									board[i6][6] = 0;
									board[i7][7] = 0;
								}
							}
						}
					}
				}
			}
		}
	}							

	return 0;
}