package com.example.question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {


    private void processInput() {

        QuestionManager questionManager = new QuestionManager();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String inputLine = reader.readLine();
                if ("q".equalsIgnoreCase(inputLine.strip()) || "quit".equalsIgnoreCase(inputLine.strip())) {
                    break;
                }
                questionManager.process(inputLine);

            }
        } catch (IOException e) {
            System.out.println("Sorry beloved user, an IOException occured");
        }
    }

    public static void main(String... args) {
        new Main().processInput();
    }
}
