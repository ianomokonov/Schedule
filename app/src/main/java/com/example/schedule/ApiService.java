package com.example.schedule;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {

    public static String get(String uri) {
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //Открытие соединения
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); //Считывание ответа
            String result = "";
            String line = "";
            while ((line = rd.readLine()) != null) {
                result += line.replaceAll("\\<.*?>",""); //сохранение ответа и удаление тегов
            }
            rd.close(); // закрытие чтения
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
