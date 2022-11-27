package io.conduktor.demos;

public class ThreadTerminationBlockingDemo {
    public static void main(String [] args) {
        Thread thread = new Thread(new BlockingTask());

        thread.start();

        // interrupt the blocking thread
        thread.interrupt();
    }

    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            //do things
            try {
                Thread.sleep(500000);
            } catch (InterruptedException e) {
                System.out.println("Existing blocking thread");
            }
        }
    }
}
