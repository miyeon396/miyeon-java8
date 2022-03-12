package com.example.java8.step7.annotation.declare;

import java.util.Arrays;
import java.util.List;

@Chicken1 //a. TYPE_USE인 경우
public class AnnotationChange1 {

    public static void main(@Chicken1 String[] args) throws @Chicken1 RuntimeException { //b. 던질 떄의 타입
        List<@Chicken1 String> names = Arrays.asList("miyeon"); //c. list 선언시 타입

    }

    //클래스가 어떤 특정한 타입을 사용하는 제네릭한 클래스. T나 R -> 타입 변수라고 부름.
    static class FeelsLikeChicken<@Chicken1 T> { //요 치킨을 지정할 수 있는게 @Target(ElementType.TYPE_PARAMETER) - 요거 없으면 못 씀. 컴파일 에러남.

        public static <@Chicken1 C> void print(@Chicken1 C c) { //같은 C랑 C를 썼지만, <C> : 타입 파라미터 / C c : 타입
            //d. 애노테이션을 타입 파라미터에 붙일 수 있다고 선언을 했으니 저 앞C에 붙일 수 있음.
            //e. C c -> Type_PARAMETER 경우에는 붙일 수 없지만 TYPE_USE를 선언하면 타입을 선언한 모든 곳에 붙일 수 있으니 쓰면 됨.

        } //어떤 특정한 타입의 인자를 받는 메서드. 접근 지시자와 리턴 타입 사이에
    }

}

//그밖에.. 기타 등등의 변화들
//애노테이션
//1. 애노테이션을 타입 선언부에도 사용할 수 있게 되었음. -> 아주 여러 타입에 ㅋㅋ @Target(ElementType.TYPE_USE)
//2. 애노테이션을 중복해서 사용할 수 있게 되었음. -> @Repetable(chickenContainer.class) -> 컨테이너가 한개 필요함
//ex) chiecken Interface