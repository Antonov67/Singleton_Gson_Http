package org.example;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;

public class Main {

    public static final String PATH1 = "https://jsonplaceholder.typicode.com/albums";
    public static final String PATH2 = "https://jsonplaceholder.typicode.com/users";


    public static void main(String[] args) {
        try {
            URL url;
            HttpURLConnection connection;

            //первое соединение
            url = new URL(PATH1);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            ConnectionManager.getInstance().registerConnection(1, connection);

            //второе соединение
            url = new URL(PATH2);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            ConnectionManager.getInstance().registerConnection(2, connection);

            Gson gson = new Gson();
            Album[] albums = gson.fromJson(ConnectionManager.getInstance().getResponseContent(1).toString(),Album[].class);
           // System.out.println(Arrays.toString(albums));
            User[] users = gson.fromJson(ConnectionManager.getInstance().getResponseContent(2).toString(),User[].class);
            //System.out.println(Arrays.toString(users));
            System.out.println(users[0]);

            System.out.println(ConnectionManager.getInstance().getCountActiveConnections());

            ConnectionManager.getInstance().disconectConnection(1);
            System.out.println(ConnectionManager.getInstance().getCountActiveConnections());
            ConnectionManager.getInstance().disconectConnection(2);
            System.out.println(ConnectionManager.getInstance().getCountActiveConnections());


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}