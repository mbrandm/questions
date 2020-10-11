package com.example.question;

import java.util.List;
import java.util.Set;

public class OutputPerformer {
    protected static final String DEFAULT_ANSWER = "the answer to life, universe and everything is 42";
    protected static final String BULLET_POINT = "\t\u2022 ";


    public void performDefaultAnswer() {
        performOutput(Set.of(DEFAULT_ANSWER));
    }

    public void performOutput(Set<String> answers) {
        StringBuilder sb = new StringBuilder();

        for (String answer : answers) {
            if (sb.length() > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(BULLET_POINT)
                    .append(answer);
        }
        System.out.println(sb.toString());
    }

    public void outputMessages(List<String> messages) {
        messages.forEach(System.out::println);
    }

    public void outputMessage(String message) {
        System.out.println(message);
    }
}