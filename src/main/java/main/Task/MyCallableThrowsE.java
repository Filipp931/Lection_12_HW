package main.Task;

import java.util.concurrent.Callable;

public class MyCallableThrowsE implements Callable {
    @Override
    public Object call() throws Exception {
        throw new Exception("myException");
    }
}
