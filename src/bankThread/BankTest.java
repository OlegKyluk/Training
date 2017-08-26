package bankThread;

public class BankTest {
    public static void main(String[] args) {
        Bank bank = new Bank(Bank.NACCOUNTS, Bank.INITIAL_BALANCE);
        for (int i = 0; i < Bank.NACCOUNTS; i++) {
            int fromAccount = i;
            Runnable r = () -> {
                try {
                    while (true) {
                        int toAccount = (int) (bank.size() * Math.random());
                        double amount = Bank.MAX_AMOUNT * Math.random();
                        bank.transfer(fromAccount, toAccount, amount);
                        Thread.sleep((int) (Bank.DELAY * Math.random()));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }
}
