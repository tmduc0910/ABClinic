package com.abclinic.test;

import com.abclinic.utils.services.JsonJavaConvertingService;
import com.abclinic.utils.services.RequestHandlingService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        RequestHandlingService requestHandler = new RequestHandlingService();
        JsonJavaConvertingService converter = new JsonJavaConvertingService();

//        TEST GET REQUEST SINGLE DATA
//        try {
//            String getJson = requestHandler.getRequest("https://jsonplaceholder.typicode.com/posts/1");
//            User user = new User();
//            user = (User) converter.mapJsonToObject(getJson, User.class);
//            System.out.println(user);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }

//        TEST GET REQUEST MULTIPLE SAME TYPE DATA
//        try {
//            String getJson = requestHandler.getRequest("https://jsonplaceholder.typicode.com/posts");
//            ArrayList<User> users;
//            users = converter.mapJsonToMultipleObjects(getJson, User.class);
//            for (User user: users) {
//                System.out.println(user);
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }

//        TEST GET REQUEST STATUS WITH MULTIPLE SAME TYPE DATA
//        try {
//            String getJson = requestHandler.getRequest("http://localhost:3000/users");
//            ArrayList<Object> users;
//            users = converter.mapJsonToObjectsWithStatus(getJson, User.class);
//            for (Object user: users) {
//                System.out.println(user);
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }

        //TEST POST REQUEST
        try {
            String postParam = "{\n" + "\"userId\": 106,\r\n" +
                    "    \"id\": 112,\r\n" +
                    "    \"title\": \"Test Title\",\r\n" +
                    "    \"body\": \"Test Body\"" + "\n}";
            String postJson = requestHandler.postRequest(postParam, "http://localhost:3000/posts");
            User user = (User) converter.mapJsonToObject(postJson, User.class);
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
