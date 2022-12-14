package org.example;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;

public class Main {

    public static final String PATH1 = "https://jsonplaceholder.typicode.com/albums";
    public static final String PATH2 = "https://jsonplaceholder.typicode.com/users";

    public static final String PATH3 = "https://jsonplaceholder.typicode.com/comments?postId=1";


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

            //третье соединение
            url = new URL(PATH3);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            ConnectionManager.getInstance().registerConnection(3, connection);

            Gson gson = new Gson();
            try {
                Album[] albums = gson.fromJson(ConnectionManager.getInstance().getResponseContent(1).toString(),Album[].class);
                // System.out.println(Arrays.toString(albums));
            }catch (Exception e){
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Ответ сервера: " + ConnectionManager.getInstance().getServerAnswerForConnection(1));
            }

            try {
                User[] users = gson.fromJson(ConnectionManager.getInstance().getResponseContent(2).toString(),User[].class);
                //System.out.println(Arrays.toString(users));
                System.out.println(users[0]);
            }catch (Exception e){
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Ответ сервера: " + ConnectionManager.getInstance().getServerAnswerForConnection(2));
            }

            try {
                Post[] posts = gson.fromJson(ConnectionManager.getInstance().getResponseContent(3).toString(),Post[].class);
                System.out.println(posts[0]);
            }catch (Exception e){
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Ответ сервера: " + ConnectionManager.getInstance().getServerAnswerForConnection(3));
            }




            System.out.println(ConnectionManager.getInstance().getCountActiveConnections());

            ConnectionManager.getInstance().disconectConnection(1);
            System.out.println(ConnectionManager.getInstance().getCountActiveConnections());
            ConnectionManager.getInstance().disconectConnection(2);
            System.out.println(ConnectionManager.getInstance().getCountActiveConnections());
            ConnectionManager.getInstance().disconectConnection(3);
            System.out.println(ConnectionManager.getInstance().getCountActiveConnections());


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //прочитаем из файла

        try {
            FileReader reader = new FileReader("test.json");
            Gson gson = new Gson();
            Root root = gson.fromJson(reader,Root.class);
            System.out.println(root);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}