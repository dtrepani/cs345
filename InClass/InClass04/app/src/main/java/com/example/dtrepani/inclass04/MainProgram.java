package com.example.dtrepani.inclass04;

public class MainProgram {
    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
            ConsoleIO.printLine("Hello world");
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {

            }
        }
    }
}
