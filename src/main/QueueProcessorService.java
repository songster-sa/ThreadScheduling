package main;

import java.util.concurrent.Callable;

public class QueueProcessorService implements Callable<TaskResult> {

    private String queueName;
    private Task task;

    public QueueProcessorService(Task task) {
        this.queueName = task.getQueueName();
        this.task = task;
    }


    @Override
    public TaskResult call() throws Exception {

        task.setStartedAt(System.currentTimeMillis());
        task.setStatus("processing...");
        // get processor from queuename and call the processor
        // set task start time etc;
        // catch any error
        // return value as required
        return new TaskResult(task.getQueueName(), 1);
    }
}
