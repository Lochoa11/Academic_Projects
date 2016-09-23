 /* Class BSTNode */
public class BSTNode
 {
     BSTNode left, right;
     Car thisCar;
 
     /* Constructor */
     public BSTNode()
     {
         left = null;
         right = null;
         thisCar = new Car();
     }
     /* Constructor */
     public BSTNode(Car c)
     {
         left = null;
         right = null;
         thisCar = c;
     }
     /* Function to set left node */
     public void setLeft(BSTNode n)
     {
         left = n;
     }
     /* Function to set right node */ 
     public void setRight(BSTNode n)
     {
         right = n;
     }
     /* Function to get left node */
     public BSTNode getLeft()
     {
         return left;
     }
     /* Function to get right node */
     public BSTNode getRight()
     {
         return right;
     }
     /* Function to set data to node */
     public void setCar(Car c)
     {
         thisCar = c;
     }
     /* Function to get data from node */
     public Car getCar()
     {
         return thisCar;
     }     
 }