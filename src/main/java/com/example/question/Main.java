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
                if ("quit".equalsIgnoreCase(inputLine)) {
                    break;
                }
                questionManager.process(inputLine);

            }
        } catch (IOException e) {
            System.out.println("Ein Fehler in der Verarbeitung ist aufgetreten");
        }
    }

    public static void main(String... args) {
        new Main().processInput();
    }
}
