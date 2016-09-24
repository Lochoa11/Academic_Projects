#include <iostream>
using namespace std;
/* run this program using the console pauser or add your own getch, system("pause") or input loop */

int main() {
	int Q[8][8] = {0}, r, c=0, i;
	Q[0][0] = 1;
////Next Colum////	
	NC: c++;
	if(c == 8)
	{
		goto print;
	}	
	r = -1;
////Next Row////
	NR: r++;
	if(r == 8)
	{
		goto backtrack;
	}
////row test////
	for(i=0; i<c; i++)
	{
		if(Q[r][i] == 1)
			goto NR;
	}
////up dim test////
	for(i=1; (r-i)>=0 && (c-i)>=0; i++)
	{
		if(Q[r-i][c-i] == 1)
			goto NR;
	}	
////down dim test////
	for(i=1; (r+i)<=7 && (c-i)>=0; i++)
	{
		if(Q[r+i][c-i] == 1)
			goto NR;
	}
	Q[r][c] = 1;
	goto NC;
////backtrack////
backtrack:
	c--;
	if(c == -1)
		return 0;
	//	cout<<"Can not find the queens!";
	else
	{
		r = 0;
	//	c = c-1;
		while(Q[r][c] != 1)
		{
			r++;
		}
			Q[r][c] = 0;
			goto NR;
	}
////print the result////	
print:
	cout << "solution:________________"<<endl;
	cout <<endl;
	for(int e = 0; e < 8; e++)
	{
		for(int f = 0; f < 8; f++)
		{
			cout<<Q[e][f]<<" ";
		}
			cout<<endl;
	}
	goto backtrack;
	return 0;
}
