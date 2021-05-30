package org.example;

import java.util.concurrent.*;

public class ThreadMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable myCallable = () -> {
            Thread.sleep(3000);
            System.out.println("calld方法执行了");
            return "call方法返回值";
        };

        Future submit = executor.submit(myCallable);
        System.out.println("获取结果："+submit.get());


        Runnable runnable = () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("runnable方法执行了");
        };

        // 第二个参数可以传入对象，在runnable中获取结果
        Future<String> res = executor.submit(runnable, "default");
        System.out.println("获取结果："+res.get());
    }

}
