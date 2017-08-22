import java.util.*;
import java.io.*;

class Main{

	public static void main(String[] args) {
		
		int k = 14;

		System.out.println("Enter message");
		Scanner reader = new Scanner(System.in);
		String x = "";
		while(true){
			x = reader.nextLine();
			x = encrypt(x, k);
			System.out.println("Encypted: " + x);
	
			x = decrypt(x, k);
			System.out.println("Decrypted: " + x);
			System.out.println("Enter message");
		}
	}

	public static String encrypt(String x, int k){

		x = x.toLowerCase();
		String newString = "";

		for(int i = 0; i < x.length(); i++){
			char letter = x.charAt(i);
			if(letter == ' '){
				newString = newString + " ";
				continue;
			}
			int numValue = (int) letter;
			numValue = numValue - 97;
			numValue = (numValue + k) % 26;
			char newletter = (char) (97+numValue);
			newString = newString + "" +newletter;
		}
		return newString;
	}

	public static String decrypt(String x, int k){
		
		String oldString = "";
		
		for(int i = 0; i < x.length(); i++){
			char letter = x.charAt(i);

			if(letter == ' '){
				oldString = oldString + " ";
				continue;
			}
			int numValue = (int) letter;
			numValue = numValue - 97;
			numValue = (numValue - k) % 26;
			while(numValue < 0){
				numValue += 26;
			}
			letter = (char) (97+numValue);
			oldString = oldString + "" + letter;
		}
		return oldString;
	}

}