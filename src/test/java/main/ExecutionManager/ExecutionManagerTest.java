package main.ExecutionManager;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Random;


@RunWith(JUnit4.class)
public class ExecutionManagerTest extends TestCase {
    Runnable callback;
    Runnable[] tasks;
    @Before
    public void init() {
        callback = new Runnable() {
            @Override
            public void run() {
                System.out.println("Callback!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        tasks = new Runnable[20];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = () -> {
                try {
                    Thread.sleep(new Random().nextInt(4000));
                    System.out.println("TASK");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }
    }
    @Test
    public void ExecutionManagerStandardTest() throws InterruptedException {
        init();
        ExecutionManager executionManager = new ExecutionManagerImpl();
        Context context = executionManager.execute(callback, tasks);
        while (!context.isFinished()) {
            System.out.println(context.toString());
            Thread.sleep(500);
        }
        System.out.println(context.toString());
        System.out.println("Finished");
    }
    @Test
    public void ExecutionManagerInterruptTest() throws InterruptedException {
        init();
        ExecutionManager executionManager = new ExecutionManagerImpl();
        Context context = executionManager.execute(callback, tasks);
        System.out.println(context.toString());
        Thread.sleep(2000);
        System.out.println("Interrupting!");
        context.interrupt();
        Thread.sleep(4000);
        System.out.println(context.toString());
        System.out.println("Finished");
    }
    @Test
    public void ExecutionManagerExceptionTest() throws InterruptedException {
        init();
        tasks[13] = new Runnable() {
            @Override
            public void run() {
                tasks[21] = null;
            }
        };
        ExecutionManager executionManager = new ExecutionManagerImpl();
        Context context = executionManager.execute(callback, tasks);
        while (!context.isFinished()) {
            System.out.println(context.toString());
            Thread.sleep(500);
        }
        System.out.println(context.toString());
        System.out.println("Finished");
    }

}