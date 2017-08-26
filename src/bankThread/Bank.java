package bankThread;

import java.util.Arrays;


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


    public synchronized void transfer(int from, int to, double amount) throws InterruptedException {

        while (accounts[from] < amount)
            wait();
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" TBalance: %10.2f%n", getTotalBalance());
        notifyAll();

    }

    public synchronized double getTotalBalance() {

        double sum = 0;
        for (double a : accounts)
            sum += a;
        return sum;


    }

    public int size() {
        return accounts.length;
    }

}
