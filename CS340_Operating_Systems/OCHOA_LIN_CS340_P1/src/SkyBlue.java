
public class SkyBlue extends Thread{

	public static long time = System.currentTimeMillis();
	public volatile static Gnome [] gnome = new Gnome[11];
	public volatile static int gnomeLineUpBookMark = 0;
	public volatile static boolean allowedToTerminate = false;
	public volatile static Gnome terminateBehindThisGnome;
	SkyBlue(long time){
		this.time = time;
		setName("SkyBlue");
	}
	public void run(){
		
		msg(" starts to clean");
		
		try {
			msg(" goes to sleep for 1000");
			sleep(1000);		
		} catch (InterruptedException e) {
			msg(" is interupted and awake now");
		}
	
		
		//introduces 
		for(int i = 0; i < 11; i++){
			gnome[i].introduce(i);
		}
		
		//allows introduction to happen then sets table
		try {
			sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		//tells the gnomes that the table is ready
		gnome[0].tableReady = true;
		msg(" sets up the table now table is ready!");
		
		while(true){
			if(Gnome.dinnerHasEnded == true){break;}
		}
		msg(" goes to sleep after dinner");
		
		//after dinner go to sleep and busy wake till last gnome wakes 
		while(true){
			if(Gnome.lastOneAwake == true){break;}
		}
		
		//sleep for formatting purposes 
		try {
			sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg(" last Gnome wakes SkyBlue up");
			
		//give kissess allows time for gnomes to get kisses
		try {
			sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < 11; i++){
			msg(" kisses " + gnome[i].getName() + " for a diamond");
			gnome[i].gaveDiamond = true;
		}

		
		//wait to be terminated
		while(true){
			if(allowedToTerminate == true){break;}
		}

		//first approach
//		tried to use .join()
		
//		try {
//			terminateBehindThisGnome.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		msg(" is terminated");
	}
	
	public void setPreviouslyTerminatingGnome(Gnome gnome){
		terminateBehindThisGnome = gnome;
	}
	public void setGnomeOrderForKisses(Gnome gnome){
		this.gnome[gnomeLineUpBookMark] = gnome;
		gnomeLineUpBookMark++;
	}
	public void accessToGnomes(Gnome gnome, int assigned_num){
		this.gnome[assigned_num] = gnome;
	}
	public void initializeGnomes(Gnome gnome, int assigned_number){
		this.gnome[assigned_number] = gnome;
	}
	public static void msg(String m) {
		 System.out.println("["+(System.currentTimeMillis()-time)+"] "+ currentThread().getName() +":"+m);
	}
}
