package com.example.question;

import java.util.Arrays;
import java.util.Set;

public class OutputPerformer {
    private static final String DEFAULT_ANSWER = "the answer to life, universe and everything is 42";

    public static void performDefaultAnswer() {
        performOutput(Set.of(DEFAULT_ANSWER));
    }

    public static void performOutput(Set<String> answers) {
        StringBuilder sb = new StringBuilder();
        answers.forEach(answer -> sb.append("\t•\t")
                .append(answer));
        System.out.println(sb.toString());
    }
}
