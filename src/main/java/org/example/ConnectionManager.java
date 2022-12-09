package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {

    private Map<Integer, HttpURLConnection> connections = new HashMap<>();
    private static ConnectionManager instance;
    private ConnectionManager(){}
    public static ConnectionManager getInstance(){
        if (instance == null){
            instance = new ConnectionManager();
        }
        return instance;
    }
    public void registerConnection(Integer id, HttpURLConnection connection){
        connections.put(id, connection);
    }
    public void disconectConnection(Integer id){
        HttpURLConnection connection = connections.get(id);
        if (connection != null){
            connection.disconnect();
            connections.remove(id);
        }
    }

    public StringBuffer getResponseContent(Integer id){
        BufferedReader reader;
        HttpURLConnection connection;
        String line;
        connection = connections.get(id);
        StringBuffer responseContent = new StringBuffer();
        try {
            int status = connections.get(id).getResponseCode();
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            } else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseContent;
    }

    public int getCountActiveConnections(){
        return connections.size();
    }

}
