package com.example.canvasejemplo.model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.example.canvasejemplo.HelloApplication;
import com.google.gson.Gson;

public class Users {

    private static Users users=new Users();
    private ArrayList<User> usersList=new ArrayList<>();

    public void addUser(){

    }

    public void updateUser(){

    }

    public ArrayList<User> getList(){
        return usersList;
    }

    public static Users getInstance(){
        return users;
    }

    public void writeJson(){

        Gson gson = new Gson();

        String usersJson = gson.toJson(usersList);

        try {

            String uri= HelloApplication.class.getResource("users.txt").getPath();
            System.out.println(uri);
            FileOutputStream fos = new FileOutputStream(new File(uri));
            fos.write( usersJson.getBytes(StandardCharsets.UTF_8) );
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readJson() {
        try {

            String uri= HelloApplication.class.getResource("users.txt").getPath();
//            String uri= HelloApplication.class.getResource("fondo1.gif").getPath();
            System.out.println(uri);
            File file = new File(uri);
            FileInputStream fis = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String json = "";
            String line;
            if((line=reader.readLine())!=null){
                json= line;
            }
            fis.close();

            Gson gson = new Gson();
            User[] usersFromJson = gson.fromJson(json, User[].class);
            ArrayList<User> toSendUsers = new ArrayList<>();


            if(usersFromJson!=null)toSendUsers.addAll(List.of(usersFromJson));

            usersList=toSendUsers;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
