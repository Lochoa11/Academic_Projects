import java.io.*;

public class P1P2{
	public static void main(String []arg) throws Exception{

	    BufferedReader keyboard;
	    String inputLine;

	    keyboard = new BufferedReader(new InputStreamReader(System.in));

	    inputLine = keyboard.readLine();

	    for (int i = 0; i < inputLine.length(); i++) {
	    	if (i == 1) {
	    		continue;
	    	}
	    	System.out.print(inputLine.charAt(i));
	    	if (i == inputLine.length()-1) {
	    		System.out.println("");	
	    	}
	    }

	}
}