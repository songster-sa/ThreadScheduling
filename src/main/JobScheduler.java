package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class JobScheduler {

    private int timeout = 3600;

    private Executor executor = new ScheduledThreadPoolExecutor(5);
    private CompletionService completionService = new ExecutorCompletionService(executor);
    private Map<String, Integer> capacityMap = new HashMap<String, Integer>();

    private List<Task> taskList = new ArrayList<>();

    // this is scheduled to run every 10ms
    public void runTask() {

        // if all plugins are started
        // if any new queue name found, register it

        // check if tasks exist with status "unprocessed"
        Task next = getNextTask();
        if (next != null && decreaseCapacity(next.getQueueName())) {

            completionService.submit(new QueueProcessorService(next));
        }

        Future<TaskResult> result = completionService.poll();
        try {
            increaseCapacity(((TaskResult) result.get()).getQueueName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private boolean decreaseCapacity(String queueName) {
        int cap = capacityMap.get(queueName);

        if (cap == 0) {
            // throttled
        }
        if (cap != -1) {
            // as -1 means use whatever is available in the pool
            capacityMap.put(queueName, cap - 1);
            return true;
        }

        return false;
    }

    private boolean increaseCapacity(String queueName) {
        int cap = capacityMap.get(queueName);

        if (cap != -1) {
            capacityMap.put(queueName, cap + 1);
            return true;
        }

        return false;
    }

    public void register(String queueName, int capacity) {

        capacityMap.putIfAbsent(queueName, capacity);
    }

    public Task getNextTask() {
        for (Task task : taskList) {
            if (task.getStatus().equalsIgnoreCase("unprocessed")) {
                return task;
            }
        }
        return null;
    }
}
