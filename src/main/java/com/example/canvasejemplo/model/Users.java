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

    private String jp1;
    private String jp2;
    private String winner;

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

//            String uri= HelloApplication.class.getResource("users.txt").getPath();
//            System.out.println(uri);
//            FileOutputStream fos = new FileOutputStream(new File(uri));
            FileOutputStream fos = new FileOutputStream(new File("dataBase\\Users.txt"));
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

//            String uri= HelloApplication.class.getResource("users.txt").getPath();
//            String uri= HelloApplication.class.getResource("fondo1.gif").getPath();
//            System.out.println(uri);
            File file = new File("dataBase\\Users.txt");
//            File file = new File(uri);
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

    public void setJP1(String name){
        jp1=name;
        boolean isregistered=false;

        for(User u:usersList){
            if (u.getName().equals(name)){
                isregistered=true;
            }
        }
        if(!isregistered){
            usersList.add(new User(name,0));
        }
    }

    public void setJP2(String name){
        jp2=name;

        boolean isregistered=false;

        for(User u:usersList){
            if (u.getName().equals(name)){
                isregistered=true;
            }
        }
        if(!isregistered) {
            usersList.add(new User(name, 0));
        }
    }

    public String getJP1(){
        return jp1;
    }

    public String getJP2(){
        return jp2;
    }

    public void setWinner (String name){winner=name;}

    public String getWinner(){
        return winner;
    }





}
