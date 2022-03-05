package com.example.java8.step1;

import java.util.function.*;

public class Plus10 implements Function<Integer, Integer> {
    @Override
    public Integer apply(Integer integer) {
        return integer + 10;
    }


    public static void main(String[] args) {
        Plus10 plus10 = new Plus10();
        System.out.println(plus10.apply(1));

        //람다 통해서 바로 구현도 가능
        Function<Integer, Integer> plus10V2 = (i) -> i + 10;
        System.out.println(plus10V2.apply(1));

        Function<Integer, Integer> multiply2 = (i) -> i * 2;

        //compose -> 값이 들어오면 곱하기 2 먼저하고 그 결과 값을 plus10의 입력값으로 사용하고 결과가 나옴 뒤먼저앞나중
        Function<Integer, Integer> multiply2AndPlus10 = plus10.compose(multiply2);
        System.out.println(multiply2AndPlus10.apply(2));

        //andThen -> plus10의 결과 값을 multiply2를 하는 것. 앞먼저뒤나중
        Function<Integer, Integer> plus10AndThenMultiply2 = plus10.andThen(multiply2);
        System.out.println(plus10AndThenMultiply2.apply(2));

        //3. Consumer
        Consumer<Integer> printT = System.out::println; //메서드 레퍼런스
        printT.accept(5);
        //= Consumer<Integer> printT = (i) -> System.out.println(i);

        //4.Supplier
        Supplier<Integer> get10 = () -> 10;
        System.out.println(get10);

        //5. Predicate
        Predicate<String> startsWithMiyeon = (s) -> s.startsWith("miyeon");
        Predicate<Integer> isEven = (i) -> i % 2 == 0;
        System.out.println(startsWithMiyeon.negate().test("miyeon"));

        //6. UnaryOperator
        UnaryOperator<Integer> plus10V3 = (i) -> i + 10; // = Function<Integer, Integer> plus10V3 = (i) -> i+ 10;
        System.out.println(plus10V3.apply(1));

        //7. BinaryOperator
        BinaryOperator<Integer> plus = (i, j) -> i+j;
        System.out.println(plus.apply(1,2));


    }
}


//함수형 인터페이스 자바에서 기본 제공
//1. Function
//값을 하나 받아서 뭔가 하나 리턴하는 함수 R apply(T t)
//미리 정의되어 있는 함수 조합하는 디폴트 메서드 제공 ex) compose, andThen
//2. BiFunction<T,U,R>
//두개의 값을 입력받아서 R을 리턴
//3. Consumer<T>
//안에서 리턴을 할 수가 없음. 타입을 받아서 아무 값도 리턴하지 않는 함수 인터페이스
//4. Supplier<T>
// 뭔가를 받지 않고 무조건 리턴하겠다
//5. Predicate<>
// 어떠한 인자값을 하나 받아서 true, false 리턴해주는 함수 인터페이스
//boolean으로 결과 값이 나오기 때문에 and, or , negate(not을 붙임) 조합이 가능함
//6. UnaryOperator
//함수형 인터페이스의 특수한 케이스
//입력과 결과의 타입이 같은 경우 UnaryOperator을 사용 가능. Function 상속 받앗음
//7. BinaryOperator
//BiFunction의 특수한 케이스 3개의 타입이 전부 같을 경우 줄여서 쓰는 것
//위까지 한걸로 나머지 함수 인터페이스 추측이 가능함 ex) BiConsumer<T, U> 두개의 값을 받아서 아무 값도 리턴하지 않는 함수 인터페이스
//상상이 안되는 것만 체크 해 보면 굿.