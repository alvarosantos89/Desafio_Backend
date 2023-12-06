package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

public class Projeto {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Insira o URL: ");
        String urlStr = scanner.nextLine();

        System.out.print("Insira a frase para busca: ");
        String inputPhrase = scanner.nextLine();

        try {
            String webpageContent = fetchWebpageContent(urlStr);

            int phraseOccurrences = countOccurrences(webpageContent, inputPhrase);
            System.out.println(inputPhrase + "=> repete " + phraseOccurrences + " vezes");

            String[] words = StringUtils.split(inputPhrase);
            System.out.println("Resultados:");

            for (String currentElement : words) {
                int occurrences = countOccurrences(webpageContent, currentElement);
                System.out.println(currentElement + " : " + occurrences);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static String fetchWebpageContent(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        return content.toString();
    }

    private static int countOccurrences(String text, String phrase) {
        int count = 0;
        int index = text.indexOf(phrase);

        while (index != -1) {
            count++;
            index = text.indexOf(phrase, index + 1);
        }

        return count;
    }
}
