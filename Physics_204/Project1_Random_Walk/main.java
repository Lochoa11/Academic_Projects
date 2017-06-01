import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.io.*;

public class main{

	
	public static void main(String [] args){
		Random rand = new Random(System.currentTimeMillis());

		JFrame f = new JFrame("FrameDemo");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600, 600);
		int x = 300;
		int y = 300;
		myPanel mp = new myPanel(10000);

		mp.setNextXY(x, y);
		f.add(mp);
		f.setVisible(true);

		// System.out.println("Hello 1");
		try{

			PrintWriter outX = new PrintWriter(new FileWriter( args[0],true));
			PrintWriter outY = new PrintWriter(new FileWriter( args[1],true));

			for(int i = 0; i < 10000; i++){
				int num = rand.nextInt() %1000;
				if(num < 0) num *= -1;
				Double num2 = (double)num/1000;

				int j = 300 - x;
				int k = 300 - y;
				if(j < 0) j*=-1;
				if(k < 0) k*=-1;
				outX.println(j + ", ");
				outY.println(k + ", ");

				//numbers from 0 - 1/4
				if(num2 < .250){
					x-=5;
					mp.setNextXY(x, y);
					if(x <= 0){
						x+=5;
						continue;
					}
				}
				// numbers from 1/4 - 1/2
				else if(num2 < .5){
					x+=5;
					mp.setNextXY(x, y);
					if(x >=600){
						x-=5;
						continue;					
					}
				}
				//numbers from 1/2 - 3/4
				else if(num2 < .75){
					y-=5;
					mp.setNextXY(x, y);
					if(y <= 0){
						y+=5;
						continue;
					}
				}
				//numbers from 3/4 - 1
				else{
					y+=5;
					mp.setNextXY(x, y);
					if(y >= 600){
						y-=5;
						continue;
					}
				}

				try{
					Thread.sleep(5);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				f.repaint();
				System.out.println(i);
			}
			outX.close();
			outY.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}