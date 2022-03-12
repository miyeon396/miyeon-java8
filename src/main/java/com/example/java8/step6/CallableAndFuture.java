package com.example.java8.step6;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class CallableAndFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        //callable이 return하는 타입의 future를 받을 수 있음.
        Future<String> helloFuture = executorService.submit(hello);
        System.out.println("started!");
        System.out.println(helloFuture.isDone()); //어떻게 마냥기다리냐! 상태 알 수 없냐? -> 잇쥐 : isDone

//        helloFuture.get(); //get 이전까지는 코드가 안기다리고 실행이 되지만, get을 만난 순간 멈추고 결과 갖고 올 떄까지 기다림.
        helloFuture.cancel(false);//cancel - true : 현재 진행중인 작업을 인터럽트 하면서 종료하는 것. false : 기다림 그치만 기다렸다한들 일단 cancel하면 get을 할 수 없고 isDone는 true가 된다.

        System.out.println(helloFuture.isDone()); //어떻게 마냥기다리냐! 상태 알 수 없냐? -> 잇쥐 : isDone
//        helloFuture.get(); //위에서 cancel false 하면 여기서 이미 취소된 작업 가져온다고 에러남.
        System.out.println("end!!");

        executorService.shutdown();


        // callable를 사용해서 여러가지 작업들을 뭉탱이로 줄 수가 있음.
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();

        Callable<String> a = () -> {
            Thread.sleep(2000L);
            return "a";
        };

        Callable<String> b = () -> {
            Thread.sleep(3000L);
            return "b";
        };

        Callable<String> c = () -> {
            Thread.sleep(1000L);
            return "c";
        };
        //이걸 한꺼번에 보낼 수가 있음.
        List<Future<String>> futures = executorService1.invokeAll(Arrays.asList(a, b, c)); //cmd+alt+v -> 변수 추출!!!! 단축어
        //invokeAll -> a,b,c,가 다 기다릴때까지 기다림 a,c가 다 끝낫어도 b가 끝날때까지 기다려서 결과를 다 가져오는거
        //서버 3개에 같은 카피 복사해놓고 다 가져오라함 -> 근데 셋다 기다릴필요가 잇나?? -> 아니지 한개만 일찍오면 되지 -> invoke any;
        for(Future<String> f : futures ) {
            System.out.println(f.get());
        }
        executorService1.shutdown();

        //invokeAny 블로킹 콜 : 바로 결과가 나옴
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        String s = executorService2.invokeAny(Arrays.asList(a,b,c));
        System.out.println("sss -> "+s); //ㅋㅋ 싱글로 했을 떄 자꾸 a가 먼저 나오는 이유 -> 자리가 없어서 a 다 끝나고 b 들가고 끝나고 c 들가고 해서 ㅋㅋ
        executorService2.shutdown();

        ExecutorService executorService3 = Executors.newFixedThreadPool(4);
        String s1 = executorService3.invokeAny(Arrays.asList(a,b,c));
        System.out.println("multi -> "+s1); //ㅋㅋ 싱글로 했을 떄 자꾸 a가 먼저 나오는 이유 -> 자리가 없어서 a 다 끝나고 b 들가고 끝나고 c 들가고 해서 ㅋㅋ
        executorService3.shutdown();



    }

    // Callable : runnable와 거의다 유사하지만 다른 것은 return 값을 가질 수 있다.
}
