package main.Task;

public class CallableException extends Exception{
    String message;
    public CallableException(String message) {
        this.message = message;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getMessage() {
        return message;
    }
}
