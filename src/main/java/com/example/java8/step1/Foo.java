package com.example.java8.step1;

import java.util.function.Function;

public class Foo {

    public static void main(String[] args) {
        //함수형 인터페이스는 인라인으로 구현한 오브젝트 -> 함수 자체를 파라미터로 보내거나 리턴값으로 받을 수도 있는 것. -> 고차 함수

        //같은 값을 넣엇을 떄 같은 값이 나와야함!

        //순수한 함수라고 볼 수 없는 경우 -> 함수 내부에서 값을 변경하는 경우..
        //함수 내부에서 밖의 변수를 final이라고 가정하에 쓰고 있기 때문에 값을 변경해버리면 컴파일 에러가 발생함 -> 함수 내부에서는 밖의 변수 변경하면 안됨.

        // 익명 내부 클래스 (runSomething를 구현)
        RunSomething runSomething = new RunSomething() {
            @Override
            public void doIt() {
                System.out.println("hello");
                System.out.println("world");
            }
        };
        runSomething.doIt();

        //java8부터는 인터페이스가 하나인 경우 더 줄여서 쓸 수 잇음 람다식으로 사용해서 줄일 수 있음.
        RunSomething runSomething1 = () -> {
            System.out.println("hello");
        };
        runSomething1.doIt();

        //한줄인 경우
        RunSomething runSomething2 = () ->System.out.println("hello");
        runSomething2.doIt();



    }


}
