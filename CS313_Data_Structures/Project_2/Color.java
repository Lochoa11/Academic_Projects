public class Color implements Comparable <Color>{
	private String color ;

	public Color(){
		color = "";
	}
	public Color(String c){
		color = c;
	}

	public void setEqualTo(Color c){
		color = c.color;
	}

	public String getColor(){
		return color;
	}

	public int compareTo(Color c){//compareTo function for Color class
		if(color.length() > c.color.length()){//if this color is longer than other color
			for(int i = 0; i < c.color.length(); i++){//loop untill other color.length because it is smaller
				int x = (int) color.charAt(i);//this compares the letters using the ASCII numbers
				int y = (int) c.color.charAt(i);//this compares the letters using the ASCII numbers
				     if(x < y)     {return-1;}//this color is before other color
				else if(x > y)     {return 1;}//other color is before this color
				else{continue;}
			}
			//this for loop compares the letters using the ASCII numbers
			//if this color's Char is smaller return -1
			//if other color Char is smaller return 1
			//loop will finish when it reaches the end of c.color.length(the smaller length)
			//this.color will contain more letters meaning it is bigger/should be sorted after other car.
			return 1;
		}
		else if (color.length() < c.color.length()) {// if other color is longer than this color
			for(int i = 0; i < color.length(); i++){//loop untill this color.length because it is smaller
				int x = (int) color.charAt(i);//sets x to the ASCII value of the strings char
				int y = (int) c.color.charAt(i);//sets y to the ASCII value of the strings char
				     if(x < y)     {return-1;}//this color is before other color
				else if(x > y)     {return 1;}//other color is before this color
				else{continue;}
			}
			//this for loop compares the letters using the ASCII numbers
			//if this color's Char is smaller return -1
			//if other color Char is smaller return 1
			//loop will finish when it reaches the end of color.length(the smaller length)
			//c.color (other color) will contain more letters meaning other color is bigger/should be sorted after other car.
			return -1;//this color is sorted before other color.
		}
		else{
			for(int i = 0; i < color.length(); i++){//loop untill this color.length which it is equal to c.color.length
				int x = (int) color.charAt(i);//sets x to the ASCII value of the strings char
				int y = (int) c.color.charAt(i);//sets y to the ASCII value of the strings char
				     if(x < y)     {return-1;}//this color is before other color
				else if(x > y)     {return 1;}//other color is before this color
				else{continue;}
			}
			//this for loop compares the letters using the ASCII numbers
			//if this color's Char is smaller return -1
			//if other color Char is smaller return 1
			//loop will finish when it reaches the end of color.length(the smaller length)
			//c.color (other color) will contain more letters meaning other color is bigger/should be sorted after other car.
			return 0;//this color is sorted before other color.
		}	
	}

}