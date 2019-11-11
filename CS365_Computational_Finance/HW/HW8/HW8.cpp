#include <iostream>
#include <cmath>

using namespace std;

int binomial_simple(double S,
	double K,
	double r,
	double q,
	double sigma,
	double T,
	double t_0,
	bool call,
	bool American,
	int n,
	double & V
);

int main(){
	double S = 100.0;
	double K = 100.0;
	double r = 0.1;
	double q = 0.0;
	double sigma = 0.5;
	double T = 0.3;
	double t_0 = 0;
	bool call = false;
	bool American = true;
	int n = 3;
	double V = 0.0;
	int result = binomial_simple(S, K, r, q, sigma, T, t_0, call, American, n, V);
	cout << "result = " << result << endl;
	cout << "V      = " << V << endl;
}

int binomial_simple(
	double S,
	double K,
	double r,
	double q,
	double sigma,
	double T,
	double t_0,
	bool call,
	bool American,
	int n,
	double & V){

	if(n < 1) return 1;
	if(S <= 0) return 1;
	if(T <= t_0) return 1;
	if(sigma <= 0.0) return 1;

	// delta t
	double dt = (T-t_0)/double(n);
	// cout << "dt = " << dt << endl;
	double df = exp(-r*dt);
	// cout << "df = " << df << endl;
	double growth = exp((r-q)*dt);
	// cout << "growth = " << growth << endl;
	double u = exp(sigma*sqrt(dt));
	// cout << "u = " << u << endl;
	double d = 1.0/u;
	// cout << "d = " << d << endl;
	double p_prob = (growth - d)/(u-d);
	// cout << "p_prob = " << p_prob << endl;
	double q_prob = 1.0 - p_prob;
	// cout << "q_prob = " << q_prob << endl;

	if(p_prob < 0.0) return 1;
	if(p_prob > 1.0) return 1;

	// allocate memory
	double **stock_nodes = new double*[n+1];
	double **option_nodes = new double*[n+1];

	double * S_tmp;
	double * V_tmp;

	for (int i = 0; i <= n; ++i) {
	
		stock_nodes[i] = new double[n+1];
		option_nodes[i] = new double[n+1];
		S_tmp = stock_nodes[i];
		V_tmp = option_nodes[i];
	
		for (int j = 0; j <= n; ++j) {
			S_tmp[j] = 0;
			V_tmp[j] = 0;
		}
	}


	S_tmp = stock_nodes[0];
	S_tmp[0] = S;

	// stock prices
	for(int i = 1; i <= n; ++i){
		double * prev = stock_nodes[i-1];
		S_tmp = stock_nodes[i];
		S_tmp[0] = prev[0] * d;
		for(int j = 1; j <= n; ++j){
			S_tmp[j] = S_tmp[j-1] * u * u;
		}
	}

	// print stock price
	// for (int i = 0; i <= n; ++i){
	// 	cout << "row " << i << endl;
	// 	for (int j = 0; j <= n; ++j){
	// 		cout << stock_nodes[i][j] << " " << endl;
	// 	}
	// 	cout << endl;
	// }

	// terminal payoff
	int i = n;
	S_tmp = stock_nodes[i];
	V_tmp = option_nodes[i];

	for (int j = 0; j <= n; ++j){
		
		double intrinsic = 0;
		// cout << "K " << K << " S_tmp[" << j << "] = " << S_tmp[j] << endl;
		if(call){
			if (S_tmp[j] > K){
				intrinsic = S_tmp[j] - K;
				// cout << "intrinsic = " << intrinsic << endl;
			}
		}
		else{//put
			if (S_tmp[j] < K){
				intrinsic = K - S_tmp[j];
				// cout << "intrinsic = " << intrinsic << endl;
			}
		}
		V_tmp[j] = intrinsic;
	}
	// // print option valuation 
	// for (int i = 0; i <= n; ++i){
	// 	cout << "row " << i << endl;
	// 	for (int j = 0; j <= n; ++j){
	// 		// cout << "stock_nodes  = " << stock_nodes[i][j] << " " << endl;
	// 		cout << "option_nodes = " << option_nodes[i][j] << " " << endl;
	// 	}
	// 	cout << endl;
	// }

	for (i = n-1; i >= 0; --i){
		S_tmp = stock_nodes[i];
		V_tmp = option_nodes[i];
		double * V_next = option_nodes[i+1];
		for (int j = 0; j <= i ; ++j){
			V_tmp[j] = df *(p_prob * V_next[j+1] + q_prob * V_next[j]);
			if(American){
				double intrinsic = 0.0;
				if(call){
					if((S_tmp[j]-K) > 0) intrinsic = S_tmp[j]-K;					
				}
				else{
					if((K-S_tmp[j]) > 0) intrinsic = K-S_tmp[j];           ;					
				}
				cout << "V_tmp[" << j << "] = " << V_tmp[j] << endl;
				cout << "intrinsic = " << intrinsic << endl << endl;
				if(V_tmp[j] < intrinsic){
					V_tmp[j] = intrinsic;
				}
			}
		}
	}

		
	// print terminal payoff 
	for (int i = 0; i <= n; ++i){
		cout << "row " << i << endl;
		for (int j = 0; j <= n; ++j){
			// cout << "stock_nodes  = " << stock_nodes[i][j] << " " << endl;
			cout << "option_nodes = " << option_nodes[i][j] << " " << endl;
		}
		cout << endl;
	}

	// option fair value
	i = 0;
	V_tmp = option_nodes[i];
	V = V_tmp[0];

	// deallocate memory
	for (i = 0; i <= n; ++i) {
		delete [] stock_nodes[i];
		delete [] option_nodes[i];
	}
	delete [] stock_nodes;
	delete [] option_nodes;

	return 0;
}
