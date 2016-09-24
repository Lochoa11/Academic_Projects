#include <iostream>
using namespace std;

bool equivalent(int a[], int b[], int c);

int main(){
        int a[5] = {3,4,5,1,2};
        int b[5] = {2,3,4,5,1};
        int c = 5;

        if(equivalent(a, b, c)){
                cout << "it is equivalent" << endl;
        }
        else
                cout << "it is not equivalent" << endl;

        return 0;
}

bool equivalent(int a[], int b[], int c){
        for(int i = 0; i < c; i++){
                if(a[i] == b[i]){
                        if(i == c-1){
                                return true;
                        }
                        else continue;
                }
                else{
                        int temp = a[0];
                        for(int j = 0; j < c; j++){
                                if (j == c-1)
                                        a[j] = temp;
                                else
                                        a[j] = a[j+1];
                        }
ß                }
        }
        return false;

}
