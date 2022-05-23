package main;

import junit.framework.TestCase;
import main.Task.CallableException;
import main.Task.MyCallable;
import main.Task.MyCallableThrowsE;
import main.Task.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.concurrent.Callable;
@RunWith(JUnit4.class)
public class TaskTest extends TestCase{
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
        new Thread(() -> {
                try {
                    System.out.println(task.get());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}