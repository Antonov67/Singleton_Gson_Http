package org.example;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;

public class Main {

    public static final String PATH = "https://jsonplaceholder.typicode.com/albums";
    private static HttpURLConnection connection;
    private static URL url;


    public static void main(String[] args) {
        try {
            url = new URL(PATH);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            ConnectionManager.getInstance().registerConnection(1, connection);

            Gson gson = new Gson();
            Album[] arr= gson.fromJson(ConnectionManager.getInstance().getResponseContent(1).toString(),Album[].class);
            System.out.println(Arrays.toString(arr));


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}