        // 1) Your solution must be in a file called P1P1.java .

        // 2) Do not edit any of the lines before the line that says 
        //    "Replace this comment with your solution."

        // 3) Your program must print only five lines from the given home page,
        //    and must print them in reverse order.  Do not add any extraneous
        //    println statements.  Do not modify the lines before printing them.
        //    The program skeleton we've given you prints a prompt before reading
        //    the String; don't change this prompt.

/* P1P1.java. */

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**  A class that provides a main function to read five lines of a commercial
 *   Web page and print them in reverse order, given the name of a company.
 */

class P1P1 {

  /** Prompts the user for the name X of a company (a single string), opens
   *  the Web site corresponding to www.X.com, and prints the first five lines
   *  of the Web page in reverse order.
   *  @param arg is not used.
   *  @exception Exception thrown if there are any problems parsing the 
   *             user's input or opening the connection.
   */
  public static void main(String[] arg) throws Exception {

    BufferedReader keyboard;
    String inputLine;

    keyboard = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Please enter the name of a company (without spaces): ");
    System.out.flush();        /* Make sure the line is printed immediately. */
    inputLine = keyboard.readLine();

    /* Replace this comment with your solution.  */

    String [] fiveLines = new String[5];//array to store a line of the website
    String website = "https://www." + inputLine + ".gov"; //concatination for a www.website.com format
    URL u = new URL(website);//URL instance with the website string passed in to get to the web site
    InputStream ins = u.openStream();//inputstream instance linked URL to get information from the website
    InputStreamReader isr = new InputStreamReader(ins); //instance of an inputstreamreadercomposes raw data into characters 
    BufferedReader webPage = new BufferedReader(isr); //instance of Bufferedreader called webPage

    for (int i = 0; i < 5; i++) {
        fiveLines[i] = webPage.readLine();
    //     System.out.println("********************This is " + i + " **********************************");
    //     System.out.println(fiveLines[i]);
    }

    for (int i = 4; i >=0 ; i--) {
        System.out.println(fiveLines[i]);
        System.out.println("/n***********************************************************************");
    }
  }
}
