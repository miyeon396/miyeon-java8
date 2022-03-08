package com.example.java8.step5;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateAndTime {
    public static void main(String[] args) throws InterruptedException{

        //기존에 있었던 데이트 라이브러리 -> 굉장히 불편 date, calendar, simpledateformat
        Date date = new Date(); //date이지만 시간까지도 나타낼 수 있고 타임스탬프도 나타낼 수 있음
        long time = date.getTime(); //날짜에서 시간을 가져온다고? 이상해.. 이포크타임 기준으로 milliseconds 리턴
        System.out.println("date=" + date);
        System.out.println("time="+ time);

        Thread.sleep(1000 * 3);
        Date after3Seconds = new Date();
        System.out.println(after3Seconds);
        after3Seconds.setTime(time);
        System.out.println(after3Seconds);
        //똑같은 인스턴스인데 데이터가 맘대로 바뀜.. 객체의 상태를 바꿀 수 있따. mutable 하다 -> 멀티쓰레드 환경에서 안전하게 쓰기 어렵다.
        //issue
        //1. thread1에서 데이터 바꾸는 중간에 thread2에서 데이터를 읽어와서 바꿀 수 있따. mutable라면 정상적이라면 t1에서 끝나고 해야하는데 아닐 수 있어서 = thread safe하지 않다. ex)date
        //2. 버그 여지가 많음 type safety가 많다. 아무 숫자나 다들어와버림.. ex) calendar
        //이런 문제가 많아서 java8 이전에 jodatime 많이 썼었음
        //몇가지 원칙이 있었음.
        //1. 명확해야함.
        // 2. immutable 해야함 기존 인스턴스에 더해주면 새 값이 나옴
        // ex) LocalDate dateOfBirth = LocalDate.of(2012, Month.MAY, 14);
        // LocalDate firstBirthday = dateOfBirth.plusYears(1);
        // 3. fluent : null을 리턴받거나 null 인게 없기 때문에 계속 이어서 연산한다.
        // 4. extensible : 불교 달력 등 그레고리력 외의 여러 시스템들의 것들을 제공함.(=여러 많은 달력 시스템을 제공함)


        Calendar calendar = new GregorianCalendar();
        Calendar miyeonBirthDay = new GregorianCalendar(1993, 2, 14); //인텔리제이가 월에 경고 줌 조심해서써야한다 상수 쓰게 권장 0부터 1월이라 실수할 여지가 많아서
//        Calendar miyeonBirthDay = new GregorianCalendar(1993, Calendar.JANUARY, 14);
        System.out.println(miyeonBirthDay.getTime()); // 얘도 이상 ㅋㅋ calendar에서 getTime 하면 date가 나옴. 얜 또 뭐야
        miyeonBirthDay.add(Calendar.DAY_OF_YEAR, 1); //얘도 값 변경이 가능함.
        System.out.println(miyeonBirthDay.getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat();


        //사람용 API vs 기계용 시간 2개로 나눌 수 있음.
        Date date1 = new Date(); //사람용
        long time1 = date.getTime(); //기계용
    }
}
