import java.util.*;
public class Gnome extends Thread{
	
	public volatile static int counter = 1;//counter for door
	public volatile static int counter2 = 0;//counter for bedtime
	public volatile static int counter3 = 0;//counter for last one to wake
	public volatile static int counter4 = 0;//counter for gnomes ready to leave
	public static long time = System.currentTimeMillis();
	public volatile int assigned_number;
	public static SkyBlue skyBlue;
	public volatile static boolean [] introducedToSB = new boolean[11];
	public volatile static boolean allowedToEnter = false;
	public volatile static boolean tableReady = false;
	public volatile static boolean dinnerHasEnded = false;
	public volatile static boolean lastOneAwake = false;
	public volatile boolean gaveDiamond = false;
//	public boolean notReadyToLeave = true;
	public volatile boolean allowedToLeave = false;
	public volatile static boolean lastOneReady = false;
	public volatile Gnome terminateBehindThisGnome;

	public volatile static boolean [] arrayForRunningGnomes = new boolean[11];
	
	public Gnome(int x, long time, SkyBlue skyBlue){	

		//set name
		setName("Gnome " + x);

		assigned_number = x;
		this.time = time;
		this.skyBlue = skyBlue;
		for(int i = 0; i < 11; i++){
			introducedToSB[i]= false;
			arrayForRunningGnomes[i] = true;
		}
	}
	
	public boolean opendoor(){
		if(counter <11){
			counter++;
			return false;
		}
		return true;
	}
	
	public void run(){

		//create random sleep time
		Random rand = new Random();
		int x = rand.nextInt(500)+1;
		try {
			sleep(x);
			msg(" has arrived slept for "+ x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
		//everyone tries to open the door while incrementing a 
		//counter for how many people are at the door
		//last one can open door
		if(opendoor()){
			allowedToEnter = true;
			msg(" opens the door");
			
			//this gives time for everyone to enter the cabin before they are introduced to skyblue
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			skyBlue.interrupt();
		}

		//busy wait till door is open
		while(allowedToEnter == false){}
	
		//reaches here when door is open
		msg(" has entered the cabin");

		
		//busy wait until introduced to skyblue
		while(!introducedToSB[assigned_number]){
			if(introducedToSB[assigned_number]==true){break;}
		}
		msg(" is introduced");
		
		//increasing everyones priority to 8
		Thread.currentThread().setPriority(8);
		
		//busy wait till the table is ready
		while(tableReady == false){
			if(tableReady == true){break;}
		}
		
		//reaches here when princess says table is ready
		msg(" rushes for the table with priority of " + Thread.currentThread().getPriority());
		
		//gives time before dinner ends so everyone heads to bed together
//		try {
//			sleep(50);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
		
		//dinner has ended time for everyone to sleep
		counter2++;
		while(dinnerHasEnded == false){
			if(counter2 >= 11){
				dinnerHasEnded = true;
				break;
			}
		}
		msg(" dinner ends time for bed");
		
		
		//random time for sleep
		int y = rand.nextInt(500)+1;
		try {
			sleep(y);
			msg(" goes to sleep for "+ y);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//set priority back to default
		Thread.currentThread().setPriority(NORM_PRIORITY);
		
		//gives time for everyone to sleep before waking up
		try {
			sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		//counts to see whos is the last one to wake up
		counter3++;

		//busy wait for last person to wake up princess
		while(lastOneAwake == false){
			if(counter3 >= 11){
				lastOneAwake = true;
			}
			else if(lastOneAwake == true){break;}
		}
		skyBlue.setGnomeOrderForKisses(this);
	
		//wait to leave
		while(gaveDiamond == false){}
		
		//formatting purposes
		try {
			sleep(200);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		msg(" gave his diamond");
		
		
		counter4++;
		if(counter4 == 10){
			lastOneReady = true;
		}
//		wait to terminate in order
		while(true){
			if(allowedToLeave == true){
				break;
			}
		}
		
		msg(" is terminated");
		
//		first approach
//		Tried to use .join() and .isAlive but couldn't get it to work 
		
//		try {
//			if(assigned_number != 1){
//				
//				while(terminateBehindThisGnome.isAlive() == true){}
//				msg(" is terminated");
////				terminateBehindThisGnome.join();
//				if(assigned_number == 0){
//					skyBlue.setPreviouslyTerminatingGnome(this);
//					skyBlue.allowedToTerminate = true;
//				}
//			}
//			else{
//				msg(" is terminated");				
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
	}
		
	public static void introduce(int assigned_number){
		introducedToSB[assigned_number] = true; 
	}
	
	public static void setPreviouslyTerminatingGnome(Gnome gnome){
//		terminateBehindThisGnome = gnome;
	}
	
	public static void msg(String m) {
		 System.out.println("["+(System.currentTimeMillis()-time)+"] "+ Thread.currentThread().getName() +":"+m);
	}
	public boolean checkIfDinnerHasEnded(){
		return dinnerHasEnded;
	}
}
