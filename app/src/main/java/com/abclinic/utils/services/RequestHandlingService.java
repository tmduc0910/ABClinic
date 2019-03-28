package com.abclinic.utils.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//Lớp xử lý các dạng Request khác nhau và trả về một String là Response của Request đó gửi từ server
//Đầu vào của các hàm là một URI chỉ địa chỉ URI của resource cần lấy về và tham số đầu vào (nếu có)
public class RequestHandlingService {

    //Hàm xử lí Request dạng GET
    public String getRequest(String url) throws IOException {
        URL requestURL = new URL(url);
        String buffer;

        //Thiết lập một kết nối với server
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();

        //Đặt dạng Request là GET
        connection.setRequestMethod("GET");

        //Lấy mã kết nối từ server, nếu mã là 200 thì kết nối thành công
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {

            //Lần lượt đọc tất cả kí tự được trả về vào một StringBuffer
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((buffer = in.readLine()) != null) {
                response.append(buffer);
            }
            in.close();
            return response.toString();
        } else {
            System.out.println("GET Error!");
            throw new NullPointerException();
        }
    }

//    EXAMPLE postParam:
//            "{\n" + "\"userId\": 101,\r\n" +
//            "    \"id\": 101,\r\n" +
//            "    \"title\": \"Test Title\",\r\n" +
//            "    \"body\": \"Test Body\"" + "\n}";

    public String postRequest(String postParam, String url) throws IOException {
        URL postURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
        connection.setRequestMethod("POST");

        //Đặt Header của Request là application/json để trả về JSON
        connection.setRequestProperty("Content-Type", "application/json");

        //Biến true để có nhận kết quả trả về
        connection.setDoOutput(true);

        //Gửi tham số đầu vào lên server
        OutputStream out = connection.getOutputStream();
        out.write(postParam.getBytes());
        out.flush();
        out.close();

        int responseCode = connection.getResponseCode();

        ////Lấy mã kết nối từ server, nếu mã là 201 thì kết nối thành công
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String buffer;
            StringBuffer response = new StringBuffer();
            while ((buffer = in.readLine()) != null)
                response.append(buffer);
            in.close();
            return response.toString();
        } else {
            System.out.println("POST Error!");
            return null;
        }
    }
}
