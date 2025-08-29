package org.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;


public class Lox {
    static boolean hadError = false;

    private static void run(String sourceInput) {
        Scanner scanner = new Scanner(sourceInput);
        List<Token> tokens = scanner.scanTokens();

        for(Token token : tokens) {
            System.out.println();
        }
    }

    static void error(String message, int line) {
        report(line, "", message);
    }

    private static void report(int lineNumber, String errorLine, String message) {
        System.err.println("[line " + lineNumber + "] Error" + errorLine + ": " + message);
        hadError = true;
    }
    private static void processInputFile(String inputFilePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(inputFilePath));
        run(new String(bytes, Charset.defaultCharset()));

        if(hadError) {
            System.exit(0);
        }
        hadError = false;
    }

    private static void processInputPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        while(true) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            run(line);
        }
    }

    public static void main(String[] args) throws IOException {
        if(args.length > 1){
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if(args.length == 1){
            processInputFile(args[0]);
        } else {
            processInputPrompt();
        }
    }
}