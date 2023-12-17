
package ta;

import java.util.concurrent.Semaphore;


public class TeachingAssistant implements Runnable {

    private Mutexlock wakeup;
    private Semaphore chairs;
    private Semaphore available;
    private Thread t;
    private int numberofTA;
    private int numberofchairs;
    private int helpTime=5000;


    public TeachingAssistant (Mutexlock wakeup, Semaphore chairs, Semaphore available, int numberofTA,int numberofchairs) {
        t = Thread.currentThread();
        this.wakeup = wakeup;
        wakeup = new Mutexlock(numberofTA);
        this.chairs = chairs;
        this.available = available;
        this.numberofTA = numberofTA;
        this.numberofchairs=numberofchairs;
    }

    @Override
    public void run() {
        while (true) {
            try {
                wakeup.release();
                t.sleep(helpTime);

                if (chairs.availablePermits() != numberofchairs) {
                    do {
                        t.sleep(helpTime);
                        chairs.release();
                    } while (chairs.availablePermits() != numberofchairs);
                }
            } catch (InterruptedException e) {
                continue;
            }
        }
    }
}
