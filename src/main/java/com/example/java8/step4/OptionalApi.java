package com.example.java8.step4;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionalApi {

    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();

        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        //stream에서 optional을 리턴하는게 몇몇 있음 -> 종료형 오퍼레이션
        //이걸로 시작하는게 있을 수도 있고 없을 수도 있으니

        Optional<OnlineClass> optional = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring")) //있 spring / 없 jpa
                .findFirst();

//        boolean present = optional.isPresent(); //java 11 부터는 isEmpty()도 제공
//        System.out.println(present);

        //optional에 있는 값을 꺼내 오는 것 : get
//        OnlineClass onlineClass = optional.get();
//        System.out.println(onlineClass.getTitle()); //값이 실제 들어 있는 경우는 이슈 없음. 근데 없으면 뭔가르 ㄹ머너 체크해야한다.

        //권장 - ifPresent
//        optional.ifPresent(oc -> System.out.println(oc.getTitle()));

        OnlineClass oc1 = optional.orElse(createNewClass()); //없으면 새로운 것을 만들어라
        System.out.println(oc1.getTitle());
        //근데 있을 떄도 만들고 없을 떄도 만드네.. 코드는 실행이 됨. -> 이상해 찜찜해 -> orElseGet을 쓰세요 supplier을 쓸 수 있음

        System.out.println("== orElseGet ==");
        OnlineClass oc2 = optional.orElseGet(OptionalApi::createNewClass);
        System.out.println(oc2.getTitle());

        //상수로 만들어 져 있는 거 : orElse 사용
        //동적으로 작업을 해서 만들어야한다 : orElseGet 사용

        //orElseThrow() 원하는 에러가 있을 떄 supplier로 지정해주면 되고
        System.out.println("== orElseThrow() ==");
//        OnlineClass oc3 = optional.orElseThrow(() -> {
//            return new IllegalStateException();
//        });
        //람다 익스프레션이나 메서드 레퍼런스 써서 일케 변경 가능
//        OnlineClass oc3 = optional.orElseThrow(IllegalStateException::new);
//        System.out.println(oc3.getTitle());

        //있다는 가정하에 값을 걸러내는 옵션. filter에 해당 되면 넘겨주고 없다하면 빈 값을 넘겨 주것지
        System.out.println("== filter ==");
        Optional<OnlineClass> oc4 = optional.filter(oc -> !oc.isClosed());
        System.out.println(oc4.isEmpty()); //비어 있는지를 체크

        //map 맵을 넘겨주면 그 타입을 갖고 있는 옵셔널이 나오겠지.
        System.out.println("== map ==");
        Optional<Integer> integer = optional.map(OnlineClass::getId);
        System.out.println(integer.isPresent());

        //근데 맵에서 꺼내려는 타입이 옵셔널이면 까고까야 쓰는데 복잡함 -> 이 떄 유용한게 flapmap
        Optional<Optional<Progress>> progress = optional.map(OnlineClass::getProgress);
        Optional<Progress> progress1 = progress.orElse(Optional.empty());
        progress1.get(); //이렇게 까지 꺼내놔야 쓸 수 있음 복잡..

        Optional<Progress> progress2 = optional.flatMap(OnlineClass::getProgress); //위와 같은 코드
        progress.get();

        //stream에서의 flapmap과 매우 다름 컨테이너에서 매핑 값을 끄집어내는 인풋은 하나지만 아웃풋은 여러개 일 떄 쓰는 stream의 flatmap
        //stream에서의 map은 일대일 매핑

       }

    private static OnlineClass createNewClass() {
        System.out.println("== New Class 생성 ==");
        return new OnlineClass(10, "New Class", false);
    }
}

