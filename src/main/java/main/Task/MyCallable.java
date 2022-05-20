package main.Task;

import java.util.concurrent.Callable;

public class MyCallable implements Callable {
    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        return "result of " + Thread.currentThread().getName() + " calculations";
    }
}
