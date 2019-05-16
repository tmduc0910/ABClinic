package com.example.abclinic;

import java.util.ArrayList;

public class ItemMeal {

    public String date = "";
    public String name = "";
    public String comment = "";
    public int [] image_src;

    public static ArrayList<ItemMeal> add_item;

    public ItemMeal(String date, String name, String comment, int [] image_src){
        this.date = date;
        this.name = name;
        this.comment = comment;
        this.image_src = image_src;
    }
}
