package com.example.java8.step4;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Optional<P> {

    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();

        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        //코드가 무사히 실행이 될 지? 안될 지?
//        OnlineClass spring_boot = new OnlineClass(1, "spring boot", true);
//        Duration studyDuration = spring_boot.getProgress().getStudyDuration();
//        System.out.println(studyDuration);
        //progress 가 기본값이 null이 기때문에 null에 어떤 operation 하려 하니 null을 참조 할 수 없으니 에러가 남.

        //-> 그래서 보통 일케 널처리함 -> 좋지는 않음. 사람이기 떄문에 까먹을 수 있음.
//        OnlineClass spring_boot = new OnlineClass(1, "spring boot", true);
//        Progress progress = spring_boot.getProgress();
//        if( progress != null)  System.out.println(progress.getStudyDuration());

        //-> 널 에러 해결. 1. 매번 할 수 없다. 2. 널을 리턴하는거 자체가 문제 (자바8 이전엔 어쩔 수 없었음.)
        //자바 8부터는 비어있는 값이 전달 될 경우에 명시적으로 해결 할 수 있다. -> Optional (OnlineClass..)
        //옵셔널이라는 박스를만들어 객체를 담음. 널일 수도 있고 뭔가 담겨있을 수도 있고 뭐가 없을 수도 있고..
        //Optional.of : 무조건 널 아닐 때 이땐 널 들어오면 에러
        //Optional.ofNullable : 널일 수 있는 애들
        //권장 사항
        // 1. 리턴 값으로만 사용해라. 메서드 매개변수로 비권장. null을 명시적으로 줘버리는 경우도 있음. 널체크를 굳이 또 해야함 별로 권장하지 않음.
        // 2. 맵의 키 타입에 옵셔널을 쓰지 말아라 (맵의 가장 큰 특징 중 하나가 키는 널이 아니다인데 이 규칙을 꺨 수도 있는 여지를 주는거)
        // 3. 필드에 쓰지 말아라. 있을 수도 있고 없을 수도 있는 것에 쓰지 말아라...
        // 4. primitive type 용 옵셔널은 따로 있다.Optional.of(10); 이 가능은 하나 박싱 언박싱이 안에서 내부적으로 이루어 지기 때문에 이렇게 쓰는게 나음. OptionalInt.of(10);
        // 5. optional에 null을 리턴하지 마라 -> 이걸 쓰는 클라이언트는 옵셔널이 제공하는 널을 처리하는 메서드 쓸 때 또 널 에러가남.. 차라리 빈 값이면 empty를 리턴하라 return Optional.empty()
        // 6.  Collection, Map, Stream. Array,옵셔널은 옵셔널로 또 감싸지 말아라. (그 자체로 비어 있는지 안 비어 있는지 알 수 있는 친구들)
        }
}

//Optional Java8에 새롭게 추가된 인터페이스 바어 있을 수도 있고 값을 하나만 담고 있을 수도 있는 것