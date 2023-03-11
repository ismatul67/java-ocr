package org.example;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws IOException {
        String filePath = "https://drive.google.com/file/d/1sQMokp53qx4nvd4JUb8rsnmr5E7Kf4tX/view";
        URL url = new URL(filePath);
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");

        File file = new File("D:/Download/motivation-quote-1.jpg");
//        Files.copy(connection.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Tesseract tesseract = new Tesseract();
        try {

            tesseract.setLanguage("eng");
            tesseract.setDatapath("D:/Download/tessdata");


//            tesseract.setDatapath(filePath);

            String text = tesseract.doOCR(file);
//            System.out.println(ANSI_RED + text + ANSI_RESET);
//            System.out.println(ANSI_BLUE + text + ANSI_RESET);
//
//            System.out.print(text);
            print(text);
        }
        catch (TesseractException e) {
            e.printStackTrace();
        }

    }

    private static void print(String text){
        String[] arr = text.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : arr){
            if (word.contains("o") || word.contains("O")){
                sb.append(ANSI_BLUE).append(word).append(ANSI_RESET).append(" ");
            } else {
                sb.append(word).append(" ");
            }
        }
        System.out.println(sb);
    }
}