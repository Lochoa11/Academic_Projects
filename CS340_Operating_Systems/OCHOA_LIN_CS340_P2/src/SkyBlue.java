import java.util.concurrent.Semaphore;

public class SkyBlue extends Thread{
	
	public static long time = System.currentTimeMillis();
	int mutex = 0;
	Semaphore sem = new Semaphore(mutex);
	int setTable = 0;
	Semaphore semForTable = new Semaphore(setTable, true);
	int allowForSleep = 0;
	Semaphore semAllowForSleep = new Semaphore(allowForSleep);
	int terminateValue = 0;
	Semaphore semToTerminate = new Semaphore(terminateValue);
	Semaphore waitToKissFirstGnome = new Semaphore(0);
	
	SkyBlue(){
		super("Sky Blue");
	}
	public void run(){
		
		msg(" cleans the house and goes to sleep");
		
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		try{
			sleep(10);//allows Gnomes to enter before skyblue wakes
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		msg(" is now awake");


		
		try{
			sem.acquire();//waits for all Gnomes to be ready to be introduced	
		}catch(InterruptedException e){
			msg(" something went wrong with princess waiting for everone to be introduced");
		}
		
		//skyblue introduces herself to the Gnome
		Gnome.waitSem.release(11);
		
		//wait to set table
		try {
			semForTable.acquire();
			
			while(Gnome.Counter < 11){
				Gnome.semWaitForTable.release(3);
				sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//wait for everyone to sleep at the same time
		try{
			semAllowForSleep.acquire();
			Gnome.semWaitForSleep.release(11);
			
			//waits till all gnomes are lined up
			waitToKissFirstGnome.acquire();
			
			//kisses the first gnome 
			Gnome.semForKisses.release();
			
			semToTerminate.acquire();
		}
		catch(InterruptedException e){
			msg(" Something happened with semAllowForSleep");
		}
		msg(" terminates");
	}
	
	public void setTable(){
		
	}
	public void msg(String x){
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+":"+x);
	}
}