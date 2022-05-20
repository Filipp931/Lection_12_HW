package main.ExecutionManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ExecutionManagerImpl implements ExecutionManager{
    private final List<Thread> myThreadPool = Collections.synchronizedList(new ArrayList<>());
    private final Map<TaskStates, Integer> statesMap = new ConcurrentHashMap<>();
    private final Queue<Runnable> queue = new ConcurrentLinkedDeque<>();
    private volatile Runnable callback;
    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        this.callback = callback;
        queue.addAll(Arrays.asList(tasks));
        generateAndStartThreads(10);
        return new ContextImpl(statesMap,queue, tasks.length);
    }
    private void generateAndStartThreads(int numberOfThreads){
        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread (new MyThread());
            thread.setUncaughtExceptionHandler(new Handler());
            myThreadPool.add(thread);
        }
        myThreadPool.stream().filter(thread -> thread.getState().equals(Thread.State.NEW)).forEach(Thread::start);
    }

    private class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.printf("%s starting\n", Thread.currentThread().getName());
            Runnable task;
            task = queue.poll();
            if (task == null) {
                if (callback != null) {
                    synchronized (callback) {
                        if (callback != null) {
                            System.out.printf("\n%s running callback\n", Thread.currentThread().getName());
                            callback.run();
                        }
                    }
                }
            } else {
                System.out.printf("%s running task\n", Thread.currentThread().getName());
                task.run();
                statesMap.put(TaskStates.COMPLETED, statesMap.get(TaskStates.COMPLETED) + 1);
            }
        }
    }


    private class Handler implements Thread.UncaughtExceptionHandler{
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            statesMap.put(TaskStates.FAILED, statesMap.get(TaskStates.FAILED) + 1);
            generateAndStartThreads(1);
        }
    }


}
