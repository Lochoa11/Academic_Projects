 /* Class AVLNode */
public class AVLNode
 {    
     AVLNode left, right;
     int data;
     int height;
     Car thisCar;
 
     /* Constructor */
     public AVLNode()
     {
         left = null;
         right = null;
         data = 0;
         height = 0;
     }
     /* Constructor */
     public AVLNode(int n)
     {
         left = null;
         right = null;
         data = n;
         height = 0;
     }
     public AVLNode(Car c){
        left = null;
        right = null;
        data = 0;
        height = 0;
        thisCar = c;
     }     
 }
 