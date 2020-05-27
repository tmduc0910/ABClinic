package com.example.abclinic;

import java.util.ArrayList;

public class HighlightEvent {
    public String date="";
    public String name="";
    public String description="";
    public boolean seen ;


    public static ArrayList<HighlightEvent> date_collection_arr;
    public HighlightEvent(String date, String name, String description, Boolean seen){

        this.date=date;
        this.name=name;
        this.description= description;
        this.seen = seen;

    }
}
