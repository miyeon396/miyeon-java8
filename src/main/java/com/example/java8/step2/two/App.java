package com.example.java8.step2.two;

import java.util.*;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        
        //forEach -> consumer 각각 스트링이 차례대로 들어올 거고 반환 값이 없는거
        List<String> name = new ArrayList<>();
        name.add("miyeon");
        name.add("coco");
        name.add("babo");
        name.add("hohoho");

        name.forEach((i) -> System.out.println(i));
        name.forEach(System.out::println); //같은 것
        for (String n : name) {
            System.out.println(n);
        } //같은 것

        //split을 할 수 있는 iterator 다음 게 없으면 false가 return;
        Spliterator<String> spliterator = name.spliterator();
        spliterator.trySplit();
        while(spliterator.tryAdvance(System.out::println));

        //두개의 spliterator로 나누고 출력
        //parellel하게 처리할 때 유용. stream의 기반
        System.out.println("== spliterator example ==");
        Spliterator<String> spliterator2 = name.spliterator();
        Spliterator<String> spliterator3 = spliterator2.trySplit();
        while(spliterator2.tryAdvance(System.out::println));
        System.out.println("===========");
        while(spliterator3.tryAdvance(System.out::println));

        //Stream
        System.out.println("== Stream example ==");
        List<String> k = name.stream().map(String::toUpperCase)
                .filter( s -> s.startsWith("M"))
                .collect(Collectors.toList());
        System.out.println(k);

        //removeIf
        System.out.println("== removeIf Example ==");
        name.removeIf(s -> s.startsWith("b"));
        name.forEach(System.out::println);

        //comparator 정렬시 쓰임 -> functional interface임
        System.out.println("== comparator Example ==");
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        name.sort(compareToIgnoreCase.reversed().thenComparing(String::compareTo)); //thenComparing를 통해 연결 가능
        name.forEach(System.out::println);

        //static reverseOrder / naturalOrder
        Integer[] a = {2,3,4,1,5};
        Arrays.sort(a, Collections.reverseOrder());
        System.out.println("== Reverse Order Example ==");
        System.out.println(Arrays.toString(a));

        //static nullsFrist / nullsLast
        //static <T> Comparator<T> nullsFirst(Comparator<? super T> comparator)
        System.out.println("== NullsFirst Example ==");
        String[] strArr = {"miyeon", "test", null, "coco"};
        Arrays.sort(strArr, Comparator.nullsFirst(Comparator.naturalOrder()));
        Arrays.sort(strArr, Comparator.nullsLast(Comparator.reverseOrder()));
        System.out.println("strArr="+Arrays.toString(strArr));


    }

    //JAVA8에 추가된 기본 메서드와 스태틱 메서드
    //가장 흔히 사용하는 인터페이스 중 몇가지

    //Iterable
    //Collection
    //Comparator

    //api의 설계나 제공하는 라이브러리 쪽에서 변화가 생김
    //인터페이스에 추상메서드 3개 있다고 할 때 -> 해당 인터페이스를 구현하는 추상클래스를 하나 만들고 비어있는 구현을 만듦. 5,6년전쯤 클래스들. a는 a만 구현하고 b는 b만 구현하고 c는 c만 구현하게 abstract를 만들어놓음
    //편의성을 위해 상속을 사용
    //근데 이 편의성을 이젠 인터페이스가 해줌 -> 기본 메서드를 사용해서 abc해놓음. -> 그럼 실제 구현해야하는 클래스는 interface를 implements를 . 비 침투성 개발자들에게 상속을 강제하지 않는 비 침투성을 스프링이 강조함.
    //ex) WebMvcConfigure / WebMvcConfigurer -> Deprecated
    //-> 자바 8 이후 부터는 기본 메서드 이용해서 api 좀더 간결하게 제공할 수 있음.
    
}
