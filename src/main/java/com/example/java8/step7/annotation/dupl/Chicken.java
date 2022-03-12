package com.example.java8.step7.annotation.dupl;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) //Retention : 이 애노테이션을 언제까지 유지할 것인가. java5 기초지식..-> 애노테이션 자체에 대한 공부 필요.
//@Target(ElementType.TYPE_PARAMETER) //Target : 애노테이션을 사용할 곳. java8에 Type Parameter(제네릭 만들 때 타입 변수=타입 파라미터), TYPE_USE 두개가 추가됨
@Target(ElementType.TYPE_USE)
@Repeatable(ChickenContainer.class)
public @interface Chicken {
    String value();
}
