#include <iostream>
#include <stdlib.h>
#include <time.h>
using namespace std;

void p (int k [], int n);

int main(){
	int k[15];
	for(int i = 0; i < 15; i++){
		k[i] = rand() % 15;
	}
	cout << "The middle number is: " << endl;
	cout << k[15/2] << endl;
	cout << "The order is: " << endl;
	for(int i = 0; i < 15; i++){
		cout << k[i] << " ";
	}
	cout << endl;
	
	p(k, 15);
	
	cout << "New order is " << endl;

	for(int i = 0; i < 15; i++){
		cout << k[i] << " ";
	}
	return 0;
}

void p(int k[], int n){
	bool passedMiddle = false;
	int m = k[n/2];
	int temp;
	for(int i = 0; i < n; i++){
		if(k[i] == m) passedMiddle = true;
		else if(k[i] > m && passedMiddle == false){
			temp = k[i];
			for(int j = i; j < n-i; j++){
				if(j == n-i-1) k[j] = temp;
				else k[j] = k[j+1];
			}
			i--;
			continue;
		}
		else if(k[i] < m && passedMiddle == true){
			temp = k[i];
			for(int j = i; j >= 0; j--){
				if(j == 0) k[j] = temp;
				else k[j] = k[j-1];
			}
		}
		else if(k[i] < m){
			continue;
		}	
	}
}