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
    public static void main(String[] args) throws IOException {
        String filePath = "https://drive.google.com/file/d/1sQMokp53qx4nvd4JUb8rsnmr5E7Kf4tX/view";
        URL url = new URL(filePath);
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
//        connection
//                .setRequestProperty("Authorization",
//                        "OAuth "+accessToken);
//
//        String docText = convertStreamToString(connection.getInputStream());
        File file = new File("/Users/ismatulmaula/Downloads/BUKTIPERMANEN_SISWA_0057386892.jpeg");
//        Files.copy(connection.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Tesseract tesseract = new Tesseract();
        try {

//            tesseract.setDatapath(filePath);
            String text = tesseract.doOCR(file);
            System.out.print(text);
        }
        catch (TesseractException e) {
            e.printStackTrace();
        }

    }
}