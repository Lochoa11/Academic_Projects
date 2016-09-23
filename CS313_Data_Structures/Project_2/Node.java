 
/*  Class Node  */
class Node
{
    protected Node next, prev;
    protected Car thisCar;
    protected int data;


    /* Constructor */
    public Node()
    {
        next = null;
        prev = null;
        data = 0;
    }
    /* Constructor */
    public Node(Car c, Node n, Node p)
    {
        thisCar = c;
        next = n;
        prev = p;
    }

    /* Function to set link to next node */
    public void setLinkNext(Node n)
    {
        next = n;
    }
    /* Function to set link to previous node */
    public void setLinkPrev(Node p)
    {
        prev = p;
    }    
    /* Funtion to get link to next node */
    public Node getLinkNext()
    {
        return next;
    }
    /* Function to get link to previous node */
    public Node getLinkPrev()
    {
        return prev;
    }
    /* Function to set data to node */
    public void setData(int d)
    {
        data = d;
    }
    //Function to get data from node 
    public int getData()
    {
        return data;
    }

    public String getMake(){
        return thisCar.getMake();
    }
    public String getModel(){
        return thisCar.getModel();
    }
    public int getYear(){
        return thisCar.getYear();
    }
    public String getColor(){
        return thisCar.getColor();
    }
}