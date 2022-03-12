package com.example.java8.step6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsExample {

    public static void main(String[] args) {
        //Executor을 직접 쓸일은 없고 ExecutorService(ExecutorService를 상속받고 있음)를 씀
        //ScheduledExecutorSrivice -> ExecutorService를 상속받고있음 특정 시간 이후에 딜레이 시켰다가 사용하거나, 주기적으로 스케줄 걸어서 사용할 수 있는 인터페이스임.

        //ExecutorService 예제
        ExecutorService executorService = Executors.newSingleThreadExecutor(); //쓰레드를 한개만 쓰겠다는 것.
        //가장 고전적인 방법 runnable를 구현
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("executorServiceThread : "+Thread.currentThread().getName());
            }
        });
        //아니면 submit을 사용 submit은 다음 작업이 들어오기 전까지 계속 대기하기때문에 살아있음 -> 명시적으로 Shutdown 해줘야함.
        executorService.submit(() -> {
            System.out.println("ThreadSubmit : "+ Thread.currentThread().getName());
        });
        executorService.shutdown(); //graceful shutdown - 현재 진행중인 작업을 끝까지 마치고 끝낸다.
//        executorService.shutdownNow(); //됬고 일단 그냥 죽여라 - 쓰레드가 안찍히고 죽을 수도 있음.


        //ThreadPool Example
        //내부의 쓰레드 풀이 있음. 쓰레드 두개 갖고 있는 executorService 만들고 쓰레드는 두개이지만 작업을 다섯개를 보냄.
        ExecutorService executorService1 = Executors.newFixedThreadPool(2);
        executorService1.submit(getRunnable("Hello"));
        executorService1.submit(getRunnable("miyeon"));
        executorService1.submit(getRunnable("coco"));
        executorService1.submit(getRunnable("babo"));
        executorService1.submit(getRunnable("rulru"));
        executorService1.shutdown();
        //실행이 되고 두개의 쓰레드 가지고 번갈아가면서 작업을 함
        //그림설명
        //blockingqueue -> 쓰레드가 2개뿐인데 작업 100개 200개 보내면 큐에서 대기 하는 것.
        //쓰레드를 생성하는 비용이 덜 드니까 쓰레드 풀을 사용하는 장점이 있는  것.


        //ScheduledExecutorService Examepl -> 스케줄을 받아서 5초 있다가 실행하도록
        ScheduledExecutorService executorService2 = Executors.newSingleThreadScheduledExecutor();
        executorService2.schedule(getRunnable("HelloSchedule"), 5, TimeUnit.SECONDS);
        executorService2.scheduleAtFixedRate(getRunnable("HelloSchedulePeriod"), 1, 2, TimeUnit.SECONDS); //반복적인 것. 1초 있다가 2번 반복
        executorService2.shutdown();

        //Fork/Join -> multi processing. multi processor 개발할 떄 유용한 fw

        //Future란? - 부가적인 쓰레드에서 수행했는데 결과를 가져 오고 싶을 때
        //runnable는 void이기 때문에 사용할 수 없음. callable를 사용해라. runnable와 같은데 return이 가능하다. 그 무언가를 받아오는 것 -> Future
    }

    private static Runnable getRunnable(String message) {
        return () -> System.out.println(message + Thread.currentThread().getName());
    }
}

// executor은 쓰레드나 러너블처럼 로우레벨 api를 직접 다루는게 아니라 쓰레드를 만들고 관리하는 작업을 고수준의 api한테 위임
// executor가 쓰레드 만들고 우리는 runnable만 정의해서 주면 됨. 해야할 일을 안에 구현해서 주면 됨.
//쓰레드 만들어서 실행하고 필요 없으면 없애고 -> 이런 일련의 작업을 executor이 해줌.