package com.example.java8.step6;

import java.util.concurrent.*;

public class CompletableFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //CompletableFuture를 쓰면 명시적으로 더이상 Executor를 만들어서 쓸 필요 없음.
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("miyeon"); //명시적으로 값을 준 경우.
        System.out.println("111"+future.get());  //get이 없어지진 않음.

        //static factory method로 똑같이 구현한다면
        CompletableFuture<String> future1 = CompletableFuture.completedFuture("miyeon1");
        System.out.println("222"+future1.get());

        //실제로 어떤 작업을 실행하고 싶으면
        //1. 리턴이 없는 경우 -> run async
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            System.out.println("HelloFuture2 : " +Thread.currentThread().getName());
        });
        System.out.println("333"+future2.get()); //future만 정의하면 암것도 일어나지 않음. get을 해야 일어남.

        //2. return type이 있는 경우 -> supply async
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("HelloFuture3: "+ Thread.currentThread().getName());
            return "Hello";
        });
        //code만 좀 달라졌을 뿐이지 future 사용할 떄와 거의 비슷.
        System.out.println("444"+future3.get());

        //결과 값에 대한 asnyc한 callback을 주는 경우 -> 이 경우도 get을 호출해줘야 뭔가가 일어남.
        //callback 1. thenApply : 리턴이 있는 경우
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> {
            System.out.println("HelloFuture4: "+ Thread.currentThread().getName());
            return "Hello4";
        }).thenApply((s) -> {
            System.out.println(Thread.currentThread().getName());
            return s.toUpperCase();
        }); //java5의 future만 제공했을 때는 콜백이 불가능 했다.

        System.out.println("555"+future4.get());

        //callback2. 리턴이 없는 콜백 걍 받아서 쓰는거 : thenAccept
        CompletableFuture<Void> future5 = CompletableFuture.supplyAsync(() -> {
            System.out.println("HelloFuture5: "+ Thread.currentThread().getName());
            return "Hello5";
        }).thenAccept((s) -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(s.toUpperCase());
        }); //java5의 future만 제공했을 때는 콜백이 불가능 했다.

        System.out.println("666"+future5.get());

        //callback 3. 리턴도 받을 필요 없다. 뭘 하기만 하면 된다. -> thenRun (결과 값을 참고.)
        CompletableFuture<Void> future6 = CompletableFuture.supplyAsync(() -> {
            System.out.println("HelloFuture6: "+ Thread.currentThread().getName());
            return "Hello6";
        }).thenRun(() -> { //runnable를 구현
            System.out.println(Thread.currentThread().getName());
        });
        System.out.println("777"+future6.get());


        //지금까지 비동기로 처리를 했는데 도대체 어떻게 쓰레드풀을 만들지도 않고 별도의 쓰레드에서 동작을 하는거냐 -> forkJoinPool 때문에
        //forkJoinPool -> 얘도 executor 구현체 중 하나인데 디큐를 씀. 맨 마지막에 들어온게 먼저 나감. 쓰레드가 할일 없으면 가져와서 처리하는 것. 태스크를 쪼개서 쓰레드에 처리하라 준 후 모아서 결과 내는 forkjoin f/w
        // -> 내부적으로 ThreadPool 쓰지 않아도 ForkJoin에 있는 CommonPool을 쓰게 된다. 하지만 원한다면 얼마든지 만들어서 줄 수 있다.
        // ex)
        //어디어디 줄 수 있냐면, supplyAsync, runcAsync쓸 때 두번쨰 인자로 줄 수 있음. -> 일케하면 풀의 이름이 달라짐.
        //콜백에도 다른 쓰레드에서 동작하고 싶다면 인자로 줄 수 있음.

        //ThreadPool 예제
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CompletableFuture<String> future7 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello7 : "+Thread.currentThread().getName());
            return "Hello7";
        }, executorService).thenApplyAsync((s) -> {
            System.out.println("run7 : "+Thread.currentThread().getName());
            return s.toUpperCase();
        }, executorService); //요기 두번째 인자로 주면 됨.
        System.out.println("ThreadPool "+future7);
        executorService.shutdown();



    }


}

//compltablefuture -> 좀 더 비동기 프로그래밍에 가까운 코딩이 가능해짐
//future의 가장 문제점 -> 예외 처리가 안됨. 예외처리 인터페이스 제공 x. future 여러개 조합이 어려움. -> //future를 가지고 뭔가를 해야한다면 무조건 get 이후에 와야함. get이 블로킹 코드..
//CompletableFuture : future도 구현하고, CompletionStage도 구현 -> Completable 몇 초안에 응답이 안오면 그냥 리턴하거나 만들 수 있음.