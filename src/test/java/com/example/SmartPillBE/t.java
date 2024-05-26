package com.example.SmartPillBE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class t {
    public static void main(String[] args) {
        List<String> history = new ArrayList<>();
        history.add("111");
        history.add("222");

        System.out.println("history = " + history.toString());

        System.out.println(history.stream()
                .map( i -> "\"" + i + "\"")
                .toList().toString());
    }
}
