package org.example;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixRead;

public class Main {

    public static  final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        System.out.println("Hello");
    }
    public static void tesseracVersionTess4j() {

        Tesseract tesseract = new Tesseract();
        try {
//            tesseract.setDatapath(filePath);
            String text = tesseract.doOCR(new File("/Users/ismatulmaula/Documents/sawitpro/java-ocr/src/main/java/resources/ImageWithWords1.jpg"));
            System.out.print(text);
        }
        catch (TesseractException e) {
            e.printStackTrace();
        }

    }

    public static void tesseracVersionJavacpp(String[] args) {
        BytePointer outText;

       tesseract.TessBaseAPI api = new tesseract.TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init(null, "eng") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        // Open input image with leptonica library
        lept.PIX image = pixRead(args.length > 0 ? args[0] : "/Users/ismatulmaula/Documents/sawitpro/java-ocr/src/main/java/resources/ImageWithWords1.jpg");
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        System.out.println("OCR output:\n" + outText.getString());

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
    }

    private  static void print (String text){
        String [] arr = text.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String word: arr) {
            if (word.contains("o") || word.contains("O")){
                stringBuilder.append(ANSI_BLUE).append(word).append(ANSI_RESET).append(" ");
            }else{
                stringBuilder.append(word).append(" ");
            }
        }
    }
    private  static  File getFileFromGDrive() throws IOException{
        String filePath = "https://drive.google.com/file/d/1sQMokp53qx4nvd4JUb8rsnmr5E7Kf4tX/view";
        URL url = new URL(filePath);
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
        String accessToken = "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";
        connection
                .setRequestProperty("Authorization",
                        "OAuth "+accessToken);

        String docText = String.valueOf(connection.getInputStream());
        System.out.println(docText);
        File file = new File("/Users/ismatulmaula/Documents/sawitpro/java-ocr/src/main/resources");
        Files.copy(connection.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return  file;
    }
}