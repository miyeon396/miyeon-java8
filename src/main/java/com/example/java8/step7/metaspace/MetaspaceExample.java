package com.example.java8.step7.metaspace;

public class MetaspaceExample {

    public static void main(String[] args) {

    }
}

//Java8 GC 관련 중요 변경사항.
//흔히 PermGen라고 부르는 영역이 사라지고 Metaspace 영역이 생김.

//- PermGen
//클래스의 이름. static한 필드들. 클래스를 많이 쓰면 많이 쓸수록 이 공간에 데이터가 많이 쌓였음.
//원래는 힙 공간의 일부였음(자바에서 메모리 관리하는 부분.)
// YoungGeneration, OldGeneration - GC의 기본적 개념이니 알아두길..(각각에서 GC가 어떻게 동작하는지 정도 )

//그림.
//OS에서 제공하는 메모리는 Native Memory라고 부름
//힙 영역이 있음
// Eden(young) | old(만들어진지오래) | PermGen(커봤자 몇백메가) -> 진짜 클래스 로딩이 많거나 동적으로 클래스를 많이 생성할 때 PermGen영역이 많이 차는 경우가 있음
// PermGen영역은 고정된 크기를 가지고 생성이 됨. 그안에 데이터가 쌓이는데.. 넘쳐버리면 GC를 하는데 그래도 넘쳐 -> 바로 에러..
// -> 이떄? PermGen 사이즈를 늘리는데 근본 해결책은 아님. 어디선가 동적으로 클래스를 마구 생성하는데 이걸 해결을 해야함. 그래야 근본적 처리.

//Java8 그림
//jvm 힙에 permgen 영역이 사라짐
// Eden(young) | old
// native Memory (=Metaspace) -> permgen은 여기 자리 잡음. 고정된 사이즈가 없음. 지나치게 늘어나지 않는지 정도만 모니터링할 필요 있음. 필요한 만큼 우리 os의 native memory가 다 찰때까지 계속 들어남(뭐 이정도까진 안되겠지만 ㅋㅋ)
// -> metaspace에 최대값을 설정. -XX:MaxMetaspaceSize=N
//중간중간 늘리지 않고 죽이면서 하고싶다? -> 초기 사이즈와 최대 사이즈를 같은 값을 줘라. -XX:MaxMetaspaceSize=N / -XX:MetaspaceSize=N
// 효율적으로 모니터링 필요 -> jstat이라는 command가 제공하는 옵션 사용시 . 메타스페이스 영역에 얼마만큼의 메모리를 사용하고 있는지.. -> 그 값을 적절한 값을 찾는게 중요. max 값정도 찾아서 적용해놓으면 아주 good
//https://docs.oracle.com/en/java/javase/13/docs/specs/man/jstat.html

//가장 중요한 차이점.
//펌젠은 heap영역에 속해있엇고, 제한된 크기를 갖고 있었지만,
//메타스페이스로 바뀌면서 힙 영역이 아니라 native memory영역으로 바뀌었고
//기본값으로 제한된 크기를 갖고 있찌 않다. 보통은 이게 문제가 안될 것인데 정말로 큰 프로젝트라서 내가 일거야하느 ㄴ클래스가 기본적으로 64메가 보다 크다 하면 알아서 늘려서 해결하겠지만, 어딘가 클래스 자꾸 생성 누수가 있따. 해결해야지
//  -> 결국 모니터링해서 최적 max 값 찾아서 하란 얘기