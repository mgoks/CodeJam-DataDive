/**
 * CodeJam: DataDive
 * Group Foo
 */

package waterToPower;

import java.util.ArrayList;
import java.util.List;

public class JavaLauncher 
{
        public static void main(String[] args) throws InterruptedException 
        {
                List<Thread> threadList = new ArrayList<Thread>();
                for (int i = 0; i < 2; i++) 
                {
                	Thread t1 = new Thread(new Analysis());
                        t1.start();
                        threadList.add(t1);
                }
                for (Thread t : threadList) 
                {
                        // waits for this thread to die
                        t.join();
                }
                System.out.println("Avg: " + Analysis.ratioSum / 2.0);
        }	
}