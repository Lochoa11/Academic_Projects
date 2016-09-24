import java.util.Random;
import java.util.concurrent.Semaphore;

public class Gnome extends Thread{
	
	public static long time = System.currentTimeMillis();

	//keeps track of amount of Gnome
	static int numberOfGnomes = 0;
	public static volatile Semaphore waitSem = new Semaphore(numberOfGnomes);//used for Gnome arrivals
	
	//makes sure Counter is mutually exclusive 
	int mutex = 1;
	Semaphore semForCounter = new Semaphore(mutex);
	public static int Counter = 0;
	public static boolean allAreWaiting = false;
	int group = 3;
	Semaphore semForGroup = new Semaphore(group);
	public static int groupNumber = 1;
	int assignedGroupNumber;
	static int table_size	= 0;
	public static volatile Semaphore semWaitForTable = new Semaphore(table_size, true);
	SkyBlue skyblue;
	static int waitToFormGroup=0;
	public static volatile Semaphore semWaitToFormGroup = new Semaphore(waitToFormGroup);
	static int waitForSleep = 0;
	public static volatile Semaphore semWaitForSleep = new Semaphore(waitForSleep);
	static int lineUp = 0;
	public static volatile Semaphore semToLineUp = new Semaphore(lineUp);
	static int kiss = 0;
	public static volatile Semaphore semForKisses = new Semaphore(kiss);
	
	Gnome(int assignedNumber, SkyBlue skyblue){
		super("Gnome " + assignedNumber);
		this.skyblue = skyblue;
	}
	
	public void run(){
		Random rand = new Random();
		int x = rand.nextInt(2000)+1;
		
		//Gnomes get home at different times
		try{
			sleep(x);
			msg(" is sleeping for " + x);
		
		}catch(InterruptedException e){
			msg("some thing went wrong with Gnome sleep");
		}
		
		
		//Counter to keep track of how many Gnomes arrive at the door
		try{
			semForCounter.acquire();
			Counter++;
			semForCounter.release();
		}catch(InterruptedException e){
			msg(" something went wrong with binary semaphore for counting the arrivals");
		}

		
		//last Gnome to arrive opens the door and wakes skyblue
		if(Counter == 11){
			msg(" is the last to arrive and opens the door");
			waitSem.release(11);
			skyblue.sem.release();
		}
		
		//Gnomes wait untill the door is open
		try {
			waitSem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
			msg("Something went wrong with block the Gnome");
		}
		msg(" enters the cabin");
		
		
		//reset Counter 
		if(Counter == 11){
			Counter = 0;
		}
		
		//Gnomes wait to be introduced to Skyblue
		try{		
			semForCounter.acquire();
			Counter++;
			semForCounter.release();
			
			if(Counter == 11){skyblue.sem.release();}
			
			sleep(1000);
			msg(" waits to be introduced");
			sleep(1000);		
			waitSem.acquire();
			msg(" is introduced to the princess");
		}catch(InterruptedException e){
			msg(" Something went wrong with the introduction");
		}

		//Reset Counter so that all Gnomes can wait for table to be ready at the same time
		if(Counter == 11){
			Counter = 0;
		}
		try{
			
			semForCounter.acquire();
			Counter++;
			semForCounter.release();
			if(Counter == 11){
				semWaitToFormGroup.release(11);
			}
			semWaitToFormGroup.acquire();
			
		}
		catch(InterruptedException e){
			msg("Something happened while waiting for form group");
		}
		
		
		//Resets Counter so that Gnomes can begin to form groups
		if(Counter == 11){
			Counter = 0;
		}
		try{
			//Gnomes form a group allows three at a time
			semForGroup.acquire();
			//Counts the gnomes
			semForCounter.acquire();
			Counter++;
			semForCounter.release();
			//every three gnomes allow three more to make a group
			if(Counter % group == 0){
				assignedGroupNumber = groupNumber;
				groupNumber++;
				//releases next 3 Gnomes to form a group
				semForGroup.release(3);
			}else{
				assignedGroupNumber = groupNumber;
			}
			//			msg(" group number is " + assignedGroupNumber);
			
			//once all gnomes are in group order wait until princess 
			//allows group this semaphore is first come first serve
			
			//last Gnome will let SkyBlue know the Gnomes are now in groups of 3
			if(Counter == 11){
				Counter = 0;
				skyblue.semForTable.release();
			}
			
			//wait until skyblue allows to be seated
			semWaitForTable.acquire();			
			msg(" group number is " + assignedGroupNumber + " and gets seated");
		}catch(InterruptedException e){
			msg(" something went wrong with seating");
		}
		
		//reset Counter to know when all Gnomes are ready to sleep
		if(Counter == 11){
			Counter = 0;
		}
		
		try {
			semForCounter.acquire();
			Counter++;
			semForCounter.release();
			//if all Gnomes are present and ready to sleep let skyblue sleep too
			if(Counter == 11){
				skyblue.semAllowForSleep.release();
			}
			
			//Gnomes wait here after dinner
			semWaitForSleep.acquire();
			
			msg(" goes to sleep");
			
			sleep(x);
			
			msg(" slept for " + x);
			
			//reset Counter so that everyone can line up together
			if(Counter == 11){
				Counter = 0;
			}
			
			//Counter to get everyone ready to line up at the same time
			semForCounter.acquire();
			Counter++;
			semForCounter.release();
			//lets skyblue know that the gnomes are lined up and first one is ready to be kissed
			if(Counter == 11){
				skyblue.waitToKissFirstGnome.release();
//				msg("I'm here guys");
			}

			
			//all Gnomes block
//			semToLineUp.acquire();
			msg(" brushes his teeth and lines up");
			
			semForKisses.acquire();
			msg(" get a kiss from SkyBlue and goes to work");
			semForKisses.release();
			
			//reset to count terminating Gnomes
			if(Counter == 11){
				Counter = 0;
			}
			
			semForCounter.acquire();
			Counter++;
			semForCounter.release();
			
			msg(" is terminated ");
			if(Counter == 11){
				skyblue.semToTerminate.release();
			}
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
	}
	
	public void msg(String x){
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+":"+x);
	}
}