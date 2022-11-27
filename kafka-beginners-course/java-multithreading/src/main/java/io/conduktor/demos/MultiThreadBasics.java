package io.conduktor.demos;

public class MultiThreadBasics {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // code will run in a new thread
                System.out.println("We are now in thread: " + Thread.currentThread().getName());
                System.out.println("Current thread priority is: " + Thread.currentThread().getPriority());
            }
        });

        thread.setName("New worker Thread");

        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are in thread: " + Thread.currentThread().getName() +
                "before starting a new thread");
        thread.start();
        System.out.println("We are in thread: " + Thread.currentThread().getName() +
                "after starting a new thread");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
