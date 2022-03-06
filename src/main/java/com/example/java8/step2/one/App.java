package com.example.java8.step2.one;

public class App {

    public static void main(String[] args) {
        Foo foo = new InterfaceDefaultStaticMethod("miyeon");
        foo.printName();
        foo.printNameUpperCase();

        //유틸리티나 헬퍼 메서드를 구현하고 싶은 겨우에는 static 메서드 만들 수 있음? -> Foo에 구현해 놓음
        //Foo라는 인터페이스의 스테틱 메서드 쓸 수 있음.
        Foo.printAnything();
    }
}
