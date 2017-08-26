package bankThread;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private final double[] accounts;

    public static final int NACCOUNTS = 100;
    public static final int DELAY = 10;
    public static final double INITIAL_BALANCE = 1000;
    public static final double MAX_AMOUNT = 1000;


    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    private Lock bLock = new ReentrantLock();

    public void transfer(int from, int to, double amount) {

        bLock.lock();
        try {
            if (accounts[from] < amount) return;
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf("TBalance: %10.2f%n", getTotalBalance());
        } finally {
            bLock.unlock();
        }
    }

    public double getTotalBalance() {
        double sum = 0;
        for (double a : accounts)
            sum += a;
        return sum;

    }

    public int size() {
        return accounts.length;
    }

}
