package com.abclinic.entity;

public class GeneralPractitioner extends Doctor {
    private int coordinatorId;
    private String type;

    public int getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(int coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
