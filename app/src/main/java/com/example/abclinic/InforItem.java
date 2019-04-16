package com.example.abclinic;

import java.util.ArrayList;

public class InforItem {

    public String date = "";
    public String name = "";
    public int [] image_src;

    public static ArrayList<InforItem> add_item;

    public InforItem(String date, String name, int [] image_src){
        this.date = date;
        this.name = name;
        this.image_src = image_src;
    }
}
