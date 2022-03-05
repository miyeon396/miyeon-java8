package com.example.java8.step1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class MethodReference {

    private String name;

    public MethodReference() {

    }

    public MethodReference(String name) {

    }

    public String hello(String name) {
        return "hello " + name;
    }

    public static String hi(String name) {
        return "hi " + name;
    }

    public static void main(String[] args) {

        UnaryOperator<String> hi = (s) -> "hi " + s; //입력, 결과 타입이 같은 경우

        //1. static Method 타입::스태틱 메서드
        UnaryOperator<String> hiV2 = MethodReference::hi; //입력, 결과 타입이 같은 경우

        //2. 특정 객체의 인스턴스 메서드 참조 : Instance Method 객체레퍼런스::인스턴스메서드
        MethodReference methodReference = new MethodReference();
        UnaryOperator<String> hiV3 = methodReference::hello;

        //3. 생성자 참조 -> 타입::new
        Supplier<MethodReference> newMethod = MethodReference::new; //이 자체는 아무일도 발생하지 않음
        newMethod.get(); // 요거까지 해야 만들어 지는 것

        //4. 임의 객체의 인스턴스 메서드 참조 -> 타입::인스턴스메서드
        Function<String, MethodReference> miyeonGreeting = MethodReference::new; //메서드 래퍼런스만 보면 같지만 실제 만들어지는 것은 다름. 문자를 받는 생성자가 생성이 됨.

        //Comparator가 자바8부터는 추상 메서드로 바뀌어서 우리가 구현해야하는게 int compare(T o1, T o2);
        //as-is
        String[] names = {"miyeon", "coco", "babo"};
        Arrays.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });

        //to-be
        Arrays.sort(names, String::compareToIgnoreCase); //비교해서 인트값 넘겨 주는 메서드
        System.out.println(Arrays.toString(names));


    }

    //메서드 레퍼런스
    //람다 표현식을 구현할 때 사용할 수 있는거. 구현을 하는게 아니라 그 메서드가 있따면 그걸 참조하는 거. 콜론이 두개 찍혀있는 것

}
