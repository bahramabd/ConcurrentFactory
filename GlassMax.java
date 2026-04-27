import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GlassMax {

    private int balance = 0;
    private int produced =0;

    public int getBalance() {
        return balance;
    }
    public void setBalance(int bal){ balance = bal;}

    public synchronized int deposit() {




            int newBalance = balance + 1;
            produced++;

            // This delay is deliberately added to magnify the
            // data-corruption problem and make it easy to see.


            balance = newBalance;




            return balance;
        }
    }


