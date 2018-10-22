package com.leetCode.java11;

import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Hachel on 2018/10/22
 */
public class NewFeature {

    public static void main(String[] args) {
        Flux.just("Hello", "World").subscribe(System.out::println);
        Consumer<String> consumer = (x) -> {
            System.out.println(x);
        };
        consumer.accept("hello world consumer");

        Supplier<String> supplier = () -> {
            System.out.println("hello world supplier");
            return "1";
        };
        System.out.println(supplier.get());
        List<String> givenList = Arrays.asList("a", "bb", "ccc", "dd");
        var result = givenList.stream()
                .collect(Collectors.toMap(x -> x, y -> y));
        System.out.println(result.size());

        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.baidu.com"))
                .GET()
                .build();
        var client = HttpClient.newHttpClient();
        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(x ->{
                        System.out.println("b");
                        System.out.println(x.body());
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
