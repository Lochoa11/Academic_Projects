

public class Main{
	public static void main(String[]args){
		Gnome [] gnome = new Gnome[11];
		SkyBlue skyblue = new SkyBlue();
		
		for(int i = 0; i < 11; i++){
			gnome[i] = new Gnome(i, skyblue);
			gnome[i].start();
		}
		skyblue.start();
	}
}