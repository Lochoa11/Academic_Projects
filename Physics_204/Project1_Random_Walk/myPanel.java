import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class myPanel extends JPanel {
	int x[];
	int y[];
	int sizeOfPossibleDots;
	int lastIndex;

	public myPanel(int sizeOfArray){
		this.x = new int[sizeOfArray];
		this.y = new int[sizeOfArray];
		lastIndex = 0;
		sizeOfPossibleDots = sizeOfArray;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(Color.WHITE);

		g.setColor(Color.BLACK);
		for(int i = 0; i < lastIndex; i++){
			g.fillOval( x[i], y[i], 5, 5);
		}
	}
	
	public void setNextXY(int x, int y){
		if(lastIndex == sizeOfPossibleDots) return;
		this.x[lastIndex] = x;
		this.y[lastIndex] = y;
		lastIndex++;
	}
}