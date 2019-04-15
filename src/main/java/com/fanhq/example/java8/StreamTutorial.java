package com.fanhq.example.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author fanhaiqiu
 * @date 2019/4/15
 */
public class StreamTutorial {

    public static void main(String[] args) {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        //filter
        stringCollection.stream().filter(x -> {
            if (x.startsWith("a")) {
                return true;
            }
            return false;
        }).collect(Collectors.toList()).forEach(x -> {
            System.out.println(x);
        });
        //sorted
        stringCollection.stream().sorted().collect(Collectors.toList()).forEach(x -> {
            System.out.println(x);
        });
        //map
        stringCollection.stream().map(x -> {
            return x.toUpperCase();
        }).collect(Collectors.toList()).forEach(x -> {
            System.out.println(x);
        });
        //match
        boolean anyStartsWithA = stringCollection
                .stream()
                .anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);
        boolean allStartsWithA = stringCollection
                .stream()
                .allMatch((s) -> s.startsWith("a"));
        System.out.println(allStartsWithA);
        boolean noneStartsWithZ = stringCollection
                .stream()
                .noneMatch((s) -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);
        //count
        long startsWithB = stringCollection
                .stream()
                .filter((s) -> s.startsWith("b"))
                .count();
        System.out.println(startsWithB);
        //reduce
        Optional<String> reduced1 = stringCollection.stream().reduce((x, y) -> x + "#" + y);
        System.out.println(reduced1.get());
        String reduced2 = stringCollection.stream().reduce("identity", (x, y) -> {
            return x + "#" + y;
        });
        System.out.println(reduced2);

    }
}
