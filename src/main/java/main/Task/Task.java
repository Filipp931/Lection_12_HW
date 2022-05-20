package main.Task;

import java.util.concurrent.Callable;

public class Task<T> {
    private final Callable<? extends T> callable;
    private T result;
    private CallableException e;
    public Task(Callable<? extends T> callable){
        this.callable = callable;
    }
    public T get() throws CallableException {
        if(e != null) throw e;
        if(result == null) {
            synchronized (this) {
                if(e != null) throw e;
                if(result == null) {
                    try {
                        System.out.println(Thread.currentThread().getName()+" calculating");
                        result = callable.call();
                    } catch (Exception e) {
                        this.e = new CallableException(e.getMessage());
                        throw this.e;
                    }
                }
            }
        }
        return result;
    }
}
