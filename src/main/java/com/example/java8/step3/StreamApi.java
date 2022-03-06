package com.example.java8.step3;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamApi {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("miyeon");
        names.add("coco");
        names.add("babo");
        names.add("foo");

        //또 다른 스트림이 되는거지 스트림으로 전달받은 대문자로 바뀌는게 아님 -> 데이터를 바꾸지 않는다.
        Stream<String> stringStream = names.stream().map(String::toUpperCase);

        //중개 오퍼레이터, 종료 오퍼레이터로 나눌 수 있음. 중개 오퍼레이터는 근본적으로 lazy하다.
        //종료 오퍼레이터는 스트림을 리턴한다.
        Stream<String> stringStream2 = names.stream().map(s -> {
            System.out.println(s);
            return s.toUpperCase();
        });
        //이게 출력이 될 것이냐 안된다! -> 중개형 오퍼레이터는 종료 오퍼레이터가 오기전까지 실행이 안됨.

        List<String> strList = names.stream().map(s -> {
            System.out.println(s);
            return s.toUpperCase();
        }).collect(Collectors.toList());
        //중개형 오퍼레이터는 여러개 와도됨. 종료형 오퍼레이터는 한개 와야함.

        //원본 소스는 바뀌지 않음 names는 그대로

        //손쉽게 병렬 처리를 할 수 있다.
        //stream 없이 병렬처리 loop 돌면서 그러기 어려움
        for(String name: names) {
            if(name.startsWith("m")) System.out.println(name.toUpperCase());
        }

        //stream의 병렬 처리 jvm이 알아서 쪼개서 해줌
        List<String> collect = names.parallelStream().map( s -> {
            System.out.println(s + " " + Thread.currentThread().getName());
            return s.toUpperCase();
        }).collect(Collectors.toList());
        collect.forEach(System.out::println);
        //parallelStream 쓴다고 다 빨라지는게 아님. 쓰레드 만들어 처리하는 비용이 절대적으로 듬. 이런게 오히려 오래걸릴 수도 있음.
        //그치만 데이터가 정말 방대할 때 더 유리할 수 있음. 그런거 아니라면 걍 stream 쓰면 됨. 딱 언제 유용하다 말하기는 어려움.

    }

}

//연속된 데이터를 처리하는 오퍼레이션들의 모음. 자체가 데이터는 아님
//컬렉션은 데이터를 가지고 있는거 스트림은 그런 데이터를 소스로 사용해서 전달을하는 것
//functional in nature 근본적으로 함수적이다 + 소스를 변경하지 않는다.
//스트림을 처리하는건 한번만 한다.
//무제한일 수도 있다. short circuit 메서드 이용해서 첫 몇개만 본다 제한할 수 있다.