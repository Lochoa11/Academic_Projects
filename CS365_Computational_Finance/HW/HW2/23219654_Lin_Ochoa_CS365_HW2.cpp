#include <iostream>
#include <math.h>
#include <vector>

using namespace std;

class Bond;


class Bond{
public:
	Bond(double T, double F, double c = 0, int freq = 2){
		Face = F;
		if(Face < 0){
			Face = 0;
		}

		cpn_freq = freq;
		if(cpn_freq < 1){
			cpn_freq = 1;
		}

		const double tol = 1.0e-6;

		num_coupons = int(cpn_freq * T + tol);
		if(num_coupons < 0){
			num_coupons = 0;
		}

		T_maturity = num_coupons /cpn_freq;
		if (num_coupons > 0) {
			coupons.resize(num_coupons, c);
		}
	}
	~Bond(){
		coupons.clear();
		num_coupons = 0;
	};
	// public methods
	/*
		FV_duration, yield, and FairValue are const because these functions should never reassign values only calculate.
	*/
	int set_coupons(std::vector<double> & c){
		if(c.size() < num_coupons)return 1;

		int i = 0; 
		while(i < c.size()){
			if(c[i] <= 0.0){
				coupons[i] = 0.0;
			}
			else{
				coupons[i] = c[i];
			}
		}
		return 0;
	}

	int FV_duration(double t0, double y, double &B, double &Macaulay_duration, double &modified_duration) const{
		B = 0;
		Macaulay_duration = 0;
		modified_duration = 0;

		cout << "Entered FV_duration T_maturity = " << T_maturity << endl;
		if(num_coupons <= 0 || t0 >= T_maturity){
			return 1;
		}
		
		double yDecimal = y * 0.01;
		const double tol = 1.0e-6;
		double ti;
		int i = 0;
		while(i < num_coupons){
			
			ti = double(i)/double(cpn_freq);

			if(i == coupons.size() && ti >= t0+tol){
				B += (Face + (coupons[i]/cpn_freq))/(pow((1 + (yDecimal/cpn_freq)),(ti-t0)));
			}
			else if(ti >= t0+tol){
				B += (coupons[i]/cpn_freq)/(pow((1 + (yDecimal/cpn_freq)),(ti-t0)));
			}
			i++;
		}

		i = 0; 

		while(i < num_coupons){
			if(i == coupons.size() && ti >= t0+tol){
				Macaulay_duration += (ti-t0)*(Face + (coupons[i]/cpn_freq))/(pow((1 + (yDecimal/cpn_freq)),(ti-t0)));
			}
			else if(ti >= t0+tol){
				Macaulay_duration += (ti-t0)*(coupons[i]/cpn_freq)/(pow((1 + (yDecimal/cpn_freq)),(ti-t0)));
			}
			i++;
		}
		Macaulay_duration = Macaulay_duration/B;

		modified_duration = Macaulay_duration/(1+(y/cpn_freq));

		return 0;
	}
	
	int yield(double B_target, double tol, int max_iter, double t0, double & y, int & num_iter) const{
		y = 0;
		num_iter = 0;
		if(B_target < 0.0 || num_coupons <= 0 || t0 < T_maturity){
			return 1;
		}
		double y_low = 0.0;
		double B_y_low = FairValue(t0, y_low);
		double diff_B_y_low = B_y_low - B_target;
		if (fabs(diff_B_y_low) <= tol){
			y = y_low;
			return 0;
		}
		double y_High = 100.0;
		double B_y_High = FairValue(t0, y_High);
		double diff_B_y_High = B_y_High - B_target;
		if(fabs(diff_B_y_High) <= tol){
			y = y_High;
			return 0;
		}
		if((diff_B_y_High * diff_B_y_low) > 0){
			y = 0;
			return 1;
		}
		double B;
		double diff_B;
		for (num_iter = 1; num_iter < max_iter; ++num_iter){
			y = (y_low + y_High)/2;
			B = FairValue(t0, y);
			diff_B = B - B_target;
			if(fabs(diff_B)){
				return 0;
			}
			if((diff_B * diff_B_y_low) > 0.0){
				y_low = y;
			}
			else{
				y_High = y;
			}
			if(abs(y_High - y_low) < tol){
				return 0;
			}
		}
		y = 0;
		return 1;
	}
	
	double FairValue(double t0, double y) const {
		double B = 0;
		double Macaulay_duration = 0;
		double modified_duration = 0;
		FV_duration(t0, y, B, Macaulay_duration, modified_duration);
		return B;
	}
	
	double maturity()const{ return T_maturity; }

private:
	// data
	double Face;
	double T_maturity;
	int cpn_freq;
	int num_coupons;
	std::vector<double> coupons;
};

int main(){
	std::vector<double> v;
	Bond * bond = new Bond(2, 100, 4.0, 2);
	// bond->set_coupons(v);
	// std::cout << bond->FairValue(0, 2) << std::endl;
}