package threadPool;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.*;

public class tPoolTest {

    public static void main(String[] args) throws Exception {

        ExecutorService pool = null;
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("enter directory: "); ///Users/oleg1/IdeaProjects/Training/src
            String directory = in.nextLine();
            System.out.print("enter keyword: ");
            String keyword = in.nextLine();

            pool = Executors.newCachedThreadPool();

            Counter counter = new Counter(new File(directory), keyword, pool);
            Future<Integer> result = pool.submit(counter);

            try {
                System.out.println(result.get() + " count files");

            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
        }
        pool.shutdown();

        int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
        System.out.println("largest pool size =  " + largestPoolSize);


    }
}
