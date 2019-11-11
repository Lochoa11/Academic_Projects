#include <iostream>
#include <cmath> 
using namespace std;
int main(){
	
	double S = 0;
	double K = 100.0;
	double r = 0.05;
	double q = 0.01;
	double sigma = 0.5;
	double T = 1.0;
	double t0 = 0.0;
	double FV_Am_put = 0;
	double FV_Eur_put = 0;
	double FV_Am_call = 0;
	double FV_Eur_call = 0;
	int n = 100;
	double dS = 0.1;
	int imax = 2000;

	for (int i = 1; i <= imax; ++i) {
		S = i*dS;
		binomial_simple(S, K, r, q, sigma, T, t0, false, true, n, FV_Am_put);
		binomial_simple(S, K, r, q, sigma, T, t0, false, false, n, FV_Eur_put);
		binomial_simple(S, K, r, q, sigma, T, t0, true, true, n, FV_Am_call);
		binomial_simple(S, K, r, q, sigma, T, t0, true, false, n, FV_Eur_call);
		// print output to file
		outfile << std::setw(16) << S << " ";
		outfile << std::setw(16) << FV_Am_put << " ";
		outfile << std::setw(16) << FV_Eur_put << " ";
		outfile << std::setw(16) << FV_Am_call << " ";
		outfile << std::setw(16) << FV_Eur_call << " ";
		outfile << std::endl;
	}
}