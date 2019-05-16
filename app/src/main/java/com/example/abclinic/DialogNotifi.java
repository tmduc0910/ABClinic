package com.example.abclinic;

public class DialogNotifi {
    private String titles;
    private int images;
    private String attatchmentd;
    private String sections;
    private String classe;

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public void setImages(int images) { this.images = images; }

    public void setAttatchmentd(String attatchmentd) {
        this.attatchmentd = attatchmentd;
    }

    public String getTitles() {
        return titles;
    }

    public int getImages() { return images; }

    public String getAttatchmentd() {
        return attatchmentd;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getClasse() {
        return classe;
    }

    public String getSections() {
        return sections;
    }
}