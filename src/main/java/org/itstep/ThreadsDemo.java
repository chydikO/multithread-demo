package org.itstep;

import java.time.LocalDateTime;

public class ThreadsDemo {
    public static void main(String[] args) {
        System.out.println("thread in main method = " + Thread.currentThread().getName());

        new Thread(() -> {
            System.out.println("thread in other thread = " + Thread.currentThread().getName());
            System.out.println("Start program " + LocalDateTime.now());
        }).start();

        System.out.println("End program " + LocalDateTime.now());
    }
}
