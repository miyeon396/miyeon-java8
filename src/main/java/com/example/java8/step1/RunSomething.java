package com.example.java8.step1;

@FunctionalInterface
public interface RunSomething {
    //함수형 인터페이스 : 인터페이스 안에 추상 메서드가 딱 하나만 있는 것.
    //정의할 일이 있다면 위에 @FunctionalInterface 어노테이션 붙여줘서 관리하면 좋음.
    //예_2개이상 abstract 메서드가 존재하는데 FunctionalInterface 어노테이션이 붙은 경우 에러 뱉음
    //실제 사용하려면 인터페이스의 구현체를 만들어서 써야함. -> 익명 내부 클래스

    abstract void doIt(); //abstract 생략 가능

    static void printName() {

    }  //java8 부터

    default void printAge() {

    } //java 8 부터 인터페이스 정의 메서드 형태가 더욱 다양해 짐. default, static 등
}

