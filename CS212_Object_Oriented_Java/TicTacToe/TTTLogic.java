/**
 * Created by Lin on 4/27/14.
 */
public class TTTLogic {
	static int counter = 0;
	static char [] board = new char[9];

	public static boolean checkForTie(){
		if(counter > 8){return true;}
		else {return false;}
	}
	public static void setXBoard(int i){
		board[i] = 'X';
		counter++;
	}

	public static void setOBoard(int i){
		board[i] = 'O';
		counter++;
	}

	public static boolean checkForXWin(){

		//------cross checker---------
		if((board[0] == 'X') && (board[1] == 'X') && (board[2] == 'X')){
			return true;
		}//top cross
		if((board[3] == 'X') && (board[4] == 'X') && (board[5] == 'X')){
			return true;
		}//middle cross
		if((board[6] == 'X') && (board[7] == 'X') && (board[8] == 'X')){
			return true;
		}//bottom cross

		//------vertical checker------
		if((board[0] == 'X') && (board[3] == 'X') && (board[6] == 'X')){
			return true;
		}//left vertical
		if((board[1] == 'X') && (board[4] == 'X') && (board[7] == 'X')){
			return true;
		}//middle vertical
		if((board[2] == 'X') && (board[5] == 'X') && (board[8] == 'X')){
			return true;
		}//right cross

		//------Diagonal--------------
		if((board[0] == 'X') && (board[4] == 'X') && (board[8] == 'X')){
			return true;
		}//top left to bottom right
		if((board[2] == 'X') && (board[4] == 'X') && (board[6] == 'X')){
			return true;
		}//top right to bottom left

		//if I don't find X win then return false;
		return false;
	}

	public static boolean checkForOWin(){

		//------cross checker---------
		if((board[0] == 'O') && (board[1] == 'O') && (board[2] == 'O')){
			return true;
		}//top cross
		if((board[3] == 'O') && (board[4] == 'O') && (board[5] == 'O')){
			return true;
		}//middle cross
		if((board[6] == 'O') && (board[7] == 'O') && (board[8] == 'O')){
			return true;
		}//bottom cross

		//------vertical checker------
		if((board[0] == 'O') && (board[3] == 'O') && (board[6] == 'O')){
			return true;
		}//left vertical
		if((board[1] == 'O') && (board[4] == 'O') && (board[7] == 'O')){
			return true;
		}//middle vertical
		if((board[2] == 'O') && (board[5] == 'O') && (board[8] == 'O')){
			return true;
		}//right vertical

		//------Diagonal--------------
		if((board[0] == 'O') && (board[4] == 'O') && (board[8] == 'O')){
			return true;
		}//top left to bottom right
		if((board[2] == 'O') && (board[4] == 'O') && (board[6] == 'O')){
			return true;
		}//top right to bottom left

		//if I don't find O win then return false;
		return false;
	}

	public static void restartGame(){
		counter = 0;
		for(int i = 0; i < 9; i++){
			board[i] = 'A';
		}
	}

	public static int getCounter(){return counter;}


}
