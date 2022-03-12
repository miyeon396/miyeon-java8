package com.example.java8.step7.arrparallel;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class ArrParallelSort {

    public static void main(String[] args) {

        int size = 1500;
        int[] numbers = new int[size];
        Random random = new Random();

        //랜덤하게 배열을 채워주고 시작하고 걍 sort -> 기본적으로 Dual-Pivot Sort 쓰는데 싱글쓰레드 씀. 한계가 있음.
        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
        long start = System.nanoTime();
        Arrays.sort(numbers);
        System.out.println("serial sorting took : " + (System.nanoTime() - start));

        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
        start = System.nanoTime();
        Arrays.parallelSort(numbers);
        System.out.println("parallel sorting took : " + (System.nanoTime() - start));
        //다시 랜덤하게 값을 채워주고 다시 parallel sort
        // ->  2 4 1 5 9 8 7 있다고 가정 시 쪼갬
        // 2 4 1       | 5 9 8 7
        // 2 | 4 1     |     5 9 | 8 7또 쪼갬
        //1 2 4 |      |   5 7 8 9 적당한 사이즈가 되었을 떄 합치면서 정렬
        // 1 2 4 5 7 8 9
        // 이런식의 합치고 쪼개는걸 ForkJoin을 사용하는 것.
        //-> 같은 알고리즘을 사용하더라도 빠름
     }
}

//Java8에서 배열 정렬 시 ForkJoin F/W 이용해서 병렬적으로 정렬 할 수 있음.
//알고리즘의 효율은 같지만 병렬로 처리하기 떄문에 정렬이 조금 빨리 됨.
