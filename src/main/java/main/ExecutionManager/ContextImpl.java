package main.ExecutionManager;

import java.util.Map;
import java.util.Queue;

public class ContextImpl implements Context{
    private final Map<TaskStates, Integer> statesMap;
    private final Queue<Runnable> queue;
    private int numberOfInterruptedTasks;
    private int numberOfTasks;

    public ContextImpl(Map<TaskStates, Integer> statesMap, Queue<Runnable> queue, int numberOfTasks) {
        this.statesMap = statesMap;
        statesMap.put(TaskStates.COMPLETED,0);
        statesMap.put(TaskStates.FAILED,0);
        this.queue = queue;
        this.numberOfTasks = numberOfTasks;
    }

    @Override
    public int getCompletedTaskCount() {
        return statesMap.get(TaskStates.COMPLETED);
    }

    @Override
    public int getFailedTaskCount() {
        return statesMap.get(TaskStates.FAILED);
    }

    @Override
    public int getInterruptedTaskCount() {
        return numberOfInterruptedTasks;
    }

    @Override
    public void interrupt() {
        while(!queue.isEmpty()){
            queue.poll();
            numberOfInterruptedTasks++;
        }
    }

    @Override
    public boolean isFinished() {
        return (numberOfTasks == (getCompletedTaskCount() + getCompletedTaskCount()));
    }

    @Override
    public String toString() {
        return  "=======================================================\n"
                + "CompletedTaskCount = " + getCompletedTaskCount()
                +"\n  FailedTaskCount = " + getFailedTaskCount()
                +"\nInterruptedTaskCount = "+ getInterruptedTaskCount()
                + "\nisFinished = " + isFinished() +
                "\n=======================================================\n";
    }
}
