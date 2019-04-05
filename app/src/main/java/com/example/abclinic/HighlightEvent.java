package com.example.abclinic;

import java.util.ArrayList;

class HighlightEvent {

    public static ArrayList<HighlightEvent> dateCollectionArr;
    public String date;
    public String name;
    public String subject;
    public String description;
    public boolean seen;

    public HighlightEvent(String date, String name, String subject, String description, Boolean seen){
        this.date = date;
        this.name = name;
        this.subject = subject;
        this.description = description;
        this.seen = seen;
    }
}
