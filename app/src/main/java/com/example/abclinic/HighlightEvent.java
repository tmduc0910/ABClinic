package com.example.abclinic;

import java.util.ArrayList;

class HighlightEvent {
    public String date="";
    public String name="";
    public String subject="";
    public String description="";
    public boolean seen ;


    public static ArrayList<HighlightEvent> date_collection_arr;
    public HighlightEvent(String date, String name, String subject, String description, Boolean seen){

        this.date=date;
        this.name=name;
        this.subject=subject;
        this.description= description;
        this.seen = seen;

    }
}
