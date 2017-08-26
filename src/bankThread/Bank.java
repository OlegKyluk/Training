package bankThread;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private final double[] accounts;
    private Lock bLock;
    private Condition requiredAmount;
    public static final int NACCOUNTS = 100;
    public static final int DELAY = 10;
    public static final double INITIAL_BALANCE = 1000;
    public static final double MAX_AMOUNT = 1000;


    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
        bLock = new ReentrantLock();
        requiredAmount = bLock.newCondition();
    }


    public void transfer(int from, int to, double amount) throws InterruptedException {
        bLock.lock();
        try {
            while (accounts[from] < amount)
                requiredAmount.await();
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" TBalance: %10.2f%n", getTotalBalance());
            requiredAmount.signalAll();
        } finally {
            bLock.unlock();
        }
    }

    public double getTotalBalance() {
        bLock.lock();
        try {
            double sum = 0;
            for (double a : accounts)
            sum += a;
            return sum;
        } finally {
            bLock.unlock();
        }

    }

    public int size() {
        return accounts.length;
    }

}
