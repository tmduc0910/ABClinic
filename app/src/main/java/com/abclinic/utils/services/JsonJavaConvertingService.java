package com.abclinic.utils.services;

import com.abclinic.entity.deprecated.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//Lớp này cung cấp các hàm để biến đổi một string JSON trả về từ hệ thống thành một Object của Java
public class JsonJavaConvertingService {

    //Tách một string các object của JSON thành một ArrayList các object con đó
    public ArrayList<String> splitJsonObjects(String jsonString) throws JSONException {
        ArrayList<String> objects = new ArrayList<>();

        //Tạo một mảng JSON từ string JSON đầu vào
        JSONArray jsonArray = new JSONArray(jsonString);
        if (jsonArray.length() == 0) return null;

        //Thêm vào objects lần lượt các phần tử trong mảng JSON
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            objects.add(jsonObject.toString());
        }
        return objects;
    }

    //Biến đổi một object JSON thành một object Java
    public Object mapJsonToObject(String jsonString, Class objClass) {

        //Khởi tạo một ObjectMapper để map 2 Object
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        //Thực hiện map 2 object
        try {
            return mapper.readValue(jsonString, objClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Biến đổi một String các object JSON có các thuộc tính giống nhau thành các Object có class T
//    VD:
//    [
//        {
//            "id": 5,
//            "name": A,
//            "age": 15
//        },
//        {
//            "id": 6,
//            "name": B,
//            "age": 20
//        }
//    ]
    public <T> ArrayList<T> mapJsonToMultipleObjects(String jsonString, Class T) throws JSONException {
        ArrayList<T> objects = new ArrayList<>();
        ArrayList<String> jsonObjects = splitJsonObjects(jsonString);
        for (String s : jsonObjects) {
            objects.add((T) mapJsonToObject(s, T));
        }
        return objects;
    }

    //Biến đổi một String các object JSON có các thuộc tính khác nhau thành các Object có class c1 hoặc class c2
//    VD:
//    [
//        {
//            "id": 5,
//            "name": A,
//            "age": 15
//        },
//        {
//            "album": 3,
//            "songName": XYZ,
//            "comment": "nhac hay",
//            "uploadDate": "29/2/2019"
//        }
//    ]
    //TODO
    public ArrayList<Object> mapJsonToDifferentObjects(String jsonString, Class c1, Class c2) {
        ArrayList<Object> objects = new ArrayList<>();
        return objects;
    }

    //Biến đổi một String bao gồm một thuộc tính Status (success hoặc failed) và một (hoặc một mảng) object
    // thành object Java
//    VD:
//    [
//        "status": "Success",
//        "data": [
//        {
//            "id": 1,
//            "name": C
//        }
//            ]
//    ]
    //TODO
    public ArrayList<Object> mapJsonToObjectsWithStatus(String jsonString, Class objClass) throws JsonProcessingException {
        ArrayList<Object> objects = new ArrayList<>();
        Response r = (Response) mapJsonToObject(jsonString, Response.class);
        objects.add(r.getStatus());

        for (int i = 0; i < r.getData().size(); i++) {
            String temp = new ObjectMapper().writeValueAsString(r.getData().get(i));
            objects.add(mapJsonToObject(temp, objClass));
        }

//        ArrayList<String> stringList = splitJsonObjects(jsonString);
//        for (String s: stringList) {
//            System.out.println(s + "\n");
//            System.out.println((Response) mapJsonToObject(s, Response.class) + "\n");
//        }
        return objects;
    }
}
