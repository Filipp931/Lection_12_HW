package main.ExecutionManager;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionManagerImplTest {
    static Runnable callback;
    static Runnable[] tasks;

    public static void init() {
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
        tasks = new Runnable[10];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = () -> {
                System.out.println("TASK");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }
    }
    @Test
    public void ExecutionManagerImplTest() throws InterruptedException {
        init();
        ExecutionManager executionManager = new ExecutionManagerImpl();
        Context context = executionManager.execute(callback, tasks);
        while (!context.isFinished()) {
            System.out.println(context.toString());
            Thread.sleep(2000);
        }
        System.out.println(context.toString());
        System.out.println("Finished");
    }


}