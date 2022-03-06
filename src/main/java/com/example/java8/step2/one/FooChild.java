package com.example.java8.step2.one;

public interface FooChild extends Foo{
    //Foo가 제공하는 기본 구현체를 제공하고 싶지 않다.
    //추상 메서드로 정의하면 된다. 이걸 안하면 기본 구현체로 제공을 한다.
    void printNameUpperCase();

}


