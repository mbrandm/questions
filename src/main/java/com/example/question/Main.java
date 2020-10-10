package com.example.question;

import com.example.question.validation.InputHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {


    private void processInput() {
        String line = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (!"quit".equalsIgnoreCase(line)) {
                line = reader.readLine();

                InputHandler inputHandler = new InputHandler(line);
                if (!inputHandler.isInputValid()) {
                    inputHandler.getErrors()
                            .forEach(System.out::println);
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            //TODO: output erromessage
            System.out.println("Ein Fehler in der Verarbeitung ist aufgetreten");
            e.printStackTrace();
        }
    }

    public static void main(String... args) {

//        String inputString = "What is the question? \"test\" \"test2\"  ";
//
//        String[] str = inputString.split("\\?", 2);
//
//        for(String s : str) {
//            System.out.println(s);
//        }


        new Main().processInput();
    }
}
