package com.abclinic.entity;

import java.util.ArrayList;

public class Response {
    private String status;
    private ArrayList data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }

    public String toString() {
        return "Status: " + status + ", Data: " + data;
    }
}
