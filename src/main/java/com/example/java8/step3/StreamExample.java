package com.example.java8.step3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample {

    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        System.out.println("spring으로 시작하는 수업");

        //스트림은 컨페이어 벨트를 만드는 건데 특정 타입들이 벨트로 지다가는거.
        //여기선 온라인 클래스 인스턴스 타입의 데이터가 지나가겠지
        springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .forEach(oc -> System.out.println(oc.getId()));

        System.out.println("close 되지 않은 수업");
        springClasses.stream()
                .filter(oc -> !oc.isClosed())
                .forEach(oc -> System.out.println(oc.getId()));

        //메서드 레퍼런스와 스테틱 인스턴스까지 활용해서 더 간단히 할 수 있음. 임의의 객체의 임의의 인스턴스 참조 위의 예제와 같음
        springClasses.stream()
                .filter(Predicate.not(OnlineClass::isClosed))
                .forEach(oc -> System.out.println(oc.getId()));

        System.out.println("수업 이름만 모아서 스트림 만들기");
        //맵은 들어올 땐 onLineClass가 들어옴 얘는 나갈 때 다른 타입이 나감. foreach에는 String이 들어옴.
        //어떤 타입이 들어와서 어떤 타입으로 나가고 마지막엔 어떤 타입이 들어오는지가 중요하네
//        Stream<String> titleStream = springClasses.stream().map(oc -> oc.getTitle()); //아래 메서드 레퍼런스와 동일
        Stream<String> titleStream = springClasses.stream().map(OnlineClass::getTitle);
        titleStream.forEach(System.out::println);

        List<OnlineClass> javaClasses = new ArrayList<>();
        javaClasses.add(new OnlineClass(6, "The Java, Test", true));
        javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
        javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

        List<List<OnlineClass>> events = new ArrayList<>();
        events.add(springClasses);
        events.add(javaClasses);

        //list의 list -> flapmap
        //stream에 list가 흘러가서 그걸 납짝하게? 꺼내서 각각 온라인 클래스들을 꺼낼 거야 -> 하나의 온라인 클래스를 다루는 걸로 변경
        System.out.println("두 수업 목록에 들어 있는 모든 수업 아이디 출력");
//        events.stream().flatMap(list -> list.stream()) //리스트를 스트림으로 바꿔준거 메서드 레퍼런스로 아래와 같이 변경
        events.stream().flatMap(Collection::stream)
                .forEach(oc -> System.out.println(oc.getId())); //이제 list가 아닌 online class가 옴. 현재 오는 오퍼레이터의 타입이 뭔지 알아야함!

        //얘는 스트림 갖고는 안되는 얘제. 스트림의 이터레이터를 사용
        System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
        Stream.iterate(10, i -> i+1 )
                .skip(10)
                .limit(10)
                .forEach(System.out::println);
        //Stream.iterate(10, i -> i+1 ) -> 무제한 스트림이 만들어짐. 이 자체를 찍는다고 찍히지 않음. 터미널 오퍼레이터 까지 만들어줘야 뭐가 찍힘.

        //매치 하는지 보는 것
        System.out.println("자바 수업 중에 Test가 들어 있는 수업이 있는지 확인");
        //allmatch, anymatch
        boolean test = javaClasses.stream().anyMatch(oc -> oc.getTitle().contains("Test"));
        System.out.println(test);

        System.out.println("스프링 수업 중에 제목이 spring이 들어간 제목만 모아서 List로 만들");
        //filter를 먼저하거나 맵을 먼저하거나 상관은 없지만 각 연산 후에 결과 타입이 달라지니 유의

        //filter를 먼저하는 경우
        List<String> springContains = springClasses.stream()
                .filter(oc -> oc.getTitle().contains("spring"))
                .map(OnlineClass::getTitle)
                .collect(Collectors.toList());
        System.out.println(springContains);
        springContains.forEach(System.out::println);

        //map을 먼저하는 경우
        List<String> springContains2 = springClasses.stream()
                .map(OnlineClass::getTitle)
                .filter(s -> s.contains("spring"))
                .collect(Collectors.toList());
        System.out.println(springContains2);
    }

}

//stream operation 사용한 실습!