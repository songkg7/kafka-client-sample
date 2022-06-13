package com.example.kafkasubbatch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String name;
    private int age;

    public String introduce() {
        return String.format("%s의 나이는 %d세입니다.", name, age);
    }
}
