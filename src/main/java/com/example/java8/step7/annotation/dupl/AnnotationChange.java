package com.example.java8.step7.annotation.dupl;

import java.util.Arrays;

@Chicken("양념")
@Chicken("간장")
public class AnnotationChange {
    //중복 애노테이션 예제

    public static void main(String[] args) {
        //가져다가 쓰면 되는데
        //1. 치킨타입으로 바로 읽어오는 방법. 두개의 애노테이션이 배열로옴.
        Chicken[] chickens = AnnotationChange.class.getAnnotationsByType(Chicken.class);
        Arrays.stream(chickens).forEach(c -> {
            System.out.println("1 = "+c.value());
        });

        //2. 컨테이너 타입으로 가져오는 방법.
        //치킨 컨테이너로 가져온다음. 
        ChickenContainer chickenContainer = AnnotationChange.class.getAnnotation(ChickenContainer.class);
        //value가 chicken의 array..
        Arrays.stream(chickenContainer.value()).forEach(c -> {
            System.out.println("2 = "+c.value());
        }); //컨테이너가 무조건 있어야하기 떄문에 컨테이너에서도 가져올 수 있다.
        //이걸 리소스나 v코드에 어케 활용할 수 있을까.
    }

}

//그밖에.. 기타 등등의 변화들
//애노테이션
//1. 애노테이션을 타입 선언부에도 사용할 수 있게 되었음. -> 아주 여러 타입에 ㅋㅋ @Target(ElementType.TYPE_USE)
//2. 애노테이션을 중복해서 사용할 수 있게 되었음. -> @Repetable(chickenContainer.class) -> 컨테이너가 한개 필요함
//ex) chiecken Interface