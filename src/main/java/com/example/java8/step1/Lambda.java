package com.example.java8.step1;

import java.util.function.*;

public class Lambda {

    public static void main(String[] args) {
        UnaryOperator<Integer> plus10 = (i) -> i + 10;
        UnaryOperator<Integer> multiply2 = (i) -> i * 2;

        //우리가 구현해야하는 추상메서드가 파라미터를 하나도 받지 않는 경우
        Supplier<Integer> get10 = () -> {
          return 10;
        };
        Supplier<Integer> get10V2 = () -> 10; //바디가 한줄이라면 생략 가능

        //인자의 값이 두개 인경우
        BiFunction<Integer,Integer,Integer> plus = (a,b) -> a+b;
        BiFunction<Integer,Integer,Integer> plusV2 = (Integer a, Integer b) -> a+b; // 타입 적어 줘도 되지만 변수 선언부에 적어져 있기 때문에 안정의 해도됨.
        BinaryOperator<Integer> plusV3 = (a, b) -> a+b; // BinaryOperator로 줄일 수 있음

        //람다를 감싸고 있는
        Lambda lambda = new Lambda();
        lambda.run();
    }

    private void run() {

        int baseNumber = 10;
        //함수 안에서 로컬 변수 참조
//        baseNumber++; 안됨.

        //익명클래스, 로컬클래스, 람다 다른게 있음
        //공통 : 참조할 수 있다. 자바8부터는 final을 생략할 수 있따. 그치만 사실상 저 변수가 final이다 못바꾼다. final 키워드가 없지만 어디서든 변수 변경하지 않는 경우. 요 변수를 셋다 모두 참조 가능하다
        //얘네랑 람다랑 다른거 : shadowing. 로컬,익명은 별도의 스콥이라 쉐도윙이 되는데 람다는 사실상 같은 스콥이라 안됨. ->

        //로컬클래스
        class LocalClass {
            void printBaseNumber() {
                int baseNumber = 11; //이걸 추가 하게 되면 아래는 10이 아니라 11이 찍힘 -> 이게 쉐도윙 (변수가 다른 변수 가리는거)
                System.out.println(baseNumber);
            }
        }

        //익명 클래스
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                int baseNumber = 11; //이걸 추가 하게 되면 아래는 10이 아니라 11이 찍힘 -> 이게 쉐도윙 //파람으로 baseNumber를 받아도 쉐도윙
                System.out.println(baseNumber);
            }
        };

        //람다
        //람다는 같은 스콥이기 떄문에 동일한 변수를 또 정의할 수 없다
//        IntConsumer printInt = (baseNumber) -> { //같은 스콥이라 이렇게 정의 불가 Variable 'baseNumber' is already defined in the scope
        IntConsumer printInt = (i) -> {
            System.out.println(i+baseNumber); //익명 클래스, 내부 클래스(내부에 정의한 로컬 클래스), 람다에서 로컬 변수 참조. 자바8이전에는 final 써줘야 됫었음
        };

        printInt.accept(10);
    }
}
