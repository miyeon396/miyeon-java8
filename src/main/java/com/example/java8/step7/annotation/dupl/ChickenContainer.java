package com.example.java8.step7.annotation.dupl;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface ChickenContainer {
    //치킨애노테이션들을 감싸고 있는 치킨 컨테이너 애노테이션
    //치킨애노테이션들의 리텐션(얼마나 유지), 타겟(어디에 선언) 정보가 치킨컨테이너에도 들어가지만 더 같거나 넓어야한다. (=보통 평범한 상황에서는 걍 같이 주면됨.)

    Chicken[] value(); //안에다가 자기 자신이 감싸고 있는 어노테이션의 배열로 가지고 있어야함.
}
