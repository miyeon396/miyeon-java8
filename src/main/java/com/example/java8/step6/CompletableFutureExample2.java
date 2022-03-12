package com.example.java8.step6;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class CompletableFutureExample2 {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello "+ Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world0 = CompletableFuture.supplyAsync(() -> {
            System.out.println("world0 "+ Thread.currentThread().getName());
            return "world0";
        });

        // - hello 다음에 world를 해야한다. hello.get 기다린 후 world.get 해야함
        hello.get();
        world0.get();

        // - thenCompose
        CompletableFuture<String> future = hello.thenCompose(CompletableFutureExample2::getWorld); //hello의 결과를 받아서 다음을 수행
        System.out.println("future = "+future.get());

        // - 또 다른 연결. 둘이 서로 연관관계가 없는 경우. 그치만 비동기로 실행하는 방법 둘이 따로 보내고, 둘다 결과가 왔을 떄. -> combine
        CompletableFuture<String> combine = hello.thenCombine(world0, (h, w) -> h + " " + w);// 입력값은 두개 리턴값은 하나 biFunction
        //둘의 결과가 왔을 때 biFuction이 실행이 됨.
        System.out.println("combine = "+combine.get());

        // - 2개 이상일 때 모든 서브태스크를 합쳐서 실행한다 -> allOf -> 전부다 끝났을 때 콜백을 실행할 수 있음.
        //문제. 모든 태스크의 결과의 타입이 동일 할 수도 없고, 에러가 났을 수도 있으니 결과라는 게 무의미 하다.
        CompletableFuture<Void> allOf = CompletableFuture.allOf(hello, world0).thenAccept(System.out::println);
        System.out.println("allOf = "+allOf); //null

        // - Task 들의 모든 결과 값을 Collection으로 만들어서 받을 순 있는데 약간 그럼.. (모든 결과를 기다렸다가 리턴)
        List<CompletableFuture<String>> futures = Arrays.asList(hello, world0);
        CompletableFuture[] futuresArr = futures.toArray(new CompletableFuture[futures.size()]);

        //결과는 어차피 무의미함. 리턴이 중요한데 thenApply 호출되는 이시점에는 future의 모든 작업이 다 끝남. get을 써도 됨. 근데 에러가 발생해서 체크할게 많음. -> join 사용.. join은 unchecked exception을 발생 시킴
        //join하면은 future의 최종 결과값이 나오는데 그 결과 값을 모아서 list로 반환
        CompletableFuture<List<String>> results = CompletableFuture.allOf(futuresArr)
                .thenApply(v -> {
                    return futures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());
                });
        //일케하면 아무것도 블락킹이 되지 않음.

        results.get().forEach(System.out::println);

        // - 결과 중 하나 암거나 빨리 끝나는거 출력
        CompletableFuture<Void> thenAcceptFuture = CompletableFuture.anyOf(hello, world0).thenAccept(System.out::println);
        System.out.println("thenAccept = "+thenAcceptFuture.get());

        // - 에러 처리 비동기적으로 실행할 때 에러
        //exceptionally에서 function을 받아서 리턴할 수 있음.
        boolean throwError = true;

        CompletableFuture<String> exceptionally = CompletableFuture.supplyAsync(() -> {
            if (throwError) {
                throw new IllegalArgumentException();
            }

            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).exceptionally(ex -> {
            System.out.println(ex);
            return "Error!";
        });
        System.out.println("exceptExample = "+exceptionally.get());

        // - handle : biFunction이 들어오는데 에러가 난 경우 정상적으로 끝난 경우 둘 다 씀
        // 첫번쨰는 정상적인 경우의 결과 값. 두번 째는 exception이 발생했을 때.
        CompletableFuture<String> handle = CompletableFuture.supplyAsync(() -> {
            if (throwError) {
                throw new IllegalArgumentException();
            }

            System.out.println("Hello2 " + Thread.currentThread().getName());
            return "Hello2";
        }).handle((result, ex) -> {
            if(ex != null){
                System.out.println(ex);
                return "Error!";
            }
            return result;
        });
        System.out.println("handle = "+handle.get());

        //이후에 관심있다면, forkjoin, flow 공부하면 good.

    }

    private static CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return message + "World";
        });
    }

}

//CompletableFuture를 통해서 조합하는 방법. 여러가지 예외를 처리하는 방법
//Future만 가지고는 콜백을 줄 수 없었기 떄문에 두개를 연결해서 사용하기가 어려웠었음.
