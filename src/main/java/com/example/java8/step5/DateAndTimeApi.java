package com.example.java8.step5;

import org.apache.tomcat.jni.Local;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateAndTimeApi {

    public static void main(String[] args) {

        // 1. 기계적 시간 (date와 비슷) = 시간을 재거나, 메서드 실행 시간을 비교하거나 이런거
        Instant instant = Instant.now();
        System.out.println(instant); //기준시 UTC, GMT
        System.out.println(instant.atZone(ZoneId.of("UTC"))); //utc check

        ZoneId zone = ZoneId.systemDefault();
        System.out.println(zone);
        ZonedDateTime zonedDateTime = instant.atZone(zone);
        System.out.println(zonedDateTime);

        // 2. 휴먼용 - 서버에 배포가 되면 서버의 존 데이트 타임 시간 갖고 체크됨.
        LocalDateTime now = LocalDateTime.now();
        System.out.println("== human example ==");
        System.out.println(now);
        //of를 이용하여 특정한 시간대를 이용한 시간 만듦이 가능함
        LocalDateTime birthDay =
                LocalDateTime.of(1993, Month.JANUARY, 14, 0, 0, 0);
        //특정 존의 현재 시간, 시간 보고 싶다.
        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println(nowInKorea);

        //그치만 이것은 instant에서도 atzone 사용해서 쓸 수 있음. 서로 변환이 가능함
        //instant -> zonedDateTime -> localDateTime 일케일케 변환 가능함 서로 왔다갔다 가능함
        Instant nowInstant = Instant.now();
        ZonedDateTime zonedDateTime1 = nowInstant.atZone(ZoneId.of("Asia/Seoul"));
        System.out.println(zonedDateTime1);

        // 3. 기간을 조회 하는 방법. Period(날짜) 휴먼용 비교
        LocalDate today = LocalDate.now();
        LocalDate thisYearBirthDay = LocalDate.of(2023, Month.JANUARY, 14);
        System.out.println("== Period example ==");

        // 생일까지 몇일 남았는지의 예제 여러가지 같은 식 구하는 여러가지 방법
        Period period = Period.between(today, thisYearBirthDay);
        System.out.println(period.getDays());

        Period until = today.until(thisYearBirthDay);
        System.out.println(until.get(ChronoUnit.DAYS));

        // 4. 기간을 조회하는 방법 Duration() 머신용 비교
        System.out.println("== Duration example ==");
        Instant now1 = Instant.now();
        Instant plus1 = now1.plus(10, ChronoUnit.SECONDS);
        Duration between = Duration.between(now1, plus1);
        System.out.println(between.getSeconds());

        // 5. formatting -> localDateTime <-> 문자열
        System.out.println("== Formatter example ==");
        LocalDateTime now2 = LocalDateTime.now();
        DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println(now2.format(MMddyyyy));
        System.out.println(now2.format(DateTimeFormatter.ISO_DATE));  //굳이 새로 만드는게 아니라 미리 정의되어 있는거 사용해도됨. docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#predefind

        // 6. Parsing
        LocalDate parse = LocalDate.parse("01/14/1993", MMddyyyy);
        System.out.println("== Parse example ==");
        System.out.println(parse);

        // 7. Legacy API 지원
        // 예전 API와 호환이 됨. ex) date <-> instant, GregorianCalendar <-> LocalDateTime, ZoneId <-> TimeZone ... etc
        Date date2 = new Date();
        Instant instant2 = date2.toInstant();
        Date newDate = Date.from(instant2);

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        ZonedDateTime dateTime2 = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault());
        GregorianCalendar from = GregorianCalendar.from(dateTime2);

        ZoneId zoneId = TimeZone.getTimeZone("PST").toZoneId(); //old to new
        TimeZone timezone = TimeZone.getTimeZone(zoneId); //new to old


        //뒤에 연산 붙여서 쭉쭉 사용하면 됨.
        //예전 API 쓰듯이 now.plus(10, ChronoUnit.DAYS);만 쓰면 아무것도 일어나지 않음. 새로운 인스턴스로 생성 되는 것임
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime plus10 = nowTime.plus(10, ChronoUnit.DAYS);
        LocalDateTime plus5 = nowTime.plusDays(5);
        System.out.println("== 연산 example ==");
        System.out.println(nowTime);
        System.out.println(plus10);
        System.out.println(plus5);

    }
}

// java8에 들어온 대표적 몇가지 date, time api를 살펴봄