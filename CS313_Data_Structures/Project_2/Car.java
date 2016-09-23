
public class Car implements Comparable <Car>{
	private String make;
	private String model;
	private int year;
	private Color color;

	private int carCounter;


	public Car(){
		make = "";
		model = "";
		year = 0;
		color = new Color("");
		carCounter = 0;
	}

	public Car(String x, String y, int z, Color c, int i){
		make = x;
		model = y;
		year = z;
		color = c;
		carCounter = i;
	}

	public Car(String x, String y, int z, String c, int i){
		make = x;
		model = y;
		year = z;
		color = new Color(c);
		carCounter = i;

	}

	public int compareTo(Car other){
		if(!make.equals(other.make)){
			return make.compareTo(other.make);
		}
		else if(!model.equals(other.model)){
			return model.compareTo(other.model);
		}
		else if(year != other.year){
			return year - other.year;
		}
		else{
			return color.compareTo(other.color);
		}

	}

	public boolean equals(Object o){
		if(o instanceof Car){
			Car other = (Car) o;
			return make.equals(other.make) && 
				   model.equals(other.model) && 
				   year == other.year && 
				   color.equals(other);
		}
		else{
			return false;
		}
	}

	public int hashCode(){
		return carCounter % 3;
	}                     //^ this number determines if the arrays begin to chain.
						  //lower number to create collisions
						  //********************************************************
						  //********************************************************
						  //********************************************************
						  //********************************************************
						  //********************************************************


	public String getMake(){
		return make;
	}

	public String getModel(){
		return model;
	}

	public int getYear(){
		return year;
	}

	public String getColor(){
		return color.getColor();
	}

}

