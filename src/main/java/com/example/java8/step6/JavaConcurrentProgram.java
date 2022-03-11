package com.example.java8.step6;

public class JavaConcurrentProgram {

    public static void main(String[] args) throws InterruptedException {

        //어떤 Application이 동작하는 이것은 메인 쓰레드 입니다.
        System.out.println(Thread.currentThread().getName());

        //메인쓰레드에서 다른 쓰레드를 크게 두가지 방법으로 만들 수 있습니다.
        //111. 상속받아 만든걸 실행 -> start를 하면 됨
        //Thread와 Process의 차이까지는 들어가지 않겠으나 이거 모르면 안되여..
        MyThread myThread = new MyThread();
        myThread.start();

        System.out.println("Hello");//thread 후 hello가 나와야할거 같은데 간혹 hello 후 run의 thread 가나올 수 있따 .-> 쓰레드는 순서를 보장 못함

        //222. 쓰레드를 쓰긴 씀. 좀 더 쉬움 생성자로 Runnable()를 주며 생성하는 것. (Runnable를 구현해서 만들거나)
        Thread threadEx2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ThreadEx2 : "+Thread.currentThread().getName());
            }
        });
        threadEx2.start();
        System.out.println("Hello2 : "+Thread.currentThread().getName());

        //222. java8부터는 람다로 바꿔 쓸 수 있음. (Runnable이 Functional Interface로 바뀌었기 때문에 가능한거당)
        Thread threadEx22 = new Thread(() -> {
            System.out.println("ThreadEx22 : " + Thread.currentThread().getName());
        });
        threadEx22.start();

        System.out.println("Hello22 : "+Thread.currentThread().getName());


        //Interrupt 예제 : 쓰레드를 꺠움
        Thread threadInterrupt = new Thread(() -> {
            while (true) {
                System.out.println("ThreadInterrupt: "+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    System.out.println("exit!");
                    return; //return안해주면 exit하지만 계속 찍을 것. 다른 쓰레드를 이처럼 종료시켜버릴 방법이 있음. 그치만 메서드가 있는건 아니고 우리가 이런식으로 구현 해야함
                }
            }
        });
        threadInterrupt.start();
        System.out.println("HelloInterrupt"+ Thread.currentThread().getName());
        Thread.sleep(3000L);
        threadInterrupt.interrupt();

        //join 예제
        //3초 정도 쉬고 끝남.
        Thread threadJoin = new Thread(() -> {
            System.out.println("ThreadJoin: "+Thread.currentThread().getName());
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
        threadJoin.start();
        //메인 쓰레드가 우리의 이 쓰레드 기다리게해보면.. -> 거기에 join 하면됨
        //기다린 다음에 아래가 출력. 기다리지 않으면 = join이 없으면? -> 아무때나 출력
        threadJoin.join(); //3초 쓰레드가 끝날때까지 기다렸다가 아래를 실행합니다. -> 기다리는 도중에 누가 interrupt 하면 interrupt exception이 발생.
        System.out.println(threadJoin + "isfinshed");

        //-> 멀티 쓰레드 프로그래밍의 문제 복잡복잡.. 쓰레드 관리가 너무 코딩으로 관리하는건 너무 어렵고 안됨 -> executor이 나오고 future사용 가능. 이제서야 completableFuture...

    }

    //메인쓰레드에서 다른 쓰레드를 크게 두가지 방법으로 만들 수 있습니다.- 111.불편한 방법 : 쓰레드를 상속받아 run을 구현
    static class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) { //자는 동안에 누군가가 이 쓰레드를 꺠우면 이 떄 Exception 들어옴 -> 요말은 꺠우는 방법이 있다!
                e.printStackTrace();
            }
            System.out.printf("Thread: " + Thread.currentThread().getName());
        }
    }
}

//자바의 멀티쓰레드 프로그래밍에 있어서 아주 기본 지식
//Concurrent 소프트웨어
// - 동시에 여러가지 작업을 하는 소프트웨어
//자바에서 컨커런트 프로그래밍을 지원하는 것
// 1. 멀티 프로세싱(//processor builder을 사용하면 하나의 프로세서에서 다른 프로세서를 만드는게 가능) 2. 멀티 쓰레드 (요거맘ㄴ 보겟음)

//자바에서 멀티쓰레드를 하려면?

//쓰레드의 가장 주요한 기능 3개
//1. sleep() -> 쓰레드를 대기 시키는 것  -> 현재 쓰레드는 자니까 다른 쓰레드가 먼저 일을함.
//2. Interrupt() : 다른 쓰레드를 꺠우는 방법
//3. joun() : 다른쓰레드를 기다림