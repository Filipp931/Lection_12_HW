package main;

import main.Task.CallableException;
import main.Task.MyCallable;
import main.Task.MyCallableThrowsE;
import main.Task.Task;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

class TaskTest {
    @Test
    public void TaskTest(){
        Callable callable = new MyCallable();
        Task task = new Task(callable);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    System.out.println(task.get());
                } catch (CallableException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test()
    public void TaskExceptionTest(){
        Callable callable = new MyCallableThrowsE();
        Task task = new Task(callable);
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                    task.get();
                } catch (CallableException e) {
                    System.out.println(e.getMessage());
                }
            });
            thread.start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}