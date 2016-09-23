import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lin on 4/27/14.
 */
public class TTTGUI extends JFrame{

//	TTTLogic logic = new TTTLogic();

	public TTTGUI(){

		super("Lin's Tic Tac Toe Game");

		setSize(500, 500);
		setLocation(100, 100);
		final Container TTTContainer =  this.getContentPane();
		final JLabel Question = new JLabel("Would you like to play Tic Tac Toe?", SwingConstants.CENTER);
		final JButton Yes = new JButton("Yes");
		final JButton No = new JButton("No");
		final JPanel yesno = new JPanel(new GridLayout(1,2));
		final JPanel Grid = new JPanel(new GridLayout(3,3));
		final JLabel xWon = new JLabel("Congrats X wins", SwingConstants.CENTER);
		final JLabel oWon = new JLabel("Congrats O wins", SwingConstants.CENTER);
		final JLabel tie = new JLabel("oh poo, no one wins :/ ", SwingConstants.CENTER);
		final JButton button1 = new JButton("1");
		final JButton button2 = new JButton("2");
		final JButton button3 = new JButton("3");
		final JButton button4 = new JButton("4");
		final JButton button5 = new JButton("5");
		final JButton button6 = new JButton("6");
		final JButton button7 = new JButton("7");
		final JButton button8 = new JButton("8");
		final JButton button9 = new JButton("9");
		final JLabel xturn = new JLabel("It is X's turn: ", SwingConstants.CENTER);
		final JLabel oturn = new JLabel("It is O's turn: ", SwingConstants.CENTER);

		xWon.setVisible(false);
		oWon.setVisible(false);

		TTTContainer.add(Question, BorderLayout.NORTH);
		yesno.add(Yes, BorderLayout.SOUTH);
		yesno.add(No, BorderLayout.SOUTH);
		TTTContainer.add(yesno, BorderLayout.SOUTH);

		Grid.add(button1);
		Grid.add(button2);
		Grid.add(button3);
		Grid.add(button4);
		Grid.add(button5);
		Grid.add(button6);
		Grid.add(button7);
		Grid.add(button8);
		Grid.add(button9);
		Grid.setVisible(false);
		TTTContainer.add(Grid, BorderLayout.CENTER);


		Yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				yesno.setVisible(false);
				Grid.setVisible(true);
				TTTLogic.restartGame();
				button1.setText("1");
				button2.setText("2");
				button3.setText("3");
				button4.setText("4");
				button5.setText("5");
				button6.setText("6");
				button7.setText("7");
				button8.setText("8");
				button9.setText("9");
				button1.setEnabled(true);
				button2.setEnabled(true);
				button3.setEnabled(true);
				button4.setEnabled(true);
				button5.setEnabled(true);
				button6.setEnabled(true);
				button7.setEnabled(true);
				button8.setEnabled(true);
				button9.setEnabled(true);
				xWon.setVisible(false);
				oWon.setVisible(false);
				tie.setVisible(false);
				Question.setVisible(false);
				xturn.setVisible(true);
				TTTContainer.add(xturn, BorderLayout.NORTH);
			}
		});


		No.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(TTTLogic.getCounter()%2 == 0){
					TTTLogic.setXBoard(0);
					button1.setText("X");
					button1.setEnabled(false);
					xturn.setVisible(false);
					oturn.setVisible(true);
					TTTContainer.add(oturn, BorderLayout.NORTH);
					if(TTTLogic.checkForXWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						xWon.setVisible(true);
						oturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(xWon, BorderLayout.CENTER);
					}
				}
				else{
					TTTLogic.setOBoard(0);
					button1.setText("O");
					button1.setEnabled(false);
					oturn.setVisible(false);
					xturn.setVisible(true);
					TTTContainer.add(xturn, BorderLayout.NORTH);
					if(TTTLogic.checkForOWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						oWon.setVisible(true);
						xturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(oWon, BorderLayout.CENTER);
					}
				}
				if(TTTLogic.checkForTie()){
					tie.setVisible(true);
					TTTLogic.restartGame();
					TTTContainer.add(tie, BorderLayout.CENTER);
					Grid.setVisible(false);
					yesno.setVisible(true);
					xWon.setVisible(false);
					oWon.setVisible(false);
					xturn.setVisible(false);
					oturn.setVisible(false);
					Question.setVisible(true);
				}
			}
		});

		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(TTTLogic.getCounter()%2 == 0){
					TTTLogic.setXBoard(1);
					button2.setText("X");
					button2.setEnabled(false);
					xturn.setVisible(false);
					oturn.setVisible(true);
					TTTContainer.add(oturn, BorderLayout.NORTH);
					if(TTTLogic.checkForXWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						xWon.setVisible(true);
						oturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(xWon, BorderLayout.CENTER);
					}
				}
				else{
					TTTLogic.setOBoard(1);
					button2.setText("O");
					button2.setEnabled(false);
					oturn.setVisible(false);
					xturn.setVisible(true);
					TTTContainer.add(xturn, BorderLayout.NORTH);
					if(TTTLogic.checkForOWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						oWon.setVisible(true);
						xturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(oWon, BorderLayout.CENTER);
					}
				}
				if(TTTLogic.checkForTie()){
					tie.setVisible(true);
					TTTLogic.restartGame();
					TTTContainer.add(tie, BorderLayout.CENTER);
					Grid.setVisible(false);
					yesno.setVisible(true);
					xWon.setVisible(false);
					oWon.setVisible(false);
					xturn.setVisible(false);
					oturn.setVisible(false);
					Question.setVisible(true);
				}
			}
		});

		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(TTTLogic.getCounter()%2 == 0){
					TTTLogic.setXBoard(2);
					button3.setText("X");
					button3.setEnabled(false);
					xturn.setVisible(false);
					oturn.setVisible(true);
					TTTContainer.add(oturn, BorderLayout.NORTH);
					if(TTTLogic.checkForXWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						xWon.setVisible(true);
						oturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(xWon, BorderLayout.CENTER);
					}
				}
				else{
					TTTLogic.setOBoard(2);
					button3.setText("O");
					button3.setEnabled(false);
					oturn.setVisible(false);
					xturn.setVisible(true);
					TTTContainer.add(xturn, BorderLayout.NORTH);
					if(TTTLogic.checkForOWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						oWon.setVisible(true);
						xturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(oWon, BorderLayout.CENTER);
					}
				}
				if(TTTLogic.checkForTie()){
					tie.setVisible(true);
					TTTLogic.restartGame();
					TTTContainer.add(tie, BorderLayout.CENTER);
					Grid.setVisible(false);
					yesno.setVisible(true);
					xWon.setVisible(false);
					oWon.setVisible(false);
					xturn.setVisible(false);
					oturn.setVisible(false);
					Question.setVisible(true);
				}
			}
		});

		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(TTTLogic.getCounter()%2 == 0){
					TTTLogic.setXBoard(3);
					button4.setText("X");
					button4.setEnabled(false);
					xturn.setVisible(false);
					oturn.setVisible(true);
					TTTContainer.add(oturn, BorderLayout.NORTH);
					if(TTTLogic.checkForXWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						xWon.setVisible(true);
						oturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(xWon, BorderLayout.CENTER);
					}
				}
				else{
					TTTLogic.setOBoard(3);
					button4.setText("O");
					button4.setEnabled(false);
					oturn.setVisible(false);
					xturn.setVisible(true);
					TTTContainer.add(xturn, BorderLayout.NORTH);
					if(TTTLogic.checkForOWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						oWon.setVisible(true);
						xturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(oWon, BorderLayout.CENTER);
					}
				}
				if(TTTLogic.checkForTie()){
					tie.setVisible(true);
					TTTLogic.restartGame();
					TTTContainer.add(tie, BorderLayout.CENTER);
					Grid.setVisible(false);
					yesno.setVisible(true);
					xWon.setVisible(false);
					oWon.setVisible(false);
					xturn.setVisible(false);
					oturn.setVisible(false);
					Question.setVisible(true);
				}
			}
		});

		button5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(TTTLogic.getCounter()%2 == 0){
					TTTLogic.setXBoard(4);
					button5.setText("X");
					button5.setEnabled(false);
					xturn.setVisible(false);
					oturn.setVisible(true);
					TTTContainer.add(oturn, BorderLayout.NORTH);
					if(TTTLogic.checkForXWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						xWon.setVisible(true);
						oturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(xWon, BorderLayout.CENTER);
					}
				}
				else{
					TTTLogic.setOBoard(4);
					button5.setText("O");
					button5.setEnabled(false);
					oturn.setVisible(false);
					xturn.setVisible(true);
					TTTContainer.add(xturn, BorderLayout.NORTH);
					if(TTTLogic.checkForOWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						oWon.setVisible(true);
						xturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(oWon, BorderLayout.CENTER);
					}
				}
				if(TTTLogic.checkForTie()){
					tie.setVisible(true);
					TTTLogic.restartGame();
					TTTContainer.add(tie, BorderLayout.CENTER);
					Grid.setVisible(false);
					yesno.setVisible(true);
					xWon.setVisible(false);
					oWon.setVisible(false);
					xturn.setVisible(false);
					oturn.setVisible(false);
					Question.setVisible(true);
				}
			}
		});

		button6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(TTTLogic.getCounter()%2 == 0){
					TTTLogic.setXBoard(5);
					button6.setText("X");
					button6.setEnabled(false);
					xturn.setVisible(false);
					oturn.setVisible(true);
					TTTContainer.add(oturn, BorderLayout.NORTH);
					if(TTTLogic.checkForXWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						xWon.setVisible(true);
						oturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(xWon, BorderLayout.CENTER);
					}
				}
				else{
					TTTLogic.setOBoard(5);
					button6.setText("O");
					button6.setEnabled(false);
					oturn.setVisible(false);
					xturn.setVisible(true);
					TTTContainer.add(xturn, BorderLayout.NORTH);
					if(TTTLogic.checkForOWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						oWon.setVisible(true);
						xturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(oWon, BorderLayout.CENTER);
					}
				}
				if(TTTLogic.checkForTie()){
					tie.setVisible(true);
					TTTLogic.restartGame();
					TTTContainer.add(tie, BorderLayout.CENTER);
					Grid.setVisible(false);
					yesno.setVisible(true);
					xWon.setVisible(false);
					oWon.setVisible(false);
					xturn.setVisible(false);
					oturn.setVisible(false);
					Question.setVisible(true);
				}
			}
		});

		button7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(TTTLogic.getCounter()%2 == 0){
					TTTLogic.setXBoard(6);
					button7.setText("X");
					button7.setEnabled(false);
					xturn.setVisible(false);
					oturn.setVisible(true);
					TTTContainer.add(oturn, BorderLayout.NORTH);
					if(TTTLogic.checkForXWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						xWon.setVisible(true);
						oturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(xWon, BorderLayout.CENTER);
					}
				}
				else{
					TTTLogic.setOBoard(6);
					button7.setText("O");
					button7.setEnabled(false);
					oturn.setVisible(false);
					xturn.setVisible(true);
					TTTContainer.add(xturn, BorderLayout.NORTH);
					if(TTTLogic.checkForOWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						oWon.setVisible(true);
						xturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(oWon, BorderLayout.CENTER);
					}
				}
				if(TTTLogic.checkForTie()){
					tie.setVisible(true);
					TTTLogic.restartGame();
					TTTContainer.add(tie, BorderLayout.CENTER);
					Grid.setVisible(false);
					yesno.setVisible(true);
					xWon.setVisible(false);
					oWon.setVisible(false);
					xturn.setVisible(false);
					oturn.setVisible(false);
					Question.setVisible(true);
				}
			}
		});

		button8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(TTTLogic.getCounter()%2 == 0){
					TTTLogic.setXBoard(7);
					button8.setText("X");
					button8.setEnabled(false);
					xturn.setVisible(false);
					oturn.setVisible(true);
					TTTContainer.add(oturn, BorderLayout.NORTH);
					if(TTTLogic.checkForXWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						xWon.setVisible(true);
						oturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(xWon, BorderLayout.CENTER);
					}
				}
				else{
					TTTLogic.setOBoard(7);
					button8.setText("O");
					button8.setEnabled(false);
					oturn.setVisible(false);
					xturn.setVisible(true);
					TTTContainer.add(xturn, BorderLayout.NORTH);
					if(TTTLogic.checkForOWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						oWon.setVisible(true);
						xturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(oWon, BorderLayout.CENTER);
					}
				}
				if(TTTLogic.checkForTie()){
					tie.setVisible(true);
					TTTLogic.restartGame();
					TTTContainer.add(tie, BorderLayout.CENTER);
					Grid.setVisible(false);
					yesno.setVisible(true);
					xWon.setVisible(false);
					oWon.setVisible(false);
					xturn.setVisible(false);
					oturn.setVisible(false);
					Question.setVisible(true);
				}
			}
		});

		button9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(TTTLogic.getCounter()%2 == 0){
					TTTLogic.setXBoard(8);
					button9.setText("X");
					button9.setEnabled(false);
					xturn.setVisible(false);
					oturn.setVisible(true);
					TTTContainer.add(oturn, BorderLayout.NORTH);
					if(TTTLogic.checkForXWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						xWon.setVisible(true);
						oturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(xWon, BorderLayout.CENTER);
					}
				}
				else{
					TTTLogic.setOBoard(8);
					button9.setText("O");
					button9.setEnabled(false);
					oturn.setVisible(false);
					xturn.setVisible(true);
					TTTContainer.add(xturn, BorderLayout.NORTH);
					if(TTTLogic.checkForOWin()){
						TTTLogic.restartGame();
						Grid.setVisible(false);
						yesno.setVisible(true);
						oWon.setVisible(true);
						xturn.setVisible(false);
						Question.setVisible(true);
						TTTContainer.add(oWon, BorderLayout.CENTER);
					}
				}
				if(TTTLogic.checkForTie()){
					tie.setVisible(true);
					TTTLogic.restartGame();
					TTTContainer.add(tie, BorderLayout.CENTER);
					Grid.setVisible(false);
					yesno.setVisible(true);
					xWon.setVisible(false);
					oWon.setVisible(false);
					xturn.setVisible(false);
					oturn.setVisible(false);
					Question.setVisible(true);
				}

			}
		});


		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

}
