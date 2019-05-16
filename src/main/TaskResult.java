package main;

public class TaskResult {

    private String queueName;
    private int resultCode;

    public TaskResult(String queueName, int resultCode) {
        this.queueName = queueName;
        this.resultCode = resultCode;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
