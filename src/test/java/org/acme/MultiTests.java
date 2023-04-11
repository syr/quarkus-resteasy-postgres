package org.acme;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MultiTests {

    @Test
    public void testMultithreading() {

        final ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<CompletableFuture<Integer>> futures = List.of(
                CompletableFuture.supplyAsync(getIntegerSupplier(1), executorService),
                CompletableFuture.supplyAsync(getIntegerSupplier(2), executorService),
                CompletableFuture.supplyAsync(getIntegerSupplier(3), executorService)
        );

        List<Integer> results = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        StringBuilder sb = new StringBuilder();
        results.forEach(i -> sb.append(i).append(","));
        sout(sb.toString());
    }

    private static Supplier<Integer> getIntegerSupplier(Integer i) {
        return () -> {
            sleep(i*1000);
            // Perform parallel computation on separate thread
            sout("Hello from supply async!");
            return i * 2;
        };
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testMultithreading2() {
        Stream.of(1,6)
                .parallel()
                .forEach(MultiTests::doublerFunction);
    }

    private static int doublerFunction(int i) {
        sout("entering doubler");
        try {
            Thread.sleep(i*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sout("Hello from doubler function");
        sout("exiting doubler");
        return i * 2;
    }

    private static void sout(String string){
        String threadName = Thread.currentThread().getName();
        System.out.printf("[%s] %s \n", threadName, string);
    }
}
