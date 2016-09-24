

public class Main extends Thread{
	
	public static long time = System.currentTimeMillis();
	
	public static void main(String[] args) throws Exception{
		
		boolean [] tableSize = new boolean[11];
		
		//creating princess and gnomes
		SkyBlue skyBlue = new SkyBlue(time);
		Gnome [] gnome = new Gnome[11];
		
		//starting princess
		skyBlue.start();

		//starting gnomes
		for(int i = 0; i < 11; i++){
			gnome[i] = new Gnome(i, time, skyBlue);
			gnome[i].start();
			sleep(5);//this makes sure each gnome gets 
					 //assigned the right number beacause
					 //the constructor is not fast enough
		}
	
//		first approach
//		tried to pass the references so that each 
//		instance knew who to terminate after
		
//		for(int i = 0; i <= 8; i++){
//			if(i % 2 == 0){
//				gnome[i].setPreviouslyTerminatingGnome(gnome[i+2]);
//			}
//		}
//		gnome[10].setPreviouslyTerminatingGnome(gnome[9]);
//		for(int i = 9; i >=3;i--){
//			if(i % 2 == 1){
//				gnome[i].setPreviouslyTerminatingGnome(gnome[i-2]);
//			}
//		}
		
		
		//executes when all gnomes are ready to terminate
		while(Gnome.lastOneReady != true){
			if(Gnome.lastOneReady == true){
				break;
			}
		}
		
		for(int i = 1; i <= 10; i++){
			if(i % 2 == 1){
				gnome[i].allowedToLeave = true;
				sleep(10);
			}
		}
		sleep(10);
		for(int i = 10; i >= 0; i--){
			if(i % 2 == 0){
				gnome[i].allowedToLeave = true;
				sleep(10);
			}
		}
		
		skyBlue.allowedToTerminate = true;
	}
		
	public static void msg(String m) {
		 System.out.println("["+(System.currentTimeMillis()-time)+"] " +":"+m);
	}

}
