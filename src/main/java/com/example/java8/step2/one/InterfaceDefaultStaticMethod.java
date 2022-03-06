package com.example.java8.step2.one;


public class InterfaceDefaultStaticMethod implements Foo, Foo2{

    String name;

    public InterfaceDefaultStaticMethod(String name) {
        this.name = name;
    }

    @Override
    public void printName() {
        System.out.println(this.name);
    }

    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public void printNameUpperCase() {
        System.out.println("default도 재정의 가능"+this.name.toUpperCase());
    }



    //인터페이스가 두개 있을 때 둘다에 동일 default method가 있을 때, 둘 중 어떤거 써야할지 자바는 판단 못하기 때문에 컴파일 에러가 남 -> 이 충돌하는 디폴트 메서드가 있는 경우엔 직접 오버라이드 해야함
    //ex) Foo Foo2 printId
    @Override
    public void printId() {
        Foo2.super.printId();
    }


}

//자바 8의 새로운 기능
//인터페이스가 있다고 가정할 때 default method 추가
//인터페이스 구현하는 모든 인스턴스에 추가적 기능이 생기게끔 해주는 기능이므로 강력하고 주의해야할 기능.


