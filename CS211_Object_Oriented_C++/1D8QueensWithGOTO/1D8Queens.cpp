
#include <iostream>
#include <cstdlib>
using namespace std;

int main()
{
    int q[8], c = 0, i ,count = 0;
    q[0] = 0;
NC: c++;
    if (c == 8) goto print;
    q[c] = -1;
NR:q[c]++;
    if (q[c]==8) goto backtrack;
    for (i = 0; i < c; i ++)
       if((q[i]== q[c]) || ((c-i) == (abs(q[c]-q[i])))) goto NR;
    goto NC;
backtrack: c--;
        if (c == -1){
           cout << count << endl;
           return 0;
         }
    goto NR;
print:
    count ++;
    for (i = 0; i < 8; i++)
       cout << q[i];
    cout << endl;
    goto backtrack;
}          
