package com.example.java8.step2.one;

import java.util.Locale;

public interface Foo {
    //인터페이스에 있는 메서드는 모두 퍼블릭
    //인터페이스에 나중에 공통 기능을 추가 하고 싶다 -> 문제 인터페이스를 구현하는 모든 클래스에 메서드를 구현해줘야함. -> default 사용 ex) removeIf

    void printName();

    //상세 구현 @ImplSpec를 적어주는게 좋음 (얘도 Java8에 추가) 이걸 사용하는 클래스들이 이해하고 사용할 수 있도록.
    //문제 시 구현하는 부분에서 오버라이딩이 가능.
    /**
     * @implSpec
     * 이 구현체는 getName()으로 가져온 문자열을 대소문자로 바꿔 출력한다.
     */
    default void printNameUpperCase() {
        System.out.println(getName().toUpperCase());
    }

    String getName();

    default void printId() {

    }

    static void printAnything() {
        System.out.println("Foo");
    }
}


//제약사항
//equals, hashcode, toString 등과 같이 object에서 제공하는 것은 재정의할 수 없다.