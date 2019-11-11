#include <iostream>
#include <math.h>
#include <iomanip>
using namespace std;
double future_value(double F0, double t0, double t1, double r);
int df_and_r(double F0, double F1, double t0, double t1, double & df, double & r);
void calculate_bond_tables();
void calculate_discount_factor();
double calculate(double F, double y, int n);
double calculate_spot_rate(double t, double df);

int main(){

	int x;

	double F0;
	double F1;
	double t0;
	double t1;
	double df;
	double r;
	
	

	while(true){
		cout << "HW 1 "<< endl << endl;
		cout << "Enter 1 for 1.1"<< endl;
		cout << "Enter 2 for 1.2"<< endl;
		cout << "Enter 3 for 1.3"<< endl;
		cout << "Enter 4 for 1.4"<< endl << endl;
		cout << "Enter 0 to stop" << endl;
		cin >> x;
		cout << endl;	
		if(x == 0){
			break;
		}
		else if(x == 1){
			cout << "Calculate future cash flow" << endl;
			cout << "Enter today's cash flow: " << endl;
			cin >> F0;
			cout << "Enter today's time: " << endl;
			cin >> t0;
			cout << "Enter future time: " << endl;
			cin >> t1;
			cout << "Enter interest rate: " << endl;
			cin >> r;
			F1 = future_value(F0, t0, t1, r);
			cout << "Future cash flow is: " <<  F1 << endl;		
		}
		else if(x == 2){			
			cout << "Inverse calculation" << endl;
			cout << "Enter today's cash flow" << endl;
			cin >> F0;
			cout << endl;
			cout << "Enter future cash flow" << endl;
			cin >> F1;
			cout << endl;
			cout << "Enter today's time: " << endl;
			cin >> t0;
			cout << endl;
			cout << "Enter future time: " << endl;
			cin >> t1;
			cout << endl;
			cout << "Result: " << df_and_r(F0, F1, t0, t1, df, r) << endl;
			cout << "df = " << df << endl;
			cout << "r  = " << r << endl;
			cout << endl << endl;			
		}
		else if (x == 3){
			cout.precision(5);
			cout << "Table of yield and bond prices F = 100 and c[1-4] = 4" << endl;
			cout << "yield bond price" << endl << endl;
			calculate_bond_tables();
		}
		else if(x == 4){
			calculate_discount_factor();
		}
	}
	return 0;
}

// 1.1
double future_value(double F0, double t0, double t1, double r){
	double r_decimal = 0.01*r;
	double F1 = F0*exp(r_decimal*(t1-t0));
	return F1;
}

// 1.2 
int df_and_r(double F0, double F1, double t0, double t1, double & df, double & r){
	if (t1-t0 == 0.0) {
		df = 0;
		r = 0;
		return -1;
	}
	if ((F0 < 0.0) || (F1 < 0.0)) {
	// *** you figure it out ***
		df = 0;
		r = 0;
		return -2;
	}
	// *** you have to write the rest ***
	df = F0/F1;

	r = (-100)*(log(df)/(t1 - t0));

	return 0;
}

// 1.3
void calculate_bond_tables(){
	
	// table 1
	/*	------------
		|y | bond  |
		-----------|
		|0 | 108.00|
		|2 | 103.90|
		|4 | 100.00|
		|6 |  96.28|
		|8 |  92.74|
		-----------
		As the percentage of the yield increases 
		the price of the bond decreases.

		
		yield bounds
		yLow  = 4.0
		yHigh = 2.0
		yMid  = 3.0		
		Bond(yMid) == 101.93

		Since the Bond(yMid) is greater than the market value 
		yHigh needs to get set to yMid

		Adjusted yield bounds		
		yLow  = 4.0
		yHigh = 3.0
		yMid  = 3.5
		Adjusted Bond(yMid) = 100.96
		
	
	*/
	double bondTable1[5];
	
	int i = 0;	
	int y = 0;
	int c = 4;

	cout << "Table 1" << endl;
	cout << "y | B" << endl; 
	cout << "-----" << endl;
	while(i < 5){		
		for(int n = 1; n <= 4; n++){
			if(n == 4){
				bondTable1[i] += ((100+(c/2))/pow((1+(y*0.01/2)),n));
			}
			else{
				bondTable1[i] += ((c/2)/pow((1+(y*0.01/2)),n));
			}
		}
		cout << y << " | " << bondTable1[i] << endl;
		i++;
		y+=2;
	}

	cout << endl;
	
	
	//bond target
	double bMkt  = 100.5;
	double yLow  = 4;
	double yHigh = 2;	
	double yMid = (yLow + yHigh)/2;
	
	cout << "The market price of the bond = "<< bMkt<< endl;
	cout << "yLow, yHigh, yMid is: " << endl;
	cout << "yLow = " << yLow << endl;
	cout << "yHigh = " << yHigh << endl;
	cout << "yMid = "<< yMid << endl;

	// set y Low and High
	double bondYMid = 0;

	for(int n = 1; n <= 4; n++){
		if(n == 4){
			bondYMid += ((100+(c/2))/pow((1+(yMid*0.01/2)),n));
		}
		else{
			bondYMid += ((c/2)/pow((1+(yMid*0.01/2)),n));
		}
	}


	cout << "Bond(yMid) = " << bondYMid << endl;

	if (bondYMid < bMkt){
		yLow = yMid;
	}
	else{
		yHigh = yMid;
	}
	cout << endl;
	cout << "Adjusted yLow, yHigh, and yMid" << endl;
	cout << "yLow  = " << yLow << endl;
	cout << "yHigh = " << yHigh << endl;
	yMid = (yHigh + yLow)/2;
	cout << "yMid  = " << yMid << endl;

	bondYMid = 0;


	for(int n = 1; n <= 4; n++){
		if(n == 4){
			bondYMid += ((100+(c/2))/pow((1+(yMid*0.01/2)),n));
		}
		else{
			bondYMid += ((c/2)/pow((1+(yMid*0.01/2)),n));
		}
	}


	cout << "Adjusted Bond(yMid) = " << bondYMid << endl << endl;
	
	// //table 2
	/*
	------------
	|y | Bond  |
	-----------|
	|1 | 103.93|
	|3 |  99.92|
	|5 |  96.12|
	|7 |  92.49|
	|9 |  89.04|
	-----------

	Again as yield increases price of bond decreases
		
	yield bounds
	yLow2  = 3 .0
	yHigh2 = 1.0
	yMid2  = 2.0
	Bond(yMid2) = 101.90

	Since the Bond(yMid2) is greater than the market value 
	yHigh needs to get set to yMid2
	
	Adjusted yield bounds
	yLow2  = 3.0
	yHigh2 = 2.0
	yMid2  = 2.5
	Adjusted Bond(yMid2) = 100.91

	*/
	double bondTable2[5];

	i = 0;
	y = 1;
	c = 1;
	cout << "Table 2" << endl;
	cout << "y | B" << endl;
	cout << "-----" << endl;

	while(i < 5){
		for (int n = 1; n <= 4; n++){
			double numerator;
			double denominator;
			double yDivideBy2;
			double onePlus;
			double power;
			double temp;
			bondTable2[i] = 0.0;
			if(n == 4){
				// cout << "bond table2[" << i << "] = " << bondTable2[i] << endl;
				numerator = 100 + (c/2.0);
				yDivideBy2 = y * 0.01/2;
				onePlus = 1 + yDivideBy2;
				power = pow(onePlus, n);
				denominator = power;
				temp = numerator/denominator;
				bondTable2[i] = bondTable2[i] + temp;
				cout << "bondTable2[" << i << "] = " << bondTable2[i] << endl;
			}
			else{
				// cout << "bond table2[" << i << "] = " << bondTable2[i] << endl;
				numerator = (c/2.0);
				yDivideBy2 = y * 0.01/2;
				onePlus = 1 + yDivideBy2;
				power = pow(onePlus, n);
				denominator = power;
				temp = numerator/denominator;
				bondTable2[i] = bondTable2[i] + temp;
				cout << "bondTable2[" << i << "] = " << bondTable2[i] << endl;
			}
			c += 2;
		}
		cout << y << " | " << bondTable2[i] << endl;
		c = 1;
		i++;
		y += 2;
	}

	cout << endl;

	//bond target
	double bMkt2  = 100;
	double yLow2  = 3;
	double yHigh2 = 1;	
	double yMid2 = (yLow2 + yHigh2)/2;
	
	cout << "The market price of the bond = "<< bMkt2 << endl;
	cout << "yLow2, yHigh2, yMid2 is: " << endl;
	cout << "yLow2 = " << yLow2 << endl;
	cout << "yHigh2 = " << yHigh2 << endl;
	cout << "yMid2 = "<< yMid2 << endl;

	
	double bondYMid2 = 0;

	for(int n = 1, c = 1; n <= 4; n++, c+=2){
		if(n == 4){
			bondYMid2 += ((100+(c/2))/pow((1+(yMid2 * 0.01/2)),n));
		}
		else{
			bondYMid2 += ((c/2)/pow((1+(yMid2 * 0.01/2)),n));
		}
	}

	cout << "Bond(yMid2) = " << bondYMid2 << endl;

	if (bondYMid2 < bMkt2){
		yLow2 = yMid2;
	}
	else{
		yHigh2 = yMid2;
	}

	cout << endl;
	cout << "Adjusted yLow2, yHigh2, and yMid2" << endl;
	cout << "yLow2  = " << yLow2 << endl;
	cout << "yHigh2 = " << yHigh2 << endl;
	yMid2 = (yHigh2 + yLow2)/2;
	cout << "yMid2  = " << yMid2 << endl;

	bondYMid2 = 0;


	for(int n = 1, c = 1; n <= 4; n++, c += 2){
		if(n == 4){
			bondYMid2 += ((100+(c/2))/pow((1+(yMid2 * 0.01/2)),n));
		}
		else{
			bondYMid2 += ((c/2)/pow((1+(yMid2 * 0.01/2)),n));
		}
	}

	cout << "Adjusted Bond(yMid2) = " << bondYMid2 << endl;
}

// 1.4

void calculate_discount_factor(){

	cout.precision(4);
	double F = 100;
	double y0_5 = 4.0;

	/*
	Discount factor for 0.5 years: 0.9804
	Discount factor for 1.0 years: 0.9593
	Discount factor for 1.5 years: 0.9409

	spot rate 1: 3.96
	spot rate 2: 4.16
	spot rate 3: 4.06
	*/

	double discountFactor1 = 1/(1+(y0_5*0.01/2));
	cout << "Discount factor for 0.5 years: " << discountFactor1 << endl;
	
	double y1_0 = 4.2;
	
	double discountFactor2 = (1-(y1_0*0.01/2*discountFactor1))/(1+(y1_0*0.01/2));
	cout << "Discount factor for 1.0 years: " << discountFactor2 << endl;

	double y1_5 = 4.1;
	
	double discountFactor3 = (1-(y1_5*0.01/2*discountFactor1)-(y1_5*0.01/2*discountFactor2))/(1+(y1_5*0.01/2));
	cout << "Discount factor for 1.5 years: " << discountFactor3 << endl;

	cout.precision(3);
	cout << endl;

	double spot_rate1 = (-100)*(log(discountFactor1)/0.5);
	cout << "spot rate 1: " << spot_rate1 << endl;

	double spot_rate2 = (-100)*(log(discountFactor2)/1.0);
	cout << "spot rate 2: " << spot_rate2 << endl;

	double spot_rate3 = (-100)*(log(discountFactor3)/1.5);
	cout << "spot rate 3: " << spot_rate3 << endl;
	cout << endl << endl;
}

double calculate_spot_rate(double t, double df){
	return (-1)*(log(df)/t);
}