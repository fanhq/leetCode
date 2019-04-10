package com.fanhq.example.common;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanhaiqiu
 * @date 2019/4/10
 */
public class Lambda {

    public static void main(String[] args) {
        List<String> alpha = Arrays.asList("a", "b", "c", "d");

        // Java 8
        List<String> collect = alpha.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(collect);

        // Extra, streams apply to any data type.
        List<Integer> num = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> collect1 = num.stream().map(n -> n * 2).collect(Collectors.toList());
        System.out.println(collect1);

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.println(squaresList);

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        long count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println(count);

        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("Current DateTime: " + currentTime.toString());

    }
}
