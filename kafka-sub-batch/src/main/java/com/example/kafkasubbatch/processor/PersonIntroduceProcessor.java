package com.example.kafkasubbatch.processor;

import com.example.kafkasubbatch.domain.Person;
import lombok.NonNull;
import org.springframework.batch.item.ItemProcessor;

public class PersonIntroduceProcessor implements ItemProcessor<String, String> {

    public static final String SEPARATOR = " ";

    @Override
    public String process(@NonNull String item) {
        Person person = toPerson(item);
        return person.introduce();
    }

    public Person toPerson(String item) {
        String[] split = item.split(SEPARATOR);
        return Person.builder()
                .name(split[0])
                .age(Integer.parseInt(split[1]))
                .build();
    }
}
