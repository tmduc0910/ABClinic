//
//package com.abclinic.entity;
//
//import com.abclinic.utils.DateTimeUtils;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//        "id",
//        "status",
//        "note",
////        "prescription",
//        "createdAt",
//        "dietitian",
//        "diagnose"
//})
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class DietRecord {
//
//    @JsonProperty("id")
//    private long id;
//    @JsonProperty("status")
//    private int status;
//    @JsonProperty("note")
//    private String note;
//    //    @JsonProperty("prescription")
////    private String prescription;
//    @JsonProperty("createdAt")
//    private List<Integer> createdAt;
//    @JsonProperty("dietitian")
//    private UserInfo dietitian;
//    @JsonProperty("diagnose")
//    private String diagnose;
//
//    @JsonProperty("id")
//    public long getId() {
//        return id;
//    }
//
//    @JsonProperty("id")
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    @JsonProperty("status")
//    public Integer getStatus() {
//        return status;
//    }
//
//    @JsonProperty("status")
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    @JsonProperty("note")
//    public String getNote() {
//        return note;
//    }
//
//    @JsonProperty("note")
//    public void setNote(String note) {
//        this.note = note;
//    }
//
////    @JsonProperty("prescription")
////    public String getPrescription() {
////        return prescription;
////    }
////
////    @JsonProperty("prescription")
////    public void setPrescription(String prescription) {
////        this.prescription = prescription;
////    }
//
//    @JsonProperty("createdAt")
//    public LocalDateTime getCreatedAt() {
//        return DateTimeUtils.parseDateTime(createdAt);
//    }
//
//    @JsonProperty("createdAt")
//    public void setCreatedAt(List<Integer> createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    @JsonProperty("dietitian")
//    public UserInfo getDietitian() {
//        return dietitian;
//    }
//
//    @JsonProperty("dietitian")
//    public void setDietitian(UserInfo dietitian) {
//        this.dietitian = dietitian;
//    }
//
//    @JsonProperty("diagnose")
//    public String getDiagnose() {
//        return diagnose;
//    }
//
//    @JsonProperty("diagnose")
//    public void setDiagnose(String diagnose) {
//        this.diagnose = diagnose;
//    }
//}